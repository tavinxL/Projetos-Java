package escola.app;

import escola.dominio.Aluno;
import escola.dominio.Desempenho;
import escola.dominio.Disciplina;
import escola.dominio.Professor;
import escola.dominio.Serie;
import escola.dominio.Turma;

public class Main {

    public static void main(String[] args) {
        // Descomente os testes antigos quando quiser rodá-los:
        // System.out.println("=== 1. TESTE DE NOTAS ===");
        // testarNotas();
        // System.out.println("\n=== 2. TESTE DE FALTAS E FREQUÊNCIA ===");
        // testarFaltas();
        // System.out.println("\n=== 3. TESTE DE SITUAÇÃO ===");
        // testarSituacao();
        // System.out.println("\n=== 4. TESTE DE TURMAS E SÉRIES ===");
        // testarTurmasESeries();
        // System.out.println("\n=== 5. TESTE DE HERANÇA E PROFESSORES ===");
        // testarProfessores();

        System.out.println("=== 6. TESTE DO BOLETIM COMPLETO ===\n");
        testarBoletimCompleto();
    }

    // =========================================================================
    // EXERCÍCIO 7: BOLETIM FORMATADO (NOVO)
    // =========================================================================

    static void testarBoletimCompleto() {
        // 1. Criar Disciplinas
        Disciplina mat = new Disciplina("Matemática", 40);
        Disciplina port = new Disciplina("Português", 40);

        // 2. Criar Professores
        Professor profMat = new Professor("Albert Einstein", 7500.0, new Disciplina[]{mat});
        Professor profPort = new Professor("Machado de Assis", 6000.0, new Disciplina[]{port});

        // 3. Montar Série e Turma
        Serie primeiroAno = new Serie("1º Ano do Ensino Médio", new Disciplina[]{mat, port});
        Turma turma1A = new Turma("1A", primeiroAno, 30);

        // 4. Alocar Professores nas matérias
        turma1A.alocarProfessor(profMat, "Matemática");
        turma1A.alocarProfessor(profPort, "Português");

        // 5. Matricular o Aluno
        Aluno otavio = new Aluno("Otávio Rabelo");
        turma1A.matricular(otavio);

        // 6. Lançar Notas e Faltas (Simulando o ano letivo)
        
        // Matemática (Média alta, poucas faltas -> APROVADO)
        lancarAnoTodo(turma1A, otavio.getMatricula(), "Matemática", 8.0, 9.5, 7.0);
        turma1A.registrarFaltas(otavio.getMatricula(), "Matemática", 1, 2); // 2 faltas no T1

        // Português (Média exata 5.0 -> RECUPERACAO)
        lancarAnoTodo(turma1A, otavio.getMatricula(), "Português", 5.0, 5.0, 5.0);
        turma1A.registrarFaltas(otavio.getMatricula(), "Português", 2, 4); // 4 faltas no T2

        // 7. Imprimir o Boletim Final
        System.out.println(turma1A.gerarBoletim(otavio.getMatricula()));
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

        Serie primeiroAno = new Serie("1º Ano do Ensino Médio", new Disciplina[]{mat, port});
        Turma turma1A = new Turma("1A", primeiroAno, 30);

        try {
            turma1A.alocarProfessor(profExatas, "Matemática");
        } catch (Exception e) {}

        try {
            turma1A.alocarProfessor(profExatas, "Português");
        } catch (IllegalArgumentException e) {}

        try {
            turma1A.alocarProfessor(profHumanas, "História");
        } catch (IllegalArgumentException e) {}
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
        
        Turma turma6A = new Turma("6A", sextoAno, 3);

        Aluno a1 = new Aluno("Otavio");
        Aluno a2 = new Aluno("Ana");
        Aluno a3 = new Aluno("João");
        Aluno a4 = new Aluno("Maria");

        turma6A.matricular(a1);
        turma6A.matricular(a2);
        turma6A.matricular(a3);
        
        tentarMatricular(turma6A, a1);
        tentarMatricular(turma6A, a4);

        lancarAnoTodo(turma6A, a1.getMatricula(), "Matemática", 8, 8, 8);
        lancarAnoTodo(turma6A, a1.getMatricula(), "Português", 8, 8, 8);

        lancarAnoTodo(turma6A, a2.getMatricula(), "Matemática", 8, 8, 8);
        lancarAnoTodo(turma6A, a2.getMatricula(), "Português", 4, 4, 4);

        turma6A.lancarNota(a3.getMatricula(), "Matemática", 1, 8.0);
    }

    // =========================================================================
    // MÉTODOS AUXILIARES COMPARTILHADOS
    // =========================================================================

    static void tentarMatricular(Turma t, Aluno a) {
        try {
            t.matricular(a);
        } catch (IllegalArgumentException | IllegalStateException e) {
            // Ignorado no console para limpeza visual
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
        tentarLancar(d, 1, 9);
        tentarLancar(d, 4, 5);
        tentarLancar(d, 3, 11);
        tentarGetNota(d, 3);
        tentarMediaAnual(d);
        tentarLancar(d, 3, 0);
        tentarMediaAnual(d);
    }

    static void testarFaltas() {
        Desempenho d = new Desempenho(new Aluno("Otavio"), new Disciplina("Matematica", 40));
        tentarFaltas(d, 1, 4);
        tentarFaltas(d, 1, 6);
        tentarFaltas(d, 1, 31);
        tentarFaltas(d, 2, 0);
        tentarFaltas(d, 4, 1);
    }

    static void testarSituacao() {
        double[][] notas = { {8, 7}, {8, 7, 9}, {7, 7, 7}, {6, 5, 5}, {5, 5, 5}, {4, 5, 5}, {9, 9, 9}, {9, 9, 9} };
        int[] faltasT1 = {0, 0, 0, 0, 0, 0, 31, 30};
        
        for (int i = 0; i < notas.length; i++) {
            Desempenho d = montar("Aluno" + (i + 1), notas[i], faltasT1[i]);
            d.situacao();
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
        try { d.lancarNota(trimestre, nota); } catch (Exception e) {}
    }

    static void tentarGetNota(Desempenho d, int trimestre) {
        try { d.getNota(trimestre); } catch (Exception e) {}
    }

    static void tentarMediaAnual(Desempenho d) {
        try { d.mediaAnual(); } catch (Exception e) {}
    }

    static void tentarFaltas(Desempenho d, int trimestre, int quantidade) {
        try { d.registrarFaltas(trimestre, quantidade); } catch (Exception e) {}
    }
}