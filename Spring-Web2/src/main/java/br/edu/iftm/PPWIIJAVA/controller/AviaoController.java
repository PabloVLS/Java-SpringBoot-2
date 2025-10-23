package br.edu.iftm.PPWIIJAVA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.iftm.PPWIIJAVA.modelo.Aviao;
import br.edu.iftm.PPWIIJAVA.service.AviaoService;
import jakarta.validation.Valid;


@Controller
public class AviaoController {
    
    @Autowired
    private AviaoService aviaoService;

    @GetMapping("/aviao")
    public String getMethodName(Model model){
        model.addAttribute("aviaoList", aviaoService.getAllAvioes());
        return "aviao/index";
    }

    @GetMapping("/aviao/create")
    public String create(Model model) {
        model.addAttribute("aviao", new Aviao());
        return "aviao/form";
    }

    @PostMapping("/aviao/save")
    public String save(@ModelAttribute @Valid Aviao aviao, BindingResult result, Model model) {

        System.out.println(aviao);
        if (result.hasErrors()) {
            model.addAttribute("aviao", aviao);
            return "aviao/form";
        }

        aviaoService.saveAviao(aviao);
        return "redirect:/aviao";
    }


    @GetMapping("/aviao/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Aviao aviao = aviaoService.getAviaoById(id);
        model.addAttribute("aviao", aviao);
        return "aviao/form";
    }

    @GetMapping("/aviao/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.aviaoService.deleteAviaoById(id);
        return "redirect:/aviao";
    }

}
