# 02 — Requisitos do Sistema

## 1. Requisitos Funcionais

| ID | Descrição |
|----|-----------|
| RF01 | O sistema deve permitir o cadastro de usuários informando nome, e-mail, senha e tipo (Estudante ou Administrador). |
| RF02 | O sistema deve autenticar usuários por e-mail e senha. |
| RF03 | O sistema deve permitir que usuários criem, editem, excluam e listem posts textuais. |
| RF04 | O sistema deve permitir que usuários comentem em posts existentes. |
| RF05 | O sistema deve permitir que usuários curtam posts. |
| RF06 | O sistema deve permitir o cadastro de professores com nome e departamento. |
| RF07 | O sistema deve permitir que Estudantes avaliem professores com uma nota de 0 a 10 e um comentário textual. |
| RF08 | O sistema deve listar todas as avaliações de um professor e calcular sua nota média. |
| RF09 | O sistema deve permitir que usuários criem, editem, excluam e listem anúncios, informando título, descrição e preço. |
| RF10 | O sistema deve permitir que Administradores removam posts, comentários ou anúncios impróprios. |

---

## 2. Requisitos Não Funcionais

| ID | Descrição |
|----|-----------|
| RNF01 | O sistema deve persistir os dados em arquivo (texto ou serialização), garantindo que as informações não sejam perdidas ao encerrar a aplicação. |
| RNF02 | A interface com o usuário deve ser implementada utilizando `JOptionPane` (javax.swing), proporcionando interação por meio de caixas de diálogo gráficas. |
| RNF03 | O sistema deve tratar exceções por meio de uma classe de exceção própria, fornecendo mensagens de erro claras ao usuário. |
| RNF04 | Os identificadores de todas as entidades (usuários, posts, comentários, professores, avaliações e anúncios) devem ser gerados automaticamente de forma sequencial e única. |
| RNF05 | O sistema deve ser desenvolvido integralmente em linguagem Java, seguindo os princípios de Programação Orientada a Objetos (herança, encapsulamento, polimorfismo e abstração). |
