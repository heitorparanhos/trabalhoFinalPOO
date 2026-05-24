package br.com.melivra.repository;

import br.com.melivra.model.Anuncio;
import br.com.melivra.model.Avaliacao;
import br.com.melivra.model.Post;
import br.com.melivra.model.Professor;
import br.com.melivra.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositório central do sistema Me Livra.
 * Gerencia todas as entidades em memória por meio de listas (ArrayList).
 * Expõe métodos CRUD uniformes para cada tipo de entidade.
 *
 * TODO: implementar persistência em arquivo (serialização ou CSV) na entrega final.
 */
public class SistemaMeLivra {

    // Listas em memória para cada entidade gerenciada
    private List<Usuario> usuarios;
    private List<Post> posts;
    private List<Professor> professores;
    private List<Avaliacao> avaliacoes;
    private List<Anuncio> anuncios;

    /**
     * Inicializa o sistema com listas vazias.
     */
    public SistemaMeLivra() {
        this.usuarios = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.professores = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
        this.anuncios = new ArrayList<>();
    }

    // =========================================================================
    // CRUD — Usuario
    // =========================================================================

    /** Cadastra um usuário no sistema. */
    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    /** Remove um usuário pelo ID. Retorna true se encontrado e removido. */
    public boolean removerUsuario(int idUsuario) {
        return usuarios.removeIf(u -> u.getIdUsuario() == idUsuario);
    }

    /** Busca e retorna um usuário pelo ID, ou null se não encontrado. */
    public Usuario consultarUsuario(int idUsuario) {
        for (Usuario u : usuarios) {
            if (u.getIdUsuario() == idUsuario) return u;
        }
        return null;
    }

    /** Retorna a lista completa de usuários cadastrados. */
    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    // =========================================================================
    // CRUD — Post
    // =========================================================================

    /** Cadastra um post no sistema. */
    public void cadastrarPost(Post post) {
        posts.add(post);
    }

    /** Remove um post pelo ID. Retorna true se encontrado e removido. */
    public boolean removerPost(int idPost) {
        return posts.removeIf(p -> p.getIdPost() == idPost);
    }

    /**
     * Atualiza o texto de um post existente.
     *
     * @param idPost    ID do post a atualizar
     * @param novoTexto novo conteúdo textual
     * @return true se o post foi encontrado e atualizado
     */
    public boolean atualizarPost(int idPost, String novoTexto) {
        Post post = consultarPost(idPost);
        if (post != null) {
            post.setTexto(novoTexto);
            return true;
        }
        return false;
    }

    /** Busca e retorna um post pelo ID, ou null se não encontrado. */
    public Post consultarPost(int idPost) {
        for (Post p : posts) {
            if (p.getIdPost() == idPost) return p;
        }
        return null;
    }

    /** Retorna a lista completa de posts cadastrados. */
    public List<Post> listarPosts() {
        return new ArrayList<>(posts);
    }

    // =========================================================================
    // CRUD — Professor
    // =========================================================================

    /** Cadastra um professor no sistema. */
    public void cadastrarProfessor(Professor professor) {
        professores.add(professor);
    }

    /** Remove um professor pelo ID. Retorna true se encontrado e removido. */
    public boolean removerProfessor(int idProfessor) {
        return professores.removeIf(p -> p.getIdProfessor() == idProfessor);
    }

    /**
     * Atualiza os dados de um professor existente.
     *
     * @param idProfessor   ID do professor a atualizar
     * @param nome          novo nome (null para não alterar)
     * @param departamento  novo departamento (null para não alterar)
     * @return true se o professor foi encontrado e atualizado
     */
    public boolean atualizarProfessor(int idProfessor, String nome, String departamento) {
        Professor prof = consultarProfessor(idProfessor);
        if (prof != null) {
            if (nome != null && !nome.isBlank()) prof.setNome(nome);
            if (departamento != null && !departamento.isBlank()) prof.setDepartamento(departamento);
            return true;
        }
        return false;
    }

    /** Busca e retorna um professor pelo ID, ou null se não encontrado. */
    public Professor consultarProfessor(int idProfessor) {
        for (Professor p : professores) {
            if (p.getIdProfessor() == idProfessor) return p;
        }
        return null;
    }

    /** Retorna a lista completa de professores cadastrados. */
    public List<Professor> listarProfessores() {
        return new ArrayList<>(professores);
    }

    // =========================================================================
    // CRUD — Avaliacao
    // =========================================================================

    /** Cadastra uma avaliação no sistema. */
    public void cadastrarAvaliacao(Avaliacao avaliacao) {
        avaliacoes.add(avaliacao);
    }

    /** Remove uma avaliação pelo ID. Retorna true se encontrada e removida. */
    public boolean removerAvaliacao(int idAvaliacao) {
        return avaliacoes.removeIf(a -> a.getIdAvaliacao() == idAvaliacao);
    }

    /** Busca e retorna uma avaliação pelo ID, ou null se não encontrada. */
    public Avaliacao consultarAvaliacao(int idAvaliacao) {
        for (Avaliacao a : avaliacoes) {
            if (a.getIdAvaliacao() == idAvaliacao) return a;
        }
        return null;
    }

    /** Retorna a lista completa de avaliações cadastradas. */
    public List<Avaliacao> listarAvaliacoes() {
        return new ArrayList<>(avaliacoes);
    }

    // =========================================================================
    // CRUD — Anuncio
    // =========================================================================

    /** Cadastra um anúncio no sistema. */
    public void cadastrarAnuncio(Anuncio anuncio) {
        anuncios.add(anuncio);
    }

    /** Remove um anúncio pelo ID. Retorna true se encontrado e removido. */
    public boolean removerAnuncio(int idAnuncio) {
        return anuncios.removeIf(a -> a.getIdAnuncio() == idAnuncio);
    }

    /**
     * Atualiza os dados de um anúncio existente.
     *
     * @param idAnuncio  ID do anúncio a atualizar
     * @param titulo     novo título (null para não alterar)
     * @param descricao  nova descrição (null para não alterar)
     * @param preco      novo preço (valor negativo para não alterar)
     * @return true se o anúncio foi encontrado e atualizado
     */
    public boolean atualizarAnuncio(int idAnuncio, String titulo, String descricao, double preco) {
        Anuncio anuncio = consultarAnuncio(idAnuncio);
        if (anuncio != null) {
            if (titulo != null && !titulo.isBlank()) anuncio.setTitulo(titulo);
            if (descricao != null && !descricao.isBlank()) anuncio.setDescricao(descricao);
            if (preco >= 0) anuncio.setPreco(preco);
            return true;
        }
        return false;
    }

    /** Busca e retorna um anúncio pelo ID, ou null se não encontrado. */
    public Anuncio consultarAnuncio(int idAnuncio) {
        for (Anuncio a : anuncios) {
            if (a.getIdAnuncio() == idAnuncio) return a;
        }
        return null;
    }

    /** Retorna a lista completa de anúncios cadastrados. */
    public List<Anuncio> listarAnuncios() {
        return new ArrayList<>(anuncios);
    }
}
