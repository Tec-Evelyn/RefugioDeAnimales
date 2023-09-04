<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Login</title>

    </head>
    <body>
       
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">
             <div class="box">
        <h5>Iniciar Sesión</h5>
        
            <form action="Usuario?accion=login" method="post" class="form">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">  
                
                <div class="row">
                    <div class="input-field col l5 s12">                                             
                        <i class="material-icons prefix">account_circle</i>
                        <input  id="txtLogin" type="text" name="login" required class="form_input" maxlength="25">  
                        <span for="txtLogin" class="form_label">Login</span>
                    </div>                                       
                </div>
                
                <div class="row">
                    <div class="input-field col l5 s12" >                                             
                        <i class="material-icons prefix">enhanced_encryption</i>
                        <input  id="txtPassword" type="password" name="password" required class="form_input" minlength="5" maxlength="32">  
                        <span for="txtPassword" class="form_label">Password</span>
                    </div>                                       
                </div>
                
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn black"><i class="material-icons right">send</i>Login</button>                                               
                    </div>
                </div>
                <% if (request.getAttribute("error") != null) { %>
                <div class="row">
                    <div class="col l12 s12">
                        <span style="color:red"><%= request.getAttribute("error") %></span>                                              
                    </div>
                </div>
                <%}%>
            </form> 
        </div>    
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />      
    </body>
</html>
