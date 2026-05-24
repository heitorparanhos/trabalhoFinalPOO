# Me Livra — Trabalho Final de POO

> Rede social universitária para posts, avaliação de professores e anúncios.

## Integrantes do Grupo

| Nome | Matrícula |
|------|-----------|
| Heitor Paranhos Carvalho | 202501288 |
| Matheus Gomes Rodrigues | 202505007 |
| Vitor Fernandes de Paula | 202505018 |

**Disciplina:** Programação Orientada a Objetos – 2026/1 SI  
**Professora:** Ana Claudia Bastos Loureiro Monção

---

## Sobre o Projeto

O **Me Livra** é uma rede social voltada para o ambiente universitário. A plataforma permite que estudantes publiquem posts, comentem e curtam publicações de colegas, avaliem professores com notas e comentários, e divulguem anúncios de interesse acadêmico. O sistema conta ainda com um perfil de Administrador responsável pela moderação do conteúdo. Este repositório contém a primeira entrega do trabalho final da disciplina de Programação Orientada a Objetos, apresentando a documentação de análise e a implementação inicial das classes em Java.

---

## Índice da Documentação

| Documento | Descrição |
|-----------|-----------|
| [01 — Problema e Escopo](docs/01-problema-escopo.md) | Definição do problema, objetivos e escopo do sistema |
| [02 — Requisitos](docs/02-requisitos.md) | Requisitos funcionais e não funcionais |
| [03 — Casos de Uso](docs/03-casos-de-uso.md) | Atores, casos de uso e diagrama |
| [04 — Classes Conceituais](docs/04-classes-conceituais.md) | Modelo conceitual em linguagem natural |
| [05 — Diagrama de Classes](docs/05-diagrama-de-classes.md) | Diagrama de classes UML (Mermaid) |

---

## Estrutura do Repositório

```
me-livra-poo/
├── README.md
├── docs/
│   ├── 01-problema-escopo.md
│   ├── 02-requisitos.md
│   ├── 03-casos-de-uso.md
│   ├── 04-classes-conceituais.md
│   └── 05-diagrama-de-classes.md
└── src/
    └── br/com/melivra/
        ├── model/
        │   ├── Usuario.java
        │   ├── Estudante.java
        │   ├── Administrador.java
        │   ├── Post.java
        │   ├── Comentario.java
        │   ├── Professor.java
        │   ├── Avaliacao.java
        │   └── Anuncio.java
        ├── repository/
        │   └── SistemaMeLivra.java
        └── Main.java
```

---

## Como Compilar e Executar

**Pré-requisito:** JDK 11 ou superior instalado.

```bash
# 1. Navegue até o diretório src/
cd me-livra-poo/src/

# 2. Compile o projeto (o compilador resolve todas as dependências automaticamente)
javac br/com/melivra/Main.java

# 3. Execute
java br.com.melivra.Main
```
