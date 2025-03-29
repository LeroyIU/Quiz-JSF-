import java.io.IOException;
import java.util.Set;

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

        String contextPath = req.getContextPath();
        String requestURI = req.getRequestURI();

        // Allow access to resources directory
        if (requestURI.startsWith(contextPath + "/resources/")) {
            chain.doFilter(request, response);
            return;
        }

        SessionBean sessionBean = (SessionBean) req.getSession().getAttribute("sessionBean");

        // Define allowed URLs
        Set<String> allowedURLs = Set.of(
            contextPath + "/pages/login.xhtml",
            contextPath + "/pages/register.xhtml",
            contextPath + "/pages/imprint.xhtml",
            contextPath + "/pages/privacy.xhtml",
            contextPath + "/pages/weihnachtsmann.xhtml",
            contextPath + "/pages/osterhase.xhtml"
        );

        boolean loggedIn = sessionBean != null && sessionBean.isLoggedIn();
        boolean resourceRequest = requestURI.startsWith(contextPath + "/javax.faces.resource");
        boolean allowedRequest = allowedURLs.contains(requestURI);

        // Allow access if logged in, requesting a public page, or accessing resources
        if (loggedIn || allowedRequest || resourceRequest) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(contextPath + "/pages/login.xhtml");
        }
    }

    /**
     * Cleans up resources used by the filter. This method is called once when the filter is destroyed.
     */
    @Override
    public void destroy() {
    }
}