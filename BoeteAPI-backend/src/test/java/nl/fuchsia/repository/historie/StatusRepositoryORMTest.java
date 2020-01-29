package nl.fuchsia.repository.historie;

import nl.fuchsia.Application;
import nl.fuchsia.model.Status;
import nl.fuchsia.repository.historie.StatusRepositoryORM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@SpringBootTest(classes = Application.class)
class StatusRepositoryORMTest {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Autowired
    private StatusRepositoryORM statusRepository;

    @BeforeEach
    public void setup() {
        entityManager.persist(new Status(1, "Open"));
    }

    @Test
    void getStatusById() {
        assertThat(statusRepository.getStatusById(1).getOmschrijving()).isEqualTo("Open");
    }
}
