package com.portfolio.cms.configuration;

import com.portfolio.core.config.EnableDefaults;
import com.portfolio.core.helpers.FileUploaderService;
import com.portfolio.core.helpers.ListMapper;
import com.portfolio.core.helpers.LocalFileUploaderServiceImpl;
import com.portfolio.core.helpers.S3FileUploaderServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDefaults
public class CoreConfig {
}
