package br.com.melivra.exception;

/**
 * Exceção de domínio <b>própria</b> lançada quando se tenta registrar uma
 * avaliação de professor com nota fora do intervalo válido [0, 10].
 *
 * <p>Esta é a classe de exceção criada especificamente para uma regra de negócio
 * do problema (RF07 / regra de negócio do caso de uso UC05): a nota de uma
 * avaliação <b>deve</b> estar entre 0 e 10. Em vez de "consertar" silenciosamente
 * o valor, o sistema interrompe a operação e informa o usuário do erro.</p>
 */
public class NotaInvalidaException extends MeLivraException {

    private static final long serialVersionUID = 1L;

    /**
     * Cria a exceção indicando a nota inválida que foi informada.
     *
     * @param nota valor inválido informado pelo usuário
     */
    public NotaInvalidaException(double nota) {
        super(String.format(
                "Nota inválida: %.1f. A nota de uma avaliação deve estar entre 0 e 10.",
                nota));
    }
}
