package br.edu.iftm.PPWIIJAVA.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.iftm.PPWIIJAVA.modelo.Aviao;
import br.edu.iftm.PPWIIJAVA.repository.AviaoForcaAereaRepository;
import br.edu.iftm.PPWIIJAVA.service.AviaoService;

@Service
public class AviaoServiceImpl implements AviaoService{
    
    @Autowired
    private AviaoForcaAereaRepository aviaofaRepository;

    @Override
    public List <Aviao> getAllAvioes(){
        return aviaofaRepository.findAll();
    }

    @Override
    public void saveAviao(Aviao aviao){
        this.aviaofaRepository.save(aviao);
    }

    @Override
    public Aviao getAviaoById(long id) {
        Optional < Aviao > optional = aviaofaRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    @Override
    public void deleteAviaoById(long id) {
        this.aviaofaRepository.deleteById(id);
    }
}
