package escola.dominio;

public class Aluno {
    private String nome;
    private int matricula;
    private static int contadorMatricula = 1;

    public Aluno(String nome) {
        if (nome == null || nome.trim().length() < 2) {
            throw new IllegalArgumentException("Nome do aluno precisa ter no mínimo 2 caracteres");
        }
        this.nome = nome.trim();
        this.matricula = contadorMatricula;
        contadorMatricula++;

    }

    public String getNome() {
        return nome;
    }


    public int getMatricula() {
        return matricula;
    }

    @Override
    public String toString() {
        return "[" + matricula + "] " + nome;
    }

    public static int getTotalAlunos() {
        return contadorMatricula - 1;
    }
}
