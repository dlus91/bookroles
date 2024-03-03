package com.bookroles.controller.valid;

import com.bookroles.tool.SecurityUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: dlus91
 * @Date: 2023/10/7 13:45
 */
public class VoJudgeSQLInjectValidator implements ConstraintValidator<VoJudgeSQLInjectValid,Object> {

    int minSize;
    int maxSize;

    @Override
    public void initialize(VoJudgeSQLInjectValid constraintAnnotation) {
        minSize = constraintAnnotation.min();
        maxSize = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        if(value instanceof String strValue){
            return validData(strValue);
        }else if(value instanceof Collection collectionValue){
            return collectionValue.stream().allMatch(strValue -> validData(String.valueOf(strValue)));
        }
        return false;
    }

    private boolean validData(String strValue){
        return (strValue.length() >= minSize && strValue.length() <= maxSize) && !SecurityUtil.judgeSQLInject(strValue);
    }


    public static void main(String[] args) {
        Set<String> sets = new CopyOnWriteArraySet<>();
        sets.add("abcd");
        sets.add("cbdfaefaf");
        sets.add("defafaff");
        sets.add("abcafaefa");
        System.out.println("set:" + sets);
        print(sets);

        List lists = new ArrayList();
        lists.add("qwea");
        lists.add("ewqdwadawdawdawd");
        lists.add("asdfe");
        System.out.println("list:" + lists);
        print(lists);
    }

    public static void print(Object obj){
        if(obj instanceof Collection<?> collectionValue){
            boolean b = collectionValue.stream().allMatch(strValue -> {
                System.out.println("strValue:" + strValue);
                String str = String.valueOf(strValue);
                return str.length() > 0 && str.length() <= 10;
            });
            System.out.println(b);
        }
    }


}
