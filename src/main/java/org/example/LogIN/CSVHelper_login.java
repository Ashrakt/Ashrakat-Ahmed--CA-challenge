package org.example.LogIN;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVHelper_login {

        @DataProvider(name = "csvData")
        public static  Object[][] getCSVData(String filePath) throws IOException, CsvException {
            // Create a CSVReader object
            CSVReader reader = new CSVReader(new FileReader(filePath));

            // Read all rows from the CSV file
            List<String[]> rows = reader.readAll();
            // Create a 2D array to store data
            Object[][] data = new Object[rows.size() - 1][3];  // 3 columns of data

            // Skip header row and populate the data array
            for (int i = 1; i < rows.size(); i++) {
                if (rows.get(i)[2] != null) {
                    data[i - 1][0] = rows.get(i)[0]; // username
                    data[i - 1][1] = rows.get(i)[1]; // password
                    data[i - 1][2] = rows.get(i)[2]; // message
                }
                else
                    break;
            }

            reader.close(); // Close the CSVReader
            return data;
        }

}