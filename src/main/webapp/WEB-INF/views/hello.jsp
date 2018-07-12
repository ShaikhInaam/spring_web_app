<%@page import="com.springboot.main.util.LogedUsersData"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Boot</title>
</head>
<body>

	<%
	
	if(session.getAttribute("username")==null)
 	{
		
 		response.sendRedirect("/");
 	}
	
	
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setDateHeader("Expires", 0); // Proxies.
	
	%>


  <h1>Spring Boot - MVC web application</h1>
  <hr>

  
  
  <table aligh="center"  border = "4">
	
		<tr>
   		 <th>ID</th>
  		 <th>Full Name</th> 
   		 <th>Username</th>
   		 <th>Email</th>
   		 <th>Cell#</th>
   		 <th>Password</th>
  		</tr>
  		
  		<tr>
  		 <td><%=session.getAttribute("id") %>  </td>
  		 <td><%=session.getAttribute("fullName") %>  </td>
  		 <td><%=session.getAttribute("username") %>  </td>
  		 <td><%=session.getAttribute("email") %>  </td>
  		 <td><%=session.getAttribute("mobile") %>  </td>
  		 <td><%=session.getAttribute("password") %>  </td>
  		 
  		</tr>
	
	</table>
	
	<br></br>
	
	
	<table aligh="center" border = "4">
	
	<tr><th>Loged Users</th></tr>
	
	<%
	 Map<Object,Object> map=LogedUsersData.mp;
	for (Map.Entry<Object, Object> entry : map.entrySet())
	{
		
	%>
	
	<tr><td><%=entry.getValue() %></td></tr>
	
	<%} %>
	
	</table>
	
	<br></br>
	
	
	<input id="messageField" type="text">
<input onclick="sendMsg();" value="send" type="button">
<div id="msg-box" style="width:500px; height: 400px; background: #eee; overflow:auto;"></div>

<script>
    var webSocket = new WebSocket("ws://localhost:8080/myHandler");
    var msgField = document.getElementById("messageField");
    var divMsg = document.getElementById("msg-box");
    var name="<%=session.getAttribute("fullName")%>"
    
    function sendMsg() {
        var msgToSend = msgField.value;
        webSocket.send(name +" :> "+msgToSend+"\n");
        divMsg.innerHTML += "<div style='color:green'>" + name +" :> "+ msgToSend +
                            "</div>"
        msgField.value = "";
    };
    webSocket.onmessage = function(message) {
    	
                divMsg.innerHTML += message.data;
                alert(message.data);
    };
    webSocket.onopen = function() {
        console.log("connection opened");
    };
    webSocket.onclose = function() {
        console.log("connection closed");
    };
    webSocket.onerror = function wserror(message) {
        console.log("error: " + message);
    };

   document.getElementById("messageField")
        .addEventListener("keyup", function(event) {
    event.preventDefault();
    if (event.keyCode === 13) {
        sendMsg();
    }
});
</script>
	
	
	<br></br>
	<form method="post" action="logout">
	
		<button name="logout" type="submit" value="logout">logout</button>
	
	</form>
	
	
	

</body>
</html>