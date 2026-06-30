package br.com.melivra.ui;

import br.com.melivra.exception.MeLivraException;
import br.com.melivra.model.Administrador;
import br.com.melivra.model.Anuncio;
import br.com.melivra.model.Avaliacao;
import br.com.melivra.model.Comentario;
import br.com.melivra.model.Estudante;
import br.com.melivra.model.Post;
import br.com.melivra.model.Professor;
import br.com.melivra.model.Usuario;
import br.com.melivra.service.SistemaMeLivra;

import javax.swing.JOptionPane;
import java.util.List;

/**
 * Interface gráfica do sistema Me Livra, construída com {@link JOptionPane}
 * (requisito f).
 *
 * <p>Apresenta menus de diálogo que permitem demonstrar, durante a
 * apresentação, todos os fluxos CRUD (Incluir, Alterar, Excluir, Consultar e
 * Listar) das entidades principais, além de login, interações sociais
 * (comentar/curtir), avaliação de professores e moderação de conteúdo.</p>
 *
 * <p>Toda exceção de negócio ({@link MeLivraException}) é capturada e exibida ao
 * usuário em caixas de diálogo de erro, sem encerrar a aplicação (requisito h).
 * Após cada operação que altera dados, o estado é gravado em arquivo
 * automaticamente (requisito c).</p>
 */
public class MeLivraGUI {

    private static final String TITULO = "Me Livra — Rede Social Universitária";

    private final SistemaMeLivra sistema;
    private Usuario usuarioLogado;

    /**
     * Cria a interface gráfica vinculada a um sistema já inicializado.
     *
     * @param sistema repositório/serviço central do Me Livra
     */
    public MeLivraGUI(SistemaMeLivra sistema) {
        this.sistema = sistema;
    }

    /**
     * Inicia o laço principal da aplicação gráfica: tela de boas-vindas,
     * autenticação e menu principal.
     */
    public void iniciar() {
        info("Bem-vindo(a) ao Me Livra!\n\n"
                + "Rede social universitária para posts, avaliação de\n"
                + "professores e anúncios acadêmicos.");
        boolean executando = true;
        while (executando) {
            if (usuarioLogado == null) {
                if (!telaAcesso()) {
                    executando = false;
                }
            } else {
                executando = menuPrincipal();
            }
        }
        salvarSilencioso();
        info("Dados salvos. Até logo!");
    }

    // =========================================================================
    // Acesso (login / cadastro)
    // =========================================================================

    private boolean telaAcesso() {
        String opcao = JOptionPane.showInputDialog(null,
                "=== ACESSO AO ME LIVRA ===\n\n"
                        + "1 - Entrar (login)\n"
                        + "2 - Cadastrar novo usuário\n"
                        + "0 - Sair do sistema\n\n"
                        + "Escolha uma opção:",
                TITULO, JOptionPane.QUESTION_MESSAGE);
        if (opcao == null || opcao.trim().equals("0")) {
            return false;
        }
        switch (opcao.trim()) {
            case "1" -> efetuarLogin();
            case "2" -> cadastrarUsuario();
            default -> erro("Opção inválida.");
        }
        return true;
    }

    private void efetuarLogin() {
        try {
            String email = lerTexto("E-mail:");
            if (email == null) {
                return;
            }
            String senha = lerTexto("Senha:");
            if (senha == null) {
                return;
            }
            usuarioLogado = sistema.autenticar(email, senha);
            info("Login efetuado com sucesso!\nOlá, " + usuarioLogado.getNome()
                    + " (" + usuarioLogado.getTipo() + ").");
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    // =========================================================================
    // Menu principal
    // =========================================================================

    private boolean menuPrincipal() {
        boolean admin = usuarioLogado instanceof Administrador;
        StringBuilder menu = new StringBuilder();
        menu.append("=== MENU PRINCIPAL ===\n");
        menu.append("Usuário: ").append(usuarioLogado.getNome())
                .append(" (").append(usuarioLogado.getTipo()).append(")\n\n");
        menu.append("1 - Posts (CRUD)\n");
        menu.append("2 - Comentar / Curtir post\n");
        menu.append("3 - Professores (CRUD)\n");
        menu.append("4 - Avaliar professor / Ver avaliações\n");
        menu.append("5 - Anúncios (CRUD)\n");
        menu.append("6 - Usuários (CRUD)\n");
        if (admin) {
            menu.append("7 - Moderar conteúdo (Administrador)\n");
        }
        menu.append("8 - Salvar dados agora\n");
        menu.append("9 - Trocar de usuário (logout)\n");
        menu.append("0 - Sair do sistema\n\nEscolha uma opção:");

        String opcao = JOptionPane.showInputDialog(null, menu.toString(),
                TITULO, JOptionPane.QUESTION_MESSAGE);
        if (opcao == null) {
            return true;
        }
        switch (opcao.trim()) {
            case "1" -> menuPosts();
            case "2" -> interagirPost();
            case "3" -> menuProfessores();
            case "4" -> menuAvaliacoes();
            case "5" -> menuAnuncios();
            case "6" -> menuUsuarios();
            case "7" -> {
                if (admin) {
                    moderarConteudo();
                } else {
                    erro("Opção inválida.");
                }
            }
            case "8" -> {
                salvarSilencioso();
                info("Dados salvos com sucesso.");
            }
            case "9" -> {
                usuarioLogado = null;
            }
            case "0" -> {
                return false;
            }
            default -> erro("Opção inválida.");
        }
        return true;
    }

    // =========================================================================
    // CRUD — Usuário
    // =========================================================================

    private void menuUsuarios() {
        String op = subMenu("USUÁRIOS");
        if (op == null) {
            return;
        }
        switch (op) {
            case "1" -> cadastrarUsuario();
            case "2" -> alterarUsuario();
            case "3" -> excluirUsuario();
            case "4" -> consultarUsuario();
            case "5" -> listarUsuarios();
            default -> { }
        }
    }

    private void cadastrarUsuario() {
        try {
            String tipo = JOptionPane.showInputDialog(null,
                    "Tipo de usuário:\n1 - Estudante\n2 - Administrador",
                    TITULO, JOptionPane.QUESTION_MESSAGE);
            if (tipo == null) {
                return;
            }
            String nome = lerTexto("Nome completo:");
            if (nome == null) {
                return;
            }
            String email = lerTexto("E-mail:");
            if (email == null) {
                return;
            }
            String senha = lerTexto("Senha:");
            if (senha == null) {
                return;
            }
            Usuario novo;
            if (tipo.trim().equals("2")) {
                novo = new Administrador(nome, email, senha);
            } else {
                String curso = lerTexto("Curso:");
                if (curso == null) {
                    return;
                }
                novo = new Estudante(nome, email, senha, curso);
            }
            sistema.cadastrarUsuario(novo);
            salvarSilencioso();
            info("Usuário cadastrado com sucesso!\n\n" + novo);
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void alterarUsuario() {
        try {
            Integer id = lerInteiro("ID do usuário a alterar:");
            if (id == null) {
                return;
            }
            Usuario u = sistema.consultarUsuario(id);
            String nome = lerTexto("Novo nome (atual: " + u.getNome() + "):");
            if (nome == null) {
                return;
            }
            u.setNome(nome);
            String email = lerTexto("Novo e-mail (atual: " + u.getEmail() + "):");
            if (email == null) {
                return;
            }
            u.setEmail(email);
            if (u instanceof Estudante est) {
                String curso = lerTexto("Novo curso (atual: " + est.getCurso() + "):");
                if (curso != null && !curso.isBlank()) {
                    est.setCurso(curso);
                }
            }
            salvarSilencioso();
            info("Usuário atualizado!\n\n" + u);
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void excluirUsuario() {
        try {
            Integer id = lerInteiro("ID do usuário a excluir:");
            if (id == null) {
                return;
            }
            Usuario u = sistema.consultarUsuario(id);
            if (confirmar("Confirma a exclusão de:\n" + u + " ?")) {
                sistema.removerUsuario(id);
                if (usuarioLogado != null && usuarioLogado.getIdUsuario() == id) {
                    usuarioLogado = null;
                }
                salvarSilencioso();
                info("Usuário excluído.");
            }
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void consultarUsuario() {
        try {
            Integer id = lerInteiro("ID do usuário:");
            if (id == null) {
                return;
            }
            info(sistema.consultarUsuario(id).toString());
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void listarUsuarios() {
        List<Usuario> lista = sistema.listarUsuarios();
        if (lista.isEmpty()) {
            info("Nenhum usuário cadastrado.");
            return;
        }
        StringBuilder sb = new StringBuilder("=== USUÁRIOS (" + lista.size() + ") ===\n\n");
        for (Usuario u : lista) {
            sb.append(u).append("\n");
        }
        info(sb.toString());
    }

    // =========================================================================
    // CRUD — Post
    // =========================================================================

    private void menuPosts() {
        String op = subMenu("POSTS");
        if (op == null) {
            return;
        }
        switch (op) {
            case "1" -> cadastrarPost();
            case "2" -> alterarPost();
            case "3" -> excluirPost();
            case "4" -> consultarPost();
            case "5" -> listarPosts();
            default -> { }
        }
    }

    private void cadastrarPost() {
        try {
            String texto = lerTexto("Conteúdo do post:");
            if (texto == null) {
                return;
            }
            Post post;
            if (usuarioLogado instanceof Estudante est) {
                post = est.criarPost(texto);
            } else {
                post = new Post(usuarioLogado, texto);
            }
            sistema.cadastrarPost(post);
            salvarSilencioso();
            info("Post publicado!\n\n" + post);
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void alterarPost() {
        try {
            Integer id = lerInteiro("ID do post a editar:");
            if (id == null) {
                return;
            }
            Post p = sistema.consultarPost(id);
            String texto = lerTexto("Novo texto (atual):\n\"" + p.getTexto() + "\"");
            if (texto == null) {
                return;
            }
            sistema.atualizarPost(id, texto);
            salvarSilencioso();
            info("Post atualizado!\n\n" + sistema.consultarPost(id));
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void excluirPost() {
        try {
            Integer id = lerInteiro("ID do post a excluir:");
            if (id == null) {
                return;
            }
            Post p = sistema.consultarPost(id);
            if (confirmar("Confirma a exclusão do post:\n" + p + " ?")) {
                sistema.removerPost(id);
                salvarSilencioso();
                info("Post excluído.");
            }
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void consultarPost() {
        try {
            Integer id = lerInteiro("ID do post:");
            if (id == null) {
                return;
            }
            Post p = sistema.consultarPost(id);
            StringBuilder sb = new StringBuilder(p.toString());
            if (!p.getComentarios().isEmpty()) {
                sb.append("\n\nComentários:");
                for (Comentario c : p.getComentarios()) {
                    sb.append("\n").append(c);
                }
            }
            info(sb.toString());
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void listarPosts() {
        List<Post> lista = sistema.listarPosts();
        if (lista.isEmpty()) {
            info("Nenhum post publicado.");
            return;
        }
        StringBuilder sb = new StringBuilder("=== POSTS (" + lista.size() + ") ===\n\n");
        for (Post p : lista) {
            sb.append(p).append("\n\n");
        }
        info(sb.toString());
    }

    // =========================================================================
    // Interação social — comentar / curtir
    // =========================================================================

    private void interagirPost() {
        try {
            Integer id = lerInteiro("ID do post para interagir:");
            if (id == null) {
                return;
            }
            Post p = sistema.consultarPost(id);
            String op = JOptionPane.showInputDialog(null,
                    p + "\n\n1 - Comentar\n2 - Curtir\n\nEscolha:",
                    TITULO, JOptionPane.QUESTION_MESSAGE);
            if (op == null) {
                return;
            }
            switch (op.trim()) {
                case "1" -> {
                    String texto = lerTexto("Seu comentário:");
                    if (texto == null) {
                        return;
                    }
                    p.adicionarComentario(new Comentario(usuarioLogado, texto));
                    salvarSilencioso();
                    info("Comentário adicionado!");
                }
                case "2" -> {
                    p.curtir();
                    salvarSilencioso();
                    info("Você curtiu o post. Total de curtidas: " + p.getCurtidas());
                }
                default -> erro("Opção inválida.");
            }
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    // =========================================================================
    // CRUD — Professor
    // =========================================================================

    private void menuProfessores() {
        String op = subMenu("PROFESSORES");
        if (op == null) {
            return;
        }
        switch (op) {
            case "1" -> cadastrarProfessor();
            case "2" -> alterarProfessor();
            case "3" -> excluirProfessor();
            case "4" -> consultarProfessor();
            case "5" -> listarProfessores();
            default -> { }
        }
    }

    private void cadastrarProfessor() {
        try {
            String nome = lerTexto("Nome do professor:");
            if (nome == null) {
                return;
            }
            String dep = lerTexto("Departamento:");
            if (dep == null) {
                return;
            }
            Professor prof = new Professor(nome, dep);
            sistema.cadastrarProfessor(prof);
            salvarSilencioso();
            info("Professor cadastrado!\n\n" + prof);
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void alterarProfessor() {
        try {
            Integer id = lerInteiro("ID do professor a alterar:");
            if (id == null) {
                return;
            }
            Professor prof = sistema.consultarProfessor(id);
            String nome = lerTexto("Novo nome (atual: " + prof.getNome() + "):");
            if (nome == null) {
                return;
            }
            String dep = lerTexto("Novo departamento (atual: " + prof.getDepartamento() + "):");
            if (dep == null) {
                return;
            }
            sistema.atualizarProfessor(id, nome, dep);
            salvarSilencioso();
            info("Professor atualizado!\n\n" + sistema.consultarProfessor(id));
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void excluirProfessor() {
        try {
            Integer id = lerInteiro("ID do professor a excluir:");
            if (id == null) {
                return;
            }
            Professor prof = sistema.consultarProfessor(id);
            if (confirmar("Confirma a exclusão de:\n" + prof + " ?")) {
                sistema.removerProfessor(id);
                salvarSilencioso();
                info("Professor excluído.");
            }
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void consultarProfessor() {
        try {
            Integer id = lerInteiro("ID do professor:");
            if (id == null) {
                return;
            }
            Professor prof = sistema.consultarProfessor(id);
            StringBuilder sb = new StringBuilder(prof.toString());
            if (!prof.getAvaliacoes().isEmpty()) {
                sb.append("\n\nAvaliações:");
                for (Avaliacao av : prof.getAvaliacoes()) {
                    sb.append("\n").append(av);
                }
            }
            info(sb.toString());
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void listarProfessores() {
        List<Professor> lista = sistema.listarProfessores();
        if (lista.isEmpty()) {
            info("Nenhum professor cadastrado.");
            return;
        }
        StringBuilder sb = new StringBuilder("=== PROFESSORES (" + lista.size() + ") ===\n\n");
        for (Professor p : lista) {
            sb.append(p).append("\n");
        }
        info(sb.toString());
    }

    // =========================================================================
    // Avaliações
    // =========================================================================

    private void menuAvaliacoes() {
        String op = JOptionPane.showInputDialog(null,
                "=== AVALIAÇÕES ===\n\n"
                        + "1 - Avaliar um professor\n"
                        + "2 - Listar todas as avaliações\n"
                        + "3 - Excluir uma avaliação\n"
                        + "0 - Voltar\n\nEscolha:",
                TITULO, JOptionPane.QUESTION_MESSAGE);
        if (op == null) {
            return;
        }
        switch (op.trim()) {
            case "1" -> avaliarProfessor();
            case "2" -> listarAvaliacoes();
            case "3" -> excluirAvaliacao();
            default -> { }
        }
    }

    private void avaliarProfessor() {
        try {
            if (!(usuarioLogado instanceof Estudante est)) {
                erro("Apenas estudantes podem avaliar professores.");
                return;
            }
            Integer id = lerInteiro("ID do professor a avaliar:");
            if (id == null) {
                return;
            }
            Professor prof = sistema.consultarProfessor(id);
            Double nota = lerDouble("Nota para " + prof.getNome() + " (0 a 10):");
            if (nota == null) {
                return;
            }
            String coment = lerTexto("Comentário sobre o professor:");
            if (coment == null) {
                return;
            }
            Avaliacao av = est.avaliarProfessor(prof, nota, coment);
            sistema.cadastrarAvaliacao(av);
            salvarSilencioso();
            info("Avaliação registrada!\n\n" + av
                    + "\n\nNova média do professor: "
                    + String.format("%.1f", prof.calcularMedia()));
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void listarAvaliacoes() {
        List<Avaliacao> lista = sistema.listarAvaliacoes();
        if (lista.isEmpty()) {
            info("Nenhuma avaliação registrada.");
            return;
        }
        StringBuilder sb = new StringBuilder("=== AVALIAÇÕES (" + lista.size() + ") ===\n\n");
        for (Avaliacao av : lista) {
            sb.append(av).append("\n\n");
        }
        info(sb.toString());
    }

    private void excluirAvaliacao() {
        try {
            Integer id = lerInteiro("ID da avaliação a excluir:");
            if (id == null) {
                return;
            }
            Avaliacao av = sistema.consultarAvaliacao(id);
            if (confirmar("Confirma a exclusão de:\n" + av + " ?")) {
                sistema.removerAvaliacao(id);
                salvarSilencioso();
                info("Avaliação excluída.");
            }
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    // =========================================================================
    // CRUD — Anúncio
    // =========================================================================

    private void menuAnuncios() {
        String op = subMenu("ANÚNCIOS");
        if (op == null) {
            return;
        }
        switch (op) {
            case "1" -> cadastrarAnuncio();
            case "2" -> alterarAnuncio();
            case "3" -> excluirAnuncio();
            case "4" -> consultarAnuncio();
            case "5" -> listarAnuncios();
            default -> { }
        }
    }

    private void cadastrarAnuncio() {
        try {
            String titulo = lerTexto("Título do anúncio:");
            if (titulo == null) {
                return;
            }
            String desc = lerTexto("Descrição:");
            if (desc == null) {
                return;
            }
            Double preco = lerDouble("Preço (0 para 'a combinar'):");
            if (preco == null) {
                return;
            }
            Anuncio anuncio;
            if (usuarioLogado instanceof Estudante est) {
                anuncio = est.criarAnuncio(titulo, desc, preco);
            } else {
                anuncio = new Anuncio(usuarioLogado, titulo, desc, preco);
            }
            sistema.cadastrarAnuncio(anuncio);
            salvarSilencioso();
            info("Anúncio publicado!\n\n" + anuncio);
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void alterarAnuncio() {
        try {
            Integer id = lerInteiro("ID do anúncio a alterar:");
            if (id == null) {
                return;
            }
            Anuncio a = sistema.consultarAnuncio(id);
            String titulo = lerTexto("Novo título (atual: " + a.getTitulo() + "):");
            if (titulo == null) {
                return;
            }
            String desc = lerTexto("Nova descrição (atual: " + a.getDescricao() + "):");
            if (desc == null) {
                return;
            }
            Double preco = lerDouble("Novo preço (atual: " + a.getPreco() + "):");
            if (preco == null) {
                return;
            }
            sistema.atualizarAnuncio(id, titulo, desc, preco);
            salvarSilencioso();
            info("Anúncio atualizado!\n\n" + sistema.consultarAnuncio(id));
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void excluirAnuncio() {
        try {
            Integer id = lerInteiro("ID do anúncio a excluir:");
            if (id == null) {
                return;
            }
            Anuncio a = sistema.consultarAnuncio(id);
            if (confirmar("Confirma a exclusão de:\n" + a + " ?")) {
                sistema.removerAnuncio(id);
                salvarSilencioso();
                info("Anúncio excluído.");
            }
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void consultarAnuncio() {
        try {
            Integer id = lerInteiro("ID do anúncio:");
            if (id == null) {
                return;
            }
            info(sistema.consultarAnuncio(id).toString());
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    private void listarAnuncios() {
        List<Anuncio> lista = sistema.listarAnuncios();
        if (lista.isEmpty()) {
            info("Nenhum anúncio publicado.");
            return;
        }
        StringBuilder sb = new StringBuilder("=== ANÚNCIOS (" + lista.size() + ") ===\n\n");
        for (Anuncio a : lista) {
            sb.append(a).append("\n\n");
        }
        info(sb.toString());
    }

    // =========================================================================
    // Moderação (Administrador)
    // =========================================================================

    private void moderarConteudo() {
        try {
            Administrador admin = (Administrador) usuarioLogado;
            String op = JOptionPane.showInputDialog(null,
                    "=== MODERAÇÃO ===\n\n"
                            + "1 - Remover post\n"
                            + "2 - Remover comentário de um post\n"
                            + "3 - Remover anúncio\n"
                            + "0 - Voltar\n\nEscolha:",
                    TITULO, JOptionPane.QUESTION_MESSAGE);
            if (op == null) {
                return;
            }
            switch (op.trim()) {
                case "1" -> {
                    Integer id = lerInteiro("ID do post a remover:");
                    if (id == null) {
                        return;
                    }
                    Post p = sistema.consultarPost(id);
                    String log = admin.moderar(p);
                    sistema.removerPost(id);
                    salvarSilencioso();
                    info(log);
                }
                case "2" -> {
                    Integer idPost = lerInteiro("ID do post:");
                    if (idPost == null) {
                        return;
                    }
                    Post p = sistema.consultarPost(idPost);
                    Integer idCom = lerInteiro("ID do comentário a remover:");
                    if (idCom == null) {
                        return;
                    }
                    if (p.removerComentario(idCom)) {
                        salvarSilencioso();
                        info("Comentário #" + idCom + " removido do post #" + idPost + ".");
                    } else {
                        erro("Comentário não encontrado nesse post.");
                    }
                }
                case "3" -> {
                    Integer id = lerInteiro("ID do anúncio a remover:");
                    if (id == null) {
                        return;
                    }
                    Anuncio a = sistema.consultarAnuncio(id);
                    String log = admin.moderar(a);
                    sistema.removerAnuncio(id);
                    salvarSilencioso();
                    info(log);
                }
                default -> { }
            }
        } catch (MeLivraException e) {
            erro(e.getMessage());
        }
    }

    // =========================================================================
    // Auxiliares de interface
    // =========================================================================

    /**
     * Exibe um submenu CRUD padrão e devolve a opção escolhida (ou null se o
     * usuário cancelar/voltar).
     */
    private String subMenu(String entidade) {
        String op = JOptionPane.showInputDialog(null,
                "=== " + entidade + " ===\n\n"
                        + "1 - Incluir\n"
                        + "2 - Alterar\n"
                        + "3 - Excluir\n"
                        + "4 - Consultar\n"
                        + "5 - Listar\n"
                        + "0 - Voltar\n\nEscolha:",
                TITULO, JOptionPane.QUESTION_MESSAGE);
        if (op == null || op.trim().equals("0")) {
            return null;
        }
        return op.trim();
    }

    private String lerTexto(String prompt) {
        return JOptionPane.showInputDialog(null, prompt, TITULO,
                JOptionPane.QUESTION_MESSAGE);
    }

    private Integer lerInteiro(String prompt) {
        String v = lerTexto(prompt);
        if (v == null) {
            return null;
        }
        try {
            return Integer.parseInt(v.trim());
        } catch (NumberFormatException e) {
            erro("Valor inválido: informe um número inteiro.");
            return null;
        }
    }

    private Double lerDouble(String prompt) {
        String v = lerTexto(prompt);
        if (v == null) {
            return null;
        }
        try {
            return Double.parseDouble(v.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            erro("Valor inválido: informe um número.");
            return null;
        }
    }

    private boolean confirmar(String mensagem) {
        return JOptionPane.showConfirmDialog(null, mensagem, TITULO,
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void info(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, TITULO,
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void erro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, TITULO,
                JOptionPane.ERROR_MESSAGE);
    }

    private void salvarSilencioso() {
        try {
            sistema.salvar();
        } catch (MeLivraException e) {
            erro("Não foi possível salvar os dados: " + e.getMessage());
        }
    }
}
