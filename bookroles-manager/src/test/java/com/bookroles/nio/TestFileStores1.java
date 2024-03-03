package com.bookroles.nio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;


/**
 * @Author: dlus91
 * @Date: 2023/10/18 20:07
 */
public class TestFileStores1 {

    long beginTime;

    @Before
    public void init(){
        beginTime = System.currentTimeMillis();
    }

    @After
    public void destory(){
        System.out.printf("耗时:%dms\r\n",System.currentTimeMillis() - beginTime);
    }


    @Test
    public void test1() throws IOException {
        for (FileStore fileStore : FileSystems.getDefault().getFileStores()) {
            long total = fileStore.getTotalSpace() / 1024;
            long used = (fileStore.getTotalSpace() - fileStore.getUnallocatedSpace()) / 1024;
            long avail = fileStore.getUsableSpace() / 1024;
            System.out.printf("%-20s %12d %12d %12d%n", fileStore, total, used, avail);
        }
    }

    @Test
    public void test2(){
        String clientLog = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\temp\\";
        String createFile = "test.tmp";
        Path path = FileSystems.getDefault().getPath(clientLog, createFile);
        System.out.println(path);

    }

    @Test
    public void test3() throws IOException, InterruptedException {
        String clientLog = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\temp\\bookroles_access.2023-10-11_1565\\";
        String createFile = "test.tmp";
        //创建文件夹
        Path testPath = Paths.get(clientLog);
        //存在 则 删除
        if(Files.exists(testPath)){
            RmDir.rmdir(testPath);
        }
        //不存在 则 创建
        if(!Files.exists(testPath)){
            Files.createDirectory(testPath);
        }
        Files.createFile(testPath.resolve(createFile));

        //从FileSystem中创建WatchService对象
//        WatchService watcher = FileSystems.getDefault().newWatchService();
//        //注册到test节点以及操作类型
//        testPath.register(watcher, ENTRY_DELETE);
//        // delTxtFiles()文件用于删除.txt文件
//        // 创建一个线程，设置运行前等待时间，删除.txt
//        Executors.newSingleThreadScheduledExecutor().schedule(()->{
//            try {
//                RmDir.rmdir(testPath);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }, 2500, TimeUnit.MILLISECONDS);
//        // 获取监控池（保存时间列表） 一个文件变化动作可能会引发一系列的事件
//        WatchKey key = watcher.take();
//        for (WatchEvent<?> pollEvent : key.pollEvents()) {
//            System.out.println("pollEvent.context():" + pollEvent.context() + "\n " +
//                    "pollEvent.count():" + pollEvent.count() + "\n " +
//                    "pollEvent.kind():" + pollEvent.kind());
//            System.exit(0);
//        }
    }


    @Test
    public void test31() throws IOException, InterruptedException {
        String clientLog = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\temp\\bookroles_access.2023-10-11_1565\\";
        Path testPath = Paths.get(clientLog);
        //从FileSystem中创建WatchService对象
        WatchService watcherDelete = FileSystems.getDefault().newWatchService();
        //注册到test节点以及操作类型
        testPath.register(watcherDelete, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
        int i = 0;
        while (i < 3) {
            // 获取监控池（保存时间列表） 一个文件变化动作可能会引发一系列的事件
            WatchKey key = watcherDelete.take();
            for (WatchEvent<?> pollEvent : key.pollEvents()) {
                System.out.println("pollEvent.context():" + pollEvent.context() + "\n " +
                        "pollEvent.count():" + pollEvent.count() + "\n " +
                        "pollEvent.kind():" + pollEvent.kind());
//            System.exit(0);
                key.reset();
                i++;
            }
        }
    }

    @Test
    public void test32(){
        String name = "asdas";
    }



    @Test
    public void test4() throws IOException {
        String clientLog = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\temp\\bookroles_access.2023-10-11_1565\\";
        //创建/获取文件夹
        Path testPath = Paths.get(clientLog);
        //以 .tmp,.txt结尾的文件
        //这里开发出去可以 自动将空或者* 转为** 并判断开头是 glob:/regex:
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.{tmp,txt}");
        //遍历文件下所有
        Files.walk(testPath)
                .filter(pathMatcher::matches)
                .forEach(System.out::println);


    }


    @Test
    public void test5(){
        String clientLog = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\temp\\bookroles_access";
        Path path = Path.of(clientLog);
        System.out.println(path.normalize());
    }


    //深度拷贝
    @Test
    public void test6(){
        //根节点 3层map
        Map<String, Map<String, Map<String, String>>> rootMap = new HashMap<>();
        //第二层节点 2层map
        Map<String, Map<String, String>> secondOneMap = new HashMap<>();
        Map<String, Map<String, String>> secondTwoMap = new HashMap<>();
        //第三层节点 1层map
        Map<String, String> threeOneMap = new HashMap<>();
        Map<String, String> threeTwoMap = new HashMap<>();
        Map<String, String> threeThreeMap = new HashMap<>();
        Map<String, String> threeFourMap = new HashMap<>();
        threeOneMap.put("threeOne_1", "aaa");// secondOneMap
        threeOneMap.put("threeOne_2", "bbb");// secondOneMap
        threeTwoMap.put("threeTwo_1", "ccc");// secondTwoMap
        threeTwoMap.put("threeTwo_2", "ddd");// secondTwoMap
        threeThreeMap.put("threeThree_1", "eee");// secondOneMap
        threeThreeMap.put("threeThree_2", "fff");// secondOneMap
        threeFourMap.put("threeFour_1", "ggg");// secondTwoMap
        threeFourMap.put("threeFour_2", "hhh");// secondTwoMap
        secondOneMap.put("secondOne_1", threeOneMap);
        secondTwoMap.put("secondTwo_1", threeTwoMap);
        secondOneMap.put("secondOne_2", threeThreeMap);
        secondTwoMap.put("secondTwo_2", threeFourMap);

        rootMap.put("One", secondOneMap);
        rootMap.put("Two", secondTwoMap);

        System.out.println(rootMap);

        System.out.println("====深度拷贝====");
        Map othreMap = Collections.synchronizedMap(new IdentityHashMap<>(rootMap));
        System.out.println(othreMap);
    }

    @Test
    public void test7(){
        NavigableSet<String> set = new ConcurrentSkipListSet<>();
        set.add("aaa");
        set.add("bbb");
        set.add("ccc");
        set.add("ddd");

        System.out.println(set.ceiling("bda"));
        System.out.println(set.floor("caa"));

        System.out.println(set.higher("css"));
        System.out.println(set.lower("add"));

//        System.out.println(set.size());
//        System.out.println(set.pollFirst());
//        System.out.println(set.size());

        System.out.println("---------");
        System.out.println(set.subSet("aaa", "ccc"));
        System.out.println(set.subSet("adc", true, "ccc", true));
        System.out.println("---------");
        System.out.println(set.tailSet("bgf"));

    }

    @Test
    public void test71(){
        NavigableSet<String> set = new ConcurrentSkipListSet<>();
        set.add("bbb");
        set.add("ccc");
        set.add("aaa");
        set.add("ddd");

        //默认升序
        System.out.println(set);
        System.out.println("=============");
        //降序
        System.out.println(set.descendingSet());

    }

    @Test
    public void test72(){
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextGaussian());
        }
    }

    @Test
    public void test73(){
        StringJoiner str =  new StringJoiner(",", "[", "]");
        String aaa = "key1:abc";
        String bbb = "key2:def";
        String abc = "key3:jio";
        List<String> ccc = new ArrayList<>();
        ccc.add(aaa);
        ccc.add(bbb);
        ccc.add(abc);
        for (int i = 0; i < ccc.size(); i++) {
            str.add(ccc.get(i));
        }
        System.out.println(str);
    }

    @Test
    public void test74(){
        Vector<String> vector = new Vector<>(1, 10);
        vector.add("abc");
        vector.add("bcd");
        vector.add("cde");
        vector.add("def");
        vector.add("efg");

        for (int i = 0; i < 5; i++) {
            System.out.println(vector.get(i));
        }

        int i = vector.indexOf(3);
        System.out.println(i);
        System.out.println(vector.lastIndexOf("def"));
        System.out.println(vector.indexOf("bcd"));

    }

    @Test
    public void test75(){
        List<Map> list = new ArrayList<>();
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("a", 3);
        map1.put("b", 20);
        map1.put("c", 100);
        list.add(map1);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("a", 1);
        map2.put("b", 20);
        map2.put("c", 300);
        list.add(map2);

        System.out.println(list);

        ConcurrentSkipListMap<Object, Object> sortMap = new ConcurrentSkipListMap<>();

        System.out.println(list.stream().map(obj -> obj.get("a")).toList());

        System.out.println("================");
        list.forEach(obj -> sortMap.put(obj.get("a"), obj));
        System.out.println(sortMap.descendingMap());



    }



}
