import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();

        // Allow access to resources directory
        if (requestURI.startsWith(req.getContextPath() + "/resources/")) {
            chain.doFilter(request, response);
            return;
        }

        SessionBean sessionBean = (SessionBean) req.getSession().getAttribute("sessionBean");

        String loginURL = req.getContextPath() + "/pages/login.xhtml";
        String registerURL = req.getContextPath() + "/pages/register.xhtml";
        String imprintURL = req.getContextPath() + "/pages/imprint.xhtml";
        String privacyURL = req.getContextPath() + "/pages/privacy.xhtml";

        boolean loggedIn = sessionBean != null && sessionBean.isLoggedIn();
        boolean loginRequest = req.getRequestURI().equals(loginURL);
        boolean registerRequest = req.getRequestURI().equals(registerURL);
        boolean imprintRequest = req.getRequestURI().equals(imprintURL); // Check if the request is for imprint
        boolean privacyRequest = req.getRequestURI().equals(privacyURL); // Check if the request is for privacy
        boolean resourceRequest = req.getRequestURI().startsWith(req.getContextPath() + "/javax.faces.resource");

        if (loggedIn || loginRequest || registerRequest || imprintRequest || privacyRequest || resourceRequest) { // Allow access to imprint and privacy
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(loginURL);
        }
    }

    @Override
    public void destroy() {
    }
}