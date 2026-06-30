# Code Review — Me Livra (Entrega Final)

> Revisão técnica do projeto. Caminhos relativos a `me-livra-poo/`.
> Status de build: compila sem erros (JDK 25); testes funcionais **8/8 OK**
> (`br.com.melivra.test.TesteFuncional`).

## 1. Sumário

O projeto está **coeso, em camadas e atende a todos os requisitos (a–k)**. A
separação em pacotes (`model`, `exception`, `persistence`, `service`, `ui`,
`util`, `test`) é clara e o acoplamento entre camadas segue a direção correta
(`ui → service → persistence/model`). Não foram encontrados bugs que quebrem
fluxos. As observações abaixo são, em sua maioria, melhorias de robustez e de
encapsulamento — nenhuma é bloqueante para a entrega.

| Aspecto | Avaliação |
|---------|-----------|
| Cobertura de requisitos | ✅ Completa |
| Qualidade de POO | ✅ Boa |
| Persistência | ✅ Segura (try-with-resources, trata arquivo ausente) |
| GUI / fluxos CRUD | ✅ Todos acessíveis |
| Tratamento de exceções | ✅ Centralizado, com exceção própria |
| Javadoc | ✅ Presente em toda a API pública |

## 2. Cobertura de Requisitos

| Req. | Onde está implementado | Correto? |
|------|------------------------|----------|
| (a) Backend/classes | `model/*` (atributos, construtores, métodos de negócio) | ✅ |
| (b) ID estático sequencial | `static int proximoId` + `proximoId++` no construtor de cada entidade | ✅ |
| (c) Persistência em arquivo | `persistence/RepositorioArquivo` + `service/SistemaMeLivra.salvar()/carregar()` | ✅ (CT08) |
| (d) Abstrata e/ou interface | `model/Usuario` (abstrata) + `model/Identificavel` (interface) | ✅ |
| (e) CRUD | `service/SistemaMeLivra` (cadastrar/remover/atualizar/consultar/listar) + GUI | ✅ (CT02) |
| (f) GUI | `ui/MeLivraGUI` (`JOptionPane`) | ✅ |
| (g) Testes | `test/TesteFuncional` + `docs/06_casos_de_teste.md` | ✅ (8/8) |
| (h) Exceções + própria | `exception/*`, com `NotaInvalidaException` para a regra de nota | ✅ (CT03) |
| (i) Coleções | `ArrayList` em `service` e nos modelos | ✅ |
| (j) Javadoc | comentários `/** */` + `docs/javadoc/` | ✅ |
| (k) JAR executável | `manifest.txt` + `build.sh jar` → `dist/me-livra.jar` | ✅ |

## 3. Qualidade de POO

**Pontos fortes**
- **Abstração:** `Usuario` abstrata com método abstrato `getTipo()`; a interface
  `Identificavel` (estendendo `Serializable`) unifica "ter ID" + "saber se
  rotular", usada de forma polimórfica na GUI/moderação.
- **Herança justificada:** `Estudante`/`Administrador` são "é-um" de `Usuario`.
- **Encapsulamento:** atributos privados; setters validam entrada e lançam
  exceções de domínio (ex.: `Usuario.setNome`, `Avaliacao.setNota`).
- **Coesão:** cada classe tem responsabilidade única; `SistemaMeLivra` concentra
  o repositório/serviço (padrão *Repository*/*Facade*).
- **Polimorfismo de exceção:** a GUI captura apenas `MeLivraException` e trata
  todos os erros de negócio de forma uniforme.

## 4. Problemas e Recomendações

Classificados por severidade. Nenhum é bloqueante.

### [Média] R1 — Getters de coleção expõem a lista interna ✅ RESOLVIDO
- **Onde:** `Post.getComentarios()`, `Professor.getAvaliacoes()`,
  `Estudante.getPosts()/getAvaliacoes()/getAnuncios()`.
- **Problema original:** retornavam a referência da lista interna (mutável); um
  chamador podia alterá-la diretamente, furando o encapsulamento.
- **Correção aplicada:** os getters agora retornam
  `Collections.unmodifiableList(...)`. A remoção de avaliações passou a usar o
  método dedicado `Professor.removerAvaliacao(int id)`, e
  `SistemaMeLivra.removerAvaliacao` foi ajustado para chamá-lo, em vez de mutar a
  lista por fora.

### [Baixa] R2 — Sem unicidade de e-mail no cadastro
- **Onde:** `SistemaMeLivra.cadastrarUsuario`.
- **Problema:** dois usuários podem ter o mesmo e-mail; `autenticar` retorna o
  primeiro que casar.
- **Recomendação:** validar duplicidade antes de cadastrar e lançar uma exceção
  de domínio (ex.: `EmailJaCadastradoException`).

### [Baixa] R3 — Senha em texto puro
- **Onde:** `Usuario` (campo `senha`, `getSenha()`).
- **Observação:** aceitável no escopo acadêmico, mas vale registrar que não há
  *hash*. Em produção, usar hashing (ex.: bcrypt) e não expor a senha em getter.

### [Baixa] R4 — `throws MeLivraException` amplo nos `atualizar*`
- **Onde:** `SistemaMeLivra.atualizarPost/atualizarProfessor/atualizarAnuncio`.
- **Observação:** funcionalmente correto (aproveita o polimorfismo da exceção
  base), mas a assinatura poderia ser mais específica para documentar melhor o
  contrato. Não é um defeito.

### [Baixa] R5 — Pequena redundância ✅ RESOLVIDO
- **Onde:** `MeLivraGUI.lerTexto` → `return v == null ? null : v;`.
- **Correção aplicada:** simplificado para retornar diretamente o resultado de
  `JOptionPane.showInputDialog(...)`.

### [Info] R6 — Tamanho da classe `MeLivraGUI`
- **Observação:** ~720 linhas, porém dividida em métodos pequenos, coesos e
  nomeados por entidade/operação. Opcionalmente poderia ser quebrada em
  controladores por entidade, mas a legibilidade atual é boa.

### [Positivo] R7 — Recursos e persistência
- `RepositorioArquivo` usa **try-with-resources**, fechando os streams mesmo em
  erro. O `salvar()`/`carregar()` grava um **instantâneo único** (`EstadoSistema`),
  o que preserva a identidade das referências cruzadas entre objetos e restaura
  os contadores estáticos de ID sem colisão. Arquivo ausente é tratado
  (`carregar()` retorna `false`); falha de E/S vira `PersistenciaException`.

## 5. Persistência — análise específica

- **Consistente:** um único arquivo (`dados/melivra.dat`) com todo o estado.
- **Segura:** streams fechados via try-with-resources; exceções de E/S e
  `ClassNotFoundException` encapsuladas em `PersistenciaException`.
- **Risco conhecido:** `serialVersionUID = 1L` fixo — alterar a forma de uma
  classe invalida arquivos antigos. Tratado: a aplicação avisa o usuário e segue
  em memória (`Main.executar`). Para um trabalho acadêmico é adequado.

## 6. Interface Gráfica — fluxos CRUD

Todos os fluxos exigidos estão acessíveis pelo menu principal de `MeLivraGUI`:

| Fluxo | Caminho na GUI | Método |
|-------|----------------|--------|
| Login | tela de acesso → "1" | `efetuarLogin` |
| Posts CRUD | menu "1" | `menuPosts` → `cadastrar/alterar/excluir/consultar/listarPost(s)` |
| Comentar/Curtir | menu "2" | `interagirPost` |
| Professores CRUD | menu "3" | `menuProfessores` |
| Avaliar/Ver médias | menu "4" | `menuAvaliacoes`/`avaliarProfessor` |
| Anúncios CRUD | menu "5" | `menuAnuncios` |
| Usuários CRUD | menu "6" | `menuUsuarios` |
| Moderação (admin) | menu "7" | `moderarConteudo` |

Cancelamento de diálogo (`null`) é tratado em todos os pontos; erros de negócio
são exibidos em caixas de erro sem encerrar a aplicação. Salvamento automático
após cada operação que altera dados.

## 7. Javadoc

- Comentários presentes em todas as classes e métodos públicos.
- A geração emite ~77 *warnings* "no comment" referentes a **campos privados** —
  não afetam a documentação da API pública gerada e podem ser ignorados (ou
  silenciados gerando o Javadoc apenas com visibilidade padrão).

## 8. Conclusão

O projeto está **pronto para entrega**. As correções **R1** (coleções
imutáveis + `Professor.removerAvaliacao`) e **R5** foram **aplicadas**, com os
testes funcionais permanecendo em **8/8**. As demais (R2–R4) são melhorias
incrementais opcionais e não comprometem os requisitos nem a execução.
