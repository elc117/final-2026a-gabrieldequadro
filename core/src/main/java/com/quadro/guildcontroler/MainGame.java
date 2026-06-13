package com.quadro.guildcontroler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.quadro.guildcontroler.model.Guilda;
import com.quadro.guildcontroler.screens.TelaGestao;

public class MainGame extends Game {
    public SpriteBatch batch;
    public Guilda minhaGuilda;

    @Override
    public void create() {
        batch = new SpriteBatch();
        minhaGuilda = new Guilda("Aventureiros do Quadro", 1000.0);
        this.setScreen(new TelaGestao(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}