package fr.eni.projet.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.DAL.DALException;
import fr.eni.projet.DAL.UtilisateurDAO;

/**
 * Servlet implementation class GestionCredits
 */
@WebServlet("/crediter")
public class GestionCredits extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionCredits() {
        super();
    }

	/**Méthode Get qui pertmet de rediriger vers la jsp crediter un utilisateur
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/crediterAdmin.jsp").forward(request, response);
	}

	/**Méthode Post qui permet de créditer un compte utilisateur en fonction du montant saisi dans la jsp credierAdmin
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message=null;
		String erreur=null;
		
		if (request.getParameter("spseudo").trim().isEmpty()) {
			erreur = "Erreur - Veuillez renseigner un pseudo uilisateur.";
			request.setAttribute("erreur", erreur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/crediterAdmin.jsp").forward(request, response);
		}
		if (request.getParameter("scredit").trim().isEmpty()) {
			erreur = "Erreur - Veuillez renseigner un crédit.";
			request.setAttribute("erreur", erreur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/crediterAdmin.jsp").forward(request, response);
		}
		String pseudo = request.getParameter("spseudo").trim();
		int credit = Integer.parseInt(request.getParameter("scredit").trim()); 
		try {
			UtilisateurDAO.crediter(credit, pseudo);
		} catch (DALException e) {

			erreur = "Utilisateur pas présent en base";
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreur.jsp").forward(request,
					response);
		} catch (SQLException e) {
			erreur = "erreur";
			request.setAttribute("erreur", erreur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreur.jsp").forward(request,
					response);
		}
		message = "L'utilisateur " + pseudo + " à bien été créditer de " + credit + " crédits.";
		request.setAttribute("message", message);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/crediterAdmin.jsp").forward(request, response);
	}

}
