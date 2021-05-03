package hu.alkfejl.filter;

import hu.alkfejl.model.ChatUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class SessionFilter implements Filter {
    private List<String> filterException;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterException = Arrays.asList(filterConfig.getServletContext().getInitParameter("outer-pages").split(","));
        this.filterException.replaceAll(String::trim);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String path = ((HttpServletRequest) servletRequest).getServletPath();

        if (this.filterException.stream().anyMatch(path::equals)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        ChatUser currentUser = (ChatUser) ((HttpServletRequest) servletRequest).getSession().getAttribute("currentUser");

        if (currentUser == null) {
            ((HttpServletResponse) servletResponse).sendRedirect(((HttpServletRequest) servletRequest).getContextPath() + "/pages/login.jsp");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}
