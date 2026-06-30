package br.com.melivra.exception;

/**
 * Exceção lançada quando a autenticação (login) falha por credenciais
 * inválidas ou e-mail não cadastrado.
 */
public class AutenticacaoException extends MeLivraException {

    private static final long serialVersionUID = 1L;

    /**
     * Cria a exceção com uma mensagem padrão de credenciais inválidas.
     */
    public AutenticacaoException() {
        super("E-mail ou senha inválidos. Verifique as credenciais e tente novamente.");
    }
}
