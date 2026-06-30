package br.com.melivra.model;

import br.com.melivra.exception.CampoObrigatorioException;

/**
 * Representa um administrador do sistema Me Livra.
 *
 * <p>Possui todos os atributos de um {@link Usuario} e, adicionalmente, o papel
 * de moderação: pode sinalizar e remover conteúdo impróprio da plataforma
 * (posts, comentários e anúncios). A remoção efetiva é delegada ao repositório
 * central, mantendo o administrador como autor lógico da ação.</p>
 */
public class Administrador extends Usuario {

    private static final long serialVersionUID = 1L;

    /**
     * Cria um novo administrador.
     *
     * @param nome  nome completo
     * @param email e-mail de acesso
     * @param senha senha de acesso
     * @throws CampoObrigatorioException se algum campo obrigatório for vazio
     */
    public Administrador(String nome, String email, String senha)
            throws CampoObrigatorioException {
        super(nome, email, senha);
    }

    /** {@inheritDoc} */
    @Override
    public String getTipo() {
        return "Administrador";
    }

    /**
     * Registra a intenção de moderação sobre um conteúdo. A exclusão real é
     * executada pelo repositório central ({@code SistemaMeLivra}).
     *
     * @param conteudo entidade a ser moderada (Post, Comentario ou Anuncio)
     * @return mensagem de auditoria da ação de moderação
     */
    public String moderar(Identificavel conteudo) {
        return String.format("[MODERAÇÃO] %s removeu %s #%d.",
                getNome(), conteudo.getTipoEntidade(), conteudo.getId());
    }

    @Override
    public String toString() {
        return super.toString() + " | Perfil: Moderador";
    }
}
