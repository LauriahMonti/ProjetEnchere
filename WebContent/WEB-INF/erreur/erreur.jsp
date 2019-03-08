<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% Exception e = (Exception) request.getAttribute("erreur"); %>
<h1>ERREUR</h1>
<h4>Une erreur s'est produite : </h4>
<font color=red>
<%=e.getMessage() %><br/> 
nom de la classe : <%=e.getStackTrace()[0].getClassName()%><br/>
nom de la methode : <%=e.getStackTrace()[0].getMethodName()%><br/>
nom du fichier : <%=e.getStackTrace()[0].getFileName()%><br/>
numero de la ligne : <%=e.getStackTrace()[0].getLineNumber()%><br/>
</font>
</body>
</html>