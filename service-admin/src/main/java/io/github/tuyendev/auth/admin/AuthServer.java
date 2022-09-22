package io.github.tuyendev.auth.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServer {

    public static void main(String[] args) {
        EnvLoader.load();
        SpringApplication.run(AuthServer.class, args);
    }
//
//	@EventListener(ApplicationReadyEvent.class)
//	public void onReady(){
//		UserInfoService infoService = AppContextUtils.getBean(UserInfoService.class);
//		var a = infoService.findActiveUserById(1L);
//		System.out.println("DEBUG");
//	}
}
