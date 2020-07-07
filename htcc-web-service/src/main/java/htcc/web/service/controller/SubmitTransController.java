package htcc.web.service.controller;

import htcc.web.service.entity.BaseResponse;
import htcc.web.service.entity.SubmitTransRequest;
import htcc.web.service.enums.ReturnCodeEnum;
import htcc.web.service.service.SubmitTransService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class SubmitTransController {

    @Autowired
    private SubmitTransService submitTransService;

    @PostMapping("/api/submittrans")
    public BaseResponse submitTrans(@RequestBody SubmitTransRequest request) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            return submitTransService.submitTrans(request);
        } catch (Exception e) {
            log.error("[submitTrans] {} ex", request.toString(), e);
            return new BaseResponse(ReturnCodeEnum.EXCEPTION);
        }
    }
}
