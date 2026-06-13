public class Main {
    public static void main(String[] args) {
        Guilda guilda = new Guilda("Taverna do Dragão", 500.0);

        // 1. Criando os personagens
        Guerreiro guerreiro = new Guerreiro("Kael", 3, 150.0, 18);
        Mago mago = new Mago("Eldrin", 4, 250.0, 20);
        Guerreiro mercenario = new Guerreiro("Grom", 5, 300.0, 22);

        // 2. Criando as missões
        Missao missaoFacil = new Missao("Limpar o Porão", "Derrotar os ratos gigantes do porão da taverna.", 5, 100.0);
        Missao missaoDificil = new Missao("O Covil do Dragão", "Recuperar o tesouro roubado na montanha.", 10, 500.0);

        // 3. Recrutando apenas o Guerreiro (Nível 3)
        guilda.recrutar(guerreiro);

        // 4. Tentativa 1: Missão Fácil só com o Guerreiro
        // A missão pede dificuldade 5, o guerreiro tem nível 3. Vai falhar!
        guilda.realizarMissao(missaoFacil);

        // 5. Estratégia: Recrutar mais alguém para somar força
        guilda.recrutar(mago); // Mago nível 4. Força total agora será 7!

        // 6. Tentativa 2: Missão Fácil com o grupo maior
        guilda.realizarMissao(missaoFacil);

        guilda.exibirStatus();
        guilda.realizarMissao(missaoDificil);
    }
}