# 05 — Diagrama de Classes

## 1. Diagrama UML

```mermaid
classDiagram
    direction TB

    class Usuario {
        <<abstract>>
        -int idUsuario
        -String nome
        -String email
        -String senha
        -$ int proximoId
        +Usuario(nome String, email String, senha String)
        +getIdUsuario() int
        +getNome() String
        +getEmail() String
        +getSenha() String
        +getTipo()* String
        +toString() String
    }

    class Estudante {
        -String curso
        -List~Post~ posts
        -List~Avaliacao~ avaliacoes
        -List~Anuncio~ anuncios
        +Estudante(nome String, email String, senha String, curso String)
        +getCurso() String
        +getPosts() List~Post~
        +getAvaliacoes() List~Avaliacao~
        +getAnuncios() List~Anuncio~
        +getTipo() String
        +criarPost(texto String) Post
        +avaliarProfessor(professor Professor, nota double, comentario String) Avaliacao
        +criarAnuncio(titulo String, descricao String, preco double) Anuncio
    }

    class Administrador {
        +Administrador(nome String, email String, senha String)
        +getTipo() String
        +removerConteudo(conteudo Object) void
    }

    class Post {
        -int idPost
        -$ int proximoId
        -Usuario autor
        -String texto
        -LocalDateTime dataHora
        -int curtidas
        -List~Comentario~ comentarios
        +Post(autor Usuario, texto String)
        +getIdPost() int
        +getAutor() Usuario
        +getTexto() String
        +getDataHora() LocalDateTime
        +getCurtidas() int
        +getComentarios() List~Comentario~
        +adicionarComentario(comentario Comentario) void
        +curtir() void
        +toString() String
    }

    class Comentario {
        -int idComentario
        -$ int proximoId
        -Usuario autor
        -String texto
        -LocalDateTime dataHora
        +Comentario(autor Usuario, texto String)
        +getIdComentario() int
        +getAutor() Usuario
        +getTexto() String
        +getDataHora() LocalDateTime
        +toString() String
    }

    class Professor {
        -int idProfessor
        -$ int proximoId
        -String nome
        -String departamento
        -List~Avaliacao~ avaliacoes
        +Professor(nome String, departamento String)
        +getIdProfessor() int
        +getNome() String
        +getDepartamento() String
        +getAvaliacoes() List~Avaliacao~
        +adicionarAvaliacao(avaliacao Avaliacao) void
        +calcularMedia() double
        +toString() String
    }

    class Avaliacao {
        -int idAvaliacao
        -$ int proximoId
        -Usuario autor
        -Professor professor
        -double nota
        -String comentario
        -LocalDateTime dataHora
        +Avaliacao(autor Usuario, professor Professor, nota double, comentario String)
        +getIdAvaliacao() int
        +getAutor() Usuario
        +getProfessor() Professor
        +getNota() double
        +getComentario() String
        +getDataHora() LocalDateTime
        +toString() String
    }

    class Anuncio {
        -int idAnuncio
        -$ int proximoId
        -Usuario autor
        -String titulo
        -String descricao
        -double preco
        -LocalDateTime dataHora
        +Anuncio(autor Usuario, titulo String, descricao String, preco double)
        +getIdAnuncio() int
        +getAutor() Usuario
        +getTitulo() String
        +getDescricao() String
        +getPreco() double
        +getDataHora() LocalDateTime
        +toString() String
    }

    class SistemaMeLivra {
        -List~Usuario~ usuarios
        -List~Post~ posts
        -List~Professor~ professores
        -List~Avaliacao~ avaliacoes
        -List~Anuncio~ anuncios
        +SistemaMeLivra()
        +cadastrarUsuario(usuario Usuario) void
        +removerUsuario(idUsuario int) boolean
        +consultarUsuario(idUsuario int) Usuario
        +listarUsuarios() List~Usuario~
        +cadastrarPost(post Post) void
        +removerPost(idPost int) boolean
        +atualizarPost(idPost int, novoTexto String) boolean
        +consultarPost(idPost int) Post
        +listarPosts() List~Post~
        +cadastrarProfessor(professor Professor) void
        +removerProfessor(idProfessor int) boolean
        +atualizarProfessor(idProfessor int, nome String, dept String) boolean
        +consultarProfessor(idProfessor int) Professor
        +listarProfessores() List~Professor~
        +cadastrarAvaliacao(avaliacao Avaliacao) void
        +removerAvaliacao(idAvaliacao int) boolean
        +consultarAvaliacao(idAvaliacao int) Avaliacao
        +listarAvaliacoes() List~Avaliacao~
        +cadastrarAnuncio(anuncio Anuncio) void
        +removerAnuncio(idAnuncio int) boolean
        +atualizarAnuncio(idAnuncio int, titulo String, desc String, preco double) boolean
        +consultarAnuncio(idAnuncio int) Anuncio
        +listarAnuncios() List~Anuncio~
    }

    %% Herança
    Usuario <|-- Estudante
    Usuario <|-- Administrador

    %% Associação: Usuário publica Posts e Anúncios
    Usuario "1" --> "0..*" Post : publica
    Usuario "1" --> "0..*" Anuncio : divulga
    Usuario "1" --> "0..*" Avaliacao : realiza

    %% Composição: Post é composto por Comentários
    Post "1" *-- "0..*" Comentario : contém

    %% Associação: Professor recebe Avaliações
    Professor "1" --> "0..*" Avaliacao : recebe

    %% SistemaMeLivra gerencia todas as entidades
    SistemaMeLivra "1" o-- "0..*" Usuario : gerencia
    SistemaMeLivra "1" o-- "0..*" Post : gerencia
    SistemaMeLivra "1" o-- "0..*" Professor : gerencia
    SistemaMeLivra "1" o-- "0..*" Avaliacao : gerencia
    SistemaMeLivra "1" o-- "0..*" Anuncio : gerencia
```

---

## 2. Decisões de Modelagem

**Classe Abstrata `Usuario`:** A escolha de tornar `Usuario` abstrata reflete o fato de que nenhum usuário pode existir no sistema sem ser, concretamente, um `Estudante` ou um `Administrador`. O método abstrato `getTipo()` força cada subclasse a se identificar, permitindo polimorfismo na exibição de informações. O atributo `static int proximoId` garante a geração de identificadores únicos e sequenciais sem depender de banco de dados, atendendo ao RNF04.

**Herança `Estudante` e `Administrador`:** A herança é justificada pela relação "é-um" (um Estudante *é um* Usuário). As listas de `Post`, `Avaliacao` e `Anuncio` foram colocadas em `Estudante` porque somente o usuário comum realiza essas ações com rastreio individual; o `Administrador` opera de forma transversal via `SistemaMeLivra`.

**Composição `Post` → `Comentario`:** Comentários têm existência dependente do post ao qual pertencem — se o post for removido, seus comentários não fazem sentido isolados. Por isso a relação é de composição (`*--`), e não de simples associação.

**Associação `Professor` → `Avaliacao`:** Professor e Avaliação existem de forma independente; um professor pode ter zero avaliações e ainda assim ser um objeto válido. Portanto, a relação é de associação (`-->`), com o professor mantendo uma lista das avaliações que recebe e o método `calcularMedia()` percorrendo essa lista dinamicamente.

**Classe `SistemaMeLivra`:** Atua como repositório central (padrão *Repository*), agregando todas as entidades com agregação (`o--`), pois as entidades podem existir conceitualmente fora do sistema. Os métodos CRUD seguem nomenclatura uniforme (`cadastrar`, `remover`, `atualizar`, `consultar`, `listar`), facilitando a manutenção e a evolução do sistema.

---

## 3. Arquitetura da Entrega Final

A entrega final acrescentou a interface `Identificavel`, a hierarquia de
exceções próprias e as camadas de persistência e serviço. A organização em
pacotes ficou assim:

```mermaid
classDiagram
    direction LR

    class Identificavel {
        <<interface>>
        +getId() int
        +getTipoEntidade() String
    }
    class Serializable {
        <<interface>>
    }

    class Usuario {
        <<abstract>>
    }

    Serializable <|.. Identificavel
    Identificavel <|.. Usuario
    Identificavel <|.. Post
    Identificavel <|.. Comentario
    Identificavel <|.. Professor
    Identificavel <|.. Avaliacao
    Identificavel <|.. Anuncio

    class MeLivraException {
        <<checked>>
    }
    class NotaInvalidaException
    class CampoObrigatorioException
    class EntidadeNaoEncontradaException
    class AutenticacaoException
    class PersistenciaException

    MeLivraException <|-- NotaInvalidaException
    MeLivraException <|-- CampoObrigatorioException
    MeLivraException <|-- EntidadeNaoEncontradaException
    MeLivraException <|-- AutenticacaoException
    MeLivraException <|-- PersistenciaException

    class SistemaMeLivra {
        +autenticar(email, senha) Usuario
        +salvar() void
        +carregar() boolean
        +cadastrar/remover/atualizar/consultar/listar...()
    }
    class RepositorioArquivo {
        +salvar(Serializable, caminho)$ void
        +carregar(caminho)$ T
    }
    class MeLivraGUI {
        +iniciar() void
    }

    SistemaMeLivra ..> RepositorioArquivo : usa
    MeLivraGUI ..> SistemaMeLivra : opera
    SistemaMeLivra ..> MeLivraException : lança
```

### Pacotes (responsabilidade)

| Pacote | Conteúdo |
|--------|----------|
| `br.com.melivra.model` | Entidades de domínio + interface `Identificavel` |
| `br.com.melivra.exception` | Exceção base `MeLivraException` e exceções específicas |
| `br.com.melivra.persistence` | `RepositorioArquivo` (serialização em arquivo) |
| `br.com.melivra.service` | `SistemaMeLivra` (CRUD + persistência + autenticação) |
| `br.com.melivra.ui` | `MeLivraGUI` (interface gráfica com `JOptionPane`) |
| `br.com.melivra.util` | `Formatador` (utilitários de formatação) |
| `br.com.melivra.test` | `TesteFuncional` (casos de teste) |

### Decisões da entrega final

- **Interface `Identificavel`:** unifica o contrato de "ter ID" e "saber se
  rotular", permitindo que a GUI e a moderação tratem qualquer entidade de forma
  polimórfica. Estende `Serializable` porque toda entidade é persistida.
- **Exceção própria `NotaInvalidaException`:** materializa a regra de negócio
  "nota entre 0 e 10" (requisito h), substituindo o *clamp* silencioso da
  primeira entrega por uma falha explícita e informativa.
- **Persistência por serialização:** o `SistemaMeLivra` grava um único
  instantâneo (`EstadoSistema`) com todas as listas e os contadores estáticos,
  preservando a identidade das referências cruzadas entre objetos e permitindo
  retomar a numeração de IDs sem colisão.
