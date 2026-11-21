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
				.title("SOPKATHON Android 2조 API")
				.description("부꾸부꾸 서비스 API입니다.\n\n"
					+ "모든 API 응답은 status, code, message, data 공통 구조를 사용합니다.\n\n"
					+ "이미지는 먼저 이미지 업로드 API를 통해 Presigned URL을 발급받아 S3에 PUT 업로드한 뒤, 응답의 imageKey를 프로필·옷·게시물 생성 요청에 넣어서 테스트하시면 됩니다!.")
				.version("v1.0.0"));
	}
}
