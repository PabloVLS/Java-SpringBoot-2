package br.edu.iftm.PPWIIJAVA.service;

import java.util.List;

import br.edu.iftm.PPWIIJAVA.modelo.Aviao;

public interface AviaoService {
    List <Aviao> getAllAvioes();
    void saveAviao(Aviao aviao);
    Aviao getAviaoById(long id);
    void deleteAviaoById(long id);

} 


