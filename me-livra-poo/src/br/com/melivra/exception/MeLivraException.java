package br.com.melivra.exception;

/**
 * Exceção base (checked) de todo o domínio do sistema Me Livra.
 *
 * <p>Todas as exceções específicas do negócio herdam desta classe, o que permite
 * que a camada de interface gráfica capture um único tipo ({@code MeLivraException})
 * e exiba a mensagem ao usuário de forma uniforme, sem precisar conhecer cada
 * subtipo de erro.</p>
 */
public class MeLivraException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Cria a exceção com uma mensagem explicativa.
     *
     * @param mensagem descrição do erro de negócio ocorrido
     */
    public MeLivraException(String mensagem) {
        super(mensagem);
    }

    /**
     * Cria a exceção com mensagem e causa original (encadeamento de exceções).
     *
     * @param mensagem descrição do erro de negócio ocorrido
     * @param causa    exceção que originou este erro
     */
    public MeLivraException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
