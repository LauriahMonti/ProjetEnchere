package fr.eni.projet.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.DAL.ArticleVenduDAO;
import fr.eni.projet.DAL.CategorieDAO;
import fr.eni.projet.DAL.DALException;
import fr.eni.projet.beans.ArticleVendu;
import fr.eni.projet.beans.Categorie;

/**
 * Servlet implementation class Encheres
 */
@WebServlet("/encheres")
public class Encheres extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Encheres() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String categorie = (String)request.getParameter("scategorie");
		ArrayList<ArticleVendu> listeArticle = null;
		ArrayList<Categorie> listeCategorie = null;
		try {	
			
			listeCategorie = CategorieDAO.lister();
			request.setAttribute("listeCategorie", listeCategorie);
			
		} catch (DALException e) {
			e.printStackTrace();
			request.setAttribute("erreur", e);
			this.getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreur.jsp").forward(request, response);
		}
		
		try {
			listeArticle = ArticleVenduDAO.lister();
			if(listeArticle != null) {
				if(categorie == null) {
				request.setAttribute("listeArticle", listeArticle);
				this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
				}
				else {
					this.getServletContext().getRequestDispatcher("/recherche").forward(request, response);
				}
			}
			
		} catch (DALException e) {
			e.printStackTrace();
			request.setAttribute("erreur", e);
			this.getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreur.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		doGet(request, response);
	}
}
