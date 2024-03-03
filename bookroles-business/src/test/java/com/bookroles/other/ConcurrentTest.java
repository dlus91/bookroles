package com.bookroles.other;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

/**
 * @Author: dlus91
 * @Date: 2024/2/19 10:53
 */
public class ConcurrentTest {

    private static ReentrantLock lock = new ReentrantLock();

    private static CountDownLatch doSomeOperationLatch = new CountDownLatch(1);

    private final static int THREAD_COUNT = 10;

    private static Semaphore semaphore = new Semaphore(THREAD_COUNT);

    private static long awaitTimes = THREAD_COUNT * 100;

    private static AtomicInteger data = new AtomicInteger(1);

    private static List<Map> userList = new ArrayList();

    // 模拟多次调用
    @Test
    public void test1() {
        System.out.println("begin times：" + LocalDateTime.now());
        ConcurrentTest cdt = new ConcurrentTest();
        cdt.doSomeOperationLatch();
        cdt.doSomeOperationLatch();
        cdt.doSomeOperationLatch();
        cdt.doSomeOperationLatch();
        cdt.doSomeOperationLatch();

        System.out.println(cdt.getSomeOperationDataLatch());
    }

    // 模拟多实例接口调用场景
    @Test
    public void test2(){
        System.out.println("begin times：" + LocalDateTime.now());
        ConcurrentTest cdt = new ConcurrentTest();
        int count = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> cdt.doSomeOperationLatch());
        }
        System.out.println(cdt.getSomeOperationDataLatch());

        try {TimeUnit.MILLISECONDS.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        executorService.shutdown();
    }


    @Test
    public void test5(){
        System.out.println("begin times：" + LocalDateTime.now());
        ConcurrentTest cdt = new ConcurrentTest();
        cdt.doSomeOperation(System.currentTimeMillis(), 10, false);
        cdt.doSomeOperation(System.currentTimeMillis(), 20, false);
        cdt.doSomeOperation(System.currentTimeMillis(), 10, true);
        cdt.doSomeOperation(System.currentTimeMillis(), 20, true);
        System.out.println(userList);
    }

    /**
     * @param localTimestamp
     * @param user
     * @param expired 用于强制过期
     */
    // 锁定单个用户的操作，这种方式会锁定方法
    // 适用场景：（实时刷新某个重要数据）话费余额，余额，单个用户多数据源数据同步问题
    public void doSomeOperation(long localTimestamp, Integer user, boolean expired){
        //用户最后操作时间 getUser().getBusinessData().getLastTimes();
        long lastTimestamp = System.currentTimeMillis() - 20000;
        Map findUserMap = getSomeOperation(user);
        if(findUserMap != null && expired) {
            if (localTimestamp - lastTimestamp < 5000) {
                throw new RuntimeException("操作过于频繁，请稍后再试");
            }
            synchronized (user) {
                findUserMap.put("lastTimes", lastTimestamp);
                findUserMap.put("userId", user);
                findUserMap.put("expired", false);
                System.out.println("update:"+findUserMap);
            }
        }else {
            Map userMap = new HashMap();
            userMap.put("lastTimes", lastTimestamp);
            userMap.put("userId", user);
            userMap.put("expired", expired);
            userList.add(userMap);
            System.out.println("add:"+userMap);
        }
    }

    public Map getSomeOperation(Integer user){
        //这里可以用布隆过滤器检查后在获取数据，效率较高
        Optional<Map> findUserMap = userList.stream().filter(
                (singleUser) -> Integer.valueOf(singleUser.getOrDefault("userId",0).toString())==user)
                .findFirst();
        return findUserMap.isEmpty() ? null : findUserMap.get();
    }

    // 更新所有并解决高并发问题
    // 类似ETL集中式单实例处理数据，实时分布式锁的java实现版
    // 适用场景：（实时场景）股市，排行榜，热门新闻，棋牌，游戏，这种刷新整体数据给所有用户的场景
    public void doSomeOperationLatch(){
        try {
            if(lock.tryLock() && doSomeOperationLatch.getCount() == 1){
                System.out.println(Thread.currentThread().getName()+"线程获取数据并设置数据：10");
                try {TimeUnit.MILLISECONDS.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
                data.set(10);
                doSomeOperationLatch.countDown();
            }

        }finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    public int getSomeOperationDataLatch(){
        try{
            try {TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println("在200ms等待尽可能多的线程，一起获取已更新数据");
            doSomeOperationLatch.await();
            System.out.println("获取已更新数据："+data.get());
        }catch (InterruptedException e){
            //需警报
            throw new RuntimeException(e.getMessage());
        }finally {
            //重置
            doSomeOperationLatch = new CountDownLatch(1);
            System.out.println("doSomeOperationLatch重置成功");
        }
        return data.get();
    }




}
