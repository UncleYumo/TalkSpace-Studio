package cn.uncleyumo.talkspacestudio.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author uncle_yumo
 */

//@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenApi() {
        return new OpenAPI()
                .openapi("3.0.0")
                .info(new Info()
                        .title("言境工坊 - Talkspace Studio API")
                        .description("Talkspace Studio API 文档")
                        .version("v1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("External Resources")
                        .url("http://localhost:3000"))
                .externalDocs(new ExternalDocumentation()
                        .description("Minio Console")
                        .url("http://localhost:40753"));
    }

}