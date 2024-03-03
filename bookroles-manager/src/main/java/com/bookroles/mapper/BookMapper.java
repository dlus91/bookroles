package com.bookroles.mapper;

import com.bookroles.service.model.*;

import java.util.List;

/**
 * @Author: dlus91
 * @Date: 2023/9/27 9:44
 */
public interface BookMapper {

    //book表
    List<Book> queryPage(Book book);

    Book queryDetail(int bookId);

    int validBookById(int id);

    int validBookByName(Book book);

    int getBookIdByName(String name);

    int saveBook(Book book);

    int updateBookById(Book book);

    int updateBookFieldOpen(int id);


    //bookdownloadurl表
    List<BookDownloadUrl> queryBookDownloadUrl(BookDownloadUrl bookDownloadUrl);

    int validBookDownloadUrl(BookDownloadUrl bookDownloadUrl);

    int saveBookDownloadUrl(BookDownloadUrl bookDownloadUrl);

    int updateBookDownloadUrlFieldUrl(BookDownloadUrl bookDownloadUrl);


    //bookIntro表
    List<BookIntro> queryBookIntro(BookIntro bookIntro);

    int validBookIntro(BookIntro bookIntro);

    int saveBookIntro(BookIntro bookIntro);
    int updateBookIntroFieldIntro(BookIntro bookIntro);


    //bookPublishHouse表
    List<BookPublishHouse> queryBookPublishHouse(BookPublishHouse bookPublishHouse);

    int validBookPublishHouse(BookPublishHouse bookPublishHouse);

    int saveBookPublishHouse(BookPublishHouse bookPublishHouse);

    int updateBookPublishHouseFieldPublishHouseId(BookPublishHouse bookPublishHouse);

    int updateBookPublishHouseFieldBookId(BookPublishHouse bookPublishHouse);

    int deleteBookPublishHouse(BookPublishHouse bookPublishHouse);


    //bookAuthor表
    List<BookAuthor> queryBookAuthor(BookAuthor bookAuthor);

    int validBookAuthor(BookAuthor bookAuthor);

    int saveBookAuthor(BookAuthor bookAuthor);

    int updateBookAuthorFieldAuthorId(BookAuthor bookAuthor);

    int updateBookAuthorFieldBookId(BookAuthor bookAuthor);

    int deleteBookAuthor(BookAuthor bookAuthor);


    //bookArchiveTYpe表
    List<BookArchiveType> queryBookArchiveType(BookArchiveType bookArchiveType);

    int validBookArchiveType(BookArchiveType bookArchiveType);

    int saveBookArchiveType(BookArchiveType bookArchiveType);

    int updateBookArchiveTypeFieldArchiveTypeId(BookArchiveType bookArchiveType);

    int updateBookArchiveTypeFieldBookId(BookArchiveType bookArchiveType);

    int deleteBookArchiveType(BookArchiveType bookArchiveType);

}
