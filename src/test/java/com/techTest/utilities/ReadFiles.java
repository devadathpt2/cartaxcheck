package com.techTest.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class ReadFiles {
    private static final String patternString = "^[A-Z][A-Z][0-9][0-9][A-Z][A-Z][A-Z]$";
    private static final String patternString1 = "^[A-Z][A-Z][0-9][0-9]$";
    private static final String patternString2 = "^[A-Z][A-Z][A-Z]$";
    private static final String patternString3 = "^[A-Z][A-Z][0-9][0-9]\\s[A-Z][A-Z][A-Z]$";
    private static final Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
    private static final Pattern pattern1 = Pattern.compile(patternString1, Pattern.CASE_INSENSITIVE);
    private static final Pattern pattern2 = Pattern.compile(patternString2, Pattern.CASE_INSENSITIVE);
    private static final Pattern pattern3 = Pattern.compile(patternString3, Pattern.CASE_INSENSITIVE);
    private static FileReader fileReader;
    private final List<String> outputFileLines = new ArrayList<>();
    private final List<String> registrations = new ArrayList<>();
    private final List<HashMap<String, String>> outputFileAllData = new ArrayList<>();
    String path = "src/test/resources/files/";
    private String[] outPutFileheaders = null;
    private String[] outputFileRows = null;
    private String line;

    public List<String> readInputFile(String fileName) throws FileNotFoundException {
        // read input file for vehicle registration using patterns and return all registrations
        fileReader = new FileReader(path + fileName);
        try (BufferedReader br = new BufferedReader(fileReader)) {
            while ((line = br.readLine()) != null) {
                String[] inputFileLines = line.replaceAll("[^a-zA-Z0-9]", " ").split("\\s");
                for (String w : inputFileLines
                ) {
                    int i = Arrays.asList(inputFileLines).indexOf(w);
                    Matcher matcher = pattern.matcher(w);
                    Matcher matcher1 = pattern1.matcher(w);
                    if (matcher.matches()) {
                        registrations.add(w);
                    } else if (matcher1.matches()) {

                        Matcher matcher2 = pattern2.matcher(inputFileLines[i + 1]);
                        Matcher matcher3 = pattern3.matcher(w + " " + inputFileLines[i + 1]);
                        if (matcher2.matches() && matcher3.matches()) {
                            registrations.add(w + inputFileLines[i + 1]);
                        }
                    }
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return registrations;
    }

    public List<HashMap<String, String>> readOutputFile(String fileName) throws FileNotFoundException {

        fileReader = new FileReader(path + fileName);
        try (BufferedReader br = new BufferedReader(fileReader)) {

            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
        //Store each line from output file into outputFileLines
                    outputFileLines.add(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Store headers from output file into outputFileHeaders
        outPutFileheaders = outputFileLines.get(0).split(",");

        // read each line from output file except headers and store data in outputFileAllData
        outputFileLines.stream().skip(1).forEach(l -> {
            HashMap<String, String> outputFileSingeVehicleData = new HashMap<>();
            outputFileRows = l.split(",");
            IntStream.rangeClosed(0,outputFileRows.length-1 )
                    .forEach(
                            i->outputFileSingeVehicleData.put(outPutFileheaders[i].toLowerCase(),
                            outputFileRows[i].toLowerCase())
                    );
            outputFileAllData.add(outputFileSingeVehicleData);

        });
        System.out.println("********* data from output file "+ outputFileAllData);
        return outputFileAllData;
    }
}
