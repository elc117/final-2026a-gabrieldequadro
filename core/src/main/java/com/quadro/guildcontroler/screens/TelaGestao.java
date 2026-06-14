package com.quadro.guildcontroler.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.quadro.guildcontroler.MainGame;
import com.quadro.guildcontroler.model.Guerreiro;
import com.quadro.guildcontroler.model.Mago;
import com.quadro.guildcontroler.model.Ladino;
import com.quadro.guildcontroler.model.MembroGuilda;
import com.quadro.guildcontroler.model.Item;

public class TelaGestao implements Screen {
    private final MainGame game;
    private BitmapFont fonte;

    private int qtdGuerreiros = 1;
    private int qtdMagos = 1;
    private int qtdLadinos = 1;

    public TelaGestao(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("Ecrã de Gestão da Guilda Iniciado!");
        fonte = new BitmapFont();
        fonte.getData().setScale(1.1f);
    }

    @Override
    public void render(float delta) {

        checarTecladoRecrutamento();
        checarTecladoLoja();
        checarTecladoEquipar();
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        // Dados do Topo
        fonte.draw(game.batch, "=== PAINEL DE GESTÃO: " + game.minhaGuilda.getNome().toUpperCase() + " ===", 50, 460);
        fonte.draw(game.batch, "Ouro Atual: " + game.minhaGuilda.getOuro() + " PO", 50, 430);

        // COLUNA DA ESQUERDA
        fonte.draw(game.batch, "--- RECRUTAR MEMBROS ---", 50, 380);
        fonte.draw(game.batch, "[G] Guerreiro (150 PO)", 50, 350);
        fonte.draw(game.batch, "[M] Mago      (250 PO)", 50, 320);
        fonte.draw(game.batch, "[L] Ladino    (120 PO)", 50, 290);

        fonte.draw(game.batch, "--- MERCADO DE ITENS ---", 50, 240);
        fonte.draw(game.batch, "[I] Espada de Ferro  (100 PO | +2)", 50, 210);
        fonte.draw(game.batch, "[K] Cajado Mágico    (200 PO | +3)", 50, 180);
        fonte.draw(game.batch, "[O] Ferramentas Larápio (80 PO | +2)", 50, 150);



        // COLUNA DA DIREITA
        fonte.draw(game.batch, "--- INTEGRANTES DA GUILDA ---", 450, 380);
        int posYMembros = 350;
        if (game.minhaGuilda.getMembros().isEmpty()) {
            fonte.draw(game.batch, "(Nenhum herói na guilda)", 450, posYMembros);
        } else {
            for (MembroGuilda heroi : game.minhaGuilda.getMembros()) {
                fonte.draw(game.batch, "- " + heroi.toString(), 450, posYMembros);
                posYMembros -= 22;
            }
        }

        fonte.draw(game.batch, "--- BAÚ DE ITENS DA GUILDA ---", 450, posYMembros - 20);
        int posYItens = posYMembros - 50;
        if (game.minhaGuilda.getInventarioItens().isEmpty()) {
            fonte.draw(game.batch, "(Nenhum item comprado)", 450, posYItens);
        } else {
            for (Item item : game.minhaGuilda.getInventarioItens()) {
                fonte.draw(game.batch, "* " + item.getNome() + " (+" + item.getBonusNivel() + " Nvl)", 450, posYItens);
                posYItens -= 22;
            }
        }
        fonte.draw(game.batch, "--- ARMAR HERÓIS ---", 50, 100);
        fonte.draw(game.batch, "Use os números [1], [2] ou [3] para", 50, 70);
        fonte.draw(game.batch, "dar o 1º item do Baú ao herói da lista", 50, 45);
        fonte.draw(game.batch, "[Pressione ESPAÇO para ir à Expedição]", 450, 45);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (game.minhaGuilda.getMembros().size() >= 2) {
                MembroGuilda heroi1 = game.minhaGuilda.getMembros().get(0);
                MembroGuilda heroi2 = game.minhaGuilda.getMembros().get(1);

                com.quadro.guildcontroler.model.Expedicao novaExpedicao =
                        new com.quadro.guildcontroler.model.Expedicao(heroi1, heroi2);
                game.setScreen(new TelaPlataforma(game, novaExpedicao));
            } else {
                System.out.println("[TAVERNA] Precisas de pelo menos 2 heróis para iniciar uma expedição!");
            }

        }

        game.batch.end();
    }

    private void checarTecladoRecrutamento() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            Guerreiro g = new Guerreiro("Guerreiro " + qtdGuerreiros, 1, 150.0, 15);
            if (game.minhaGuilda.recrutar(g)) qtdGuerreiros++;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Mago m = new Mago("Mago " + qtdMagos, 1, 250.0, 18);
            if (game.minhaGuilda.recrutar(m)) qtdMagos++;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            Ladino l = new Ladino("Ladino " + qtdLadinos, 1, 120.0, 14);
            if (game.minhaGuilda.recrutar(l)) qtdLadinos++;
        }
    }
    private void checarTecladoLoja() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            Item espada = new Item("Espada de Ferro", 100.0, 2);
            game.minhaGuilda.comprarItem(espada);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            Item cajado = new Item("Cajado Mágico", 200.0, 3);
            game.minhaGuilda.comprarItem(cajado);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            Item ferramentas = new Item("Ferramentas de Ladrão", 80.0, 2);
            game.minhaGuilda.comprarItem(ferramentas);
        }
    }
    private void checarTecladoEquipar() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            game.minhaGuilda.equiparMembroDaGuilda(0); // Equipa o 1º membro da lista
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
            game.minhaGuilda.equiparMembroDaGuilda(1); // Equipa o 2º membro
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_3)) {
            game.minhaGuilda.equiparMembroDaGuilda(2); // Equipa o 3º membro
        }
    }




    @Override public void dispose() { fonte.dispose(); }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { dispose(); }
}