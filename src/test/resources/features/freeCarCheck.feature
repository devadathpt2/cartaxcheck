Feature: Car check

  Scenario Outline: Read input file for Car registrations, verify whether search produces results from cartaxcheck.com
    Given the input file "<inputFile>" contains car registrations
    When the vehicle identities data are obtained from cartaxcheck
    And verify that search for all registrations from input file found vehicle data
    Examples:
      | inputFile      |
      | car_input1.txt |


#  Scenario Outline: Read input file for Car registrations, obtain information from cartaxcheck.com and verify against the output file
#    Given the input file "<inputFile>" contains car registrations
#    When the vehicle identities data are obtained from cartaxcheck
#    And the vehicle data is obtained output file "<outputFile>"
#    Then the vehicle data from cartaxcheck is verified against outputfile
#    Examples:
#      | inputFile      | outputFile      |
#      | car_input1.txt | car_output1.txt |
#      | car_input2.txt | car_output1.txt |
#      | car_input2.txt | car_output2.txt |
      # Example 1 : car_input1.txt has registration BW57BOW but car_output1.txt has BW57BOF, the test identifies this descrepancy
      # Example 2 : car_input2.txt now has BW57BOF to match car_output1.txt, the test identifies that colour from search is different
      #             to colour from car_output1.txt
      # Example 3 : car_output2.txt is modified to match the colour for BW57BOF from search. This is a happy path scenario where
      #             search results for registrations from car_input2.txt match the car_outp2.txt