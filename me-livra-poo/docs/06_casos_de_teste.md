# 06 — Casos de Teste Funcionais

Os casos abaixo cobrem os fluxos principais do sistema. Eles estão
**automatizados** na classe `br.com.melivra.test.TesteFuncional`, que pode ser
executada com:

```bash
./build.sh test
# ou
java -cp build br.com.melivra.test.TesteFuncional
```

Cada caso descreve entradas, passos e resultado esperado, e indica o método de
teste correspondente.

---

### CT01 — IDs automáticos e sequenciais
- **Objetivo:** validar o requisito (b) — geração de ID via atributo estático.
- **Entradas:** criação de dois estudantes em sequência.
- **Passos:** instanciar `Estudante A`, depois `Estudante B`.
- **Resultado esperado:** `idB == idA + 1`.
- **Método:** `ct01_idsSequenciais`.

### CT02 — CRUD completo de Post
- **Objetivo:** validar o requisito (e) — Incluir, Consultar, Alterar, Listar, Excluir.
- **Entradas:** post com texto "texto original".
- **Passos:** cadastrar → consultar → atualizar para "texto editado" → listar → remover.
- **Resultado esperado:** post é encontrado, texto é alterado, listagem tem 1 item e, após remoção, a lista fica vazia.
- **Método:** `ct02_crudPostCompleto`.

### CT03 — Nota inválida (exceção própria)
- **Objetivo:** validar o requisito (h) — exceção própria de regra de negócio.
- **Entradas:** avaliação com nota `11.0`.
- **Passos:** estudante tenta avaliar professor com nota fora de [0, 10].
- **Resultado esperado:** o sistema lança `NotaInvalidaException` e **não** registra a avaliação.
- **Método:** `ct03_notaInvalidaLancaExcecao`.

### CT04 — Campo obrigatório vazio
- **Objetivo:** validar tratamento de entrada inválida.
- **Entradas:** estudante com `nome = ""`.
- **Passos:** tentar criar `Estudante` com nome em branco.
- **Resultado esperado:** lança `CampoObrigatorioException`.
- **Método:** `ct04_campoObrigatorioLancaExcecao`.

### CT05 — Cálculo da média do professor
- **Objetivo:** validar a regra de cálculo de média (RF08).
- **Entradas:** notas `8.0` e `6.0` para o mesmo professor.
- **Passos:** registrar duas avaliações; chamar `calcularMedia()`.
- **Resultado esperado:** média `7.0`.
- **Método:** `ct05_mediaProfessor`.

### CT06 — Autenticação
- **Objetivo:** validar UC01 (login).
- **Entradas:** usuário `adm@x.br` / `segredo`.
- **Passos:** autenticar com senha correta e depois com senha errada.
- **Resultado esperado:** login correto retorna o usuário; senha errada lança `AutenticacaoException`.
- **Método:** `ct06_autenticacao`.

### CT07 — Entidade não encontrada
- **Objetivo:** validar tratamento de consulta inexistente.
- **Entradas:** consulta de post com ID `9999` em sistema vazio.
- **Passos:** chamar `consultarPost(9999)`.
- **Resultado esperado:** lança `EntidadeNaoEncontradaException`.
- **Método:** `ct07_entidadeNaoEncontrada`.

### CT08 — Persistência entre execuções
- **Objetivo:** validar o requisito (c) — leitura/gravação de objetos em arquivo.
- **Entradas:** um usuário e um anúncio.
- **Passos:** gravar com `salvar()`; criar nova instância de `SistemaMeLivra` apontando para o mesmo arquivo; chamar `carregar()`.
- **Resultado esperado:** os dados (1 usuário e 1 anúncio) são recuperados.
- **Método:** `ct08_persistenciaEntreExecucoes`.

---

## Resultado da última execução

```
==== TESTES FUNCIONAIS — ME LIVRA ====

[PASSOU] CT01 — IDs de usuário devem ser sequenciais
[PASSOU] CT02 — CRUD completo de Post deve funcionar
[PASSOU] CT03 — Nota 11 deve lançar NotaInvalidaException
[PASSOU] CT04 — Nome vazio deve lançar CampoObrigatorioException
[PASSOU] CT05 — Média de 8 e 6 deve ser 7.0
[PASSOU] CT06 — Login válido aceita e senha errada é rejeitada
[PASSOU] CT07 — Consulta inexistente lança EntidadeNaoEncontradaException
[PASSOU] CT08 — Dados devem persistir e ser recarregados de arquivo

==== RESUMO ====
Passou: 8 | Falhou: 0
```

## Teste manual da interface gráfica (roteiro de apresentação)

1. Executar `java -jar dist/me-livra.jar`.
2. Login com **admin@uni.br / admin**.
3. Menu **1 (Posts)** → Incluir, Listar, Alterar, Excluir.
4. Menu **2** → comentar e curtir um post.
5. Menu **3 (Professores)** → cadastrar e consultar.
6. Menu **4** → avaliar professor (testar nota `15` para ver a exceção própria) e ver a média.
7. Menu **5 (Anúncios)** → CRUD completo.
8. Menu **7 (Moderação)** → remover um post/anúncio.
9. Fechar e reabrir a aplicação → confirmar que os dados persistiram.
