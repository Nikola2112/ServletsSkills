package servlet;


import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    private TemplateEngine engine;

    @Override
    public void init() throws ServletException {
        super.init();

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");

        engine = new TemplateEngine();
        engine.setTemplateResolver(templateResolver);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");


        String timezoneParam = (String) request.getAttribute("timezone");
        if (timezoneParam == null) {
            timezoneParam = "UTC";
        }

        ZoneId zoneId = ZoneId.of(timezoneParam);
        LocalDateTime currentTime = LocalDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'");
        String formattedTime = currentTime.format(formatter);

        Context context = new Context();
        context.setVariable("formattedTime", formattedTime);

        PrintWriter writer = response.getWriter();
        engine.process("time", context, writer);
        writer.flush();
    }
}


//надо написть регион/город например: time?timezone=America/Denver , time?timezone=Europe/Kiev
/*@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html; charset=utf-8");
    String timezoneParam = request.getParameter("timezone");

    ZoneId zoneId = ZoneId.of("UTC");
    if (timezoneParam != null && !timezoneParam.isEmpty()) {
        zoneId = ZoneId.of(timezoneParam);
    }

    response.getWriter()
            .write(LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'")));


}*/