import java.util.ArrayList;
import java.util.List;

public class Guilda {
    private String nome;
    private double ouro;
    private List<MembroGuilda> membros;

    public Guilda(String nome, double ouroInicial) {
        this.nome = nome;
        this.ouro = ouroInicial;
        this.membros = new ArrayList<>();
    }

    public boolean recrutar(MembroGuilda candidato) {
        if (ouro >= candidato.getCustoRecrutamento()) {
            ouro -= candidato.getCustoRecrutamento();
            membros.add(candidato);
            System.out.println("[SUCESSO] " + candidato.getNome() + " entrou para o grupo!");
            return true;
        } else {
            System.out.println("[ERRO] Ouro insuficiente para " + candidato.getNome() + ".");
            return false;
        }
    }

    public void exibirStatus() {
        System.out.println("\n--- GUILDA: " + nome + " ---");
        System.out.println("Caixa: " + ouro + " moedas de ouro");
        System.out.println("Integrantes:");
        for (MembroGuilda m : membros) {
            System.out.println(" - " + m.toString());
        }
        System.out.println("--------------------------\n");
    }

    public void prepararParaAventura() {
        System.out.println("\nPreparando o grupo para a exploração:");
        for (MembroGuilda m : membros) {
            m.exibirHabilidade();
        }
    }
}