<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.*" %>
<%
    response.sendRedirect(request.getContextPath() + "/HomeServlet"); // Reindirizza direttamente alla HomeServlet
%>