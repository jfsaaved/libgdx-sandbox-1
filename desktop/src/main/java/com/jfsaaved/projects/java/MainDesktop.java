package com.jfsaaved.projects.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jfsaaved.projects.core.Main;

public class MainDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) Main.WIDTH;
		config.height = (int) Main.HEIGHT;
		config.resizable = false;
		new LwjglApplication(new Main(), config);
	}
}
