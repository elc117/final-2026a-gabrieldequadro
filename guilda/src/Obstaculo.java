public class Obstaculo {
    private String nome;
    private String tipoDesafio; // força magia ladinagem"trocar nome" por enquanto
    private int nivelNecessario;
    private double recompensaOculta;

    public Obstaculo(String nome, String tipoDesafio, int nivelNecessario, double recompensaOculta) {
        this.nome = nome;
        this.tipoDesafio = tipoDesafio;
        this.nivelNecessario = nivelNecessario;
        this.recompensaOculta = recompensaOculta;
    }


    public boolean tentarSuperar(MembroGuilda personagem) {
        System.out.println("\n[Fase Side-Scroller] Você encontrou: " + nome);


        String profissao = personagem.getClass().getSimpleName();

        boolean temAClasseCerta = false;


        if (tipoDesafio.equals("Força") && profissao.equals("Guerreiro")) temAClasseCerta = true;
        if (tipoDesafio.equals("Magia") && profissao.equals("Mago")) temAClasseCerta = true;
        if (tipoDesafio.equals("Ladinagem") && profissao.equals("Ladino")) temAClasseCerta = true;

        if (temAClasseCerta && personagem.getNivel() >= nivelNecessario) {
            System.out.println("-> " + personagem.getNome() + " usou suas habilidades para superar o obstáculo!");
            System.out.println("-> Você encontrou " + recompensaOculta + " de ouro escondido!");
            return true;
        } else {
            System.out.println("-> " + personagem.getNome() + " não tem a classe ou o nível necessário para passar por aqui.");
            return false;
        }
    }

    public double getRecompensaOculta() { return recompensaOculta; }
}