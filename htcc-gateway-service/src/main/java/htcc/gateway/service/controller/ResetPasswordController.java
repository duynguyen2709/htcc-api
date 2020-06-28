package htcc.gateway.service.controller;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.base.BaseUser;
import htcc.common.util.StringUtil;
import htcc.gateway.service.entity.request.ResetPasswordRequest;
import htcc.gateway.service.service.authentication.AuthenticationService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Reset Password API (Không cần gửi token trong header)",
     description = "API public để reset mật khẩu")
@RestController
@Log4j2
@RequestMapping("/public")
public class ResetPasswordController {

    @Autowired
    private AuthenticationService authenService;

    @Autowired
    private KafkaProducerService kafka;

    @PostMapping("/resetpassword")
    public BaseResponse sendRequestResetPassword(@RequestBody ResetPasswordRequest request) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Một email đã được gửi đến hộp thư của bạn. Vui lòng thực hiện theo hướng dẫn để đặt lại mật khẩu");
        BaseUser user = null;
        try {
            user = authenService.getUser(request);
            if (user == null) {
                response = new BaseResponse(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy người dùng " + request.getUsername());
                return response;
            }
        } catch (Exception e) {
            log.error("[sendRequestResetPassword] {} ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                if (user != null) {
                    user.setPassword("");
                }
            }
        }
        return response;
    }
}
