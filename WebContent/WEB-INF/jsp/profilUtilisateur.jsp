<%@page import="fr.eni.projet.beans.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../fragments/header.jspf"%>

<div class="row">
<h3 class="formulaire text-center">Profil Utilisateur</h3>
<br><br>
	<div class="col-lg-offset-3 col-lg-6 col-xs-12 blocProfil">
			<div class="col-lg-6 col-xs-6">

				<p><strong>Pseudo : </strong></p>
				<p><strong>Nom : </strong></p>
				<p><strong>Prénom : </strong></p>
				<p><strong>Email : </strong></p>
				<p><strong>Téléphone : </strong></p>
				<p><strong>Rue : </strong></p>
				<p><strong>Code postal :</strong></p>
				<p><strong>Ville :</strong></p>
			</div>
			<div class="col-lg-6 col-xs-6 text-center">
				<%
					Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
				%>
				<p><strong><%=utilisateur.getPseudo()%></strong></p>
				<p><%=utilisateur.getNom()%></p>
				<p><%=utilisateur.getPrenom()%></p>
				<p><%=utilisateur.getEmail()%></p>
				<p><%=utilisateur.getTelephone()%></p>
				<p><%=utilisateur.getRue()%></p>
				<p><%=utilisateur.getCodePostal()%></p>
				<p><%=utilisateur.getVille()%></p>
			</div>
			
			<%
				boolean btnOn = (boolean) request.getAttribute("btnOn");
				if (btnOn == true) {
			%>
			<form action="<%=request.getContextPath()%>/GestionModificationProfil" method="get" >
			<input type="submit" class="bouton btn btn-default" value="Modifier" name="Modifier"> 
			</form>
			<%
				}
			%>
	</div>
</div>
</body>
</html>