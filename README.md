# Sistema Escolar

Sistema de gestão escolar em Java, construído por etapas para praticar **orientação a objetos**, **tratamento de exceptions** e **validação de dados**. O projeto cresce junto com os meus estudos: cada etapa adiciona uma funcionalidade nova e, mais para frente, as estruturas simples dão lugar a coleções, arquivos, banco de dados e uma API REST.

> **Status:** em desenvolvimento (Fase 1, só com classes, arrays e exceptions, sem frameworks).

## O que já funciona

### Etapa 1: modelo base

- **`Aluno`**: nome validado e **matrícula gerada automaticamente** (sequencial, a partir de 1).
- **`Disciplina`**: nome e total de aulas por trimestre, ambos validados.

### Regras de validação

| Classe | Regra | Resultado se violada |
|---|---|---|
| `Aluno` | Nome não pode ser `null` e precisa ter ao menos 2 caracteres após remover espaços das pontas | `IllegalArgumentException` |
| `Disciplina` | Nome não pode ser `null`, vazio ou só espaços | `IllegalArgumentException` |
| `Disciplina` | Total de aulas por trimestre precisa ser maior que zero | `IllegalArgumentException` |

Nomes são guardados já sem espaços nas pontas (`" Bia "` vira `"Bia"`).

### Exemplo de saída

```
Criado: [1] GB
Criado: [2] Ana
Criado: [3] Otavio
Erro: Nome do aluno precisa ter no mínimo 2 caracteres
Erro: Nome do aluno precisa ter no mínimo 2 caracteres
Erro: Nome do aluno precisa ter no mínimo 2 caracteres
Erro: Nome do aluno precisa ter no mínimo 2 caracteres
Criado: [4] Bia

Criada: Matemática (40 aulas/trimestre)
Erro: Numero de aulas inválido
Erro: Nome da disciplina vazio
Erro: Numero de aulas inválido

Total de alunos: 4
```

Os quatro nomes inválidos não consomem matrícula: o contador só avança depois que a validação passa.

## Estrutura do projeto

```
src/
└── escola/
    ├── dominio/
    │   ├── Aluno.java
    │   └── Disciplina.java
    └── app/
        └── Main.java
```

- `escola.dominio`: regras de negócio e entidades.
- `escola.app`: ponto de entrada que exercita o domínio.

## Como executar

Requisito: **JDK 17 ou superior**.

```bash
# compilar
javac -d out src/escola/dominio/*.java src/escola/app/*.java

# executar
java -cp out escola.app.Main
```

Ou abra o projeto no IntelliJ IDEA e rode a classe `Main`.

## Decisões de design

- **Matrícula como estado de instância, contador como estado de classe.** Cada `Aluno` guarda a sua matrícula; o contador é `private static`, compartilhado, e invisível para quem usa a classe.
- **Validar antes de alterar qualquer estado.** Um aluno que falha na criação não consome número de matrícula.
- **`Disciplina` é imutável** (só getters, sem setters). O total de aulas serve de base para o cálculo de frequência, então mudá-lo no meio do ano corromperia os dados dos alunos.
- **Exceptions com mensagens claras**, para que o erro diga o que está errado, e não apenas que algo deu errado.

## Roadmap

- [x] Modelo base: `Aluno` e `Disciplina`
- [ ] Notas por trimestre e média
- [ ] Faltas e frequência
- [ ] Situação do aluno (aprovado, recuperação, reprovado)
- [ ] Turmas e séries
- [ ] Boletim formatado
- [ ] Menu no console
- [ ] Testes automatizados

### Evoluções planejadas

1. Trocar arrays por coleções (`List`)
2. Persistir dados em arquivo
3. Persistir em banco de dados (JDBC)
4. Expor uma API REST

## Autor

Projeto de estudo desenvolvido por Otavio.
[GitHub](https://github.com/tavinxL) · [LinkedIn](https://www.linkedin.com/in/otavio-souza-46b8bb3a6/)
