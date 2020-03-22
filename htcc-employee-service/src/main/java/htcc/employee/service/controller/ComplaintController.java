package htcc.employee.service.controller;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.employee.service.entity.complaint.ComplaintRequest;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "API phản hồi/ khiếu nại",
     description = "API để phản hồi/ khiếu nại của nhân viên")
@RestController
@Log4j2
public class ComplaintController {


    @ApiOperation(value = "Gửi khiếu nại", response = BaseResponse.class)
    @PostMapping(value = "/complaint",
                 consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse complain(@ApiParam(value = "[multipart/form-data] Thông tin phản hồi/ khiếu nại", required = true)
                                     @RequestParam(name = "receiverType") int receiverType,
                                    @RequestParam(name = "isAnonymous") int isAnonymous,
                                    @RequestParam(name = "companyId") String companyId,
                                    @RequestParam(name = "username") String username,
                                    @RequestParam(name = "clientTime") long clientTime,
                                    @RequestParam(name = "category") String category,
                                    @RequestParam(name = "title") String title,
                                    @RequestParam(name = "content") String content,
                                    @RequestParam(name = "images") MultipartFile[] images) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        ComplaintRequest request = null;
        try {
            request = new ComplaintRequest(receiverType,
                    isAnonymous, companyId, username, clientTime, category, title, content, images);
        } catch (Exception e){
            log.error(String.format("complain [%s] ex", StringUtil.toJsonString(request)), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }


}
