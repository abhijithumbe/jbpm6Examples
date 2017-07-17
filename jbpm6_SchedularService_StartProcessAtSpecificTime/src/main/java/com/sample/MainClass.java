package com.sample;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class MainClass {

    public static void main(String[] args) {
	// TODO Auto-generated method stub

	Calendar calendar = Calendar.getInstance();
	calendar.set(Calendar.HOUR_OF_DAY, 10);
	calendar.set(Calendar.MINUTE, 30);
	calendar.set(Calendar.SECOND, 00);

	Date startTime = calendar.getTime();
	System.out.println("alarmTime" + startTime);

	Timer timer = new Timer();
	timer.schedule(new ScheduledTask(), 0, 5000); // It will start Proces after every 5 seconds
	// timer.schedule(new ScheduledTask(), startTime); //It will start at 10.30 AM
        // timer.cancel();  //Cancel scheduler.
    }

}
