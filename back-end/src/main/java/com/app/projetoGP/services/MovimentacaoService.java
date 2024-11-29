package com.app.projetoGP.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.projetoGP.model.Movimentacao;
import com.app.projetoGP.repository.MovimentacaoRepository;
import java.util.List;

/**
 * Serviço para manipulação de movimentações, incluindo operações de consulta, adição e remoção.
 */
@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    /**
     * Retorna todas as movimentações registradas no sistema.
     * 
     * @return uma lista de objetos {@link Movimentacao}.
     */
    public List<Movimentacao> consultarMovimentacoes() {
        return movimentacaoRepository.findAll();
    }

    /**
     * Salva uma nova movimentação no banco de dados.
     * 
     * @param movimentacao objeto que contém os dados da movimentação.
     * @return o objeto {@link Movimentacao} salvo.
     */
    public Movimentacao saveMovimentacao(Movimentacao movimentacao) {
        return movimentacaoRepository.save(movimentacao);
    }

    /**
     * Deleta uma movimentação pelo ID.
     * 
     * @param id o ID da movimentação a ser deletada.
     */
    public void deleteMovimentacao(Long id) {
        movimentacaoRepository.deleteById(id);
    }

    /**
     * Limpa todas as movimentações do banco de dados.
     */
    public void limparMovimentacoes() {
        movimentacaoRepository.deleteAll();
    }
}
