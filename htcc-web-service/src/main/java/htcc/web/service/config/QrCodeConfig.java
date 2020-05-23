package htcc.web.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "qrcode")
public class QrCodeConfig {
    public boolean genSig      = false;
    public boolean loadFromConfig = true;
    public String  hashKey     = "";
    public String  companyId   = "";
    public String  officeId    = "";

    public String baseUrl = "";
    public String methodName = "";
}
