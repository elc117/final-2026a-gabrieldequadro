public class Main {
    public static void main(String[] args) {

        Guerreiro guerreiro = new Guerreiro("Kael", 4, 150.0, 18);
        Ladino ladino = new Ladino("Sly", 3, 120.0, 20);


        Obstaculo pedraGigante = new Obstaculo("Pedra Bloqueando o Caminho", "Força", 3, 0.0);
        Obstaculo portaTrancada = new Obstaculo("Porta do Tesouro", "Ladinagem", 2, 500.0);


        Expedicao minhaExpedicao = new Expedicao(guerreiro, ladino);

        System.out.println("--- INICIANDO FASE SIDE-SCROLLER ---");
        System.out.println("Controlando atualmente: " + minhaExpedicao.getPersonagemAtivo().getNome());


        minhaExpedicao.enfrentarObstaculo(pedraGigante);
        minhaExpedicao.enfrentarObstaculo(portaTrancada);
        minhaExpedicao.alternarPersonagem();
        minhaExpedicao.enfrentarObstaculo(portaTrancada);
    }
}