package htcc.gateway.service.component.filter;

import htcc.common.constant.Constant;
import htcc.common.util.StringUtil;
import htcc.gateway.service.entity.request.LoginRequest;
import htcc.gateway.service.service.authentication.JwtTokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

        if (uri.startsWith(Constant.BASE_API_GATEWAY_PATH + Constant.PUBLIC_API_PATH) ||
                uri.startsWith(Constant.BASE_API_EMPLOYEE_PATH + Constant.PUBLIC_API_PATH) ||
                uri.startsWith(Constant.BASE_API_ADMIN_PATH + Constant.PUBLIC_API_PATH)) {
            shouldNotFilter = true;
        }

        if (uri.endsWith(Constant.SWAGGER_DOCS_PATH)) {
            shouldNotFilter = true;
        }

        return shouldNotFilter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {

        try {
            String requestTokenHeader = request.getHeader(AUTHORIZATION);

            LoginRequest loginRequest = null;
            String       jwtToken     = null;
            if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER)) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    loginRequest = jwtTokenService.getLoginInfo(jwtToken);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw new Exception(String.format("JWT Token [%s] Invalid", requestTokenHeader));
                }
            } else {
                throw new Exception(String.format("JWT Token [%s] Invalid", requestTokenHeader));
            }

            if (loginRequest != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = jwtTokenService.loadUserByUsername(StringUtil.toJsonString(loginRequest));
                if (jwtTokenService.validateToken(jwtToken, loginRequest.username)) {
                    UsernamePasswordAuthenticationToken detail =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    detail.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(detail);
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("doFilterInternal ex: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

}
