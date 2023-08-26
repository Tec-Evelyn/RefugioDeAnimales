<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="refugiodeanimales.entidadesdenegocio.Tipo"%>
<%@page import="refugiodeanimales.accesoadatos.TipoDAL"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Tipo> tipos = TipoDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slTipo" name="idContacto">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Tipo tipo : tipos) {%>
        <option <%=(id == tipo.getId()) ? "selected" : "" %>  value="<%=tipo.getId()%>"><%= tipo.getNombre()%></option>
    <%}%>
</select>
<label for="idContacto">Tipo</label>