package com.portfolio.account;

import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class PortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// Set the matching strategy to STRICT
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		// Define a condition to skip properties that are not present in the destination type
		Condition<?, ?> skipUnmapped = new Condition<Object, Object>() {
			@Override
			public boolean applies(MappingContext<Object, Object> context) {
				return context.getMapping() != null;
			}
		};

		// Set the property condition to skip unmapped properties
		modelMapper.getConfiguration().setPropertyCondition(skipUnmapped);

		// Add a custom property condition to ignore nulls
		modelMapper.getConfiguration().setPropertyCondition(context ->
				context.getSource() != null // Only map if source is not null
		);

		return modelMapper;
	}
}
