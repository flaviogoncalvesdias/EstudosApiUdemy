package scenarios.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class Arquivo {

	public void naoEnvioArquivo() {
		given()
			.log().all()
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404);
	}
	
	public void envioArquivo() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/test/resources/rest-assured-4.1.1.jar"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("rest-assured-4.1.1.jar"));
	}
	
	
	public void naoEnviaArquivoGrande() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/test/resources/rest-assured-4.1.1.jar"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)
			.time(lessThan(10000L))
			.body("name", is("rest-assured-4.1.1.jar"));
	}
	
	@Test
	public void deveBaixarArquivo() throws Exception {
		byte[] image = given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/download")
		.then()
			.statusCode(200)
			.extract().asByteArray();
		File imagem = new File("src/test/resources/file.jpg");
		OutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
		
		System.out.println(imagem.length());
		assertThat(imagem.length(), lessThan(100000L));

	}
}
