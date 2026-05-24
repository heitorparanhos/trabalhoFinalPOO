package br.com.melivra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa um comentário feito por um usuário em um post.
 * Comentários fazem parte da composição de um Post e não existem de forma independente.
 */
public class Comentario {

    // Contador estático para IDs sequenciais
    private static int proximoId = 1;

    private int idComentario;
    private Usuario autor;
    private String texto;
    private LocalDateTime dataHora;

    /**
     * Cria um novo comentário. ID e data/hora são gerados automaticamente.
     *
     * @param autor usuário que está comentando
     * @param texto conteúdo textual do comentário
     */
    public Comentario(Usuario autor, String texto) {
        this.idComentario = proximoId++;
        this.autor = autor;
        this.texto = texto;
        this.dataHora = LocalDateTime.now();
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public int getIdComentario() {
        return idComentario;
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

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("  Comentário #%d | %s (%s): \"%s\"",
                idComentario, autor.getNome(), dataHora.format(fmt), texto);
    }
}
