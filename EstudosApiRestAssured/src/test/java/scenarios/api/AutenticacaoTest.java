package scenarios.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.http.ContentType;

public class AutenticacaoTest {
	
	public void acessarApiComChave() {

		given() 
		.log().all()
		.queryParam("q", "Embu das Artes, São Paulo, BR")
		.queryParam("appid", "eb7957195031e5a46549df9b5bcffaa3")
		.queryParam("units", "metric")
		.when() 
			.get("http://api.openweathermap.org/data/2.5/weather")
		.then() 
			.statusCode(200)
			.log().all()
			.body("name",is("Embu"))
			.body("coord.lon",is(-46.85f)) 
			.body("coord.lat", is(-23.65f))
			.body("main.temp", greaterThan(25f))
			;	
	}
	
		public void naoAcessarApiComBasicAuth() {
		given() 
			.log().all()
		.when() 
			.get("http://restapi.wcaquino.me/basicauth")
		.then() 
			.statusCode(401)
			.log().all()
			;	
	}
	

	public void acessarApiComBasicAuth() {
		given() 
			.log().all()
		.when() 
			.get("http://admin:senha@restapi.wcaquino.me/basicauth") //Passado usuario e senha antes da uri
		.then() 
			.statusCode(200)
			.log().all()
			;	
	}
	
	

	public void acessarApiComBasicAuthSegundaForma() {
		given() 
			.log().all()
			.auth().basic("admin", "senha") //Passado usuario e senha antes da uri
		.when() 
			.get("http://restapi.wcaquino.me/basicauth") 
		.then() 
			.statusCode(200)
			.log().all()
			.body("status", is("logado"))
			;	
	}
	

	public void acessarApiComBasicAuthChallengeTerceiraForma() {
		given() 
			.log().all()
			.auth().preemptive().basic("admin", "senha") //Passado usuario e senha antes da uri
		.when() 
			.get("http://restapi.wcaquino.me/basicauth2") 
		.then() 
			.statusCode(200)
			.log().all()
			.body("status", is("logado"))
			;	
	}
	
	@Test
	public void acessarApiComTokenJWT() {
		
		Map<String,String> login = new HashMap<String, String>();
		
		login.put("email", "flavio_goncalvesdias@yahoo.com.br");
		login.put("senha", "Teste");
		// Login
		
		//Token		
		String token = given() 
			.log().all()
			.body(login)
			.contentType(ContentType.JSON)
		.when() 
			.post("http://barrigarest.wcaquino.me/signin") 
		.then() 
			.statusCode(200)
			.log().all()
			.extract().path("token");	
		
		
		// Contas
			given() 
				.log().all()
				.header("Authorization","JWT " +token)
			.when() 
				.get("http://barrigarest.wcaquino.me/contas") 
			.then() 
				.log().all() 
				.statusCode(200)
				.body("nome", hasItem("fgdias"))				
				;
				
	}


}
