package cw.vertx;

import io.vertx.core.Vertx;
import cw.vertx.services.Main;

public class Load {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new Main());
	}

}