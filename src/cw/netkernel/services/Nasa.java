package cw.netkernel.services;

import org.json.JSONObject;

import org.netkernel.layer0.nkf.INKFRequestContext;
import org.netkernel.module.standard.endpoint.StandardAccessorImpl;
import java.util.Scanner;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;

public class Nasa extends StandardAccessorImpl {
	//static String API_KEY = "MaqPDdbGPOTsIU3nSjF9ayrAQQ5zEYoY4ApiNSwz";
	static String API_KEY = "API_KEY";

    @Override
	public void onSource(INKFRequestContext aContext) throws Exception {
		String p = aContext.getThisRequest().getArgumentValue("year");
		String q = aContext.getThisRequest().getArgumentValue("month");
		String r = aContext.getThisRequest().getArgumentValue("day");
		String res = load(p + "-" + q + "-" + r);
    	aContext.createResponseFrom(res);
	}

	public static String load(String day){
		String charset = "UTF-8";
		String timestamp = "date=" + day;
		try {
			URLConnection connection = new URL(
					"https://api.nasa.gov/planetary/apod" + "?"
							+  timestamp
							+ "&api_key=" + API_KEY).openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			InputStream response = connection.getInputStream();
			Scanner scanner = new Scanner(response);
			String responseBody = scanner.useDelimiter("\\A").next();
			JSONObject json =  new JSONObject(responseBody);
			String result = json.getString("explanation");
			return result;
		} catch (Exception e) {
			return "Exception, no resource on date = " + day + "               Try another day!";
		}
	}

}
