package menggp.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *  Copy from tall-2017 on 13.06.2019
 */
public class PointDTOTest {
    private String expected = "{\"lat\":56.0,\"lon\":74.0,\"autoId\":\"o567gfd\",\"time\":1560770948193}";
    private String autoId = "o567gfd";

    @Test
    public void toJson() throws Exception {
        PointDTO point = new PointDTO();
        point.setLat(56);
        point.setLon(74);
        point.setAutoId("o567gfd");
        point.setTime(System.currentTimeMillis());
        assertTrue( point.toJson().contains("\"lat\":56") );
        assertTrue(point.toJson().contains("\"time\":"));
        System.out.println(point.toJson());
    }

    @Test
    public void decodeDto() throws Exception {

        // создаем экземпляр класса ObjectMapper - из библиотеки faterxml
        ObjectMapper mapper = new ObjectMapper();

        // создаем экзампляр класса PointDTO и инициализируем его с помошью объекта "mapper" используя значениями аттрибута expected
        // expected - содержит строку, являющуюся результатом преобразования объекта класса PointDTO в json,
        // соответственно тут мы получем обратное преобразования json -> PointDTO
        PointDTO dto = mapper.readValue(expected, PointDTO.class);

        // проверяем корректность преобразования
        assertEquals(autoId, dto.getAutoId());
        assertEquals(1560770948193L, dto.getTime());
        assertEquals(56.0, dto.getLat(), 0);
        assertEquals(74.0, dto.getLon(), 0);

        // выводим на поля объекта "после преобразования"
        System.out.println(dto.toString());

    }
}
