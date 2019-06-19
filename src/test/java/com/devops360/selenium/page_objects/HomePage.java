package com.devops360.selenium.page_objects;

import com.devops360.selenium.DriverBase;
import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.By;

import static com.lazerycode.selenium.util.AssignDriver.initQueryObjects;

public class HomePage {

    private Query edtUserName = new Query().defaultLocator(By.id("C2__USER_NAME"));


    public HomePage() throws Exception {
        initQueryObjects(this, DriverBase.getDriver());
    }

    public void enterLoginDetails() throws InterruptedException {


        String strUserName = "111111";
        String strDOB = "01/01/1980"
        String strPhnDigits = "1111";


        setValue(getObject("launch.edtUserName"), strUserName, "Username");

        if (isElementDisplayed(getObject("launch.edtDateOfBirth"), 1)) {
            setValue(getObject("launch.edtDateOfBirth"), strDOB, "Date of Birth");
        } else {
            setValue(getObject("launch.edtPhoneNumberDigits"), strPhnDigits, "Password");
        }
        clickJS(getObject("launch.btnLogIn"), "Login");
    }
}
