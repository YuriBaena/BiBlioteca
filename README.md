# 📚 My Books Management System

Sistema de gerenciamento de biblioteca desenvolvido em Java com interface gráfica (Swing), que permite o controle eficiente de livros, usuários e empréstimos.

## ✨ Funcionalidades

- 📖 Cadastro de livros com título, autor, ano e número de cópias
- 👤 Cadastro de usuários da biblioteca
- 📥 Empréstimo e 📤 devolução de livros com controle de disponibilidade
- 🧾 Geração de relatórios com:
  - Livros emprestados por usuário
  - Data do empréstimo
  - Cálculo de multa (se aplicável)
- 🔍 Campo de busca para filtrar livros ou usuários na tabela de empréstimos
- 💾 Salvamento automático de dados em arquivos locais

## 🖼 Interface

O sistema possui uma interface gráfica intuitiva feita com Java Swing, com janelas para:

- Gerenciamento de livros
- Gerenciamento de usuários
- Registro e devolução de empréstimos
- Visualização de relatórios

## 🚀 Como executar

1. Certifique-se de ter o **Java JDK 8 ou superior** instalado.
2. Compile o projeto:

```bash
javac -d bin src/lp2g35/biblioteca/*.java
```

3. Execute o sistema:

```bash
java -cp bin lp2g35.biblioteca.Main
```

> Obs: substitua `Main` pelo nome da classe principal do seu sistema, se for diferente.

## 🗂 Estrutura do Projeto

```
MyBooksManagement/
├── src/
│   └── lp2g35/
│       └── biblioteca/
│           ├── Livro.java
│           ├── Usuario.java
│           ├── Emprestimo.java
│           └── ... outros arquivos
├── bin/           # Arquivos compilados
├── README.md
└── .gitignore
```

## ⚠️ Requisitos

- Java 8 ou superior
- Sistema operacional: compatível com Windows, macOS e Linux

## 📌 Observações

- Os dados são persistidos localmente em arquivos `.ser`
- O sistema trata exceções como falta de cópias disponíveis e devoluções não registradas

## 👨‍💻 Autor

Desenvolvido por **Yuri Nascimento**, estudante de Ciência da Computação na UERJ.  
Este projeto faz parte das atividades da disciplina de **Linguagem de Programação 2**.
