package com.bookroles.tool.dLogNio;

import com.bookroles.tool.LogEnum;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.util.regex.Pattern.matches;

/**
 * @Author: dlus91
 * @Date: 2023/10/14 15:19
 */
public class Test1 {

    @Test
    public void test1() throws IOException {
        LinkOption[] linkOptions = new LinkOption[0];
        System.out.println(linkOptions);

        LinkOption[] linkOptions1 = {LinkOption.NOFOLLOW_LINKS};
        System.out.println(linkOptions1);

        String destPath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\bookroles_access.2023-10-12.txt";
        Path path = Paths.get(destPath);
        System.out.println(path.toRealPath(LinkOption.NOFOLLOW_LINKS).toString());
    }

    @Test
    public void test2() throws IOException {
        String destPath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client\\aaa.txt";
        Path path = Paths.get(destPath);
        //size,lastModifiedTime,lastAccessTime
        String attribute = Files.getAttribute(path, "size").toString();
        System.out.println(attribute);

//        System.out.println(Files.readAttributes(path, "size,lastModifiedTime,lastAccessTime", LinkOption.NOFOLLOW_LINKS));

//        System.out.println(Files.readAttributes(path, "size,lastModifiedTime,lastAccessTime"));

        //Window操作系统不支持
//        System.out.println(Files.getPosixFilePermissions(path, LinkOption.NOFOLLOW_LINKS));

//        System.out.println(Files.readAttributes(path, PosixFileAttributes.class).permissions());

        System.out.println(Files.getOwner(path));

        Path path2 = Paths.get("C:\\Users\\Public\\Desktop\\Audacity");
        System.out.println(Files.isSymbolicLink(path2));



    }

    //拷贝文件夹
    @Test
    public void test3() throws IOException {
        Path source = Paths.get("H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\client");
        Path target = Paths.get("H:\\programmer\\Interviewspace\\bookroles\\bookroles-manager\\log\\aa");
        Path path = Files.walkFileTree(source, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                            throws IOException {
                        Path targetdir = target.resolve(source.relativize(dir));
                        try {
                            Files.copy(dir, targetdir);
                        } catch (FileAlreadyExistsException e) {
                            if (!Files.isDirectory(targetdir))
                                throw e;
                        }
                        return CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException {
                        Files.copy(file, target.resolve(source.relativize(file)));
                        return CONTINUE;
                    }
                });
        System.out.println(path);

    }

    @Test
    public void test4(){
        String aaa = "2023-10-11 13:34:03.611";

        String bbb = "bookroles_access.2023-10-11.txt";

        Pattern compile = Pattern.compile(LogEnum.BUSSINESS_TOMCAT_ACCESS.getValidFileNameRegex());
        Matcher matcher = compile.matcher(bbb);
        if (matcher.find()){
            System.out.println(matcher.group(1));
        }
//        System.out.println(aaa.substring(0, aaa.length()- 4));
    }

    @Test
    public void test5(){
        String aaa = "2023-10-11 13:34:03.611";

        String ccc = aaa.substring(0, 10);
        System.out.println(ccc);
        String vvv = LogEnum.BUSSINESS_TOMCAT_ACCESS.getPrefix()+ccc+LogEnum.BUSSINESS_TOMCAT_ACCESS.getSuffix();
        System.out.println(vvv);

    }

    @Test
    public void test6(){
        HashMap<String, String> map = new HashMap<>();
        map.compute("a", (k,v) -> (v == null) ? "bbb" : "ccc");
        System.out.println(map);

        map.merge("a", "ccc",String::concat);
        System.out.println(map);
    }

    @Test
    public void test7(){

    }




}
