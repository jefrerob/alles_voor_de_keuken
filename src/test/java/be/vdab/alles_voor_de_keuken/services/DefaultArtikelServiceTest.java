package be.vdab.alles_voor_de_keuken.services;

import be.vdab.alles_voor_de_keuken.domain.Artikel;
import be.vdab.alles_voor_de_keuken.domain.ArtikelGroep;
import be.vdab.alles_voor_de_keuken.domain.FoodArtikel;
import be.vdab.alles_voor_de_keuken.exceptions.ArtikelNietGevondenException;
import be.vdab.alles_voor_de_keuken.repositories.ArtikelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DefaultArtikelServiceTest {
    private DefaultArtikelService artikelService;
    @Mock
    private ArtikelRepository artikelRepository;
    private Artikel artikel;
    private ArtikelGroep artikelGroep;

    @BeforeEach
    void beforeEach () {
        artikelGroep = new ArtikelGroep("test");
        artikel = new FoodArtikel("test",BigDecimal.ONE,BigDecimal.TEN, artikelGroep,1);
        artikelService = new DefaultArtikelService(artikelRepository);
    }

    @Test
    void verhoogVerkoopPrijs() {
        when(artikelRepository.findById(1)).thenReturn(Optional.of(artikel));
        artikelService.verhoogVerkoopPrijs(1, BigDecimal.TEN);
        assertThat(artikel.getVerkoopPrijs()).isEqualByComparingTo(BigDecimal.valueOf(20));
        verify(artikelRepository).findById(1);
    }

    @Test
    void verhoogVerkoopPrijsMetOnbestaandeArtikelIdGooitException() {
        assertThatExceptionOfType(ArtikelNietGevondenException.class).isThrownBy(() ->artikelService.verhoogVerkoopPrijs(-1, BigDecimal.TEN));
        verify(artikelRepository).findById(-1);
    }


}