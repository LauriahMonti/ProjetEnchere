<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../fragments/header.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="blocProfil col-lg-6 col-lg-offset-3 col-xs-12">
	<%String sidentifiant = (String)request.getAttribute("sidentifiant");%>
		<%
			String erreur = (String) request.getAttribute("erreur");
			if (erreur != null) {
		%>
		<p class="red"><%=erreur%></p>
		<%
			}
		%>
		<form action="<%=request.getContextPath()%>/connexion" method="post">
			<p>
				<strong>identifiant :</strong>
			</p>
			<input type="text" id="sidentifiant" name="sidentifiant" size="80"
				value="${fn:escapeXml(sidentifiant)}" placeholder="<%=sidentifiant%>"><br> <br>
			<p>
				<strong>Mot de passe :</strong>
			</p>
			<input type="password" id="smdp" name="smdp" size="80"
				value="${fn:escapeXml(smdp)}"><br> <br>
			<button type="submit" class=" col-lg-4 btn btn-default">
				<p>Connexion</p>
			</button>
			<div class="col-lg-8 offset-lg-1">
	</div>
		</form>
				<form action="LireCookie" method="post">
				  <label for="memoire">Se souvenir de moi</label>
                <input type="checkbox" id="memoire" name="memoire" />
			</form>
				<a href="#ServletPourGestionEnvoiMailAvecJMailEtMimeMessage">Mot de passe oublié</a>
		<form action="<%=request.getContextPath()%>/GestionInscription" method="get">
			<br>
			<p> </p>
			<br>
			<button type="submit" class="col-lg-11 btn btn-default">
				<p>Créer un compte</p>
			</button>
		</form>
	</div>
</div>
</body>
</html>