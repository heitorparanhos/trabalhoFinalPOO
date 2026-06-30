# Análise do Repositório de Referência `melivra`

> **Passo 0 do trabalho** — Este documento registra a leitura do repositório
> original `melivra` e como o seu tema foi mapeado para as classes deste
> trabalho de POO em Java.

## 1. O que é o `melivra`

O `melivra` é uma **rede social universitária** desenvolvida em **Elixir/Phoenix**
(LiveView) com banco **PostgreSQL**. É uma aplicação web real, voltada à
comunidade acadêmica, cujo código está organizado em *contextos* Phoenix
(`lib/melivra/*.ex`). A leitura do `README.md` e da árvore de módulos revela os
seguintes domínios principais:

| Contexto / módulo no `melivra` | Responsabilidade no sistema original |
|--------------------------------|--------------------------------------|
| `accounts.ex`, `profiles.ex`   | Cadastro e autenticação de usuários; perfis (inclui flag de **admin**) |
| `timeline.ex`                  | **Posts** da linha do tempo (publicações dos estudantes) |
| `feedback.ex`, `moderation.ex` | Comentários, interações e **moderação** de conteúdo |
| `professors.ex`, `provas.ex`   | Cadastro de **professores** e provas; base para avaliação docente |
| `ads.ex`                       | **Anúncios** / campanhas dentro da plataforma |
| `points.ex`, `awards.ex`, `levels.ex` | Gamificação (pontos, prêmios) |
| `groups.ex`, `chat.ex`, `news.ex` | Grupos, chat e notícias |

### Entidades centrais identificadas

- **Usuário/Perfil** (estudante ou administrador) — autentica-se e produz conteúdo.
- **Post** — publicação textual na linha do tempo, com interações.
- **Comentário** — resposta a um post.
- **Professor** — docente que pode ser avaliado pelos estudantes.
- **Avaliação** — nota + comentário de um estudante sobre um professor.
- **Anúncio** — classificado/divulgação dentro da comunidade.
- **Administrador** — perfil com poder de **moderação** de conteúdo.

### Regras de negócio relevantes para o nosso recorte

- Um usuário precisa estar autenticado para publicar.
- O administrador pode remover conteúdo impróprio (moderação).
- A nota de avaliação é limitada a uma escala (adotamos **0 a 10**).
- Cada registro do banco possui um identificador único e sequencial.

## 2. Como o tema foi mapeado para este trabalho Java

Como o trabalho de POO é uma aplicação **desktop em Java puro** (sem web/banco),
fizemos um **recorte** do `melivra` que preserva o seu núcleo conceitual —
*rede social universitária com posts, avaliação de professores, anúncios e
moderação* — e o reimplementamos com os recursos de POO exigidos.

| Conceito no `melivra` (Elixir) | Classe Java neste trabalho |
|--------------------------------|----------------------------|
| Perfil / conta                 | `Usuario` (abstrata), `Estudante`, `Administrador` |
| Post da timeline               | `Post` |
| Comentário                     | `Comentario` |
| Professor                      | `Professor` |
| Avaliação docente              | `Avaliacao` |
| Anúncio                        | `Anuncio` |
| Contexto/Repo + Ecto           | `SistemaMeLivra` (repositório/serviço) + `RepositorioArquivo` |
| Migração com `id` serial       | Atributo `static int proximoId` em cada entidade |
| Banco PostgreSQL               | Persistência por **serialização** em arquivo (`dados/melivra.dat`) |
| Autenticação `accounts`        | `SistemaMeLivra.autenticar(email, senha)` |
| Moderação `moderation`         | `Administrador.moderar(...)` + remoção via `SistemaMeLivra` |

### O que ficou de fora do recorte (e por quê)

Gamificação (pontos/prêmios), chat em tempo real, grupos, notícias e o jogo
*truco* presentes no `melivra` foram deixados de fora por dependerem de
infraestrutura (WebSocket, jobs, banco) que extrapola o escopo de um trabalho
de POO desktop. Mantivemos o **coração social-acadêmico** do produto, que é
suficiente para exercitar herança, polimorfismo, abstração, coleções,
persistência, exceções e CRUD com interface gráfica.

## 3. Conclusão

O tema deste trabalho — **"Me Livra: rede social universitária"** — é uma
derivação fiel e justificada do repositório `melivra`. Toda a modelagem
(documentos 01 a 05) e a implementação Java partem desse recorte.
