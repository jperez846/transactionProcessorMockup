import org.example.ProcessReport;
import org.example.TransactionProcessorApp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*; // import static so we do not need to append class name to the static methods we want to use


public class TransactionProcessorAppTest {
    @Test
    void testProcessor() {
        String[] inputFileLines = loadFileLines();
        System.out.println("Inside the the test processor");
        //Arrays.stream(inputFileLines).forEach(input -> System.out.println("Input: " + input));
        TransactionProcessorApp transactionProcessorApp = new TransactionProcessorApp();
        ProcessReport processReport = transactionProcessorApp.processTransactions(inputFileLines, "csv");

        assertTrue(processReport.results.stream().anyMatch(result -> result.contains("msg-1:APPLIED")));


    }

    private static String[] loadFileLines() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/test/resources/input.txt"));
            String str;
            List<String> list = new ArrayList<String>();
            while ((str = in.readLine()) != null) {
                list.add(str);

            }
            String[] stringArr = list.toArray(new String[0]);
            return stringArr;
        } catch (IOException e) {
            System.out.println("Could not load file." + e.getMessage());
        }
        return null;
    }
}
