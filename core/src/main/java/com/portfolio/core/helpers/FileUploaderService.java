package com.portfolio.core.helpers;

import com.portfolio.core.exception.FileUploadFailedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;

public interface FileUploaderService {
    File upload(MultipartFile document, String path) throws FileUploadFailedException;

    default String getFileName(MultipartFile document, String path) {
        return path + LocalDateTime.now() + "-" + document.getOriginalFilename().replace(" ", "_");
    }
}
