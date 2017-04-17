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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import java.util.*;

import javax.security.auth.login.Configuration;

/**
 * Created by sharm on 4/13/2017.
 */

public class MainGhosts extends Ghosts{
    private int direction;
    private TextureAtlas atlas;
    private TextureAtlas killatlas;
    private float speed = 40f;
    private float rightspeed =5f;
    //SpriteBatch batch;
   // Rectangle bounds;
    float w = 600f;
    float h = 750f;
    private MoveToAction mta;
    private MoveToAction mtr;
    public SequenceAction sequence= new SequenceAction();
    private int animlevel=0;
    int occ=5;

    public Rectangle getBounds(){
        return bounds;
    }
    private Action moveToCenter(){
        mta = new MoveToAction();
        mta.setPosition(Gdx.graphics.getWidth()/2-w/2,40);
        mta.setDuration(speed);
        //MainGhosts.this.addAction(mta);
        return mta;
    }

    private Action moveToRight(float x, float y){
        mtr = new MoveToAction();
        mtr.setPosition(x,y);
        mtr.setPosition(Gdx.graphics.getWidth()-40, Gdx.graphics.getHeight()/2);
        mtr.setDuration(rightspeed);
        // MainGhosts.this.addAction(mtr);
        return mtr;
    }


    public void setSequence( )
    {
        if(occ>0) {
            this.sequence.reset();
            this.sequence.addAction(moveToRight(this.getX(),this.getY()));
           this.sequence.addAction(moveToCenter());

            this.addAction(sequence);
            occ--;
        }
        //return occ;
    }
    public int getOcc()
    {
        return occ;
    }




    public MainGhosts( int direction,List<GestureTexture> texture,TextureAtlas atlas,TextureAtlas killatlas,float X,float Y, float speed){
        super( direction,texture,atlas,killatlas,X, Y, speed,600f,750f);
        this.direction = direction;
       // gestureSet = texture;
        this.atlas = atlas;
        this.killatlas = killatlas;
        this.speed = speed;
       // animation = new Animation(1/9f,atlas.getRegions());
      //  bounds = new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
       // setBounds(this.getX(),this.getY(),this.getWidth(),this.getHeight());
       // setHeight(h);
       // setWidth(w);
        //batch = new SpriteBatch();
        //setZIndex(zindex);
        setPosition(X,Y);

        //setHeight(height);
      //  setWidth(width);
        sequence.addAction(moveToCenter());
       // sequence.addAction(moveToRight());


        this.addAction(sequence);
         //addAction(moveToCenter());
    }


    @Override
    public void setBounds(float x,float y, float height, float width){
        super.setBounds( x, y,  height,  width);
       // bounds.set(x,y,h/2,w/2);
    }


}