# Project uses Serenity BDD and Serenity reports

## Get the code

```git clone https://github.com/devadathpt2/cartaxcheck```

```cd cartaxcheck```

The project directory structure
The project has build scripts for Maven and follows the standard directory structure used in most Serenity projects:

src
  + main
  + test
    + java                          Test runners and supporting code
    + resources
      + features                    Feature files
          freeCarCheck.feature  
      + webdriver                   Bundled webdriver binaries
        + windows
          chromedriver.exe          OS-specific Webdriver binaries

## Executing the tests

### Runs on chrome by default.
```$ mvn clean verify```

### To run on firefox
```$ mvn clean verify -Dwebdriver.driver=firefox```
## or
```$ mvn clean verify -D"webdriver.driver=firefox"```


### Test reports
The test results will be recorded in the target/site/serenity directory.
Open index.html to view results. Each test result can be seen by clicking on the links

### How the code stores data from inputfile and outputfile while executing

#### Data from search from the site stored in List<HashMap<String,String>> vehiclesDataFromCarTaxCheck within the code as shown in sample below

[

{colour=black, year=2009, registered=15 jun 2009, registration=dn09hrm, model=320d se, v5c issue date=13 jul 2022, make=bmw}, 
{colour=red, year=2008, registered=18 feb 2008, registration=bw57bof, model=yaris t2, v5c issue date=29 jul 2022, make=toyota}, 
{colour=white, year=2017, registered=20 jul 2017, registration=kt17dlx, model=superb sportline tdi s-a, v5c issue date=04 jan 2018, make=skoda},
{colour=white, year=2018, registered=19 mar 2018, registration=sg18htn, model=golf se navigation tsi evo, v5c issue date=23 sep 2021, make=volkswagen}

]

#### Data from output file is stored in List<HashMap<String,String>> outputFileData within the code as shown in sample below
[

{colour=white, year=2018, registration=sg18htn, model=golf se navigation tsi evo, make=volkswagen},
{colour=black, year=2009, registration=dn09hrm, model=320d se, make=bmw},
{colour=red, year=2008, registration=bw57bof, model=yaris t2, make=toyota},
{colour=white, year=2017, registration=kt17dlx, model=superb sportline tdi s-a, make=skoda}

]

#### The assertion then picks data for each vehicle from output file and compares whether data from searching website matches.
