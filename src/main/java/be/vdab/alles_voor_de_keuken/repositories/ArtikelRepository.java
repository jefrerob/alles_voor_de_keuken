package be.vdab.alles_voor_de_keuken.repositories;

import be.vdab.alles_voor_de_keuken.domain.Artikel;

import java.util.Optional;

public interface ArtikelRepository {
    Optional<Artikel> findById(long id);
}
