package escola.dominio;

public class Aluno extends Pessoa {
    private int matricula;
    private static int contadorMatricula = 1;

    public Aluno(String nome) {
        super(nome != null ? nome.trim() : null);   
        this.matricula = contadorMatricula;
        contadorMatricula++;

    }

    public int getMatricula() {
        return matricula;
    }

    @Override
    public String toString() {
        return "[" + matricula + "] " + getNome();
    }

    public static int getTotalAlunos() {
        return contadorMatricula - 1;
    }
}
