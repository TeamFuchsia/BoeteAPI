package nl.fuchsia.repository;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.fuchsia.configuration.TestDatabaseConfig;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)
@Transactional(propagation = Propagation.REQUIRES_NEW)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatusRepositoryTest {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Autowired
    private StatusRepository statusRepository;

    @BeforeEach
    public void setup() {
        entityManager.persist(new Status(1, "Open"));
    }

    @Test
    void getStatusById() {
        assertThat(statusRepository.getStatusById(1).getOmschrijving()).isEqualTo("Open");
    }
}