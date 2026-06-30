package br.com.melivra.model;

import br.com.melivra.exception.CampoObrigatorioException;
import br.com.melivra.exception.NotaInvalidaException;
import br.com.melivra.util.Formatador;

import java.time.LocalDateTime;

/**
 * Representa a avaliação de um professor feita por um usuário.
 *
 * <p>Registra uma nota numérica (0 a 10) e um comentário textual. A regra de
 * negócio "a nota deve estar entre 0 e 10" é garantida no construtor por meio da
 * exceção própria {@link NotaInvalidaException}.</p>
 */
public class Avaliacao implements Identificavel {

    private static final long serialVersionUID = 1L;

    /** Nota mínima válida. */
    public static final double NOTA_MINIMA = 0.0;
    /** Nota máxima válida. */
    public static final double NOTA_MAXIMA = 10.0;

    /** Contador estático para IDs sequenciais (requisito b). */
    private static int proximoId = 1;

    private final int idAvaliacao;
    private final Usuario autor;
    private final Professor professor;
    private double nota;
    private String comentario;
    private final LocalDateTime dataHora;

    /**
     * Cria uma nova avaliação. ID e data/hora são gerados automaticamente.
     *
     * @param autor      usuário que está avaliando
     * @param professor  professor sendo avaliado
     * @param nota       nota de 0 a 10
     * @param comentario comentário textual sobre o professor
     * @throws NotaInvalidaException     se a nota estiver fora de [0, 10]
     * @throws CampoObrigatorioException se o comentário for vazio
     */
    public Avaliacao(Usuario autor, Professor professor, double nota, String comentario)
            throws NotaInvalidaException, CampoObrigatorioException {
        validarNota(nota);
        Usuario.exigir(comentario, "comentário da avaliação");
        this.idAvaliacao = proximoId++;
        this.autor = autor;
        this.professor = professor;
        this.nota = nota;
        this.comentario = comentario.trim();
        this.dataHora = LocalDateTime.now();
    }

    /**
     * Valida que a nota está dentro do intervalo permitido.
     *
     * @param nota nota a validar
     * @throws NotaInvalidaException se a nota estiver fora de [0, 10]
     */
    public static void validarNota(double nota) throws NotaInvalidaException {
        if (nota < NOTA_MINIMA || nota > NOTA_MAXIMA) {
            throw new NotaInvalidaException(nota);
        }
    }

    /** {@inheritDoc} */
    @Override
    public int getId() {
        return idAvaliacao;
    }

    /** {@inheritDoc} */
    @Override
    public String getTipoEntidade() {
        return "Avaliação";
    }

    /** @return ID da avaliação */
    public int getIdAvaliacao() {
        return idAvaliacao;
    }

    /** @return autor da avaliação */
    public Usuario getAutor() {
        return autor;
    }

    /** @return professor avaliado */
    public Professor getProfessor() {
        return professor;
    }

    /** @return nota atribuída (0 a 10) */
    public double getNota() {
        return nota;
    }

    /** @return comentário da avaliação */
    public String getComentario() {
        return comentario;
    }

    /** @return data/hora de criação */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Altera a nota da avaliação.
     *
     * @param nota nova nota (deve estar entre 0 e 10)
     * @throws NotaInvalidaException se a nota estiver fora de [0, 10]
     */
    public void setNota(double nota) throws NotaInvalidaException {
        validarNota(nota);
        this.nota = nota;
    }

    /**
     * Altera o comentário da avaliação.
     *
     * @param comentario novo comentário (não pode ser vazio)
     * @throws CampoObrigatorioException se o comentário for vazio
     */
    public void setComentario(String comentario) throws CampoObrigatorioException {
        Usuario.exigir(comentario, "comentário da avaliação");
        this.comentario = comentario.trim();
    }

    /** @return próximo ID que será atribuído a uma nova avaliação */
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
                "Avaliação #%d | Prof.: %s | Nota: %.1f/10 | Por: %s | %s%n  \"%s\"",
                idAvaliacao, professor.getNome(), nota, autor.getNome(),
                Formatador.formatar(dataHora), comentario);
    }
}
