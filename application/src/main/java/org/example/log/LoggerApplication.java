package org.example.log;

import org.example.log.model.DBOperations;
import org.example.log.model.JSONReader;
import org.example.log.model.LogData;
import org.example.log.util.EventDuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class LoggerApplication {

    public static void main(String[] args) throws URISyntaxException {
        System.out.println("Inside main!");
        LoggerApplication application = new LoggerApplication();
        JSONReader reader =  new JSONReader(application.getFileFromResource("logfile.json"));
        try {
            List<LogData> dataList = reader.getLogsFromFile();

           // read data to update AlertFlag (true if the event took longer than 4ms, otherwise false)
            EventDuration eventDuration = new EventDuration();
            eventDuration.setAlertFlagsForDelayedEvents(dataList);
            DBOperations.uploadToDatabase(dataList);

            System.out.println("Log read and upload Process successfully completed!");
        } catch (FileNotFoundException exp) {
            exp.printStackTrace();
        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }

    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found error! " + fileName);
        } else {
            return new File(resource.toURI());
        }

    }


}
