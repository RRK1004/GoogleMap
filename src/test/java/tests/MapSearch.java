package tests;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class MapSearch {
	
	 AppiumDriver<MobileElement> driver;
	 WebDriverWait wait ;
	 List<MobileElement> listOfResults;
	 String resName;
	 MobileElement element;
	
	
	@BeforeClass
	public void setup() {
	
		try {
			
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			cap.setCapability(CapabilityType.PLATFORM_NAME, "ANDROID");
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.0");
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, "RanjithTest");
			cap.setCapability(MobileCapabilityType.UDID, "13753599610004X");
			cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,"60");
			cap.setCapability(MobileCapabilityType.NO_RESET,true);
			cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.google.android.apps.maps");
			cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,"com.google.android.maps.MapsActivity");

			URL url =new URL("http://127.0.0.1:4723/wd/hub");
			driver = new AndroidDriver<MobileElement>(url,cap);
			 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			 wait = new WebDriverWait(driver, 30);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void TestMapSearch() {
		 // How to click the first element in the search result
        driver.findElementById("com.google.android.apps.maps:id/search_omnibox_text_box").click();
        driver.findElementById("com.google.android.apps.maps:id/search_omnibox_edit_text").sendKeys("restaurants near me "+"\n");
        ((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.apps.maps:id/recycler_view")));
        listOfResults = driver.findElementsById("com.google.android.apps.maps:id/title");
        for (MobileElement e : listOfResults) {
            System.out.println("Restaurant Name : " + e.getText().toString() + "\n");
        }
        resName = listOfResults.get(0).getText();
        listOfResults.get(0).click();
        element = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + resName + "\");"));
        Assert.assertTrue(element.isDisplayed());
        Assert.assertEquals(resName, "Hotel ayyanar");

        //How to pick the search result randomly

        driver.navigate().back();

        driver.navigate().back();

        driver.findElementById("com.google.android.apps.maps:id/search_omnibox_text_box").click();
        driver.findElementById("com.google.android.apps.maps:id/search_omnibox_edit_text").sendKeys("restaurants near me \n");
        ((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.apps.maps:id/recycler_view")));
        listOfResults = driver.findElementsById("com.google.android.apps.maps:id/title");

        Random random = new Random();
        MobileElement element1 = listOfResults.get(random.nextInt(listOfResults.size()));

        resName = element1.getText();
        System.out.println("Random Restaurant Name : " + resName);
        element1.click();

        element = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + resName + "\");"));
        Assert.assertTrue(element.isDisplayed());
		
	}
	
	@AfterClass
	public void teardown() {
	    if (driver != null) {
            driver.quit();
		
	}}}


