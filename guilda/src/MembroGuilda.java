public abstract class MembroGuilda {
    private String nome;
    private int nivel;
    private double custoRecrutamento;

    public MembroGuilda(String nome, int nivel, double custoRecrutamento) {
        this.nome = nome;
        this.nivel = nivel;
        this.custoRecrutamento = custoRecrutamento;
    }

    public String getNome() { return nome; }
    public double getCustoRecrutamento() { return custoRecrutamento; }

    public abstract void exibirHabilidade();

    @Override
    public String toString() {
        return nome + " (Nível " + nivel + ")";
    }
}