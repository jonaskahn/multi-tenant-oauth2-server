package io.github.tuyendev.auth.admin;

import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.context.event.EventListener;

@SpringBootApplication(nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
public class AuthServer {
    @Autowired
    ApplicationContext applicationContext;

    public static void main(String[] args) {
        EnvLoader.load();
        SpringApplication.run(AuthServer.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        StreamEx.of(applicationContext.getBeanDefinitionNames()).forEach(name -> {
            if (name.contains("tuyendev")) {
                System.out.println(name);
            }
        });
    }
}
