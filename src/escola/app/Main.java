package escola.app;

import escola.dominio.Aluno;
import escola.dominio.Desempenho;
import escola.dominio.Disciplina;
import escola.dominio.NotaInvalidaException;
import escola.dominio.Professor;
import escola.dominio.Serie;
import escola.dominio.Situacao;
import escola.dominio.Turma;

public class Main {

    public static void main(String[] args) {
        // Descomente os testes que deseja executar no terminal:
        
        // System.out.println("=== 1. TESTE DE NOTAS ===");
        // testarNotas();

        // System.out.println("\n=== 2. TESTE DE FALTAS E FREQUÊNCIA ===");
        // testarFaltas();

        // System.out.println("\n=== 3. TESTE DE SITUAÇÃO ===");
        // testarSituacao();

        // System.out.println("\n=== 4. TESTE DE TURMAS E SÉRIES ===");
        // testarTurmasESeries();

        System.out.println("=== 5. TESTE DE HERANÇA E PROFESSORES ===");
        testarProfessores();
    }

    // =========================================================================
    // EXERCÍCIO 6: HERANÇA E PROFESSORES
    // =========================================================================

    static void testarProfessores() {
        Disciplina mat = new Disciplina("Matemática", 40);
        Disciplina fis = new Disciplina("Física", 30);
        Disciplina port = new Disciplina("Português", 40);

        Professor profExatas = new Professor("Albert Einstein", 7500.0, new Disciplina[]{mat, fis});
        Professor profHumanas = new Professor("Machado de Assis", 6000.0, new Disciplina[]{port});

        System.out.println("--- Professores Criados ---");
        System.out.println(profExatas);
        System.out.println(profHumanas);
        System.out.println();

        Serie primeiroAno = new Serie("1º Ano do Ensino Médio", new Disciplina[]{mat, port});
        Turma turma1A = new Turma("1A", primeiroAno, 30);

        System.out.println("--- Testes de Alocação na Turma 1A ---");
        
        try {
            turma1A.alocarProfessor(profExatas, "Matemática");
            System.out.println("[OK] Professor " + profExatas.getNome() + " alocado para Matemática!");
        } catch (Exception e) {
            System.out.println("[FALHA] Erro inesperado: " + e.getMessage());
        }

        try {
            turma1A.alocarProfessor(profExatas, "Português");
            System.out.println("[FALHA] Aceitou professor numa matéria que ele não leciona!");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Erro bloqueado corretamente: " + e.getMessage());
        }

        try {
            turma1A.alocarProfessor(profHumanas, "História");
            System.out.println("[FALHA] Aceitou matéria inexistente na série!");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Erro bloqueado corretamente: " + e.getMessage());
        }
    }

    // =========================================================================
    // EXERCÍCIO 5: TURMAS E SÉRIES
    // =========================================================================

    static void testarTurmasESeries() {
        Disciplina mat = new Disciplina("Matemática", 40);
        Disciplina port = new Disciplina("Português", 30);
        Disciplina[] grade = {mat, port};

        Serie sextoAno = new Serie("6º Ano", grade);

        grade[0] = new Disciplina("Física", 40); 
        System.out.println("Teste de Cópia Defensiva:");
        System.out.println(sextoAno); 
        System.out.println();

        Turma turma6A = new Turma("6A", sextoAno, 3);

        Aluno a1 = new Aluno("Otavio");
        Aluno a2 = new Aluno("Ana");
        Aluno a3 = new Aluno("João");
        Aluno a4 = new Aluno("Maria");

        System.out.println("--- Matrículas ---");
        turma6A.matricular(a1);
        turma6A.matricular(a2);
        turma6A.matricular(a3);
        
        tentarMatricular(turma6A, a1);
        tentarMatricular(turma6A, a4);
        System.out.println();

        System.out.println("--- Lançando Notas ---");
        lancarAnoTodo(turma6A, a1.getMatricula(), "Matemática", 8, 8, 8);
        lancarAnoTodo(turma6A, a1.getMatricula(), "Português", 8, 8, 8);

        lancarAnoTodo(turma6A, a2.getMatricula(), "Matemática", 8, 8, 8);
        lancarAnoTodo(turma6A, a2.getMatricula(), "Português", 4, 4, 4);

        turma6A.lancarNota(a3.getMatricula(), "Matemática", 1, 8.0);

        System.out.println("--- Situação Geral dos Alunos ---");
        System.out.println(a1.getNome() + ": " + turma6A.situacaoDoAluno(a1.getMatricula()).getDescricao());
        System.out.println(a2.getNome() + ": " + turma6A.situacaoDoAluno(a2.getMatricula()).getDescricao());
        System.out.println(a3.getNome() + ": " + turma6A.situacaoDoAluno(a3.getMatricula()).getDescricao());
        System.out.println();

        System.out.println("--- Contadores ---");
        System.out.println("Qtd APROVADO: " + turma6A.contarPorSituacao(Situacao.APROVADO));
        System.out.println("Qtd REPROVADO_POR_NOTA: " + turma6A.contarPorSituacao(Situacao.REPROVADO_POR_NOTA));
        System.out.println("Qtd EM_ANDAMENTO: " + turma6A.contarPorSituacao(Situacao.EM_ANDAMENTO));
        System.out.println();

        System.out.println("--- toString da Turma ---");
        System.out.println(turma6A);
    }

    static void tentarMatricular(Turma t, Aluno a) {
        try {
            t.matricular(a);
            System.out.println("Aluno " + a.getNome() + " matriculado.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro ao matricular " + a.getNome() + ": " + e.getMessage());
        }
    }

    static void lancarAnoTodo(Turma t, int matricula, String disciplina, double n1, double n2, double n3) {
        t.lancarNota(matricula, disciplina, 1, n1);
        t.lancarNota(matricula, disciplina, 2, n2);
        t.lancarNota(matricula, disciplina, 3, n3);
    }

    // =========================================================================
    // TESTES ANTERIORES (NOTAS, FALTAS E SITUAÇÃO INDIVIDUAL)
    // =========================================================================

    static void testarNotas() {
        Desempenho d = new Desempenho(new Aluno("Otavio"), new Disciplina("Matematica", 40));

        tentarLancar(d, 1, 8);
        tentarLancar(d, 2, 7);
        System.out.println("Trimestres lançados: " + d.trimestresLancados());

        tentarLancar(d, 1, 9);
        tentarLancar(d, 4, 5);
        tentarLancar(d, 3, 11);

        tentarGetNota(d, 3);
        System.out.println("Média parcial: " + d.mediaParcial());
        tentarMediaAnual(d);

        tentarLancar(d, 3, 0);
        tentarMediaAnual(d);

        Desempenho d2 = new Desempenho(new Aluno("Ana"), new Disciplina("Matematica", 40));
        tentarLancar(d2, 1, 0);
        System.out.println("Média parcial (nota zero): " + d2.mediaParcial());

        System.out.println(d);
    }

    static void testarFaltas() {
        Desempenho d = new Desempenho(new Aluno("Otavio"), new Disciplina("Matematica", 40));

        tentarFaltas(d, 1, 4);
        System.out.println("Frequência T1: " + d.frequenciaTrimestre(1));
        tentarFaltas(d, 1, 6);
        System.out.println("Frequência T1: " + d.frequenciaTrimestre(1));

        tentarFaltas(d, 1, 31);
        tentarFaltas(d, 2, 0);
        tentarFaltas(d, 4, 1);

        System.out.println("Faltas T1: " + d.getFaltas(1));
        System.out.println("Total de faltas: " + d.totalFaltas());
        System.out.println("Frequência anual: " + String.format("%.2f", d.frequenciaAnual()) + "%");
        System.out.println(d);
    }

    static void testarSituacao() {
        double[][] notas = {
                {8, 7},
                {8, 7, 9},
                {7, 7, 7},
                {6, 5, 5},
                {5, 5, 5},
                {4, 5, 5},
                {9, 9, 9},
                {9, 9, 9}
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