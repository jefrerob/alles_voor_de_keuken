package be.vdab.alles_voor_de_keuken.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Sql("/insertArtikel.sql")
@Import(JpaArtikelRepository.class)
class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private  final JpaArtikelRepository repository;

    public JpaArtikelRepositoryTest(JpaArtikelRepository repository) {
        this.repository = repository;
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

}