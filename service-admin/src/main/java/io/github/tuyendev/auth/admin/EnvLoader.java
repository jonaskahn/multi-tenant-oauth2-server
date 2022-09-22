package io.github.tuyendev.auth.admin;

import io.github.tuyendev.auth.common.CommonConstants;

import java.util.Objects;

public class EnvLoader {

    public static void load() {
        defaultAppSettings();
        defaultAdminUserSettings();
    }

    private static void defaultAppSettings() {

        final String hostname = Objects.requireNonNullElse(System.getenv("APP_DEFAULT_HOSTNAME"), CommonConstants.APP_HOSTNAME);
        System.setProperty("app.default.hostname", hostname);

        final String port = Objects.requireNonNullElse(System.getenv("APP_DEFAULT_PORT"), CommonConstants.APP_PORT);
        System.setProperty("app.default.port", port);
        System.setProperty("server.port", port);

        final String useSSL = Objects.requireNonNullElse(System.getenv("APP_DEFAULT_USESSL"), "false");
        System.setProperty("app.default.url", Objects.equals(useSSL, "true") ? "http://" : "https://" + hostname + ":" + port);

        final String secretKey = Objects.requireNonNullElse(System.getenv("APP_SECRET_KEY"), "default-secret-key");
        System.setProperty("app.default.remember-me.key", secretKey);


        final String clientId = Objects.requireNonNullElse(System.getenv("APP_DEFAULT_CLIENT_ID"), "default");
        System.setProperty("app.initial.realm.client.default.id", clientId);
        final String clientSecret = Objects.requireNonNullElse(System.getenv("APP_DEFAULT_CLIENT_SECRET_KEY"), "password");
        System.setProperty("app.initial.realm.master.secret-key", clientSecret);

    }

    private static void defaultAdminUserSettings() {

        final String username = Objects.requireNonNullElse(System.getenv("APP_INITIAL_ADMIN_USERNAME"), "");
        System.setProperty("app.initial.admin.username", username);

        final String password = Objects.requireNonNullElse(System.getenv("APP_INITIAL_ADMIN_PASSWORD"), "");
        System.setProperty("app.initial.admin.password", password);

        final String email = Objects.requireNonNullElse(System.getenv("APP_INITIAL_ADMIN_EMAIL"), "");
        System.setProperty("app.initial.admin.email", email);
    }
}
