package APITest.API;

import static org.testng.AssertJUnit.assertEquals;
import java.io.FileReader;
import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.minidev.json.parser.JSONParser;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.config.RestAssuredConfig.config;
import APITest.API.JsonParser;
import org.json.JSONArray;
import org.testng.annotations.Test;
import com.google.gson.Gson;

public class testAPITask {

	/********************* First GET API Method Assert Status Code and Body*******************/
	@Test
	public void getMethod ()
	{

		ReadPropertiesFile expectedValues = new ReadPropertiesFile();
		try {
			expectedValues.ReadPropFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		RestAssured.config = config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
		String URL = "https://jsonplaceholder.typicode.com/posts/1";
		Response response = given()
				.when()
				.get(URL);

		System.out.println("Response: "+ response.asString());
		System.out.println("Status Code: "+ response.getStatusCode());
		JsonParser Git = response.getBody().as(JsonParser.class);

		assertEquals(200, response.getStatusCode());

		String Userid = Git.getuserId().toString();
		assertEquals(expectedValues.getUserId(), Userid);
		
		String id = Git.getid().toString();
		assertEquals(expectedValues.getId(), id);

		String title = Git.gettitle();
		assertEquals(expectedValues.getTitle(), title);

		String body = Git.getbody();
		assertEquals(expectedValues.getBody(), body);

		System.out.println("All assertions passed successfully");
	}
	
	/********************* POST API Method Assert Status Code and Body*******************/
	@Test
	public void postMethod ()
	{

		RestAssured.config = config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
		String URL = "https://jsonplaceholder.typicode.com/posts";
		Response response = given()
				.when()
				.post(URL);

		System.out.println("Response: "+ response.asString());
		System.out.println("Status Code: "+ response.getStatusCode());
		JsonParser Git = response.getBody().as(JsonParser.class);
		
		String id = Git.getid().toString();
		
		assertEquals("101", id);
		assertEquals(201, response.getStatusCode());

		System.out.println("All assertions passed successfully");

	}


	
	/***************   Assert method to assert the four values **************************/
	public void assertValues(JsonParser parser, JsonParser expectedParser)
	{
		assertEquals(expectedParser.getuserId().toString(), parser.getuserId().toString());
		assertEquals(expectedParser.getid().toString(), parser.getid().toString());
		assertEquals(expectedParser.gettitle().toString(), parser.gettitle().toString());
		assertEquals(expectedParser.getbody().toString(), parser.getbody().toString());
	
	}
	
	public net.minidev.json.JSONArray readJSONfile ()
	{
		JSONParser file = new JSONParser();
		net.minidev.json.JSONArray parselist = null;
		try {
			
			parselist = (net.minidev.json.JSONArray) file.parse(new FileReader("./src/test/resources/TestJsonFiles/AssertionValues.json"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parselist;
	}
	

}
