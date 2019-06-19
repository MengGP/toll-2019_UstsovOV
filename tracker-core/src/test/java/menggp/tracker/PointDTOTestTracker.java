package menggp.tracker;

import org.junit.Test;
import menggp.dto.PointDTOTest;

public class PointDTOTestTracker {

    @Test
    // проверка преобразования в JSON - используя класса PointDTOTest
    public void toJson() throws Exception {
        //  создаем объект класса PointDTOTest
        PointDTOTest pDTOTest = new PointDTOTest();
        // вызываем мото "toJson" - проверка преобразования в JSON
        pDTOTest.toJson();
    } // end method

} // end_class
