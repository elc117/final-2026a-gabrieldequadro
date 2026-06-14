package com.quadro.guildcontroler.model;
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
            membro.equiparItem(item);
            inventarioItens.remove(item);
        }
    }

    public String getNome() {
        return nome;
    }

    public double getOuro() {
        return ouro;
    }

    public java.util.List<MembroGuilda> getMembros() {
        return membros;
    }

    public java.util.List<Item> getInventarioItens() {
        return inventarioItens;
    }
    public void adicionarOuro(double quantidade) {
        this.ouro += quantidade;
        System.out.println("[GUILDA] Ganhou +" + quantidade + " PO!");
    }
    public void equiparMembroDaGuilda(int indiceMembro) {
        if (membros.size() > indiceMembro && !inventarioItens.isEmpty()) {
            MembroGuilda membro = membros.get(indiceMembro);
            Item item = inventarioItens.remove(0); // Tira o primeiro item do baú

            membro.equiparItem(item);
            System.out.println("[SISTEMA] " + membro.getNome() + " pegou o item " + item.getNome() + " do baú!");
        } else {
            System.out.println("[SISTEMA] Não há heróis nessa posição ou o baú está vazio!");
        }
    }
}