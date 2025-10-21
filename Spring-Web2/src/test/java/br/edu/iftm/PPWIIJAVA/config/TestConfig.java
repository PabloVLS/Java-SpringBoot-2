package br.edu.iftm.PPWIIJAVA.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import br.edu.iftm.PPWIIJAVA.service.AviaoService;

@TestConfiguration
public class TestConfig {

    @Bean
    public AviaoService aviaoService() {
        return Mockito.mock(AviaoService.class);
    }

} 
