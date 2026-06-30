package br.com.melivra.test;

import br.com.melivra.exception.AutenticacaoException;
import br.com.melivra.exception.CampoObrigatorioException;
import br.com.melivra.exception.EntidadeNaoEncontradaException;
import br.com.melivra.exception.MeLivraException;
import br.com.melivra.exception.NotaInvalidaException;
import br.com.melivra.model.Administrador;
import br.com.melivra.model.Anuncio;
import br.com.melivra.model.Avaliacao;
import br.com.melivra.model.Estudante;
import br.com.melivra.model.Post;
import br.com.melivra.model.Professor;
import br.com.melivra.model.Usuario;
import br.com.melivra.service.SistemaMeLivra;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Casos de teste funcionais do sistema Me Livra (requisito g).
 *
 * <p>Implementado como um pequeno <i>runner</i> próprio (sem dependências
 * externas) para que possa ser executado apenas com {@code java}, sem exigir
 * JUnit no <i>classpath</i>. Cada cenário valida um fluxo principal do sistema e
 * é resumido ao final como PASSOU/FALHOU. O programa encerra com código de saída
 * diferente de zero se algum teste falhar.</p>
 *
 * <p>Os cenários cobertos correspondem aos descritos em
 * {@code docs/06_casos_de_teste.md}.</p>
 */
public final class TesteFuncional {

    private static int passou = 0;
    private static int falhou = 0;

    private TesteFuncional() {
    }

    /**
     * Executa todos os cenários de teste.
     *
     * @param args não utilizados
     * @throws Exception se a preparação do ambiente de teste falhar
     */
    public static void main(String[] args) throws Exception {
        System.out.println("==== TESTES FUNCIONAIS — ME LIVRA ====\n");

        ct01_idsSequenciais();
        ct02_crudPostCompleto();
        ct03_notaInvalidaLancaExcecao();
        ct04_campoObrigatorioLancaExcecao();
        ct05_mediaProfessor();
        ct06_autenticacao();
        ct07_entidadeNaoEncontrada();
        ct08_persistenciaEntreExecucoes();

        System.out.println("\n==== RESUMO ====");
        System.out.println("Passou: " + passou + " | Falhou: " + falhou);
        if (falhou > 0) {
            System.exit(1);
        }
    }

    /** CT01 — IDs automáticos e sequenciais via atributo estático. */
    private static void ct01_idsSequenciais() {
        try {
            Estudante a = new Estudante("A", "a@x.br", "1", "SI");
            Estudante b = new Estudante("B", "b@x.br", "1", "SI");
            verificar("CT01", b.getIdUsuario() == a.getIdUsuario() + 1,
                    "IDs de usuário devem ser sequenciais");
        } catch (Exception e) {
            falha("CT01", e);
        }
    }

    /** CT02 — CRUD completo de Post (incluir, consultar, alterar, listar, excluir). */
    private static void ct02_crudPostCompleto() {
        try {
            SistemaMeLivra s = new SistemaMeLivra(arquivoTemp());
            Estudante autor = new Estudante("Autor", "autor@x.br", "1", "SI");
            s.cadastrarUsuario(autor);
            Post p = autor.criarPost("texto original");
            s.cadastrarPost(p);
            int id = p.getIdPost();

            boolean incluido = s.consultarPost(id) != null;
            s.atualizarPost(id, "texto editado");
            boolean alterado = s.consultarPost(id).getTexto().equals("texto editado");
            boolean listado = s.listarPosts().size() == 1;
            s.removerPost(id);
            boolean excluido = s.listarPosts().isEmpty();

            verificar("CT02", incluido && alterado && listado && excluido,
                    "CRUD completo de Post deve funcionar");
        } catch (Exception e) {
            falha("CT02", e);
        }
    }

    /** CT03 — Nota fora de [0,10] lança a exceção própria NotaInvalidaException. */
    private static void ct03_notaInvalidaLancaExcecao() {
        try {
            Estudante est = new Estudante("E", "e@x.br", "1", "SI");
            Professor prof = new Professor("P", "Dept");
            est.avaliarProfessor(prof, 11.0, "comentário");
            falha("CT03", new IllegalStateException("Deveria ter lançado NotaInvalidaException"));
        } catch (NotaInvalidaException e) {
            verificar("CT03", true, "Nota 11 deve lançar NotaInvalidaException");
        } catch (Exception e) {
            falha("CT03", e);
        }
    }

    /** CT04 — Campo obrigatório vazio lança CampoObrigatorioException. */
    private static void ct04_campoObrigatorioLancaExcecao() {
        try {
            new Estudante("", "e@x.br", "1", "SI");
            falha("CT04", new IllegalStateException("Deveria ter lançado CampoObrigatorioException"));
        } catch (CampoObrigatorioException e) {
            verificar("CT04", true, "Nome vazio deve lançar CampoObrigatorioException");
        } catch (Exception e) {
            falha("CT04", e);
        }
    }

    /** CT05 — Cálculo da média de avaliações do professor. */
    private static void ct05_mediaProfessor() {
        try {
            Estudante est = new Estudante("E", "e@x.br", "1", "SI");
            Professor prof = new Professor("P", "Dept");
            est.avaliarProfessor(prof, 8.0, "ok");
            est.avaliarProfessor(prof, 6.0, "ok");
            verificar("CT05", Math.abs(prof.calcularMedia() - 7.0) < 0.0001,
                    "Média de 8 e 6 deve ser 7.0");
        } catch (Exception e) {
            falha("CT05", e);
        }
    }

    /** CT06 — Autenticação por e-mail/senha. */
    private static void ct06_autenticacao() {
        try {
            SistemaMeLivra s = new SistemaMeLivra(arquivoTemp());
            s.cadastrarUsuario(new Administrador("Admin", "adm@x.br", "segredo"));
            Usuario logado = s.autenticar("adm@x.br", "segredo");
            boolean ok = logado != null;
            boolean rejeita = false;
            try {
                s.autenticar("adm@x.br", "errada");
            } catch (AutenticacaoException e) {
                rejeita = true;
            }
            verificar("CT06", ok && rejeita,
                    "Login válido aceita e senha errada é rejeitada");
        } catch (Exception e) {
            falha("CT06", e);
        }
    }

    /** CT07 — Consulta de entidade inexistente lança EntidadeNaoEncontradaException. */
    private static void ct07_entidadeNaoEncontrada() {
        try {
            SistemaMeLivra s = new SistemaMeLivra(arquivoTemp());
            s.consultarPost(9999);
            falha("CT07", new IllegalStateException("Deveria ter lançado exceção"));
        } catch (EntidadeNaoEncontradaException e) {
            verificar("CT07", true, "Consulta inexistente lança EntidadeNaoEncontradaException");
        } catch (Exception e) {
            falha("CT07", e);
        }
    }

    /** CT08 — Persistência: dados gravados são recuperados em nova instância. */
    private static void ct08_persistenciaEntreExecucoes() {
        try {
            String arq = arquivoTemp();
            SistemaMeLivra s1 = new SistemaMeLivra(arq);
            Estudante autor = new Estudante("Persistente", "p@x.br", "1", "SI");
            s1.cadastrarUsuario(autor);
            s1.cadastrarAnuncio(autor.criarAnuncio("Título", "Descrição", 10.0));
            s1.salvar();

            SistemaMeLivra s2 = new SistemaMeLivra(arq);
            boolean carregou = s2.carregar();
            boolean dados = s2.listarUsuarios().size() == 1 && s2.listarAnuncios().size() == 1;
            verificar("CT08", carregou && dados,
                    "Dados devem persistir e ser recarregados de arquivo");
        } catch (Exception e) {
            falha("CT08", e);
        }
    }

    // ---- infraestrutura de teste ----

    private static String arquivoTemp() throws Exception {
        Path dir = Files.createTempDirectory("melivra-test");
        return dir.resolve("dados.dat").toString();
    }

    private static void verificar(String id, boolean condicao, String descricao) {
        if (condicao) {
            passou++;
            System.out.println("[PASSOU] " + id + " — " + descricao);
        } else {
            falhou++;
            System.out.println("[FALHOU] " + id + " — " + descricao);
        }
    }

    private static void falha(String id, Exception e) {
        falhou++;
        System.out.println("[FALHOU] " + id + " — exceção inesperada: " + e);
    }
}
