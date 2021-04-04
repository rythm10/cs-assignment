package org.example.log.model;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONReader {

    private File jsonFile;

    public JSONReader(File jsonFile) {
        this.jsonFile = jsonFile;
    }

    public List<LogData> getLogsFromFile() throws FileNotFoundException {
        Gson gson = new Gson();
        List<LogData> logArray = new ArrayList<LogData>();
        try {
            Reader reader = new FileReader(jsonFile);

            // Convert JSON File to Java Object
            Type logListType = new TypeToken<ArrayList<LogData>>() {}.getType();
             logArray = gson.fromJson(reader, logListType);

            // print serverLog
            for (LogData serverLog : logArray) {
                System.out.println(serverLog.toString());
            }


        } catch (IOException exp) {
            exp.printStackTrace();
        }
        return logArray;
    }
}
