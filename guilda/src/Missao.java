public class Missao {
    private String titulo;
    private String descricao;
    private int dificuldadeRequerida; // A soma dos níveis dos membros precisa atingir isso
    private double recompensaOuro;

    public Missao(String titulo, String descricao, int dificuldadeRequerida, double recompensaOuro) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dificuldadeRequerida = dificuldadeRequerida;
        this.recompensaOuro = recompensaOuro;
    }


    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public int getDificuldadeRequerida() { return dificuldadeRequerida; }
    public double getRecompensaOuro() { return recompensaOuro; }
}