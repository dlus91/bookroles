package com.bookroles.other;

import org.junit.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Author: dlus91
 * @Date: 2024/2/29 22:58
 */
public class AtomicIntegerArrayTest {

    @Test
    public void test1(){
        int size = 10;
        if(size > Integer.MAX_VALUE){
            size = Integer.MAX_VALUE;
        }
        //它可以用于布隆过滤器
        AtomicIntegerArray chain = new AtomicIntegerArray(size);
        for (int i = 0; i < 5; i++) {
            chain.compareAndSet(i, 0, 1);
        }
        System.out.println(chain);
    }

    //AtomicIntegerArray+Semaphore 可以模拟线程池
    @Test
    public void test2(){
        Pool pool = new Pool(10);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(()->{
                pool.borrow();
                //something
//                pool.free(finalI);
            }).run();
        }

        try {TimeUnit.MILLISECONDS.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(()->{
                pool.free(finalI);
            }).run();
        }
    }

    class Pool{
        private int poolSize;
        //0空闲，1繁忙
        private AtomicIntegerArray states;
        private Semaphore semaphore;

        public Pool(int poolSize) {
            this.poolSize = poolSize;
            this.semaphore = new Semaphore(poolSize);
            this.states = new AtomicIntegerArray(poolSize);
        }

        public void borrow(){
            //获取许可
            try {
                //没有许可的线程，在此等待
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < poolSize; i++) {
                //获取空闲线程
                if(states.get(i) == 0){
                    if(states.compareAndSet(i, 0, 1)){
                        System.out.println(i+"号线程设置为繁忙");
                        break;
                    }
                }
            }
            System.out.println(states);
        }

        public void free(int num){
            for (int i = 0; i < poolSize; i++) {
                if(i == num){
                    states.set(i, 0);
                    semaphore.release();
                    System.out.println(i+"号线程已归还，空闲中");
                    break;
                }
            }
        }

    }


}
