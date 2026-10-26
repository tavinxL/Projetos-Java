package escola.app;

import escola.dominio.Aluno;
import escola.dominio.Desempenho;
import escola.dominio.Disciplina;
import escola.dominio.NotaInvalidaException;
import escola.dominio.Situacao;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== NOTAS ===");
        testarNotas();

        System.out.println();
        System.out.println("=== FALTAS E FREQUÊNCIA ===");
        testarFaltas();

        System.out.println();
        System.out.println("=== SITUAÇÃO ===");
        testarSituacao();
    }

    // ---------- Testes ----------

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

    // ---------- Auxiliares ----------

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
            System.out.println("T" + trimestre + " lançado: " + nota);
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