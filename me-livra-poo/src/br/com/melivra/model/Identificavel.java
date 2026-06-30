package br.com.melivra.model;

import java.io.Serializable;

/**
 * Contrato implementado por toda entidade persistível do sistema Me Livra.
 *
 * <p>A interface cumpre dois papéis justificados pelo domínio:</p>
 * <ul>
 *   <li>Garante que toda entidade exponha um identificador único e sequencial
 *       ({@link #getId()}), usado pelas operações CRUD do repositório;</li>
 *   <li>Garante que toda entidade saiba se rotular ({@link #getTipoEntidade()}),
 *       permitindo que a interface gráfica trate listas heterogêneas de forma
 *       polimórfica.</li>
 * </ul>
 *
 * <p>Estende {@link Serializable} porque todas as entidades precisam ser
 * gravadas e lidas em arquivo (persistência por serialização).</p>
 */
public interface Identificavel extends Serializable {

    /**
     * Retorna o identificador único e sequencial da entidade.
     *
     * @return identificador numérico
     */
    int getId();

    /**
     * Retorna o nome legível do tipo da entidade (ex.: "Post", "Professor").
     *
     * @return rótulo do tipo da entidade
     */
    String getTipoEntidade();
}
