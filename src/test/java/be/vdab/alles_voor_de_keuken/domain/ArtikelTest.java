package be.vdab.alles_voor_de_keuken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.*;



class ArtikelTest {

    private Artikel artikel;
    private final static BigDecimal AANKOOPPRIJS = BigDecimal.valueOf(100);
    private final static BigDecimal VERKOOPPRIJS = BigDecimal.valueOf(200);

    @BeforeEach
    void beforeEach(){
        artikel = new Artikel("test",AANKOOPPRIJS,VERKOOPPRIJS);
    }

    @Test
    void verhoogVerkoopPrijs () {
        artikel.verhoogVerkoopPrijs(BigDecimal.TEN);
        assertThat(artikel.getVerkoopPrijs()).isEqualByComparingTo(BigDecimal.valueOf(210));
    }

    @Test
    void verhoogVerkoopPrijsMetNullGooitNullpointerException() {
        assertThatNullPointerException().isThrownBy(() -> artikel.verhoogVerkoopPrijs(null));
    }

    @Test
    void verhoogVerkoopPrijsMetVerhoging0Mislukt() {
        assertThatIllegalArgumentException().isThrownBy(()-> artikel.verhoogVerkoopPrijs(BigDecimal.ZERO));
    }

    @Test
    void verhoogVerkoopPrijsMetVerhogingNegatiefMislukt() {
        assertThatIllegalArgumentException().isThrownBy(()-> artikel.verhoogVerkoopPrijs(BigDecimal.valueOf(-1)));
    }

}