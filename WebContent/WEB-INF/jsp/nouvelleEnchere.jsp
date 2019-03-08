<%@page import="fr.eni.projet.beans.Retrait"%>
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
%>
<div class="container">
	<header class="row">
		<div class="col-lg-12">
			<h4 class="text-center">Nouvelle vente</h4>
			<br>
		</div>
	</header>
	<div class="row">
		<nav class="col-lg-2"><img src="./images/default.jpg" alt="" height="300px" width="300px"/></nav>
		<section class="col-lg-10">
			<form class="form-horizontal"action="<%=request.getContextPath()%>/creationEnchere"
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
						<label class="col-md-4 control-label" for="sphotoArticle">Photo
							de l'article :</label>
						<div class="col-md-4">
							<input id="sphotoArticle" name="sphotoArticle" class="input-file"
								type="file" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="smiseAPrix"  id="smiseAPrix">Mise
							à prix :</label>
						<div class="col-md-2">
							<input type="text" list="comboid" name="smiseAPrix" class="form-control">
								<datalist id="comboid">
								<option value="150">150</option>
								<option value="250">250</option>
								<option value="350">350</option>
								<option value="450">450</option>
								<option value="550">550</option>
								<option value="650">650</option>
								<option value="750">750</option>
								<option value="850">850</option>
							</datalist>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="sdebutEnchere">Début
							de l'enchère :</label>
						<div class='col-md-4'>
							<div class='input-group date' id='datetimepicker1'>
								<input type="date" class="form-control" name="sdebutEnchere" /> 
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="sfinEnchere">Fin
							de l'enchère :</label>
						<div class='col-md-4'>
							<div class='input-group date' id='datetimepicker1'>
								<input type='date' class="form-control" name="sfinEnchere"/> 
							</div>
						</div>
					</div>
					<div class="col-lg-12">
					<h4 class="text-center">Retrait</h4>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="srue">Rue :</label>
						<div class="col-md-4">
							<input id="srue" name="srue" type="text"
								placeholder="<%=utilisateur.getRue()%>" class="form-control input-md">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="scodePostal">Code
							Postal :</label>
						<div class="col-md-4">
							<input id="scodePostal" name="scodePostal" type="text"
								placeholder="<%=utilisateur.getCodePostal()%>" class="form-control input-md">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="sville">Ville :</label>
						<div class="col-md-4">
							<input id="sville" name="sville" type="text"
								placeholder="<%=utilisateur.getVille()%>" class="form-control input-md">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="button1id"></label>
						<div class="col-md-4">
							<button type="submit" id="button1id" name="button1id" class="btn btn-default">Enregistrer</button></a>
							<a href="<%=request.getContextPath()%>/creationEnchere"><button id="button2id" name="button2id" class="btn btn-default">Annuler</button></a>
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