import org.apache.commons.lang3.StringUtils;

import org.testng.Assert;
import org.testng.annotations.Test;

import beans.APODObject;
import beans.APODResponse;



import java.io.IOException;
import java.util.HashMap;


import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test_APOD_GET {
	private static Logger LOGGER = Logger.getLogger(Test_APOD_GET.class.getName());

	//Pass all blank parameters to ACOD GET URI, we should get 403 error 
	 @Test
	void test_ACOD_GET_01() {
		 APODResponse response;
			try {
				response = CallAPODGETWithParams.callAPODGETwithParamsSingle("");
				Assert.assertEquals(response.getResponseCode(), 403);
				Assert.assertEquals(response.getResponseMessage(),"Forbidden");
				LOGGER.log(Level.INFO,"response message in   test_ACOD_GET_01 with params"+response);

			} catch (IOException e) {
				LOGGER.log(Level.SEVERE,"Error in calling  APOD GET with params");
				e.printStackTrace();
			}

	}

	// Pass only date param to ACOD GET URI, we should get 403 error saying api-key
	// missing
	
	@Test
	void test_ACOD_GET_02()

	{

		HashMap<String, String> aMap = new HashMap<String, String>();
		aMap.put("date", "2021-10-17");
		APODResponse response;
	
		String queryParams = Test_APOD_GET.buildQueryParam(aMap);

		try {
			response = CallAPODGETWithParams.callAPODGETwithParamsSingle(queryParams);
			Assert.assertEquals(response.getResponseCode(), 403);
			Assert.assertEquals(response.getResponseMessage(),"Forbidden");
			LOGGER.log(Level.INFO,"response message in   test_ACOD_GET_01 with params"+response);

		} catch (IOException e) {
			LOGGER.log(Level.INFO,"Error in calling  APOD GET with params");
			e.printStackTrace();
		}

	}
	// pass api_key as DEMO_KEY
//	 @Test
	void test_ACOD_GET_03()

	{
		// Specify the base URL to the RESTful web service
		
		HashMap<String, String> aMap = new HashMap<String, String>();
		aMap.put("api_key", "DEMO_KEY");
		APODResponse response;
	
		
		String queryParams = Test_APOD_GET.buildQueryParam(aMap);
		try {
			response = CallAPODGETWithParams.callAPODGETwithParamsSingle(queryParams);
			
			Assert.assertEquals(response.getResponseCode(), 200,
					"expect 200, but received" + response.getResponseCode()
							+ " when paseed only DEMO KEY is passed  for TEST CASE 3 for GET ACOD API ");
			Assert.assertEquals(response.getResponseCode(), 429,
					"DEMO API Key Daily limit exceeded ");
		} catch (IOException e) {
			LOGGER.log(Level.INFO,"Error in calling  APOD GET with params");
			e.printStackTrace();
		}
	}

	// passing date and assigned api_key ( set api_key in environment variable  
	 @Test
	void test_APOD_GET_04() {
		HashMap<String, String> aMap = new HashMap<String, String>();
		String api_key = System.getenv("APOD_KEY");
		aMap.put("api_key", api_key);
		aMap.put("date", "2021-10-19");
		
		String queryParams = Test_APOD_GET.buildQueryParam(aMap);
		
		APODResponse response;
		try {
			response = CallAPODGETWithParams.callAPODGETwithParamsSingle(queryParams);
			Assert.assertEquals(response.getResponseCode(), 200,
					"expect 200, but received" +response.getResponseCode()
							+ " when paseed only date in params for TEST CASE 4 for GET ACOD API ");

		} catch (IOException e) {
			LOGGER.log(Level.INFO,"Error in calling  APOD GET with params");
			e.printStackTrace();
		}

	}

//passing date and start_date params  with assigned api_key
	@Test
	void test_APOD_GET_05() {
		HashMap<String, String> aMap = new HashMap<String, String>();
		String api_key = System.getenv("APOD_KEY");
		aMap.put("api_key", api_key);
		String today = Test_APOD_GET.formatDate(new Date());
		aMap.put("date", today);
		aMap.put("start_date", today);
		String queryParams = Test_APOD_GET.buildQueryParam(aMap);

		APODResponse response;
		try {
			response = CallAPODGETWithParams.callAPODGETwithParamsSingle(queryParams);
			Assert.assertEquals(response.getResponseCode(),400,
					"expect 400, but received" + response.getResponseCode()
							+ " when paseed only date in params for TEST CASE 5 for GET ACOD API ");
			
			String badRequest =(String) response.getResponseMessage();
			boolean bl = StringUtils.contains(badRequest, "invalid field combination passed");
			Assert.assertEquals(bl, true, "Expected invalid field combination");
					

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in calling  APOD GET with params");
			e.printStackTrace();
		}

	}

//passing date and end date params with API_KEY
	@Test
	void test_APOD_GET_06() {
		HashMap<String, String> aMap = new HashMap<String, String>();
		String api_key = System.getenv("APOD_KEY");
		aMap.put("api_key", api_key);
		String today = Test_APOD_GET.formatDate(new Date());
		aMap.put("date", today);
		aMap.put("end_date", today);
		String queryParams = Test_APOD_GET.buildQueryParam(aMap);

		APODResponse response;
		try {
			response = CallAPODGETWithParams.callAPODGETwithParamsSingle(queryParams);
			Assert.assertEquals(response.getResponseCode(),400,
					"expect 400, but received" + response.getResponseCode()
							+ " when paseed only date in params for TEST CASE 6 for GET ACOD API ");
			
			String badRequest =(String) response.getResponseMessage();
			boolean bl = StringUtils.contains(badRequest, "invalid field combination passed");
			Assert.assertEquals(bl, true, "Expected invalid field combination");
					

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in calling  APOD GET with params");
			e.printStackTrace();
		}

	}
//passing start_date and end_date range	with assigned api_key
	@Test
	void test_APOD_GET_07() {
		HashMap<String, String> aMap = new HashMap<String, String>();
		String api_key = System.getenv("APOD_KEY");
		aMap.put("api_key", api_key);
		String today = Test_APOD_GET.formatDate(new Date());
		aMap.put("start_date", "2021-10-18");
		aMap.put("end_date", today);
		String queryParams = Test_APOD_GET.buildQueryParam(aMap);

		APODResponse response;
		try {
			response = CallAPODGETWithParams.callAPODGETwithParamsArray(queryParams);
			Assert.assertEquals(response.getResponseCode(),200,
					"expect 200, but received" + response.getResponseCode()
							+ " when paseed only date in params for TEST CASE 7 for GET ACOD API ");
			
					
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in calling  APOD GET with params");
			e.printStackTrace();
		}

	}


	//pass count with api_key
	@Test
	void test_APOD_GET_08() {
		HashMap<String, String> aMap = new HashMap<String, String>();
		String api_key = System.getenv("APOD_KEY");
		aMap.put("api_key", api_key);
		//String today = Test_APOD_GET.formatDate(new Date());
		aMap.put("count", "10");
		
		String queryParams = Test_APOD_GET.buildQueryParam(aMap);

		APODResponse response;
		try {
			response = CallAPODGETWithParams.callAPODGETwithParamsArray(queryParams);
			Assert.assertEquals(response.getResponseCode(),200,
					"expect 200, but received" + response.getResponseCode()
							+ " when paseed only date in params for TEST CASE 6 for GET ACOD API ");
			
			
			// Check for 10 images
			APODObject[] imageRes = response.getMultipleAPODS();
			LOGGER.log(Level.INFO, String.valueOf(imageRes.length));
			Assert.assertEquals(String.valueOf(imageRes.length),"10", 
					"expected 10 object but received "+String.valueOf(imageRes.length));

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in calling  APOD GET test_APOD_GET_08 with params");
			e.printStackTrace();
		}

	}
	//scenario pass count with date. Expected result, 400 ,count can not be passed with date
	@Test
	void test_APOD_GET_09()
	{
		HashMap<String, String> aMap = new HashMap<String, String>();
		String api_key = System.getenv("APOD_KEY");
		aMap.put("api_key", api_key);
		aMap.put("count", "10");
		String today = Test_APOD_GET.formatDate(new Date());
		aMap.put("date", today);
		String queryParams = Test_APOD_GET.buildQueryParam(aMap);
		APODResponse response;
		
		try {
			response = CallAPODGETWithParams.callAPODGETwithParamsSingle(queryParams);
			LOGGER.log(Level.INFO,"Response in in test_APOD_GET_09 is"+response);
			String badRequest =(String) response.getResponseMessage();
			LOGGER.log(Level.INFO,"badRequest in test_APOD_GET_09 is"+badRequest);
			boolean bl = StringUtils.contains(badRequest,"invalid field combination passed");
				
			Assert.assertEquals(response.getResponseCode(),400,
					"expect 400, but received" + response.getResponseCode()
							+ " when paseed only date in params for TEST CASE 6 for GET ACOD API ");
			Assert.assertEquals(bl, true, "Expected invalid field combination");
				

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in calling  APOD GET with params");
			e.printStackTrace();
		}
	

	}

//pass count with start_date and end_date, Expected result, 400 ,count can not be passed with start_date, end_date
	
	@Test
	void test_APOD_GET_10 ()
	{
		
		HashMap<String, String> aMap = new HashMap<String, String>();
		String api_key = System.getenv("APOD_KEY");
		aMap.put("api_key", api_key);
		aMap.put("count", "10");
		String today = Test_APOD_GET.formatDate(new Date());
		aMap.put("start_date", today);
		aMap.put("end_date", today);
		String queryParams = Test_APOD_GET.buildQueryParam(aMap);
		APODResponse response;
		
		try {
			response = CallAPODGETWithParams.callAPODGETwithParamsSingle(queryParams);
			LOGGER.log(Level.INFO,"Response in in test_APOD_GET_10 is"+response);
			String badRequest =(String) response.getResponseMessage();
			LOGGER.log(Level.INFO,"badRequest in test_APOD_GET_10 is"+badRequest);
			boolean bl = StringUtils.contains(badRequest,"invalid field combination passed");
				
			Assert.assertEquals(response.getResponseCode(),400,
					"expect 400, but received" + response.getResponseCode()
							+ " when paseed only date in params for TEST CASE 10 for GET ACOD API ");
			Assert.assertEquals(bl, true, "Expected invalid field combination");
				

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in calling  APOD GET with params");
			e.printStackTrace();
		}
	
		
	}

//thumbs -- check if there is image or video in JSON output if APOD is not an video, this parameter is ignored

//pass future date in date field
	@Test
	void test_APOD_GET_11 ()
	{
		
		HashMap<String, String> aMap = new HashMap<String, String>();
		String api_key = System.getenv("APOD_KEY");
		aMap.put("api_key", api_key);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, yyyy");  
		OffsetDateTime odt =OffsetDateTime.now( ZoneOffset.UTC );
		String today= dtf.format(odt);
		LOGGER.log(Level.INFO, "today = "+today);
		LOGGER.log(Level.INFO, "today with zone offset = "+odt.toString());
		String futureDate = getFutureDate(10);
		aMap.put("date", futureDate);
		
		String queryParams = Test_APOD_GET.buildQueryParam(aMap);
		APODResponse response;
		
		try {
			response = CallAPODGETWithParams.callAPODGETwithParamsSingle(queryParams);
			LOGGER.log(Level.INFO,"Response in in test_APOD_GET_11 is"+response);
			String badRequest =(String) response.getResponseMessage();
			LOGGER.log(Level.INFO,"badRequest in test_APOD_GET_11 is"+badRequest);
			boolean bl = StringUtils.contains(badRequest,"Date must be between Jun 16, 1995 and "+ today);
			
			Assert.assertEquals(response.getResponseCode(),400,
					"expect 400, but received" + response.getResponseCode()
							+ " when paseed only date in params for TEST CASE 11 for GET ACOD API ");
			Assert.assertEquals(bl, true, "Date must be between Jun 16, 1995 and "+today);
			

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in calling  AP"
					+ "OD GET with params");
			e.printStackTrace();
		}
	}
	//check data types of the fields
	@Test
	void test_APOD_GET_12()
	{
		
		HashMap<String, String> aMap = new HashMap<String, String>();
		String api_key = System.getenv("APOD_KEY");
		aMap.put("api_key", api_key);
		aMap.put("date", "12-31-2009");
		
		String queryParams = Test_APOD_GET.buildQueryParam(aMap);
		APODResponse response;
		
		try {
			response = CallAPODGETWithParams.callAPODGETwithParamsSingle(queryParams);
			LOGGER.log(Level.INFO,"Response in in test_APOD_GET_12 is"+response);
			String badRequest =(String) response.getResponseMessage();
			LOGGER.log(Level.INFO,"badRequest in test_APOD_GET_12 is"+badRequest);
			boolean bl = StringUtils.contains(badRequest,"does not match format '%Y-%m-%d'");
				
			Assert.assertEquals(response.getResponseCode(),400,
					"expect 400, but received" + response.getResponseCode()
							+ " when paseed only date in params for TEST CASE 12 for GET ACOD API ");
			Assert.assertEquals(bl, true, "does not match format '%Y-%m-%d'");
			

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in calling  AP"
					+ "OD GET with params");
			e.printStackTrace();
		}
	}
	//check end_date is always greater than start_date  
	@Test
	void test_APOD_GET_13()
	{
		
		HashMap<String, String> aMap = new HashMap<String, String>();
		String api_key = System.getenv("APOD_KEY");
		aMap.put("api_key", api_key);
		aMap.put("start_date", "2021-10-02");
		aMap.put("end_date", "2021-10-01");
		
		String queryParams = Test_APOD_GET.buildQueryParam(aMap);
		APODResponse response;
		
		try {
			response = CallAPODGETWithParams.callAPODGETwithParamsSingle(queryParams);
			LOGGER.log(Level.INFO,"Response in in test_APOD_GET_13 is"+response);
			String badRequest =(String) response.getResponseMessage();
			LOGGER.log(Level.INFO,"badRequest in test_APOD_GET_13 is"+badRequest);
			boolean bl = StringUtils.contains(badRequest,"start_date cannot be after end_date");
				
			Assert.assertEquals(response.getResponseCode(),400,
					"expect 400, but received" + response.getResponseCode()
							+ " when paseed only date in params for TEST CASE 13 for GET ACOD API ");
			Assert.assertEquals(bl, true, "start_date cannot be after end_date");
			

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in calling  test_APOD_GET_13"
					+ "with params");
			e.printStackTrace();
		}
	}
	
	//APOD as Video 
	@Test
	void test_APOD_GET_14()
	{
		
		HashMap<String, String> aMap = new HashMap<String, String>();
		String api_key = System.getenv("APOD_KEY");
		aMap.put("api_key", api_key);
		// Figured out the date when video is available by searching the site
		aMap.put("date", "2021-10-11");
		aMap.put("thumbs", "True");
		
		String queryParams = Test_APOD_GET.buildQueryParam(aMap);
		APODResponse response;
		
		try {
			response = CallAPODGETWithParams.callAPODGETwithParamsSingle(queryParams);
			LOGGER.log(Level.INFO,"Response in in test_APOD_GET_14 is"+response);
			String goodrequest =(String) response.getResponseMessage();
			LOGGER.log(Level.INFO,"rtesponse message in test_APOD_GET_14 is"+goodrequest);
			APODObject apod = response.getSingleAPOD();
			String thumbnailurl = apod.getThumbnail_url();
			
			Assert.assertEquals(response.getResponseCode(),200,
					"expect 200, but received" + response.getResponseCode()
							);
			
			Assert.assertNotNull(thumbnailurl);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in calling  AP"
					+ "OD GET with params");
			e.printStackTrace();
		}
	}
	
	
		public static String buildQueryParam(HashMap<String, String> kvparams) {
			StringBuilder sb = new StringBuilder();

			for (String key : kvparams.keySet()) {
				sb.append(key);
				sb.append("=");
				sb.append(kvparams.get(key));
				sb.append("&");
			}
			return sb.toString();
		}

		public static String formatDate(Date aDate) {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String dateformatted = simpleDateFormat.format(aDate);
			return dateformatted;
		}
		
		public static String getFutureDate(int days) {
			Calendar ed = Calendar.getInstance();
			ed.add(Calendar.DATE, days);
			String futureDate =formatDate (ed.getTime());
			return futureDate;
			
		}
		

}


