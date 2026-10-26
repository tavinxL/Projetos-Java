package escola.dominio;

public enum Situacao {
    EM_ANDAMENTO("Em andamento"),
    APROVADO("Aprovado"),
    RECUPERACAO("Recuperação"),
    REPROVADO_POR_NOTA("Reprovado por nota"),
    REPROVADO_POR_FALTA("Reprovado por falta");


    private String descricao;

    Situacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
