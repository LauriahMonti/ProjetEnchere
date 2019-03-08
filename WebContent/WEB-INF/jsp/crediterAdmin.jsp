<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../fragments/header.jspf"%>

<div class="formulaire">
	<h3 class="text-center">Crediter un Utilisateur</h3>
	<form action="<%=request.getContextPath()%>/crediter" method="post">
	<%
			String erreur = (String)request.getAttribute("erreur");
			if (erreur != null) {
		%>
		<p class="red"><%=erreur%></p>
		<%
			}
		%>
		<div class="col-lg-12">
			<p class="col-lg-2">Pseudo de l'utilisateur :</p>
			<input type="text" name="spseudo" value="" class="col-lg-3"><br>
		</div>
		<div class="col-lg-12">
			<p class="col-lg-2">Montant à créditer :</p>
			<input type="text" name="scredit" value="" class="col-lg-3"><br>
		</div>
		<div class="bouton col-lg-12">
			<button type="submit" class=" col-lg-4 btn btn-default">
				Créditer l'utilisateur
			</button>
		</div>
	</form>
	<%
		String message = (String)request.getAttribute("message");
		if (message != null) {
	%>
	<p class="text-center"><Strong><%= message %></Strong></p>
	<%
		}
	%>
</div>
</body>
</html>