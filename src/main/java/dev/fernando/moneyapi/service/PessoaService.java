package dev.fernando.moneyapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.fernando.moneyapi.exception.NotFoundException;
import dev.fernando.moneyapi.model.Pessoa;
import dev.fernando.moneyapi.repository.PessoaRepository;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    public PessoaService(final PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }
    public List<Pessoa> findAll() {
        return this.pessoaRepository.findAll();
    }
    public Pessoa findById(Long pessoaId) {
        return this.pessoaRepository.findById(pessoaId).orElseThrow(() -> new NotFoundException("Pessoa de id = %d não encontrada!".formatted(pessoaId)));
    }
    public Pessoa save(Pessoa pessoa) {
        return this.pessoaRepository.save(pessoa);
    }
    public void deleteById(Long id) {
        var pessoa = this.findById(id);
        this.pessoaRepository.delete(pessoa);
    }
}
