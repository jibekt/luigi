package be.vdab.luigi.controllers;

import  be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.forms.VanTotPrijsForm;
import be.vdab.luigi.services.DefaultEuroService;
import be.vdab.luigi.services.DefaultPizzaService;
import be.vdab.luigi.services.EuroService;
import be.vdab.luigi.services.PizzaService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;


@Controller
@RequestMapping("pizzas")
public class PizzaController {
    private final EuroService euroService;
    private final PizzaService pizzaService;

    public PizzaController(EuroService euroService, PizzaService pizzaService){
        this.euroService=euroService;
        this.pizzaService=pizzaService;
    }

    /*private final Pizza[] pizzas = {
            new Pizza(1, "Prosciutto", BigDecimal.valueOf(4), true),
            new Pizza(2, "Margherita", BigDecimal.valueOf(5), false),
            new Pizza(3, "Calzone", BigDecimal.valueOf(4), false)};*/

    @GetMapping
    public ModelAndView pizzas (){
       return new ModelAndView("pizzas", "pizzas", pizzaService.findAll());
   }

   @GetMapping("{id}")
    public ModelAndView pizza (@PathVariable long id){
       ModelAndView modelAndView = new ModelAndView("pizza");
       pizzaService.findById(id).ifPresent(pizza -> {
                   modelAndView.addObject("pizza", pizza);
                   modelAndView.addObject("inDollar", euroService.naarDollar(pizza.getPrijs()));
               });
       return modelAndView;
   }

  /* private List<BigDecimal> uniekePrijzen(){
       return Arrays.stream(pizzas).map(pizza->pizza.getPrijs())
               .distinct().sorted().collect(Collectors.toList());
   }*/

   @GetMapping("prijzen")
    public ModelAndView prijzen(){
        return new ModelAndView("prijzen", "prijzen", pizzaService.findUniekePrijzen());
   }

   /*private List<Pizza> pizzasMetPrijs(BigDecimal prijs){
       return Arrays.stream(pizzas)
               .filter(pizza -> pizza.getPrijs().compareTo(prijs)==0)
               .collect(Collectors.toList());
   }*/

   @GetMapping("prijzen/{prijs}")
    public ModelAndView pizzasMetEenPrijs(@PathVariable BigDecimal prijs){
       return new ModelAndView("prijzen", "pizzas", pizzaService.findByPrijs(prijs))
               .addObject("prijzen", pizzaService.findUniekePrijzen());

   }
    @GetMapping("vantotprijs/form")
    public ModelAndView vanTotPrijsForm() {
        return new ModelAndView("vantotprijs")
                .addObject(new VanTotPrijsForm(null,null));
    }
    @GetMapping("vantotprijs")
    public ModelAndView vanTotPrijs(@Valid VanTotPrijsForm form, Errors errors) {
        ModelAndView modelAndView = new ModelAndView("vantotprijs");
        if (errors.hasErrors()) {
            return modelAndView;
        }
        return modelAndView.addObject("pizzas",
                pizzaService.findByPrijsBetween(form.getVan(), form.getTot()));
    }
    @GetMapping("toevoegen/form")
    public ModelAndView toevoegenForm() {
        return new ModelAndView("toevoegen").addObject(new Pizza(0,"",null,false));
    }
    @PostMapping
    public String toevoegen(@Valid Pizza pizza, Errors errors, RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            return "toevoegen";
        }
        long id=pizzaService.create(pizza);
        redirect.addAttribute("toegevoegd", id);
        return "redirect:/pizzas";
    }
}
