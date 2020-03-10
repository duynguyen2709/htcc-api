package htcc.gateway.service.service.authentication;

import htcc.common.constant.AccountStatusEnum;
import htcc.common.constant.Constant;
import htcc.common.service.ICallback;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.NumberUtil;
import htcc.common.util.StringUtil;
import htcc.gateway.service.config.file.RedisBuzConfig;
import htcc.gateway.service.config.file.SecurityConfig;
import htcc.gateway.service.entity.jpa.BaseUser;
import htcc.gateway.service.entity.request.LoginRequest;
import htcc.gateway.service.service.RedisService;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
public class JwtTokenService implements UserDetailsService, Serializable {

	//<editor-fold defaultstate="collapsed" desc="Autowired">
	private static final long serialVersionUID = -2550185165626007488L;

	@Autowired
	private SecurityConfig config;

	@Autowired
	private AuthenticationService authenService;

	@Autowired
	private RedisService redis;

	@Autowired
	private RedisBuzConfig redisConfig;
	//</editor-fold>

	@Override
	public UserDetails loadUserByUsername(String object) throws UsernameNotFoundException {
		boolean isLoginDashboard = StringUtil.isJsonString(object);
		LoginRequest req = null;

		try {
			if (isLoginDashboard) {
				req = StringUtil.fromJsonString(object, LoginRequest.class);
			} else {
				req = new LoginRequest(0, "", object, "");
			}
		} catch (Exception e){
			String err = String.format("loadUserByUsername ex, raw Request [%s]", object);
			log.error(err, e.getMessage());
			throw new UsernameNotFoundException(err);
		}

		BaseUser user = authenService.getUser(req);

		if (user == null) {
			String err = String.format("User not found with username: [%s] | raw Request %s", req.username, object);
			throw new UsernameNotFoundException(err);
		}

		return User.withUsername(user.username)
				.password(user.password)
				.authorities(Collections.emptyList())
				.accountExpired(false)
				.accountLocked(user.status == AccountStatusEnum.BLOCK.getValue())
				.credentialsExpired(false)
				.disabled(false)
				.build();
	}

	private String getUsername(String token) {
		return getClaim(token, Claims::getSubject);
	}

	public LoginRequest getLoginInfo(String token) {
		int clientId = NumberUtil.getIntValue(getClaim(token, c -> c.get(Constant.CLIENT_ID)));
		String companyId = StringUtil.valueOf(getClaim(token, c -> c.get(Constant.COMPANY_ID)));
		String username = getUsername(token);

		return new LoginRequest(clientId, companyId, username, "");
	}

	private Date getExpireDate(String token) {
		return getClaim(token, Claims::getExpiration);
	}

	private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaims(String token) {
		return Jwts.parser().setSigningKey(config.jwt.key).parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		boolean ignore = ignoreTokenExpire(token);
		if (ignore){
			return true;
		}

		Date expiration = getExpireDate(token);
		return expiration.before(new Date());
	}

	private boolean ignoreTokenExpire(String token) {
		return false;
	}

	private String generateToken(LoginRequest request) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(Constant.CLIENT_ID, request.clientId);
		claims.put(Constant.COMPANY_ID, StringUtil.valueOf(request.companyId));

		long now = System.currentTimeMillis();
		log.info(String.format("Generate new token for [%s-%s-%s], expired at [%s]",
				request.clientId, StringUtil.valueOf(request.companyId) ,request.username,
				DateTimeUtil.parseTimestampToDateString(now + config.jwt.expireSecond * 1000)));

		return Jwts.builder().setClaims(claims)
				.setSubject(request.username)
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(now + config.jwt.expireSecond * 1000))
				.signWith(SignatureAlgorithm.HS512, config.jwt.key).compact();
	}

	public String getToken(LoginRequest request) {
		ICallback genTokenCb = new ICallback() {
			@Override
			public Object callback() {
				return generateToken(request);
			}
		};

		return StringUtil.valueOf(redis.getOrSet(genTokenCb,
												config.jwt.expireSecond,
												redisConfig.tokenFormat,
												request.clientId,
												StringUtil.valueOf(request.companyId),
												request.username));
	}

	public boolean validateToken(String token) throws ExpiredJwtException {
		try {
			Jwts.parser().setSigningKey(config.jwt.key).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			log.warn(String.format("Invalid JWT token [%s]: [%s]", ex.getMessage(),
					StringUtil.valueOf(token)));
		}
		return false;
	}

}
