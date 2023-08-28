<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="refugiodeanimales.entidadesdenegocio.Genero"%>
<%@page import="refugiodeanimales.accesoadatos.GeneroDAL"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Genero> generos = GeneroDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slGenero" name="idGenero">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Genero genero : generos) {%>
    <option <%=(id == genero.getId()) ? "selected" : ""%>  value="<%=genero.getId()%>"><%= genero.getNombre()%></option>
    <%}%>
</select>
<label for="idGenero">Genero</label>