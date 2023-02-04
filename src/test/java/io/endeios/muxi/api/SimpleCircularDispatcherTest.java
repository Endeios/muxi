package io.endeios.muxi.api;

import io.endeios.muxi.utils.SimpleCircularDispatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SimpleCircularDispatcherTest {
    @Test
    void exists() {
        List<String> asdas = List.of("asdas", "sdfsdf");
        assertNotNull(new SimpleCircularDispatcher(asdas));
    }

    @Test
    void returnTheSameObjectWhenCalledModuloTimes() {
        List<String> asdas = List.of("2348902348234","asdas", "sdfsdf");
        SimpleCircularDispatcher<String> disp = new SimpleCircularDispatcher<>(asdas);
        int size = disp.getSize();
        assertEquals(asdas.size(),size);
        String[] objs= new String[size];
        for (int j=0; j < size; j++){
            objs[j] = disp.getNext();
        }
        String[] objs2= new String[size];
        for (int j=0; j < size; j++){
            objs2[j] = disp.getNext();
        }

        for (int i = 0; i < objs.length; i++) {
            assertNotNull(objs[i]);
            assertNotNull(objs2[i]);
            assertEquals(objs[i],objs2[i]);
        }



    }
}
