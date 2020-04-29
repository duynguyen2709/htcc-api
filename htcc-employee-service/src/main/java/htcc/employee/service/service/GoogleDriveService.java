package htcc.employee.service.service;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import htcc.common.constant.Constant;
import htcc.employee.service.config.GoogleDriveBuzConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class GoogleDriveService {

    @Autowired
    private Drive googleDrive;

    @Autowired
    private GoogleDriveBuzConfig buzConfig;

    @Async("asyncExecutor")
    public CompletableFuture<String> uploadComplaintImage(MultipartFile multipartFile, String fileName) {
        java.io.File tempFile = null;
        long start = System.currentTimeMillis();
        try {
            tempFile = java.io.File.createTempFile("temp", null);
            tempFile.deleteOnExit();

            multipartFile.transferTo(tempFile);

            File newGGDriveFile = new File()
                    .setParents(Collections.singletonList(buzConfig.buz.complaintImageFolder))
                    .setName(fileName);

            FileContent mediaContent = new FileContent(multipartFile.getContentType(), tempFile);

            File uploadedFile = googleDrive.files()
                    .create(newGGDriveFile, mediaContent)
                    .setFields("id")
                    .execute();

            log.info(String.format("[uploadComplaintImage] Uploaded Filename [%s] - Content Type [%s]" +
                            "- File Size [%s] - Total Time [%sms] - Id [%s] Succeed",
                    fileName,
                    multipartFile.getContentType(),
                    multipartFile.getSize(),
                    (System.currentTimeMillis() - start),
                    uploadedFile.getId()));

            return CompletableFuture.completedFuture(Constant.GOOGLE_DRIVE_IMAGE_FORMAT + uploadedFile.getId());
        } catch (Exception e) {
            log.error("uploadComplaintImage ex", e);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
        // error case
        return CompletableFuture.completedFuture(buzConfig.buz.errorImage);
    }

    public String uploadAvatar(MultipartFile multipartFile, String fileName) {
        java.io.File tempFile = null;
        long start = System.currentTimeMillis();
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

            log.info(String.format("[uploadAvatar] Uploaded Filename [%s] - Content Type [%s]" +
                            " - File Size [%s] - Total Time [%sms] -  Id [%s] Succeed",
                    fileName,
                    multipartFile.getContentType(),
                    multipartFile.getSize(),
                    (System.currentTimeMillis() - start),
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




    public String uploadCheckInImage(MultipartFile multipartFile, String fileName) {
        java.io.File tempFile = null;
        long start = System.currentTimeMillis();
        try {
            tempFile = java.io.File.createTempFile("temp", null);
            tempFile.deleteOnExit();

            multipartFile.transferTo(tempFile);

            File newGGDriveFile = new File()
                    .setParents(Collections.singletonList(buzConfig.buz.checkInImageFolder))
                    .setName(fileName);

            FileContent mediaContent = new FileContent(multipartFile.getContentType(), tempFile);

            File uploadedFile = googleDrive.files()
                    .create(newGGDriveFile, mediaContent)
                    .setFields("id")
                    .execute();

            log.info(String.format("[uploadCheckInImage] Uploaded Filename [%s] " +
                            "- File Size [%s] - Total Time [%sms] -  Id [%s] Succeed",
                    fileName,
                    multipartFile.getSize(),
                    (System.currentTimeMillis() - start),
                    uploadedFile.getId()));

            return Constant.GOOGLE_DRIVE_IMAGE_FORMAT + uploadedFile.getId();
        } catch (Exception e) {
            log.error("uploadCheckInImage ex", e);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
        // error case
        return buzConfig.getBuz().getErrorImage();
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
