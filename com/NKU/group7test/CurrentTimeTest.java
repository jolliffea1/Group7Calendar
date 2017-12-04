package group7test;

import static org.junit.Assert.*;
import java.util.ArrayList;

import group7calendar.currentTimeFormatter;
import org.junit.Test;

public class CurrentTimeTest {
    public String setup(int listIndex) {
        currentTimeFormatter ct = new currentTimeFormatter();

        ArrayList<String> list = new ArrayList<String>();
        list.add(ct.getCurrentTime("00:00:00"));
        list.add(ct.getCurrentTime("11:59:59"));
        list.add(ct.getCurrentTime("12:00:00"));
        list.add(ct.getCurrentTime("13:00:00"));
        list.add(ct.getCurrentTime("23:59:59"));
        list.add(ct.getCurrentTime("abc"));

        return list.get(listIndex);
    }

    @Test
    public void test1() {
        String test = setup(0);
        System.out.println("test1 ran");
        assertEquals("12:00:00 AM", test);
    }
    @Test
    public void test2() {
        String test = setup(1);
        System.out.println("test2 ran");
        assertEquals("11:59:59 AM", test);
    }
    @Test
    public void test3() {
        String test = setup(2);
        System.out.println("test3 ran");
        assertEquals("12:00:00 PM", test);
    }
    @Test
    public void test4() {
        String test = setup(3);
        System.out.println("test4 ran");
        assertEquals("1:00:00 PM", test);
    }
    @Test
    public void test5() {
        String test = setup(4);
        System.out.println("test5 ran");
        assertEquals("11:59:59 PM", test);
    }
    @Test
    public void test6() {
        String test = setup(5);
        System.out.println("test6 ran");
        assertEquals("abc", test);
    }
}

