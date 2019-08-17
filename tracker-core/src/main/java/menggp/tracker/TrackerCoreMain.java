package menggp.tracker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 *  Устаревший
 *        - функию этого класса взял на себя menggp.tracker.springApp.Application
 *
 *  Класс содержащий метод main субпроекта Tracker-core
 */

public class TrackerCoreMain {

    public static void main(String args[]) throws InterruptedException {

        ApplicationContext context = new AnnotationConfigApplicationContext(TrackerCoreContext.class);

    } // end_main

} // end_class
