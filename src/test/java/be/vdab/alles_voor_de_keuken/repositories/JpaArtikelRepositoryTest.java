package be.vdab.alles_voor_de_keuken.repositories;

import be.vdab.alles_voor_de_keuken.domain.Artikel;
import be.vdab.alles_voor_de_keuken.domain.FoodArtikel;
import be.vdab.alles_voor_de_keuken.domain.Korting;
import be.vdab.alles_voor_de_keuken.domain.NonFoodArtikel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Sql("/insertArtikel.sql")
@Import(JpaArtikelRepository.class)
class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final JpaArtikelRepository repository;
    private final static String ARTIKELS = "artikels";

    public JpaArtikelRepositoryTest(JpaArtikelRepository repository) {
        this.repository = repository;
    }


    private long idVanTestFoodArtikel() {
        return super.jdbcTemplate.queryForObject("select id from artikels where naam = 'testfood'", long.class);
    }

    private long idVanTestNonFoodArtikel() {
        return super.jdbcTemplate.queryForObject("select id from artikels where naam = 'testnonfood'", long.class);
    }



    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void findFoodArtikelById() {
        var artikel = repository.findById(idVanTestFoodArtikel()).get(); assertThat(artikel).isInstanceOf(FoodArtikel.class); assertThat(artikel.getNaam()).isEqualTo("testfood");
    }
    @Test
    void findNonFoodArtikelById() {
        var artikel = repository.findById(idVanTestNonFoodArtikel()).get(); assertThat(artikel).isInstanceOf(NonFoodArtikel.class); assertThat(artikel.getNaam()).isEqualTo("testnonfood");
    }
    @Test
    void findOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }
    @Test
    void createFoodArtikel() {
        var artikel = new FoodArtikel("testfood2",BigDecimal.ONE,BigDecimal.TEN,7);
        repository.create(artikel);
        assertThat(super.countRowsInTableWhere(ARTIKELS,
                "id=" + artikel.getId())).isOne();
    }
    @Test
    void createNonFoodArtikel() {
        var artikel =
                new NonFoodArtikel("testnonfood2", BigDecimal.ONE, BigDecimal.TEN, 30);
        repository.create(artikel);
        assertThat(super.countRowsInTableWhere(ARTIKELS,
                "id=" + artikel.getId())).isOne();
    }

    @Test
    void findByNaam() {
        assertThat(repository.findByNaam("test"))
                .hasSize(super.jdbcTemplate.queryForObject(
                        "select count(*) from artikels where naam like '%test%'",Integer.class))
                .extracting(artikel -> artikel.getNaam().toLowerCase())
                .allSatisfy(naam -> assertThat(naam).contains("test"))
                .isSorted();
    }

    @Test
    void verhoogAlleVerkoopPrijzen() {
        assertThat(repository.verhoogAlleVerkoopprijzenMet(BigDecimal.TEN))
                .isEqualTo(super.countRowsInTable("artikels"));
        assertThat(super.jdbcTemplate.queryForObject(
                "select verkoopprijs from artikels where id=?", BigDecimal.class,
                idVanTestFoodArtikel())).isEqualByComparingTo("132");
    }

    @Test
    void kortingenLezen() {
        assertThat(repository.findById(idVanTestFoodArtikel()).get().getKortingen())
                .containsOnly(new Korting(1, BigDecimal.ONE));
    }

}