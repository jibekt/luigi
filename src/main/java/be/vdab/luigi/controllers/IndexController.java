package be.vdab.luigi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalTime;

//@RestController
@Controller
@RequestMapping
public class IndexController {
   @GetMapping
    public ModelAndView index(){
       int uur = LocalTime.now().getHour();
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

}
