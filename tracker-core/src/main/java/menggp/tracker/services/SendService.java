package menggp.tracker.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SendService {

    // связанные классы
    //------------------------------------------------------------------------
    //@Autowired
    //private StoreService storeService;

    // аттрибуты
    //------------------------------------------------------------------------
    private static final Logger Log = LoggerFactory.getLogger(SendService.class);

    // методы
    //------------------------------------------------------------------------
    public void sendLocation( String str) {
        Log.info(str);
    } // end_method



} //end_class
