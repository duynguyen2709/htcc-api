package htcc.gateway.service.component.filter;

import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.util.StringUtil;
import htcc.gateway.service.config.file.SecurityConfig;
import htcc.gateway.service.entity.request.LoginRequest;
import htcc.gateway.service.service.authentication.JwtTokenService;
import io.jsonwebtoken.ExpiredJwtException;
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

    @Autowired
    private SecurityConfig securityConfig;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (securityConfig.bypassJWT) {
            return true;
        }

        String  uri = request.getRequestURI();
        boolean shouldNotFilter = false;

        if (!uri.startsWith(Constant.API_PATH)) {
            shouldNotFilter = true;
        }

        if (uri.startsWith(Constant.BASE_API_GATEWAY_PATH + Constant.PUBLIC_API_PATH) ||
                uri.startsWith(Constant.BASE_API_EMPLOYEE_PATH + Constant.PUBLIC_API_PATH) ||
                uri.startsWith(Constant.BASE_API_ADMIN_PATH + Constant.PUBLIC_API_PATH)) {
            shouldNotFilter = true;
        }

        if (uri.contains(Constant.SWAGGER_DOCS_PATH)) {
            shouldNotFilter = true;
        }

        return shouldNotFilter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        String jwtToken = getTokenFromHeader(request);
        try {
            if (jwtToken == null || !jwtTokenService.validateToken(jwtToken)) {
                throw new Exception(String.format("JWT Token [%s] Invalid", jwtToken));
            }

            LoginRequest loginRequest = null;
            try {
                loginRequest = jwtTokenService.getLoginInfo(jwtToken);

                if (loginRequest == null) {
                    throw new Exception("getLoginInfo from JWT return null");
                }
            } catch (Exception e) {
                throw new Exception(String.format("JWT Token [%s] Invalid, Reason: %s", jwtToken, e.getMessage()));
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = jwtTokenService.loadUserByUsername(StringUtil.toJsonString(loginRequest));
                UsernamePasswordAuthenticationToken detail =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                detail.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(detail);
            }

            chain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.warn(String.format("%s : %s", e.getMessage(), jwtToken));
            response.sendError(ReturnCodeEnum.TOKEN_EXPIRED.getValue(), e.getMessage());
        } catch (Exception e) {
            log.error(String.format("doFilterInternal Uri [%s] ex: %s", request.getRequestURI(), e.getMessage()));
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader(Constant.AUTHORIZATION);

        if (requestTokenHeader == null) {
            log.error("Header AUTHORIZATION null");
            return null;
        }

        if (!requestTokenHeader.startsWith(Constant.BEARER)) {
            log.error("requestTokenHeader not starts with BEARER: {}", requestTokenHeader);
            return null;
        }

        return requestTokenHeader.substring(Constant.BEARER.length());
    }

}
