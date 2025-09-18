package br.edu.iftm.PPWIIJAVA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model; 

import org.springframework.web.bind.annotation.PostMapping;

import br.edu.iftm.PPWIIJAVA.modelo.Aviao;
import br.edu.iftm.PPWIIJAVA.service.AviaoService;


@Controller
public class AviaoController {
    
    @Autowired
    private AviaoService aviaoService;

    @GetMapping("/aviao")
    public String getMethodName(Model model){
        model.addAttribute("aviaoList", aviaoService.getAllAvioes());
        return "/aviao/index";
    }

    @GetMapping("/aviao/create")
    public String create(Model model) {
        model.addAttribute("aviao", new Aviao());
        return "aviao/create";
    }

     @PostMapping("/aviao/save")
    public String postMethodName(@ModelAttribute("product") Aviao aviao) {
        aviaoService.saveAviao(aviao);
        return "redirect:/aviao";
    }

}
