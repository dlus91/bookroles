package com.bookroles.configurer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import javax.servlet.FilterConfig;

import static org.mockito.Mockito.*;

/**
 * @Author: dlus91
 * @Date: 2023/9/21 9:29
 */
public class EncryptFilterTest {
    @Mock
    Logger logger;
    @Mock
    FilterConfig filterConfig;
    @InjectMocks
    EncryptFilter encryptFilter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInit() throws Exception {
        encryptFilter.init(null);
    }

    @Test
    public void testDoFilter() throws Exception {
        encryptFilter.doFilter(null, null, null);
    }

    @Test
    public void testDestroy() throws Exception {
        encryptFilter.destroy();
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: https://weirddev.com/forum#!/testme