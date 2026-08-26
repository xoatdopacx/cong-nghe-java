package vn.edu.eaut.lab10.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter đảm bảo UTF-8 encoding cho tất cả request/response.
 */
@WebFilter("/*")
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Chỉ set Content-Type text/html cho non-static requests
        if (request instanceof HttpServletRequest) {
            String uri = ((HttpServletRequest) request).getRequestURI();
            if (!isStaticResource(uri)) {
                response.setContentType("text/html; charset=UTF-8");
            }
        } else {
            response.setContentType("text/html; charset=UTF-8");
        }

        chain.doFilter(request, response);
    }

    private boolean isStaticResource(String uri) {
        return uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png")
                || uri.endsWith(".jpg") || uri.endsWith(".jpeg") || uri.endsWith(".gif")
                || uri.endsWith(".svg") || uri.endsWith(".ico") || uri.endsWith(".woff")
                || uri.endsWith(".woff2") || uri.endsWith(".ttf") || uri.endsWith(".eot");
    }
}
