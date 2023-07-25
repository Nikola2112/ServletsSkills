package servlet;

import java.io.IOException;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       
        String timezoneParam = request.getParameter("timezone");
        if (timezoneParam == null || timezoneParam.isEmpty()) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
            return;
        }
        if (!isValidTimezone(timezoneParam)) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isValidTimezone(String timezone) {
        TimeZone timeZone = TimeZone.getTimeZone(timezone);
        return timeZone.getID().equals(timezone);
    }
}