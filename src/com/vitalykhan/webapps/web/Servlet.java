package com.vitalykhan.webapps.web;

import com.vitalykhan.webapps.Config;
import com.vitalykhan.webapps.model.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Servlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        request.setAttribute("resumes", Config.get().getStorage().getAllSorted());

//        String name = request.getParameter("name");
//        response.getWriter().write("Hello, " + (name == null ? "stranger" : name) + "!");
//
//        request.getRequestDispatcher("/resumes.jsp").forward(request, response);
        List<Resume> resumeList = Config.get().getStorage().getAllSorted();
        for (Resume resume : resumeList) {
            PrintWriter writer = response.getWriter();
            writer.println("<html><head></head><body>");
            writer.println("Resume\nUUID=" + resume.getUuid());
            writer.println("Full name: " + resume.getFullName());
            for (Map.Entry<ContactType, String> entry : resume.getContactsMap().entrySet()) {
                writer.println(entry.getKey().name() + ":\t" + entry.getValue());
            }
            for (Map.Entry<SectionType, Section> entry : resume.getSectionsMap().entrySet()) {
                writer.print(entry.getKey().name() + ":\t");
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        writer.println(((StringSection) entry.getValue()).getContent());
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        ((ListSection) entry.getValue()).getItems().forEach(
                                x -> writer.println(x));
                        break;
                    default:
                        throw new IllegalStateException("Wrong resume section!");
                }
            }


            writer.println("</body></html>");

        }
    }
}
