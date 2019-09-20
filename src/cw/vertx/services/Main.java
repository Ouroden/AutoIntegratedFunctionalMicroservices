package cw.vertx.services;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import tools.LoaderAnalyzer;
import java.util.*;

public class Main extends AbstractVerticle {

	private HttpClient client;

	@Override
	public void start() throws Exception {

		Router router = Router.router(vertx);
		client = vertx.createHttpClient();

		router.route("/vertx/main/:yr/:mt/:dy").handler(routingContext -> {
			String year = routingContext.request().getParam("yr");
			String month = routingContext.request().getParam("mt");
			String day = routingContext.request().getParam("dy");

			client.getNow(8080, "localhost", "/cw-netkernel/nasa/" + year + "/" +month + "/" + day,
					response -> {
						response.bodyHandler( buffer-> {
							String result = buffer.toString();
							replaceLink(result, result, 0, routingContext);
						});
					});
		});
		vertx.createHttpServer().requestHandler(router::accept).listen(8081);
	}

	private void replaceLink(String origresponse, String replaced, int index, RoutingContext routingContext) {
		int[] nextPN = LoaderAnalyzer.findNextPN(origresponse, index);
		if (nextPN[1] < 0) {
			routingContext
					.response()
					.putHeader("content-type", "text/html")
					.end( replaced );
			return;
		}
		String name = origresponse.substring(nextPN[0], nextPN[1]);
		client.getNow(8080, "localhost", "/cw-netkernel/wikimedia/" + name.replace(" ", "_"),
				response2 -> {
					response2.bodyHandler( buffer2-> {
						String result2 = buffer2.toString();
						replaceLink(origresponse, replaced.replaceFirst(name, createHTML(result2, name)), nextPN[1], routingContext);
					});
				});
	}

	private String createHTML(String link, String name) {
		return "<a href=" + link + ">" + name + "</a>";
	}

}