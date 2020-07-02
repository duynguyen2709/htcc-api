package htcc.gateway.service.controller;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.base.BaseUser;
import htcc.common.util.StringUtil;
import htcc.gateway.service.config.file.SecurityConfig;
import htcc.common.entity.request.ResetPasswordRequest;
import htcc.common.entity.request.ResetPasswordUpdateRequest;
import htcc.gateway.service.service.authentication.AuthenticationService;
import htcc.gateway.service.service.authentication.ResetPasswordService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Reset Password API (Không cần gửi token trong header)",
     description = "API public để reset mật khẩu")
@RestController
@Log4j2
@RequestMapping("/public")
public class ResetPasswordController {

    @Autowired
    private AuthenticationService authenService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private SecurityConfig securityConfig;

    @PostMapping("/resetpassword")
    public BaseResponse sendRequestResetPassword(@RequestBody ResetPasswordRequest request) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Một email đã được gửi đến hộp thư của bạn. Vui lòng thực hiện theo hướng dẫn để đặt lại mật khẩu");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            BaseUser user = authenService.getUser(request);
            if (user == null) {
                response = new BaseResponse(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy người dùng " + request.getUsername());
                return response;
            }
            request.setEmail(user.getEmail());
            resetPasswordService.createResetPasswordData(request);
        } catch (Exception e) {
            log.error("[sendRequestResetPassword] {} ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @PostMapping("/resetpassword/update")
    public BaseResponse resetPassword(@RequestBody ResetPasswordUpdateRequest request, @RequestParam String sig) {
        log.info(StringUtil.toJsonString(request));
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Đổi mật khẩu thành công");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            if (!validateSig(request, sig) || !validateData(request)) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                return response;
            }

            if (resetPasswordService.updatePassword(request)) {
                resetPasswordService.invalidateToken(request.getToken());
            }
        } catch (Exception e) {
            log.error("[resetPassword] {}, sig = [{}] ex", StringUtil.toJsonString(request), sig, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private boolean validateData(ResetPasswordUpdateRequest request) {
        ResetPasswordRequest cacheReq = resetPasswordService.getForgotPasswordInfo(request.getToken());
        if (cacheReq == null) {
            log.error("Cache Request is null");
            return false;
        }

        if (!cacheReq.same(request)) {
            log.error("Cache Request {} != Submit Request {}",
                    StringUtil.toJsonString(cacheReq), StringUtil.toJsonString(request));
            return false;
        }

        return true;
    }

    private boolean validateSig(ResetPasswordUpdateRequest request, String sig) throws Exception {
        if (StringUtil.isEmpty(sig)) {
            throw new Exception("clientSig is empty");
        }

        String serverSig = StringUtil.hashSHA256(String.format("%s|%s|%s|%s",
                request.getClientId(), StringUtil.valueOf(request.getCompanyId()),
                request.getUsername(), securityConfig.getHashKey()));

        if (!serverSig.equals(sig)) {
            log.error("\nserverSig [{}] != clientSig [{}]\n", serverSig, sig);
        }

        return serverSig.equals(sig);
    }
}
