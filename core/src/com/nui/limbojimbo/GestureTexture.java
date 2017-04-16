package com.nui.limbojimbo;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Maddy on 14/04/17.
 */

public class GestureTexture {

    Texture text;
    String s;

    public GestureTexture(Texture t, String s){
        text = t;
        this.s =s;
    }
    public void setText(Texture t){
        this.text = t;

    }
    public Texture getText(){
        return this.text;
    }

    public void setString(String s){
        this.s = s;

    }
    public String getString(){
        return this.s;
    }

}
