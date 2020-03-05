package htcc.gateway.service.service;

import constant.Constant;
import htcc.gateway.service.config.file.SecurityConfig;
import htcc.gateway.service.entity.jpa.AdminUser;
import htcc.gateway.service.entity.jpa.BaseUser;
import htcc.gateway.service.entity.request.LoginRequest;
import htcc.gateway.service.service.authentication.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import util.NumberUtil;
import util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenService implements UserDetailsService, Serializable {

	//<editor-fold defaultstate="collapsed" desc="Autowired">
	private static final long serialVersionUID = -2550185165626007488L;

	@Autowired
	private SecurityConfig config;

	@Autowired
	private AuthenticationService authenService;
	//</editor-fold>

	@Override
	public UserDetails loadUserByUsername(String object) throws UsernameNotFoundException {
		LoginRequest req = null;
		try {
			req = StringUtil.fromJsonString(object, LoginRequest.class);
		} catch (Exception e){
			String err = String.format("loadUserByUsername ex, raw Request %s", object);
			throw new UsernameNotFoundException(err);
		}

		BaseUser user = authenService.getUser(req);

		if (user == null) {
			String err = String.format("User not found with username: [%s] | raw Request %s", req.username, object);
			throw new UsernameNotFoundException(err);
		}

		return new User(user.username, user.password, new ArrayList<>());
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public LoginRequest getLoginInfoFromToken(String token) {
		int clientId = NumberUtil.getIntValue(getClaimFromToken(token, c -> c.get(Constant.CLIENT_ID)));
		String companyId = StringUtil.valueOf(getClaimFromToken(token, c -> c.get(Constant.COMPANY_ID)));
		String username = getUsernameFromToken(token);

		return new LoginRequest(clientId, companyId, username, "");
	}

	private Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(config.jwt.key).parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		boolean ignore = ignoreTokenExpiration(token);
		if (ignore){
			return true;
		}

		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private boolean ignoreTokenExpiration(String token) {
		return false;
	}

	public String generateToken(LoginRequest request) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(Constant.CLIENT_ID, request.clientId);
		claims.put(Constant.COMPANY_ID, StringUtil.valueOf(request.companyId));

		long now = System.currentTimeMillis();

		return Jwts.builder().setClaims(claims)
				.setSubject(request.username)
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(now + config.jwt.expireSecond * 1000))
				.signWith(SignatureAlgorithm.HS512, config.jwt.key).compact();
	}

	public boolean validateToken(String token, String reqUsername) {
		String username = getUsernameFromToken(token);
		return (username.equals(reqUsername) && !isTokenExpired(token));
	}

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
