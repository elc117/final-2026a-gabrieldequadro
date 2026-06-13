public class Item {
    private String nome;
    private double preco;
    private int bonusNivel;

    public Item(String nome, double preco, int bonusNivel) {
        this.nome = nome;
        this.preco = preco;
        this.bonusNivel = bonusNivel;
    }


    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getBonusNivel() { return bonusNivel; }

    @Override
    public String toString() {
        return nome + " (Preço: " + preco + " | Bónus: +" + bonusNivel + " de Nível)";
    }
}