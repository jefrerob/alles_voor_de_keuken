package be.vdab.alles_voor_de_keuken.repositories;

import be.vdab.alles_voor_de_keuken.domain.Artikel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ArtikelRepository {
    Optional<Artikel> findById(long id);
    void create(Artikel artikel);
    List<Artikel> findByNaam (String woord);
    int verhoogAlleVerkoopprijzenMet (BigDecimal percentage);
}
