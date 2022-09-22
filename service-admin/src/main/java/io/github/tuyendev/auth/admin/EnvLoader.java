package io.github.tuyendev.auth.admin;

import io.github.tuyendev.auth.common.CommonConstants;

import java.util.Objects;

public class EnvLoader {

    public static void load() {
        defaultAppSettings();
        defaultAdminSettings();
    }

    private static void defaultAppSettings() {

        final String hostname = Objects.requireNonNullElse(System.getenv("APP_DEFAULT_HOSTNAME"), CommonConstants.APP_HOSTNAME);
        System.setProperty("app.default.hostname", hostname);

        final String port = Objects.requireNonNullElse(System.getenv("APP_DEFAULT_PORT"), CommonConstants.APP_PORT);
        System.setProperty("app.default.port", port);
        System.setProperty("server.port", port);

        final String useSSL = Objects.requireNonNullElse(System.getenv("APP_DEFAULT_USESSL"), "false");
        System.setProperty("app.default.url", Objects.equals(useSSL, "true") ? "http://" : "https://" + hostname + ":" + port);

    }

    private static void defaultAdminSettings() {

        final String username = Objects.requireNonNullElse(System.getenv("APP_INITIAL_ADMIN_USERNAME"), "");
        System.setProperty("app.initial.admin.username", username);

        final String password = Objects.requireNonNullElse(System.getenv("APP_INITIAL_ADMIN_PASSWORD"), "");
        System.setProperty("app.initial.admin.password", password);

        final String email = Objects.requireNonNullElse(System.getenv("APP_INITIAL_ADMIN_EMAIL"), "");
        System.setProperty("app.initial.admin.email", email);
    }
}
