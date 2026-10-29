package escola.dominio;

public class Turma {
    private String nome;
    private Serie serie;
    private Aluno[] alunos;
    private Desempenho[][] desempenhos;
    private int quantidade;
    private int qtdDisciplinas;

    public Turma(String nome, Serie serie, int capacidade) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        if (serie == null) {
            throw new IllegalArgumentException("Série está nula");
        }
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade menor ou igual a zero");
        }
        qtdDisciplinas = serie.getDisciplinas().length;
        this.nome = nome.trim();
        this.serie = serie;
        desempenhos = new Desempenho[capacidade][qtdDisciplinas];
        alunos = new Aluno[capacidade];

    }

    public void matricular(Aluno aluno) { 
        for (int i = 0; i < quantidade; i++) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno está nulo");
        }
        if (alunos[i].getMatricula() == aluno.getMatricula()) {
            throw new IllegalStateException("Aluno já está matriculado");
        }
        if (quantidade >= alunos.length) {
            throw new IllegalStateException("Turma cheia");
        }
        }
        alunos[quantidade] = aluno;
        Disciplina[] grade = serie.getDisciplinas();
        for (int j = 0; j < grade.length; j++) {
            desempenhos[quantidade][j] = new Desempenho(aluno, grade[j]);
        }
        quantidade++;
    }

    public void lancarNota(int matricula, String disciplina, int trimestre, double nota) {
        if (matricula <= 0) {
            throw new IllegalArgumentException("Matrícula inválida");
        }
        if (disciplina == null || disciplina.trim().isEmpty()) {
            throw new IllegalArgumentException("Disciplina inválida");
        }
        if (trimestre < 1 || trimestre > 3) {
            throw new IllegalArgumentException("Trimestre inválido");
        }
        if (nota < 0 || nota > 10) {
            throw new NotaInvalidaException("Nota inválida: " + nota);
        }

        for (int i = 0; i < quantidade; i++) {
            if (alunos[i].getMatricula() == matricula) {
                for (int j = 0; j < qtdDisciplinas; j++) {
                    if (serie.getDisciplinas()[j].getNome().equalsIgnoreCase(disciplina)) {
                        desempenhos[i][j].lancarNota(trimestre, nota);
                        return;
                    }
                }
                throw new IllegalArgumentException("Disciplina não encontrada para o aluno");
            }
        }
        throw new IllegalArgumentException("Aluno não encontrado na turma");
    }

    public void registrarFaltas(int matricula, String disciplina, int trimestre, int faltas) {
        if (matricula <= 0) {
            throw new IllegalArgumentException("Matrícula inválida");
        }
        if (disciplina == null || disciplina.trim().isEmpty()) {
            throw new IllegalArgumentException("Disciplina inválida");
        }
        if (faltas < 0) {
            throw new IllegalArgumentException("Número de faltas inválido");
        }
        if (trimestre < 1 || trimestre > 3) {
            throw new IllegalArgumentException("Trimestre inválido");
        }

        for (int i = 0; i < quantidade; i++) {
            if (alunos[i].getMatricula() == matricula) {
                for (int j = 0; j < qtdDisciplinas; j++) {
                    if (serie.getDisciplinas()[j].getNome().equalsIgnoreCase(disciplina)) {
                        desempenhos[i][j].registrarFaltas(trimestre, faltas);
                        return;
                    }
                }
                throw new IllegalArgumentException("Disciplina não encontrada para o aluno");
            }
        }
        throw new IllegalArgumentException("Aluno não encontrado na turma");
    }

    public Situacao situacaoDoAluno(int matricula) {
        if (matricula <= 0) {
            throw new IllegalArgumentException("Matrícula inválida");
        }

        for (int i = 0; i < quantidade; i++) {
            if (alunos[i].getMatricula() == matricula) {
                boolean temEmAndamento = false;
                boolean temReprovadoFaltas = false;
                boolean temReprovadoNota = false;
                boolean temRecuperacao = false;
                for (int j = 0; j < qtdDisciplinas; j++) {
                    Desempenho desempenho = desempenhos[i][j];
                    Situacao situacao = desempenho.situacao();
                    if (situacao == Situacao.EM_ANDAMENTO) {
                        temEmAndamento = true;
                    } else if (situacao == Situacao.REPROVADO_POR_FALTA) {
                        temReprovadoFaltas = true;
                    } else if (situacao == Situacao.REPROVADO_POR_NOTA) {
                        temReprovadoNota = true;
                    } else if (situacao == Situacao.RECUPERACAO) {
                        temRecuperacao = true;
                    }
                }
                if (temEmAndamento) return Situacao.EM_ANDAMENTO;
                if (temReprovadoFaltas) return Situacao.REPROVADO_POR_FALTA;
                if (temReprovadoNota) return Situacao.REPROVADO_POR_NOTA;
                if (temRecuperacao) return Situacao.RECUPERACAO;
                return Situacao.APROVADO;
            }

        }

        throw new IllegalArgumentException("Aluno não encontrado na turma");
    }

    public int contarPorSituacao(Situacao situacao) {
        if (situacao == null) {
            throw new IllegalArgumentException("Situação inválida");
        }
        int contador = 0;
        for (int i = 0; i < quantidade; i++) {
                if (situacaoDoAluno(alunos[i].getMatricula()) == situacao) {
                    contador++;
                }
                
        }
        return contador;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Turma: ").append(nome).append(" - ");
        sb.append("Série: ").append(serie.getNome()).append(": ");
        sb.append(quantidade).append("/").append(alunos.length).append("\n");
        for (int i = 0; i < quantidade; i++) {
            sb.append(alunos[i].toString()).append("\n");
        }
        return sb.toString();
    }
}
