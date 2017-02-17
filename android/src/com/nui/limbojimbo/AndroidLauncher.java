package com.nui.limbojimbo;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nui.limbojimbo.LimboScreen;
import com.nui.limbojimbo.GdxSplashScreenGame;


public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new LimboScreen(), config);

		//initialize( new GdxSplashScreenGame(), config);
	}
}
