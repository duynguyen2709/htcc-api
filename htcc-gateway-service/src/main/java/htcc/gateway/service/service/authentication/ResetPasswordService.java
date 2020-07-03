package htcc.gateway.service.service.authentication;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.component.redis.RedisService;
import htcc.common.entity.request.ResetPasswordRequest;
import htcc.common.entity.request.ResetPasswordUpdateRequest;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ResetPasswordService {

    @Autowired
    private RedisService redis;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private KafkaProducerService kafka;

    public void createResetPasswordData(ResetPasswordRequest request) throws Exception {
        String token = RandomStringUtils.randomAlphanumeric(32);
        request.setToken(token);

        redis.set(StringUtil.toJsonString(request), 30 * 60,
                redis.buzConfig.getResetPasswordFormat(), token);
        sendMail(request);
    }

    public ResetPasswordRequest getForgotPasswordInfo(String token) {
        String rawValue = StringUtil.valueOf(redis.get(redis.buzConfig.getResetPasswordFormat(), token));
        if (rawValue.isEmpty()) {
            return null;
        }

        return StringUtil.fromJsonString(rawValue, ResetPasswordRequest.class);
    }

    public void invalidateToken(String token){
        ResetPasswordRequest forgotPasswordRequest = getForgotPasswordInfo(token);
        if (forgotPasswordRequest != null) {
            redis.delete(redis.buzConfig.getResetPasswordFormat(), token);
            redis.delete(redis.buzConfig.getTokenFormat(), forgotPasswordRequest.getClientId(),
                    forgotPasswordRequest.getCompanyId(), forgotPasswordRequest.getUsername());
        }
    }

    private void sendMail(ResetPasswordRequest request){
        kafka.sendMessage(kafka.getBuzConfig().getEventResetPassword().getTopicName(), request);
    }

    public boolean updatePassword(ResetPasswordUpdateRequest forgotPasswordUpdateRequest) {
        return authenticationService.updatePassword(forgotPasswordUpdateRequest);
    }

}
