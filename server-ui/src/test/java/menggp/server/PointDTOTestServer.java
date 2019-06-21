package menggp.server;


import menggp.dto.PointDTOTest;
import org.junit.Test;

public class PointDTOTestServer {

    @Test
    // проверка преобразования в JSON - используя класса PointDTOTest
    public void toJson() throws Exception {
        //  создаем объект класса PointDTOTest
        PointDTOTest pDTOTest = new PointDTOTest();
        // вызываем мото "toJson" - проверка преобразования в JSON
        pDTOTest.toJson();
    } // end method

    @Test
    // проверка обратного преобразования JSON - PointDTO
    public void decodeDTO() throws Exception {
        // работает аналогично методу toJson()
        PointDTOTest pDTOTest = new PointDTOTest();
        pDTOTest.decodeDto();
    } // end method


} // end_class
