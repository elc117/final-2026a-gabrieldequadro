public class Mago extends MembroGuilda {
    private int poderMagico;

    public Mago(String nome, int nivel, double custo, int poderMagico) {
        super(nome, nivel, custo);
        this.poderMagico = poderMagico;
    }

    @Override
    public void exibirHabilidade() {
        System.out.println(getNome() + " conjura uma bola de fogo com poder " + poderMagico + "!");
    }

    @Override
    public String toString() {
        return super.toString() + " - Classe: Mago";
    }
}