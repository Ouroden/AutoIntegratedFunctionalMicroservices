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

public class Main extends StandardAccessorImpl {

    @Override
	public void onSource(INKFRequestContext context) throws Exception {
		
		context.createResponseFrom("result");
	}
}
