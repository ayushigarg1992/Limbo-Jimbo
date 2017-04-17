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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by ayushi on 2/15/2017.
 */

public class Wizard extends Image  {
    private Animation animation;
    private TextureAtlas atlas;
    private float stateTime = 0;
    private float width = 500f;
    private float height = 400f;
    private boolean istouch = false;

    private Rectangle bounds;
    private Action moveToCenter(){
        MoveToAction mta = new MoveToAction();
        mta.setPosition(Gdx.graphics.getWidth()/2-width/2,60);
        mta.setDuration(0f);
        Wizard.this.addAction(mta);
        return mta;
    }
    public void setBounds(float x,float y, float height, float width){
        this.bounds.set(x+30,y-30,height-30,width-30);
    }
    public Rectangle getBounds(){
        return bounds;
    }
    public Wizard(Texture texture){
        super(texture);
        atlas = new TextureAtlas(Gdx.files.internal("witch.atlas"));
        animation = new Animation(1/5f,atlas.getRegions());

        animation.setPlayMode(Animation.PlayMode.NORMAL);


        bounds = new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        setBounds(getX(),getY(),getWidth(),getHeight());
        setPosition(Gdx.graphics.getWidth()/2-width/2,60);
        setHeight(height);
        setWidth(width);
        addAction(moveToCenter());
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //sound.play();
                Gdx.app.log("test", "TouchDown");
                istouch = true;
                //act(float delta);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("test", "touchUp");
                istouch =false;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.app.log("test", "exit");
            }

        });
    }
    public void setTouch(boolean touch){
        this.istouch = touch;
        if (!istouch)
            stateTime= 0;
    }


    @Override
    public void act(float delta)
    {
        if (istouch)
            stateTime += delta;

        TextureRegion region = (TextureRegion) animation.getKeyFrame(stateTime, true);
        ((TextureRegionDrawable) getDrawable()).setRegion(region);
        super.act(delta);
    }

    public void dispose(){
        atlas.dispose();
    }
}
