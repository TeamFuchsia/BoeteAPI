package nl.fuchsia.repository;

import nl.fuchsia.model.Feit;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FeitRepository {

    private List<Feit> feitList = new ArrayList<>();

    private long eersteNieuweFeitnummer = 1;

    public void addFeit(Feit feit) {
        feitList.add(feit);
        eersteNieuweFeitnummer +=1;
    }

    public List<Feit> getFeiten() {

        return feitList;
    }

    public Long getNieuwFeitnummer() {
        return eersteNieuweFeitnummer;
    }
}
