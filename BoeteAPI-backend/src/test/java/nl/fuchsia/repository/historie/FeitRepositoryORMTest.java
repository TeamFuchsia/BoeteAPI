//package nl.fuchsia.repository.historie;
//
//import nl.fuchsia.Application;
//import nl.fuchsia.model.Feit;
//import nl.fuchsia.repository.historie.FeitRepositoryORM;
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
//public class FeitRepositoryORMTest {
//
//    @PersistenceContext(unitName = "entityManagerFactory")
//    private EntityManager entityManager;
//
//    @Autowired
//    private FeitRepositoryORM feitRepositoryORM;
//
//    @BeforeEach
//    public void setup() {
//
//        entityManager.createQuery("Delete FROM Feit").executeUpdate();
//
//        Feit feit = new Feit("VBF-001", "Test", 500);
//        feitRepositoryORM.addFeit(feit);
//    }
//
//    @Test
//    public void testAddFeit() {
//        feitRepositoryORM.addFeit(new Feit("VBF-002", "Test", 500));
//
//        assertThat(feitRepositoryORM.getFeiten()).hasSize(2);
//
//        Feit feit = feitRepositoryORM.addFeit(new Feit("VBF-003", "Test", 500));
//
//        assertThat(feitRepositoryORM.getFeiten()).hasSize(3);
//        assertThat(feitRepositoryORM.getFeitById(feit.getFeitnr()).getFeitcode()).isEqualTo("VBF-003");
//    }
//
//    @Test
//    public void testGetFeit() {
//        assertThat(feitRepositoryORM.getFeiten()).hasSize(1);
//    }
//
//    @Test()
//    public void testGetPFeitById() {
//        Feit feitId = feitRepositoryORM.addFeit(new Feit("VBF-002", "Test", 500));
//
//        assertThat(feitRepositoryORM.getFeiten()).hasSize(2);
//        assertThat(feitRepositoryORM.getFeitById(feitId.getFeitnr()).getFeitcode()).isEqualTo("VBF-002");
//    }
//
//    @Test
//    public void testUpdateFeitById() {
//        Feit feit = feitRepositoryORM.addFeit(new Feit("VBF-002", "Test", 500));
//        feitRepositoryORM.getFeiten();
//
//        assertThat(feitRepositoryORM.getFeitById(feit.getFeitnr()).getBedrag()).isEqualTo(feit.getBedrag());
//
//        Feit updatedFeit = feitRepositoryORM.updateFeitById(new Feit(feit.getFeitnr(), "VBF-002", "Test", 5000));
//
//        assertThat(feitRepositoryORM.getFeiten()).hasSize(2);
//        assertThat(feitRepositoryORM.getFeitById(feit.getFeitnr()).getBedrag()).isEqualTo(updatedFeit.getBedrag());
//    }
//}
