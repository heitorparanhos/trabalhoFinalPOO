# Checklist de Requisitos — Entrega Final

> Mapeamento de cada requisito do enunciado para o(s) arquivo(s)/classe(s)/
> método(s) que o atende(m). Caminhos relativos a `me-livra-poo/`.

## Itens de Modelagem

| Item | Artefato | Onde |
|------|----------|------|
| Análise do tema (Passo 0) | Análise do `melivra` e mapeamento | `docs/ANALISE_MELIVRA.md` |
| 1. Problema e Escopo | Definição do problema/escopo | `docs/01-problema-escopo.md` |
| 2. Lista de Requisitos | Requisitos funcionais e não funcionais | `docs/02-requisitos.md` |
| 3. Casos de Uso | Atores, tabela e diagrama (Mermaid) | `docs/03-casos-de-uso.md` |
| 4. Classes Conceituais (≥4) | 8 classes conceituais + relacionamentos | `docs/04-classes-conceituais.md` |
| 5. Diagrama de Classes | UML em Mermaid (conceitual + arquitetura final) | `docs/05-diagrama-de-classes.md` |

## Itens de Implementação (a–k)

### (a) Backend — classes conforme o diagrama
- **Onde:** `src/br/com/melivra/model/` — `Usuario`, `Estudante`, `Administrador`,
  `Post`, `Comentario`, `Professor`, `Avaliacao`, `Anuncio` (atributos,
  construtores, métodos de negócio como `criarPost`, `avaliarProfessor`,
  `calcularMedia`, `curtir`).

### (b) Identificadores automáticos e sequenciais (atributo estático)
- **Onde:** atributo `private static int proximoId` incrementado no construtor de
  cada entidade.
  - `model/Usuario.java` (linha do construtor: `this.idUsuario = proximoId++`)
  - `model/Post.java`, `model/Comentario.java`, `model/Professor.java`,
    `model/Avaliacao.java`, `model/Anuncio.java`.
- Contadores são salvos/restaurados na persistência via `getProximoId()` /
  `setProximoId()` (ver `service/SistemaMeLivra.java` → `EstadoSistema`).

### (c) Persistência em arquivo (leitura/gravação de objetos; persiste entre execuções)
- **Onde:** `persistence/RepositorioArquivo.java` (`ObjectOutputStream`/
  `ObjectInputStream`) e `service/SistemaMeLivra.java` → métodos `salvar()` e
  `carregar()`. Arquivo gerado: `dados/melivra.dat`.
- Todas as entidades implementam `Serializable` (via `model/Identificavel.java`).
- **Comprovação:** caso de teste **CT08** em `test/TesteFuncional.java`.

### (d) Classe Abstrata e/ou Interface
- **Classe abstrata:** `model/Usuario.java` (`public abstract class Usuario`,
  método abstrato `getTipo()`).
- **Interface:** `model/Identificavel.java` (estende `Serializable`),
  implementada por todas as entidades.

### (e) CRUD completo (Inclusão, Exclusão, Alteração, Consulta, Listagem)
- **Onde:** `service/SistemaMeLivra.java` — para cada entidade principal:
  - Usuário: `cadastrarUsuario`, `removerUsuario`, *(alteração via GUI/setters)*, `consultarUsuario`, `listarUsuarios`.
  - Post: `cadastrarPost`, `removerPost`, `atualizarPost`, `consultarPost`, `listarPosts`.
  - Professor: `cadastrarProfessor`, `removerProfessor`, `atualizarProfessor`, `consultarProfessor`, `listarProfessores`.
  - Avaliação: `cadastrarAvaliacao`, `removerAvaliacao`, `consultarAvaliacao`, `listarAvaliacoes`.
  - Anúncio: `cadastrarAnuncio`, `removerAnuncio`, `atualizarAnuncio`, `consultarAnuncio`, `listarAnuncios`.
- **Pela GUI:** `ui/MeLivraGUI.java` (submenus Incluir/Alterar/Excluir/Consultar/Listar).

### (f) Interface gráfica (mínimo JOptionPane)
- **Onde:** `ui/MeLivraGUI.java` (usa `javax.swing.JOptionPane`) e
  `Main.java` (inicia a GUI via `SwingUtilities.invokeLater`).

### (g) Casos de teste funcionais
- **Automatizados:** `test/TesteFuncional.java` (8 casos, runner próprio).
- **Documentados:** `docs/06_casos_de_teste.md` (entradas, passos, esperado) +
  roteiro de teste manual da GUI.
- **Executar:** `./build.sh test`.

### (h) Tratamento de Exceções + exceção própria
- **Exceção própria de regra de negócio:** `exception/NotaInvalidaException.java`
  (nota fora de [0, 10], lançada em `model/Avaliacao.java`).
- **Demais exceções próprias:** `MeLivraException` (base), `CampoObrigatorioException`,
  `EntidadeNaoEncontradaException`, `AutenticacaoException`, `PersistenciaException`.
- **Tratamento:** capturadas e exibidas ao usuário em `ui/MeLivraGUI.java`
  (blocos `try/catch (MeLivraException e)`), sem derrubar a aplicação.

### (i) Coleções (ArrayList)
- **Onde:** `service/SistemaMeLivra.java` (5 `ArrayList`), `model/Estudante.java`
  (listas de posts/avaliações/anúncios), `model/Post.java` (comentários),
  `model/Professor.java` (avaliações).

### (j) Documentação Javadoc
- **Fonte:** comentários `/** ... */` em todas as classes públicas.
- **Geração:** `./build.sh javadoc`.
- **Resultado:** `docs/javadoc/index.html`.

### (k) JAR executável
- **Build:** `./build.sh jar` (usa `javac` + `jar` com `manifest.txt`).
- **Resultado:** `dist/me-livra.jar` (Main-Class: `br.com.melivra.Main`).
- **Executar:** `java -jar dist/me-livra.jar`.

## Princípios de POO demonstrados

| Princípio | Exemplo |
|-----------|---------|
| **Abstração** | `Usuario` abstrata + interface `Identificavel` |
| **Herança** | `Estudante`/`Administrador` estendem `Usuario` |
| **Polimorfismo** | `getTipo()` sobrescrito; tratamento uniforme de `Identificavel`/`MeLivraException` |
| **Encapsulamento** | atributos privados com getters/setters validados |
