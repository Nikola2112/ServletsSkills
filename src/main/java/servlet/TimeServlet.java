package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        String timezoneParam = request.getParameter("timezone");

        ZoneId zoneId = ZoneId.of("UTC");
        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            zoneId = ZoneId.of(timezoneParam);
        }

        response.getWriter()
                .write(LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'")));


    }
}
//надо написть регион/город например: time?timezone=America/Denver , time?timezone=Europe/Kiev