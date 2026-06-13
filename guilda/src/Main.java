public class Main {
    public static void main(String[] args) {

        Guerreiro guerreiro = new Guerreiro("Kael", 2, 100.0, 15);
        Ladino ladino = new Ladino("Sly", 3, 150.0, 20);

        Obstaculo portaPesada = new Obstaculo("Portão de Ferro Enferrujado", "Força", 4, 200.0);
        Obstaculo bauTrancado = new Obstaculo("Baú com Fechadura Mestra", "Ladinagem", 2, 500.0);

        portaPesada.tentarSuperar(guerreiro);
        bauTrancado.tentarSuperar(guerreiro);
        bauTrancado.tentarSuperar(ladino);
    }
}