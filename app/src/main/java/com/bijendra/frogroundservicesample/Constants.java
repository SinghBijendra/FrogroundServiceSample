package com.bijendra.frogroundservicesample;

/**
 * Created by Newdream on 13-Nov-16.
 */

public class Constants {
    public interface AUDIO_SERVICE_ACTION {
        public static String MAIN = "com.bijendra.action.main";
        public static String INIT = "com.bijendra.action.init";
        public static String PREV = "com.bijendra.action.prev";
        public static String PLAY = "com.bijendra.action.play";
        public static String NEXT = "com.bijendra.action.next";
        public static String STARTFOREGROUND= "com.bijendra.action.startforeground";
        public static String STOPFOREGROUND = "com.bijendra.action.stopforeground";
    }
    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 2016;
    }

}
