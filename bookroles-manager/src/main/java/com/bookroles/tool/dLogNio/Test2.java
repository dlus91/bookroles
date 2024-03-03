package com.bookroles.tool.dLogNio;

import com.bookroles.tool.LogEnum;
import org.junit.Test;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2023/10/13 21:52
 */
public class Test2 {

    @Test
    public void test1() throws IOException {

//        String sourcePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client";
//        Path folder = Paths.get(sourcePath);
//        List<Path> list = Files.walk(folder).filter(Files::isRegularFile).filter(file -> {
//            try {
//                return Files.find(file, file.getNameCount(), (path, attr) -> path.getFileName().toString().matches(LogEnum.BUSSINESS_TOMCAT_ACCESS.getValidFileNameRegex())).toList().size() > 0;
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).toList();

        String fileDirectory = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\";
        Path pathAny = Files.walk(Paths.get(fileDirectory)).filter(Files::isRegularFile).findAny().get();
        System.out.println(pathAny.getNameCount());
        List<Path> list = Files.find(pathAny, pathAny.getNameCount(), (path, attr) -> path.getFileName().toString().matches(LogEnum.BUSSINESS_TOMCAT_ACCESS.getValidFileNameRegex())).toList();
        for (Path path : list) {

            System.out.println(path.getFileName().toString());
        }


    }

    @Test
    public void test11() throws IOException {
        String destPath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\aaa";
        Files.createDirectory(Path.of(destPath));
    }

    @Test
    public void test12() throws IOException, InterruptedException {
        String path = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\bbb";
        String path2 = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\aaa.2023-10-11.txt";

        File.createTempFile("test", ".txt", new File(path));
    }

    @Test
    public void test13(){
        for (LinkOption c : LinkOption.values())
            System.out.println(c);

        System.out.println(LinkOption.valueOf("123"));
    }

    //超过 2g 大小的文件传输
    public static void channelCopy(){
        String sourcePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\bookroles_access.2023-10-11.txt";
        String destPath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\aaa.txt";
//        channelCopy(sourcePath, destPath);


        FileChannel sourceChannel=null;
        FileChannel destChannel=null;
        try {
            sourceChannel = new FileInputStream(sourcePath).getChannel();
            destChannel = new FileOutputStream(destPath).getChannel();
            //所要拷贝的原文件大小
            long size = sourceChannel.size();
            System.out.println("start...");
            for (long left = size; left > 0;){
                //transferSize所拷贝过去的真实长度
                //size - left计算出下次要拷贝的位置
                long transferSize = sourceChannel.transferTo((size - left), left, destChannel);
                //还剩余多少
                left = left - transferSize;
            }
            System.out.println("end...");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (sourceChannel!=null){
                    sourceChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (destChannel!=null){
                    destChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test2(){
        try{
            String[] cmds = {"ipconfig","/all"};
            ProcessBuilder builder = new ProcessBuilder(cmds);
            //合并输出流和错误流
            builder.redirectErrorStream(true);
            //启动进程
            Process process = builder.start();
            //获得输出流
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream(),"GBK"));
            String line = null;
            while (null != (line = br.readLine())){
                System.out.println(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void test3() throws IOException {
        //操作环境变量
        ProcessBuilder pb = new ProcessBuilder("myCommand", "myArg1", "myArg2");
        Map<String, String> env = pb.environment();
        System.out.println(env);
//        env.put("VAR1", "myValue");
//        env.remove("OTHERVAR");
//        env.put("VAR2", env.get("VAR1") + "suffix");
//        pb.directory(new File("myDir"));
//        File log = new File("log");
//        pb.redirectErrorStream(true);
//        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
//        Process p = pb.start();
//        assert pb.redirectInput() == ProcessBuilder.Redirect.PIPE;
//        assert pb.redirectOutput().file() == log;
//        assert p.getInputStream().read() == -1;
    }

    @Test
    public void test4() throws IOException {
        String sourcePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\bookroles_access.2023-10-11.txt";
        String destPath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\aaa.txt";

        Files.copy(Path.of(sourcePath),Path.of(destPath));

    }

    @Test
    public void test5() throws IOException {
        String destPath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\aaa.txt";
        Path path = Paths.get(destPath);
        BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class);
        BasicFileAttributes basicfile = basicview.readAttributes();
        System.out.println("创建时间" + new Date(basicfile.creationTime().toMillis()));
        System.out.println("文件大小" + basicfile.size());
        //dos命令 设置权限
        DosFileAttributeView dos = Files.getFileAttributeView(path, DosFileAttributeView.class);
        dos.setHidden(true);
        dos.setReadOnly(true);
    }




}
