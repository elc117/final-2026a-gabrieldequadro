public class Main {
    public static void main(String[] args) {
        // 1. Instancia a guilda
        Guilda guilda = new Guilda("Taverna do Dragão", 500.0);
        guilda.exibirStatus();

        // 2. Instancia personagens com atributos de Dungeons & Dragons
        Guerreiro guerreiro = new Guerreiro("Kael", 3, 150.0, 18);
        Mago mago = new Mago("Eldrin", 4, 250.0, 20);
        Guerreiro mercenario = new Guerreiro("Grom", 5, 300.0, 22);

        // 3. Testa a lógica de negócio (recrutamento e saldo)
        guilda.recrutar(guerreiro);
        guilda.recrutar(mago);
        guilda.recrutar(mercenario); // Deve falhar por falta de ouro

        // 4. Exibe o resultado
        guilda.exibirStatus();

        // 5. Testa o polimorfismo
        guilda.prepararParaAventura();
    }
}