package br.com.melivra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa a avaliação de um professor feita por um usuário.
 * Registra uma nota numérica (0 a 10) e um comentário textual.
 */
public class Avaliacao {

    // Contador estático para IDs sequenciais
    private static int proximoId = 1;

    private int idAvaliacao;
    private Usuario autor;
    private Professor professor;
    private double nota;
    private String comentario;
    private LocalDateTime dataHora;

    /**
     * Cria uma nova avaliação. ID e data/hora são gerados automaticamente.
     *
     * @param autor      usuário que está avaliando
     * @param professor  professor sendo avaliado
     * @param nota       nota de 0 a 10
     * @param comentario comentário textual sobre o professor
     */
    public Avaliacao(Usuario autor, Professor professor, double nota, String comentario) {
        this.idAvaliacao = proximoId++;
        this.autor = autor;
        this.professor = professor;
        // Garante que a nota fique no intervalo válido
        this.nota = Math.max(0.0, Math.min(10.0, nota));
        this.comentario = comentario;
        this.dataHora = LocalDateTime.now();
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public int getIdAvaliacao() {
        return idAvaliacao;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Professor getProfessor() {
        return professor;
    }

    public double getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
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
        return String.format("Avaliação #%d | Prof.: %s | Nota: %.1f/10 | Por: %s | %s%n  \"%s\"",
                idAvaliacao, professor.getNome(), nota, autor.getNome(),
                dataHora.format(fmt), comentario);
    }
}
