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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import java.util.List;

import javax.security.auth.login.Configuration;

/**
 * Created by ayushi on 2/15/2017.
 */

public class Ghosts extends Image{
    private int direction;
    private Animation animation;
    private TextureAtlas atlas;
    private TextureAtlas killatlas;
    private Texture gestureImage;
    private float width = 500f;
    private float height = 400f;
    private float stateTime = 0;
    public boolean isdead = false;
    private String type;
    public List<GestureTexture> gestureSet;
    private float speed = 40f;
    SpriteBatch batch;
    Rectangle bounds;
    String gest;
    private MoveToAction mta;

    public Rectangle getBounds(){
        return bounds;
    }
    private Action moveToCenter(){
        mta = new MoveToAction();
        mta.setPosition(Gdx.graphics.getWidth()/2-width/2,40);
        mta.setDuration(speed);
        Ghosts.this.addAction(mta);
        return mta;
    }
    public Ghosts(Texture texture){
        super(texture);
    }
    public Ghosts( int direction,Texture texture,TextureAtlas atlas,float X,float Y){
        super(texture);
        this.direction = direction;
        gestureImage = texture;
        this.atlas = atlas;
        this.gest=null;
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
    /*public Ghosts(int direction, List<Texture> texture, TextureAtlas atlas, float X, float Y, float speed) {
        this.direction = direction;
        this.gestureSet = texture;
        this.atlas = atlas;
        this.speed = speed;
        animation = new Animation(1/9f,atlas.getRegions());
        bounds = new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        setBounds(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        setHeight(height);
        setWidth(width);
        batch = new SpriteBatch();
        //setZIndex(zindex);
        setPosition(X,Y);
        addAction(moveToCenter());
    }*/
    public Ghosts( int direction,Texture texture,TextureAtlas atlas,float X,float Y, float speed){
        super(texture);
        this.direction = direction;
        gestureImage = texture;
        this.atlas = atlas;
        this.speed = speed;
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


    public Ghosts(Texture texture,TextureAtlas atlas,TextureAtlas killatlas,float X,float Y, float speed , boolean isdemo){
        super(texture);
        this.direction = direction;
        gestureImage = texture;
        this.atlas = atlas;
        this.killatlas = killatlas;
        this.speed = speed;
        animation = new Animation(1/9f,atlas.getRegions());
        bounds = new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        setBounds(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        setHeight(height);
        setWidth(width);
        batch = new SpriteBatch();
        //setZIndex(zindex);
        setPosition(X,Y);
      //  addAction(moveToCenter());
        if (isdemo ==false)
            addAction(moveToCenter());
    }

    public Ghosts( int direction, Texture texture,TextureAtlas atlas,float X,float Y, String type){
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


    public Ghosts( int direction,Texture texture,TextureAtlas atlas,TextureAtlas killatlas,float X,float Y, float speed){
        super(texture);
        this.direction = direction;
        gestureImage = texture;
        this.atlas = atlas;
        this.killatlas = killatlas;
        this.speed = speed;
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
    public Ghosts( int direction,List<GestureTexture> texture,TextureAtlas atlas,TextureAtlas killatlas,float X,float Y, float speed){
        super(texture.get(0).getText());
        this.direction = direction;
        gestureSet = texture;
        this.atlas = atlas;
        this.killatlas = killatlas;
        this.speed = speed;
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


    public Ghosts( int direction,List<GestureTexture> texture,TextureAtlas atlas,TextureAtlas killatlas,float X,float Y, float speed,float w, float h){
        super(texture.get(0).getText());
        width = w;
        height = h;
        this.direction = direction;
        gestureSet = texture;
        this.atlas = atlas;
        this.killatlas = killatlas;
        this.speed = speed;
        animation = new Animation(1/9f,atlas.getRegions());
        setHeight(h);
        setWidth(w);
        bounds = new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        setBounds(this.getX(),this.getY(),this.getWidth(),this.getHeight());

        batch = new SpriteBatch();
        //setZIndex(zindex);
        setPosition(X,Y);
        addAction(moveToCenter());
    }
    public void setBounds(float x,float y, float height, float width){
        this.bounds.set(x,y,height/2,width/2);
    }

    public void setKillatlas(TextureAtlas atlas){
        this.killatlas = atlas;
    }
    // changed
//    public void setGesture(Texture t){
//
//        //gestureImage =t;
//        gestureImage.load(t.getTextureData());
//
//
//    }

    public void setDead(){
        this.isdead = true;
        animation = new Animation(1/4f,killatlas.getRegions());
        bounds = new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        setBounds(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        stateTime = 0;
        setHeight(height);
        setWidth(width);
    }
    public void setGestureSet(List<GestureTexture> Set)
    {
        this.gestureSet=Set;
    }


    public void setUndead(){
        this.isdead = false;
    }


    public String getType(){
        return this.type;
    }
    public String getGest(){
        return this.gest;
    }

    public void setGest(String s){
        this.gest=s;
    }

    @Override
    public void act(float delta)
    {

            if (isdead && animation.isAnimationFinished(stateTime)) {
                remove();

            } else if (isdead) {
                this.removeAction(mta);
                TextureRegion region = (TextureRegion) animation.getKeyFrame(stateTime += delta, false);
                ((TextureRegionDrawable) getDrawable()).setRegion(region);
            } else {
                batch.begin();
                // batch.draw();

                if (gestureSet != null && !gestureSet.isEmpty()) {

                    int tp = 65;
                    if (direction == 1) {
                        System.out.println("gestureset = "+gestureSet.size());
                        for (int i = 0; i < gestureSet.size(); i++) {
                            batch.draw(gestureSet.get(i).getText(), getGestureCoords(direction)[0] + tp, getGestureCoords(direction)[1], 80, 80);
                            tp += 65;
                        }
                    } else if (direction == 0) {
                        for (int i = gestureSet.size() - 1; i >= 0; i--) {
                            batch.draw(gestureSet.get(i).getText(), getGestureCoords(direction)[0] + tp, getGestureCoords(direction)[1], 80, 80);
                            tp += 65;
                        }
                    }
                }
                if (gestureImage != null)
                    batch.draw(gestureImage, getGestureCoords(direction)[0], getGestureCoords(direction)[1], 80, 80); //changed
                batch.end();
                TextureRegion region = (TextureRegion) animation.getKeyFrame(stateTime += delta, true);
                ((TextureRegionDrawable) getDrawable()).setRegion(region);
            }


            super.act(delta);
    }
    private void removeGesture(int idx){
        gestureSet.remove(idx);
    }
    private void updateBounds() {
        setBounds(getX(), getY(), getWidth(), getHeight());
    }
    private float[] getGestureCoords(int isleft){
        float[] arr =new float[2];
        if(isleft==0){
            arr[0] = getX()+getBounds().getWidth()/2;
            arr[1] = getY()+getBounds().getHeight()*(6f/4f);
        }
        else{
            arr[0] = getX()+getBounds().getWidth()/2;
            arr[1] = getY()+getBounds().getHeight()*(6f/4f);

        }
        return arr;
    }
    public void dispose() {
        atlas.dispose();
        killatlas.dispose();
        gestureImage.dispose();
        batch.dispose();


    }

//jkjfknnhdkfjjhhgfk ;

}