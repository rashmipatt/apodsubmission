import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import beans.APODObject;
import beans.APODResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CallAPODGETWithParams {
	private static Logger LOGGER = Logger.getLogger(CallAPODGETWithParams.class.getName());

	
	public static APODResponse callAPODGETwithParamsArray(String sbUrl) throws IOException {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		APODResponse res = new APODResponse();
		
		String apodGETURI = "https://api.nasa.gov/planetary/apod?";
		//LOGGER.log(Level.INFO, "URI with params is" + sbUrl);
		URL baseUri = new URL(apodGETURI + sbUrl);
		
		HttpURLConnection urlConn = (HttpURLConnection) baseUri.openConnection();
		urlConn.setDoOutput(true);
		urlConn.setRequestMethod("GET");
		int responseCode = urlConn.getResponseCode();
		String responseMessage = urlConn.getResponseMessage();
		LOGGER.log(Level.INFO, "the status code from callAPODGETwithParams method is " + responseCode);
		LOGGER.log(Level.INFO,
				"the response message from callAPODGETwithParams method is " + urlConn.getResponseMessage());
		// json.put("responseCode", responseCode);
		res.setResponseCode(responseCode);
		res.setResponseMessage(responseMessage);
		BufferedReader buff = null;
		if (100 <= urlConn.getResponseCode() && urlConn.getResponseCode() <= 399) {
			buff = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			StringBuffer sb = new StringBuffer();

			String inputLine = "";
			while ((inputLine = buff.readLine()) != null) {
				sb.append(inputLine);
			}
			APODObject[] list = new Gson().fromJson(sb.toString(), APODObject[].class);
			
			res.setMultipleAPODS(list);
			res.setMultiple(true);
		} else {
			buff = new BufferedReader(new InputStreamReader(urlConn.getErrorStream()));
			StringBuffer sb = new StringBuffer();

			String inputLine = "";
			while ((inputLine = buff.readLine()) != null) {
				sb.append(inputLine);
			}

			APODObject apod = new Gson().fromJson(sb.toString(), APODObject.class);
			res.setSingleAPOD(apod);
			res.setMultiple(false);
		}

		
		

		return res;
	}

	
	public static APODResponse callAPODGETwithParamsSingle(String sbUrl) throws IOException {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		APODResponse res = new APODResponse();
		
		String apodGETURI = "https://api.nasa.gov/planetary/apod?";
		//LOGGER.log(Level.INFO, "URI with params is" + sbUrl);
		URL baseUri = new URL(apodGETURI + sbUrl);
		
		HttpURLConnection urlConn = (HttpURLConnection) baseUri.openConnection();
		urlConn.setDoOutput(true);
		urlConn.setRequestMethod("GET");
		int responseCode = urlConn.getResponseCode();
		String responseMessage = urlConn.getResponseMessage();
		LOGGER.log(Level.INFO, "the status code from callAPODGETwithParams method is " + responseCode);
		LOGGER.log(Level.INFO,
				"the response message from callAPODGETwithParams method is " + urlConn.getResponseMessage());
		// json.put("responseCode", responseCode);
		res.setResponseCode(responseCode);
		
		//when status code is 403 or 429, we get the message from input stream
		if (urlConn.getResponseCode() == 403 || urlConn.getResponseCode() == 429 ) {
			APODResponse obj = new APODResponse();
			obj.setResponseCode(responseCode);
			obj.setResponseMessage(responseMessage);
			return obj;
		}
		BufferedReader buff = null;
		if ((100 <= urlConn.getResponseCode() && urlConn.getResponseCode() <= 399)  ) {
			buff = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		} else {
			buff = new BufferedReader(new InputStreamReader(urlConn.getErrorStream()));
		}

		StringBuffer sb = new StringBuffer();

		String inputLine = "";
		while ((inputLine = buff.readLine()) != null) {
			sb.append(inputLine);
		}
		System.out.println("+++++++++++++++"+sb.toString());
		APODObject apod = new Gson().fromJson(sb.toString(), APODObject.class);
		
		res.setSingleAPOD(apod);
		res.setMultiple(false);
		if (res.getResponseCode() < 100 || res.getResponseCode() >= 400 ) {
			res.setResponseMessage(apod.getMsg());
		}
		

		return res;
	}
}
