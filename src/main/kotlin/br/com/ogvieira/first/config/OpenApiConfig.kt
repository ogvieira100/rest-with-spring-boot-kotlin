package br.com.ogvieira.first.config
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme

@Configuration
class OpenApiConfig {

    val securitySchemeName = "bearerAuth"

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("RESTful API with Kotlin 1.6.10 and Spring Boot 3.0.0")
                    .version("v1")
                    .description("Some description about your API.")
                    .termsOfService("https://github.com/ogvieira100")
                    .license(
                        License().name("Apache 2.0")
                            .url("https://github.com/ogvieira100")
                    )
            )   .addSecurityItem(
                SecurityRequirement().addList(securitySchemeName)
            )
            .components(
                io.swagger.v3.oas.models.Components()
                    .addSecuritySchemes(
                        securitySchemeName,
                        SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
    }
}