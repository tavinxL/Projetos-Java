package escola.app;

import escola.dominio.Aluno;
import escola.dominio.Desempenho;
import escola.dominio.Disciplina;
import escola.dominio.Serie;
import escola.dominio.Situacao;
import escola.dominio.Turma;
import escola.dominio.NotaInvalidaException;

public class Main {

    public static void main(String[] args) {
        // Testes antigos comentados para limpar o console
        // System.out.println("=== NOTAS ===");
        // testarNotas();
        // System.out.println("\n=== FALTAS E FREQUÊNCIA ===");
        // testarFaltas();
        // System.out.println("\n=== SITUAÇÃO ===");
        // testarSituacao();

        System.out.println("=== TESTE EXERCÍCIO 5: TURMAS E SÉRIES ===");
        testarTurmasESeries();
    }

    static void testarTurmasESeries() {
        // 1. Criar Disciplinas e a Série
        Disciplina mat = new Disciplina("Matemática", 40);
        Disciplina port = new Disciplina("Português", 30);
        Disciplina[] grade = {mat, port};

        Serie sextoAno = new Serie("6º Ano", grade);

        // 2. Testar Cópia Defensiva da Série (Física não deve aparecer na série)
        grade[0] = new Disciplina("Física", 40); 
        System.out.println("Teste de Cópia Defensiva (Deve continuar Matemática e Português):");
        System.out.println(sextoAno); 
        System.out.println();

        // 3. Criar a Turma com capacidade para 3 alunos
        Turma turma6A = new Turma("6A", sextoAno, 3);

        // 4. Criar Alunos
        Aluno a1 = new Aluno("Otavio");
        Aluno a2 = new Aluno("Ana");
        Aluno a3 = new Aluno("João");
        Aluno a4 = new Aluno("Maria"); // Usado para estourar a capacidade

        // 5. Testar Matrículas
        System.out.println("--- Matrículas ---");
        turma6A.matricular(a1);
        turma6A.matricular(a2);
        turma6A.matricular(a3);
        System.out.println("3 alunos matriculados com sucesso.");
        
        tentarMatricular(turma6A, a1); // Deve dar erro: já matriculado
        tentarMatricular(turma6A, a4); // Deve dar erro: turma cheia
        System.out.println();

        // 6. Lançamentos dos Cenários
        System.out.println("--- Lançando Notas ---");
        // Aluno 1: média 8 nas duas disciplinas (APROVADO)
        lancarAnoTodo(turma6A, a1.getMatricula(), "Matemática", 8, 8, 8);
        lancarAnoTodo(turma6A, a1.getMatricula(), "Português", 8, 8, 8);

        // Aluno 2: média 8 em Mat, 4 em Port (REPROVADO_POR_NOTA)
        lancarAnoTodo(turma6A, a2.getMatricula(), "Matemática", 8, 8, 8);
        lancarAnoTodo(turma6A, a2.getMatricula(), "Português", 4, 4, 4);

        // Aluno 3: só 1 nota em Mat (EM_ANDAMENTO)
        turma6A.lancarNota(a3.getMatricula(), "Matemática", 1, 8.0);
        System.out.println("Notas lançadas com sucesso.\n");

        // 7. Checar a Situação Geral
        System.out.println("--- Situação Geral dos Alunos ---");
        System.out.println(a1.getNome() + ": " + turma6A.situacaoDoAluno(a1.getMatricula()).getDescricao()); // Aprovado
        System.out.println(a2.getNome() + ": " + turma6A.situacaoDoAluno(a2.getMatricula()).getDescricao()); // Reprovado Nota
        System.out.println(a3.getNome() + ": " + turma6A.situacaoDoAluno(a3.getMatricula()).getDescricao()); // Em Andamento
        System.out.println();

        // 8. Checar o Contador
        System.out.println("--- Contadores ---");
        System.out.println("Qtd APROVADO: " + turma6A.contarPorSituacao(Situacao.APROVADO)); // Deve ser 1
        System.out.println("Qtd REPROVADO_POR_NOTA: " + turma6A.contarPorSituacao(Situacao.REPROVADO_POR_NOTA)); // Deve ser 1
        System.out.println("Qtd EM_ANDAMENTO: " + turma6A.contarPorSituacao(Situacao.EM_ANDAMENTO)); // Deve ser 1
        System.out.println();

        // 9. Checar o toString (com o ajuste da formatação que conversamos)
        System.out.println("--- toString da Turma ---");
        System.out.println(turma6A);
    }

    // ---------- Novos Auxiliares para Turma ----------

    static void tentarMatricular(Turma t, Aluno a) {
        try {
            t.matricular(a);
            System.out.println("Aluno " + a.getNome() + " matriculado.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro ao matricular " + a.getNome() + ": " + e.getMessage());
        }
    }

    // Método pra evitar repetir as 3 chamadas de lancarNota
    static void lancarAnoTodo(Turma t, int matricula, String disciplina, double n1, double n2, double n3) {
        t.lancarNota(matricula, disciplina, 1, n1);
        t.lancarNota(matricula, disciplina, 2, n2);
        t.lancarNota(matricula, disciplina, 3, n3);
    }

    // ---------- Testes Antigos e seus Auxiliares (mantidos intactos) ----------

    static void testarNotas() {
        Desempenho d = new Desempenho(new Aluno("Otavio"), new Disciplina("Matematica", 40));

        tentarLancar(d, 1, 8);                                    // ok
        tentarLancar(d, 2, 7);                                    // ok
        System.out.println("Trimestres lançados: " + d.trimestresLancados());   // 2

        tentarLancar(d, 1, 9);                                    // erro: já lançado
        tentarLancar(d, 4, 5);                                    // erro: trimestre inválido
        tentarLancar(d, 3, 11);                                   // erro: nota inválida

        tentarGetNota(d, 3);                                      // erro: sem nota
        System.out.println("Média parcial: " + d.mediaParcial()); // 7.5
        tentarMediaAnual(d);                                      // erro: falta o T3

        tentarLancar(d, 3, 0);                                    // ok (zero é nota válida)
        tentarMediaAnual(d);                                      // 5.0

        // Caso do zero: média parcial com uma única nota 0 não pode dar erro
        Desempenho d2 = new Desempenho(new Aluno("Ana"), new Disciplina("Matematica", 40));
        tentarLancar(d2, 1, 0);
        System.out.println("Média parcial (nota zero): " + d2.mediaParcial());  // 0.0

        System.out.println(d);
    }

    static void testarFaltas() {
        Desempenho d = new Desempenho(new Aluno("Otavio"), new Disciplina("Matematica", 40));

        tentarFaltas(d, 1, 4);                                    // ok
        System.out.println("Frequência T1: " + d.frequenciaTrimestre(1));       // 90.0
        tentarFaltas(d, 1, 6);                                    // ok (acumula: 10)
        System.out.println("Frequência T1: " + d.frequenciaTrimestre(1));       // 75.0

        tentarFaltas(d, 1, 31);                                   // erro: passaria de 40
        tentarFaltas(d, 2, 0);                                    // erro: quantidade inválida
        tentarFaltas(d, 4, 1);                                    // erro: trimestre inválido

        System.out.println("Faltas T1 (nada mudou pelos erros): " + d.getFaltas(1));   // 10
        System.out.println("Total de faltas: " + d.totalFaltas());                       // 10
        System.out.println("Frequência anual: " + String.format("%.2f", d.frequenciaAnual()) + "%");  // 91.67
        System.out.println(d);
    }

    static void testarSituacao() {
        double[][] notas = {
                {8, 7},          // só duas notas
                {8, 7, 9},
                {7, 7, 7},       // média exatamente 7.0
                {6, 5, 5},
                {5, 5, 5},       // média exatamente 5.0
                {4, 5, 5},
                {9, 9, 9},       // 31 faltas: 74,17% de frequência
                {9, 9, 9}        // 30 faltas: exatamente 75% de frequência
        };
        int[] faltasT1 = {0, 0, 0, 0, 0, 0, 31, 30};
        Situacao[] esperado = {
                Situacao.EM_ANDAMENTO,
                Situacao.APROVADO,
                Situacao.APROVADO,
                Situacao.RECUPERACAO,
                Situacao.RECUPERACAO,
                Situacao.REPROVADO_POR_NOTA,
                Situacao.REPROVADO_POR_FALTA,
                Situacao.APROVADO
        };

        for (int i = 0; i < notas.length; i++) {
            Desempenho d = montar("Aluno" + (i + 1), notas[i], faltasT1[i]);
            Situacao obtida = d.situacao();
            String resultado = (obtida == esperado[i]) ? "OK" : "FALHOU";
            System.out.println((i + 1) + ") " + obtida.getDescricao()
                    + " (esperado: " + esperado[i].getDescricao() + ") -> " + resultado);
        }

        System.out.println();
        System.out.println(montar("Aluno9", new double[]{8, 7, 9}, 0));
    }

    static Desempenho montar(String nome, double[] notas, int faltasT1) {
        Desempenho d = new Desempenho(new Aluno(nome), new Disciplina("Matematica", 40));
        for (int i = 0; i < notas.length; i++) {
            d.lancarNota(i + 1, notas[i]);
        }
        if (faltasT1 > 0) {
            d.registrarFaltas(1, faltasT1);
        }
        return d;
    }

    static void tentarLancar(Desempenho d, int trimestre, double nota) {
        try {
            d.lancarNota(trimestre, nota);
        } catch (IllegalArgumentException | IllegalStateException | NotaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void tentarGetNota(Desempenho d, int trimestre) {
        try {
            System.out.println("Nota T" + trimestre + ": " + d.getNota(trimestre));
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void tentarMediaAnual(Desempenho d) {
        try {
            System.out.println("Média anual: " + d.mediaAnual());
        } catch (IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void tentarFaltas(Desempenho d, int trimestre, int quantidade) {
        try {
            d.registrarFaltas(trimestre, quantidade);
            System.out.println("Faltas registradas: " + quantidade + " no T" + trimestre);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}