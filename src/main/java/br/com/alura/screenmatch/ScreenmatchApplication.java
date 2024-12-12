package br.com.alura.screenmatch;

import br.com.alura.screenmatch.principal.Program;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// classe de aplicacao de linha de comando
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Program program = new Program();
		program.menuExibit();

	}
}
