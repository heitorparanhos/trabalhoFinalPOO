package br.com.melivra;

import br.com.melivra.exception.MeLivraException;
import br.com.melivra.model.Administrador;
import br.com.melivra.model.Avaliacao;
import br.com.melivra.model.Comentario;
import br.com.melivra.model.Estudante;
import br.com.melivra.model.Post;
import br.com.melivra.model.Professor;
import br.com.melivra.service.SistemaMeLivra;
import br.com.melivra.ui.MeLivraGUI;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Ponto de entrada da aplicação Me Livra (entrega final).
 *
 * <p>Inicializa o repositório central, carrega os dados previamente persistidos
 * em arquivo (ou cria um conjunto de dados de demonstração no primeiro uso) e
 * inicia a interface gráfica baseada em {@link JOptionPane}.</p>
 */
public final class Main {

    private Main() {
    }

    /**
     * Inicia a aplicação.
     *
     * @param args não utilizados
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::executar);
    }

    private static void executar() {
        SistemaMeLivra sistema = new SistemaMeLivra();
        try {
            boolean carregou = sistema.carregar();
            if (!carregou) {
                criarDadosDemonstracao(sistema);
                sistema.salvar();
            }
        } catch (MeLivraException e) {
            JOptionPane.showMessageDialog(null,
                    "Falha ao iniciar a persistência: " + e.getMessage()
                            + "\nO sistema continuará apenas em memória.",
                    "Me Livra", JOptionPane.WARNING_MESSAGE);
        }
        new MeLivraGUI(sistema).iniciar();
        System.exit(0);
    }

    /**
     * Cria dados de demonstração para facilitar a apresentação no primeiro uso.
     * Inclui um administrador com credenciais conhecidas:
     * <b>admin@uni.br / admin</b>.
     *
     * @param sistema repositório a ser populado
     * @throws MeLivraException se algum dado de exemplo for inválido
     */
    private static void criarDadosDemonstracao(SistemaMeLivra sistema) throws MeLivraException {
        Administrador admin = new Administrador(
                "Ana Claudia Bastos Loureiro Monção", "admin@uni.br", "admin");
        Estudante heitor = new Estudante(
                "Heitor Paranhos Carvalho", "heitor@uni.br", "123", "Sistemas de Informação");
        Estudante matheus = new Estudante(
                "Matheus Gomes Rodrigues", "matheus@uni.br", "123", "Sistemas de Informação");
        sistema.cadastrarUsuario(admin);
        sistema.cadastrarUsuario(heitor);
        sistema.cadastrarUsuario(matheus);

        Post post1 = heitor.criarPost(
                "Alguém tem o livro de Estruturas de Dados do Ziviani para emprestar?");
        post1.adicionarComentario(new Comentario(matheus, "Tenho! Te chamo no e-mail."));
        post1.curtir();
        post1.curtir();
        Post post2 = matheus.criarPost(
                "Dica: a biblioteca central tem sala de estudos em grupo até as 22h.");
        post2.curtir();
        sistema.cadastrarPost(post1);
        sistema.cadastrarPost(post2);

        Professor prof = new Professor("João Carlos Lima", "Ciências da Computação");
        sistema.cadastrarProfessor(prof);
        Avaliacao av1 = heitor.avaliarProfessor(prof, 9.0,
                "Ótimo professor, explica muito bem.");
        Avaliacao av2 = matheus.avaliarProfessor(prof, 7.5,
                "Bom professor, mas as provas são difíceis.");
        sistema.cadastrarAvaliacao(av1);
        sistema.cadastrarAvaliacao(av2);

        sistema.cadastrarAnuncio(heitor.criarAnuncio(
                "Vendo Livro de Cálculo II",
                "Stewart, Vol. 2, 7ª ed. Pouco uso.", 80.0));
        sistema.cadastrarAnuncio(matheus.criarAnuncio(
                "Monitoria de POO",
                "Ofereço monitoria de Programação Orientada a Objetos.", 0.0));
    }
}
