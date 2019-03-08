<%@page import="fr.eni.projet.beans.ArticleVendu"%>
<%@page import="fr.eni.projet.beans.Utilisateur"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../fragments/header.jspf"%>

	<div class="row">
	<h3 class="formulaire text-center">Détail Vente</h3>
	
	<div class="blocProfil col-md-6 col-md-offset-3 col-xs-12">
	<%
	Date finEnchere = new Date();
	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String finEnchereFin;
	ArticleVendu vente = (ArticleVendu) request.getAttribute("vente");
	%>
		<div class="table-responsive">
		  <table class="table">
			    <thead>
				    <tr>
					    <th>Article : </th>
					    <th><%=vente.getNomArticle() %></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><strong>Description : </strong></td>
					    <td><%=vente.getDescription()%></td>
					</tr>
					<tr>
						<td><strong>Categorie : </strong></td>
						<td><%=vente.getCategorieDetails().getLibelle()%></td>
					</tr>
					<tr>
						<td><strong>Meilleure offre : </strong></td>
						<td><%=vente.getPrixVente()%> pts par <%= vente.getAcheteur().getPseudo() %></td>
					</tr>
					<tr>
						<td><strong>Mise à prix : </strong></td>
						<td><%=vente.getMiseAPrix()%> pts</td>
					</tr>
						<% finEnchere = vente.getDateFinEncheres();
							finEnchereFin = sdf.format(finEnchere);%>
					<tr>
						<td><strong>Fin de l'enchère : </strong></td>
						<td><%=finEnchereFin%></td>
					</tr>
					<tr>
						<td><strong>Retrait :</strong></td>
						<td><%=vente.getLieuRetrait()%></td>
					<tr>
						<td><strong>Vendeur :</strong></td>
						<td><%=vente.getVendeurO().getPseudo() %></td>
					</tr>
				</tbody>
		  </table>
		</div>
				<form action="<%=request.getContextPath()%>/detailVente" method="post">
					<div class="form-group">
						<label class="col-md-4 control-label" for="sproposition" name="sproposition" id="sproposition">Ma proposition :</label>
						<div class="col-md-2">
							<input type="text" list="comboid" class="form-control" id="sproposition" name="sproposition">
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
						<button type="submit" class="col-lg-2 btn btn-default" name="noArticle" value="<%= vente.getNoArticle() %>"><p>Encherir</p></button>
				</form>
				<% String message = (String)request.getAttribute("message");
					if (message != null){
				%>
				<p>resultat : <%=message %></p>
				<%
					}
				%>
			</div>
			
		</div>
</body>
</html>