package htcc.gateway.service.controller;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.companyuser.CompanyUserModel;
import htcc.common.util.StringUtil;
import htcc.gateway.service.entity.jpa.company.CompanyUser;
import htcc.gateway.service.service.jpa.CompanyUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/internal")
@ApiIgnore
public class InternalCompanyUserController {

    @Autowired
    private CompanyUserService service;

    @Autowired
    private KafkaProducerService kafka;


    @PostMapping("/companyusers")
    public BaseResponse createUser(@RequestBody @Valid CompanyUserModel model) {
        BaseResponse<CompanyUserModel> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            CompanyUser oldUser = service.findById(new CompanyUser.Key(model.companyId, model.username));
            if (oldUser != null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_ALREADY_EXISTED);
                response.setReturnMessage(String.format("User %s đã tồn tại", model.getUsername()));
                return response;
            }

            CompanyUser user = new CompanyUser(model);

            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setRole(0);
            user.setStatus(1);

            user = service.create(user);
            response.setData(user.fromEntity());

        } catch (Exception e) {
            log.error("[createUser] {} ex", StringUtil.toJsonString(model), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }




    @PostMapping("/companyusers/delete")
    public BaseResponse deleteUser(@RequestBody CompanyUserModel model) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            service.delete(new CompanyUser.Key(model.getCompanyId(), model.getUsername()));
        } catch (Exception e) {
            log.error("[deleteUser] {} ex", StringUtil.toJsonString(model), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }




    @GetMapping("/companyusers/{companyId}")
    public BaseResponse getCompanyUsers(@PathVariable String companyId) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            List<CompanyUserModel> list = service.findByCompanyId(companyId).stream()
                              .map(CompanyUser::fromEntity)
                              .collect(Collectors.toList());

            response.setData(list);

        } catch (Exception e) {
            log.error("[getCompanyUsers] [{}] ex", companyId, e);
            response = new BaseResponse(e);
        }

        return response;
    }




    @PostMapping("/companyusers/update")
    public BaseResponse updateCompanyUserInfo(@RequestBody @Valid CompanyUserModel user) {
        BaseResponse<CompanyUserModel> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        boolean needUpdate = false;
        try {
            CompanyUser oldUser = service.findById(new CompanyUser.Key(user.companyId, user.username));
            if (oldUser == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND,
                        String.format("Không tìm thấy user %s", user.username));
                return response;
            }
            CompanyUser newUser = new CompanyUser(user);

            newUser.setPassword(oldUser.getPassword());
            newUser.setRole(oldUser.getRole());
            newUser.setStatus(oldUser.getStatus());

            newUser = service.update(newUser);

            response.setData(newUser.fromEntity());

            if (!oldUser.getEmail().equals(newUser.getEmail()) ||
                    !oldUser.getPhoneNumber().equals(newUser.getPhoneNumber())){
                needUpdate = true;
            }

        } catch (Exception e) {
            log.error("[updateCompanyUserInfo] [{}] ex", StringUtil.toJsonString(user), e);
            response = new BaseResponse<>(e);
        } finally {
            if (needUpdate) {
                kafka.sendMessage(kafka.getBuzConfig().eventUpdateCompanyUser.topicName, user);
            }
        }

        return response;
    }




    @PostMapping("/companyusers/status")
    public BaseResponse updateCompanyUserStatus(@RequestBody CompanyUserModel user) {
        BaseResponse<CompanyUserModel> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            CompanyUser oldUser = service.findById(new CompanyUser.Key(user.companyId, user.username));
            if (oldUser == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND,
                        String.format("Không tìm thấy user %s", user.username));
                return response;
            }

            if (oldUser.getStatus() == user.getStatus()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID,
                        String.format("Trạng thái mới [%s] không được giống trạng thái cũ", user.getStatus()));
                return response;
            }

            oldUser.setStatus(user.getStatus());

            oldUser = service.update(oldUser);

            response.setData(oldUser.fromEntity());

        } catch (Exception e) {
            log.error("[updateCompanyUserStatus] [{}] ex", StringUtil.toJsonString(user), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }
}
