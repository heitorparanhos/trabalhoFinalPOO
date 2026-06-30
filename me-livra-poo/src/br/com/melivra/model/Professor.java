package br.com.melivra.model;

import br.com.melivra.exception.CampoObrigatorioException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa um professor universitário que pode ser avaliado pelos estudantes.
 *
 * <p>Agrega todas as avaliações recebidas e calcula a nota média dinamicamente.
 * A relação com {@link Avaliacao} é de associação: um professor pode existir com
 * zero avaliações.</p>
 */
public class Professor implements Identificavel {

    private static final long serialVersionUID = 1L;

    /** Contador estático para IDs sequenciais (requisito b). */
    private static int proximoId = 1;

    private final int idProfessor;
    private String nome;
    private String departamento;

    private final List<Avaliacao> avaliacoes;

    /**
     * Cria um novo professor.
     *
     * @param nome         nome completo do professor
     * @param departamento departamento ou curso ao qual está vinculado
     * @throws CampoObrigatorioException se nome ou departamento forem vazios
     */
    public Professor(String nome, String departamento) throws CampoObrigatorioException {
        Usuario.exigir(nome, "nome");
        Usuario.exigir(departamento, "departamento");
        this.idProfessor = proximoId++;
        this.nome = nome.trim();
        this.departamento = departamento.trim();
        this.avaliacoes = new ArrayList<>();
    }

    /**
     * Adiciona uma avaliação recebida à lista do professor.
     *
     * @param avaliacao avaliação a ser registrada
     */
    public void adicionarAvaliacao(Avaliacao avaliacao) {
        this.avaliacoes.add(avaliacao);
    }

    /**
     * Remove do professor a avaliação com o ID informado.
     *
     * @param idAvaliacao ID da avaliação a remover
     * @return {@code true} se a avaliação existia e foi removida
     */
    public boolean removerAvaliacao(int idAvaliacao) {
        return this.avaliacoes.removeIf(a -> a.getIdAvaliacao() == idAvaliacao);
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

    /** {@inheritDoc} */
    @Override
    public int getId() {
        return idProfessor;
    }

    /** {@inheritDoc} */
    @Override
    public String getTipoEntidade() {
        return "Professor";
    }

    /** @return ID do professor */
    public int getIdProfessor() {
        return idProfessor;
    }

    /** @return nome do professor */
    public String getNome() {
        return nome;
    }

    /** @return departamento do professor */
    public String getDepartamento() {
        return departamento;
    }

    /** @return lista <b>somente leitura</b> das avaliações recebidas pelo professor */
    public List<Avaliacao> getAvaliacoes() {
        return Collections.unmodifiableList(avaliacoes);
    }

    /**
     * Altera o nome do professor.
     *
     * @param nome novo nome (não pode ser vazio)
     * @throws CampoObrigatorioException se o nome for vazio
     */
    public void setNome(String nome) throws CampoObrigatorioException {
        Usuario.exigir(nome, "nome");
        this.nome = nome.trim();
    }

    /**
     * Altera o departamento do professor.
     *
     * @param departamento novo departamento (não pode ser vazio)
     * @throws CampoObrigatorioException se o departamento for vazio
     */
    public void setDepartamento(String departamento) throws CampoObrigatorioException {
        Usuario.exigir(departamento, "departamento");
        this.departamento = departamento.trim();
    }

    /** @return próximo ID que será atribuído a um novo professor */
    public static int getProximoId() {
        return proximoId;
    }

    /**
     * Restaura o contador estático após o carregamento de dados de arquivo.
     *
     * @param valor novo valor do contador
     */
    public static void setProximoId(int valor) {
        proximoId = valor;
    }

    @Override
    public String toString() {
        return String.format(
                "Professor #%d | %s | Departamento: %s | Média: %.1f (%d avaliação(ões))",
                idProfessor, nome, departamento, calcularMedia(), avaliacoes.size());
    }
}
