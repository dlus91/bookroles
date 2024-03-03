package com.bookroles.nio;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.*;
import java.util.Locale;

/**
 * @Author: dlus91
 * @Date: 2023/10/21 17:56
 */
public class TestText1 {

    @Test
    public void test1() throws IOException {
//        String path = "H:\\dbook\\男人和女人的16个心理差别.txt";
//        String path = "H:\\dbook\\美好的性，是阳光下的火炬.txt";
        String path = "H:\\dbook\\男人和女人2.txt";
        Path book = Path.of(path);
        System.out.println(Files.lines(book).count());
//        List<String> list = Files.lines(book).toList();
//        for (String s : list) {
//            System.out.println(s);
//        }
    }

    @Test
    public void test2() throws IOException {
//        String sourcePath = "H:\\dbook\\男人和女人2.txt";
        String sourcePath = "H:\\dbook\\1催眠术的奥秘.格桑泽仁+.txt";
//        String sourcePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\abc.txt";
        Path book = Path.of(sourcePath);
        BufferedReader bufferedReader = Files.newBufferedReader(book, StandardCharsets.UTF_8);
        String line;
        while((line = bufferedReader.readLine()) != null){
            System.out.println(line);
        }
    }

    @Test
    public void test3(){
        String text = "男人和女人的16个心理差别[z]\n" +
                "1、男性在年轻时会交很多朋友，但女性过了中年以后才会有更多的朋友。 \n" +
                "2、约有25%的男性，在第一次约会时就爱上对方，但女性到了第四次约会，才有15%爱上对方。 \n" +
                "3、女性作决定的速度比男性快。 \n" +
                "4、入学前到中学期的男孩子比女孩子更爱支配别人。成年后婚姻生活越长久，妻子就越成为被支配者。 \n" +
                "5、男性时常害怕爱侣会被杀或自杀，而女性则常常害怕爱侣会遭受意外的事故或年老死去。 \n" +
                "6、男人喜欢冲锋式的工作，间隔休息，而女人则喜欢以同一个节奏工作。 ";
        int i = nextWordStartAfter(10, text);
        System.out.println(i);
    }

    @Test
    public void test31(){
        String stringToExamine = "男人和女人的16个心理差别[z]\n" +
                "1、男性在年轻时会交很多朋友，但女性过了中年以后才会有更多的朋友。 \n" +
                "2、约有25%的男性，在第一次约会时就爱上对方，但女性到了第四次约会，才有15%爱上对方。 \n" +
                "3、女性作决定的速度比男性快。";
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(stringToExamine);

//        printEachForward(boundary, stringToExamine);
//        printEachBackward(boundary, stringToExamine);

//        printFirst(boundary, stringToExamine);
//        printLast(boundary, stringToExamine);
        printAt(boundary, 10, stringToExamine);

        int nextIndex = nextWordStartAfter(15, stringToExamine);
        printAt(boundary, nextIndex, stringToExamine);


    }


    //按顺序打印每个元素
    public static void printEachForward(BreakIterator boundary, String source){
        int start = boundary.first();
        for(int end = boundary.first(); end != BreakIterator.DONE; start = end, end = boundary.next()){
            System.out.println(source.substring(start, end));
        }
    }

    //以相反的顺序打印每个元素
    public static void printEachBackward(BreakIterator boundary, String source){
        int end = boundary.last();
        for(int start = boundary.previous(); start != BreakIterator.DONE; end = start, start = boundary.previous()){
            System.out.println(source.substring(start, end));
        }
    }

    //打印第一个元素
    public static void printFirst(BreakIterator boundary, String source){
        int start = boundary.first();
        int end = boundary.next();
        System.out.println(source.substring(start, end));
    }

    //打印最后一个元素
    public static void printLast(BreakIterator boundary, String source){
        int end = boundary.last();
        int start = boundary.previous();
        System.out.println(source.substring(start, end));
    }

    //在指定位置打印元素
    public static void printAt(BreakIterator boundary, int pos, String source){
        int end = boundary.following(pos);
        int start = boundary.previous();
        System.out.println(source.substring(start, end));
    }

    //找到下一个字
    public static int nextWordStartAfter(int pos, String text){
        BreakIterator wb = BreakIterator.getWordInstance();
        wb.setText(text);
        int last = wb.following(pos);
        int current = wb.next();
        while(current != BreakIterator.DONE){
            for (int p = last; p < current; p++) {
                if(Character.isLetter(text.codePointAt(p))){
                    return last;
                }
            }
            last = current;
            current = wb.next();
        }
        return BreakIterator.DONE;
    }


    //这是一个显示格式和解析的简单示例
    @Test
    public void test4(){
        double[] limits = {1,2,3,4,5,6,7};
        String[] dayOfWeekNames = {"Mon","Tue","Wed","Thur","Fri","Sat","Sun"};
        ChoiceFormat form = new ChoiceFormat(limits, dayOfWeekNames);
        ParsePosition status = new ParsePosition(0);
        for (double i = 1.0; i < 14.0; ++i) {
            status.setIndex(0);
            System.out.println(i + "->" + form.format(i) + "->" + form.parse(form.format(i), status));
        }
    }

    //这是一个更复杂的示例，具有模式格式
    @Test
    public void test5(){
        double[] filelimits = {0,1,2};
        String[] filepart = {"are no files", "is one file", "are {2} files"};
        ChoiceFormat fileform = new ChoiceFormat(filelimits, filepart);
        Format[] testFormats = {fileform, null, NumberFormat.getInstance()};
        MessageFormat pattform = new MessageFormat("There {0} on {1}");
        pattform.setFormats(testFormats);
        Object[] testArgs = {null, "ADisk", null};
        for (int i = 0; i < 6; ++i) {
            testArgs[0] = i;
            testArgs[2] = testArgs[0];
            System.out.println(pattform.format(testArgs));
        }
    }

    @Test
    public void test6(){
        ChoiceFormat fmt = new ChoiceFormat("-1#is negative| 0#is zero or fraction | 1#is one | 1.0<is 1+ | 2#is two | 2<is more than 2.");
        System.out.println("Formatter Pattern : " + fmt.toPattern());
        System.out.println("Formatter with -INF : " + fmt.format(-1.0));
        System.out.println("Formatter with 0 : " + fmt.format(0));
        System.out.println("Formatter with 0.9 : " + fmt.format(0.9));
        System.out.println("Formatter with 1.0 : " + fmt.format(1));
        System.out.println("Formatter with 1.5 : " + fmt.format(1.5));
        System.out.println("Formatter with 2 : " + fmt.format(2));
        System.out.println("Formatter with 2.1 : " + fmt.format(2.1));
        System.out.println("Formatter with NaN : " + fmt.format(Double.NaN));
        System.out.println("Formatter with +INF : " + fmt.format(Double.POSITIVE_INFINITY));

    }

    @Test
    public void test7(){
        String testString = "abcdefg hijk";
        Collator col = Collator.getInstance();
        if(col instanceof RuleBasedCollator){
            RuleBasedCollator ruleBasedCollator = (RuleBasedCollator) col;
            CollationElementIterator collationElementIterator = ruleBasedCollator.getCollationElementIterator(testString);
            int next = collationElementIterator.next();
            int i = CollationElementIterator.primaryOrder(next);
            short i1 = CollationElementIterator.secondaryOrder(i);
            System.out.println((char) i);
            System.out.printf("%d %d \n",i, i1);
        }
    }

    @Test
    public void test8(){
        Collator collator = Collator.getInstance();
        CollationKey[] keys = new CollationKey[3];
        keys[0] = collator.getCollationKey("Tom");
        keys[1] = collator.getCollationKey("Dick");
        keys[2] = collator.getCollationKey("Harry");


        System.out.println(keys[0].getSourceString());
        System.out.println(keys[1].getSourceString());
        System.out.println(keys[2].getSourceString());
    }

    @Test
    public void test9(){
        Collator collator = Collator.getInstance();
        if(collator.compare("abc", "ABC") < 0){
            System.out.println("abc is less than ABC");
        }else{
            System.out.println("abc is greater than or equal to ABC");
        }
    }

    @Test
    public void test10(){
        Locale locale = new Locale(Locale.SIMPLIFIED_CHINESE.getLanguage());
        NumberFormat f = NumberFormat.getInstance(locale);
        if(f instanceof DecimalFormat){
            ((DecimalFormat) f).setDecimalSeparatorAlwaysShown(true);
        }
    }

    @Test
    public void test11(){
        Locale[] locales = {Locale.SIMPLIFIED_CHINESE, Locale.TRADITIONAL_CHINESE, Locale.US};
        double myNumber = -1234.56;
        NumberFormat form;
        for (int j = 0; j < 4; j++) {
            System.out.println("FORMAT");
            for (int i = 0; i < locales.length; i++) {
                if (locales[i].getCountry().length() == 0) {
                    continue;
                }
                System.out.print(locales[i].getDisplayName());
                switch (j) {
                    case 0 -> form = NumberFormat.getInstance(locales[i]);
                    case 1 -> form = NumberFormat.getIntegerInstance(locales[i]);
                    case 2 -> form = NumberFormat.getCurrencyInstance(locales[i]);
                    default -> form = NumberFormat.getPercentInstance(locales[i]);
                }
                if(form instanceof DecimalFormat){
                    System.out.print(": " + ((DecimalFormat) form).toPattern());
                }
                System.out.print("-> " + form.format(myNumber));
                try {
                    System.out.println("-> " + form.parse(form.format(myNumber)));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Test
    public void test12() throws ParseException {
        double myNumber = 1234.56;
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.CHINA);
        String pattern = ((DecimalFormat) currencyInstance).toPattern();
        System.out.println(pattern);
        System.out.println(currencyInstance.format(myNumber));
        System.out.println(currencyInstance.parse(currencyInstance.format(myNumber)));
    }

    @Test
    public void test13() throws ParseException {
        double number = 122.231231;
        NumberFormat numFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        System.out.println(numFormat.format(number));
    }

    @Test
    public void test14(){
        System.out.println(Locale.getDefault().getDisplayLanguage());
        System.out.println(Locale.getDefault().getDisplayCountry());
        System.out.println(Locale.getDefault().getDisplayName());
        System.out.println(Locale.getDefault().getDisplayScript());
        System.out.println(Locale.getDefault().getDisplayVariant());

        NumberFormat numberInstance = NumberFormat.getNumberInstance(Locale.forLanguageTag("th-TH-u-nu-thai"));
        System.out.println(numberInstance.getCurrency());

        System.out.println(Locale.getDefault().toLanguageTag());

        NumberFormat cNumber = NumberFormat.getNumberInstance(Locale.forLanguageTag("zh-CN"));
        System.out.println(cNumber.getCurrency());

    }

    @Test
    public void test15(){
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault(Locale.Category.FORMAT));
        System.out.println(decimalFormatSymbols.getZeroDigit());
        //币种
        System.out.println(decimalFormatSymbols.getCurrency());
        //0
        System.out.println(decimalFormatSymbols.getDigit());
        //千分位
        System.out.println(decimalFormatSymbols.getGroupingSeparator());

        //千分位 组 符号
//        decimalFormatSymbols.setGroupingSeparator('_');
        //千位 符号
        decimalFormatSymbols.setPerMill('/');
        //百位 符号
        decimalFormatSymbols.setPercent('=');
        //小数 符号
        decimalFormatSymbols.setDecimalSeparator(':');
        //零符号
        decimalFormatSymbols.setZeroDigit('0');
        //设置正负数 符号
        decimalFormatSymbols.setMinusSign('^');
        //设置货币符号
        decimalFormatSymbols.setCurrencySymbol("&");

        double currency = 2121231233123.291923;
        double currency0 = 0;
        double currency1 = -110.231;
        String pattern = "#,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, decimalFormatSymbols);
        decimalFormat.setGroupingSize(5);
//        decimalFormat.setGroupingUsed(false);
        System.out.println(decimalFormatSymbols.getDigit());
        System.out.println(decimalFormatSymbols.getPatternSeparator());
        System.out.println(decimalFormatSymbols.getInfinity());
        System.out.println(decimalFormatSymbols.getMinusSign());
        System.out.println(decimalFormatSymbols.getCurrencySymbol());
        System.out.println(decimalFormat.format(currency1));
    }




}
