package com.techTest.steps;

import com.techTest.page.CarTaxCheckPage;
import com.techTest.utilities.ReadFiles;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CarCheckStepdefs {

    ReadFiles readFiles = new ReadFiles();
    List<String> regFromInputFile = new ArrayList<>();
    List<HashMap<String, String>> vehiclesDataFromCarTaxCheck = new ArrayList<>();
    List<HashMap<String, String>> outputFileData = new ArrayList<>();
    CarTaxCheckPage carTaxCheckPage;

    @Given("the input file {string} contains car registrations")
    public void theInputFileContainCarRegistrations(String inputFile) throws FileNotFoundException {
        regFromInputFile = readFiles.readInputFile(inputFile);
    }

    @When("the vehicle identities data are obtained from cartaxcheck")
    public void theVehicleIdentitiesAreObtainedFromCarTaxCheck() throws InterruptedException {
        vehiclesDataFromCarTaxCheck = carTaxCheckPage.getAllVehiclesDataFromCatTaxCheckSite(regFromInputFile);
    }

    @And("verify that search for all registrations from input file found vehicle data")
    public void verifyThatSearchForAllRegistrationsFromInputFileFoundVehicleData(){
        carTaxCheckPage.runAssertionForRegistrationSearchResults(vehiclesDataFromCarTaxCheck);
    }
    @And("the vehicle data is obtained output file {string}")
    public void theVehicleIdentitiesAreVerifiedAgainstOutPutFile(String outputFile) throws FileNotFoundException {
        outputFileData = readFiles.readOutputFile(outputFile);
    }

    @Then("the vehicle data from cartaxcheck is verified against outputfile")
    public void theVehicleDataFromCartaxcheckIsVerifiedAgainstOutputfile() {
        carTaxCheckPage.runAssertionInputVsOutput(outputFileData, vehiclesDataFromCarTaxCheck);
    }
}
