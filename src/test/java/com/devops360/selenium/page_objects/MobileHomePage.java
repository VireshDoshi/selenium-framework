package com.devops360.selenium.page_objects;

import com.devops360.selenium.DriverBase;
import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.lazerycode.selenium.util.AssignDriver.initQueryObjects;

public class MobileHomePage {

    private Query userId = new Query().defaultLocator(By.id("C2__USER_NAME"));
    private Query contactNumber = new Query().defaultLocator(By.id("C2__QUE_D1B0884CB20C5FFB341823"));
    private Query loginButton = new Query().defaultLocator(By.id("C2__Login-LoginPage-Login"));
    private Query dobInput = new Query().defaultLocator(By.id("C2__QUE_7FF7CAFAACF93118221880"));
    private Query pin1 = new Query().defaultLocator(By.id("C2__QUE_E5926CB29B1866D1625680"));
    private Query pin2 = new Query().defaultLocator(By.id("C2__QUE_E5926CB29B1866D1625681"));
    private Query pin3 = new Query().defaultLocator(By.id("C2__QUE_E5926CB29B1866D1625683"));
    private Query pinLoginButton = new Query().defaultLocator(By.id("C2__Login-LoginPinPage-Login"));
    private Query termsRow = new Query().defaultLocator(By.id("C2__QUE_5C9476C369D3E12B401113_0"));
    private Query termsContinueButton = new Query().defaultLocator(By.id("C2__Login-TermsAndCondition-Continue"));

    public MobileHomePage() throws Exception {
        initQueryObjects(this, DriverBase.getDriver());
    }

    public void enterLogin() {

        this.
        userId.findWebElement().clear();
        userId.findWebElement().sendKeys("111111");

        if (dobInput.findWebElement().isDisplayed()){
            System.out.println("date of birth is displayed");
            try {
                dobInput.findWebElement().clear();
                dobInput.findWebElement().sendKeys("01");
                dobInput.findWebElement().clear();
                dobInput.findWebElement().sendKeys("01");
                dobInput.findWebElement().sendKeys("01");
                dobInput.findWebElement().sendKeys("1980");
            }
            catch ( Exception NoSuchElement){
                System.out.println("date of birth is displayed (catch)" );
                dobInput.findWebElement().clear();
                dobInput.findWebElement().sendKeys("01");
                dobInput.findWebElement().sendKeys("01");
                dobInput.findWebElement().sendKeys("1980");
            }
        }
        else {
            System.out.println("Contact number is displayed");
            contactNumber.findWebElement().sendKeys("1111");
        }
    }

    public void submitLogin() {
        System.out.println("click on the login button");
        loginButton.findWebElement().click();

    }

    public void enterPin() {
        System.out.println("enter pin numbers");

        try{
            pin1.findWebElement().sendKeys("1");
            pin2.findWebElement().sendKeys("1");
            pin3.findWebElement().sendKeys("1");
        }
        catch (Exception NoSuchElement) {
            submitLogin();
            pin1.findWebElement().sendKeys("1");
            pin2.findWebElement().sendKeys("1");
            pin3.findWebElement().sendKeys("1");

        }

    }

    public void submitPinLogin() {
        System.out.println("submit pin login");
        pinLoginButton.findWebElement().isSelected();
        pinLoginButton.findWebElement().click();
    }

    public void clickTerms() {
        try {
            termsRow.findWebElement().click();
        }
        catch (Exception e) {
            termsRow.findWebElement().sendKeys(Keys.SPACE);

        }

    }

    public void clickContinue() {

         termsContinueButton.findWebElement().click();
        }
}
