# 📦 Sistema de Controle de Estoque - Loja de Informática

[cite_start]Este repositório contém o código-fonte do trabalho final da disciplina de **Programação Orientada a Objetos** [cite: 2][cite_start], da Universidade Regional de Blumenau (FURB) [cite: 1][cite_start], ministrada pelo Prof. André Felipe Bürger[cite: 2].

[cite_start]O projeto é um software desktop para o controle de estoque de uma loja de informática [cite: 4][cite_start], desenvolvido em Java com interface gráfica (GUI) utilizando **Java Swing**[cite: 4].

---

## 👥 Equipe

* Carlos Alfredo
* Filipe Tiago
* Pedro Henrique Jose

---

## ✨ Funcionalidades Principais

[cite_start]O sistema foi projetado para atender aos seguintes requisitos[cite: 12]:

* [cite_start]**Cadastro de Produtos:** Permite cadastrar produtos com código, nome, preço, quantidade e categoria[cite: 13].
    * [cite_start]**Categorias:** Componentes de hardware [cite: 13][cite_start], periféricos [cite: 14][cite_start], acessórios [cite: 15] [cite_start]e outros[cite: 16].
* [cite_start]**Registro de Entradas:** Registrar a entrada de itens no estoque, informando produto, data, quantidade e valor[cite: 17].
* [cite_start]**Registro de Saídas:** Registrar saídas de produtos (como vendas [cite: 8][cite_start], uso interno [cite: 9][cite_start], devoluções [cite: 10][cite_start], etc.), informando produto, data e quantidade[cite: 18].
* **Consultas de Saldo:**
    * [cite_start]Consultar o saldo atual (quantidade e valor) de produtos específicos[cite: 19].
    * [cite_start]Consultar o saldo total do estoque em um período informado[cite: 20].
* **Relatórios de Movimentação:**
    * [cite_start]Listar todas as entradas [cite: 21] [cite_start]e saídas [cite: 22] registradas.
    * [cite_start]Gerar um extrato de movimentações ordenado por data, detalhando o impacto de cada lançamento no saldo[cite: 23, 24].

---

## 🛠️ Requisitos Técnicos

* **Linguagem:** Java.
* [cite_start]**Interface Gráfica:** Java Swing[cite: 4].
* [cite_start]**Princípios de POO:** O sistema utiliza conceitos de **Herança** e **Interfaces**[cite: 26].
* [cite_start]**Arquitetura:** Construído seguindo uma arquitetura em duas camadas[cite: 69].
* [cite_start]**Persistência:** Os dados são salvos em disco (formato CSV ou binário) e recuperados na inicialização do programa[cite: 27, 28].
* [cite_start]**Testes:** O projeto inclui testes unitários com **JUnit** [cite: 72] [cite_start]para validar as classes da camada de negócios[cite: 71, 73].
* [cite_start]**Documentação:** As classes da camada de negócio são documentadas usando o estilo Javadoc[cite: 70].
