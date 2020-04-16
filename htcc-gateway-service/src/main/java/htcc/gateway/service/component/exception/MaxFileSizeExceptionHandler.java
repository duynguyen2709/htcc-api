package htcc.gateway.service.component.exception;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
@Log4j2
public class MaxFileSizeExceptionHandler {

    /**
     * Description: Handle exception occurring when user upload too large file
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public void handleMaxUploadException(MaxUploadSizeExceededException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.warn("!!! MaxFileSizeException: " + e.getMessage());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(StringUtil.toJsonString(new BaseResponse<>(ReturnCodeEnum.MAXIMUM_FILE_SIZE_EXCEED)));
    }
}
