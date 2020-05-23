package htcc.web.service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thainq
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("zalopay.app")
public class AppProperties {

	public String entranceDomain;
	public int serviceId;
	public String tokenSecretKey;
	public long tokenExpiredTime;
}
