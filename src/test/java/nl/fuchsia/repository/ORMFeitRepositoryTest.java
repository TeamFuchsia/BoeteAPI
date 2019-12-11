package nl.fuchsia.repository;

import nl.fuchsia.configuration.TestDatabaseConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)
public class ORMFeitRepositoryTest {

    @Autowired
    private ORMFeitRepository ormFeitRepository;

    @Test
    public void addFeit() {
    }

    @Test
    public void getFeiten() {
    }
}