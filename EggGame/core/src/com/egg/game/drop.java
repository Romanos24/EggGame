package com.egg.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Roman on 23.02.2017.
 */

public class drop extends Game {

   SpriteBatch batch;
   BitmapFont font;

   @Override
   public void create () {
       batch = new SpriteBatch();
       font = new BitmapFont();
       this.setScreen(new MainMenuScreen(this));
   }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
