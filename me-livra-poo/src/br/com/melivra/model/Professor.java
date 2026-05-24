package br.com.melivra.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um professor universitário que pode ser avaliado pelos estudantes.
 * Agrega todas as avaliações recebidas e calcula a nota média dinamicamente.
 */
public class Professor {

    // Contador estático para IDs sequenciais
    private static int proximoId = 1;

    private int idProfessor;
    private String nome;
    private String departamento;

    // Lista de avaliações recebidas (associação com Avaliacao)
    private List<Avaliacao> avaliacoes;

    /**
     * Cria um novo professor.
     *
     * @param nome        nome completo do professor
     * @param departamento departamento ou curso ao qual está vinculado
     */
    public Professor(String nome, String departamento) {
        this.idProfessor = proximoId++;
        this.nome = nome;
        this.departamento = departamento;
        this.avaliacoes = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Métodos de negócio
    // -------------------------------------------------------------------------

    /**
     * Adiciona uma avaliação recebida à lista do professor.
     *
     * @param avaliacao avaliação a ser registrada
     */
    public void adicionarAvaliacao(Avaliacao avaliacao) {
        this.avaliacoes.add(avaliacao);
    }

    /**
     * Calcula e retorna a média das notas de todas as avaliações recebidas.
     *
     * @return média das notas (0.0 se não houver avaliações)
     */
    public double calcularMedia() {
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }
        double soma = 0.0;
        for (Avaliacao av : avaliacoes) {
            soma += av.getNota();
        }
        return soma / avaliacoes.size();
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public int getIdProfessor() {
        return idProfessor;
    }

    public String getNome() {
        return nome;
    }

    public String getDepartamento() {
        return departamento;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    // -------------------------------------------------------------------------
    // Setters (para atualização via SistemaMeLivra)
    // -------------------------------------------------------------------------

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("Professor #%d | %s | Departamento: %s | Média: %.1f (%d avaliação(ões))",
                idProfessor, nome, departamento, calcularMedia(), avaliacoes.size());
    }
}
