package org.example.Petfinder_Register;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVHelper_Register {

        @DataProvider(name = "csvData_reg")
        public static  Object[][] getCSVData(String filePath) throws IOException, CsvException {
            // Create a CSVReader object
            CSVReader reader = new CSVReader(new FileReader(filePath));

            // Read all rows from the CSV file
            List<String[]> rows = reader.readAll();
            // Create a 2D array to store data
            Object[][] data = new Object[rows.size() - 1][8];  // 8 columns of data

            // Skip header row and populate the data array
            for (int i = 1; i < rows.size(); i++) {
                if (rows.get(i)[7] != null) {
                    data[i - 1][0] = rows.get(i)[0]; // email
                    data[i - 1][1] = rows.get(i)[1]; // firstname
                    data[i - 1][2] = rows.get(i)[2]; // lastname
                    data[i - 1][3] = rows.get(i)[3]; // postal
                    data[i - 1][4] = rows.get(i)[4]; // cats
                    data[i - 1][5] = rows.get(i)[5]; // dogs
                    data[i - 1][6] = rows.get(i)[6]; // password
                    data[i - 1][7] = rows.get(i)[7]; // message
                }
                else
                    break;
            }

            reader.close(); // Close the CSVReader
            return data;
        }

}