package menggp.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class WriteLocationService {

    // Константы
    //------------------------------------------------------------------------
    private static final Logger Log = LoggerFactory.getLogger(WriteLocationService.class);
    private static final String FILE_PATH = ".\\LogLocation.txt";

    // Аттрибуты
    //------------------------------------------------------------------------

    // Методы
    //------------------------------------------------------------------------

    // метод пишет сразу в лог и в файл (дозапись в конец файла)
    public void writeAll(String str) {
        // пишем в лог
        new WriteLocationService().writeLog(str);
        new WriteLocationService().writeFile(str);

    } // end_method

    // метод пишет в лог
    public void writeLog(String str) {
        Log.info(str);
    } // end_method

    // метод пишет в файл (дозаписмь в конец файла)
    public void writeFile(String str) {

        try ( PrintWriter printWriter = new PrintWriter( new FileWriter(FILE_PATH,true)  ) )
        {
            printWriter.println(str);
        }
        catch (IOException ex) {
            Log.info(ex.getMessage());
        }

    } // end_method


}  // end_class
