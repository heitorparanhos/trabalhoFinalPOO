# Me Livra — Trabalho Final de POO

> Rede social universitária para posts, avaliação de professores e anúncios.
> Tema derivado do repositório [`melivra`](docs/ANALISE_MELIVRA.md) (rede social
> acadêmica originalmente em Elixir/Phoenix), reimplementado em **Java** com
> interface gráfica.

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

O **Me Livra** é uma rede social voltada ao ambiente universitário. Estudantes
publicam posts, comentam e curtem publicações, avaliam professores com nota
(0–10) e comentário, e divulgam anúncios acadêmicos. Um perfil de
**Administrador** modera o conteúdo. Os dados são **persistidos em arquivo**
(serialização), de modo que permanecem entre execuções.

Este repositório consolida a **modelagem revisada** (docs 01–05) e a
**implementação final** completa em Java, com interface gráfica (`JOptionPane`),
CRUD, exceções próprias, testes e build de JAR.

---

## Índice da Documentação

| Documento | Descrição |
|-----------|-----------|
| [Análise do `melivra`](docs/ANALISE_MELIVRA.md) | Passo 0 — tema extraído do repositório de referência |
| [01 — Problema e Escopo](docs/01-problema-escopo.md) | Definição do problema, objetivos e escopo |
| [02 — Requisitos](docs/02-requisitos.md) | Requisitos funcionais e não funcionais |
| [03 — Casos de Uso](docs/03-casos-de-uso.md) | Atores, casos de uso e diagrama |
| [04 — Classes Conceituais](docs/04-classes-conceituais.md) | Modelo conceitual |
| [05 — Diagrama de Classes](docs/05-diagrama-de-classes.md) | UML (conceitual + arquitetura final) |
| [06 — Casos de Teste](docs/06_casos_de_teste.md) | Casos de teste funcionais |
| [Checklist de Requisitos](docs/CHECKLIST_REQUISITOS.md) | Mapeamento de cada requisito (a–k) |

---

## Estrutura do Repositório

```
me-livra-poo/
├── README.md
├── build.sh                 # compila, gera JAR e Javadoc (sem Maven/Gradle)
├── run.sh                   # compila (se preciso) e executa a aplicação
├── manifest.txt             # manifest do JAR (Main-Class)
├── docs/                    # documentação + javadoc gerado
└── src/br/com/melivra/
    ├── Main.java            # ponto de entrada (inicia a GUI)
    ├── model/               # entidades + interface Identificavel
    │   ├── Identificavel.java  Usuario.java  Estudante.java  Administrador.java
    │   ├── Post.java  Comentario.java  Professor.java  Avaliacao.java  Anuncio.java
    ├── exception/           # MeLivraException + exceções próprias
    ├── persistence/         # RepositorioArquivo (serialização em arquivo)
    ├── service/             # SistemaMeLivra (CRUD + persistência + login)
    ├── ui/                  # MeLivraGUI (JOptionPane)
    ├── util/                # Formatador
    └── test/                # TesteFuncional (casos de teste)
```

---

## Pré-requisitos

- **JDK 17 ou superior** (desenvolvido e testado com JDK 25).
- Ambiente com interface gráfica (a aplicação usa `JOptionPane`).

---

## Como Compilar, Gerar o JAR, o Javadoc e Executar

> Todos os comandos a partir da pasta `me-livra-poo/`.

### Opção A — Script de build (recomendado)

```bash
./build.sh          # compila + gera dist/me-livra.jar + docs/javadoc/
./build.sh jar      # apenas o JAR executável
./build.sh javadoc  # apenas o Javadoc (em docs/javadoc/)
./build.sh test     # roda os testes funcionais
```

Executar a aplicação:

```bash
java -jar dist/me-livra.jar
# ou simplesmente:
./run.sh
```

### Opção B — Comandos manuais (javac / jar / javadoc)

```bash
# Compilar
mkdir -p build
javac -encoding UTF-8 -d build $(find src -name '*.java')

# Executar diretamente
java -cp build br.com.melivra.Main

# Gerar JAR executável
mkdir -p dist
jar cfm dist/me-livra.jar manifest.txt -C build .
java -jar dist/me-livra.jar

# Gerar Javadoc
javadoc -encoding UTF-8 -d docs/javadoc $(find src -name '*.java')

# Rodar os testes funcionais
java -cp build br.com.melivra.test.TesteFuncional
```

---

## Uso da Aplicação

No **primeiro uso**, o sistema cria dados de demonstração e um administrador:

- **Login do administrador:** `admin@uni.br` / senha `admin`
- Estudantes de exemplo: `heitor@uni.br` / `123` e `matheus@uni.br` / `123`

A partir do menu principal é possível: gerenciar posts (CRUD), comentar/curtir,
gerenciar professores (CRUD), avaliar professores e ver médias, gerenciar
anúncios (CRUD), gerenciar usuários (CRUD) e — como administrador — moderar
conteúdo. Os dados são salvos automaticamente em `dados/melivra.dat`.

---

## Requisitos Atendidos

Veja o mapeamento completo de cada requisito (a–k) em
[docs/CHECKLIST_REQUISITOS.md](docs/CHECKLIST_REQUISITOS.md).
