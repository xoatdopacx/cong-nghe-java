package vn.edu.eaut.lab8.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 * Managed Bean xử lý đăng nhập (Bài 8).
 */
@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;
    private boolean loggedIn = false;

    // Tài khoản mẫu
    private static final String VALID_USER = "admin";
    private static final String VALID_PASS = "123456";

    public String login() {
        if (VALID_USER.equals(username) && VALID_PASS.equals(password)) {
            loggedIn = true;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Đăng nhập thành công",
                            "Chào mừng " + username + "!"));
            return "index?faces-redirect=true";
        } else {
            loggedIn = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lỗi đăng nhập",
                            "Sai tài khoản hoặc mật khẩu!"));
            return null;
        }
    }

    public String logout() {
        loggedIn = false;
        username = null;
        password = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }
}
