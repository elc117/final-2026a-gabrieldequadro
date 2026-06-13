public class Main {
    public static void main(String[] args) {
        Guilda guilda = new Guilda("Taverna do Dragão", 300.0);

        Guerreiro guerreiro = new Guerreiro("Kael", 3, 150.0, 18);
        guilda.recrutar(guerreiro);


        Missao missaoDificil = new Missao("O Covil do Dragão", "Recuperar o tesouro.", 6, 800.0);
        guilda.realizarMissao(missaoDificil);


        Item espadaLendaria = new Item("Espada de Cristal", 120.0, 3);
        guilda.comprarItem(espadaLendaria);


        guilda.darItemAoMembro(guerreiro, espadaLendaria);


        guilda.exibirStatus();


        guilda.realizarMissao(missaoDificil);


        guilda.exibirStatus();
    }
}