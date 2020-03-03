package htcc.gateway.service.component.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import htcc.gateway.service.component.authentication.JwtTokenUtil;
import htcc.gateway.service.config.file.ServiceConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.ExpiredJwtException;
import htcc.gateway.service.component.service.JwtUserDetailsService;

@Component
@Log4j2
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${zuul.prefix}")
	private String apiPath;

	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER = "Bearer ";

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String uri = request.getRequestURI();
		boolean shouldNotFilter = false;

		if (!uri.startsWith(apiPath)) {
			shouldNotFilter = true;
		}

		if (uri.startsWith(ServiceConfig.BASE_API_PATH + ServiceConfig.PUBLIC_API_PATH)) {
			shouldNotFilter = true;
		}

		logger.info(String.format("{%s} - {%s}", uri, shouldNotFilter));
		return shouldNotFilter;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		logger.info("Filter");
		String requestTokenHeader = request.getHeader(AUTHORIZATION);

		String username = null;
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER)) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (Exception e) {
				log.error("JWT Token [{}] Invalid", requestTokenHeader, e);
			}
		} else {
			log.error("JWT Token [{}] Invalid", requestTokenHeader);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken detail = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				detail.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(detail);
			}
		}
		chain.doFilter(request, response);
	}

}
