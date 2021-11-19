//package mk.ukim.finki.wp.lab.web.sessionCounter;
//
//import javax.servlet.annotation.WebListener;
//import javax.servlet.http.HttpSessionEvent;
//import javax.servlet.http.HttpSessionListener;
//@WebListener
//public class SessionCounter implements HttpSessionListener {
//    private static int counter;
//
//    public SessionCounter() {
//        counter=0;
//    }
//    @Override
//    public void sessionCreated(HttpSessionEvent se) {
//        counter++;
//    }
//    @Override
//    public void sessionDestroyed(HttpSessionEvent se) {
//        counter--;
//    }
//    public int getNumSessions()
//    {
//        return counter;
//    }
//}
