Feature: Car check

  Scenario Outline: Read input file for Car registrations, obtain results from cartaxcheck.com
    Given the input file "<inputFile>" contains car registrations
    When the vehicle identities data are obtained from cartaxcheck
    And verify that search for all registrations from input file found vehicle data
    Examples:
      | inputFile      |
      | car_input1.txt |


  Scenario Outline: Read input file for Car registrations, obtain information from cartaxcheck.com and verify against the output file
    Given the input file "<inputFile>" contains car registrations
    When the vehicle identities data are obtained from cartaxcheck
    And the vehicle data is obtained output file "<outputFile>"
    Then the vehicle data from cartaxcheck is verified against outputfile
    Examples:
      | inputFile      | outputFile      |
      | car_input1.txt | car_output1.txt |
      | car_input2.txt | car_output1.txt |
      | car_input2.txt | car_output2.txt |

