package br.com.melivra.model;

import br.com.melivra.exception.CampoObrigatorioException;
import br.com.melivra.util.Formatador;

import java.time.LocalDateTime;

/**
 * Representa um anúncio universitário publicado por um usuário.
 *
 * <p>Pode ser usado para venda de materiais, prestação de serviços ou
 * divulgação de oportunidades acadêmicas. O preço zero é interpretado como
 * "a combinar".</p>
 */
public class Anuncio implements Identificavel {

    private static final long serialVersionUID = 1L;

    /** Contador estático para IDs sequenciais (requisito b). */
    private static int proximoId = 1;

    private final int idAnuncio;
    private final Usuario autor;
    private String titulo;
    private String descricao;
    private double preco;
    private final LocalDateTime dataHora;

    /**
     * Cria um novo anúncio. ID e data/hora são gerados automaticamente.
     *
     * @param autor     usuário que está publicando o anúncio
     * @param titulo    título resumido do anúncio
     * @param descricao descrição detalhada do item ou serviço
     * @param preco     preço (use 0.0 para "a combinar")
     * @throws CampoObrigatorioException se título ou descrição forem vazios
     */
    public Anuncio(Usuario autor, String titulo, String descricao, double preco)
            throws CampoObrigatorioException {
        Usuario.exigir(titulo, "título do anúncio");
        Usuario.exigir(descricao, "descrição do anúncio");
        this.idAnuncio = proximoId++;
        this.autor = autor;
        this.titulo = titulo.trim();
        this.descricao = descricao.trim();
        this.preco = Math.max(0.0, preco);
        this.dataHora = LocalDateTime.now();
    }

    /** {@inheritDoc} */
    @Override
    public int getId() {
        return idAnuncio;
    }

    /** {@inheritDoc} */
    @Override
    public String getTipoEntidade() {
        return "Anúncio";
    }

    /** @return ID do anúncio */
    public int getIdAnuncio() {
        return idAnuncio;
    }

    /** @return autor do anúncio */
    public Usuario getAutor() {
        return autor;
    }

    /** @return título do anúncio */
    public String getTitulo() {
        return titulo;
    }

    /** @return descrição do anúncio */
    public String getDescricao() {
        return descricao;
    }

    /** @return preço do anúncio (0.0 = a combinar) */
    public double getPreco() {
        return preco;
    }

    /** @return data/hora de criação */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Altera o título do anúncio.
     *
     * @param titulo novo título (não pode ser vazio)
     * @throws CampoObrigatorioException se o título for vazio
     */
    public void setTitulo(String titulo) throws CampoObrigatorioException {
        Usuario.exigir(titulo, "título do anúncio");
        this.titulo = titulo.trim();
    }

    /**
     * Altera a descrição do anúncio.
     *
     * @param descricao nova descrição (não pode ser vazia)
     * @throws CampoObrigatorioException se a descrição for vazia
     */
    public void setDescricao(String descricao) throws CampoObrigatorioException {
        Usuario.exigir(descricao, "descrição do anúncio");
        this.descricao = descricao.trim();
    }

    /**
     * Altera o preço do anúncio. Valores negativos são tratados como zero.
     *
     * @param preco novo preço
     */
    public void setPreco(double preco) {
        this.preco = Math.max(0.0, preco);
    }

    /** @return próximo ID que será atribuído a um novo anúncio */
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
        return String.format("Anúncio #%d | %s | %s | Por: %s | %s%n  %s",
                idAnuncio, titulo, Formatador.formatarPreco(preco), autor.getNome(),
                Formatador.formatar(dataHora), descricao);
    }
}
