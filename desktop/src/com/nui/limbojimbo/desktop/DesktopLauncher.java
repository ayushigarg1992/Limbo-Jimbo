package com.nui.limbojimbo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nui.limbojimbo.GdxSplashScreenGame;
import com.nui.limbojimbo.LimboJimbo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GdxSplashScreenGame(), config);
	}
}
