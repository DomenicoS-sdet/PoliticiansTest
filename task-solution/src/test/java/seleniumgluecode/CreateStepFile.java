package seleniumgluecode;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utils.*;

public class CreateStepFile {

	private Politician pol;
	private Response res;
	private String id;

	@Given("^the politician with below data$")
	public void the_politician_with_below_data(DataTable arg1) throws Throwable {
		List<String> list = arg1.asList(String.class);

		// Transform the DataTable to a list and then create the new politician
		pol = new Politician(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));
	}
	
	@Given("^the politician API$")
	public void the_politician_API() throws Throwable {
	    //Just for narration, no operation is performed in this step
	}

	@When("^the retrieve politician API is called without an id$")
	public void the_retrieve_politician_API_is_called_without_an_id() throws Throwable {
	    res = get("http://ec2-34-250-139-60.eu-west-1.compute.amazonaws.com/peps/id");
	}

	@When("^the data are submitted using the CREATE Api$")
	public void the_data_are_submitted_using_the_CREATE_Api() throws Throwable {
		Gson gson = new Gson();
		String payload = gson.toJson(pol);

		res = given().contentType(ContentType.JSON).and().body(payload)
				.post("http://ec2-34-250-139-60.eu-west-1.compute.amazonaws.com/peps");
		// Get the id of the just inserted Politician
		id = gson.fromJson(res.body().asString(), Politician.class).getId();
		// and assign to the object
		pol.setId(id);
	}
	
	@Then("^is available by id$")
	public void is_available_by_id() throws Throwable {
		Gson gson = new Gson();
	    
	    res = get("http://ec2-34-250-139-60.eu-west-1.compute.amazonaws.com/peps/" + id);
	    
	    Assert.assertTrue("Politician not available from the database", gson.fromJson(res.body().asString(), Politician.class).equals(pol));
	}

	@Then("^the politician is added to the database$")
	public void the_politician_is_added_to_the_database() throws Throwable {
		int statusCode = res.getStatusCode();
		Assert.assertTrue("The status code for the request is " + statusCode + " instead of 201", statusCode == 201);
	}

	@Then("^the politician is not added to the databse$")
	public void the_politician_is_not_added_to_the_databse() throws Throwable {
		int statusCode = res.getStatusCode();

		Assert.assertTrue("The status code for the request is " + statusCode, statusCode != 201);

	}

	@Then("^they are available insidde the five latest politicians$")
	public void they_are_available_insidde_the_five_latest_politicians() throws Throwable {
		res = get("http://ec2-34-250-139-60.eu-west-1.compute.amazonaws.com/peps");

		Gson gson = new Gson();

		String bodyResponse = res.getBody().asString();

		// Transfor the response json in a list of Politicians
		java.lang.reflect.Type politicianListType = new TypeToken<ArrayList<Politician>>() {
		}.getType();
		List<Politician> polList = gson.fromJson(bodyResponse, politicianListType);

		Assert.assertTrue("Politician not inside the latest 5 created", polList.contains(pol));
	}
		

	@Then("^no politician is returned$")
	public void no_politician_is_returned() throws Throwable {
	   Assert.assertTrue("A politician is returned",res.body().asString().isEmpty() ); 
	}

	@Then("^the status code returned should be (\\d+)$")
	public void the_status_code_returned_should_be(int arg1) throws Throwable {
	    Assert.assertTrue("Status code is instead" + res.getStatusCode(), res.getStatusCode() == 404);
	}

}
