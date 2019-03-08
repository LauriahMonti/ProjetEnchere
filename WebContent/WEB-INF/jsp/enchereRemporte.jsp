<%@page import="fr.eni.projet.beans.ArticleVendu"%>
<%@page import="fr.eni.projet.beans.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../fragments/header.jspf"%>

	<div class="row">
	<h3 class="formulaire text-center">Détail Vente</h3>
	
	<div class="col-lg-offset-3 col-lg-6 col-xs-12">
			<div class="blocProfil">
				<div class="col-lg-6 col-xs-6">
					<p><strong>Article : </strong></p>
					<p><strong>Description : </strong></p>
					<p><strong>Meilleure offre : </strong></p>
					<p><strong>Mise à prix : </strong></p>
					<p><strong>Fin de l'enchère : </strong></p>
					<p><strong>Retrait : </strong></p>
					<p><strong>Vendeur : </strong></p>
				</div>
				<div class="col-lg-6 col-xs-6 text-center">
					<%
						ArticleVendu vente = (ArticleVendu) request.getAttribute("vente");
					%>
					<p><strong><%=vente.getNomArticle() %></strong></p>
					<p><%=vente.getDescription()%></p>
					<p><%=vente.getPrixVente()%> pts par <%= vente.getAcheteur().getPseudo() %></p>
					<p><%=vente.getMiseAPrix()%></p>
					<p><%=vente.getDateFinEncheres()%></p>
					<p><%=vente.getLieuRetrait()%></p>
					<p><%=vente.getVendeurO().getPseudo() %></p>
				</div>
				<%
					boolean btnOn = (boolean) request.getAttribute("btnOn");
					if (btnOn == true) {
				%>
				<form action="<%=request.getContextPath()%>/#" method="get">
						<button type="submit" class="col-lg-11 btn btn-default"><p>Retrait effectué</p></button>
				</form>
				<%
					}
				%>
			</div>
		</div>
	</div>
</body>
</html>