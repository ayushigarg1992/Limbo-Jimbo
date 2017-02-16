package com.nui.limbojimbo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LimboScreen extends ApplicationAdapter{
	Stage stage;
	SpriteBatch batch;
	Texture texture;
	Sprite sprite ;

	@Override
	public void create(){
		//sprite = new Sprite(new Texture(Gdx.files.internal("wizard.png")));
		ScreenViewport viewport = new ScreenViewport();
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		Wizard wiz = new Wizard();
		Ghosts ghoul1 = new Ghosts(new Texture(Gdx.files.internal("ghouls.png")),1700f,500f);
		Ghosts ghoul2 = new Ghosts(new Texture(Gdx.files.internal("ghouls2.png")),1700f,100f);
		stage.addActor(wiz);
		stage.addActor(ghoul1);
		stage.addActor(ghoul2);

			}
	public void resize (int width, int height) {
		// See below for what true means.
		stage.getViewport().update(width, height, true);
	}
	@Override
	public void render(){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1,1,1, 1);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		}
	@Override
	public void dispose(){
		stage.dispose();
		batch.dispose();
		texture.dispose();
	}
}
