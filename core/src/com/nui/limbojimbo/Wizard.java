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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by ayushi on 2/15/2017.
 */

public class Wizard extends Image {
    private Animation animation;
    private TextureAtlas atlas;
    private float stateTime = 0;
    private Rectangle bounds;
    private Action moveToCenter(){
        MoveToAction mta = new MoveToAction();
        mta.setPosition(900f,500f);
        mta.setDuration(0f);
        Wizard.this.addAction(mta);
        return mta;
    }
    public Rectangle getBounds(){
        return bounds;
    }
    public Wizard(Texture texture){
        super(texture);
        atlas = new TextureAtlas(Gdx.files.internal("wizard.atlas"));
        animation = new Animation(1/5f,atlas.getRegions());
        bounds = new Rectangle(getX(),getY(),getWidth(),getHeight());
        setBounds(getX(),getY(),getWidth(),getHeight());
        setPosition(1200f,1200f);
        setHeight(400f);
        setWidth(500f);
        addAction(moveToCenter());
    }
    public void act(float delta)
    {
        TextureRegion region = (TextureRegion)animation.getKeyFrame(stateTime+=delta, true);
        ((TextureRegionDrawable)getDrawable()).setRegion(region);
        super.act(delta);
    }
}
