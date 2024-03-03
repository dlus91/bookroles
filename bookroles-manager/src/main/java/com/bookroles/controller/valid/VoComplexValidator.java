package com.bookroles.controller.valid;

import com.bookroles.controller.vo.BookDownloadUrlVo;
import com.bookroles.controller.vo.BookIntroVo;
import com.bookroles.controller.vo.BookVo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

/**
 * @Author: dlus91
 * @Date: 2023/9/29 23:34
 */
public class VoComplexValidator implements ConstraintValidator<VoComplexValid,Object> {

    Class<?> clazz;
    String staticMethod;

    @Override
    public void initialize(VoComplexValid constraintAnnotation) {
        this.clazz = constraintAnnotation.clazz();
        this.staticMethod = constraintAnnotation.staticMethod();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        boolean result = false;
//        switch (this.clazz.getName()){
//            case "com.bookroles.controller.vo.BookVo" -> result = validateBookVoField(this.staticMethod,value);
//            case "com.bookroles.controller.vo.BookDownloadUrlVo" -> result = validateBookDownloadUrlVoField(this.staticMethod,value);
//            case "com.bookroles.controller.vo.BookIntroVo" ->  result = validateBookContentIntroVoField(this.staticMethod,value);
//        }

        return result;
    }

//    private boolean validateBookVoField(String method,Object value){
//        boolean result = false;
//        switch (method){
//            case "validatePublishMonth" ->
//                    result = BookVo.validatePublishMonth(String.valueOf(value));
//        }
//        return result;
//    }

//    private boolean validateBookContentIntroVoField(String method,Object value){
//        boolean result = false;
//        if(method.equals("validateContentIntro")){
//            result = BookIntroVo.validateContentIntro(String.valueOf(value));
//        }
//        return result;
//    }
//
//    private boolean validateBookDownloadUrlVoField(String method,Object value){
//        boolean result = false;
//        if(method.equals("validateDownloadUrl")){
//            result = BookDownloadUrlVo.validateDownloadUrl(String.valueOf(value));
//        }
//        return result;
//    }

    private boolean newErrorMsg(ConstraintValidatorContext context, String errMsg) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errMsg)
                .addConstraintViolation();
        return false;
    }

    public static void main(String[] args) {
        System.out.println(BookDownloadUrlVo.class.getName());
        System.out.println(BookIntroVo.class.getName());
    }

}
