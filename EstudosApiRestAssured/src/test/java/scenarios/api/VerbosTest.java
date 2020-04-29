package scenarios.api;

import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.assertion.BodyMatcher;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class VerbosTest {
	
	
	public void salvarUsuarioPost() {
		given() 
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"Flávio Dias\",\"age\": 30}")
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201) //StatusCode padrão para post
			.body("id", is(notNullValue()))
			.body("name", is("Flávio Dias"))
			.body("age", is(30));
	}
	
	public void naoSalvaUsuarioSemNomePost() {
		given() 
			.log().all()
			.contentType("application/json")
			.body("{\"age\": 30}")
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(400) //StatusCode padrão para post
			.body("id", is(nullValue()))
			.body("error",is("Name é um atributo obrigatório"));
	}
	
	
	public void atualizaUsuarioPut() {
		given() 
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"Flávio Dias\",\"age\": 30}")
		.when()
			.put("http://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(200) 
			.body("id", is(1));
//			.body("error",is("Name é um atributo obrigatório"));
	}
	
	public void deletaUsuarioDelete() {
		given() 
			.log().all()
		.when()
			.delete("http://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(204); //Resposta Padrão quando deleta
	}
	
	@Test
	public void naoDeletaUsuarioDelete() {
		given() 
			.log().all()
		.when()
			.delete("http://restapi.wcaquino.me/users/1000")
		.then()
			.log().all()
			.statusCode(400); //Resposta Padrão quando não existe
	}

}
