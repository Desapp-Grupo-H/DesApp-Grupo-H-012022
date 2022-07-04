package ar.edu.unq.desapp.grupoh.backenddesappapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(new ArrayList<>(Collections.singleton(bearerToken())))
                .securityContexts(new ArrayList<>(Collections.singleton(securityContext())))
                .select()
                .apis(RequestHandlerSelectors.basePackage("ar.edu.unq.desapp.grupoh.backenddesappapi"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                ;
    }

    private ApiKey bearerToken() {
        return new ApiKey("JWT", "Authorization", "header");
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList<SecurityReference>(
                Collections.singleton(
                        new SecurityReference("JWT", authorizationScopes))
        );
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