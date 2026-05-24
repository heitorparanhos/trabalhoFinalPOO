# 04 — Classes Conceituais

> Este documento descreve o modelo conceitual do sistema Me Livra em linguagem natural, sem código ou tipos de dados. As classes conceituais representam os principais conceitos do domínio do problema.

---

## 1. Classes Conceituais

### 1.1 Usuario
**Responsabilidade:** Representa qualquer pessoa cadastrada na plataforma. É a entidade central do sistema, especializada em dois tipos: Estudante e Administrador.

### 1.2 Estudante
**Responsabilidade:** Representa o usuário comum da plataforma. É o ator principal do sistema, podendo publicar posts, comentar, curtir, avaliar professores e divulgar anúncios. Herda de Usuario.

### 1.3 Administrador
**Responsabilidade:** Representa o usuário com privilégios de moderação. Possui todas as capacidades de um Estudante e, adicionalmente, pode remover qualquer conteúdo impróprio da plataforma. Herda de Usuario.

### 1.4 Post
**Responsabilidade:** Representa uma publicação textual feita por um usuário na rede social. Centraliza a interação entre usuários, acumulando comentários e curtidas.

### 1.5 Comentario
**Responsabilidade:** Representa a resposta textual de um usuário a um post. Está sempre associado a um post específico e pertence à composição deste.

### 1.6 Professor
**Responsabilidade:** Representa um docente universitário que pode ser avaliado pelos estudantes. Agrega as avaliações recebidas e calcula sua nota média automaticamente.

### 1.7 Avaliacao
**Responsabilidade:** Representa a opinião de um estudante sobre um professor, registrando uma nota numérica (0 a 10) e um comentário textual. Associa um usuário a um professor.

### 1.8 Anuncio
**Responsabilidade:** Representa uma publicação de classificado universitário, permitindo que usuários divulguem itens, serviços ou oportunidades de interesse acadêmico.

---

## 2. Relacionamentos Conceituais

| Relacionamento | Tipo | Descrição |
|----------------|------|-----------|
| Usuario → Estudante | **Herança (generalização/especialização)** | Estudante é um tipo especializado de Usuario |
| Usuario → Administrador | **Herança (generalização/especialização)** | Administrador é um tipo especializado de Usuario |
| Usuario publica Post | **Associação (1 para muitos)** | Um usuário pode publicar zero ou mais posts |
| Post contém Comentario | **Composição (1 para muitos)** | Um post é composto por zero ou mais comentários; comentários não existem sem o post |
| Usuario escreve Comentario | **Associação (1 para muitos)** | Um usuário pode escrever zero ou mais comentários |
| Usuario faz Avaliacao | **Associação (1 para muitos)** | Um usuário pode realizar zero ou mais avaliações de professores |
| Professor recebe Avaliacao | **Associação (1 para muitos)** | Um professor pode receber zero ou mais avaliações |
| Usuario publica Anuncio | **Associação (1 para muitos)** | Um usuário pode publicar zero ou mais anúncios |

---

## 3. Glossário do Domínio

| Termo | Definição |
|-------|-----------|
| Curtida | Registro de aprovação de um usuário a um post; incrementa um contador. |
| Média | Valor calculado automaticamente dividindo a soma das notas de avaliação pelo número de avaliações de um professor. |
| Moderação | Ação do Administrador de remover conteúdo que viole as regras da plataforma. |
| Anúncio universitário | Classificado voltado ao contexto acadêmico (venda de livros, estágios, serviços entre estudantes, etc.). |
