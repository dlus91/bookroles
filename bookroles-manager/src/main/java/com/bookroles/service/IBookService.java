package com.bookroles.service;

import com.bookroles.controller.vo.*;
import com.bookroles.service.model.*;

import java.util.List;

/**
 * @Author: dlus91
 * @Date: 2023/9/27 10:28
 */
public interface IBookService {

    //book表
    List<BookVo> searchPage(Book book);

    BookVo searchDetail(int id);

    boolean validBookId(int bookId);

    boolean validSaveBookName(Book book);

    boolean validModifyBookName(Book book);

    int addBook(Book book);

    int modifyBookById(Book book);

    int modifyBookFieldOpen(int id);


    //BookDownloadUr表
    List<BookDownloadUrlVo> searchBookDownloadUrl(BookDownloadUrl bookDownloadUrl);

    boolean validBookDownloadUrl(BookDownloadUrl bookDownloadUrl);

    int addBookDownloadUrl(BookDownloadUrl bookDownloadUrl);

    int modifyBookDownloadUrlFieldUrl(BookDownloadUrl bookDownloadUrl);


    //BookIntro表
    List<BookIntroVo> searchBookIntro(BookIntro bookIntro);

    boolean validBookIntro(BookIntro bookIntro);

    int addBookIntro(BookIntro bookIntro);

    int modifyBookIntroFieldIntro(BookIntro bookIntro);


    //BookPublishHouse表
    List<BookPublishHouseVo> searchBookPublishHouse(BookPublishHouse bookPublishHouse);

    boolean validBookPublishHouse(BookPublishHouse bookPublishHouse);

    int addBookPublishHouse(BookPublishHouse bookPublishHouse);

    int modifyBookPublishHouseFieldPublishHouseId(BookPublishHouse bookPublishHouse);

    int modifyBookPublishHouseFieldBookId(BookPublishHouse bookPublishHouse);

    int deleteBookPublishHouse(BookPublishHouse bookPublishHouse);


    //BookAuthor表
    List<BookAuthorVo> searchBookAuthor(BookAuthor bookAuthor);

    boolean validBookAuthor(BookAuthor bookAuthor);

    int addBookAuthor(BookAuthor bookAuthor);

    int modifyBookAuthorFieldAuthorId(BookAuthor bookAuthor);

    int modifyBookAuthorFieldBookId(BookAuthor bookAuthor);

    int deleteBookAuthor(BookAuthor bookAuthor);


    //BookArchiveType表
    List<BookArchiveTypeVo> searchBookArchiveType(BookArchiveType bookArchiveType);

    boolean validBookArchiveType(BookArchiveType bookArchiveType);

    int addBookArchiveType(BookArchiveType bookArchiveType);

    int modifyBookArchiveTypeFieldArchiveTypeId(BookArchiveType bookArchiveType);

    int modifyBookArchiveTypeFieldBookId(BookArchiveType bookArchiveType);

    int deleteBookArchiveType(BookArchiveType bookArchiveType);


}
