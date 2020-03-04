package htcc.gateway.service.component.filter;

import htcc.gateway.service.component.service.JwtTokenService;
import htcc.gateway.service.config.file.ServiceConfig;
import htcc.gateway.service.constant.Constant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER        = "Bearer ";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String  uri             = request.getRequestURI();
        boolean shouldNotFilter = false;

        if (!uri.startsWith(Constant.API_PATH)) {
            shouldNotFilter = true;
        }

        if (uri.startsWith(Constant.BASE_API_GATEWAY_PATH + Constant.PUBLIC_API_PATH)) {
            shouldNotFilter = true;
        }

        return shouldNotFilter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String requestTokenHeader = request.getHeader(AUTHORIZATION);

        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER)) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenService.getUsernameFromToken(jwtToken);
            } catch (Exception e) {
                log.error("JWT Token [{}] Invalid", requestTokenHeader, e);
            }
        } else {
            log.error("JWT Token [{}] Invalid", requestTokenHeader);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtTokenService.loadUserByUsername(username);

            if (jwtTokenService.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken detail =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                detail.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(detail);
            }
        }

        chain.doFilter(request, response);
    }

}
