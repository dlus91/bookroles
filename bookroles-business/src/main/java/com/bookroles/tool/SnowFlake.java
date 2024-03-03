package com.bookroles.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: dlus91
 * @Date: 2023/9/14 21:42
 */
public class SnowFlake {

    final static Logger logger = LoggerFactory.getLogger(SnowFlake.class);

    // 起始的时间戳，这个时间戳可以是你的系统初始时间，一般取当前时间戳
    private final static long START_TIMESTAMP = 1693497600000L; // 2023-09-01 00:00:00

    // 每一部分占用的位数，可以根据自己的需求进行调整，这里是按照默认的占位数进行分配
    private final static long SEQUENCE_BIT = 12; // 序列号占用的位数
    private final static long MACHINE_BIT = 5; // 机器标识占用的位数
    private final static long DATA_CENTER_BIT = 5; // 数据中心占用的位数

    // 每一部分的最大值，可以根据占用的位数进行计算得到
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);

    // 每一部分向左的位移，计算出来的值是为了后面生成 ID 做准备
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private long dataCenterId; // 数据中心 ID
    private long machineId; // 机器 ID
    private long sequence = 0L; // 序列号
    private long lastTimeStamp = -1L; // 上一次时间戳

    /**
     * <h2>构造方法</h2>
     * @param dataCenterId 数据中心 ID
     * @param machineId 机器 ID
     * */
    public SnowFlake(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            String exceptionStr = "数据中心标识不能大于等于 " + MAX_DATA_CENTER_NUM + " 或小于 0";
            logger.error(exceptionStr);
            throw new IllegalArgumentException(exceptionStr);
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            String exceptionStr = "机器标识不能大于等于 " + MAX_MACHINE_NUM + " 或小于 0";
            logger.error(exceptionStr);
            throw new IllegalArgumentException(exceptionStr);
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * <h2>雪花算法核心方法</h2>
     * 通过调用 nextId() 方法，让当前这台机器上的 snowflake 算法程序生成一个全局唯一的 id
     * */
    public synchronized long nextId() {
        // 获取系统当前时间戳
        long currentTimeStamp = getSystemCurrentTimeMillis();
        if (currentTimeStamp < lastTimeStamp) {
            String exceptionStr = "时钟向后移动，拒绝生成雪花算法ID";
            logger.error(exceptionStr);
            throw new RuntimeException(exceptionStr);
        }

        if (currentTimeStamp == lastTimeStamp) {
            // 当前毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 序列号超出范围，需要等待下一毫秒
            if (sequence == 0L) {
                // 获取下一毫秒
                currentTimeStamp = getNextMill(lastTimeStamp);
            }
        } else {
            // 不同毫秒内，序列号置为 0
            sequence = 0L;
        }

        lastTimeStamp = currentTimeStamp;

        // 使用位运算生成最终的 ID
        return (currentTimeStamp - START_TIMESTAMP) << TIMESTAMP_LEFT
                | dataCenterId << DATA_CENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    /**
     * <h2>获取系统当前时间戳</h2>
     * @return 当前时间(毫秒)
     */
    private long getSystemCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * <h2>获取下一毫秒</h2>
     * 当某一毫秒的时间，产生的 id 数 超过4095，系统会进入等待，直到下一毫秒，系统继续产生 ID
     * @param lastTimestamp 上次生成 ID 的时间截
     * @return 当前时间戳
     */
    private long getNextMill(long lastTimestamp) {
        long timeMillis = getSystemCurrentTimeMillis();
        while(timeMillis <= lastTimestamp){
            timeMillis = getSystemCurrentTimeMillis();
        }
        return timeMillis;
    }

    /**
     * <h2>测试类</h2>
     */
    public static void main(String[] args) throws ParseException {
        SnowFlake worker1 = new SnowFlake(10,3);
        SnowFlake worker2 = new SnowFlake(2,1);
        SnowFlake worker3 = new SnowFlake(3,1);
        for (int i = 0; i < 30; i++){
            System.out.println("数据中心10，雪花算法 ID：" + worker1.nextId());
            System.out.println("数据中心2，雪花算法 ID：" + worker2.nextId());
            System.out.println("数据中心3，雪花算法 ID：" + worker3.nextId());
        }

        String dateStr = "2023-09-01 00:00:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(dateStr);
        System.out.println(date.getTime());

        SimpleDateFormat ft =
                new SimpleDateFormat ("yyyy.MM.dd HH:mm:ss");
        System.out.println(ft.format(date));
    }
}
