package escola.dominio;

public class Serie {
    private String nome;
    private Disciplina[] disciplinas;

    public Serie(String nome, Disciplina[] disciplinas) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome nulo ou vazio");
        }
        if (disciplinas == null) {
            throw new IllegalArgumentException("Disciplina esta nula");
        }
        if (disciplinas.length == 0) {
            throw new IllegalArgumentException("Disciplina esta vazia");
        }
        for (int i = 0; i < disciplinas.length; i++) {
            if (disciplinas[i] == null) {
                throw new IllegalArgumentException("Posição " + i + " está nula");
            }
        }
        for (int i = 0; i < disciplinas.length; i++) {
            String disciplinaFirst = disciplinas[i].getNome();
            for (int j = 1 + i; j < disciplinas.length; j++) {
                String disciplinaSecond = disciplinas[j].getNome();

                if (disciplinaFirst.equalsIgnoreCase(disciplinaSecond)) {
                    throw new IllegalArgumentException("Disciplinas com o mesmo nome");
                }
            }
        }
        this.nome = nome.trim();
        this.disciplinas = disciplinas.clone();
    }
    public String getNome() {
        return nome;
    }

    public Disciplina[] getDisciplinas() {
        return disciplinas.clone();
    }

    @Override
    public String toString() {
        return nome + " (" + getDisciplinas().length + " disciplinas)";
    }
}





