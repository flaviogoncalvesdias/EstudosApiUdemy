package scenarios.api;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SerializacaoObjetos {//Transformando objetos Java para Json. Usando a Biblioteca Gson
	public void mapUsuario() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "Flávio Dias MAP");
		params.put("age", 25);
		given() 
			.log().all()
			.contentType("application/json")
			.body(params)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201) //StatusCode padrão para post
			.body("id", is(notNullValue()))
			.body("name", is("Flávio Dias MAP"))
			.body("age", is(25));
	}
	
	public void usuarioSerializado() {
		
		User user = new User("Flávio Dias OBJ", 32, 8962.00);
		given() 
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201) //StatusCode padrão para post
			.body("id", is(notNullValue()))
			.body("name", is("Flávio Dias OBJ"))
			.body("age", is(32));
	}
	
	
	@Test
	public void usuarioDeserializado() {
		
		User user = new User("Flávio Dias OBJ Deserializado", 32, 8962.00);
		
		User userInserido =
		given() 
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201) //StatusCode padrão para post
			.extract().body().as(User.class);
		
		System.out.println(userInserido);
		assertEquals("Flávio Dias OBJ Deserializado", userInserido.getName());
		assertThat(userInserido.getAge(), is(32));
		assertThat(userInserido.getId(), notNullValue());
		
		
	}

}
