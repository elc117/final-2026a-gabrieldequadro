public class Ladino extends MembroGuilda {
    private int furtividade;

    public Ladino(String nome, int nivel, double custo, int furtividade) {
        super(nome, nivel, custo);
        this.furtividade = furtividade;
    }

    @Override
    public void exibirHabilidade() {
        System.out.println(getNome() + " se esconde nas sombras com furtividade " + furtividade + "!");
    }
}