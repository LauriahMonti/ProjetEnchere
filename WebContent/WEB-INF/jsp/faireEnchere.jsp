<%@page import="fr.eni.projet.beans.Enchere"%>
<%@page import="fr.eni.projet.beans.ArticleVendu"%>
<%@page import="fr.eni.projet.beans.Utilisateur"%>
<%@page import="fr.eni.projet.beans.Categorie"%>
<%@page import="java.util.ArrayList"%>
<%@page import="fr.eni.projet.DAL.CategorieDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../fragments/header.jspf"%>
<%
	Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateurConnecte");
	ArticleVendu articleVendu = (ArticleVendu) request.getSession().getAttribute("utilisateurConnecte");
	Enchere e = (Enchere) request.getSession().getAttribute("utilisateurConnecte");
%>
<div class="container">
	<header class="row">
		<div class="col-lg-12">
			<h4 class="text-center">Détail vente</h4>
			<br>
		</div>
	</header>
	<div class="row">
		<nav class="col-lg-2"><img src="./images/default.jpg" alt="" height="300px" width="300px"/></nav>
		<section class="col-lg-10">
			<form class="form-horizontal"action="<%=request.getContextPath()%>/encherir"
		method="post">
		<fieldset>
					<div class="form-group">
						<label class="col-md-4 control-label" for="sarticle">Article
							:</label>
						<div class="col-md-4">
							<input id="sarticle" name="sarticle" type="text" placeholder=""
								class="form-control input-md">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="sdescription">Description
							:</label>
						<div class="col-md-4">
							<textarea class="form-control" id="sdescription"
								name="sdescription"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="scategorie">Catégorie
							:</label>
						<div class="col-md-4">
							<select id="scategorie" name="scategorie" class="form-control">
					<%
						@SuppressWarnings("unchecked")
						ArrayList<Categorie> listeCategorie = (ArrayList<Categorie>)request.getAttribute("listeCategorie");
						for (Categorie c : listeCategorie) {
					%>
					<option value="<%=c.getNoCategorie()%>"><%=c.getLibelle()%></option>
					<%
						}
					%>
				</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="smiseAPrix">Meilleur offre :</label>
						<%=e.getMontantEnchere() %>
						<div class="col-md-2">
							<select id="smiseAPrix" name="smiseAPrix" class="form-control">
								<option value="">150</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="smiseAPrix">Mise
							à prix :</label>
							<%=articleVendu.getMiseAPrix() %>
						<div class="col-md-2">
							<select id="smiseAPrix" name="smiseAPrix" class="form-control">
								<option value="">150</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-4 control-label" for="sfinEnchere">Fin
							de l'enchère :</label>
					
					<%=articleVendu.getDateFinEncheres() %>
					
						<div class='col-md-4'>
							<div class='input-group date' id='datetimepicker1'>
								<input type='text' class="form-control" /> <span
									class="input-group-addon"> <span
									class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="sfinEnchere">Retrait</label>
						<div class='col-md-4'>
							<div class='input-group date' id='datetimepicker1'>
								<%=utilisateur.getRue() %><br><%=utilisateur.getCodePostal() %>
								<%=utilisateur.getVille() %>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="sfinEnchere">Vendeur</label>
						<div class='col-md-4'>
							<div class='input-group date' id='datetimepicker1'>
								<%=utilisateur.getPrenom() %>

							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="sfinEnchere">Ma proposition</label>
						<div class='col-md-4'>
							<div class='input-group date' id='datetimepicker1'>
														<div class="col-md-2">
							<select id="smiseAPrix" name="smiseAPrix" class="form-control">
								<option value="">500</option>
							</select>
						</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="button1id"></label>
						<div class="col-md-4">
							<button id="button1id" name="button1id" class="btn btn-default">Encherir</button>
						</div>
					</div>
				</fieldset>
			</form>
		</section>
	</div>
	<footer class="row"> </footer>
</div>
</body>
</html>