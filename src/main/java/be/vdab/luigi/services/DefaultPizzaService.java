package be.vdab.luigi.services;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.repositories.JdbcPizzaRepository;
import be.vdab.luigi.repositories.PizzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class DefaultPizzaService implements PizzaService {
    private final JdbcPizzaRepository jdbcPizzaRepository;
    DefaultPizzaService(JdbcPizzaRepository jdbcPizzaRepository) {
        this.jdbcPizzaRepository = jdbcPizzaRepository;
    }
    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public long create(Pizza pizza) {
        return jdbcPizzaRepository.create(pizza);
    }
    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void update(Pizza pizza) {
        jdbcPizzaRepository.update(pizza);
    }
    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void delete(long id) {
        jdbcPizzaRepository.delete(id);
    }
    @Override
    public List<Pizza> findAll() {
        return jdbcPizzaRepository.findAll();
    }
    @Override
    public Optional<Pizza> findById(long id) {
        return jdbcPizzaRepository.findById(id);
    }
    @Override
    public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
        return jdbcPizzaRepository.findByPrijsBetween(van, tot);
    }
    @Override
    public long findAantalPizzas() {
        return jdbcPizzaRepository.findAantalPizzas();
    }
    @Override
    public List<BigDecimal> findUniekePrijzen() {
        return jdbcPizzaRepository.findUniekePrijzen();
    }
    @Override
    public List<Pizza> findByPrijs(BigDecimal prijs) {
        return jdbcPizzaRepository.findByPrijs(prijs);
    }
}
