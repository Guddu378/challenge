package com.example.demo.config;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class OpenApiConfig {

  @Bean
  public Docket generateApiDocumentation() {
    ParameterBuilder authorizationHeader = new ParameterBuilder();
    authorizationHeader.name("Authorization")
        .modelRef(new ModelRef("string"))
        .description("Bearer your_token ")
        .parameterType("header")
        .required(false)
        .build();

    java.util.List<Parameter> parametersList = new ArrayList<>();
    parametersList.add(authorizationHeader.build());

    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .globalOperationParameters(parametersList);
  }
}