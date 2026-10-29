package escola.dominio;

public class Professor extends Pessoa {
    private double salario;
    private Disciplina[] disciplinas;

    public Professor(String nome, double salario, Disciplina[] disciplinas) {
        super(nome);
        if (salario <= 0) {
            throw new IllegalArgumentException("Salário inválido");
        }
        if (disciplinas == null) {
            throw new IllegalArgumentException("O professor não pode ser nulo");
        }
        if (disciplinas.length == 0) {
            throw new IllegalArgumentException("O professor deve ter pelo menos uma disciplina");
        }
        this.salario = salario;
        this.disciplinas = disciplinas.clone();
    }

    public double getSalario() {
        return salario;
    }

    public Disciplina[] getDisciplinas() {
        return disciplinas.clone();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNome()).append(" - Salário: ").append(salario).append(" - Disciplinas: ");
        for (int i = 0; i < disciplinas.length; i++) {
            sb.append(disciplinas[i].getNome());
            if (i < disciplinas.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}

