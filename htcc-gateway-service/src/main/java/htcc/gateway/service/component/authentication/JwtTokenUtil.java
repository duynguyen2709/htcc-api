package htcc.gateway.service.component.authentication;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import htcc.gateway.service.config.file.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	@Autowired
	private ServiceConfig config;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
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
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		long now = System.currentTimeMillis();

		return Jwts.builder().setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(now + config.jwt.expireSecond * 1000))
				.signWith(SignatureAlgorithm.HS512, config.jwt.key).compact();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
