package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/event")
public class EventServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setHeader("Access-Control-Allow-Methods","POST, GET, OPTIONS, DELETE");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/event_db",
                            "root", "binu12345");

            ResultSet resultSet = connection.prepareStatement("SELECT * FROM eventTable").executeQuery();
            List<Map<String, String>> elist = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, String> event=new HashMap<String,String>();
                event.put("eid", resultSet.getString("id"));
                event.put("ename", resultSet.getString("name"));
                event.put("edescription", resultSet.getString("description"));
                event.put("edate", resultSet.getString("date"));
                event.put("eplace", resultSet.getString("place"));
                elist.add(event);
            }
            resp.setContentType("application/json");
            ObjectMapper mapper=new ObjectMapper();
            mapper.writeValue(resp.getWriter(), elist);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> event = objectMapper.readValue(req.getInputStream(), Map.class);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/event_db", "root", "binu12345");

            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO eventTable (id, name, description, date, place) VALUES (?, ?, ?, ?, ?)"
            );

            stmt.setString(1, event.get("eid"));
            stmt.setString(2, event.get("ename"));
            stmt.setString(3, event.get("edescription"));
            stmt.setString(4, event.get("edate"));
            stmt.setString(5, event.get("eplace"));

            int rows = stmt.executeUpdate();
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), rows);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> event = mapper.readValue(req.getInputStream(), Map.class);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/event_db", "root", "binu12345");

            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE eventTable SET name = ?, description = ?, date = ?, place = ? WHERE id = ?"
            );
            stmt.setString(1, event.get("ename"));
            stmt.setString(2, event.get("edescription"));
            stmt.setString(3, event.get("edate"));
            stmt.setString(4, event.get("eplace"));
            stmt.setString(5, event.get("eid"));

            int rows = stmt.executeUpdate();
            resp.setContentType("application/json");
            mapper.writeValue(resp.getWriter(), rows);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> body = mapper.readValue(req.getInputStream(), Map.class);
        String eid = body.get("eid");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/event_db", "root", "binu12345");

            PreparedStatement stmt = connection.prepareStatement("DELETE FROM eventTable WHERE id = ?");
            stmt.setString(1, eid);

            int rows = stmt.executeUpdate();
            resp.setContentType("application/json");
            mapper.writeValue(resp.getWriter(), rows);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
