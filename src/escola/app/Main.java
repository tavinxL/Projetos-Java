package escola.app;

import escola.dominio.Aluno;
import escola.dominio.Desempenho;
import escola.dominio.Disciplina;
import escola.dominio.NotaInvalidaException;

public class Main {
    static void main(String[] args) {

        Desempenho d = new Desempenho(new Aluno("Otavio"), new Disciplina("Matematica", 40));

        try {
            d.lancarNota(1, 8);
            System.out.println("Notas lançadas com sucesso");
        } catch (IllegalArgumentException | IllegalStateException | NotaInvalidaException e) {
            System.out.println(e.getMessage());
        }

        try {
            d.lancarNota(2, 7);
            System.out.println("Notas lançadas com sucesso");
        } catch (IllegalArgumentException | IllegalStateException | NotaInvalidaException e) {
            System.out.println(e.getMessage());
        }


        try {
            d.lancarNota(3, 0);
        } catch (IllegalArgumentException | IllegalStateException | NotaInvalidaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(d.trimestresLancados());



        try {
            d.lancarNota(1, 9);
        } catch (IllegalArgumentException | IllegalStateException | NotaInvalidaException e) {
            System.out.println(e.getMessage());
        }

        try {
            d.lancarNota(4, 5);
        } catch (IllegalArgumentException | IllegalStateException | NotaInvalidaException e) {
            System.out.println(e.getMessage());
        }

        try {
            d.lancarNota(3, 11);
        } catch (IllegalArgumentException | IllegalStateException | NotaInvalidaException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(d.getNota(3));
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(d.mediaParcial());

        try {
            System.out.println(d.mediaAnual());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(d);

        Desempenho d2 = new Desempenho(new Aluno("Ana"), new Disciplina("Matematica", 40));

        try {
            d2.lancarNota(1, 0);
        } catch (IllegalArgumentException | IllegalStateException | NotaInvalidaException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(d2.mediaParcial());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        try {
            d.registrarFaltas(1, 4);
            System.out.println("Faltas registradas");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        try {
            d.registrarFaltas(1, 6);
            System.out.println("Faltas registradas");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        try {
            d.registrarFaltas(1, 31);
            System.out.println("Faltas registradas");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        try {
            d.registrarFaltas(2, 0);
            System.out.println("Faltas registradas");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        try {
            d.registrarFaltas(4, 1);
            System.out.println("Faltas registradas");

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(d.getFaltas(1));
        System.out.println(d.totalFaltas());
        System.out.printf("%.2f%n", d.frequenciaAnual());
        System.out.println(d.frequenciaTrimestre(1));
        System.out.println(d);
    }
}