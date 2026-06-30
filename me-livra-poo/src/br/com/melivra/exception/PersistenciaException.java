package br.com.melivra.exception;

/**
 * Exceção lançada quando ocorre uma falha de leitura ou gravação dos dados
 * em arquivo (entrada/saída ou desserialização).
 *
 * <p>Encapsula as {@link java.io.IOException} e
 * {@link java.lang.ClassNotFoundException} de baixo nível, traduzindo-as para
 * o vocabulário do domínio.</p>
 */
public class PersistenciaException extends MeLivraException {

    private static final long serialVersionUID = 1L;

    /**
     * Cria a exceção com mensagem e causa original.
     *
     * @param mensagem descrição do erro de persistência
     * @param causa    exceção de E/S que originou o problema
     */
    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
