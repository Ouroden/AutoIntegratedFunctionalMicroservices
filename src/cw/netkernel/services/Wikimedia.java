package cw.netkernel.services;

import org.json.JSONObject;

import org.netkernel.layer0.nkf.INKFRequestContext;
import org.netkernel.module.standard.endpoint.StandardAccessorImpl;
import java.util.Scanner;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.io.*;

public class Wikimedia extends StandardAccessorImpl {

    @Override
	public void onSource(INKFRequestContext aContext) throws Exception {
		String q = aContext.getThisRequest().getArgumentValue("query");
    	aContext.createResponseFrom(wm(q));
	}

	public static String wm(String querry){
		String charset = "UTF-8";
		String connector = "https://en.wikipedia.org/w/api.php?"
				+ "action=query&"
				+ "titles=" + querry + "&"
				+ "prop=revisions&"
				+ "rvprop=content&"
				+ "format=json";
		String responseBody = "";
		try {
			URLConnection connection = new URL(
					connector ).openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			InputStream response = connection.getInputStream();
			Scanner scanner = new Scanner(response);
			responseBody = scanner.useDelimiter("\\A").next();
			JSONObject json =  new JSONObject(responseBody);
			Iterator<String> iter = json.getJSONObject("query").getJSONObject("pages").keys();
			while (iter.hasNext()) {
				json = json.getJSONObject("query")
						.getJSONObject("pages")
						.getJSONObject(iter.next());
				break;
			}
			String title = json.getString("title");
			String url = "https://en.wikipedia.org/wiki/" + title;
			return url;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString() + connector + responseBody;
		}
	}

}
