package org.example.log.util;

import org.example.log.model.LogData;

import java.util.List;

public class EventDuration {

    public void setAlertFlagsForDelayedEvents(List<LogData> list) {

        long lapsTime = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 1; j < list.size(); j++) {
                if (list.get(i).getId().equals(list.get(j).getId())) {
                    if (!list.get(i).getState().equals(list.get(j).getState())) {
                        if (list.get(i).getState().toUpperCase().equals("STARTED")) {
                            lapsTime = list.get(j).getTimestamp() - list.get(i).getTimestamp();
                        } else {
                            lapsTime = list.get(i).getTimestamp() - list.get(j).getTimestamp();
                        }
                    }
                }
                if (lapsTime > 4) {
                    list.get(i).setAlertFlag(true);
                    list.get(i).setEventDuration((int) lapsTime);
                    list.get(j - 1).setAlertFlag(true);
                    list.get(j - 1).setEventDuration((int) lapsTime);
                    lapsTime = 0;
                    break;
                } else if (lapsTime <= 4) {
                    if (list.get(j).getId().equals(list.get(0).getId())) {
                        list.get(j).setEventDuration(list.get(0).getEventDuration());
                    }
                    if (list.get(i).getId().equals(list.get(j).getId())) {
                        list.get(i).setAlertFlag(false);
                        list.get(i).setEventDuration((int) lapsTime);
                        list.get(j).setAlertFlag(false);
                        list.get(j).setEventDuration((int) lapsTime);
                    }
                }
            }
        }
    }
}
