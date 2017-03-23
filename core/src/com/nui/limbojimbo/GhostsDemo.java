package com.nui.limbojimbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Utsa on 3/23/2017.
 */

public class GhostsDemo extends Image{
    private int direction;
    private Animation animation;
    private TextureAtlas atlas;
    private Texture gestureImage;
    private float width = 500f;
    private float height = 400f;
    private float stateTime = 0;
    private String type;
    SpriteBatch batch;
    Rectangle bounds;

    public Rectangle getBounds(){
        return bounds;
    }
    private Action moveToCenter(){
        MoveToAction mta = new MoveToAction();
        mta.setPosition(Gdx.graphics.getWidth()/2-width/2,Gdx.graphics.getHeight()/2-height/2-180f);
        mta.setDuration(40f);
        com.nui.limbojimbo.GhostsDemo.this.addAction(mta);
        return mta;
    }

    public GhostsDemo( int direction,Texture texture,TextureAtlas atlas,float X,float Y){
        super(texture);
        this.direction = direction;
        gestureImage = texture;
        this.atlas = atlas;
        animation = new Animation(1/9f,atlas.getRegions());
        bounds = new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        setBounds(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        setHeight(height);
        setWidth(width);
        batch = new SpriteBatch();
        //setZIndex(zindex);
        setPosition(X,Y);
        addAction(moveToCenter());
    }

    public GhostsDemo( int direction, Texture texture,TextureAtlas atlas,float X,float Y, String type){
        super(texture);
        this.direction = direction;
        gestureImage = texture;
        this.atlas = atlas;
        animation = new Animation(1/9f,atlas.getRegions());
        bounds = new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        setBounds(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        setHeight(height);
        setWidth(width);
        this.type = type;
        //setZIndex(zindex);
        setPosition(X,Y);
        addAction(moveToCenter());
    }




    public void setBounds(float x,float y, float height, float width){
        this.bounds.set(x,y,height/2,width/2);
    }
    // changed
//    public void setGesture(Texture t){
//
//        //gestureImage =t;
//        gestureImage.load(t.getTextureData());
//
//
//    }
    public String getType(){
        return this.type;
    }
    @Override
    public void act(float delta)
    {
        //Texture texture = gestureImage;


        batch.begin();
        // batch.draw();
       // batch.draw(gestureImage,getGestureCoords(direction)[0],getGestureCoords(direction)[1],80,80); //changed
        batch.end();
        TextureRegion region = (TextureRegion)animation.getKeyFrame(stateTime+=delta, true);
        ((TextureRegionDrawable)getDrawable()).setRegion(region);

        super.act(delta);
    }
    private void updateBounds() {
        setBounds(getX(), getY(), getWidth(), getHeight());
    }
    private float[] getGestureCoords(int isleft){
        float[] arr =new float[2];
        if(isleft==0){
            arr[0] = getX()+this.getBounds().getWidth()/2;
            arr[1] = getY()+this.getBounds().getHeight()*(6f/4f);
        }
        else{
            arr[0] = getX()+this.getBounds().getWidth()/2;
            arr[1] = getY()+this.getBounds().getHeight()*(6f/4f);

        }
        return arr;
    }


}
