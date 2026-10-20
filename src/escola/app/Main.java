package escola.app;

import escola.dominio.Aluno;
import escola.dominio.Disciplina;

public class Main {
    static void main(String[] args) {
        String[] nomesTeste = {"GB", "Ana", "Otavio", "", "A", "  ", null, " Bia "};
        String[] disciplinasTeste = {"Matemática", "Português", "", "História"};
        int[] aulasDisciplinas = {40, 0, 30, -5};

        for (String nome : nomesTeste) {
            try {
                Aluno a = new Aluno(nome);
                System.out.println("Criado: " + a);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        System.out.println();

        for (int i = 0; i < aulasDisciplinas.length; i++) {
            try {
                Disciplina b = new Disciplina(disciplinasTeste[i], aulasDisciplinas[i]);
                System.out.println("Criada: " + b);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        System.out.println();
        System.out.println("Total de alunos: " + Aluno.getTotalAlunos());



    }
}



