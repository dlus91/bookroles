package com.bookroles.nio;

import org.aspectj.apache.bcel.util.SyntheticRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @Author: dlus91
 * @Date: 2023/10/16 23:03
 */
public class TestReadRow2 {

    private static String filePath2 = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\bookroles_access.2023-10-13.txt";

    private static String fileDic = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\";

    private static String filePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\abc.txt";

    long beginTime = 0L;

    @Before
    public void init(){
        beginTime = System.currentTimeMillis();
    }

    @After
    public void destory(){
        System.out.println(System.currentTimeMillis() - beginTime + "ms");
    }


    //todo 连接池绑定分页
    @Test
    public void test1 () {
        long lineNumber = 0;
        String line = null;
        try (BufferedReader b = Files.newBufferedReader(Paths.get(filePath2), StandardCharsets.UTF_8)) {
            while ((line = b.readLine()) != null) {
                lineNumber++;
//                System.out.println(line);
            }
        } catch (Exception e) {

            System.err.println("Error in reviewBufferedReader : "+e.getMessage());
        } finally {

        }
        System.out.println("Total no. of lines: "+lineNumber);
    }

    @Test
    public void reviewFileChannelWithMappedByteBuffer () {
        long lineNumber = 0;
        try (RandomAccessFile raFile = new RandomAccessFile(filePath2, "r");
             FileChannel inChannel = raFile.getChannel();){
            MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            buffer.load();
            char c;
//            StringBuilder sb = new StringBuilder();
            for(int i = 0; i< buffer.limit(); i++) {
                c = (char) buffer.get();
//                sb.append(c);
                if ('\n' == c) {
                    lineNumber++;
//                    System.out.println(new String(sb.toString().getBytes(), StandardCharsets.UTF_8));
//                    sb.delete(0, sb.length());
                }
            }
            buffer.clear(); // do something with the data and clear/compact it.
        } catch (Exception e) {
            System.err.println("Error in reviewFileChannelWithMappedByteBuffer : "+e.getMessage());
        } finally {

        }
        System.out.println("Total no. of lines: "+lineNumber);
    }

    @Test
    public void test3() throws IOException {
        //效率较低
//        List<String> list = Files.lines(Path.of(filePath2)).toList();

        System.out.println(Files.lines(Path.of(filePath2)).count());
//        System.out.println(list);
    }

    @Test
    public void test4(){
        int num = 0;
        try {
//            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath2), StandardCharsets.UTF_8);
//            BufferedReader reader = new BufferedReader(fReader);
            BufferedReader reader
                    = new BufferedReader(new FileReader(filePath2));
            while (reader.readLine() != null) {
                num++;
            }
            System.out.println(num);
            reader.close();
//            fReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test5() throws IOException {
        FileStore fileStore = Files.getFileStore(Path.of(fileDic));
        System.out.printf("%d \r\n", fileStore.getBlockSize());
        System.out.printf("%s \r\n", fileStore.type());
        System.out.printf("%d \r\n", fileStore.getTotalSpace());
        System.out.printf("%d \r\n", fileStore.getUsableSpace());
        System.out.printf("%d \r\n", fileStore.getBlockSize());
        System.out.printf("%d \r\n", fileStore.getUnallocatedSpace());
        System.out.printf("%s \r\n", fileStore.name());
    }


}
