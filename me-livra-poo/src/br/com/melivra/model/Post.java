package br.com.melivra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma publicação textual feita por um usuário na plataforma Me Livra.
 * Um post possui autor, conteúdo, data/hora de criação, curtidas
 * e uma lista de comentários (composição).
 */
public class Post {

    // Contador estático para IDs sequenciais
    private static int proximoId = 1;

    private int idPost;
    private Usuario autor;
    private String texto;
    private LocalDateTime dataHora;
    private int curtidas;

    // Composição: comentários pertencem ao post e não existem sem ele
    private List<Comentario> comentarios;

    /**
     * Cria um novo post. ID e data/hora são gerados automaticamente.
     *
     * @param autor usuário que está publicando
     * @param texto conteúdo textual do post
     */
    public Post(Usuario autor, String texto) {
        this.idPost = proximoId++;
        this.autor = autor;
        this.texto = texto;
        this.dataHora = LocalDateTime.now();
        this.curtidas = 0;
        this.comentarios = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Métodos de negócio
    // -------------------------------------------------------------------------

    /**
     * Adiciona um comentário à lista de comentários do post.
     *
     * @param comentario comentário a ser adicionado
     */
    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    /**
     * Registra uma curtida no post, incrementando o contador.
     */
    public void curtir() {
        this.curtidas++;
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public int getIdPost() {
        return idPost;
    }

    public Usuario getAutor() {
        return autor;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public int getCurtidas() {
        return curtidas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    // -------------------------------------------------------------------------
    // Setter (para atualização via SistemaMeLivra)
    // -------------------------------------------------------------------------

    public void setTexto(String texto) {
        this.texto = texto;
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("Post #%d | Autor: %s | %s curtida(s) | %s comentário(s) | %s%n  \"%s\"",
                idPost, autor.getNome(), curtidas, comentarios.size(),
                dataHora.format(fmt), texto);
    }
}
