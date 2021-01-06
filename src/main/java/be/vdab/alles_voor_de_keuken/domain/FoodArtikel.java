package be.vdab.alles_voor_de_keuken.domain;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("F")
public class FoodArtikel extends Artikel {
    private int houdbaarheid;

    protected FoodArtikel() {
    }

    public FoodArtikel(String naam, BigDecimal aankoopPrijs, BigDecimal verkoopPrijs, int houdbaarheid) {
        super(naam, aankoopPrijs, verkoopPrijs);
        this.houdbaarheid = houdbaarheid;
    }

    public int getHoudbaarheid() {
        return houdbaarheid;
    }
}
