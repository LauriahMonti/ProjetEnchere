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
 * Servlet qui est appeler au moment de l'action "rechercher" de la page index
 * Servlet implementation class Recherche
 */
@WebServlet("/recherche")
public class Recherche extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Recherche() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categorie = request.getParameter("scategorie");
		String nomArticle = request.getParameter("srecherche");

		int cat = Integer.parseInt(categorie);
		String message = null;
		ArrayList<ArticleVendu> listeArticleBySearch = null;
		
		if(nomArticle.length() >= 1 && cat == 0) {

			try {

				listeArticleBySearch = ArticleVenduDAO.rechercheParMotCle(nomArticle);

				if(listeArticleBySearch != null) {

						request.setAttribute("listeArticle", listeArticleBySearch);
						this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward( request, response );

				}else {

						message = "Pas d'articles pour votre recherche";
						request.setAttribute("erreur", message);
						getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
					}
				} catch (DALException e) {
					e.printStackTrace();
					request.setAttribute("erreur", e);
					this.getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreur.jsp").forward(request, response);
				}
		}
		

		if(cat >= 1  && ((nomArticle == null) || (nomArticle.length() == 0))) {

			try {
				listeArticleBySearch = ArticleVenduDAO.rechercheParIdCategories(cat);
				if(listeArticleBySearch != null) {

					request.setAttribute("listeArticle", listeArticleBySearch);
					
					this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward( request, response );

					
				}else {

					message = "Pas d'articles de cette catégorie";
					request.setAttribute("erreur", message);
					getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
				}
				
			} catch (DALException e) {
				e.printStackTrace();
				request.setAttribute("erreur", e);
				this.getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreur.jsp").forward(request, response);
			}
		}
		if(cat >= 1 && nomArticle.length() >= 1) {

			try {
				listeArticleBySearch = ArticleVenduDAO.rechercheParDeuxFiltres(nomArticle, cat);
				if(listeArticleBySearch != null) {

					request.setAttribute("listeArticle", listeArticleBySearch);
					
					this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward( request, response );

					
				}else {

					message = "Pas d'articles pour votre recherche";
					request.setAttribute("erreur", message);
					getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
				}
				
			} catch (DALException e) {
				e.printStackTrace();
				request.setAttribute("erreur", e);
				this.getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreur.jsp").forward(request, response);
			}
		}
		}
		

}
