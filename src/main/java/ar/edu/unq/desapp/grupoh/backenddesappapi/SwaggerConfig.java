package ar.edu.unq.desapp.grupoh.backenddesappapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
/*
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ar.edu.unq.desapp.grupoh.backenddesappapi.webservice"))
                .paths(PathSelectors.any())
                .build()
                ;
    }

}*/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ar.edu.unq.desapp.grupoh.backenddesappapi"))
                //.apis(RequestHandlerSelectors.basePackage("ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                ;
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "CriptoP2P",
                "CriptoP2P api",
                "1.0",
                "",
                new Contact("Desapp-Grupo-H", "https://github.com/Desapp-Grupo-H/DesApp-Grupo-H-012022", ""),
                "No License",
                "",
                Collections.emptyList()
        );
    }
}