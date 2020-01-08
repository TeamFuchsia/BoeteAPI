package nl.fuchsia.repository.historie;

import nl.fuchsia.model.Feit;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ListFeitRepository {
    /*
     * List is de tijdelijke vervanger van de database.
     */
    private List<Feit> feitList = new ArrayList<>();

    /*
     * eersteNieuweFeitnummer is tijdelijke counter voor het feitnummer, deze sleutel gaat later door de database worden bijgehouden.
     */
    private int eersteNieuweFeitnummer = 1;

    /**
     * AddFeit haalt het nieuwe feitnummer op, daarna plust hij de counter voor het feitnummer op.
     * Als laatste voegt de methode @param feit toe aan de database(list)
     */
    public void addFeit(Feit feit) {
        feit.setFeitNr(eersteNieuweFeitnummer);
        eersteNieuweFeitnummer += 1;
        feitList.add(feit);
    }

    /**
     * Methode haalt de feiten op.
     */
    public List<Feit> getfeiten() {
        return Collections.unmodifiableList(feitList);
    }
}
