package br.com.melivra.exception;

/**
 * Exceção lançada quando uma operação CRUD (consulta, alteração ou exclusão)
 * referencia uma entidade que não existe no sistema.
 */
public class EntidadeNaoEncontradaException extends MeLivraException {

    private static final long serialVersionUID = 1L;

    /**
     * Cria a exceção descrevendo a entidade e o identificador não encontrados.
     *
     * @param entidade nome da entidade (ex.: "Post", "Professor")
     * @param id       identificador procurado
     */
    public EntidadeNaoEncontradaException(String entidade, int id) {
        super(entidade + " com ID " + id + " não foi encontrado(a) no sistema.");
    }
}
