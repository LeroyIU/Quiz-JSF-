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

    /**
     * Initializes the filter. This method is called once when the filter is first created.
     * 
     * @param filterConfig the filter configuration object
     * @throws ServletException if an error occurs during initialization
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Filters incoming requests to enforce authentication and authorization rules.
     * Redirects unauthenticated users to the login page unless the request is for
     * public resources or specific pages like login, register, imprint, or privacy.
     * 
     * @param request  the servlet request
     * @param response the servlet response
     * @param chain    the filter chain
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
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
        String weihnachtsmannURL = req.getContextPath() + "/pages/weihnachtsmann.xhtml";
        String osterhaseURL = req.getContextPath() + "/pages/osterhase.xhtml";

        boolean loggedIn = sessionBean != null && sessionBean.isLoggedIn();
        boolean loginRequest = req.getRequestURI().equals(loginURL);
        boolean registerRequest = req.getRequestURI().equals(registerURL);
        boolean imprintRequest = req.getRequestURI().equals(imprintURL); // Check if the request is for imprint
        boolean privacyRequest = req.getRequestURI().equals(privacyURL); // Check if the request is for privacy
        boolean weihnachtsmannRequest = req.getRequestURI().equals(weihnachtsmannURL);
        boolean osterhaseRequest = req.getRequestURI().equals(osterhaseURL);
        boolean resourceRequest = req.getRequestURI().startsWith(req.getContextPath() + "/javax.faces.resource");

        if (loggedIn || loginRequest || registerRequest || imprintRequest || privacyRequest || resourceRequest || weihnachtsmannRequest || osterhaseRequest) { // Allow access to new pages
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(loginURL);
        }
    }

    /**
     * Cleans up resources used by the filter. This method is called once when the filter is destroyed.
     */
    @Override
    public void destroy() {
    }
}