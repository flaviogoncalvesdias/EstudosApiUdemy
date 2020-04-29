package scenarios.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.junit.Test;

public class TesteApiAsserts {

	
	public void testAssertsRestAssured() {
		
		given() //PreCondição
		.when() //Executa
			.get("http://restapi.wcaquino.me/ola")
		.then() //Depois da Execução
			.statusCode(200);
	}
	
	
	
	public void conhecerHamcrest() {
		assertThat("Maria", Matchers.is("Maria"));
		assertThat("Maria", Matchers.isA(String.class));
		assertThat(128d, Matchers.greaterThan(120d));
		
		/**
		 * AllOf -> Todas as condições precisam ser verdadeiras; 
		 * anyOf() -> OU
		 */
				
	}
	
	@Test 
	public void validarBody() {
		given() 
		.when() 
			.get("http://restapi.wcaquino.me/ola")
		.then() 
			.statusCode(200)
			.body(is(not(nullValue())))
			.body(is("Ola Mundo!")) 
			.body(containsString("Mundo"))
			;
	}
}
