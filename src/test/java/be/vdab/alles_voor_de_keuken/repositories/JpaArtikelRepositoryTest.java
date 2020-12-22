package be.vdab.alles_voor_de_keuken.repositories;

import be.vdab.alles_voor_de_keuken.domain.Artikel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Sql("/insertArtikel.sql")
@Import(JpaArtikelRepository.class)
class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final JpaArtikelRepository repository;
    private Artikel artikel;
    private final static String ARTIKELS = "artikels";

    public JpaArtikelRepositoryTest(JpaArtikelRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    private void beforeEach(){
        artikel = new Artikel("test", BigDecimal.ONE,BigDecimal.TEN);
    }

    private long idVanTestArtikel1() {
        return super.jdbcTemplate.queryForObject("select id from artikels where naam = 'testArtikel1'", long.class);
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestArtikel1()).get().getNaam()).isEqualTo("testArtikel1");
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void create() {
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(ARTIKELS, "id =" + artikel.getId())).isOne();
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
    void verhoogAlleVerkoopprijzenMet(){
        assertThat(repository.verhoogAlleVerkoopprijzenMet(BigDecimal.TEN))
                .isEqualTo(super.countRowsInTable(ARTIKELS));
        assertThat(super.jdbcTemplate.queryForObject(
                "select verkoopprijs from artikels where id=?",BigDecimal.class, idVanTestArtikel1()))
                .isEqualByComparingTo("110");
    }

}