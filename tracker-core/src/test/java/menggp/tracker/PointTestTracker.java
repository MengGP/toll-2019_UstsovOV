package menggp.tracker;

import menggp.dto.PointTest;
import org.junit.Test;

public class PointTestTracker {

    @Test
    // проверка преобразования в JSON - используя класса PointTest
    public void encodeDTO() throws Exception {
        //  создаем объект класса PointTest
        PointTest pDTOTest = new PointTest();
        // вызываем мото "encodeDTO" - проверка преобразования в JSON
        pDTOTest.encodeDTO();
    } // end method

    @Test
    // проверка обратного преобразования JSON - Point
    public void decodeDTO() throws Exception {
        // работает аналогично методу encodeDTO()
        PointTest pDTOTest = new PointTest();
        pDTOTest.decodeDto();
    } // end method

} // end_class
