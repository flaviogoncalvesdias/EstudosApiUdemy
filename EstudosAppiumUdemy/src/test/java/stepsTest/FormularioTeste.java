package stepsTest;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class FormularioTeste {
	private AndroidDriver driver;

	@Test
	public void devePreencherCampoTexto() throws MalformedURLException {
	    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

		desiredCapabilities.setCapability("platformName", "Android");
	    desiredCapabilities.setCapability("deviceName", "RQ8MA0F8HRT");
	    desiredCapabilities.setCapability("automationName", "uiautomator2");
	    desiredCapabilities.setCapability("app", "Users/mac/Documents/Projetos_Estudos/EstudosApiUdemy/EstudosAppiumUdemy/src/test/resources/CTAppium_1_2.apk");
	    desiredCapabilities.setCapability("autoGrantPermissions","true");
	    desiredCapabilities.setCapability("noReset","true");

	    URL remoteUrl = new URL("http://localhost:4723/wd/hub");

	    driver = new AndroidDriver(remoteUrl, desiredCapabilities);
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    
	    java.util.List<MobileElement> elementosEncontrados= driver.findElements(By.className("android.widget.TextView"));
	    elementosEncontrados.get(1).click();
	    
	    MobileElement nome = (MobileElement) driver.findElement(MobileBy.AccessibilityId("nome"));
	    nome.sendKeys("Flávio Dias");
	    
//	    for (MobileElement mobileElement : elementosEncontrados) {
//			System.out.println(mobileElement.getText());
//		}
	    
	    assertEquals("Flávio Dias", nome.getText()); 
	    driver.quit();
	}

}
