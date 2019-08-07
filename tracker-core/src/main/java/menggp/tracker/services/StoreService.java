package menggp.tracker.services;

import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *  Сервис хранения данных GPS (очередь)
 *      - кладет в очередь данные геерируемые GpsService
 *      - отправляет данные из очереди с помощью SendService
 *      - класс связан с классами GpsService и SendService с помощью аннотации @Autwoired
 */

@Service
public class StoreService {

    // связанные классы
    //------------------------------------------------------------------------

    // аттрибуты
    //------------------------------------------------------------------------
    private BlockingDeque<String> queue = new LinkedBlockingDeque<>(1000);

    // методы
    //------------------------------------------------------------------------

    // метод реализует наполнение очереди
    public void putToQueue(String str) throws InterruptedException {
        queue.put(str);
        return;
    }

    // метод возврящает размер очереди
    public int sizeOfQueue() throws InterruptedException {
        return queue.size();
    } // end_method

    // метод реализующий извлечение объектов из очереди - извлечение с блокировкой
    public String takeFromQueue() throws InterruptedException {
        return queue.take();
    } // end_method


} // end_class
