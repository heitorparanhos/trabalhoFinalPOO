package br.com.melivra.model;

import br.com.melivra.exception.CampoObrigatorioException;
import br.com.melivra.util.Formatador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa uma publicação textual feita por um usuário na plataforma Me Livra.
 *
 * <p>Um post possui autor, conteúdo, data/hora de criação, contador de curtidas
 * e uma lista de comentários. A relação com {@link Comentario} é de
 * <b>composição</b>: os comentários pertencem ao post e não existem sem ele.</p>
 */
public class Post implements Identificavel {

    private static final long serialVersionUID = 1L;

    /** Contador estático para IDs sequenciais (requisito b). */
    private static int proximoId = 1;

    private final int idPost;
    private final Usuario autor;
    private String texto;
    private final LocalDateTime dataHora;
    private int curtidas;

    /** Composição: comentários pertencem ao post e não existem sem ele. */
    private final List<Comentario> comentarios;

    /**
     * Cria um novo post. ID e data/hora são gerados automaticamente.
     *
     * @param autor usuário que está publicando
     * @param texto conteúdo textual do post
     * @throws CampoObrigatorioException se o texto for vazio
     */
    public Post(Usuario autor, String texto) throws CampoObrigatorioException {
        Usuario.exigir(texto, "texto do post");
        this.idPost = proximoId++;
        this.autor = autor;
        this.texto = texto.trim();
        this.dataHora = LocalDateTime.now();
        this.curtidas = 0;
        this.comentarios = new ArrayList<>();
    }

    /**
     * Adiciona um comentário à lista de comentários do post.
     *
     * @param comentario comentário a ser adicionado
     */
    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    /**
     * Remove um comentário do post pelo seu ID (usado na moderação).
     *
     * @param idComentario ID do comentário a remover
     * @return {@code true} se o comentário foi encontrado e removido
     */
    public boolean removerComentario(int idComentario) {
        return comentarios.removeIf(c -> c.getIdComentario() == idComentario);
    }

    /** Registra uma curtida no post, incrementando o contador. */
    public void curtir() {
        this.curtidas++;
    }

    /** {@inheritDoc} */
    @Override
    public int getId() {
        return idPost;
    }

    /** {@inheritDoc} */
    @Override
    public String getTipoEntidade() {
        return "Post";
    }

    /** @return ID do post */
    public int getIdPost() {
        return idPost;
    }

    /** @return autor do post */
    public Usuario getAutor() {
        return autor;
    }

    /** @return texto do post */
    public String getTexto() {
        return texto;
    }

    /** @return data/hora de criação */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /** @return número de curtidas */
    public int getCurtidas() {
        return curtidas;
    }

    /** @return lista <b>somente leitura</b> dos comentários do post */
    public List<Comentario> getComentarios() {
        return Collections.unmodifiableList(comentarios);
    }

    /**
     * Altera o texto do post.
     *
     * @param texto novo conteúdo (não pode ser vazio)
     * @throws CampoObrigatorioException se o texto for vazio
     */
    public void setTexto(String texto) throws CampoObrigatorioException {
        Usuario.exigir(texto, "texto do post");
        this.texto = texto.trim();
    }

    /** @return próximo ID que será atribuído a um novo post */
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
                "Post #%d | Autor: %s | %d curtida(s) | %d comentário(s) | %s%n  \"%s\"",
                idPost, autor.getNome(), curtidas, comentarios.size(),
                Formatador.formatar(dataHora), texto);
    }
}
