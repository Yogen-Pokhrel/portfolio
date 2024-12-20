package com.portfolio.core.config;

import com.portfolio.core.helpers.FileUploaderService;
import com.portfolio.core.helpers.LocalFileUploaderServiceImpl;
import com.portfolio.core.helpers.S3FileUploaderServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

public class FileServiceConfig {
    @Bean
    @ConditionalOnProperty(name = "uploadServiceType", havingValue = "s3")
    public FileUploaderService s3FileUploaderService() {
        return new S3FileUploaderServiceImpl();
    }

    @Bean
    @ConditionalOnProperty(name = "uploadServiceType", havingValue = "local")
    public FileUploaderService localFileUploaderService() {
        return new LocalFileUploaderServiceImpl();
    }
}
