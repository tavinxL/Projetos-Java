package escola.dominio;

public class Disciplina {
    private String nome;
    private int totalAulasPorTrimestre;

    public Disciplina(String nome, int totalAulasPorTrimestre) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da disciplina vazio");
        }
        if (totalAulasPorTrimestre <= 0) {
            throw new IllegalArgumentException("Numero de aulas inválido");
        }
        this.nome = nome.trim();
        this.totalAulasPorTrimestre = totalAulasPorTrimestre;
    }

    public String getNome() {
        return nome;
    }

    public int getTotalAulasPorTrimestre() {
        return totalAulasPorTrimestre;
    }

    @Override
    public String toString() {
        return nome + " (" + totalAulasPorTrimestre + " aulas/trimestre)";
    }
}
