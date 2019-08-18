package menggp.server.services;

import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class WriteLocationServiceTest {

    private static final Logger Log = LoggerFactory.getLogger(WriteLocationServiceTest.class);

    @Test
    public void writeFile() {
        WriteLocationService testWriteLocationService = new WriteLocationService();
        // дописываем в файл тестовую строку
        testWriteLocationService.writeFile("test_string");

        // считываем из файла последнюю строку
        String pathToFile = ".\\LogLocation.txt";
        String lastLine = "", sCurrentLine;

        try ( BufferedReader br = new BufferedReader( new FileReader(pathToFile))  )
        {
            while ((sCurrentLine = br.readLine()) != null)
                lastLine = sCurrentLine;
        }
        catch (IOException ex) {
            Log.info(ex.getMessage());
        }

        assertEquals("test_string",lastLine);
    }


} // end_tests