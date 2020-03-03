package htcc.gateway.service.component.authentication;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import htcc.gateway.service.constant.ReturnCodeEnum;
import htcc.gateway.service.entity.base.BaseResponse;
import htcc.gateway.service.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		response.getWriter().print(StringUtil.toJsonString(new BaseResponse(ReturnCodeEnum.UNAUTHORIZE)));
	}
}
