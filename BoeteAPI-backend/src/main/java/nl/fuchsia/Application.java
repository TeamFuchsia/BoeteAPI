package nl.fuchsia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// Als parameter aan de JVM meegeven: -javaagent:D:/Work/.m2/repository/org/springframework/spring-instrument/5.2.3.RELEASE/spring-instrument-5.2.3.RELEASE.jar
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
