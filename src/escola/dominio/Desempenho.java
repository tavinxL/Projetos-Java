package escola.dominio;



public class Desempenho {
    private Aluno aluno;
    private Disciplina disciplina;
    private double[] notas = {0.0, 0.0, 0.0};
    private boolean[] lancada = {false, false, false};

    public Desempenho(Aluno aluno, Disciplina disciplina) {
        if (aluno == null || disciplina == null) {
            throw new IllegalArgumentException("Aluno ou disciplina não pode ficar vazia");
        }
        this.aluno = aluno;
        this.disciplina = disciplina;
    }

    public void lancarNota(int trimestre, double nota) {
        if (trimestre < 1 || trimestre > 3) {
            throw new IllegalArgumentException("Trimestre inválido");
        }
        if (nota < 0 || nota > 10) {
            throw new NotaInvalidaException("Nota inválida: " + nota);
        }
        if (lancada[trimestre - 1]) {
            throw new IllegalStateException("Trimestre com nota existente");
        }
        notas[trimestre - 1] = nota;
        lancada[trimestre - 1] = true;
    }

    public double getNota(int trimestre) {
        if (trimestre < 1 || trimestre > 3) {
            throw new IllegalArgumentException("Trimestre inválido");
        }
        if (!lancada[trimestre - 1]) {
            throw new IllegalStateException("Trimestre sem nota");
        }
        return notas[trimestre - 1];
    }

    public int trimestresLancados() {
        int contador = 0;
        for (int i = 0; i < lancada.length; i++) {
            if (lancada[i]) {
                contador++;
            }
        }
        return contador;
    }

    public double mediaParcial() {
        double media = 0;
        double soma = 0;
        int contador = 0;
        for (int i = 0; i < notas.length; i++) {
            if (lancada[i]) {
                soma = soma + notas[i];
                contador++;
            }
        }
        if (contador == 0) {
            throw new IllegalStateException("Nenhum trimestre lançado");
        }
        media = soma / contador;
        return media;
    }



    public double mediaAnual() {
        double media = 0;
        double soma = 0;
        for (int i = 0; i < notas.length; i++) {
            if (!lancada[i]) {
                throw new IllegalStateException("Trimestre sem nota");
            } else {
                soma = soma + notas[i];
            }
        }
        media = soma / notas.length;
        return media;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(aluno.getNome()).append(" - ").append(disciplina.getNome()).append(": ");
        for (int i = 0; i < notas.length; i++) {
            sb.append("T").append(i + 1).append("=");
            if (lancada[i]) {
                sb.append(notas[i]);
            } else {
                sb.append("--");
            }
            if (i < notas.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}


