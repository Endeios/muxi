package io.endeios.muxi.utils;

import java.util.ArrayList;
import java.util.List;

public class  SimpleCircularDispatcher<T> {

    private List<T> array;
    private int cursor;

    public SimpleCircularDispatcher(Iterable<T> input) {

        this.array = new ArrayList<>();
        input.forEach(t -> this.array.add(t));
        cursor=0;

    }

    public int getSize() {
        return array.size();
    }

    public T getNext() {

        T retVal = array.get(cursor);
        cursor++;
        if (cursor>getSize()-1)
            cursor = 0;
        return retVal;
    }
}
