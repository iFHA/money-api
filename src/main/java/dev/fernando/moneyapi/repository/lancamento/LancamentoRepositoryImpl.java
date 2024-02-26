package dev.fernando.moneyapi.repository.lancamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.util.StringUtils;

import dev.fernando.moneyapi.model.Lancamento;
import dev.fernando.moneyapi.model.Lancamento_;
import dev.fernando.moneyapi.repository.filter.LancamentoFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Lancamento> filtrar(LancamentoFilter filtro) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);

        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(filtro, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = em.createQuery(criteria);

        return query.getResultList();
    }

    private Predicate[] criarRestricoes(LancamentoFilter filtro, CriteriaBuilder builder, Root<Lancamento> root) {
        List<Predicate> predicates = new ArrayList<>();
        if(StringUtils.hasLength(filtro.descricao())) {
            predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)), "%"+filtro.descricao().toLowerCase()+"%"));
        }
        if(Objects.nonNull(filtro.dataVencimentoDe())) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filtro.dataVencimentoDe()));
        }
        if(Objects.nonNull(filtro.dataVencimentoAte())) {
            predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), filtro.dataVencimentoAte()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
    
}
