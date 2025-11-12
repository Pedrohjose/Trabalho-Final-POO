# 📦 Sistema de Controle de Estoque - Loja de Informática

Este repositório contém o código-fonte do trabalho final da disciplina de **Programação Orientada a Objetos**, da Universidade Regional de Blumenau (FURB), ministrada pelo Prof. André Felipe Bürger.

O projeto é um software desktop para o controle de estoque de uma loja de informática, desenvolvido em Java com interface gráfica (GUI) utilizando **Java Swing**.

---

## 👥 Equipe

* Carlos Alfredo
* Filipe Tiago
* Pedro Henrique

---

## ✨ Funcionalidades Principais

O sistema foi projetado para atender aos seguintes requisitos:

* **Cadastro de Produtos:** Permite cadastrar produtos com código, nome, preço, quantidade e categoria.
    * **Categorias:** Componentes de hardware, periféricos, acessórios e outros.
* **Registro de Entradas:** Registrar a entrada de itens no estoque, informando produto, data, quantidade e valor.
* **Registro de Saídas:** Registrar saídas de produtos (como vendas, uso interno, devoluções, etc.), informando produto, data e quantidade.
* **Consultas de Saldo:**
    * Consultar o saldo atual (quantidade e valor) de produtos específicos.
    * Consultar o saldo total do estoque em um período informado.
* **Relatórios de Movimentação:**
    * Listar todas as entradas e saídas registradas.
    * Gerar um extrato de movimentações ordenado por data, detalhando o impacto de cada lançamento no saldo.

---

## 🛠️ Requisitos Técnicos

* **Linguagem:** Java.
* **Interface Gráfica:** Java Swing.
* **Princípios de POO:** O sistema utiliza conceitos de **Herança** e **Interfaces**.
* **Arquitetura:** Construído seguindo uma arquitetura em duas camadas.
* **Persistência:** Os dados são salvos em disco (formato CSV ou binário) e recuperados na inicialização do programa.
* **Testes:** O projeto inclui testes unitários com **JUnit** para validar as classes da camada de negócios.
* **Documentação:** As classes da camada de negócio são documentadas usando o estilo Javadoc.
