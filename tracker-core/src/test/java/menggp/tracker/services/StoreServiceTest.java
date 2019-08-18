package menggp.tracker.services;

import org.junit.Test;

import static org.junit.Assert.*;

public class StoreServiceTest {

    private  String testString = "test_string";

    @Test
    public void putToQueue() throws InterruptedException {
        StoreService testStoreService = new StoreService();
        testStoreService.putToQueue(testString);
        String result = testStoreService.takeFromQueue();
        assertEquals("test_string",result);
    }

    @Test
    public void sizeOfQueue() throws InterruptedException {
        StoreService testStoreService = new StoreService();

        testStoreService.putToQueue(testString);
        assertEquals("sizeOfQueue #1 ",1,testStoreService.sizeOfQueue(),0);

        testStoreService.putToQueue(testString);
        assertEquals("sizeOfQueue #2 ",2,testStoreService.sizeOfQueue(),0);

        String result = testStoreService.takeFromQueue();
        assertEquals("sizeOfQueue #3 ",1,testStoreService.sizeOfQueue(),0);

    }

    @Test
    public void takeFromQueue() throws  InterruptedException {
        StoreService testStoreService = new StoreService();
        testStoreService.putToQueue(testString);
        String result = testStoreService.takeFromQueue();
        assertEquals("test_string",result);
    }
}