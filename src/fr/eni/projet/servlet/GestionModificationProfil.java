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
import fr.eni.projet.beans.Utilisateur;

/**
 * Servlet implementation class GestionModificationProfil
 */
@WebServlet("/GestionModificationProfil")
public class GestionModificationProfil extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionModificationProfil() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utilisateur utilisateurConnecte = null;
		utilisateurConnecte = (Utilisateur) request.getSession().getAttribute("utilisateurConnecte");
		if (utilisateurConnecte != null) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request,
					response);
		} else {
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utilisateur utilisateur =(Utilisateur)request.getSession().getAttribute("utilisateurConnecte");
		String motDePasseSession = utilisateur.getMotDePasse();
		String ancienPseudo = utilisateur.getPseudo();
		String erreur = null;
		if (request.getParameter("spseudo").trim().isEmpty()) {
			erreur = "Erreur - Veuillez renseigner votre pseudo.";
			request.setAttribute("erreur", erreur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
		}
		if (request.getParameter("snom").trim().isEmpty()) {
			erreur = "Erreur - Veuillez renseigner votre nom.";
			request.setAttribute("erreur", erreur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
		}
		if (request.getParameter("sprenom").trim().isEmpty()) {
			erreur = "Erreur - Veuillez renseigner votre prénom.";
			request.setAttribute("erreur", erreur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
		}
		if (request.getParameter("scodePostal").trim().isEmpty()) {
			erreur = "Erreur - Veuillez renseigner votre codePostal.";
			request.setAttribute("erreur", erreur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
		}
		if (request.getParameter("sville").trim().isEmpty()) {
			erreur = "Erreur - Veuillez renseigner votre ville.";
			request.setAttribute("erreur", erreur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
		}
		if (request.getParameter("smotDePasseActuel").trim().isEmpty()) {
			erreur = "Erreur - Veuillez renseigner votre mot de passe.";
			request.setAttribute("erreur", erreur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
		}
		if (request.getParameter("snouveauMotDePasse").trim().isEmpty()) {
			erreur = "Erreur - Veuillez renseigner votre nouveau mot de passe.";
			request.setAttribute("erreur", erreur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
		}
		
		utilisateur.setPseudo(request.getParameter("spseudo"));
		utilisateur.setNom(request.getParameter("snom"));
		utilisateur.setPrenom(request.getParameter("sprenom"));
		utilisateur.setEmail(request.getParameter("semail"));
		utilisateur.setTelephone(request.getParameter("stelephone"));
		utilisateur.setCodePostal(request.getParameter("scodePostal"));
		utilisateur.setRue(request.getParameter("srue"));
		utilisateur.setVille(request.getParameter("sville"));
		utilisateur.setMotDePasse(request.getParameter("snouveauMotDePasse"));
		
		
		if (request.getParameter("snouveauMotDePasse").equals(request.getParameter("sconfirmation"))) {
			if (motDePasseSession.equals(request.getParameter("smotDePasseActuel"))) {
				try {
					UtilisateurDAO.modifier(utilisateur, ancienPseudo);
					this.getServletContext().getRequestDispatcher("/encheresCo").forward(request, response);
				} catch (DALException e) {

					erreur = "information déjà renseigné par une autre personne";
					request.setAttribute("erreur", erreur);
					this.getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreur.jsp").forward(request,
							response);
				} catch (SQLException e) {
					erreur = "erreur";
					this.getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreur.jsp").forward(request,
							response);
				}
			} else {
				erreur = "Le mot de passe actuel ne correspond pas au mot de passe de votre profil.";
			}
		} else {
			erreur = "Les deux mots de passes ne correspondent pas";
			request.setAttribute("erreur", erreur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
		}
	}
}
