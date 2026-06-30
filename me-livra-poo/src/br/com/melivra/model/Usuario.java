package br.com.melivra.model;

import br.com.melivra.exception.CampoObrigatorioException;

/**
 * Classe <b>abstrata</b> que representa um usuário do sistema Me Livra.
 *
 * <p>Todo usuário possui identificador único gerado automaticamente e de forma
 * sequencial (via atributo estático {@code proximoId}), além de nome, e-mail e
 * senha. A classe é abstrata porque nenhum usuário existe "genericamente": ele é
 * sempre, concretamente, um {@link Estudante} ou um {@link Administrador}. O
 * método abstrato {@link #getTipo()} obriga cada subclasse a se identificar,
 * habilitando o polimorfismo na exibição de informações.</p>
 *
 * @see Estudante
 * @see Administrador
 */
public abstract class Usuario implements Identificavel {

    private static final long serialVersionUID = 1L;

    /** Contador estático para geração de IDs sequenciais automáticos (requisito b). */
    private static int proximoId = 1;

    private final int idUsuario;
    private String nome;
    private String email;
    private String senha;

    /**
     * Construtor base. O ID é gerado automaticamente de forma sequencial.
     *
     * @param nome  nome completo do usuário
     * @param email endereço de e-mail (usado no login)
     * @param senha senha de acesso
     * @throws CampoObrigatorioException se nome, e-mail ou senha forem vazios
     */
    protected Usuario(String nome, String email, String senha) throws CampoObrigatorioException {
        exigir(nome, "nome");
        exigir(email, "e-mail");
        exigir(senha, "senha");
        this.idUsuario = proximoId++;
        this.nome = nome.trim();
        this.email = email.trim();
        this.senha = senha;
    }

    /**
     * Valida que um campo de texto obrigatório não está vazio.
     *
     * @param valor valor informado
     * @param campo nome do campo (para a mensagem de erro)
     * @throws CampoObrigatorioException se o valor for nulo ou em branco
     */
    protected static void exigir(String valor, String campo) throws CampoObrigatorioException {
        if (valor == null || valor.isBlank()) {
            throw new CampoObrigatorioException(campo);
        }
    }

    /**
     * Retorna o tipo concreto do usuário (ex.: "Estudante", "Administrador").
     *
     * @return string com o tipo do usuário
     */
    public abstract String getTipo();

    /** {@inheritDoc} */
    @Override
    public int getId() {
        return idUsuario;
    }

    /** {@inheritDoc} */
    @Override
    public String getTipoEntidade() {
        return getTipo();
    }

    /**
     * Retorna o identificador único do usuário.
     *
     * @return ID sequencial do usuário
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /** @return nome do usuário */
    public String getNome() {
        return nome;
    }

    /** @return e-mail do usuário */
    public String getEmail() {
        return email;
    }

    /** @return senha do usuário */
    public String getSenha() {
        return senha;
    }

    /**
     * Altera o nome do usuário.
     *
     * @param nome novo nome (não pode ser vazio)
     * @throws CampoObrigatorioException se o nome for vazio
     */
    public void setNome(String nome) throws CampoObrigatorioException {
        exigir(nome, "nome");
        this.nome = nome.trim();
    }

    /**
     * Altera o e-mail do usuário.
     *
     * @param email novo e-mail (não pode ser vazio)
     * @throws CampoObrigatorioException se o e-mail for vazio
     */
    public void setEmail(String email) throws CampoObrigatorioException {
        exigir(email, "e-mail");
        this.email = email.trim();
    }

    /**
     * Altera a senha do usuário.
     *
     * @param senha nova senha (não pode ser vazia)
     * @throws CampoObrigatorioException se a senha for vazia
     */
    public void setSenha(String senha) throws CampoObrigatorioException {
        exigir(senha, "senha");
        this.senha = senha;
    }

    /**
     * Verifica se a senha informada corresponde à do usuário.
     *
     * @param tentativa senha digitada
     * @return {@code true} se a senha confere
     */
    public boolean autenticar(String tentativa) {
        return senha.equals(tentativa);
    }

    // ---- Gestão do contador estático (usado pela camada de persistência) ----

    /** @return próximo ID que será atribuído a um novo usuário */
    public static int getProximoId() {
        return proximoId;
    }

    /**
     * Restaura o contador estático após o carregamento de dados de arquivo,
     * garantindo que novos IDs continuem a sequência sem colidir.
     *
     * @param valor novo valor do contador
     */
    public static void setProximoId(int valor) {
        proximoId = valor;
    }

    @Override
    public String toString() {
        return String.format("[%s] ID=%d | Nome: %s | E-mail: %s",
                getTipo(), idUsuario, nome, email);
    }
}
