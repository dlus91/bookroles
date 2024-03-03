package com.bookroles.service.impl;

import com.bookroles.controller.vo.*;
import com.bookroles.mapper.BookMapper;
import com.bookroles.service.IDictionaryService;
import com.bookroles.service.model.*;
import com.bookroles.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: dlus91
 * @Date: 2023/9/26 15:47
 */
@Service
public class BookServiceImpl implements IBookService{

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private IDictionaryService dictionaryServiceImpl;

    @Override
    public List<BookVo> searchPage(Book book){
        List<Book> bookList = bookMapper.queryPage(book);
        List<BookVo> bookVos = null;
        if(bookList != null && !bookList.isEmpty()){
            bookVos = bookList.stream().map(model -> model.transformVo()).toList();
        }
        return bookVos;
    }

    @Override
    public BookVo searchDetail(int id) {
        Book resultBook = bookMapper.queryDetail(id);
        BookVo bookVo = null;
        if(resultBook != null){
            bookVo = resultBook.transformVo();
        }
        return bookVo;
    }

    @Override
    public boolean validBookId(int bookId) {
        return bookMapper.validBookById(bookId) > 0 ? false : true;
    }

    @Override
    public boolean validSaveBookName(Book book) {
        return bookMapper.validBookByName(book) > 0 ? false : true;
    }

    @Override
    public boolean validModifyBookName(Book book) {
        int id = bookMapper.getBookIdByName(book.getName());
        if(id == book.getId()){
            return true;
        }
        return false;
    }

    @Override
    public int addBook(Book book) {
        if(validSaveBookName(book)){
            if(book.getCreateTime() == 0){
                book.setCreateTime(System.currentTimeMillis());
            }
            return bookMapper.saveBook(book);
        }
        return 0;
    }

    @Override
    public int modifyBookById(Book book) {
        if(validModifyBookName(book)) {
            return bookMapper.updateBookById(book);
        }
        return 0;
    }

    @Override
    public int modifyBookFieldOpen(int id){
        return bookMapper.updateBookFieldOpen(id);
    }


    @Override
    public List<BookDownloadUrlVo> searchBookDownloadUrl(BookDownloadUrl bookDownloadUrl) {
        List<BookDownloadUrl> bookDownloadUrlList = bookMapper.queryBookDownloadUrl(bookDownloadUrl);
        List<BookDownloadUrlVo> bookDownloadUrlVos = null;
        if(bookDownloadUrlList != null && !bookDownloadUrlList.isEmpty()){
            bookDownloadUrlVos = bookDownloadUrlList.stream().map(model -> model.transformVo()).toList();
        }
        return bookDownloadUrlVos;
    }

    @Override
    public boolean validBookDownloadUrl(BookDownloadUrl bookDownloadUrl) {
        int count = bookMapper.validBookDownloadUrl(bookDownloadUrl);
        if(count > 0){
            return false;
        }
        return true;
    }

    @Override
    public int addBookDownloadUrl(BookDownloadUrl bookDownloadUrl) {
        if(validBookDownloadUrl(bookDownloadUrl)){
            if(bookDownloadUrl.getCreateTime() == 0){
                bookDownloadUrl.setCreateTime(System.currentTimeMillis());
            }
            return bookMapper.saveBookDownloadUrl(bookDownloadUrl);
        }
        return 0;
    }

    @Override
    public int modifyBookDownloadUrlFieldUrl(BookDownloadUrl bookDownloadUrl) {
        if(validBookDownloadUrl(bookDownloadUrl)) {
            return bookMapper.updateBookDownloadUrlFieldUrl(bookDownloadUrl);
        }
        return 0;
    }


    @Override
    public List<BookIntroVo> searchBookIntro(BookIntro bookIntro) {
        List<BookIntro> bookIntroList = bookMapper.queryBookIntro(bookIntro);
        List<BookIntroVo> bookIntroVos = null;
        if(bookIntroList != null && !bookIntroList.isEmpty()){
            bookIntroVos = bookIntroList.stream().map(model -> model.transformVo()).toList();
        }
        return bookIntroVos;
    }

    @Override
    public boolean validBookIntro(BookIntro bookIntro) {
        return bookMapper.validBookIntro(bookIntro) > 0 ? false : true;
    }

    @Override
    public int addBookIntro(BookIntro bookIntro) {
        if(bookIntro.getCreateTime() == 0){
            bookIntro.setCreateTime(System.currentTimeMillis());
        }
        return bookMapper.saveBookIntro(bookIntro);
    }

    @Override
    public int modifyBookIntroFieldIntro(BookIntro bookIntro) {
        return bookMapper.updateBookIntroFieldIntro(bookIntro);
    }

    @Override
    public List<BookPublishHouseVo> searchBookPublishHouse(BookPublishHouse bookPublishHouse) {
        List<BookPublishHouse> bookPublishHouses = bookMapper.queryBookPublishHouse(bookPublishHouse);
        List<BookPublishHouseVo> bookPublishHouseVos = null;
        if(bookPublishHouses != null || !bookPublishHouses.isEmpty()){
            bookPublishHouseVos = bookPublishHouses.stream().map(resultBookPublishHouse -> resultBookPublishHouse.transformVo()).toList();
        }
        return bookPublishHouseVos;
    }


    @Override
    public boolean validBookPublishHouse(BookPublishHouse bookPublishHouse) {
        return bookMapper.validBookPublishHouse(bookPublishHouse) > 0 ? false : true;
    }

    //新增逻辑需要 验证书籍出版社关系表是否存在 和 书籍是否存在 和 该出版社是否存在，需要 关系表不存在 并且 书籍和出版社都存在的情况下才能增加
    @Override
    public int addBookPublishHouse(BookPublishHouse bookPublishHouse) {
        Dictionary dictionary = new Dictionary(bookPublishHouse.getPublishHouseId(), null, null);
        if(validBookPublishHouse(bookPublishHouse)
                && !validBookId(bookPublishHouse.getBookId())
                && !dictionaryServiceImpl.validPublishHouse(dictionary)
        ){
            return bookMapper.saveBookPublishHouse(bookPublishHouse);
        }
        return 0;
    }

    //修改逻辑需要 验证 新数据关系表是否存在， 更新的出版社id要已存在(new)
    @Override
    public int modifyBookPublishHouseFieldPublishHouseId(BookPublishHouse bookPublishHouse) {
        Dictionary dictionary = new Dictionary(bookPublishHouse.getPublishHouseId(), null, null);
        if(validBookPublishHouse(new BookPublishHouse(bookPublishHouse.getBookId(), bookPublishHouse.getNewPublishHouseId()))
               && !dictionaryServiceImpl.validPublishHouse(dictionary)){
            return bookMapper.updateBookPublishHouseFieldPublishHouseId(bookPublishHouse);
        }
        return 0;
    }

    //修改逻辑需要 验证 新数据关系表是否存在， 更新的书籍id要已存在(new)
    @Override
    public int modifyBookPublishHouseFieldBookId(BookPublishHouse bookPublishHouse){
        if(validBookPublishHouse(new BookPublishHouse(bookPublishHouse.getNewBookId(), bookPublishHouse.getPublishHouseId()))
                && !validBookId(bookPublishHouse.getNewBookId())){
            return bookMapper.updateBookPublishHouseFieldBookId(bookPublishHouse);
        }
        return 0;
    }

    @Override
    public int deleteBookPublishHouse(BookPublishHouse bookPublishHouse){
        return bookMapper.deleteBookPublishHouse(bookPublishHouse);
    }

    @Override
    public List<BookAuthorVo> searchBookAuthor(BookAuthor bookAuthor) {
        List<BookAuthor> bookAuthors = bookMapper.queryBookAuthor(bookAuthor);
        List<BookAuthorVo> bookAuthorVos = null;
        if(bookAuthors != null || !bookAuthors.isEmpty()){
            bookAuthorVos = bookAuthors.stream().map(resultBookAuthor -> resultBookAuthor.transformVo()).toList();
        }
        return bookAuthorVos;
    }


    @Override
    public boolean validBookAuthor(BookAuthor bookAuthor) {
        return bookMapper.validBookAuthor(bookAuthor) > 0 ? false : true;
    }

    //新增逻辑需要 验证书籍作者关系表是否存在 和 书籍是否存在 和 该作者是否存在，需要 关系表不存在 并且 书籍和作者都存在的情况下才能增加
    @Override
    public int addBookAuthor(BookAuthor bookAuthor) {
        Dictionary dictionary = new Dictionary(bookAuthor.getAuthorId(), null, null);
        if(validBookAuthor(bookAuthor)
                && !validBookId(bookAuthor.getBookId())
                && !dictionaryServiceImpl.validAuthor(dictionary)
        ){
            return bookMapper.saveBookAuthor(bookAuthor);
        }
        return 0;
    }

    //修改逻辑需要 验证 新数据关系表是否存在， 更新的出版社id要已存在 (new)
    @Override
    public int modifyBookAuthorFieldAuthorId(BookAuthor bookAuthor) {
        Dictionary dictionary = new Dictionary(bookAuthor.getAuthorId(), null, null);
        if(validBookAuthor(new BookAuthor(bookAuthor.getBookId(), bookAuthor.getNewAuthorId()))
                && !dictionaryServiceImpl.validAuthor(dictionary)){
            return bookMapper.updateBookAuthorFieldAuthorId(bookAuthor);
        }
        return 0;
    }

    //修改逻辑需要 验证 新数据关系表是否存在， 更新的出版社id要已存在 (new)
    @Override
    public int modifyBookAuthorFieldBookId(BookAuthor bookAuthor) {
        if(validBookAuthor(new BookAuthor(bookAuthor.getNewBookId(), bookAuthor.getAuthorId()))
                && !validBookId(bookAuthor.getNewBookId())){
            return bookMapper.updateBookAuthorFieldBookId(bookAuthor);
        }
        return 0;
    }

    @Override
    public int deleteBookAuthor(BookAuthor bookAuthor) {
        return bookMapper.deleteBookAuthor(bookAuthor);
    }

    @Override
    public List<BookArchiveTypeVo> searchBookArchiveType(BookArchiveType bookArchiveType) {
        List<BookArchiveType> bookArchiveTypes = bookMapper.queryBookArchiveType(bookArchiveType);
        List<BookArchiveTypeVo> bookArchiveTypeVos = null;
        if(bookArchiveTypes != null || !bookArchiveTypes.isEmpty()){
            bookArchiveTypeVos = bookArchiveTypes.stream().map(resultBookArchiveType -> resultBookArchiveType.transformVo()).toList();
        }
        return bookArchiveTypeVos;
    }


    @Override
    public boolean validBookArchiveType(BookArchiveType bookArchiveType) {
        int count = bookMapper.validBookArchiveType(bookArchiveType);
        if(count > 0){
            return false;
        }
        return true;
    }

    @Override
    public int addBookArchiveType(BookArchiveType bookArchiveType) {
        if(validBookArchiveType(bookArchiveType)
                && !validBookId(bookArchiveType.getBookId())
                && !dictionaryServiceImpl.validCacheName(bookArchiveType.getArchiveTypeId(), Dictionary.DictionaryEnum.ARCHIVE_TYPE.getName())
        ){
            return bookMapper.saveBookArchiveType(bookArchiveType);
        }
        return 0;
    }

    @Override
    public int modifyBookArchiveTypeFieldArchiveTypeId(BookArchiveType bookArchiveType) {
        if(validBookArchiveType(new BookArchiveType(bookArchiveType.getBookId(), bookArchiveType.getNewArchiveTypeId()))
                && !dictionaryServiceImpl.validCacheName(bookArchiveType.getNewArchiveTypeId(), Dictionary.DictionaryEnum.ARCHIVE_TYPE.getName())){
            return bookMapper.updateBookArchiveTypeFieldArchiveTypeId(bookArchiveType);
        }
        return 0;
    }

    @Override
    public int modifyBookArchiveTypeFieldBookId(BookArchiveType bookArchiveType) {
        if(validBookArchiveType(new BookArchiveType(bookArchiveType.getNewBookId(), bookArchiveType.getArchiveTypeId()))
                && !validBookId(bookArchiveType.getNewBookId())){
            return bookMapper.updateBookArchiveTypeFieldBookId(bookArchiveType);
        }
        return 0;
    }

    @Override
    public int deleteBookArchiveType(BookArchiveType bookArchiveType) {
        return bookMapper.deleteBookArchiveType(bookArchiveType);
    }


}
