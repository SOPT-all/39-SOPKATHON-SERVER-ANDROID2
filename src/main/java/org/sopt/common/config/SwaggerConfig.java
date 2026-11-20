package org.sopt.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	OpenAPI openAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("39-SOPKATHON-ANDRIOD-2")
				.description("39-SOPKATHON-ANDRIOD-2조 API 문서")
				.version("v1.0.0"));
	}
}
