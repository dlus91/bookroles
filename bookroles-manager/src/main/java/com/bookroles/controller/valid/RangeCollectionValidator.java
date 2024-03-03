package com.bookroles.controller.valid;

import com.bookroles.tool.SecurityUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: dlus91
 * @Date: 2023/10/7 15:27
 */
public class RangeCollectionValidator implements ConstraintValidator<RangeCollection,Object> {

    int minSize;
    int maxSize;

    @Override
    public void initialize(RangeCollection constraintAnnotation) {
        minSize = constraintAnnotation.min();
        maxSize = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        if(value instanceof Integer integerValue){
            return validData(integerValue);
        }else if(value instanceof Collection collectionValue){
            return collectionValue.stream().allMatch(integerValue -> validData(Integer.parseInt(String.valueOf(integerValue))));
        }

        return false;
    }

    private boolean validData(Integer integerValue){
        return (integerValue >= minSize && integerValue <= maxSize);
    }

    public static void main(String[] args) {
        Set<Integer> sets = new CopyOnWriteArraySet<>();
        sets.add(123);
        sets.add(234);
        sets.add(345);
        sets.add(456);
        System.out.println("set:" + sets);
        print(sets);

        List lists = new ArrayList();
        lists.add(234);
        lists.add(32345);
        lists.add(435);
        System.out.println("list:" + lists);
        print(lists);
    }

    public static void print(Object obj){
        if(obj instanceof Collection<?> collectionValue){
            boolean b = collectionValue.stream().allMatch(integerValue -> {
                System.out.println("integerValue:" + integerValue);
                int i = Integer.parseInt(String.valueOf(integerValue));
                return i > 0 && i <= 1000;
            });
            System.out.println(b);
        }
    }

}
