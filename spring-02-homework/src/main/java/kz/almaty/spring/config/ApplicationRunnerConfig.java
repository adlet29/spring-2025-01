package kz.almaty.spring.config;

import kz.almaty.spring.service.ApplicationRunner;
import kz.almaty.spring.service.IOService;
import kz.almaty.spring.service.MenuCommandsProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationRunnerConfig {
    @Bean
    public ApplicationRunner runner (IOService ioService, MenuCommandsProcessor menuCommandsProcessor) {
        return new ApplicationRunner(ioService, menuCommandsProcessor);
    }
}
