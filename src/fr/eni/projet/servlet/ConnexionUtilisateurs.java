package fr.eni.projet.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import fr.eni.projet.DAL.DALException;
import fr.eni.projet.DAL.UtilisateurDAO;
import fr.eni.projet.beans.Categorie;
import fr.eni.projet.beans.Utilisateur;

/**
 * Servlet implementation class ConnexionUtilisateurs
 */
@WebServlet("/connexion")
public class ConnexionUtilisateurs extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	 public static final String  COOKIE_DERNIERE_CONNEXION = "derniereConnexion";
	 public static final String  VUE                       = "/WEB-INF/jsp/connexion.jsp";
	 Cookie cookie = null;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConnexionUtilisateurs() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/connexion.jsp" ).forward( request, response );
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String identifiant = null;
		String motdepasse = null;
		String message = null;
		Utilisateur utilisateurConnecte = null;


		if (request.getParameter( "sidentifiant" ).trim().isEmpty() ) {
			message = "Erreur - Veuillez renseigner votre identifiant.";
			request.setAttribute("erreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/connexion.jsp" ).forward( request, response );
			return; // TODO Refactorer
		}

		if (request.getParameter( "smdp" ).trim().isEmpty()) {
			message = "Erreur - Veuillez renseigner votre mot de passe.";
			request.setAttribute("erreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/connexion.jsp" ).forward( request, response );
			return; // TODO Refactorer
		}

		identifiant = request.getParameter( "sidentifiant" );
		motdepasse = request.getParameter( "smdp" );
		
		try {
			utilisateurConnecte = UtilisateurDAO.rechercher(identifiant, motdepasse);
			if (utilisateurConnecte != null) {
				 String pseudo = request.getParameter("sidentifiant");
			     cookie = new Cookie(COOKIE_DERNIERE_CONNEXION,pseudo);
			     cookie.setMaxAge(300);
			     response.addCookie(cookie);
				 request.getSession().invalidate();
				 request.getSession().setAttribute("utilisateurConnecte", utilisateurConnecte);
				getServletContext().getRequestDispatcher("/encheresCo").forward( request, response );
				return; // TODO Refactorer
			}else {
				message = "Utilisateur inconnu. Veuillez ressaisir votre identifiant et mot de passe, ou vous inscrire. ";
				request.setAttribute("erreur", message);
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/connexion.jsp").forward(request, response);
				return; // TODO Refactorer
			}
		} catch (DALException e) {
			request.setAttribute("erreur", e);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/erreur/erreur.jsp" ).forward( request, response );			
		}  
	        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

}
