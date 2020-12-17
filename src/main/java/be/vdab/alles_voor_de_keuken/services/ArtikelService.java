package be.vdab.alles_voor_de_keuken.services;

import java.math.BigDecimal;

public interface ArtikelService {
    void verhoogVerkoopPrijs(long id, BigDecimal waarde);

}
