package fr.genetic.server.web.filter;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * {@see org.springframework.web.socket.sockjs.support.AbstractSockJsService}
 */
@Component
public class CorsFilter implements Filter {

    private static final String ONE_YEAR = "3600";

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        addCorsHeaders(req, res, HttpMethod.GET, HttpMethod.OPTIONS, HttpMethod.POST);
        chain.doFilter(req, res);

    }

    protected void addCorsHeaders(ServletRequest request, ServletResponse response, HttpMethod... httpMethods) {

        HttpServletRequest requestHttp = (HttpServletRequest) request;
        HttpServletResponse responseHttp = (HttpServletResponse) response;

        String origin = requestHttp.getHeader("Origin");
        origin = ((origin == null) || origin.equals("null")) ? "*" : origin;

        responseHttp.addHeader("Access-Control-Allow-Origin", origin);
        responseHttp.addHeader("Access-Control-Allow-Credentials", "true");

        Enumeration<String> accessControllerHeaders = requestHttp.getHeaders("Access-Control-Request-Headers");
        if (accessControllerHeaders != null) {
            while (accessControllerHeaders.hasMoreElements()) {
                responseHttp.addHeader("Access-Control-Allow-Headers", accessControllerHeaders.nextElement());
            }
        }

        if (!ObjectUtils.isEmpty(httpMethods)) {
            responseHttp.addHeader("Access-Control-Allow-Methods", StringUtils.arrayToDelimitedString(httpMethods, ", "));
            responseHttp.addHeader("Access-Control-Max-Age", String.valueOf(ONE_YEAR));
        }
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}
