package htcc.log.service.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@Log4j2
public class FireBaseAdminSDKConfig {

    @Autowired
    private FirebaseBuzConfig config;

    @Bean
    public FirebaseApp initAdminSDK() {
        try {
            Resource resource = new ClassPathResource(config.getServiceAccountFile());
            InputStream inputStream = resource.getInputStream();

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .setDatabaseUrl(config.getDatabaseURL())
                    .build();

            FirebaseApp app = FirebaseApp.initializeApp(options);
            log.info("####### FireBaseAdminSDK init succeed ! #######");
            return app;

        } catch (Exception e){
            log.error("[initAdminSDK] ex", e);
            System.exit(3);
            return null;
        }
    }
}
