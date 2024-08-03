package org.lion.medicapi.config;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

@Component
public class ApplicationStartListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String port = event.getApplicationContext().getEnvironment().getProperty("server.port", "8080");
        System.out.println();
        System.out.println("URL: http://localhost:" + port);
        System.out.println("URL: http://52.78.188.110:" + port);
        System.out.println();
    }
}
