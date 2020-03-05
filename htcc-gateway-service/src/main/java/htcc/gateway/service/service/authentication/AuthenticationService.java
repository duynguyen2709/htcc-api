package htcc.gateway.service.service.authentication;

import constant.ClientSystemEnum;
import htcc.gateway.service.config.file.SecurityConfig;
import htcc.gateway.service.entity.jpa.AdminUser;
import htcc.gateway.service.entity.jpa.BaseUser;
import htcc.gateway.service.entity.jpa.CompanyUser;
import htcc.gateway.service.entity.request.LoginRequest;
import htcc.gateway.service.service.jpa.AdminUserService;
import htcc.gateway.service.service.jpa.CompanyUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuthenticationService {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private CompanyUserService companyUserService;

    public BaseUser getUser(LoginRequest req) {
        BaseUser user = null;

        try {
            ClientSystemEnum system = ClientSystemEnum.fromInt(req.clientId);

            switch (system) {
                case EUREKA_DASHBOARD:
                    user = new BaseUser(securityConfig.user.name, securityConfig.user.password);
                    break;

                case ADMIN_WEB:
                    AdminUser admin = adminUserService.findById(req.username);
                    if (admin != null) {
                        user = new BaseUser(admin.username, admin.password);
                    }
                    break;

                case MANAGER_WEB:
                case MOBILE:
                    CompanyUser.Key key = new CompanyUser.Key(req.companyId, req.username);
                    CompanyUser companyUser = companyUserService.findById(key);
                    if (companyUser != null) {
                        user = new BaseUser(companyUser.username, companyUser.password);
                    }
                    break;
            }
        } catch (Exception e){
            log.error("getUser ex", e);
        }
        return user;
    }
}
