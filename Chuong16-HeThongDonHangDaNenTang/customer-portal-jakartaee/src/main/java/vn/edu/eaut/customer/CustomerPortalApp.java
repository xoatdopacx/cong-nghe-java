package vn.edu.eaut.customer;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

/**
 * Entry point khởi chạy Cổng Khách Hàng (Customer Portal - Jakarta EE)
 * Sử dụng Embedded Tomcat 10.1 (chuẩn Jakarta EE 10 / Servlet 6.0)
 * Chạy độc lập trên cổng 8080 mà không cần cài đặt Web Server ngoài.
 */
public class CustomerPortalApp {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        String envPort = System.getenv("PORT");
        if (envPort != null) {
            try {
                port = Integer.parseInt(envPort);
            } catch (NumberFormatException ignored) {}
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector();

        // Tìm thư mục webapp
        File webappDir = new File("src/main/webapp");
        if (!webappDir.exists()) {
            webappDir = new File("customer-portal-jakartaee/src/main/webapp");
        }

        StandardContext ctx = (StandardContext) tomcat.addWebapp("", webappDir.getAbsolutePath());

        // Gắn classes đã compile vào WEB-INF/classes
        File classesDir = new File("target/classes");
        if (!classesDir.exists()) {
            classesDir = new File("customer-portal-jakartaee/target/classes");
        }
        if (classesDir.exists()) {
            WebResourceRoot resources = new StandardRoot(ctx);
            resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                    classesDir.getAbsolutePath(), "/"));
            ctx.setResources(resources);
        }

        System.out.println("==================================================================");
        System.out.println("🚀 CUSTOMER PORTAL (Jakarta EE / Servlet / JSP) RUNNING");
        System.out.println("🌐 Truy cập: http://localhost:" + port);
        System.out.println("==================================================================");

        tomcat.start();
        tomcat.getServer().await();
    }
}
