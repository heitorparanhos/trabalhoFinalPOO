package br.com.melivra.model;

import br.com.melivra.exception.CampoObrigatorioException;
import br.com.melivra.util.Formatador;

import java.time.LocalDateTime;

/**
 * Representa um comentário feito por um usuário em um {@link Post}.
 *
 * <p>Comentários fazem parte da composição de um Post: não existem de forma
 * independente e são removidos junto com o post ao qual pertencem.</p>
 */
public class Comentario implements Identificavel {

    private static final long serialVersionUID = 1L;

    /** Contador estático para IDs sequenciais (requisito b). */
    private static int proximoId = 1;

    private final int idComentario;
    private final Usuario autor;
    private String texto;
    private final LocalDateTime dataHora;

    /**
     * Cria um novo comentário. ID e data/hora são gerados automaticamente.
     *
     * @param autor usuário que está comentando
     * @param texto conteúdo textual do comentário
     * @throws CampoObrigatorioException se o texto for vazio
     */
    public Comentario(Usuario autor, String texto) throws CampoObrigatorioException {
        Usuario.exigir(texto, "texto do comentário");
        this.idComentario = proximoId++;
        this.autor = autor;
        this.texto = texto.trim();
        this.dataHora = LocalDateTime.now();
    }

    /** {@inheritDoc} */
    @Override
    public int getId() {
        return idComentario;
    }

    /** {@inheritDoc} */
    @Override
    public String getTipoEntidade() {
        return "Comentário";
    }

    /** @return ID do comentário */
    public int getIdComentario() {
        return idComentario;
    }

    /** @return autor do comentário */
    public Usuario getAutor() {
        return autor;
    }

    /** @return texto do comentário */
    public String getTexto() {
        return texto;
    }

    /** @return data/hora de criação */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Altera o texto do comentário.
     *
     * @param texto novo texto (não pode ser vazio)
     * @throws CampoObrigatorioException se o texto for vazio
     */
    public void setTexto(String texto) throws CampoObrigatorioException {
        Usuario.exigir(texto, "texto do comentário");
        this.texto = texto.trim();
    }

    /** @return próximo ID que será atribuído a um novo comentário */
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
        return String.format("  Comentário #%d | %s (%s): \"%s\"",
                idComentario, autor.getNome(), Formatador.formatar(dataHora), texto);
    }
}
