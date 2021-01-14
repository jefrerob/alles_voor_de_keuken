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
    private ArtikelGroep groep1, groep2;

    @BeforeEach
    void beforeEach(){
        groep1 = new ArtikelGroep("test");
        groep2 = new ArtikelGroep("test2");
        artikel = new FoodArtikel("test", BigDecimal.ONE, BigDecimal.TEN, groep1, 1);
    }

    @Test
    void verhoogVerkoopPrijs () {
        artikel.verhoogVerkoopPrijs(BigDecimal.TEN);
        assertThat(artikel.getVerkoopPrijs()).isEqualByComparingTo(BigDecimal.valueOf(20));
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
    @Test
    void groep1EnArtikelZijnVerbonden() {
        assertThat(groep1.getArtikels()).containsOnly(artikel);
        assertThat(artikel.getArtikelGroep()).isEqualTo(groep1);
    }

    @Test
    void artikelVerhuistNaarGroep2() {
        artikel.setArtikelGroep(groep2);
        assertThat(groep1.getArtikels()).doesNotContain(artikel);
        assertThat(groep2.getArtikels()).containsOnly(artikel);
    }
    @Test
    void nullAlsArtikelGroepInDeConstructorMislukt() {
        assertThatNullPointerException().isThrownBy(
                () -> new FoodArtikel("test", BigDecimal.ONE, BigDecimal.ONE,  null, 1));
    }
    @Test
    void nullAlsArtikelGroepInDeSetterMislukt() {
        assertThatNullPointerException().isThrownBy(
                ()->artikel.setArtikelGroep(null));
    }

}