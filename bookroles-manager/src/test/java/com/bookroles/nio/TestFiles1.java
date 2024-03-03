package com.bookroles.nio;

import com.bookroles.tool.LogEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: dlus91
 * @Date: 2023/10/17 15:33
 */
public class TestFiles1 {

    static int charNum = 0;

    long beginTime;

    @Before
    public void init(){
        beginTime = System.currentTimeMillis();
    }

    @After
    public void destory(){
        System.out.printf("耗时:%dms\r\n",System.currentTimeMillis() - beginTime);
    }


    //单线程 实现断点续传
    @Test
    public void test1() throws IOException {
        new TestFiles1().sameBufferLoop(0);
        //模拟断点续传
        System.out.println("===模拟断点续传1===");
        new TestFiles1().sameBufferLoop(charNum);
        System.out.println("===模拟断点续传2===");
        new TestFiles1().sameBufferLoop(charNum);
        System.out.println("===模拟断点续传3===");
        new TestFiles1().sameBufferLoop(charNum);
        System.out.println("===模拟断点续传4===");
        new TestFiles1().sameBufferLoop(charNum);
        System.out.println("===模拟断点续传5===");
        new TestFiles1().sameBufferLoop(charNum);
    }

    //多线程 下载数据
    @Test
    public void test2() throws Exception {
        //获取临时文件夹 并验证文件夹格式 和 删除多余的文件夹 ps：删除多余文件夹可能是多余的 后续在关注
        String fileName = "bookroles_access.2023-10-11";
        Path tempDirectory = getTempDirectory(fileName);
        System.out.println(tempDirectory);

        //拆分buffer到临时文件夹 这里同个文件一起拆分的时候要加锁

        //先获取到目标文件 并映射成buffer
        String clientLog = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\" + fileName + ".txt";
        Path path = Path.of(clientLog);
        BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

        int threadAndFileCount = 4;

        createTempFile(path, tempDirectory, threadAndFileCount);

        //假设3个线程 一个线程对应一个拆分文件
//        ExecutorService executorService = Executors.newFixedThreadPool(threadAndFileCount);


//        int startNum = 0;
//        int pageSize = 2;
//        for (int i = 0; i < 3; i++) {
//            startNum = i * pageSize;
//            Future<List> submit = executorService.submit(new TestRun(startNum,pageSize));
//
//        }

    }

    public boolean createTempFile(Path sourcePath, Path targetTempDirectory, int count){
        try {
            int realCount = (int) Files.walk(targetTempDirectory)
                    .filter(Files::isRegularFile)
                    .filter(file -> Pattern.matches(LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempFileRegex(), file.getFileName().toString()))
                    .count();

            //临时文件一斤存在 则不拆分
            if(realCount == count){
                System.out.println("临时文件已经创建");
                return true;
            }else if(realCount > 0){
                System.out.println("临时文件有缺失或不符合规则，则删除重新拆分");
                Files.walk(targetTempDirectory)
                        .filter(Files::isRegularFile)
                        .forEach(file -> {
                            try {
                                if(Pattern.matches(LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempFileRegex(), file.getFileName().toString())){
                                    Files.delete(file);
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
//            operationBufferByChar(sourcePath, targetTempDirectory, count);
            operationBufferByByte(sourcePath, targetTempDirectory, count);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public synchronized void operationBufferByChar(Path sourcePath, Path targetTempDirectory, int count) throws IOException {
        System.out.println("按char大小，开始拆分");
        BufferedReader bufferedReader = Files.newBufferedReader(sourcePath, StandardCharsets.UTF_8);
        //文件大小
        long fileSize = Files.size(sourcePath);
        //所有bufferReader buffer不能拆分，但可以拆分文件，通过临时文件夹将文件一分为几
        //然后 buffer拆分为较小的文件，给读数据buffer的请求使用
        for (int i = 0; i < count; i++) {
            int singleSize = (int) fileSize / count;
            int fillSize = (int) fileSize % count;
            //拆分多余的数量给第一个？或最后一个？ 暂定第一个
            //下载或者显示图片/音乐/视频等业务用字节char来拆分，但可以识别的数据可以用字符byte来处理
            char[] chars;
            if(i == 0){
                int pageSize = singleSize + fillSize;
                chars = new char[pageSize];
                bufferedReader.read(chars, 0, pageSize);
            }else {
                chars = new char[singleSize];
                bufferedReader.read(chars, 0, singleSize);
            }
            String prefix = LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempPrefix() + (i+1) + "_";
            //创建临时文件
            Path tempFile = Files.createTempFile(targetTempDirectory, prefix, LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempSuffix());
            BufferedWriter bufferedWriter = Files.newBufferedWriter(tempFile, Charset.forName("UTF-8"));
            bufferedWriter.write(chars);
            bufferedWriter.close();
        }
        bufferedReader.close();
        System.out.println("拆分成功");
    }

    public synchronized void operationBufferByByte(Path sourcePath, Path targetTempDirectory, int count) throws IOException {
        System.out.println("按byte大小，开始拆分");
        BufferedReader bufferedReader = Files.newBufferedReader(sourcePath, StandardCharsets.UTF_8);
        //文件行数
        long fileRows = Files.lines(sourcePath).count();
        //所有bufferReader buffer不能拆分，但可以拆分文件，通过临时文件夹将文件一分为几
        //然后 buffer拆分为较小的文件，给读数据buffer的请求使用
        for (int i = 0; i < count; i++) {
            int singleRow = (int) fileRows / count;
            int fillRow = (int) fileRows % count;
            int pageRow = 0;
            //拆分多余的数量给第一个？或最后一个？ 暂定第一个
            //下载或者显示图片/音乐/视频等业务用字节char来拆分，但可以识别的数据可以用字符byte来处理
            if(i == 0){
                pageRow = singleRow + fillRow;
            }else {
                pageRow = singleRow;
            }
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < pageRow; j++) {
                String line = bufferedReader.readLine();
                sb.append(line).append("\r\n");
            }
            String prefix = LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempPrefix() + (i+1) + "_";
            //创建临时文件
            Path tempFile = Files.createTempFile(targetTempDirectory, prefix, LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempSuffix());
            BufferedWriter bufferedWriter = Files.newBufferedWriter(tempFile, Charset.forName("UTF-8"));
            bufferedWriter.write(sb.toString());
            bufferedWriter.close();
        }

        bufferedReader.close();
        System.out.println("拆分成功");
    }

    public Path getTempDirectory(String fileName){
        String tempDic = LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempFileDirectoryPath();
        Path tempDirPath = Path.of(tempDic);
        if(!Files.exists(tempDirPath)){
            try {
                Files.createDirectory(tempDirPath);
            } catch (IOException e) {
                System.out.printf("错误 创建 %s 临时文件夹失败\r\n", tempDic);
                throw new RuntimeException(e);
            }
        }
        //检查临时文件夹里是否存在
        List<Path> pathList = null;
        try {
            pathList = Files.walk(tempDirPath)
                    .filter(file -> Pattern.matches(LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempFileDirectoryRegex(), file.getFileName().toString())).toList();
        } catch (IOException e) {
            System.out.printf("错误 检查 %s 临时文件夹异常，请手动检查\r\n", tempDirPath);
            throw new RuntimeException(e);
        }
        Path tempDirectory = null;
        if(pathList.isEmpty()){//不存在
            //拆分临时文件 每次创建一次都要保存最后修改日期到数据库
            try {
                String tempName = fileName + "_";
                tempDirectory = Files.createTempDirectory(tempDirPath, tempName);
            } catch (IOException e) {
                System.out.printf("错误 创建 %s 临时文件夹失败\r\n", tempDirPath);
                throw new RuntimeException(e);
            }
            System.out.printf("创建 %s 临时文件夹成功\r\n", tempDirectory.getFileName());
        }else {
            Path path = pathList.stream().max((l1,l2) -> {
                try {
                    int i = Files.getLastModifiedTime(l1).compareTo(Files.getLastModifiedTime(l2));
                    if(i > 0){
                        Files.delete(l2);
                    }else if(i == 0){
                        Files.delete(l2);
                    }else{
                        Files.delete(l1);
                    }
                    return i;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).get();
            tempDirectory = path;
        }
        return tempDirectory;
    }

    //创建临时文件夹
    @Test
    public void test31() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            String tempDic = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\temp\\";
            String fileName = "bookroles_access.2023-10-11";
            Path tempDicPath = Path.of(tempDic);
            try {
                String tempName = fileName + "_";
                Files.createTempDirectory(tempDicPath, tempName);
            } catch (IOException e) {
                System.out.printf("错误 创建 %s 临时文件夹失败\r\n", tempDicPath);
                throw new RuntimeException(e);
            }
            System.out.printf("创建 %d 个临时文件夹\r\n", i+1);
            Thread.sleep(200);
        }
    }

    @Test
    public void test32(){
//        String tempDic = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\temp\\";
//        int a = 102;
//        int count = 5;
//        System.out.println(a%count);
//        System.out.println(a/count);
//        System.out.println(Path.of(tempDic).getFileSystem());

        String aaa = "66_123213";
        String tempFileRegex = "([1-9]|\\d{2})_(?:[0-9]+)";
        String tempFileRegex1 = LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempFileRegex();
        System.out.println(tempFileRegex1);
        System.out.println(aaa.matches(tempFileRegex));
        System.out.println(aaa.matches(tempFileRegex1));

    }

    @Test
    public void test33() throws IOException {
        String tempDirectory = "H:\\\\programmer\\\\Interviewspace\\\\bookroles\\\\bookroles-manager\\\\log\\\\client\\\\temp\\\\bookroles_access.2023-10-11_6910194008514843244\\\\";
        Path path = Path.of(tempDirectory);
        String tempFileRegex = LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempFileRegex();
        int count = (int) Files.walk(path)
                .filter(Files::isRegularFile)
                .filter(file -> Pattern.matches(tempFileRegex, file.getFileName().toString()))
                .count();
        System.out.println(count);


        Files.walk(path)
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        if(Pattern.matches(LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempFileRegex(), file.getFileName().toString())){
                            Files.delete(file);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        System.out.println(LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempFileRegex());
        System.out.println(path.getFileName().toString());
    }

    @Test
    public void test34() throws IOException {
        String aaa = "bookroles_access.1_14876282162505872699.tmp";
        String tempFileRegex = LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempFileRegex();
        System.out.println(tempFileRegex);
        System.out.println(aaa.matches(tempFileRegex));

        //效率较低
        Pattern compile = Pattern.compile(tempFileRegex);
        Matcher matcher = compile.matcher(aaa);
        System.out.println(matcher.find());
        System.out.println(matcher.group(0));
        System.out.println(matcher.group(1));

        //效率高
        String bbb = aaa.substring(LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempPrefix().length());
        int ccc = bbb.indexOf("_");
        String ddd = bbb.substring(0, ccc);
        System.out.println(ddd);

        //排序
        String tempDirectory = "H:\\\\programmer\\\\Interviewspace\\\\bookroles\\\\bookroles-manager\\\\log\\\\client\\\\temp\\\\bookroles_access.2023-10-11_7266759285268684883\\\\";
        Path path = Path.of(tempDirectory);
        List list = Files.walk(path)
                .filter(Files::isRegularFile)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(list);
    }


    @Test
    public void test3() throws IOException {
        String aaa = "bookroles_access.2023-10-11_559119580456582047";
        boolean matches = aaa.matches("^bookroles_access.(\\d{4}-\\d{2}-\\d{2})_(?:[0-9]+)");
        System.out.println(matches);

        String tempDic = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\temp\\";
        Path tempDicPath = Path.of(tempDic);
        List<Path> pathList = Files.walk(tempDicPath)
                .filter(file -> Pattern.matches(LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempFileDirectoryRegex(), file.getFileName().toString()))
                .toList();
        System.out.println(pathList.size());
        System.out.println(LogEnum.BUSSINESS_TOMCAT_ACCESS.getTempFileDirectoryRegex());
    }

    public void sameBufferLoop(int skipNum) throws IOException {
        int pageSize = 2;
        String clientLog = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\bookroles_access.2023-10-11.txt";
        BufferedReader bufferedReader = Files.newBufferedReader(Path.of(clientLog), StandardCharsets.UTF_8);
        if (skipNum > 0) {
            bufferedReader.skip(skipNum);
        }

        for (int i = 0; i < 2; i++) {
            String log = this.getLogString(bufferedReader,pageSize);
            System.out.printf(log);
        }
        bufferedReader.close();
    }


    private String getLogString(BufferedReader bufferedReader, int pageSize){
        StringBuilder logContent = new StringBuilder();
        try{
            String line = null;
            int num = 0;
            while (num < pageSize) {
                line = bufferedReader.readLine();
                //加2的原因是跨行符 \n或10
                charNum += line.length()+2;
                if(line != null){
                    logContent.append(line).append("\r\n");
                    num++;
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logContent.toString();
    }


    public class TestRun implements Callable<List> {

        int startNum;
        int pageSize;
        int skipNum;

        public TestRun(int startNum, int pageSize, int skipNum) {
            this.startNum = startNum;
            this.pageSize = pageSize;
            this.skipNum = skipNum;
        }

        @Override
        public List call() throws Exception {
            return null;
        }
    }


}
