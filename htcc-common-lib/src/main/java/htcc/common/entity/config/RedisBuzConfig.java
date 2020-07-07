package htcc.common.entity.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class RedisBuzConfig {

    public String orderIdFormat = "ORDER-ID";
    public String orderFormat = "ORDER-%s"; // orderId

    public String employeeInfoFormat = "EMPLOYEE-INFO-%s-%s"; // clientId-companyId-username

    public String resetPasswordFormat = "RESET-PASSWORD-%s"; //token
    public String tokenFormat = "TOKEN-%s-%s-%s"; // clientId-companyId-username
    public String blacklistTokenFormat = "BLACKLIST-TOKEN-%s-%s-%s"; // clientId-companyId-username
    public String checkinFormat = "CHECKIN-%s-%s-%s"; // companyId-username-date
    public String checkoutFormat = "CHECKOUT-%s-%s-%s"; // companyId-username-date

    public String lastCheckInFormat = "LAST-CHECKIN-%s-%s-%s"; // companyId-username-yyyyMMdd
    public String qrCodeCheckInFormat = "QR-CHECKIN-%s"; // qrCodeId

    // for special case when blocking:
    // user A was blocked by company B
    // System admin block all account of company B
    // when admin unblock company B, user A was also unblocked.
    // => save which user was blocked before company is blocked
    public String statusBlockUserFormat = "STATUS-BLOCK-%s"; // companyId : save list user

    public String notificationFormat = "NOTIFICATION-%s-%s-%s"; // clientId-companyId-username

    @Value("${security.jwt.expireSecond:86400}")
    public long jwtTTL;
}
