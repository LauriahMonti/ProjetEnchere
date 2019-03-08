<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="fr.eni.projet.beans.Categorie"%>
<%@page import="fr.eni.projet.beans.ArticleVendu"%>
<%@page import="fr.eni.projet.DAL.CategorieDAO"%>
<%@page import="fr.eni.projet.DAL.ArticleVenduDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ include file="../fragments/header.jspf"%>
<div class="row">
	<div class="col-lg-12 text-right">
		<a href="<%=request.getContextPath()%>/GestionInscription">S'inscrire
			- </a> <a href="<%=request.getContextPath()%>/LireCookie">Se connecter</a>
	</div>
</div>
<div class="row">
	<div class="col-lg-12">
		<h3 class="text-center">Liste des enchères</h3>
	</div>
</div>

<form action="<%= request.getContextPath()%>/encheres" method="get">
<div class="row text-center">
	<div class="col-lg-6 pad">
	
		<label class="col-md-4 control-label" for="sfiltres">Filtres:</label>
		<div class="input-group">
			<span class="input-group-addon" id="basic-addon1"><span
				class="glyphicon glyphicon-search"></span></span> <input type="text" name ="srecherche"
				class="form-control" placeholder="Le nom de l'article contient">
		</div>

			<label class="col-md-4 control-label" for="scategorie">Catégories:</label>
			
			<div class="col-md-4">
				<select id="scategorie" name="scategorie" class="form-control">
				<option value ="0">-Choisir Catégorie-</option>
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
	<div class="col-lg-6">

		<button type="submit" class="btn btn-default" name = "search">Rechercher</button>

	</div>
</form>
</div>


<div class="row">
<% 

						Date finEnchere = new Date();
						DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String finEnchereFin;
						
						ArrayList<ArticleVendu> listeArticle = (ArrayList<ArticleVendu>)request.getAttribute("listeArticle");
						for (ArticleVendu av : listeArticle) {
							
%>

	<div class="col-lg-5 article">
		<div class="col-lg-3 image">
			<img src="./images/default.jpg" alt="" width="100px" height="100px">
		</div>
		<div class="col-lg-9">
			<h4><a href="<%= request.getContextPath()%>/connexion"><%= av.getNomArticle() %></a></h4>
			<p>
				Prix :<%= av.getPrixVente() %></p>
			<% finEnchere = av.getDateFinEncheres();
				finEnchereFin = sdf.format(finEnchere);%>
			<p>
				Fin de l'enchère :<%= finEnchereFin %></p>
			<p>Vendeur :
				<form action="<%= request.getContextPath()%>/profil" method="post">
					<%= av.getVendeur() %>
					<button type="submit" name="spseudo" value="<%= av.getVendeur() %>"><span class="glyphicon glyphicon-search"></span></button>
				</form>
			</p>
		</div>
	</div> 
	<%
	}
	%>



<%
			String erreur = (String) request.getAttribute("erreur");
			if (erreur != null) {
		%>
		<p class="red"><%=erreur%></p>
		
				<%
			}
		%> 
					

</div>
</body>
</html>