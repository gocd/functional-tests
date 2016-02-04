package com.thoughtworks.twist.driver;


import com.thoughtworks.cruise.util.CruiseConstants;
import org.openqa.selenium.WebDriver;

public class WebDriverClient {

    private WebDriver driver=null;

    public WebDriver getFirefoxDriver(){
        if(driver == null) {
            if (CruiseConstants.WEBDRIVER_TESTS.equals("Y") ){
                driver = new org.openqa.selenium.firefox.FirefoxDriver();
            }
        }
        return driver;
    }
}
