package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Adres;
import be.vdab.luigi.domain.Persoon;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;

//@RestController
@Controller
@RequestMapping
public class IndexController {

    private String boodschap() {
        int uur = LocalTime.now().getHour();
        if (uur < 12) {
            return "morgen";
        }
        if (uur < 18) {
            return "middag";
        }
        return "avond";
    }
   @GetMapping
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index", "boodschap", boodschap());
        modelAndView.addObject("zaakvoerder", new Persoon("Luigi", "Peperone",7, true, LocalDate.of(1966,1,31),new Adres("Grote Markt", "3", 9700, "Oudenaarde")));
        return modelAndView;


   }
       /*int uur = LocalTime.now().getHour();
       if (uur <12){
           return new ModelAndView("index", "boodschap", "morgen");
       }
       if(uur < 18){
           return new ModelAndView("index", "boodschap", "middag");
       }
       return new ModelAndView("index", "boodschap", "avond");



    /*public String index() {
         ====================
       StringBuffer buffer =
                new StringBuffer("<!doctype html><html><title>Hallo</title><body>");
        int uur = LocalTime.now().getHour();
        if (uur < 12) {
            buffer.append("Goede morgen");
        } else if (uur < 18) {
            buffer.append("Goede middag");
        } else {
            buffer.append("Goede avond");
        }
        buffer.append("</body></html>");
        return buffer.toString();
        =======================
       return "index";
        =======================  */






}
