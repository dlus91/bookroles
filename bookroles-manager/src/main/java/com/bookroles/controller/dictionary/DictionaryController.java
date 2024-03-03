package com.bookroles.controller.dictionary;

import com.bookroles.controller.BaseController;
import com.bookroles.controller.valid.Group;
import com.bookroles.controller.vo.BookVo;
import com.bookroles.controller.vo.DictionaryVo;
import com.bookroles.controller.vo.R;
import com.bookroles.service.IBookService;
import com.bookroles.service.IDictionaryService;
import com.bookroles.service.model.Dictionary;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.Optional;

/**
 * @Author: dlus91
 * @Date: 2023/10/6 0:10
 */
@Controller
@RequestMapping("/system/dictionary/")
public class DictionaryController extends BaseController {

    @Autowired
    private IDictionaryService dictionaryServiceImpl;

    @GetMapping("query")
    @ResponseBody
    @SessionScope
    public R query(@Validated(value = Group.Select.class) DictionaryVo dictionaryVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() -> {
            List list = null;
            if(dictionaryVo.getType() == null || StringUtils.isEmpty(dictionaryVo.getType())){
                list = dictionaryServiceImpl.search();
            }else {
                list = dictionaryServiceImpl.search(dictionaryVo.transformModel());
            }
            return (list != null && !list.isEmpty()) ? new R().ok().setData(list) : new R().fail(dictionaryVo.getId());
        });
        System.out.println(rData);
        return rData;
    }

    @PutMapping("add")
    @ResponseBody
    @SessionScope
    public R add(@Validated(value = Group.Add.class) DictionaryVo dictionaryVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() -> {
            int count = dictionaryServiceImpl.addName(dictionaryVo.transformModel());
            return count > 0 ? new R().ok().setData(count) : new R().saveError(dictionaryVo);
        });
        System.out.println(rData);
        return rData;
    }

    @PostMapping("modify")
    @ResponseBody
    @SessionScope
    public R modify(@Validated(value = Group.Update.class) DictionaryVo dictionaryVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() -> {
            int count = dictionaryServiceImpl.modifyName(dictionaryVo.transformModel());
            return count > 0 ? new R().ok().setData(count) : new R().modifyError(dictionaryVo.getId());
        });
        System.out.println(rData);
        return rData;
    }

}
