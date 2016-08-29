package com.kaz.lottery;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Predicates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class SimpleLotteryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleLotteryApplication.class, args);
	}

	@Bean
	public Docket swaggerSpringMvcPlugin() {
		final ApiInfo info = new ApiInfo(
				"Simple lottery REST Service",
				"This APIs set gives you ability to generate a random Ticket and perform further operations on that ticket.",
				"0.0.1",
				"",
				"Kazimierz Maciaszek<kazimierz.maciaszek@gmail.com>",
				"",
				"");
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.groupName("simple-lottery")
				.apiInfo(info)
				.select()
				.paths(Predicates.or(PathSelectors.regex("/v1.*")))
				.build();
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		om.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
		return om;
	}

}
