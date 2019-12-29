package be.vdab.luigi.controllers;
import static org.mockito.Mockito.when;
import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.services.DefaultEuroService;
import be.vdab.luigi.services.DefaultPizzaService;
import be.vdab.luigi.services.EuroService;
import be.vdab.luigi.services.PizzaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PizzaControllerTest {
    private PizzaController controller;
    @Mock
    private EuroService euroService;
    private PizzaService pizzaService;
    @Before
    public void before() {
        when(pizzaService.findById(1))
                .thenReturn(Optional.of(new Pizza(1, "Test", BigDecimal.ONE, true)));
        controller = new PizzaController(euroService, pizzaService);
    }
    @Test
    public void pizzasGebruiktDeThymeleafPaginaPizzas() {
        assertThat(controller.pizzas().getViewName()).isEqualTo("pizzas");
    }
    @Test
    public void pizzasGeeftPizzasDoorAanDeThymeleafPagina() {
        assertThat(controller.pizzas().getModel().get("pizzas") instanceof List);
    }
    @Test
    public void pizzaGebruiktDeThymeleafPaginaPizza() {
        assertThat(controller.pizza(1).getViewName()).isEqualTo("pizza");
    }
    @Test
    public void pizzaGeeftGevondenPizzaDoorAanDeThymeleafPagina() {
        Pizza pizza = (Pizza) controller.pizza(1).getModel().get("pizza");
        assertThat(pizza.getId()).isEqualTo(1);
    }
    @Test
    public void pizzaGeeftOnbestaandePizzaNietDoorAanDeThymeleafPagina() {
        assertThat(controller.pizza(-1).getModel()).doesNotContainKeys("pizza");
    }
}
