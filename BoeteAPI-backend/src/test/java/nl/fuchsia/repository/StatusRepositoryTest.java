//package nl.fuchsia.repository;
//
//import nl.fuchsia.Application;
//import nl.fuchsia.model.Status;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Transactional(propagation = Propagation.REQUIRES_NEW)
//@SpringBootTest(classes = Application.class)
//class StatusRepositoryTest {
//
//    @PersistenceContext(unitName = "entityManagerFactory")
//    private EntityManager entityManager;
//
//    @Autowired
//    private StatusRepository statusRepository;
//
//    @BeforeEach
//    public void setup() {
//        entityManager.persist(new Status(1, "Open"));
//    }
//
//    @Test
//    void getStatusById() {
//        assertThat(statusRepository.getStatusById(1).getOmschrijving()).isEqualTo("Open");
//    }
//}
