package dev.fernando.moneyapi.repository.lancamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<Lancamento> filtrar(LancamentoFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);

        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(filtro, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = em.createQuery(criteria);

        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filtro));
    }

    private Long total(LancamentoFilter filtro) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<Lancamento> root = criteria.from(Lancamento.class);
        Predicate[] predicates = criarRestricoes(filtro, builder, root);
        criteria.where(predicates);
        criteria.select(builder.count(root));
        
        return em.createQuery(criteria).getSingleResult();
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
        
        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
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
