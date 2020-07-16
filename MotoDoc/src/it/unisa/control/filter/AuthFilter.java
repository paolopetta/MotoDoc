package it.unisa.control.filter;

import it.unisa.model.UserBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/login/admin/*", "/login/user/*"})
public class AuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, resp);
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession(false);
        UserBean userBean = (UserBean) session.getAttribute("user");

        String uri = request.getRequestURI();
        if (session != null) {
            if (uri.contains("/user/")) { //qualcuno tenta di accedere alle pagine utente
                if (userBean != null && (userBean.isAdmin() || !userBean.isAdmin()))
                    chain.doFilter(req, resp); //se registrato vado avanti con i filtri
                else
                    response.sendRedirect(response.encodeRedirectURL(request.getContextPath()) + "/home.jsp"); //se non é reg rimanda alla home
            } else if (uri.contains("/admin/")) { //qualcuno tenta di accedere alle pagine admin
                if (userBean != null && (userBean.isAdmin())) chain.doFilter(req, resp);
                else response.sendRedirect(response.encodeRedirectURL(request.getContextPath()) + "/home.jsp");
            }
        } else response.sendRedirect(response.encodeRedirectURL(request.getContextPath()) + "/home.jsp");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
