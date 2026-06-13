
import java.util.ArrayList;
import java.util.List;

public class Guilda {
    private String nome;
    private double ouro;
    private List<MembroGuilda> membros;
    private List<Item> inventarioItens = new ArrayList<>();

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

    public void realizarMissao(Missao missao) {
        System.out.println("\n>>> INICIANDO MISSÃO: " + missao.getTitulo() + " <<<");
        System.out.println("Objetivo: " + missao.getDescricao());

        if (membros.isEmpty()) {
            System.out.println("[FALHA] A guilda não tem membros para enviar nesta missão!");
            return;
        }

        int forcaTotalGrupo = 0;
        for (MembroGuilda m : membros) {
            forcaTotalGrupo += m.getNivel();
        }

        System.out.println("Força total do grupo: " + forcaTotalGrupo + " | Dificuldade da missão: " + missao.getDificuldadeRequerida());

        if (forcaTotalGrupo >= missao.getDificuldadeRequerida()) {
            this.ouro += missao.getRecompensaOuro();
            System.out.println("[VITÓRIA] Missão concluída com sucesso!");
            System.out.println("+ " + missao.getRecompensaOuro() + " moedas de ouro adicionadas ao caixa.");
        } else {
            System.out.println("[DERROTA] O grupo não foi forte o suficiente e precisou recuar.");
        }
    }

    public void comprarItem(Item item) {
            if (this.ouro >= item.getPreco()) {
                this.ouro -= item.getPreco();
                inventarioItens.add(item);
                System.out.println("[LOJA] Comprou " + item.getNome() + " para o inventário da guilda.");
            } else {
                System.out.println("[LOJA] Ouro insuficiente para comprar " + item.getNome());
            }
        }

        public void darItemAoMembro(MembroGuilda membro, Item item) {
            if (inventarioItens.contains(item)) {
                membro.equipar(item);
                inventarioItens.remove(item); // O item sai do baú da guilda e vai para o herói
            } else {
                System.out.println("[ERRO] Esse item não está no inventário da guilda.");
            }
        }
    }
