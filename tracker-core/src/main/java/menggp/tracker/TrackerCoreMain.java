package menggp.tracker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *  Класс содержащий метод main субпроекта Tracker-core
 */

public class TrackerCoreMain {

    public static void main(String args[]) {

        ApplicationContext context = new AnnotationConfigApplicationContext(TrackerCoreContext.class);

    } // end_main

} // end_class
