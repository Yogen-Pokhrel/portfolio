package com.portfolio.core.helpers;

import com.portfolio.core.exception.FileUploadFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@ConditionalOnProperty(
        name = "uploadServiceType",
        havingValue = "local")
public class LocalFileUploaderServiceImpl implements FileUploaderService {

    @Value("${fileUploader.path}")
    private String uploadPath;

    @Override
    public File upload(MultipartFile document, String uploadPath) throws FileUploadFailedException {
        File file = new File(getFileName(document, uploadPath));
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        try {
            document.transferTo(file);
            return file;
        } catch (Exception e) {
            throw new FileUploadFailedException(e.getMessage());
        }
    }
}