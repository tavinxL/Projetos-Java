# Sistema Escolar

Sistema de gestão escolar em Java, construído por etapas para praticar **orientação a objetos**, **tratamento de exceptions** e **validação de dados**. O projeto cresce junto com os meus estudos: cada etapa adiciona uma funcionalidade nova e, mais para frente, as estruturas simples dão lugar a coleções, arquivos, banco de dados e uma API REST.

> **Status:** em desenvolvimento (Fase 1, só com classes, arrays e exceptions, sem frameworks).

## O que já funciona

### Etapa 1: modelo base

* **`Aluno`**: herda de `Pessoa`, tem **matrícula gerada automaticamente** (sequencial, a partir de 1).
* **`Disciplina`**: nome e total de aulas por trimestre, ambos validados.

### Etapa 2: notas por trimestre

* **`Desempenho`**: liga um aluno a uma disciplina e guarda as notas dos 3 trimestres.
* Lançamento de nota por trimestre, consulta de nota, **média parcial** (só os trimestres lançados) e **média anual** (exige os 3).

### Etapa 3: faltas e frequência

* Registro de faltas por trimestre, **acumulativo**, limitado ao total de aulas da disciplina.
* **Frequência por trimestre** e **frequência anual**, em porcentagem.

### Etapa 4: situação do aluno

* O enum **`Situacao`** representa o resultado do aluno, cada valor com um texto de exibição.
* `Desempenho.situacao()` aplica as regras da escola, combinando média anual e frequência.

### Etapa 5: turmas e séries

* **`Serie`**: representa o currículo (ex: "6º Ano") e guarda um array de disciplinas.
* **`Turma`**: agrupa uma série, uma capacidade de alunos e uma matriz cruzando Alunos x Disciplinas (`Desempenho[][]`).
* Consolida a matrícula e o lançamento em lote (notas e faltas) varrendo a matriz internamente, além de gerar estatísticas (`contarPorSituacao`).

### Etapa 6: herança e professores

* **`Pessoa`**: superclasse que centraliza o estado e a validação do nome para alunos, professores e funcionários.
* **`Professor`**: herda de `Pessoa`, recebe um salário e domina um array de disciplinas.
* Alocação inteligente na `Turma`: um professor só pode ser designado para uma disciplina que exista na série e que faça parte de sua área de competência.

## Regras de validação

| Onde | Regra | Resultado se violada |
| --- | --- | --- |
| `Pessoa` | Nome não pode ser `null` e precisa ter ao menos 2 caracteres | `IllegalArgumentException` |
| `Professor` | Salário deve ser > 0 e array de disciplinas não nulo/vazio | `IllegalArgumentException` |
| `Disciplina` | Nome não pode ser `null`, vazio ou só espaços | `IllegalArgumentException` |
| `Disciplina` | Total de aulas por trimestre precisa ser maior que zero | `IllegalArgumentException` |
| `Serie` | Nome obrigatório; disciplinas não nulas, não vazias e sem nomes repetidos | `IllegalArgumentException` |
| `Turma` | Nome obrigatório, série preenchida e capacidade > 0 | `IllegalArgumentException` |
| `Turma` | Matricular além da capacidade | `IllegalStateException` |
| `Turma` | Matricular o mesmo aluno duas vezes | `IllegalStateException` |
| `Turma` | Alocar professor em disciplina que ele não leciona ou que não está na série | `IllegalArgumentException` |
| `Desempenho` | Aluno e disciplina são obrigatórios | `IllegalArgumentException` |
| `Desempenho` | Trimestre precisa estar entre 1 e 3 | `IllegalArgumentException` |
| `Desempenho` | Nota precisa estar entre 0 e 10 | `NotaInvalidaException` |
| `Desempenho` | Um trimestre só aceita uma nota | `IllegalStateException` |
| `Desempenho` | Ler nota sem lançamento, ou média parcial sem nota alguma | `IllegalStateException` |
| `Desempenho` | Média anual exige os 3 trimestres lançados | `IllegalStateException` |
| `Desempenho` | Quantidade de faltas precisa ser maior que zero | `IllegalArgumentException` |
| `Desempenho` | Faltas acumuladas não podem passar do total de aulas do trimestre | `IllegalStateException` |

Nomes são guardados já sem espaços nas pontas (`" Bia "` vira `"Bia"`).

### Como a frequência é calculada

```
frequência (%) = (aulas - faltas) * 100 / aulas

```

Na frequência anual, o total de aulas é o do ano inteiro (3 trimestres), e trimestres sem faltas registradas contam como presença total.

### Regras da situação

Avaliadas **nesta ordem**:

| # | Condição | Situação |
| --- | --- | --- |
| 1 | Menos de 3 trimestres com nota lançada | `EM_ANDAMENTO` |
| 2 | Frequência anual **abaixo de 75%** | `REPROVADO_POR_FALTA` |
| 3 | Média anual **7,0 ou mais** | `APROVADO` |
| 4 | Média anual **5,0 ou mais** (e abaixo de 7,0) | `RECUPERACAO` |
| 5 | Qualquer outro caso | `REPROVADO_POR_NOTA` |

A ordem importa: um aluno com média 9 e 60% de frequência reprova por falta. Os limites ficam em constantes da classe (`MEDIA_APROVACAO`, `MEDIA_RECUPERACAO`, `FREQUENCIA_MINIMA`), então mudar uma regra da escola é alterar uma linha.

## Exemplo de uso

```java
Disciplina mat = new Disciplina("Matemática", 40);
Serie primeiroAno = new Serie("1º Ano do EM", new Disciplina[]{ mat });
Turma turma1A = new Turma("1A", primeiroAno, 30);

Professor profMat = new Professor("Albert", 7500.0, new Disciplina[]{ mat });
turma1A.alocarProfessor(profMat, "Matemática");

Aluno aluno1 = new Aluno("Otavio");
turma1A.matricular(aluno1);

turma1A.lancarNota(aluno1.getMatricula(), "Matemática", 1, 8.0);
turma1A.registrarFaltas(aluno1.getMatricula(), "Matemática", 1, 4);

System.out.println(turma1A.situacaoDoAluno(aluno1.getMatricula())); // EM_ANDAMENTO

```

## Estrutura do projeto

```
src/
└── escola/
    ├── dominio/
    │   ├── Pessoa.java
    │   ├── Aluno.java
    │   ├── Professor.java
    │   ├── Disciplina.java
    │   ├── Serie.java
    │   ├── Turma.java
    │   ├── Desempenho.java
    │   ├── Situacao.java
    │   └── NotaInvalidaException.java
    └── app/
        └── Main.java

```

* `escola.dominio`: regras de negócio e entidades. Nenhuma classe daqui imprime nada na tela.
* `escola.app`: ponto de entrada que exercita o domínio e mostra os resultados.

## Como executar

Requisito: **JDK 17 ou superior**.

```bash
# compilar
javac -d out src/escola/dominio/*.java src/escola/app/*.java

# executar
java -cp out escola.app.Main

```

Ou abra o projeto no IntelliJ IDEA e rode a classe `Main`.

A `Main` funciona como roteiro de testes manuais automatizados: cada cenário simula operações reais (como estourar o limite de alunos ou tentar alocar um professor para uma disciplina que ele não domina) e exibe no console se a exceção foi bloqueada corretamente `[OK]` ou se algo `[FALHOU]`.

## Decisões de design

* **Matrícula como estado de instância, contador como estado de classe.** Cada `Aluno` guarda a sua matrícula; o contador é `private static`, compartilhado, e invisível para quem usa a classe.
* **Herança para evitar Shadowing.** `Aluno` e `Professor` herdam de `Pessoa`, reaproveitando o atributo `nome` e as validações via `super()`, sem criar variáveis locais duplicadas.
* **Cópia Defensiva (Clone).** A `Serie` e o `Professor` não guardam nem devolvem as referências de array originais. Eles aplicam o método `.clone()` nos construtores e getters para que o estado interno não seja alterado de fora (ex: alguém mudando as disciplinas do professor na Main).
* **Matriz Bidimensional.** A `Turma` gerencia as notas através de uma matriz `Desempenho[][]` (linhas = alunos, colunas = disciplinas), espelhando o funcionamento real de um diário de classe.
* **Validar antes de alterar qualquer estado.** Um aluno que falha na criação não consome número de matrícula, e uma nota ou falta inválida não deixa o `Desempenho` pela metade.
* **`Disciplina` é imutável** (só getters, sem setters). O total de aulas é a base do cálculo de frequência, então mudá-lo no meio do ano corromperia os dados dos alunos.
* **Controle separado de "nota lançada".** `0.0` é uma nota válida, então um array de `boolean` indica quais trimestres já têm nota, em vez de usar o zero como "vazio".
* **`NotaInvalidaException` é unchecked.** Nota fora de 0 a 10 é erro de uso (dado errado de quem chama), não uma falha recuperável, e forçar `try/catch` em cada lançamento só poluiria o código.
* **Domínio sem entrada nem saída.** As classes de `escola.dominio` só recebem dados e devolvem resultados, e quem decide o que mostrar é a `Main`. Isso permite reaproveitar o domínio num menu, em testes ou numa API.
* **Situação como enum, e não como texto.** O compilador garante que só existam valores válidos, e o texto de exibição fica separado da regra de negócio.

## Roadmap

* [x] Modelo base: `Aluno` e `Disciplina`
* [x] Notas por trimestre e média
* [x] Faltas e frequência
* [x] Situação do aluno (aprovado, recuperação, reprovado)
* [x] Turmas e séries
* [x] Professores e herança
* [ ] Boletim formatado
* [ ] Menu no console
* [ ] Testes automatizados

### Evoluções planejadas

1. Trocar arrays por coleções (`List`)
2. Persistir dados em arquivo
3. Persistir em banco de dados (JDBC)
4. Expor uma API REST

## Autor

Projeto de estudo desenvolvido por Otavio.
[GitHub](https://github.com/SEU-USUARIO) · [LinkedIn](https://www.linkedin.com/in/SEU-PERFIL)