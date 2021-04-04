package org.example.log;

import org.example.log.model.LogData;
import org.example.log.util.EventDuration;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class LogDataTest {

    LogData log1;
    LogData log2;

    @org.junit.Before
    public void setup() {
         log1 = new LogData("scsmbstgra", "STARTED", 1491377495212L,
                "12345", "APPLICATION_LOG");
         log2 = new LogData("scsmbstgra", "FINISHED", 1491377495218L,
                "12345", "APPLICATION_LOG");
    }

    @Test
    public void setAlertFlagsForDelayedEventsTest() {

        List<LogData> testList = new ArrayList<>();
        testList.add(log1);
        testList.add(log2);
        EventDuration ed = new EventDuration();
        ed.setAlertFlagsForDelayedEvents(testList);
        assertEquals(Boolean.TRUE, log1.isAlertFlag());
    }


}