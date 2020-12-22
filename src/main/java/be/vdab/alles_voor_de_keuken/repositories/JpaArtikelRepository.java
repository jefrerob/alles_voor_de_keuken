package be.vdab.alles_voor_de_keuken.repositories;

import be.vdab.alles_voor_de_keuken.domain.Artikel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaArtikelRepository implements  ArtikelRepository{
    private  final EntityManager manager;

    public JpaArtikelRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Artikel> findById(long id) {
        return Optional.ofNullable(manager.find(Artikel.class, id));
    }

    @Override
    public void create(Artikel artikel) {
        manager.persist(artikel);
    }

    @Override
    public List<Artikel> findByNaam (String woord){
        return manager.createNamedQuery("Artikel.findByNaam", Artikel.class)
                .setParameter("woord", '%' + woord + '%').getResultList();
    }

    @Override
    public int verhoogAlleVerkoopprijzenMet(BigDecimal percentage) {
        var factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
        return manager.createNamedQuery("Artikel.verhoogAlleVerkoopprijzenMet")
                .setParameter("factor", factor)
                .executeUpdate();
    }

}