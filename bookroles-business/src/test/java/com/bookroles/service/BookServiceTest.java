package com.bookroles.service;

import com.bookroles.dao.BookDaoImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * @Author: dlus91
 * @Date: 2023/9/20 17:13
 */
public class BookServiceTest {
    @Mock
    BookDaoImpl bookDaoImpl;
    @InjectMocks
    BookService bookService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDetailById() throws Exception {
        when(bookDaoImpl.getDetailById(anyInt())).thenReturn(Map.of("getDetailByIdResponse", "getDetailByIdResponse"));

        Map<String, String> result = bookService.getDetailById(0);
        Assert.assertEquals(Map.of("getDetailByIdResponse", "getDetailByIdResponse"), result);
    }

    @Test
    public void testGetDownLoadUrl() throws Exception {
        when(bookDaoImpl.getDownLoadUrl(anyInt())).thenReturn("getDownLoadUrlResponse");

        String result = bookService.getDownLoadUrl(0);
        Assert.assertEquals("getDownLoadUrlResponse", result);
    }

    @Test
    public void testFindByWord() throws Exception {
        when(bookDaoImpl.findByWord(anyString(), anyInt())).thenReturn(List.of(Map.of("findByWordResponse", "findByWordResponse")));

        List<Map<String, String>> result = bookService.findByWord("word", 0);
        Assert.assertEquals(List.of(Map.of("findByWordResponse", "findByWordResponse")), result);
    }

    @Test
    public void testFindByArchiveType() throws Exception {
        when(bookDaoImpl.findByArchiveType(anyInt(), anyInt())).thenReturn(List.of(Map.of("findByArchiveTypeResponse", "findByArchiveTypeResponse")));

        List<Map<String, String>> result = bookService.findByArchiveType(0, 0);
        Assert.assertEquals(List.of(Map.of("findByArchiveTypeResponse", "findByArchiveTypeResponse")), result);
    }

    @Test
    public void testFindByAuthor() throws Exception {
        when(bookDaoImpl.findByAuthor(anyString(), anyInt())).thenReturn(List.of(Map.of("findByAuthorResponse", "findByAuthorResponse")));

        List<Map<String, String>> result = bookService.findByAuthor("author", 0);
        Assert.assertEquals(List.of(Map.of("findByAuthorResponse", "findByAuthorResponse")), result);
    }

    @Test
    public void testFindHot() throws Exception {
        when(bookDaoImpl.findHot(anyInt())).thenReturn(List.of(Map.of("findHotResponse", "findHotResponse")));

        List<Map<String, String>> result = bookService.findHot(0);
        Assert.assertEquals(List.of(Map.of("findHotResponse", "findHotResponse")), result);
    }
}
