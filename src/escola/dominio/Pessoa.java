package escola.dominio;

public class Pessoa {
    private String nome;

    public Pessoa(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        if (nome.length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres");
        }
        this.nome = nome;

    }

    public String getNome() {
        return nome;
    }
}
