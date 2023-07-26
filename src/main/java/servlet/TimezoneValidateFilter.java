package servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String timezoneParam = request.getParameter("timezone");
        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            if (!isValidTimezone(timezoneParam)) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
                return;
            }
        }

        chain.doFilter(request, response);
    }
    private boolean isValidTimezone(String timezone) {
        try {
            TimeZone.getTimeZone(timezone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
