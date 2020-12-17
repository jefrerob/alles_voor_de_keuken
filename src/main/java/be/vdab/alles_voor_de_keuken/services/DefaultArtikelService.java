package be.vdab.alles_voor_de_keuken.services;


import be.vdab.alles_voor_de_keuken.exceptions.ArtikelNietGevondenException;
import be.vdab.alles_voor_de_keuken.repositories.ArtikelRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DefaultArtikelService implements ArtikelService {
    private final ArtikelRepository artikelRepository;

    public DefaultArtikelService(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

    @Override
    public void verhoogVerkoopPrijs(long id, BigDecimal waarde){
        artikelRepository.findById(id)
                .orElseThrow(()-> new ArtikelNietGevondenException())
                .verhoogVerkoopPrijs(waarde);
    }


}