package br.com.melivra.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilitários de formatação compartilhados pelas entidades do sistema.
 * Classe utilitária — não deve ser instanciada.
 */
public final class Formatador {

    /** Formato padrão de data/hora usado em toda a aplicação. */
    public static final DateTimeFormatter DATA_HORA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Formatador() {
        // Construtor privado: impede instanciação da classe utilitária.
    }

    /**
     * Formata uma data/hora no padrão {@code dd/MM/yyyy HH:mm}.
     *
     * @param dataHora data/hora a formatar (pode ser nula)
     * @return string formatada ou "-" se a entrada for nula
     */
    public static String formatar(LocalDateTime dataHora) {
        return dataHora == null ? "-" : dataHora.format(DATA_HORA);
    }

    /**
     * Formata um valor monetário em reais; valor zero é exibido como
     * "A combinar".
     *
     * @param preco valor a formatar
     * @return string formatada (ex.: "R$ 80,00" ou "A combinar")
     */
    public static String formatarPreco(double preco) {
        return preco == 0.0 ? "A combinar" : String.format("R$ %.2f", preco);
    }
}
