package htcc.log.service.controller;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.shift.ShiftArrangementLogEntity;
import htcc.common.entity.shift.ShiftArrangementModel;
import htcc.common.entity.shift.ShiftTime;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.BaseLogDAO;
import htcc.log.service.repository.ShiftArrangementLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/internal/logs")
public class ShiftArrangementLogController {

    @Autowired
    private ShiftArrangementLogRepository repo;

    @Autowired
    private BaseLogDAO logDAO;

    @GetMapping("/shifts/{companyId}/{week}")
    public BaseResponse getListShiftArrangementLog(@PathVariable String companyId,
                                      @PathVariable int week){
        BaseResponse<List<ShiftArrangementModel>> response =
                new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<ShiftArrangementLogEntity> logEntityList = repo.getListShiftArrangementLog(companyId, week);
            if (logEntityList == null) {
                throw new Exception("ShiftArrangementLogRepository.getListShiftArrangementLog return null");
            }

            List<ShiftArrangementModel> modelList = logEntityList
                    .stream()
                    .map(ShiftArrangementModel::new)
                    .collect(Collectors.toList());

            response.setData(modelList);
        } catch (Exception e) {
            log.error(String.format("[getListShiftArrangementLog] [%s-%s] ex", companyId, week), e);
            return new BaseResponse<>(e);
        }
        return response;
    }

    @PostMapping("/shifts/delete/{arrangementId}")
    public BaseResponse deleteShiftArrangement(@PathVariable String arrangementId){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xóa lịch xếp ca thành công");
        try {
            ShiftArrangementLogEntity logEntity = repo.getOneShiftArrangementLog(arrangementId);
            if (logEntity == null) {
                return new BaseResponse(ReturnCodeEnum.LOG_NOT_FOUND);
            }

            int result = repo.deleteShiftArrangementLog(arrangementId);
            if (result != 1) {
                throw new Exception("repo.deleteShiftArrangementLog result = " + result);
            }
        } catch (Exception e) {
            log.error(String.format("[deleteShiftArrangement] [%s] ex", arrangementId), e);
            return new BaseResponse(e);
        }
        return response;
    }

    @PostMapping("/shifts/delete")
    public BaseResponse deleteShiftArrangement(@RequestBody ShiftTime request){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            int result = repo.deleteShiftArrangementLog(request);
            log.info("[repo.deleteShiftArrangementLog] delete request = {}, result = {}",StringUtil.toJsonString(request), result);
        } catch (Exception e) {
            log.error(String.format("[deleteShiftArrangement] [%s] ex", StringUtil.toJsonString(request)), e);
            return new BaseResponse(e);
        }
        return response;
    }

    @PostMapping("/shifts")
    public BaseResponse insertShiftArrangement(@RequestBody ShiftArrangementModel request){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xếp ca thành công");
        try {
            ShiftArrangementLogEntity logEntity = new ShiftArrangementLogEntity(request);

            int result = logDAO.insertLog(logEntity);
            if (result != 1) {
                throw new Exception("logDAO.insertLog result = " + result);
            }
        } catch (Exception e) {
            log.error(String.format("[insertShiftArrangement] [%s] ex", StringUtil.toJsonString(request)), e);
            return new BaseResponse(e);
        }
        return response;
    }
}
