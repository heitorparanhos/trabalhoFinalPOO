package br.com.melivra.persistence;

import br.com.melivra.exception.PersistenciaException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Camada de persistência genérica baseada em <b>serialização de objetos Java</b>.
 *
 * <p>Grava e lê qualquer objeto {@link Serializable} em um arquivo binário
 * ({@code .dat}). É usada pelo {@code SistemaMeLivra} para salvar um instantâneo
 * completo do estado do sistema (todas as listas de entidades), garantindo que
 * os dados persistam entre execuções (requisito c).</p>
 */
public final class RepositorioArquivo {

    private RepositorioArquivo() {
        // Classe utilitária — não deve ser instanciada.
    }

    /**
     * Grava um objeto serializável em arquivo, criando os diretórios pais se
     * necessário.
     *
     * @param dados   objeto a ser gravado
     * @param caminho caminho do arquivo de destino
     * @throws PersistenciaException se ocorrer falha de E/S
     */
    public static void salvar(Serializable dados, String caminho) throws PersistenciaException {
        try {
            Path path = Paths.get(caminho);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            try (ObjectOutputStream out =
                         new ObjectOutputStream(new FileOutputStream(caminho))) {
                out.writeObject(dados);
            }
        } catch (IOException e) {
            throw new PersistenciaException(
                    "Falha ao gravar os dados no arquivo: " + caminho, e);
        }
    }

    /**
     * Lê um objeto serializado de um arquivo.
     *
     * @param caminho caminho do arquivo de origem
     * @param <T>     tipo esperado do objeto lido
     * @return o objeto desserializado
     * @throws PersistenciaException se o arquivo não puder ser lido ou a classe
     *                               não for encontrada
     */
    @SuppressWarnings("unchecked")
    public static <T> T carregar(String caminho) throws PersistenciaException {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(caminho))) {
            return (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException(
                    "Falha ao ler os dados do arquivo: " + caminho, e);
        }
    }

    /**
     * Indica se o arquivo de persistência já existe.
     *
     * @param caminho caminho do arquivo
     * @return {@code true} se o arquivo existe
     */
    public static boolean existe(String caminho) {
        return Files.exists(Paths.get(caminho));
    }
}
