package filters;

import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Filter",
        urlPatterns = {"/*"}
)
public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        CustomLogger.logEnter(this.getClass(),"doFilter",
                "req: " + servletRequest.toString(),"chain: " +chain.toString());

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("CORSFilter HTTP Request: " + request.getMethod());

        // Authorize (allow) all domains to consume the content
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");

        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
        if (request.getMethod().equals("OPTIONS")) {
            String reqHead = request.getHeader("Access-Control-Request-Headers");

            if(null != reqHead && !reqHead.equals(null)){
                ((HttpServletResponse) servletResponse).addHeader(
                        "Access-Control-Allow-Headers", reqHead);
            }
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            CustomLogger.logExit(this.getClass(),"doFilter",HttpServletResponse.SC_ACCEPTED + "");
            return;
        }

        // pass the request along the filters chain
        chain.doFilter(request, servletResponse);
        CustomLogger.logExit(this.getClass(),"doFilter",servletResponse.toString());
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
