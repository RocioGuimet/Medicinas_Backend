package com.medicina.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import javax.sql.DataSource;

@Configuration
public class RenderDataSourceConfig {
    @Bean
    @Profile("render")
    public DataSource dataSource(DataSourceProperties properties) {
        String originalUrl = properties.getUrl();

        System.out.println("=== CONFIGURACIÓN RENDER DETECTADA ===");
        System.out.println("URL original de Render: " + maskPassword(originalUrl));

        if (originalUrl != null && originalUrl.startsWith("postgresql://")) {
            try {
                DataSource convertedDataSource = convertRenderUrl(originalUrl, properties);
                System.out.println("URL convertida exitosamente para JDBC");
                return convertedDataSource;
            } catch (Exception e) {
                System.err.println("Error convirtiendo URL de Render: " + e.getMessage());
                System.err.println("Usando configuración por defecto...");
            }
        }

        // Si no es URL de Render o hay error, usa configuración normal
        return properties.initializeDataSourceBuilder().build();
    }

    private DataSource convertRenderUrl(String renderUrl, DataSourceProperties properties) {
        String urlWithoutProtocol = renderUrl.substring("postgresql://".length());

        // Separar credenciales y host
        String[] userHostParts = urlWithoutProtocol.split("@");
        if (userHostParts.length != 2) {
            throw new IllegalArgumentException("Formato de URL de Render inválido");
        }

        String credentials = userHostParts[0]; // user:password
        String hostAndDatabase = userHostParts[1]; // host:port/dbname

        // Separar usuario y contraseña
        String[] credParts = credentials.split(":");
        if (credParts.length != 2) {
            throw new IllegalArgumentException("Formato de credenciales inválido");
        }

        String username = credParts[0];
        String password = credParts[1];

        // Construir URL JDBC correcta
        String jdbcUrl = "jdbc:postgresql://" + hostAndDatabase;

        // Añadir parámetros SSL para Render
        if (!hostAndDatabase.contains("?")) {
            jdbcUrl += "?ssl=true&sslmode=require";
        } else {
            jdbcUrl += "&ssl=true&sslmode=require";
        }

        System.out.println("URL JDBC convertida: " + jdbcUrl.replace(password, "***"));
        System.out.println("Usuario: " + username);

        // Crear DataSource con la URL convertida
        return org.springframework.boot.jdbc.DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    // Ocultar password
    private String maskPassword(String url) {
        if (url == null || !url.contains(":")) return url;

        try {
            // postgresql://user:password@host...
            int passStart = url.indexOf(":", url.indexOf("://") + 3) + 1;
            int passEnd = url.indexOf("@", passStart);

            if (passStart > 0 && passEnd > passStart) {
                String masked = url.substring(0, passStart) +
                        "***" +
                        url.substring(passEnd);
                return masked;
            }
        } catch (Exception e) {
        }

        return url;
    }
}