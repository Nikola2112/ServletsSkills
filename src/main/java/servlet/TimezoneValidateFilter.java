package servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    private static final String COOKIE_NAME = "lastTimezone";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String timezoneParam = request.getParameter("timezone");
        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            if (!isValidTimezone(timezoneParam)) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
                return;
            }


            timezoneParam = URLEncoder.encode(timezoneParam, StandardCharsets.UTF_8.toString());
            Cookie cookie = new Cookie(COOKIE_NAME, timezoneParam);
            cookie.setMaxAge(60 * 60 * 24); // 1 день
            ((HttpServletResponse) response).addCookie(cookie);
        } else {

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(COOKIE_NAME)) {
                        String savedTimezone = cookie.getValue();
                        if (isValidTimezone(savedTimezone)) {
                            timezoneParam = savedTimezone;
                            break;
                        }
                    }
                }
            }
        }


        if (timezoneParam == null) {
            timezoneParam = "UTC";
        }


        request.setAttribute("timezone", timezoneParam);

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
