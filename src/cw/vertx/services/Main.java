package cw.vertx.services;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import tools.LoaderAnalyzer;
import java.util.*;

public class Main extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		Router router = Router.router(vertx);
		HttpClient client = vertx.createHttpClient();

		router.route("/vertx/main/:yr/:mt/:dy").handler(routingContext -> {
			String year = routingContext.request().getParam("yr");
			String month = routingContext.request().getParam("mt");
			String day = routingContext.request().getParam("dy");

			routingContext
					.response()
					.putHeader("content-type", "text/html")
					.end( "result" );
		});
		vertx.createHttpServer().requestHandler(router::accept).listen(8081);
	}
}