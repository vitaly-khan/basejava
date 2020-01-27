package com.vitalykhan.webapps.web;

import com.vitalykhan.webapps.Config;
import com.vitalykhan.webapps.model.*;
import com.vitalykhan.webapps.storage.Storage;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Servlet extends javax.servlet.http.HttpServlet {
    Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        request.setAttribute("resumes", Config.get().getStorage().getAllSorted());
//        request.getRequestDispatcher("/resumes.jsp").forward(request, response);

        PrintWriter writer = response.getWriter();
        List<Resume> resumeList = storage.getAllSorted();

        writer.println("<html><head></head><body>");

        for (Resume resume : resumeList) {
            writer.println("<h3>Full name: " + resume.getFullName() + "</h3>");
            writer.println("UUID=" + resume.getUuid() + "<br>");
            for (Map.Entry<ContactType, String> entry : resume.getContactsMap().entrySet()) {
                writer.println(entry.getKey().name() + ":\t" + entry.getValue() + "<br>");
            }
            writer.println("<br>");
            for (Map.Entry<SectionType, Section> entry : resume.getSectionsMap().entrySet()) {
                writer.print(entry.getKey().name() + ":\t" + "<br>");
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        writer.println(((StringSection) entry.getValue()).getContent() + "<br>");
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        ((ListSection) entry.getValue()).getItems().forEach(
                                x -> writer.println(x + "<br>"));
                        break;
                    default:
                        throw new IllegalStateException("Wrong resume section!");
                }
            }
            writer.println("<br><br>");
        }
        writer.println("</body></html>");
    }
}
