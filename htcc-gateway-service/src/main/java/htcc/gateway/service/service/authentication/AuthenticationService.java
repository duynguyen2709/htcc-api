package htcc.gateway.service.service.authentication;

import htcc.common.constant.ClientSystemEnum;
import htcc.common.entity.base.BaseUser;
import htcc.common.util.StringUtil;
import htcc.gateway.service.config.file.SecurityConfig;
import htcc.gateway.service.entity.jpa.admin.AdminUser;
import htcc.gateway.service.entity.jpa.company.CompanyUser;
import htcc.common.entity.request.ChangePasswordRequest;
import htcc.common.entity.request.LoginRequest;
import htcc.common.entity.request.ResetPasswordRequest;
import htcc.common.entity.request.ResetPasswordUpdateRequest;
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
                    user = new BaseUser();
                    user.setUsername(securityConfig.user.name);
                    user.setPassword(password);
                    user.setStatus(1);
                    break;

                case ADMIN_WEB:
                    AdminUser admin = adminUserService.findById(req.username);
                    if (admin != null) {
                        user = new BaseUser();
                        user.setUsername(admin.getUsername());
                        user.setPassword(admin.getPassword());
                        user.setStatus(admin.getStatus());
                        user.setEmail(admin.getEmail());
                    }
                    break;

                case MANAGER_WEB:
                case MOBILE:
                    CompanyUser.Key key = new CompanyUser.Key(req.companyId, req.username);
                    CompanyUser companyUser = companyUserService.findById(key);
                    if (companyUser != null) {
                        user = new BaseUser();
                        user.setUsername(companyUser.getUsername());
                        user.setPassword(companyUser.getPassword());
                        user.setStatus(companyUser.getStatus());
                        user.setEmail(companyUser.getEmail());
                    }
                    break;
            }
        } catch (Exception e){
            log.error("getUser ex", e);
        }
        return user;
    }

    public BaseUser getUser(ChangePasswordRequest req) {
        LoginRequest entity = new LoginRequest(req.clientId, req.companyId, req.username, req.oldPassword, "");
        return getUser(entity);
    }

    public BaseUser getUser(ResetPasswordRequest req) {
        LoginRequest entity = new LoginRequest(req.clientId, req.companyId, req.username, "", "");
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

    public boolean updatePassword(ResetPasswordUpdateRequest req) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(
                req.getClientId(), req.getCompanyId(), req.getUsername(), null, req.getPassword());
        return updatePassword(changePasswordRequest);
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
