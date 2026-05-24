package br.com.melivra;

import br.com.melivra.model.Administrador;
import br.com.melivra.model.Anuncio;
import br.com.melivra.model.Avaliacao;
import br.com.melivra.model.Comentario;
import br.com.melivra.model.Estudante;
import br.com.melivra.model.Post;
import br.com.melivra.model.Professor;
import br.com.melivra.repository.SistemaMeLivra;

/**
 * Classe principal — ponto de entrada do sistema Me Livra.
 *
 * Esta versão inicial serve como teste de integração entre as classes:
 * instancia entidades de exemplo, demonstra relacionamentos e imprime
 * os resultados no console.
 *
 * A interface gráfica via JOptionPane será implementada na entrega final.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║         Me Livra — Sistema Universitário      ║");
        System.out.println("║         POO 2026/1 — 1ª Entrega              ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println();

        // =====================================================================
        // 1. Inicializar o sistema
        // =====================================================================
        SistemaMeLivra sistema = new SistemaMeLivra();

        // =====================================================================
        // 2. Cadastrar usuários
        // =====================================================================
        System.out.println("=== USUÁRIOS ===");

        Estudante heitor = new Estudante(
                "Heitor Paranhos Carvalho",
                "heitor@uni.br",
                "senha123",
                "Sistemas de Informação"
        );

        Estudante matheus = new Estudante(
                "Matheus Gomes Rodrigues",
                "matheus@uni.br",
                "senha456",
                "Sistemas de Informação"
        );

        Administrador admin = new Administrador(
                "Ana Claudia Bastos Loureiro Monção",
                "admin@uni.br",
                "adminSenha"
        );

        sistema.cadastrarUsuario(heitor);
        sistema.cadastrarUsuario(matheus);
        sistema.cadastrarUsuario(admin);

        for (var u : sistema.listarUsuarios()) {
            System.out.println(u);
        }

        // =====================================================================
        // 3. Criar posts e demonstrar comentários/curtidas
        // =====================================================================
        System.out.println();
        System.out.println("=== POSTS ===");

        Post post1 = heitor.criarPost(
                "Alguém tem o livro de Estruturas de Dados do Ziviani? "
                + "Procuro para comprar ou emprestar!"
        );
        sistema.cadastrarPost(post1);

        Post post2 = matheus.criarPost(
                "Dica: a biblioteca central tem sala de estudos em grupo "
                + "aberta até as 22h. Recomendo demais!"
        );
        sistema.cadastrarPost(post2);

        // Comentários no post1
        Comentario c1 = new Comentario(matheus, "Tenho! Pode me chamar no e-mail.");
        Comentario c2 = new Comentario(admin, "Boa sorte na busca, pessoal!");
        post1.adicionarComentario(c1);
        post1.adicionarComentario(c2);

        // Curtidas
        post1.curtir();
        post1.curtir();
        post2.curtir();

        for (Post p : sistema.listarPosts()) {
            System.out.println(p);
            for (Comentario c : p.getComentarios()) {
                System.out.println(c);
            }
        }

        // =====================================================================
        // 4. Cadastrar professor e avaliações
        // =====================================================================
        System.out.println();
        System.out.println("=== PROFESSORES E AVALIAÇÕES ===");

        Professor profJoao = new Professor("João Carlos Lima", "Ciências da Computação");
        sistema.cadastrarProfessor(profJoao);

        // Estudantes avaliam o professor
        Avaliacao av1 = heitor.avaliarProfessor(profJoao, 9.0,
                "Ótimo professor! Explica muito bem e é acessível fora de sala.");
        Avaliacao av2 = matheus.avaliarProfessor(profJoao, 7.5,
                "Bom professor, mas as provas são bem difíceis.");

        sistema.cadastrarAvaliacao(av1);
        sistema.cadastrarAvaliacao(av2);

        System.out.println(profJoao);
        System.out.println();
        System.out.println("Avaliações de " + profJoao.getNome() + ":");
        for (Avaliacao av : profJoao.getAvaliacoes()) {
            System.out.println(av);
        }
        System.out.printf("Média calculada: %.2f%n", profJoao.calcularMedia());

        // =====================================================================
        // 5. Publicar anúncios
        // =====================================================================
        System.out.println();
        System.out.println("=== ANÚNCIOS ===");

        Anuncio anuncio1 = heitor.criarAnuncio(
                "Vendo Livro de Cálculo II",
                "Livro Stewart — Cálculo Vol. 2, 7ª edição. Pouco uso, sem marcações.",
                80.00
        );
        Anuncio anuncio2 = matheus.criarAnuncio(
                "Monitoria de POO",
                "Ofereço monitoria de Programação Orientada a Objetos. "
                + "Online ou presencial. Entre em contato!",
                0.0
        );

        sistema.cadastrarAnuncio(anuncio1);
        sistema.cadastrarAnuncio(anuncio2);

        for (Anuncio an : sistema.listarAnuncios()) {
            System.out.println(an);
        }

        // =====================================================================
        // 6. Demonstrar moderação pelo Administrador
        // =====================================================================
        System.out.println();
        System.out.println("=== MODERAÇÃO ===");
        admin.removerConteudo(post2);
        boolean removido = sistema.removerPost(post2.getIdPost());
        System.out.println("Post #" + post2.getIdPost() + " removido do sistema: " + removido);

        // =====================================================================
        // 7. Resumo final
        // =====================================================================
        System.out.println();
        System.out.println("=== RESUMO DO SISTEMA ===");
        System.out.println("Usuários cadastrados : " + sistema.listarUsuarios().size());
        System.out.println("Posts ativos         : " + sistema.listarPosts().size());
        System.out.println("Professores          : " + sistema.listarProfessores().size());
        System.out.println("Avaliações           : " + sistema.listarAvaliacoes().size());
        System.out.println("Anúncios             : " + sistema.listarAnuncios().size());

        System.out.println();
        System.out.println("Sistema inicializado com sucesso. Primeira entrega concluída.");
    }
}
