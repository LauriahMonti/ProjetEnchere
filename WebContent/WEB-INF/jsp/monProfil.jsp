<%@page import="fr.eni.projet.beans.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="../fragments/header.jspf"%>
<div class="row">
	<div class="col-lg-6 offset-lg-3 col-xs-12">
	<% Utilisateur utilisateur = (Utilisateur)request.getSession().getAttribute("utilisateurConnecte"); %>

<form action="<%= request.getContextPath()%>/GestionUtilisateurs" method="post">
			<p>
				<strong>Pseudo : <%=utilisateur.getPseudo() %></strong>
			</p>
			<br> <br>
			<p>
				<strong>Nom : <%=utilisateur.getNom() %></strong>
			</p>
			<br> <br>
			<p>
				<strong>Pr�nom : <%=utilisateur.getPrenom() %></strong>
			</p>
			<br> <br>
			<p>
				<strong>Email : <%=utilisateur.getEmail() %></strong>
			</p>
			<br> <br>
			<p>
				<strong>T�l�phone : <%=utilisateur.getTelephone() %></strong>
			</p>
			<br> <br>
			<p>
				<strong>Rue : <%=utilisateur.getRue() %></strong>
			</p>
			<br> <br>
			<p>
				<strong>Code postal : <%=utilisateur.getCodePostal() %></strong>
			</p>
			<br> <br>
			<p>
				<strong>Ville : <%=utilisateur.getVille() %></strong>
			</p>
			<br> <br>
			<button type="submit" class=" col-lg-4 btn btn-default">
				<p>Modifier</p>
			</button>
</div>
</div>
</body>
</html>