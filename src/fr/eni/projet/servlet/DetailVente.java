package fr.eni.projet.servlet;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.DAL.DALException;
import fr.eni.projet.DAL.EnchereDAO;
import fr.eni.projet.beans.ArticleVendu;
import fr.eni.projet.beans.Utilisateur;

/**
 * Servlet implementation class DetailVente
 */
@WebServlet("/detailVente")
public class DetailVente extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailVente() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String article = request.getParameter("noArticle");
		int noArticle = Integer.parseInt(article);
		
		try {
			ArticleVendu vente = EnchereDAO.detailVente(noArticle);
			if(vente != null) {
				request.setAttribute("vente", vente);
				this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/detailVente.jsp").forward(request,
						response);
			}			
		} catch (DALException e) {
			e.printStackTrace();
			request.setAttribute("erreur", e);
			getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreur.jsp").forward(request, response);
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String montant = request.getParameter("sproposition");
		int Montantenchere = Integer.parseInt(montant);
		Utilisateur encherisseur = (Utilisateur)request.getSession().getAttribute("utilisateurConnecte");
		int noEncherisseur = encherisseur.getNoUtilisateur();
		int noArticle = Integer.parseInt(request.getParameter("noArticle"));
		
		try {
			String message = EnchereDAO.encherir(noEncherisseur, noArticle, Montantenchere);
			request.setAttribute("message", message);
			doGet(request, response);
			} catch (DALException e) {
			request.setAttribute("erreur", e);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/erreur/erreur.jsp" ).forward( request, response );	
		}
		
	}

}
