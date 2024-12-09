package com.portfolio.auth.configuration;

import com.portfolio.core.helpers.FileUploaderService;
import com.portfolio.core.helpers.ListMapper;
import com.portfolio.core.helpers.LocalFileUploaderServiceImpl;
import com.portfolio.core.helpers.S3FileUploaderServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

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

    @Bean
    public ListMapper<?, ?> listMapper() {
        return new ListMapper<>(new ModelMapper());
    }
}
