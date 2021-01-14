package be.vdab.alles_voor_de_keuken.domain;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "artikelgroepen")
public class ArtikelGroep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @OneToMany(mappedBy = "artikelgroep")
    @OrderBy("naam")
    private Set<Artikel> artikels;

    protected ArtikelGroep() {}

    public ArtikelGroep(String naam) {
        this.naam = naam;
        this.artikels = new LinkedHashSet<>();
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Set<Artikel> getArtikels() {
        return Collections.unmodifiableSet(artikels);
    }

    public boolean add(Artikel artikel) {
        var toegevoegd = artikels.add(artikel);
        var oudeArtikelGroep = artikel.getArtikelGroep();
        if (oudeArtikelGroep != null && oudeArtikelGroep != this) {
            oudeArtikelGroep.artikels.remove(artikel);
        }
        if (oudeArtikelGroep != this) {
            artikel.setArtikelGroep(this);
        }
        return toegevoegd;
    }

}
