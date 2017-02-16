package com.nui.limbojimbo;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

/**
 * Created by ayushi on 2/15/2017.
 */

public class Ghosts extends Image{
    private  Animation animation;
    private Action moveToCenter(){
        MoveToAction mta = new MoveToAction();
        mta.setPosition(700f,500f);
        mta.setDuration(7f);
        Ghosts.this.addAction(mta);
        return mta;
    }
    private Action moveToRight(){
        MoveToAction mta = new MoveToAction();
        mta.setPosition(1700f,700f);
        mta.setDuration(0f);
        Ghosts.this.addAction(mta);
        return mta;
    }
    public Ghosts(Texture texture,float X,float Y){
        super(texture);
        setBounds(getX(),getY(),getWidth(),getHeight());
        setHeight(400f);
        setWidth(500f);
        setPosition(X,Y);
        addAction(moveToCenter());
        }
    private void updateBounds() {
        setBounds(getX(), getY(), getWidth(), getHeight());
    }


}
