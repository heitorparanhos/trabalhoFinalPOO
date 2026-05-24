# 01 — Problema e Escopo

## 1. Definição do Problema

Os estudantes universitários carecem de um espaço digital unificado voltado especificamente para o contexto acadêmico. As redes sociais genéricas existentes não oferecem funcionalidades adequadas para avaliar professores, compartilhar experiências sobre disciplinas ou divulgar anúncios de interesse exclusivamente universitário. Essa fragmentação obriga os alunos a utilizarem múltiplas plataformas (grupos de WhatsApp, murais físicos, redes sociais genéricas), dificultando a comunicação, a troca de informações e a construção de uma comunidade acadêmica coesa.

O **Me Livra** surge para centralizar essas necessidades em um único sistema, proporcionando um ambiente dedicado à comunidade universitária.

---

## 2. Objetivo do Sistema

Desenvolver uma rede social universitária que permita aos estudantes:

- **Publicar e interagir** com posts textuais de outros usuários (comentários e curtidas);
- **Avaliar professores** com notas (escala de 0 a 10) e comentários, auxiliando colegas na escolha de disciplinas;
- **Divulgar anúncios** de interesse acadêmico (venda de materiais, serviços, estágios, etc.);
- **Moderar o conteúdo** por meio de administradores que podem remover publicações impróprias.

---

## 3. Escopo

### 3.1 O que ESTÁ incluído nesta versão

| # | Funcionalidade |
|---|----------------|
| 1 | Cadastro e autenticação de usuários (Estudante e Administrador) |
| 2 | Criação, edição, exclusão e listagem de posts |
| 3 | Comentários e curtidas em posts |
| 4 | Cadastro de professores |
| 5 | Avaliação de professores com nota (0–10) e comentário |
| 6 | Listagem de avaliações e cálculo de média por professor |
| 7 | Criação, edição, exclusão e listagem de anúncios |
| 8 | Moderação de conteúdo pelo Administrador |
| 9 | Persistência de dados em arquivo |

### 3.2 O que NÃO está incluído (fora do escopo desta versão acadêmica)

| # | Funcionalidade excluída | Justificativa |
|---|------------------------|---------------|
| 1 | Chat em tempo real | Requer infraestrutura de WebSocket/servidor dedicado |
| 2 | Gerenciamento de eventos universitários | Fora do escopo definido para esta entrega |
| 3 | Integração com atléticas e grupos estudantis | Complexidade adicional não prevista |
| 4 | Gamificação e jogos | Não relacionado ao objetivo principal do sistema |
| 5 | Notificações push | Requer infraestrutura externa |
| 6 | Aplicativo mobile | Sistema desktop/console nesta versão |
