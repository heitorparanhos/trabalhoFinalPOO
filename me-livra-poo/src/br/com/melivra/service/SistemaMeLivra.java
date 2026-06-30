package br.com.melivra.service;

import br.com.melivra.exception.AutenticacaoException;
import br.com.melivra.exception.EntidadeNaoEncontradaException;
import br.com.melivra.exception.PersistenciaException;
import br.com.melivra.model.Anuncio;
import br.com.melivra.model.Avaliacao;
import br.com.melivra.model.Comentario;
import br.com.melivra.model.Post;
import br.com.melivra.model.Professor;
import br.com.melivra.model.Usuario;
import br.com.melivra.persistence.RepositorioArquivo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório central e serviço de negócio do sistema Me Livra (padrão
 * <i>Repository</i> + <i>Facade</i>).
 *
 * <p>Mantém todas as entidades em coleções {@link ArrayList} (requisito i) e
 * expõe operações CRUD uniformes — Incluir, Excluir, Alterar, Consultar e
 * Listar — para cada entidade principal (requisito e). Também concentra a
 * persistência: ao chamar {@link #salvar()} grava um instantâneo do estado em
 * arquivo, e {@link #carregar()} o restaura, recompondo os contadores estáticos
 * de ID (requisitos b e c).</p>
 */
public class SistemaMeLivra {

    /** Caminho padrão do arquivo de persistência (relativo à execução). */
    public static final String ARQUIVO_PADRAO = "dados/melivra.dat";

    private final String caminhoArquivo;

    // Coleções em memória (ArrayList) para cada entidade gerenciada.
    private List<Usuario> usuarios;
    private List<Post> posts;
    private List<Professor> professores;
    private List<Avaliacao> avaliacoes;
    private List<Anuncio> anuncios;

    /**
     * Cria o sistema usando o arquivo de persistência padrão.
     */
    public SistemaMeLivra() {
        this(ARQUIVO_PADRAO);
    }

    /**
     * Cria o sistema apontando para um arquivo de persistência específico
     * (útil para testes automatizados).
     *
     * @param caminhoArquivo caminho do arquivo de dados
     */
    public SistemaMeLivra(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.usuarios = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.professores = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
        this.anuncios = new ArrayList<>();
    }

    // =========================================================================
    // Persistência
    // =========================================================================

    /**
     * Estrutura serializável que agrupa todo o estado do sistema em um único
     * objeto, preservando a identidade das referências cruzadas entre entidades
     * (ex.: o autor de um post é o mesmo objeto da lista de usuários).
     */
    private static class EstadoSistema implements Serializable {
        private static final long serialVersionUID = 1L;
        List<Usuario> usuarios;
        List<Post> posts;
        List<Professor> professores;
        List<Avaliacao> avaliacoes;
        List<Anuncio> anuncios;
        int proximoIdUsuario;
        int proximoIdPost;
        int proximoIdComentario;
        int proximoIdProfessor;
        int proximoIdAvaliacao;
        int proximoIdAnuncio;
    }

    /**
     * Grava todo o estado do sistema (entidades + contadores de ID) em arquivo.
     *
     * @throws PersistenciaException se ocorrer falha de E/S
     */
    public void salvar() throws PersistenciaException {
        EstadoSistema estado = new EstadoSistema();
        estado.usuarios = usuarios;
        estado.posts = posts;
        estado.professores = professores;
        estado.avaliacoes = avaliacoes;
        estado.anuncios = anuncios;
        estado.proximoIdUsuario = Usuario.getProximoId();
        estado.proximoIdPost = Post.getProximoId();
        estado.proximoIdComentario = Comentario.getProximoId();
        estado.proximoIdProfessor = Professor.getProximoId();
        estado.proximoIdAvaliacao = Avaliacao.getProximoId();
        estado.proximoIdAnuncio = Anuncio.getProximoId();
        RepositorioArquivo.salvar(estado, caminhoArquivo);
    }

    /**
     * Carrega o estado do sistema a partir do arquivo, caso ele exista, e
     * restaura os contadores estáticos de ID para que novos identificadores
     * continuem a sequência sem colisão.
     *
     * @return {@code true} se havia dados salvos e foram carregados;
     *         {@code false} se o arquivo ainda não existe
     * @throws PersistenciaException se o arquivo existir mas não puder ser lido
     */
    public boolean carregar() throws PersistenciaException {
        if (!RepositorioArquivo.existe(caminhoArquivo)) {
            return false;
        }
        EstadoSistema estado = RepositorioArquivo.carregar(caminhoArquivo);
        this.usuarios = estado.usuarios != null ? estado.usuarios : new ArrayList<>();
        this.posts = estado.posts != null ? estado.posts : new ArrayList<>();
        this.professores = estado.professores != null ? estado.professores : new ArrayList<>();
        this.avaliacoes = estado.avaliacoes != null ? estado.avaliacoes : new ArrayList<>();
        this.anuncios = estado.anuncios != null ? estado.anuncios : new ArrayList<>();
        Usuario.setProximoId(estado.proximoIdUsuario);
        Post.setProximoId(estado.proximoIdPost);
        Comentario.setProximoId(estado.proximoIdComentario);
        Professor.setProximoId(estado.proximoIdProfessor);
        Avaliacao.setProximoId(estado.proximoIdAvaliacao);
        Anuncio.setProximoId(estado.proximoIdAnuncio);
        return true;
    }

    // =========================================================================
    // Autenticação
    // =========================================================================

    /**
     * Autentica um usuário por e-mail e senha.
     *
     * @param email e-mail informado
     * @param senha senha informada
     * @return o usuário autenticado
     * @throws AutenticacaoException se as credenciais forem inválidas
     */
    public Usuario autenticar(String email, String senha) throws AutenticacaoException {
        if (email != null) {
            for (Usuario u : usuarios) {
                if (u.getEmail().equalsIgnoreCase(email.trim()) && u.autenticar(senha)) {
                    return u;
                }
            }
        }
        throw new AutenticacaoException();
    }

    // =========================================================================
    // CRUD — Usuario
    // =========================================================================

    /**
     * Inclui um usuário no sistema.
     *
     * @param usuario usuário a cadastrar
     */
    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    /**
     * Exclui um usuário pelo ID.
     *
     * @param idUsuario ID do usuário
     * @throws EntidadeNaoEncontradaException se o usuário não existir
     */
    public void removerUsuario(int idUsuario) throws EntidadeNaoEncontradaException {
        if (!usuarios.removeIf(u -> u.getIdUsuario() == idUsuario)) {
            throw new EntidadeNaoEncontradaException("Usuário", idUsuario);
        }
    }

    /**
     * Consulta um usuário pelo ID.
     *
     * @param idUsuario ID do usuário
     * @return o usuário encontrado
     * @throws EntidadeNaoEncontradaException se o usuário não existir
     */
    public Usuario consultarUsuario(int idUsuario) throws EntidadeNaoEncontradaException {
        for (Usuario u : usuarios) {
            if (u.getIdUsuario() == idUsuario) {
                return u;
            }
        }
        throw new EntidadeNaoEncontradaException("Usuário", idUsuario);
    }

    /** @return cópia da lista de usuários cadastrados */
    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    // =========================================================================
    // CRUD — Post
    // =========================================================================

    /**
     * Inclui um post no sistema.
     *
     * @param post post a cadastrar
     */
    public void cadastrarPost(Post post) {
        posts.add(post);
    }

    /**
     * Exclui um post pelo ID.
     *
     * @param idPost ID do post
     * @throws EntidadeNaoEncontradaException se o post não existir
     */
    public void removerPost(int idPost) throws EntidadeNaoEncontradaException {
        if (!posts.removeIf(p -> p.getIdPost() == idPost)) {
            throw new EntidadeNaoEncontradaException("Post", idPost);
        }
    }

    /**
     * Altera o texto de um post existente.
     *
     * @param idPost    ID do post
     * @param novoTexto novo conteúdo textual
     * @throws EntidadeNaoEncontradaException             se o post não existir
     * @throws br.com.melivra.exception.CampoObrigatorioException se o texto for vazio
     */
    public void atualizarPost(int idPost, String novoTexto)
            throws br.com.melivra.exception.MeLivraException {
        consultarPost(idPost).setTexto(novoTexto);
    }

    /**
     * Consulta um post pelo ID.
     *
     * @param idPost ID do post
     * @return o post encontrado
     * @throws EntidadeNaoEncontradaException se o post não existir
     */
    public Post consultarPost(int idPost) throws EntidadeNaoEncontradaException {
        for (Post p : posts) {
            if (p.getIdPost() == idPost) {
                return p;
            }
        }
        throw new EntidadeNaoEncontradaException("Post", idPost);
    }

    /** @return cópia da lista de posts cadastrados */
    public List<Post> listarPosts() {
        return new ArrayList<>(posts);
    }

    // =========================================================================
    // CRUD — Professor
    // =========================================================================

    /**
     * Inclui um professor no sistema.
     *
     * @param professor professor a cadastrar
     */
    public void cadastrarProfessor(Professor professor) {
        professores.add(professor);
    }

    /**
     * Exclui um professor pelo ID.
     *
     * @param idProfessor ID do professor
     * @throws EntidadeNaoEncontradaException se o professor não existir
     */
    public void removerProfessor(int idProfessor) throws EntidadeNaoEncontradaException {
        if (!professores.removeIf(p -> p.getIdProfessor() == idProfessor)) {
            throw new EntidadeNaoEncontradaException("Professor", idProfessor);
        }
    }

    /**
     * Altera os dados de um professor existente. Campos nulos/vazios são
     * ignorados (mantêm o valor atual).
     *
     * @param idProfessor  ID do professor
     * @param nome         novo nome (ou null/vazio para não alterar)
     * @param departamento novo departamento (ou null/vazio para não alterar)
     * @throws br.com.melivra.exception.MeLivraException se o professor não existir
     */
    public void atualizarProfessor(int idProfessor, String nome, String departamento)
            throws br.com.melivra.exception.MeLivraException {
        Professor prof = consultarProfessor(idProfessor);
        if (nome != null && !nome.isBlank()) {
            prof.setNome(nome);
        }
        if (departamento != null && !departamento.isBlank()) {
            prof.setDepartamento(departamento);
        }
    }

    /**
     * Consulta um professor pelo ID.
     *
     * @param idProfessor ID do professor
     * @return o professor encontrado
     * @throws EntidadeNaoEncontradaException se o professor não existir
     */
    public Professor consultarProfessor(int idProfessor) throws EntidadeNaoEncontradaException {
        for (Professor p : professores) {
            if (p.getIdProfessor() == idProfessor) {
                return p;
            }
        }
        throw new EntidadeNaoEncontradaException("Professor", idProfessor);
    }

    /** @return cópia da lista de professores cadastrados */
    public List<Professor> listarProfessores() {
        return new ArrayList<>(professores);
    }

    // =========================================================================
    // CRUD — Avaliacao
    // =========================================================================

    /**
     * Inclui uma avaliação no sistema.
     *
     * @param avaliacao avaliação a cadastrar
     */
    public void cadastrarAvaliacao(Avaliacao avaliacao) {
        avaliacoes.add(avaliacao);
    }

    /**
     * Exclui uma avaliação pelo ID, removendo-a também do professor avaliado.
     *
     * @param idAvaliacao ID da avaliação
     * @throws EntidadeNaoEncontradaException se a avaliação não existir
     */
    public void removerAvaliacao(int idAvaliacao) throws EntidadeNaoEncontradaException {
        Avaliacao alvo = consultarAvaliacao(idAvaliacao);
        alvo.getProfessor().removerAvaliacao(idAvaliacao);
        avaliacoes.removeIf(a -> a.getIdAvaliacao() == idAvaliacao);
    }

    /**
     * Consulta uma avaliação pelo ID.
     *
     * @param idAvaliacao ID da avaliação
     * @return a avaliação encontrada
     * @throws EntidadeNaoEncontradaException se a avaliação não existir
     */
    public Avaliacao consultarAvaliacao(int idAvaliacao) throws EntidadeNaoEncontradaException {
        for (Avaliacao a : avaliacoes) {
            if (a.getIdAvaliacao() == idAvaliacao) {
                return a;
            }
        }
        throw new EntidadeNaoEncontradaException("Avaliação", idAvaliacao);
    }

    /** @return cópia da lista de avaliações cadastradas */
    public List<Avaliacao> listarAvaliacoes() {
        return new ArrayList<>(avaliacoes);
    }

    // =========================================================================
    // CRUD — Anuncio
    // =========================================================================

    /**
     * Inclui um anúncio no sistema.
     *
     * @param anuncio anúncio a cadastrar
     */
    public void cadastrarAnuncio(Anuncio anuncio) {
        anuncios.add(anuncio);
    }

    /**
     * Exclui um anúncio pelo ID.
     *
     * @param idAnuncio ID do anúncio
     * @throws EntidadeNaoEncontradaException se o anúncio não existir
     */
    public void removerAnuncio(int idAnuncio) throws EntidadeNaoEncontradaException {
        if (!anuncios.removeIf(a -> a.getIdAnuncio() == idAnuncio)) {
            throw new EntidadeNaoEncontradaException("Anúncio", idAnuncio);
        }
    }

    /**
     * Altera os dados de um anúncio existente. Campos nulos/vazios (ou preço
     * negativo) são ignorados.
     *
     * @param idAnuncio ID do anúncio
     * @param titulo    novo título (ou null/vazio para não alterar)
     * @param descricao nova descrição (ou null/vazio para não alterar)
     * @param preco     novo preço (negativo para não alterar)
     * @throws br.com.melivra.exception.MeLivraException se o anúncio não existir
     */
    public void atualizarAnuncio(int idAnuncio, String titulo, String descricao, double preco)
            throws br.com.melivra.exception.MeLivraException {
        Anuncio anuncio = consultarAnuncio(idAnuncio);
        if (titulo != null && !titulo.isBlank()) {
            anuncio.setTitulo(titulo);
        }
        if (descricao != null && !descricao.isBlank()) {
            anuncio.setDescricao(descricao);
        }
        if (preco >= 0) {
            anuncio.setPreco(preco);
        }
    }

    /**
     * Consulta um anúncio pelo ID.
     *
     * @param idAnuncio ID do anúncio
     * @return o anúncio encontrado
     * @throws EntidadeNaoEncontradaException se o anúncio não existir
     */
    public Anuncio consultarAnuncio(int idAnuncio) throws EntidadeNaoEncontradaException {
        for (Anuncio a : anuncios) {
            if (a.getIdAnuncio() == idAnuncio) {
                return a;
            }
        }
        throw new EntidadeNaoEncontradaException("Anúncio", idAnuncio);
    }

    /** @return cópia da lista de anúncios cadastrados */
    public List<Anuncio> listarAnuncios() {
        return new ArrayList<>(anuncios);
    }
}
