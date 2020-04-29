package scenarios.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

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

public class UserJson {
	
	
	public static RequestSpecification reqSpec;
	public static ResponseSpecification resSpec;
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://restapi.wcaquino.me";
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.log(LogDetail.ALL);
		reqSpec = reqBuilder.build();//Constroi o requestSpecification
		
		ResponseSpecBuilder resBulder = new ResponseSpecBuilder();
		resBulder.expectStatusCode(200);
		resSpec = resBulder.build();//Constroi a response Specification
		
		RestAssured.requestSpecification = reqSpec;
		RestAssured.responseSpecification = resSpec;

	}
	
	
	@Test
	public void verificaPrimeiroNivelJson() {
		given()
		.when()
			.get("/users/1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", containsString("Silva"))
			.body("age", greaterThan(18))
			.body("name", isA(String.class));
	}
	@Test
	public void verificaPrimeiroNivelJsonOutrasFormas() {
		Response response = request(Method.GET, "/users/1");

		assertEquals(new Integer(1), response.path("id"));

		JsonPath jpath = new JsonPath(response.asString());
		assertEquals(1, jpath.getInt("id"));
	}
	@Test
	public void verificaSegundoNivelJsonOutrasFormas() {

		given()
		.when()
			.get("/users/2")
		.then()
			.statusCode(200)
			.body("id", is(2))
			.body("name", containsString("Joaquina"))
			.body("endereco.rua", is("Rua dos bobos"));
		
	}
	@Test
	public void verificaListaSegundoNivelJsonOutrasFormas() {

		given()
		.when()
			.get("/users/3")
		.then()
			.statusCode(200)
			.body("id", is(3))
			.body("name", containsString("Ana"))
			.body("filhos", hasSize(2))
			.body("filhos[0].name",is("Zezinho"))
			.body("filhos[1].name",is("Luizinho"))
			.body("filhos.name", hasItems("Zezinho", "Luizinho"))			;
		
	}
	
	@Test
	public void usuarioInexistente() {

		given()
		.when()
			.get("/users/4")
		.then()
			.statusCode(404)
			.body("error", is("Usuário inexistente"));
		
	}
	
	@Test
	public void verificaListaRaiz() {

		given()
		.when()
			.get("/users")
		.then()
			.statusCode(200)
			.body("$", hasSize(3)) 	//$ significa Raiz	
			.body("age[1]",is(25))
			.body("filhos.name", hasItem(Arrays.asList("Zezinho","Luizinho")))
			.body("salary", contains(1234.5678f, 2500, null))			
			;
	}
	
	@Test
	public void verificacaoAvancada() {

		given()
		.when()
			.get("/users")
		.then()
			.statusCode(200)
			.body("$", hasSize(3)) 	
			.body("age.findAll{it <= 25}.size()", is(2))
			.body("age.findAll{it <= 25 && it > 20}.size()", is(1))
			.body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))
			.body("findAll{it.age <= 25}[0].name", is("Maria Joaquina")) //[0].nome a partir do primeiro registro
			.body("findAll{it.age <= 25}[-1].name", is("Ana Júlia")) //-1 busca a partir do ultimo registro da lista
			.body("find{it.age <= 25}.name", is("Maria Joaquina")) //find busca apenas o primeiro da lista   - Metodos Baseados em Groovy
			.body("findAll{it.name.contains('n')}.name", hasItems("Maria Joaquina", "Ana Júlia")) //Verifica os nomes que possuem n
			.body("findAll{it.name.length() > 10}.name", hasItems("João da Silva", "Maria Joaquina")) //Verifica nomes maiores que 10
			.body("name.collect{it.toUpperCase()}",hasItems("MARIA JOAQUINA"))
			.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}.toArray()", allOf(arrayContaining("MARIA JOAQUINA"), arrayWithSize(1))) //Verifica nomes maiores que 10

			;
	}
	
	@Test
	public void JsonPathJava() {

		ArrayList<String> nomes = 
		given()
		.when()
			.get("/users")
		.then()
			.statusCode(200)
			.extract().path("name.findAll{it.startsWith('Maria')}"); //Extrai com base em um jSonpath com Groovy 
		assertEquals(1, nomes.size());
	}
	
	

}
