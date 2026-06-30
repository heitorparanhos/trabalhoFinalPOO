package br.com.melivra.exception;

/**
 * Exceção lançada quando um campo obrigatório (nome, e-mail, texto de post, etc.)
 * é informado vazio, em branco ou nulo.
 */
public class CampoObrigatorioException extends MeLivraException {

    private static final long serialVersionUID = 1L;

    /**
     * Cria a exceção indicando qual campo obrigatório não foi preenchido.
     *
     * @param campo nome do campo que estava vazio
     */
    public CampoObrigatorioException(String campo) {
        super("O campo obrigatório \"" + campo + "\" não foi preenchido.");
    }
}
