package br.gov.caixa.repository;

import br.gov.caixa.model.HistoricoInvestimento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class HistoricoInvestimentoRepository implements PanacheRepository<HistoricoInvestimento> {
    public List<HistoricoInvestimento> listaHistoricoInvestimentos(Long clienteId){
        return find("clienteId", clienteId).stream().toList();
    }
}
