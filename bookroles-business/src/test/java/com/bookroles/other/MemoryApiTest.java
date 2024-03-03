package com.bookroles.other;

import org.junit.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * @Author: dlus91
 * @Date: 2024/2/29 22:43
 */
public class MemoryApiTest {

    private static VarHandle COUNT_HANDLE;

    static {
        try {
            COUNT_HANDLE = MethodHandles.lookup().findVarHandle(MemoryApiTest.class, "count", int.class);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private volatile int count;

    public void increment(){
        int oldValue;
        int newValue;
        do {
            oldValue = (int) COUNT_HANDLE.getVolatile(this);
            newValue = oldValue + 1;
        } while (!COUNT_HANDLE.compareAndSet(this, oldValue, newValue));
    }

    public int getCount(){
        return count;
    }



    @Test
    public void test1(){
        MemoryApiTest memoryApiTest = new MemoryApiTest();
        int i = memoryApiTest.getCount();
        System.out.println(memoryApiTest.getCount());
        while (memoryApiTest.getCount() < 10) {
            memoryApiTest.increment();
            System.out.println(memoryApiTest.getCount());
        }

    }



}
