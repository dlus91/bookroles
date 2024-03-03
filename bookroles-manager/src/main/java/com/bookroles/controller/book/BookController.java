package com.bookroles.controller.book;

import com.bookroles.controller.BaseController;
import com.bookroles.controller.valid.Group;
import com.bookroles.controller.view.OutputView;
import com.bookroles.controller.view.ViewError;
import com.bookroles.controller.vo.*;
import com.bookroles.service.IBookService;
import com.bookroles.service.model.BookDownloadUrl;
import com.bookroles.service.model.BookIntro;
import com.bookroles.service.model.BookPublishHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;



/**
 * @Author: dlus91
 * @Date: 2023/9/25 16:31
 */

@Controller
@RequestMapping("/system/book/")
public class BookController extends BaseController{

    @Autowired
    private IBookService bookServiceImpl;

    @GetMapping("queryPage")
    @SessionScope
    public ModelAndView queryPage(@Validated(value = Group.Select.class) BookVo bookVo, BindingResult bindingResult) {
        ModelAndView modelAndView = paramsErrorHandler(new ModelAndView(OutputView.BookPage.getName()), bindingResult);
        if(modelAndView.getModelMap().get(ViewError.PARAMS.getName()) == null){
            List<BookVo> bookVos = bookServiceImpl.searchPage(bookVo.transformModel(Group.Select.class));
            bookVos.forEach(vo -> vo.transformViewObject());
            modelAndView.addObject("books", (bookVos != null && !bookVos.isEmpty()) ? new R().ok().setData(bookVos) : new R().fail(bookVo));
        }
        System.out.println(modelAndView.getModelMap().get("books"));
        System.out.println(modelAndView.getModelMap().get(ViewError.PARAMS.getName()));
        return modelAndView;
    }

    @GetMapping("detail")
    @SessionScope
    public ModelAndView getDetail(@Validated(value = Group.SelectOne.class) BookVo bookVo, BindingResult bindingResult){
        ModelAndView modelAndView = paramsErrorHandler(new ModelAndView(OutputView.BookDetail.getName()), bindingResult);
        if(modelAndView.getModelMap().get(ViewError.PARAMS.getName()) == null){
            BookVo resultBookVo = bookServiceImpl.searchDetail(bookVo.getId());
            if(resultBookVo != null){
                BookDownloadUrl bookDownloadUrl = new BookDownloadUrl();
                bookDownloadUrl.setBookId(bookVo.getId());
                bookDownloadUrl.setCanUse(0);
                List<BookDownloadUrlVo> bookDownloadUrlVos = bookServiceImpl.searchBookDownloadUrl(bookDownloadUrl);
                BookIntro bookIntro = new BookIntro();
                bookIntro.setBookId(bookVo.getId());
                bookIntro.setCanUse(0);
                List<BookIntroVo> bookIntroVos = bookServiceImpl.searchBookIntro(bookIntro);
                modelAndView.addObject("book", new R().ok().setData(resultBookVo));
                modelAndView.addObject("bookDownloadUrls", new R().ok().setData(bookDownloadUrlVos));
                modelAndView.addObject("bookIntros", new R().ok().setData(bookIntroVos));
            }else{
                modelAndView.addObject("book", new R().fail(bookVo.getId()));
            }
        }
        System.out.println(modelAndView.getModelMap().get("book"));
        System.out.println(modelAndView.getModelMap().get("bookDownloadUrls"));
        System.out.println(modelAndView.getModelMap().get("bookIntros"));
        System.out.println(modelAndView.getModelMap().get(ViewError.PARAMS.getName()));
        return modelAndView;
    }

    @PutMapping("addBook")
    @ResponseBody
    @SessionScope
    public R addBook(@Validated(value = Group.Add.class) BookVo bookVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() -> {
            int result = bookServiceImpl.addBook(bookVo.transformModel(Group.Add.class));
            return result > 0 ? new R().ok().setData(result) : new R().saveError(bookVo);
        });
        System.out.println(rData);
        return rData;
    }

    @PostMapping("modifyBook")
    @ResponseBody
    @SessionScope
    public R modifyBook(@Validated(value = Group.Update.class) BookVo bookVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(()->{
            int result = bookServiceImpl.modifyBookById(bookVo.transformModel(Group.Update.class));
            return result > 0 ? new R().ok().setData(result) : new R().modifyError(bookVo.getId());
        });
        System.out.println(rData);
        return rData;
    }

    @PostMapping("modifyBookOpen")
    @ResponseBody
    @SessionScope
    public R modifyBookOpen(@Validated(value = Group.SelectOne.class) BookVo bookVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(()->{
            int result = bookServiceImpl.modifyBookFieldOpen(bookVo.getId());
            return result > 0 ? new R().ok().setCode(result) : new R().modifyError(bookVo.getId());
        });
        System.out.println(rData);
        return rData;
    }



    @GetMapping("queryBookDownloadUrlPage")
    @ResponseBody
    @SessionScope
    public R queryBookDownloadUrlPage(@Validated(value = Group.Select.class) BookDownloadUrlVo bookDownloadUrlVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(()->{
            List<BookDownloadUrlVo> bookDownloadUrlVos = bookServiceImpl.searchBookDownloadUrl(bookDownloadUrlVo.transformModel(Group.Select.class));
            return (bookDownloadUrlVos != null && !bookDownloadUrlVos.isEmpty()) ? new R().ok().setData(bookDownloadUrlVos) : new R().fail(bookDownloadUrlVo);
        });
        System.out.println(rData);
        return rData;
    }

    @PutMapping("addBookDownloadUrl")
    @ResponseBody
    @SessionScope
    public R addBookDownloadUrl(@Validated(value = Group.Add.class) BookDownloadUrlVo bookDownloadUrlVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() -> {
            int result = bookServiceImpl.addBookDownloadUrl(bookDownloadUrlVo.transformModel(Group.Add.class));
            return result > 0 ? new R().ok().setData(result) : new R().saveError(bookDownloadUrlVo);
        });
        System.out.println(rData);
        return rData;
    }

    @PostMapping("modifyBookDownloadUrl")
    @ResponseBody
    @SessionScope
    public R modifyBookDownloadUrl(@Validated(value = Group.Update.class) BookDownloadUrlVo bookDownloadUrlVo,BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() ->{
            int result = bookServiceImpl.modifyBookDownloadUrlFieldUrl(bookDownloadUrlVo.transformModel(Group.Update.class));
            return result > 0 ? new R().ok().setData(result) : new R().modifyError(bookDownloadUrlVo.getId());
        });
        System.out.println(rData);
        return rData;
    }



    @GetMapping("queryBookIntroPage")
    @ResponseBody
    @SessionScope
    public R queryBookIntroPage(@Validated(value = Group.Select.class) BookIntroVo bookIntroVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(()->{
            List<BookIntroVo> bookIntroVos = bookServiceImpl.searchBookIntro(bookIntroVo.transformModel(Group.Select.class));
            return (bookIntroVos != null && !bookIntroVos.isEmpty()) ? new R().ok().setData(bookIntroVos) : new R().fail(bookIntroVo);
        });
        System.out.println(rData);
        return rData;
    }

    @PutMapping("addBookIntro")
    @ResponseBody
    @SessionScope
    public R addBookIntro(@Validated(value = Group.Add.class) BookIntroVo bookIntroVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(()->{
            int result = bookServiceImpl.addBookIntro(bookIntroVo.transformModel(Group.Add.class));
            return result > 0 ? new R().ok().setData(result) : new R().saveError(bookIntroVo);
        });
        System.out.println(rData);
        return rData;
    }

    @PostMapping("modifyBookIntro")
    @ResponseBody
    @SessionScope
    public R modifyBookIntro(@Validated(value = Group.Update.class) BookIntroVo bookIntroVo,BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(()->{
            int result = bookServiceImpl.modifyBookIntroFieldIntro(bookIntroVo.transformModel(Group.Update.class));
            return result > 0 ? new R().ok().setData(result) : new R().modifyError(bookIntroVo.getId());
        });
        System.out.println(rData);
        return rData;
    }



    @GetMapping("queryBookPublishHousePage")
    @ResponseBody
    @SessionScope
    public R queryBookPublishHousePage(@Validated(value = Group.Select.class) BookPublishHouseVo bookPublishHouseVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(()->{
            List<BookPublishHouseVo> bookPublishHouseVos = bookServiceImpl.searchBookPublishHouse(bookPublishHouseVo.transformModel(Group.Select.class));
            return (bookPublishHouseVos != null && !bookPublishHouseVos.isEmpty()) ? new R().ok().setData(bookPublishHouseVos) : new R().fail(bookPublishHouseVo);
        });
        System.out.println(rData);
        return rData;
    }

    @PutMapping("addBookPublishHouse")
    @ResponseBody
    @SessionScope
    public R addBookPublishHouse(@Validated(Group.Add.class) BookPublishHouseVo bookPublishHouseVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() ->{
           int result = bookServiceImpl.addBookPublishHouse(bookPublishHouseVo.transformModel(Group.Add.class));
           return result > 0 ? new R().ok().setData(result) : new R().saveError(bookPublishHouseVo);
        });
        System.out.println(rData);
        return rData;
    }

    @PostMapping("modifyBookPublishHouseFieldPublishHouseId")
    @ResponseBody
    @SessionScope
    public R modifyBookPublishHouseFieldPublishHouseId(@Validated(Group.Update2.class) BookPublishHouseVo bookPublishHouseVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() ->{
            int result = bookServiceImpl.modifyBookPublishHouseFieldPublishHouseId(bookPublishHouseVo.transformModel(Group.Update2.class));
            return result > 0 ? new R().ok().setData(result) : new R().modifyError(bookPublishHouseVo.getPublishHouseId());
        });
        System.out.println(rData);
        return rData;
    }

    @PostMapping("modifyBookPublishHouseFieldBookId")
    @ResponseBody
    @SessionScope
    public R modifyBookPublishHouseFieldBookId(@Validated(Group.Update1.class) BookPublishHouseVo bookPublishHouseVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() ->{
            int result = bookServiceImpl.modifyBookPublishHouseFieldBookId(bookPublishHouseVo.transformModel(Group.Update1.class));
            return result > 0 ? new R().ok().setData(result) : new R().modifyError(bookPublishHouseVo.getBookId());
        });
        System.out.println(rData);
        return rData;
    }

    @DeleteMapping("deleteBookPublishHouse")
    @ResponseBody
    @SessionScope
    public R deleteBookPublishHouse(@Validated(Group.Delete.class) BookPublishHouseVo bookPublishHouseVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() ->{
            int result = bookServiceImpl.deleteBookPublishHouse(bookPublishHouseVo.transformModel(Group.Delete.class));
            return result > 0 ? new R().ok().setData(result) : new R().deleteError(bookPublishHouseVo);
        });
        System.out.println(rData);
        return rData;
    }



    @GetMapping("queryBookAuthorPage")
    @ResponseBody
    @SessionScope
    public R queryBookAuthorPage(@Validated(value = Group.Select.class) BookAuthorVo bookAuthorVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(()->{
            List<BookAuthorVo> bookAuthorVos = bookServiceImpl.searchBookAuthor(bookAuthorVo.transformModel(Group.Select.class));
            return (bookAuthorVos != null && !bookAuthorVos.isEmpty()) ? new R().ok().setData(bookAuthorVos) : new R().fail(bookAuthorVo);
        });
        System.out.println(rData);
        return rData;
    }

    @PutMapping("addBookAuthor")
    @ResponseBody
    @SessionScope
    public R addBookAuthor(@Validated(Group.Add.class) BookAuthorVo bookAuthorVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() ->{
            int result = bookServiceImpl.addBookAuthor(bookAuthorVo.transformModel(Group.Add.class));
            return result > 0 ? new R().ok().setData(result) : new R().saveError(bookAuthorVo);
        });
        System.out.println(rData);
        return rData;
    }

    @PostMapping("modifyBookAuthorFieldAuthorId")
    @ResponseBody
    @SessionScope
    public R modifyBookAuthorFieldAuthorId(@Validated(Group.Update2.class) BookAuthorVo bookAuthorVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() ->{
            int result = bookServiceImpl.modifyBookAuthorFieldAuthorId(bookAuthorVo.transformModel(Group.Update2.class));
            return result > 0 ? new R().ok().setData(result) : new R().modifyError(bookAuthorVo.getAuthorId());
        });
        System.out.println(rData);
        return rData;
    }

    @PostMapping("modifyBookAuthorFieldBookId")
    @ResponseBody
    @SessionScope
    public R modifyBookAuthorFieldBookId(@Validated(Group.Update1.class) BookAuthorVo bookAuthorVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() ->{
            int result = bookServiceImpl.modifyBookAuthorFieldBookId(bookAuthorVo.transformModel(Group.Update1.class));
            return result > 0 ? new R().ok().setData(result) : new R().modifyError(bookAuthorVo.getBookId());
        });
        System.out.println(rData);
        return rData;
    }

    @DeleteMapping("deleteBookAuthor")
    @ResponseBody
    @SessionScope
    public R deleteBookAuthor(@Validated(Group.Delete.class) BookAuthorVo bookAuthorVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() ->{
            int result = bookServiceImpl.deleteBookAuthor(bookAuthorVo.transformModel(Group.Delete.class));
            return result > 0 ? new R().ok().setData(result) : new R().deleteError(bookAuthorVo);
        });
        System.out.println(rData);
        return rData;
    }



    @GetMapping("queryBookArchiveTypePage")
    @ResponseBody
    @SessionScope
    public R queryBookArchiveTypePage(@Validated(value = Group.Select.class) BookArchiveTypeVo bookArchiveTypeVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(()->{
            List<BookArchiveTypeVo> bookArchiveTypeVos = bookServiceImpl.searchBookArchiveType(bookArchiveTypeVo.transformModel(Group.Select.class));
            return (bookArchiveTypeVos != null && !bookArchiveTypeVos.isEmpty()) ? new R().ok().setData(bookArchiveTypeVos) : new R().fail(bookArchiveTypeVo);
        });
        System.out.println(rData);
        return rData;
    }

    @PutMapping("addBookArchiveType")
    @ResponseBody
    @SessionScope
    public R addBookArchiveType(@Validated(Group.Add.class) BookArchiveTypeVo bookArchiveTypeVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() ->{
            int result = bookServiceImpl.addBookArchiveType(bookArchiveTypeVo.transformModel(Group.Add.class));
            return result > 0 ? new R().ok().setData(result) : new R().saveError(bookArchiveTypeVo);
        });
        System.out.println(rData);
        return rData;
    }

    @PostMapping("modifyBookArchiveTypeFieldArchiveTypeId")
    @ResponseBody
    @SessionScope
    public R modifyBookArchiveTypeFieldArchiveTypeId(@Validated(Group.Update2.class) BookArchiveTypeVo bookArchiveTypeVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() ->{
            int result = bookServiceImpl.modifyBookArchiveTypeFieldArchiveTypeId(bookArchiveTypeVo.transformModel(Group.Update2.class));
            return result > 0 ? new R().ok().setData(result) : new R().modifyError(bookArchiveTypeVo.getArchiveTypeId());
        });
        System.out.println(rData);
        return rData;
    }

    @PostMapping("modifyBookArchiveTypeFieldBookId")
    @ResponseBody
    @SessionScope
    public R modifyBookArchiveTypeFieldBookId(@Validated(Group.Update1.class) BookArchiveTypeVo bookArchiveTypeVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() ->{
            int result = bookServiceImpl.modifyBookArchiveTypeFieldBookId(bookArchiveTypeVo.transformModel(Group.Update1.class));
            return result > 0 ? new R().ok().setData(result) : new R().modifyError(bookArchiveTypeVo.getBookId());
        });
        System.out.println(rData);
        return rData;
    }

    @DeleteMapping("deleteBookArchiveType")
    @ResponseBody
    @SessionScope
    public R deleteBookArchiveType(@Validated(Group.Delete.class) BookArchiveTypeVo bookArchiveTypeVo, BindingResult bindingResult){
        R rData = paramsErrorHandlerOptional(bindingResult).orElseGet(() ->{
            int result = bookServiceImpl.deleteBookArchiveType(bookArchiveTypeVo.transformModel(Group.Delete.class));
            return result > 0 ? new R().ok().setData(result) : new R().deleteError(bookArchiveTypeVo);
        });
        System.out.println(rData);
        return rData;
    }


    @PostMapping("tList")
    @ResponseBody
    public List tList(@Valid BookVo bookVo, BindingResult bindingResult){
        System.out.println("================bookVo defaultlist=================");
        System.out.println("bindingResult.getErrorCount(): " + bindingResult.getErrorCount());
        System.out.println("bindingResult.getFieldError():" + bindingResult.getFieldError());
        if(!bindingResult.hasErrors()) {
//            List list = bookServiceImpl.search(bookVo);
//            System.out.println("list -> " + list);
            System.out.println("bindingResult.getAllErrors(): "+bindingResult.getAllErrors());
        }
        return null;
    }





}
