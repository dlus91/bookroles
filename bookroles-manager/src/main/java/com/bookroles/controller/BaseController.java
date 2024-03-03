package com.bookroles.controller;

import com.bookroles.controller.view.ViewError;
import com.bookroles.controller.vo.R;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: dlus91
 * @Date: 2023/10/1 14:52
 */
public abstract class BaseController {

    public ModelAndView paramsErrorHandler(ModelAndView modelAndView, BindingResult bindingResult){
        paramsErrorHandlerOptional(bindingResult).ifPresent(r -> modelAndView.addObject(ViewError.PARAMS.getName(), r));
        return modelAndView;
    }

    public Optional<R> paramsErrorHandlerOptional(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String,String> map = new HashMap<>();
            bindingResult.getFieldErrors().forEach((item) -> {
                map.put(item.getField(), item.getDefaultMessage());
            });
            return Optional.of(new R(HttpStatus.NOT_ACCEPTABLE.value(), "参数不合理").setData(map));
        }
        return Optional.empty();
    }

}
