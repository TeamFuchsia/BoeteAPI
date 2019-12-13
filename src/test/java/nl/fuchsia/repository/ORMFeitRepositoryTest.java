package nl.fuchsia.repository;

import nl.fuchsia.configuration.TestDatabaseConfig;
import nl.fuchsia.model.Feit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)
@Transactional
public class ORMFeitRepositoryTest {

    @Autowired
    private ORMFeitRepository ormFeitRepository;

    @Test
    public void addGetFeit() {
        ormFeitRepository.addFeit(new Feit("VBF-001", "Test", 500));
        ormFeitRepository.addFeit(new Feit("VBF-002", "Test", 500));

        List<Feit> allefeiten = ormFeitRepository.getFeiten();
        Feit eersteFeit = allefeiten.get(0);
        Feit tweedeFeit = allefeiten.get(1);

        assertThat(allefeiten).hasSize(2);
        assertThat(eersteFeit.getFeitcode()).isEqualTo("VBF-001");
        assertThat(tweedeFeit.getFeitcode()).isEqualTo("VBF-002");

    }
}