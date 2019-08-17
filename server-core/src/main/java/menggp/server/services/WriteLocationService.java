package menggp.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteLocationService {

    // Константы
    //------------------------------------------------------------------------
    private static final Logger Log = LoggerFactory.getLogger(WriteLocationService.class);

    // Методы
    //------------------------------------------------------------------------

    public void writeAll(String str) {
        // пишем в лог
        new WriteLocationService().writeLog(str);

    } // end_method

    public void writeLog(String str) {
        Log.info(str);
    } // end_method


}  // end_class
