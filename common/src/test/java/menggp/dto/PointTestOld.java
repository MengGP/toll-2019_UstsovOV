package menggp.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *  Copy from tall-2017 on 13.06.2019
 */
public class PointTestOld {
    private String json = "{\"lat\":56.0,\"lon\":74.0,\"autoId\":\"o567gfd\",\"time\":1560770948193}";
    private String autoId = "o567gfd";

    @Test
    public void encodeDTO() throws Exception {
        Point point = new Point();
        point.setLat(56);
        point.setLon(74);
        point.setAutoId(autoId);
        point.setTime(System.currentTimeMillis());
        String pointJson = point.toJson();
        assertTrue(pointJson.contains("\"lat\":56") );
        assertTrue(pointJson.contains("\"time\":"));
        System.out.println(pointJson);
    }

    @Test
    public void decodeDto() throws Exception {

        // создаем экземпляр класса ObjectMapper - из библиотеки faterxml
        ObjectMapper mapper = new ObjectMapper();

        // создаем экзампляр класса Point и инициализируем его с помошью объекта "mapper" используя значениями аттрибута json
        // json - содержит строку, являющуюся результатом преобразования объекта класса Point в json,
        // соответственно тут мы получем обратное преобразования json -> Point
        Point dto = mapper.readValue(json, Point.class);

        // проверяем корректность преобразования
        assertEquals(autoId, dto.getAutoId());
        assertEquals(1560770948193L, dto.getTime());
        assertEquals(56.0, dto.getLat(), 0);
        assertEquals(74.0, dto.getLon(), 0);

        // выводим на поля объекта "после преобразования"
        System.out.println(dto.toString());

    }
}
