package com.sprintpay.projetsig.configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@OpenAPIDefinition(info = @Info(title = "SIG-MINPOSTEL-DOC API",version = "v1"))
@SecurityScheme(
        name = OpenApi30Config.SECURITY_CONFIG_NAME,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
@CrossOrigin
public class OpenApi30Config {
    public static final String SECURITY_CONFIG_NAME = "JWT Token";
}