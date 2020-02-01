package com.vitalykhan.webapps.web;

import com.vitalykhan.webapps.Config;
import com.vitalykhan.webapps.model.*;
import com.vitalykhan.webapps.storage.Storage;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Servlet extends javax.servlet.http.HttpServlet {
    public static final String RESUMES_JSP_PATH = "/WEB-INF/jsp/list.jsp";
    public static final String VIEW_JSP_PATH = "/WEB-INF/jsp/view.jsp";
    public static final String EDIT_JSP_PATH = "WEB-INF/jsp/edit.jsp";
    Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Resume resume;
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        boolean resumeExists = uuid != null && uuid.length() > 0;
        if (!resumeExists) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }
        for (ContactType contactType : ContactType.values()) {
            String parameter = request.getParameter(contactType.name());
            if (parameter != null && parameter.trim().length() > 0) {
                resume.addContact(contactType, parameter);
            } else {
                resume.getContactsMap().remove(contactType);
            }
        }
        for (SectionType type : SectionType.values()) {
            String parameters = request.getParameter(type.name());
            if (parameters == null) {
                continue;
            }
            parameters = parameters.replaceAll("\r","");
            List<String> notNullValuesList = Arrays.stream(parameters.split("\n"))
                    .filter(x -> x.trim().length() > 0)
                    .collect(Collectors.toList());
            if (notNullValuesList.size() == 0) {
                resume.getSectionsMap().remove(type);
                continue;
            }
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                case HOBBIES:
                        resume.addSection(type, new StringSection(parameters));
                    break;
                case LANGUAGES:
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                        resume.addSection(type, new ListSection(notNullValuesList));
                case EDUCATION:
                case EXPERIENCE:
                    //TODO
                    break;
            }
        }
        if (!resumeExists) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("serv");
    }

    protected void doGet(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher(RESUMES_JSP_PATH).forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("serv");
                return;
            case "new":
                r = Resume.EMPTY;
                break;
            case "edit":
            case "view":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalStateException("Wrong action with resume!");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher("view".equals(action) ? VIEW_JSP_PATH :
                EDIT_JSP_PATH).forward(request, response);
/*
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
*/
    }
}
