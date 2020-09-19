package com.mainul35.google.calendar.configuration;

import com.google.api.services.calendar.Calendar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestGoogleServiceConfig {

    @Autowired
    private GoogleServiceConfig googleServiceConfig;
    @Mock
    private Calendar calendar;

    @BeforeEach
    public void setup() {

    }

/*    @Test
    public void testGetCalendar() throws GeneralSecurityException, IOException {

        assertNotNull(calendar);
    }*/
}
