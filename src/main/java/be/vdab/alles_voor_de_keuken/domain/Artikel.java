package be.vdab.alles_voor_de_keuken.domain;


import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "artikels")
public class Artikel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    private BigDecimal aankoopPrijs;
    private BigDecimal verkoopPrijs;

    protected Artikel(){

    }

    public Artikel(String naam, BigDecimal aankoopPrijs, BigDecimal verkoopPrijs) {
        this.naam = naam;
        this.aankoopPrijs = aankoopPrijs;
        this.verkoopPrijs = verkoopPrijs;
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

    public void verhoogVerkoopPrijs (BigDecimal bedrag) {
        if (bedrag.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException();
        }
      verkoopPrijs = verkoopPrijs.add(bedrag).setScale(2, RoundingMode.HALF_UP);
    }

}
