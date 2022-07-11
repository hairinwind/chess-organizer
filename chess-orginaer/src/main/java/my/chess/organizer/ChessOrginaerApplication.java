package my.chess.organizer;

import lombok.extern.slf4j.Slf4j;
import my.chess.organizer.service.OrganizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ChessOrginaerApplication implements CommandLineRunner {

	@Autowired
	OrganizeService organizeService;

	public static void main(String[] args) {
		SpringApplication.run(ChessOrginaerApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("EXECUTING : command line runner");

		for (int i = 0; i < args.length; ++i) {
			log.info("args[{}]: {}", i, args[i]);
		}

		log.info("Working Directory = " + System.getProperty("user.dir"));

		organizeService.createGames(4);
	}

}
