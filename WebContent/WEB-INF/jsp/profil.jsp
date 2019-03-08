
<%@page import="fr.eni.projet.beans.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="<%=request.getContextPath() %>/themes/bootstrap-3.3.7/dist/css/bootstrap-theme.min.css"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/themes/bootstrap-3.3.7/dist/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<title>ENI Ench&#232re</title>
</head>
<body class="container-fluid">
<header class="row">
<h2 class="col-lg-12">ENI-Ench&#232re</h2>
</header>
			<%
		Utilisateur utilisateur = (Utilisateur)request.getSession().getAttribute("utilisateurConnecte");
		%>
<div class="row">
	<div class="col-sm-12">

		<p>Bonjour <%= utilisateur.getNom() %>,<%= utilisateur.getPrenom() %>.</p>
	</div>
</div>


	<a href="<%= request.getContextPath() %>/GestionDeconnexion">Deconnexion</a>

</body>
</html>