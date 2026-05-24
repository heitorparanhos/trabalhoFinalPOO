package br.com.melivra.model;

/**
 * Representa um administrador do sistema Me Livra.
 * Possui todos os atributos de um Usuario e, adicionalmente,
 * pode remover conteúdo impróprio da plataforma.
 */
public class Administrador extends Usuario {

    /**
     * Cria um novo administrador.
     *
     * @param nome  nome completo
     * @param email e-mail de acesso
     * @param senha senha de acesso
     */
    public Administrador(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    // -------------------------------------------------------------------------
    // Implementação do método abstrato
    // -------------------------------------------------------------------------

    @Override
    public String getTipo() {
        return "Administrador";
    }

    // -------------------------------------------------------------------------
    // Métodos de negócio
    // -------------------------------------------------------------------------

    /**
     * Remove um conteúdo impróprio da plataforma.
     * Implementação simplificada: a remoção efetiva é delegada ao SistemaMeLivra.
     * Este método registra a intenção de moderação.
     *
     * @param conteudo objeto a ser removido (Post, Comentario ou Anuncio)
     */
    public void removerConteudo(Object conteudo) {
        // Implementação simplificada — a remoção real ocorre via SistemaMeLivra
        System.out.println("[MODERAÇÃO] Administrador '" + getNome()
                + "' sinalizou remoção de conteúdo: " + conteudo);
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + " | Perfil: Moderador";
    }
}
