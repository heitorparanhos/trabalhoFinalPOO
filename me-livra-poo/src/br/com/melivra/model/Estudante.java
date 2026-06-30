package br.com.melivra.model;

import br.com.melivra.exception.CampoObrigatorioException;
import br.com.melivra.exception.NotaInvalidaException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa um estudante universitário — o usuário comum da plataforma Me Livra.
 *
 * <p>É o ator principal do sistema: pode criar posts, avaliar professores e
 * publicar anúncios. Mantém listas próprias dos conteúdos que originou, o que
 * permite rastreá-los individualmente.</p>
 */
public class Estudante extends Usuario {

    private static final long serialVersionUID = 1L;

    private String curso;

    private final List<Post> posts;
    private final List<Avaliacao> avaliacoes;
    private final List<Anuncio> anuncios;

    /**
     * Cria um novo estudante.
     *
     * @param nome  nome completo
     * @param email e-mail de acesso
     * @param senha senha de acesso
     * @param curso curso universitário (ex.: "Sistemas de Informação")
     * @throws CampoObrigatorioException se algum campo obrigatório for vazio
     */
    public Estudante(String nome, String email, String senha, String curso)
            throws CampoObrigatorioException {
        super(nome, email, senha);
        exigir(curso, "curso");
        this.curso = curso.trim();
        this.posts = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
        this.anuncios = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Override
    public String getTipo() {
        return "Estudante";
    }

    /**
     * Cria um novo post e o adiciona à lista de posts do estudante.
     *
     * @param texto conteúdo textual do post
     * @return o post criado
     * @throws CampoObrigatorioException se o texto for vazio
     */
    public Post criarPost(String texto) throws CampoObrigatorioException {
        Post novoPost = new Post(this, texto);
        this.posts.add(novoPost);
        return novoPost;
    }

    /**
     * Avalia um professor com nota e comentário.
     *
     * @param professor  professor a ser avaliado
     * @param nota       nota de 0 a 10
     * @param comentario comentário sobre o professor
     * @return a avaliação criada
     * @throws NotaInvalidaException     se a nota estiver fora de [0, 10]
     * @throws CampoObrigatorioException se o comentário for vazio
     */
    public Avaliacao avaliarProfessor(Professor professor, double nota, String comentario)
            throws NotaInvalidaException, CampoObrigatorioException {
        Avaliacao avaliacao = new Avaliacao(this, professor, nota, comentario);
        this.avaliacoes.add(avaliacao);
        professor.adicionarAvaliacao(avaliacao);
        return avaliacao;
    }

    /**
     * Cria um novo anúncio e o adiciona à lista de anúncios do estudante.
     *
     * @param titulo    título do anúncio
     * @param descricao descrição detalhada
     * @param preco     preço (0.0 para "a combinar")
     * @return o anúncio criado
     * @throws CampoObrigatorioException se título ou descrição forem vazios
     */
    public Anuncio criarAnuncio(String titulo, String descricao, double preco)
            throws CampoObrigatorioException {
        Anuncio novoAnuncio = new Anuncio(this, titulo, descricao, preco);
        this.anuncios.add(novoAnuncio);
        return novoAnuncio;
    }

    /** @return curso do estudante */
    public String getCurso() {
        return curso;
    }

    /**
     * Altera o curso do estudante.
     *
     * @param curso novo curso (não pode ser vazio)
     * @throws CampoObrigatorioException se o curso for vazio
     */
    public void setCurso(String curso) throws CampoObrigatorioException {
        exigir(curso, "curso");
        this.curso = curso.trim();
    }

    /** @return lista <b>somente leitura</b> dos posts criados pelo estudante */
    public List<Post> getPosts() {
        return Collections.unmodifiableList(posts);
    }

    /** @return lista <b>somente leitura</b> das avaliações feitas pelo estudante */
    public List<Avaliacao> getAvaliacoes() {
        return Collections.unmodifiableList(avaliacoes);
    }

    /** @return lista <b>somente leitura</b> dos anúncios publicados pelo estudante */
    public List<Anuncio> getAnuncios() {
        return Collections.unmodifiableList(anuncios);
    }

    @Override
    public String toString() {
        return super.toString() + " | Curso: " + curso;
    }
}
