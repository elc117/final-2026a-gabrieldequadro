package com.quadro.guildcontroler.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.quadro.guildcontroler.MainGame;
import com.quadro.guildcontroler.model.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TelaPlataforma implements Screen {
    private final MainGame game;
    private final Expedicao expedicao;
    private ShapeRenderer shapeRenderer;
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer mapRenderer;
    private List<Rectangle> colisoesMapa;
    private Rectangle jogadorRect;
    private float jogadorVelocidadeX = 300f;
    private float velocidadeY = 0f;
    private final float GRAVIDADE = -1200f;
    private final float FORCA_PULO = 550f;
    private boolean estaNoChao = true;
    private boolean isFurtivo = false;
    private float tempoFurtividade = 0f;
    private List<Rectangle> moedas;
    private Rectangle obstaculoVisual;
    private Obstaculo obstaculoLogico;
    private boolean portaDestruida = false;
    private Rectangle guardaFisico;
    private Rectangle guardaVisao;
    private com.badlogic.gdx.graphics.Texture texturaAssets;
    private com.badlogic.gdx.graphics.g2d.TextureRegion imagemMoeda;
    private com.badlogic.gdx.graphics.g2d.TextureRegion imagemPorta;
    private com.badlogic.gdx.graphics.Texture imagemFundo;
    private com.badlogic.gdx.graphics.Texture texGuerreiro, texMago, texLadino, texGuarda, texSaida;
    private com.badlogic.gdx.graphics.g2d.TextureRegion imgGuerreiro, imgMago, imgLadino;
    private Rectangle saidaVisual;
    private List<Rectangle> armadilhas;
    private com.badlogic.gdx.graphics.g2d.TextureRegion imagemarmadilha;

    public TelaPlataforma(MainGame game, Expedicao expedicao) {
        this.game = game;
        this.expedicao = expedicao;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        jogadorRect = new Rectangle(50, 200, 32, 32);
        imagemFundo = new com.badlogic.gdx.graphics.Texture("backgrounbd.png");

        mapa = new TmxMapLoader().load("mapa.tmx");
        texturaAssets = new com.badlogic.gdx.graphics.Texture("Assets.png");
        imagemMoeda = new com.badlogic.gdx.graphics.g2d.TextureRegion(texturaAssets, 240, 1, 16, 16);
        imagemPorta = new com.badlogic.gdx.graphics.g2d.TextureRegion(texturaAssets, 112, 32, 16, 64);
        imagemarmadilha = new com.badlogic.gdx.graphics.g2d.TextureRegion(texturaAssets, 80, 96, 16, 16);
        mapRenderer = new OrthogonalTiledMapRenderer(mapa, 2f);
        texGuerreiro = new com.badlogic.gdx.graphics.Texture("guerreiro.png");
        texMago = new com.badlogic.gdx.graphics.Texture("mago.png");
        texLadino = new com.badlogic.gdx.graphics.Texture("ladino.png");
        texGuarda = new com.badlogic.gdx.graphics.Texture("guarda.png");
        texSaida = new com.badlogic.gdx.graphics.Texture("saida.png");
        int wG = texGuerreiro.getWidth() / 3;
        imgGuerreiro = new com.badlogic.gdx.graphics.g2d.TextureRegion(texGuerreiro, wG, 0, wG, texGuerreiro.getHeight());
        int wM = texMago.getWidth() / 3;
        imgMago = new com.badlogic.gdx.graphics.g2d.TextureRegion(texMago, wM, 0, wM, texMago.getHeight());
        int wL = texLadino.getWidth() / 3;
        imgLadino = new com.badlogic.gdx.graphics.g2d.TextureRegion(texLadino, wL, 0, wL, texLadino.getHeight());

        colisoesMapa = new ArrayList<>();
        moedas = new ArrayList<>();
        armadilhas = new ArrayList<>();
        obstaculoLogico = new Obstaculo("Porta", "Força", 1, 200.0);


        MapObjects objetosDoMapa = mapa.getLayers().get("Colisoes").getObjects();

        for (MapObject objeto : objetosDoMapa) {
            if (objeto instanceof RectangleMapObject) {
                Rectangle rectOriginal = ((RectangleMapObject) objeto).getRectangle();
                Rectangle rect = new Rectangle(
                        rectOriginal.x * 2f, rectOriginal.y * 2f,
                        rectOriginal.width * 2f, rectOriginal.height * 2f
                );

                String nomeDoObjeto = objeto.getName();

                if (nomeDoObjeto == null) {
                    colisoesMapa.add(rect);
                } else if (nomeDoObjeto.equalsIgnoreCase("moeda")) {
                    moedas.add(rect);
                } else if (nomeDoObjeto.equalsIgnoreCase("porta")) {
                    obstaculoVisual = rect;
                } else if (nomeDoObjeto.equalsIgnoreCase("guarda")) {
                    guardaFisico = rect;
                    // Visão do guarda 150 pixels para a esquerda
                    guardaVisao = new Rectangle(guardaFisico.x - 150, guardaFisico.y, 150, guardaFisico.height);
                } else if (nomeDoObjeto.equalsIgnoreCase("saida")) {
                    saidaVisual = rect;
                } else if (nomeDoObjeto.equalsIgnoreCase("armadilha")) {
                    armadilhas.add(rect);
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        MembroGuilda heroiAtivo = expedicao.getPersonagemAtivo();

        // --- 1. INPUTS E LÓGICA ---
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            expedicao.alternarPersonagem();
            isFurtivo = false;
        }
        if (saidaVisual != null && jogadorRect.overlaps(saidaVisual)) {
            System.out.println("🎉 EXPEDIÇÃO CONCLUÍDA!");
            game.setScreen(new TelaGestao(game));
            return;
        }

        // --- COLISÃO COM AS ARMADILHAS (DERROTA) ---
        for (Rectangle trap : armadilhas) {
            if (jogadorRect.overlaps(trap)) {
                System.out.println("💀 MORREU NOS ESPINHOS!");
                game.setScreen(new TelaGestao(game));
                return;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && !portaDestruida && obstaculoVisual != null) {
            float centroJogador = jogadorRect.x + (jogadorRect.width / 2);
            float centroPorta = obstaculoVisual.x + (obstaculoVisual.width / 2);
            if (Math.abs(centroJogador - centroPorta) <= 70f) {
                if (obstaculoLogico.tentarSuperar(heroiAtivo)) {
                    portaDestruida = true;
                    game.minhaGuilda.adicionarOuro(obstaculoLogico.getRecompensaOculta());
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q) && !isFurtivo && heroiAtivo instanceof Ladino) {
            isFurtivo = true; tempoFurtividade = 3.0f;
        }

        if (isFurtivo) {
            tempoFurtividade -= delta;
            if (tempoFurtividade <= 0) isFurtivo = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) game.setScreen(new TelaGestao(game));

        // --- 2. FÍSICA VERTICAL (Gravidade e Pulo) ---
        if (!estaNoChao) velocidadeY += GRAVIDADE * delta;

        if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) && estaNoChao) {
            velocidadeY = FORCA_PULO;
        }

        jogadorRect.y += velocidadeY * delta;
        estaNoChao = false;

        for (Rectangle parede : colisoesMapa) {
            if (jogadorRect.overlaps(parede)) {
                if (velocidadeY < 0) {
                    jogadorRect.y = parede.y + parede.height;
                    estaNoChao = true;
                } else if (velocidadeY > 0) {
                    jogadorRect.y = parede.y - jogadorRect.height;
                }
                velocidadeY = 0f;
            }
        }

        // --- 3. FÍSICA HORIZONTAL ---
        float posicaoAntigaX = jogadorRect.x;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) jogadorRect.x -= jogadorVelocidadeX * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) jogadorRect.x += jogadorVelocidadeX * delta;
        if (jogadorRect.x < 0) jogadorRect.x = 0;

        for (Rectangle parede : colisoesMapa) {
            if (jogadorRect.overlaps(parede)) {
                jogadorRect.x = posicaoAntigaX;
            }
        }

        if (!portaDestruida && obstaculoVisual != null && jogadorRect.overlaps(obstaculoVisual)) {
            jogadorRect.x = posicaoAntigaX;
        }

        if (guardaVisao != null && jogadorRect.overlaps(guardaVisao) && !isFurtivo) {
            System.out.println("🚨 ALARME! O Guarda te viu!");
            game.setScreen(new TelaGestao(game));
            return;
        }

        Iterator<Rectangle> iter = moedas.iterator();
        while (iter.hasNext()) {
            Rectangle moeda = iter.next();
            if (jogadorRect.overlaps(moeda)) {
                game.minhaGuilda.adicionarOuro(10);
                iter.remove();
            }
        }

        // --- 4. RENDERIZAÇÃO GRÁFICA ---
        game.camera.update();
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.batch.draw(imagemFundo,
                game.camera.position.x - (game.camera.viewportWidth / 2f),
                game.camera.position.y - (game.camera.viewportHeight / 2f),
                game.camera.viewportWidth,
                game.camera.viewportHeight);
        game.batch.end();

        mapRenderer.setView(game.camera);
        mapRenderer.render();

        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();

        for (Rectangle moeda : moedas) {
            game.batch.draw(imagemMoeda, moeda.x, moeda.y, moeda.width, moeda.height);
        }

        if (!portaDestruida && obstaculoVisual != null) {
            game.batch.draw(imagemPorta, obstaculoVisual.x, obstaculoVisual.y, obstaculoVisual.width, obstaculoVisual.height);
        }

        if (guardaFisico != null) {
            game.batch.draw(texGuarda, guardaFisico.x, guardaFisico.y, guardaFisico.width, guardaFisico.height);
        }

        if (isFurtivo) game.batch.setColor(0.4f, 0.4f, 0.4f, 0.6f);

        if (heroiAtivo instanceof Guerreiro) {
            game.batch.draw(imgGuerreiro, jogadorRect.x, jogadorRect.y, jogadorRect.width, jogadorRect.height);
        } else if (heroiAtivo instanceof Mago) {
            game.batch.draw(imgMago, jogadorRect.x, jogadorRect.y, jogadorRect.width, jogadorRect.height);
        } else if (heroiAtivo instanceof Ladino) {
            game.batch.draw(imgLadino, jogadorRect.x, jogadorRect.y, jogadorRect.width, jogadorRect.height);
        }

        if (saidaVisual != null) {
            game.batch.draw(texSaida, saidaVisual.x, saidaVisual.y, saidaVisual.width, saidaVisual.height);
        }

        for (Rectangle trap : armadilhas) {
            game.batch.draw(imagemarmadilha, trap.x, trap.y, trap.width, trap.height);
        }

        game.batch.setColor(1f, 1f, 1f, 1f); // Reseta a cor para não bugar o próximo frame
        game.batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(game.camera.combined);
        shapeRenderer.begin(ShapeType.Filled);

        if (guardaFisico != null) {
            shapeRenderer.setColor(1f, 0.8f, 0f, 0.3f);
            shapeRenderer.rect(guardaVisao.x, guardaVisao.y, guardaVisao.width, guardaVisao.height);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override public void resize(int width, int height) { game.viewport.update(width, height); }
    @Override public void dispose() {
        shapeRenderer.dispose();
        if(mapa != null) mapa.dispose();
        if(mapRenderer != null) mapRenderer.dispose();
        if(texGuerreiro != null) texGuerreiro.dispose();
        if(texMago != null) texMago.dispose();
        if(texLadino != null) texLadino.dispose();
        if(texGuarda != null) texGuarda.dispose();
        if(texSaida != null) texSaida.dispose();
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { dispose(); }
}