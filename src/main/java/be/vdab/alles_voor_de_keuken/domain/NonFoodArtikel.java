package be.vdab.alles_voor_de_keuken.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("NF")
public class NonFoodArtikel extends Artikel {
    private int garantie;

    protected NonFoodArtikel() {
    }

    public NonFoodArtikel(String naam, BigDecimal aankoopPrijs, BigDecimal verkoopPrijs, ArtikelGroep artikelGroep, int garantie) {
        super(naam, aankoopPrijs, verkoopPrijs, artikelGroep);
        this.garantie = garantie;
    }

    public int getGarantie() {
        return garantie;
    }
}
