package com.techTest.page;

import net.thucydides.core.pages.PageObject;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

public class CarTaxCheckPage extends PageObject {

    final SoftAssertions soft = new SoftAssertions();
    By searchBox = By.xpath("//*[@id=\"vrm-input\"]");
    By freeCheckButton = By.xpath("//*[@id=\"m\"]/div[2]/div/div/div/div/form/button");
    By vehicleIdentity = By.xpath("//*[text()='Vehicle Identity']");
    By vehicleNotFound = By.xpath("//span[text()='Vehicle Not Found']");
    By vehicleIdentityData = By.xpath("//*[@id=\"m\"]/div[2]/div[3]/div[1]/div/span/div[2]/dl[*]");
    String registration = "registration";

    public void openCarTaxCheckPage() {
        getDriver().manage().deleteAllCookies();
        open();
    }


    public List<HashMap<String, String>> getAllVehiclesDataFromCatTaxCheckSite(List<String> registrations) throws InterruptedException {

        List<HashMap<String, String>> vehiclesDataFromCarTaxCheck = new ArrayList<>();

        for (String reg : registrations) {

            searchForCarReg(reg);

            HashMap<String, String> vehicleData = new HashMap<>();

            List<WebElement> vehicleDataItems = getDriver().findElements(vehicleIdentityData);

            vehicleDataItems.forEach(v -> {
                    if ( v.findElement(By.tagName("dt")).getText().equalsIgnoreCase("Registration"))
                    {
                        vehicleData.put(
                                v.findElement(By.tagName("dt")).getText().toLowerCase(),
                                reg.toLowerCase());}
                    else {

                    vehicleData.put(
                            v.findElement(By.tagName("dt")).getText().toLowerCase(),
                            v.findElement(By.tagName("dd")).getText().toLowerCase());}});

            vehiclesDataFromCarTaxCheck.add(vehicleData);
        }

        System.out.println("********* data from search " + vehiclesDataFromCarTaxCheck);
        return vehiclesDataFromCarTaxCheck;

    }

    public void searchForCarReg(String reg) throws InterruptedException {
        openCarTaxCheckPage();
        $(searchBox).clear();
        $(searchBox).sendKeys(reg);
        $(freeCheckButton).waitUntilEnabled();
        $(freeCheckButton).click();
        Thread.sleep(5000);
    }

    public void runAssertionForRegistrationSearchResults(List<HashMap<String, String>> vehiclesDataFromCarTaxCheck){
        for (HashMap<String, String> vehicleData : vehiclesDataFromCarTaxCheck) {
            vehicleData.forEach((key, value) -> Assert.assertFalse("The search for registration number "
                    + vehicleData.get("registration").toUpperCase()
                    + " from inputfile found no results ",value.equalsIgnoreCase("")));

        }
    }

    public void runAssertionInputVsOutput(List<HashMap<String, String>> outputFileData,
                                          List<HashMap<String, String>> vehiclesDataFromCarTaxCheck) {

        for (HashMap<String, String> vehicles : outputFileData) {

            registration = vehicles.get("registration");
            try {
                HashMap<String, String> tempMap = new HashMap<>();


                 tempMap = vehiclesDataFromCarTaxCheck.stream().filter(v -> v.containsValue(registration))
                        .findFirst().orElseThrow(()->new Exception("No value found"));

                // get the headers(REGISTRATION,MAKE,MODEL,COLOUR,YEAR)
                Set<String> keys = vehicles.keySet();

                for (String key : keys) {

                    Assert.assertEquals("For registration " + registration.toUpperCase() +
                            " from output file " + key + " -- ", (vehicles.get(key)),tempMap.get(key));
                }
            } catch (Exception e) {
                Assert.fail(e.getMessage() + " -- The registration " + registration.toUpperCase() +
                        " found in output file but not found in the inputfile : ");
            }
        }
    }
}
