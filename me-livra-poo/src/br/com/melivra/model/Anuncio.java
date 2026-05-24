package br.com.melivra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa um anúncio universitário publicado por um usuário.
 * Pode ser usado para venda de materiais, prestação de serviços,
 * divulgação de oportunidades, entre outros.
 */
public class Anuncio {

    // Contador estático para IDs sequenciais
    private static int proximoId = 1;

    private int idAnuncio;
    private Usuario autor;
    private String titulo;
    private String descricao;
    private double preco;
    private LocalDateTime dataHora;

    /**
     * Cria um novo anúncio. ID e data/hora são gerados automaticamente.
     *
     * @param autor     usuário que está publicando o anúncio
     * @param titulo    título resumido do anúncio
     * @param descricao descrição detalhada do item ou serviço
     * @param preco     preço (use 0.0 para "a combinar")
     */
    public Anuncio(Usuario autor, String titulo, String descricao, double preco) {
        this.idAnuncio = proximoId++;
        this.autor = autor;
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
        this.dataHora = LocalDateTime.now();
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public int getIdAnuncio() {
        return idAnuncio;
    }

    public Usuario getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    // -------------------------------------------------------------------------
    // Setters (para atualização via SistemaMeLivra)
    // -------------------------------------------------------------------------

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String precoStr = (preco == 0.0) ? "A combinar" : String.format("R$ %.2f", preco);
        return String.format("Anúncio #%d | %s | %s | Por: %s | %s%n  %s",
                idAnuncio, titulo, precoStr, autor.getNome(),
                dataHora.format(fmt), descricao);
    }
}
