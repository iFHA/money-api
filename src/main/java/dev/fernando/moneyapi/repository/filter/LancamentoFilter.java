package dev.fernando.moneyapi.repository.filter;

import java.time.LocalDate;

public record LancamentoFilter (
    String descricao,
    LocalDate dataVencimentoDe,
    LocalDate dataVencimentoAte
    ) {
}
