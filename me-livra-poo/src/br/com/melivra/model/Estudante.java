package br.com.melivra.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um estudante universitário — o usuário comum da plataforma Me Livra.
 * Pode criar posts, avaliar professores e publicar anúncios.
 */
public class Estudante extends Usuario {

    private String curso;

    // Relacionamentos: listas que pertencem ao estudante
    private List<Post> posts;
    private List<Avaliacao> avaliacoes;
    private List<Anuncio> anuncios;

    /**
     * Cria um novo estudante.
     *
     * @param nome  nome completo
     * @param email e-mail de acesso
     * @param senha senha de acesso
     * @param curso curso universitário (ex: "Sistemas de Informação")
     */
    public Estudante(String nome, String email, String senha, String curso) {
        super(nome, email, senha);
        this.curso = curso;
        this.posts = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
        this.anuncios = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Implementação do método abstrato
    // -------------------------------------------------------------------------

    @Override
    public String getTipo() {
        return "Estudante";
    }

    // -------------------------------------------------------------------------
    // Métodos de negócio
    // -------------------------------------------------------------------------

    /**
     * Cria um novo post e o adiciona à lista de posts do estudante.
     *
     * @param texto conteúdo textual do post
     * @return o post criado
     */
    public Post criarPost(String texto) {
        Post novoPost = new Post(this, texto);
        this.posts.add(novoPost);
        return novoPost;
    }

    /**
     * Avalia um professor com nota e comentário.
     *
     * @param professor professor a ser avaliado
     * @param nota      nota de 0 a 10
     * @param comentario comentário sobre o professor
     * @return a avaliação criada
     */
    public Avaliacao avaliarProfessor(Professor professor, double nota, String comentario) {
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
     */
    public Anuncio criarAnuncio(String titulo, String descricao, double preco) {
        Anuncio novoAnuncio = new Anuncio(this, titulo, descricao, preco);
        this.anuncios.add(novoAnuncio);
        return novoAnuncio;
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public String getCurso() {
        return curso;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public List<Anuncio> getAnuncios() {
        return anuncios;
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + " | Curso: " + curso;
    }
}
