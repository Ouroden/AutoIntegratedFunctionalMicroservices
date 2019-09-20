package cw.netkernel.services;

import org.json.JSONObject;

import org.netkernel.layer0.nkf.INKFRequestContext;
import org.netkernel.layer0.nkf.NKFException;
import org.netkernel.module.standard.endpoint.StandardAccessorImpl;
import tools.LoaderAnalyzer;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import java.util.*;
import tools.*;

public class Main extends StandardAccessorImpl {

    @Override
	public void onSource(INKFRequestContext context) throws Exception {
		String year = context.getThisRequest().getArgumentValue("year");
		String month = context.getThisRequest().getArgumentValue("month");
		String day = context.getThisRequest().getArgumentValue("day");
		String res = context.source("res:/cw-netkernel/nasa/" + year + "/" + month + "/" + day, String.class);
		ArrayList<int[]> names = LoaderAnalyzer.findProperNames(res);

		String result = "";
		for (int[] row : names) {
			String name = res.substring(row[0], row[1]);
			String link = context.source("res:/cw-netkernel/wikimedia/" + name.replace(" ", "_"), String.class);
			result += link + "\n";
			res = res.replace(name, createHTML(link, name));
		}
		context.createResponseFrom(res);

	}

	private String createHTML(String link, String name) {
    	return "<a href=" + link + ">" + name + "</a>";
	}
}
