package br.edu.utfpr.caixaeletronico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.edu.utfpr.caixaeletronico.model.service.AccountService;

@SpringBootApplication
public class CaixaEletronicoSpringApplication {

	@Autowired
	private AccountService accountService;
	
	public static void main(String[] args) {
		SpringApplication.run(CaixaEletronicoSpringApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            accountService.init();
        };
    }

}
