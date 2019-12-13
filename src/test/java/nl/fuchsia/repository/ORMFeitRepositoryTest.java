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
    public void testAddGetFeit() {
        ormFeitRepository.addFeit(new Feit("VBF-001", "Test", 500));

        assertThat(ormFeitRepository.getFeiten()).hasSize(1);

        ormFeitRepository.addFeit(new Feit("VBF-002", "Test", 500));
        ormFeitRepository.addFeit(new Feit("VBF-003", "Test", 500));
        ormFeitRepository.addFeit(new Feit("VBF-004", "Test", 500));

        List<Feit> allefeiten = ormFeitRepository.getFeiten();
        Feit eersteFeit = allefeiten.get(0);
        Feit tweedeFeit = allefeiten.get(1);
        Feit derdeFeit = allefeiten.get(2);
        Feit vierdeFeit = allefeiten.get(3);

        assertThat(allefeiten).hasSize(4);
        assertThat(eersteFeit.getFeitcode()).isEqualTo("VBF-001");
        assertThat(tweedeFeit.getFeitcode()).isNotEqualTo("VBF-001");
        assertThat(derdeFeit.getOmschrijving()).isEqualTo("Test");
        assertThat(vierdeFeit.getBedrag()).isEqualTo(500);
    }
}