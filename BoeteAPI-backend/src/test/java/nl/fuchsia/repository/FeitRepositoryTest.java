package nl.fuchsia.repository;

import nl.fuchsia.Application;
import nl.fuchsia.model.Feit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@SpringBootTest(classes = Application.class)
public class FeitRepositoryTest {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Autowired
    private FeitRepository feitRepository;

    @BeforeEach
    public void setup() {

        entityManager.createQuery("Delete FROM Feit").executeUpdate();

        Feit feit = new Feit("VBF-001", "Test", 500);
        feitRepository.addFeit(feit);
    }

    @Test
    public void testAddFeit() {
        feitRepository.addFeit(new Feit("VBF-002", "Test", 500));

        assertThat(feitRepository.getFeiten()).hasSize(2);

        Feit feit = feitRepository.addFeit(new Feit("VBF-003", "Test", 500));

        assertThat(feitRepository.getFeiten()).hasSize(3);
        assertThat(feitRepository.getFeitById(feit.getFeitnr()).getFeitcode()).isEqualTo("VBF-003");
    }

    @Test
    public void testGetFeit() {
        assertThat(feitRepository.getFeiten()).hasSize(1);
    }

    @Test()
    public void testGetPFeitById() {
        Feit feitId = feitRepository.addFeit(new Feit("VBF-002", "Test", 500));

        assertThat(feitRepository.getFeiten()).hasSize(2);
        assertThat(feitRepository.getFeitById(feitId.getFeitnr()).getFeitcode()).isEqualTo("VBF-002");
    }

    @Test
    public void testUpdateFeitById() {
        Feit feit = feitRepository.addFeit(new Feit("VBF-002", "Test", 500));
        feitRepository.getFeiten();

        assertThat(feitRepository.getFeitById(feit.getFeitnr()).getBedrag()).isEqualTo(feit.getBedrag());

        Feit updatedFeit = feitRepository.updateFeitById(new Feit(feit.getFeitnr(), "VBF-002", "Test", 5000));

        assertThat(feitRepository.getFeiten()).hasSize(2);
        assertThat(feitRepository.getFeitById(feit.getFeitnr()).getBedrag()).isEqualTo(updatedFeit.getBedrag());
    }
}
