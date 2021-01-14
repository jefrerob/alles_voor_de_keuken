package be.vdab.alles_voor_de_keuken.domain;


import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "artikels")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "soort")
public abstract class Artikel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    private BigDecimal aankoopPrijs;
    private BigDecimal verkoopPrijs;
    @ElementCollection
    @CollectionTable(name = "kortingen",
            joinColumns = @JoinColumn(name = "artikelId"))
    @OrderBy("vanafAantal")
    private Set<Korting> kortingen;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "artikelgroepid")
    private ArtikelGroep artikelGroep;

    protected Artikel(){

    }

    public Artikel(String naam, BigDecimal aankoopPrijs, BigDecimal verkoopPrijs, ArtikelGroep artikelGroep) {
        this.naam = naam;
        this.aankoopPrijs = aankoopPrijs;
        this.verkoopPrijs = verkoopPrijs;
        this.kortingen = new LinkedHashSet<>();
        setArtikelGroep(artikelGroep);
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public BigDecimal getAankoopPrijs() {
        return aankoopPrijs;
    }

    public BigDecimal getVerkoopPrijs() {
        return verkoopPrijs;
    }

    public Set<Korting> getKortingen() {
        return Collections.unmodifiableSet(kortingen);
    }

    public ArtikelGroep getArtikelGroep() {
        return artikelGroep;
    }

    public void setArtikelGroep(ArtikelGroep artikelGroep) {
        if (!artikelGroep.getArtikels().contains(this)) {
            artikelGroep.add(this);
        }
        this.artikelGroep = artikelGroep;
    }

    public void verhoogVerkoopPrijs (BigDecimal bedrag) {
        if (bedrag.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException();
        }
      verkoopPrijs = verkoopPrijs.add(bedrag).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artikel)) return false;
        Artikel artikel = (Artikel) o;
        return naam.toUpperCase().equals(artikel.naam.toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam.toUpperCase());
    }
}