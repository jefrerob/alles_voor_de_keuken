package be.vdab.alles_voor_de_keuken.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "artikels")
public class Artikel {

    @Id
    private long id;
    private String naam;
    private BigDecimal aankoopPrijs;
    private BigDecimal verkoopPrijs;

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
}
