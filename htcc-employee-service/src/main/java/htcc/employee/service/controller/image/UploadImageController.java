package htcc.employee.service.controller.image;

import htcc.common.constant.ServiceSystemEnum;
import htcc.common.entity.log.RequestLogEntity;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.googledrive.GoogleDriveService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
public class UploadImageController {

    @Autowired
    private GoogleDriveService driveService;

    @PostMapping(value = "/public/uploads", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<String> uploadImages(@RequestParam(name = "images", required = false) MultipartFile[] images,
                                     HttpServletRequest httpServletRequest) throws Exception {
        long requestTime = System.currentTimeMillis();
        List<String> response = new ArrayList<>();
        try {
            for (MultipartFile image : images) {
                String fileName = String.format("%s", System.currentTimeMillis());
                String link     = driveService.uploadAvatar(image, fileName);
                if (link == null) {
                    throw new Exception("driveService.uploadAvatar return null");
                }
                response.add(link);
            }
        } finally {
            printRequestLogEntity(httpServletRequest, response, requestTime);
        }
        return response;
    }

    @PostMapping(value = "/public/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadImage(@RequestParam(name = "image", required = false) MultipartFile image,
                              HttpServletRequest httpServletRequest) {
        long requestTime = System.currentTimeMillis();
        String response = "";
        try {
            String fileName  = String.format("%s", System.currentTimeMillis());
            String link = driveService.uploadAvatar(image, fileName);
            if (link == null) {
                throw new Exception("driveService.uploadAvatar return null");
            }
            response = link;
        } catch (Exception e) {
            log.error("[uploadImage] ex", e);
            response = "";
        } finally {
            printRequestLogEntity(httpServletRequest, response, requestTime);
        }
        return response;
    }

    private void printRequestLogEntity(HttpServletRequest request, Object response, long requestTime){
        RequestLogEntity logEnt = new RequestLogEntity();
        try {
            logEnt.setRequestTime(requestTime);
            logEnt.setResponseTime(System.currentTimeMillis());
            logEnt.setMethod(request.getMethod());
            logEnt.setPath(request.getRequestURI());
            logEnt.setParams(request.getParameterMap());
            logEnt.setRequest(UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).build().toUriString());
            logEnt.setServiceId(ServiceSystemEnum.getServiceFromUri(logEnt.path));
            logEnt.setIp(request);
            logEnt.body = "";
            logEnt.setResponse(StringUtil.toJsonString(response));
        } catch (Exception e) {
            log.warn("printRequestLogEntity ex {}", e.getMessage(), e);
        } finally {
            log.info(String.format("%s , Total Time : %sms\n",
                    StringUtil.toJsonString(logEnt), (logEnt.responseTime - logEnt.requestTime)));
        }
    }
}
