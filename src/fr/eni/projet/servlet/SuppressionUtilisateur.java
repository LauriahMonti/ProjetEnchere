package fr.eni.projet.servlet;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.DAL.DALException;
import fr.eni.projet.DAL.UtilisateurDAO;
import fr.eni.projet.beans.Utilisateur;

/**
 * Servlet implementation class SuppressionUtilisateur
 */
@WebServlet("/SuppressionUtilisateur")
public class SuppressionUtilisateur extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuppressionUtilisateur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateurConnecte = null;
		utilisateurConnecte = (Utilisateur)request.getSession().getAttribute("utilisateurConnecte");
		if (utilisateurConnecte!=null) {
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/modifierProfil.jsp" ).forward( request, response );
		} else {
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/index.jsp" ).forward( request, response );
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateurConnecte = (Utilisateur)request.getSession().getAttribute("utilisateurConnecte");
		String pseudo;
		
		pseudo = utilisateurConnecte.getPseudo();
		try {
			UtilisateurDAO.supprimer(pseudo);
		} catch (DALException e) {
			getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreur.jsp").forward(request, response);
		}
		request.getSession().invalidate();
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/connexion.jsp").forward(request, response);
	}

}
