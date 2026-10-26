# Sistema Escolar

Sistema de gestão escolar em Java, construído por etapas para praticar **orientação a objetos**, **tratamento de exceptions** e **validação de dados**. O projeto cresce junto com os meus estudos: cada etapa adiciona uma funcionalidade nova e, mais para frente, as estruturas simples dão lugar a coleções, arquivos, banco de dados e uma API REST.

> **Status:** em desenvolvimento (Fase 1, só com classes, arrays e exceptions, sem frameworks).

## O que já funciona

### Etapa 1: modelo base

- **`Aluno`**: nome validado e **matrícula gerada automaticamente** (sequencial, a partir de 1).
- **`Disciplina`**: nome e total de aulas por trimestre, ambos validados.

### Etapa 2: notas por trimestre

- **`Desempenho`** liga um aluno a uma disciplina e guarda as notas dos 3 trimestres.
- Lançamento de nota por trimestre, consulta de nota, **média parcial** (só os trimestres lançados) e **média anual** (exige os 3).

### Etapa 3: faltas e frequência

- Registro de faltas por trimestre, **acumulativo**, limitado ao total de aulas da disciplina.
- **Frequência por trimestre** e **frequência anual**, em porcentagem.

### Etapa 4: situação do aluno

- O enum **`Situacao`** representa o resultado do aluno, cada valor com um texto de exibição.
- `Desempenho.situacao()` aplica as regras da escola, combinando média anual e frequência.

## Regras de validação

| Onde | Regra | Resultado se violada |
|---|---|---|
| `Aluno` | Nome não pode ser `null` e precisa ter ao menos 2 caracteres após remover espaços das pontas | `IllegalArgumentException` |
| `Disciplina` | Nome não pode ser `null`, vazio ou só espaços | `IllegalArgumentException` |
| `Disciplina` | Total de aulas por trimestre precisa ser maior que zero | `IllegalArgumentException` |
| `Desempenho` | Aluno e disciplina são obrigatórios | `IllegalArgumentException` |
| `Desempenho` | Trimestre precisa estar entre 1 e 3 | `IllegalArgumentException` |
| `Desempenho` | Nota precisa estar entre 0 e 10 | `NotaInvalidaException` |
| `Desempenho` | Um trimestre só aceita uma nota | `IllegalStateException` |
| `Desempenho` | Ler nota de trimestre sem nota, ou média parcial sem nenhuma nota | `IllegalStateException` |
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
|---|---|---|
| 1 | Menos de 3 trimestres com nota lançada | `EM_ANDAMENTO` |
| 2 | Frequência anual **abaixo de 75%** | `REPROVADO_POR_FALTA` |
| 3 | Média anual **7,0 ou mais** | `APROVADO` |
| 4 | Média anual **5,0 ou mais** (e abaixo de 7,0) | `RECUPERACAO` |
| 5 | Qualquer outro caso | `REPROVADO_POR_NOTA` |

A ordem importa: um aluno com média 9 e 60% de frequência reprova por falta. Os limites ficam em constantes da classe (`MEDIA_APROVACAO`, `MEDIA_RECUPERACAO`, `FREQUENCIA_MINIMA`), então mudar uma regra da escola é alterar uma linha.

## Exemplo de uso

```java
Desempenho d = new Desempenho(new Aluno("Otavio"), new Disciplina("Matematica", 40));

d.lancarNota(1, 8.0);
d.lancarNota(2, 7.0);

d.mediaParcial();            // 7.5
d.mediaAnual();              // IllegalStateException: falta o 3º trimestre
d.lancarNota(1, 9.0);        // IllegalStateException: trimestre já tem nota
d.lancarNota(3, 11);         // NotaInvalidaException: Nota inválida: 11.0
d.situacao();                // EM_ANDAMENTO

d.registrarFaltas(1, 4);
d.frequenciaTrimestre(1);    // 90.0
d.registrarFaltas(1, 6);
d.frequenciaTrimestre(1);    // 75.0
d.registrarFaltas(1, 31);    // IllegalStateException: passaria do total de aulas
d.totalFaltas();             // 10
d.frequenciaAnual();         // 91.666... (110 de 120 aulas)

d.lancarNota(3, 9.0);
d.situacao();                // APROVADO (média 8.0, frequência 91,7%)
d.situacao().getDescricao(); // "Aprovado"
System.out.println(d);       // Otavio - Matematica: T1=8.0 (10 faltas) T2=7.0 (0 faltas) T3=9.0 (0 faltas) => Aprovado
```

## Estrutura do projeto

```
src/
└── escola/
    ├── dominio/
    │   ├── Aluno.java
    │   ├── Disciplina.java
    │   ├── Desempenho.java
    │   ├── Situacao.java
    │   └── NotaInvalidaException.java
    └── app/
        └── Main.java
```

- `escola.dominio`: regras de negócio e entidades. Nenhuma classe daqui imprime nada na tela.
- `escola.app`: ponto de entrada que exercita o domínio e mostra os resultados.

## Como executar

Requisito: **JDK 17 ou superior**.

```bash
# compilar
javac -d out src/escola/dominio/*.java src/escola/app/*.java

# executar
java -cp out escola.app.Main
```

Ou abra o projeto no IntelliJ IDEA e rode a classe `Main`.

A `Main` funciona como roteiro de testes manuais: cada caso imprime o resultado, e os cenários de situação mostram `OK` ou `FALHOU` ao lado do valor esperado. Entre eles estão os casos de fronteira (média exatamente 7,0 e 5,0, frequência exatamente 75%, nota zero).

## Decisões de design

- **Matrícula como estado de instância, contador como estado de classe.** Cada `Aluno` guarda a sua matrícula; o contador é `private static`, compartilhado, e invisível para quem usa a classe.
- **Validar antes de alterar qualquer estado.** Um aluno que falha na criação não consome número de matrícula, e uma nota ou falta inválida não deixa o `Desempenho` pela metade.
- **`Disciplina` é imutável** (só getters, sem setters). O total de aulas é a base do cálculo de frequência, então mudá-lo no meio do ano corromperia os dados dos alunos.
- **Controle separado de "nota lançada".** `0.0` é uma nota válida, então um array de `boolean` indica quais trimestres já têm nota, em vez de usar o zero como "vazio".
- **`NotaInvalidaException` é unchecked.** Nota fora de 0 a 10 é erro de uso (dado errado de quem chama), não uma falha recuperável, e forçar `try/catch` em cada lançamento só poluiria o código.
- **Domínio sem entrada nem saída.** As classes de `escola.dominio` só recebem dados e devolvem resultados, e quem decide o que mostrar é a `Main`. Isso permite reaproveitar o domínio num menu, em testes ou numa API.
- **Divisão em `double`.** O cálculo de frequência multiplica por 100 antes de dividir, evitando que a divisão inteira do Java zere o resultado.
- **Situação como enum, e não como texto.** O compilador garante que só existam valores válidos, e o texto de exibição fica separado da regra de negócio.
- **Constantes nomeadas para os limites da escola** (`static final`), em vez de números soltos espalhados pelo código.

## Roadmap

- [x] Modelo base: `Aluno` e `Disciplina`
- [x] Notas por trimestre e média
- [x] Faltas e frequência
- [x] Situação do aluno (aprovado, recuperação, reprovado)
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
[GitHub](https://github.com/SEU-USUARIO) · [LinkedIn](https://www.linkedin.com/in/SEU-PERFIL)