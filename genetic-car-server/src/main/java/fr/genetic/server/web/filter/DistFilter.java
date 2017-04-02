package fr.genetic.server.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Profile("intellij-dev")
@Component
public class DistFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String requestURI = httpServletRequest.getRequestURI();
        if (requestURI.startsWith("/index.html") || requestURI.startsWith("/scripts") || requestURI.startsWith("/styles")) {
            LOGGER.info("forward {}", httpServletRequest.getRequestURI());
            request.getRequestDispatcher("/build-gui" + requestURI).forward(request, response);
        } else {
            LOGGER.debug("no redirect for {}", httpServletRequest.getRequestURI());
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
