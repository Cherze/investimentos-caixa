package br.gov.caixa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import br.gov.caixa.model.Cliente;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
}
