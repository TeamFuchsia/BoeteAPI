package nl.fuchsia.repository;

import nl.fuchsia.configuration.TestDatabaseConfig;
import nl.fuchsia.model.Feit;
import nl.fuchsia.model.Zaak;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
		assertThat(feitRepository.getFeitById(feit.getFeitNr()).getFeitcode()).isEqualTo("VBF-003");
	}

	@Test
	public void testGetFeit() {
		assertThat(feitRepository.getFeiten()).hasSize(1);
	}

	@Test()
	public void testGetPFeitById() {
		Feit feitId = feitRepository.addFeit(new Feit("VBF-002", "Test", 500));

		assertThat(feitRepository.getFeiten()).hasSize(2);
		assertThat(feitRepository.getFeitById(feitId.getFeitNr()).getFeitcode()).isEqualTo("VBF-002");
	}
}