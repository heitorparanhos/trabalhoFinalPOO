package br.com.melivra.model;

/**
 * Classe abstrata que representa um usuário do sistema Me Livra.
 * Todo usuário possui identificador único gerado automaticamente,
 * nome, e-mail e senha. Subclasses devem implementar getTipo().
 */
public abstract class Usuario {

    // Contador estático para geração de IDs sequenciais automáticos
    private static int proximoId = 1;

    private int idUsuario;
    private String nome;
    private String email;
    private String senha;

    /**
     * Construtor padrão. O ID é gerado automaticamente de forma sequencial.
     *
     * @param nome  nome completo do usuário
     * @param email endereço de e-mail (usado no login)
     * @param senha senha de acesso
     */
    public Usuario(String nome, String email, String senha) {
        this.idUsuario = proximoId++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // -------------------------------------------------------------------------
    // Método abstrato — cada subclasse informa seu tipo
    // -------------------------------------------------------------------------

    /**
     * Retorna o tipo do usuário (ex: "Estudante", "Administrador").
     *
     * @return string com o tipo do usuário
     */
    public abstract String getTipo();

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("[%s] ID=%d | Nome: %s | E-mail: %s",
                getTipo(), idUsuario, nome, email);
    }
}
