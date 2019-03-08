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
import fr.eni.projet.beans.Utilisateur;

/**
 * Servlet implementation class EnchereRemporte
 */
@WebServlet("/EnchereRemporte")
public class EnchereRemporte extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnchereRemporte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateur = (Utilisateur)request.getSession().getAttribute("utilisateurConnecte");
		try {
			EnchereDAO.AFFICHERACQUEREUR(utilisateur.getNoUtilisateur());
		} catch (DALException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/enchereRemporte.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
