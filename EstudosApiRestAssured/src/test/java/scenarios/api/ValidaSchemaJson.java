package scenarios.api;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.module.jsv.JsonSchemaValidator;


//Usando o schema validator do RestAssured para validar o schema (Contrato)
public class ValidaSchemaJson {
	
	@Test
	public void validaSchemaJson() {
		
	given()
		.log().all()
	.when()
		.get("http://restapi.wcaquino.me/users")
	.then()
	.log().all()
	.statusCode(200)
	.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema"));

	}

}
