package com.quadro.guildcontroler.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.quadro.guildcontroler.MainGame;
import com.quadro.guildcontroler.model.*;

public class TelaGestao implements Screen {
    private final MainGame game;
    private Stage stage;
    private Skin skin;
    private Label labelOuro;
    private Label labelListaMembros;
    private Label labelListaItens;
    private int qtdGuerreiros = 1;
    private int qtdMagos = 1;
    private int qtdLadinos = 1;

    public TelaGestao(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(game.viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        criarSkinBasica();
        Table tabelaPrincipal = new Table();
        tabelaPrincipal.setFillParent(true);
        stage.addActor(tabelaPrincipal);
        labelOuro = new Label("", skin);
        labelListaMembros = new Label("", skin);
        labelListaItens = new Label("", skin);
        TextButton btnGuerreiro = new TextButton("Guerreiro (150 PO)", skin);
        TextButton btnMago = new TextButton("Mago (250 PO)", skin);
        TextButton btnLadino = new TextButton("Ladino (120 PO)", skin);
        TextButton btnEspada = new TextButton("Espada (100 PO | +1)", skin);
        TextButton btnCajado = new TextButton("Cajado (400 PO | +3)", skin);
        TextButton btnFerramentas = new TextButton("Ferramentas (200 PO | +2)", skin);
        TextButton btnEquipar1 = new TextButton("Armar Heroi 1", skin);
        TextButton btnEquipar2 = new TextButton("Armar Heroi 2", skin);
        TextButton btnEquipar3 = new TextButton("Armar Heroi 3", skin);
        TextButton btnExpedicao = new TextButton("INICIAR EXPEDICAO", skin);
        btnGuerreiro.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { if (game.minhaGuilda.recrutar(new Guerreiro("Guerreiro " + qtdGuerreiros, 1, 150.0, 15))) { qtdGuerreiros++; atualizarDadosDaTela(); } }});
        btnMago.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { if (game.minhaGuilda.recrutar(new Mago("Mago " + qtdMagos, 1, 250.0, 18))) { qtdMagos++; atualizarDadosDaTela(); } }});
        btnLadino.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { if (game.minhaGuilda.recrutar(new Ladino("Ladino " + qtdLadinos, 1, 120.0, 14))) { qtdLadinos++; atualizarDadosDaTela(); } }});
        btnEspada.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { game.minhaGuilda.comprarItem(new Item("Espada de Ferro", 100.0, 2)); atualizarDadosDaTela(); }});
        btnCajado.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { game.minhaGuilda.comprarItem(new Item("Cajado Magico", 200.0, 3)); atualizarDadosDaTela(); }});
        btnFerramentas.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { game.minhaGuilda.comprarItem(new Item("Ferramentas", 80.0, 2)); atualizarDadosDaTela(); }});
        btnEquipar1.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { game.minhaGuilda.equiparMembroDaGuilda(0); atualizarDadosDaTela(); }});
        btnEquipar2.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { game.minhaGuilda.equiparMembroDaGuilda(1); atualizarDadosDaTela(); }});
        btnEquipar3.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { game.minhaGuilda.equiparMembroDaGuilda(2); atualizarDadosDaTela(); }});
        btnExpedicao.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.minhaGuilda.getMembros().size() >= 2) {
                    game.setScreen(new TelaPlataforma(game, new Expedicao(game.minhaGuilda.getMembros())));
                } else {
                    System.out.println("Você precisa de pelo menos 2 heróis para fazer a mecânica de equipe!");
                }
            }
        });

        Table tabelaEsquerda = new Table();
        tabelaEsquerda.add(new Label("--- RECRUTAR ---", skin)).padBottom(10).colspan(3).row();
        tabelaEsquerda.add(btnGuerreiro).pad(5).width(200);
        tabelaEsquerda.add(btnMago).pad(5).width(200);
        tabelaEsquerda.add(btnLadino).pad(5).width(200).row();
        tabelaEsquerda.add(new Label("--- MERCADO ---", skin)).padTop(20).padBottom(10).colspan(3).row();
        tabelaEsquerda.add(btnEspada).pad(5).width(200);
        tabelaEsquerda.add(btnCajado).pad(5).width(200);
        tabelaEsquerda.add(btnFerramentas).pad(5).width(200).row();
        tabelaEsquerda.add(new Label("--- ARMAR ---", skin)).padTop(20).padBottom(10).colspan(3).row();
        tabelaEsquerda.add(btnEquipar1).pad(5).width(200);
        tabelaEsquerda.add(btnEquipar2).pad(5).width(200);
        tabelaEsquerda.add(btnEquipar3).pad(5).width(200).row();
        Table tabelaDireita = new Table();
        tabelaDireita.top().left();
        tabelaDireita.add(new Label("--- MEMBROS DA GUILDA ---", skin)).padBottom(10).row();
        tabelaDireita.add(labelListaMembros).padBottom(30).row();
        tabelaDireita.add(new Label("--- BAU DE ITENS ---", skin)).padBottom(10).row();
        tabelaDireita.add(labelListaItens).row();
        tabelaPrincipal.add(new Label("=== TAVERNA: " + game.minhaGuilda.getNome().toUpperCase() + " ===", skin)).colspan(2).padBottom(10).row();
        tabelaPrincipal.add(labelOuro).colspan(2).padBottom(30).row();
        tabelaPrincipal.add(tabelaEsquerda).padRight(50).top();
        tabelaPrincipal.add(tabelaDireita).top().row();
        tabelaPrincipal.add(btnExpedicao).colspan(2).padTop(50).width(350).height(60);
        atualizarDadosDaTela();
    }

    private void atualizarDadosDaTela() {
        labelOuro.setText("Ouro Atual: " + game.minhaGuilda.getOuro() + " PO");

        StringBuilder sbMembros = new StringBuilder();
        if (game.minhaGuilda.getMembros().isEmpty()) sbMembros.append("(Nenhum heroi)\n");
        for (MembroGuilda m : game.minhaGuilda.getMembros()) sbMembros.append("- ").append(m.toString()).append("\n");
        labelListaMembros.setText(sbMembros.toString());
        StringBuilder sbItens = new StringBuilder();
        if (game.minhaGuilda.getInventarioItens().isEmpty()) sbItens.append("(Bau vazio)\n");
        for (Item i : game.minhaGuilda.getInventarioItens()) sbItens.append("* ").append(i.getNome()).append(" (+").append(i.getBonusNivel()).append(" Nvl)\n");
        labelListaItens.setText(sbItens.toString());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    private void criarSkinBasica() {
        skin = new Skin();
        BitmapFont font = new BitmapFont(); font.getData().setScale(1.2f); skin.add("default", font);
        Pixmap pixmapDown = new Pixmap(1, 1, Pixmap.Format.RGBA8888); pixmapDown.setColor(Color.DARK_GRAY); pixmapDown.fill();
        skin.add("fundo_pressionado", new Texture(pixmapDown));
        Pixmap pixmapUp = new Pixmap(1, 1, Pixmap.Format.RGBA8888); pixmapUp.setColor(Color.valueOf("#2a2a35")); pixmapUp.fill();
        skin.add("fundo_normal", new Texture(pixmapUp));
        Label.LabelStyle labelStyle = new Label.LabelStyle(); labelStyle.font = skin.getFont("default"); labelStyle.fontColor = Color.WHITE; skin.add("default", labelStyle);
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.newDrawable("fundo_normal"); buttonStyle.down = skin.newDrawable("fundo_pressionado"); buttonStyle.over = skin.newDrawable("fundo_pressionado"); buttonStyle.font = skin.getFont("default");
        skin.add("default", buttonStyle);
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void dispose() { stage.dispose(); skin.dispose(); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { dispose(); }
}