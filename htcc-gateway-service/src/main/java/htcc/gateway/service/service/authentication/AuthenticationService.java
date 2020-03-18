package htcc.gateway.service.service.authentication;

import htcc.common.constant.ClientSystemEnum;
import htcc.common.util.StringUtil;
import htcc.gateway.service.config.file.SecurityConfig;
import htcc.gateway.service.entity.jpa.admin.AdminUser;
import htcc.gateway.service.entity.jpa.BaseUser;
import htcc.gateway.service.entity.jpa.company.CompanyUser;
import htcc.gateway.service.entity.request.ChangePasswordRequest;
import htcc.gateway.service.entity.request.LoginRequest;
import htcc.gateway.service.service.jpa.AdminUserService;
import htcc.gateway.service.service.jpa.CompanyUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public BaseUser getUser(LoginRequest req) {
        BaseUser user = null;

        try {
            ClientSystemEnum system = ClientSystemEnum.fromInt(req.clientId);

            switch (system) {
                case EUREKA_DASHBOARD:
                    String password = passwordEncoder.encode(securityConfig.user.password);
                    user = new BaseUser(securityConfig.user.name, password, 1);
                    break;

                case ADMIN_WEB:
                    AdminUser admin = adminUserService.findById(req.username);
                    if (admin != null) {
                        user = new BaseUser(admin.username, admin.password, admin.status);
                    }
                    break;

                case MANAGER_WEB:
                case MOBILE:
                    CompanyUser.Key key = new CompanyUser.Key(req.companyId, req.username);
                    CompanyUser companyUser = companyUserService.findById(key);
                    if (companyUser != null) {
                        user = new BaseUser(companyUser.username, companyUser.password, companyUser.status);
                    }
                    break;
            }
        } catch (Exception e){
            log.error("getUser ex", e);
        }
        return user;
    }

    public BaseUser getUser(ChangePasswordRequest req) {
        LoginRequest entity = new LoginRequest(req.clientId, req.companyId, req.username, req.oldPassword);
        return getUser(entity);
    }

    public boolean updatePassword(ChangePasswordRequest req) {
        try {
            ClientSystemEnum system = ClientSystemEnum.fromInt(req.clientId);

            switch (system) {
                case ADMIN_WEB:
                    AdminUser admin = adminUserService.findById(req.username);
                    admin.password = passwordEncoder.encode(req.newPassword);
                    if (adminUserService.update(admin) != null) {
                        return true;
                    }
                    break;

                case MANAGER_WEB:
                case MOBILE:
                    CompanyUser.Key key = new CompanyUser.Key(req.companyId, req.username);
                    CompanyUser companyUser = companyUserService.findById(key);
                    companyUser.password = passwordEncoder.encode(req.newPassword);
                    if (companyUserService.update(companyUser) != null) {
                        return true;
                    }
                    break;
                default:
                    log.warn("updatePassword {} client not found", StringUtil.toJsonString(req));
                    break;
            }
        } catch (Exception e){
            log.error("updatePassword ex", e);
        }
        return false;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
