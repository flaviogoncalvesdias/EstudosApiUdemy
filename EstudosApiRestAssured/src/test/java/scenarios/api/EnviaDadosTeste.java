package scenarios.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.http.ContentType;

public class EnviaDadosTeste {

	
public void testeContentType() {
		
		given() 
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/v2/users?format=xml")
		.then()
			.log().all()
			.statusCode(200) 
			.contentType(ContentType.XML);
	}

public void testeContentTypeViaQueryParam() {
		
		given() 
			.log().all()
			.queryParam("format","xml")
			.queryParam("outra", "coisa")
		.when()
			.get("http://restapi.wcaquino.me/v2/users")
		.then()
			.log().all()
			.statusCode(200) 
			.contentType(ContentType.XML)
			.contentType(containsString("utaf-8"));
	}

@Test	
public void deveEnviarValorViaHeader() {
		
		given() 
			.log().all()
			.accept(ContentType.JSON)
		.when()
			.get("http://restapi.wcaquino.me/v2/users")
		.then()
			.log().all()
			.statusCode(200) 
			.contentType(ContentType.JSON);
	}
	

}
