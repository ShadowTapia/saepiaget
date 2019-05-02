package com.piaget.Clases;

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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marcelo
 */
@WebFilter("*.xhtml")
public class SessionUrlFilter implements Filter {

    FilterConfig filterConfig;

    @Override
    public void init(FilterConfig fc) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sre, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sr;
        HttpServletResponse res = (HttpServletResponse) sre;
        HttpSession session = req.getSession(true);

        String requestUrl = req.getRequestURL().toString();

        if (session.getAttribute("userMail") == null && !requestUrl.contains("index.xhtml")) {
            res.sendRedirect(req.getContextPath() + Utilidades.basePathLogin());
        } else {
            fc.doFilter(sr, sre);
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

}
