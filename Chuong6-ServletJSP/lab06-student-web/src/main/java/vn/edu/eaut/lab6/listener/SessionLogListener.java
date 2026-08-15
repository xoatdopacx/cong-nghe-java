package vn.edu.eaut.lab6.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * Bài 5: Listener ghi log khi session mới được tạo hoặc bị hủy.
 */
@WebListener
public class SessionLogListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("[SessionLogListener] Session moi duoc tao: " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("[SessionLogListener] Session da bi huy: " + se.getSession().getId());
    }
}
