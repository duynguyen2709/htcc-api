package htcc.admin.service.service;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import htcc.admin.service.config.GoogleDriveBuzConfig;
import htcc.common.constant.Constant;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@Service
@Log4j2
public class GoogleDriveService {

    @Autowired
    private Drive googleDrive;

    @Autowired
    private GoogleDriveBuzConfig buzConfig;

    public String uploadAvatar(MultipartFile multipartFile, String fileName) {
        java.io.File tempFile = null;
        try {
            tempFile = java.io.File.createTempFile("temp", null);
            tempFile.deleteOnExit();

            multipartFile.transferTo(tempFile);

            File newGGDriveFile = new File()
                    .setParents(Collections.singletonList(buzConfig.buz.avatarImageFolder))
                    .setName(fileName);

            FileContent mediaContent = new FileContent(multipartFile.getContentType(), tempFile);

            File uploadedFile = googleDrive.files()
                    .create(newGGDriveFile, mediaContent)
                    .setFields("id")
                    .execute();

            log.info(String.format("[uploadAvatar] Uploaded Filename [%s] - Content Type [%s] - Id [%s] Succeed",
                    fileName,
                    multipartFile.getContentType(),
                    uploadedFile.getId()));

            return Constant.GOOGLE_DRIVE_IMAGE_FORMAT + uploadedFile.getId();
        } catch (Exception e) {
            log.error("uploadAvatar ex", e);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
        // error case
        return null;
    }

    public String uploadNotiIcon(MultipartFile multipartFile, String fileName) {
        java.io.File tempFile = null;
        try {
            tempFile = java.io.File.createTempFile("temp", null);
            tempFile.deleteOnExit();

            multipartFile.transferTo(tempFile);

            File newGGDriveFile = new File()
                    .setParents(Collections.singletonList(buzConfig.buz.iconFolder))
                    .setName(fileName);

            FileContent mediaContent = new FileContent(multipartFile.getContentType(), tempFile);

            File uploadedFile = googleDrive.files()
                    .create(newGGDriveFile, mediaContent)
                    .setFields("id")
                    .execute();

            log.info(String.format("[uploadNotiIcon] Uploaded Filename [%s] - Content Type [%s] - Id [%s] Succeed",
                    fileName,
                    multipartFile.getContentType(),
                    uploadedFile.getId()));

            return Constant.GOOGLE_DRIVE_IMAGE_FORMAT + uploadedFile.getId();
        } catch (Exception e) {
            log.error("uploadNotiIcon ex", e);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
        // error case
        return StringUtil.EMPTY;
    }

    @Async("asyncExecutor")
    public void deleteFile(String fileId) {
        try {
            googleDrive.files().delete(fileId).execute();
        } catch (Exception e) {
            log.error("[deleteFile] {} ex", fileId, e);
        }
    }
}
