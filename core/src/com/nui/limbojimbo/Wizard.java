package com.nui.limbojimbo;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * Created by ayushi on 2/15/2017.
 */

public class Wizard extends Actor{
    Sprite sprite = new Sprite(new Texture(Gdx.files.internal("wizard5.png")));
    @Override
    public void draw(Batch batch, float parentAlpha) {
    sprite.draw(batch);
    }
    Wizard(){
        setBounds(getX(), getY(), getWidth(), getHeight());
        setPosition(100f,1500f);
        setHeight(400f);
        setWidth(500f);
    }
}
