package fr.eni.projet.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.DAL.ArticleVenduDAO;
import fr.eni.projet.DAL.CategorieDAO;
import fr.eni.projet.DAL.DALException;
import fr.eni.projet.DAL.RetraitDAO;
import fr.eni.projet.beans.ArticleVendu;
import fr.eni.projet.beans.Categorie;
import fr.eni.projet.beans.Retrait;

/**
 * Servlet implementation class CreationEnchere
 */
@WebServlet("/creationEnchere")
public class CreationEnchere extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreationEnchere() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {	
			ArrayList<Categorie> listeCategorie = CategorieDAO.lister();
			request.setAttribute("listeCategorie", listeCategorie);



		} catch (DALException e) {
			getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreur.jsp").forward(request, response);
		}
		RequestDispatcher navig = request.getRequestDispatcher("/WEB-INF/jsp/nouvelleEnchere.jsp");
		navig.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		


		String rue = null;
		String codePostal = null;
		String ville = null;
		String message;
		String erreur = null;
		ArticleVendu enchere = new ArticleVendu();
		Retrait retrait = new Retrait();
		
		//Vérification avant insertion
		
		if (request.getParameter( "sarticle" ).trim().isEmpty() ) {
			message = "Erreur - Veuillez renseigner un article.";
			request.setAttribute("erreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/nouvelleEnchere.jsp" ).forward( request, response );
		}
		if (request.getParameter( "sdescription" ).trim().isEmpty() ) {
			message = "Erreur - Veuillez renseigner une description.";
			request.setAttribute("erreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/nouvelleEnchere.jsp" ).forward( request, response );
		}

		if (request.getParameter( "scategorie" ).trim().isEmpty()) {
			message = "Erreur - Veuillez renseigner une catégorie.";
			request.setAttribute("erreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/nouvelleEnchere.jsp" ).forward( request, response );
		}
		if (request.getParameter( "smiseAPrix" ).trim().isEmpty() ) {
			message = "Erreur - Veuillez renseigner une mise à prix.";
			request.setAttribute("erreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/nouvelleEnchere.jsp" ).forward( request, response );
		}

		if (request.getParameter( "sdebutEnchere" ).trim().isEmpty()) {
			message = "Erreur - Veuillez renseigner une date de début d'enchère.";
			request.setAttribute("erreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/nouvelleEnchere.jsp" ).forward( request, response );
		}
		if (request.getParameter( "sfinEnchere" ).trim().isEmpty() ) {
			message = "Erreur - Veuillez renseigner une date de fin d'enchère.";
			request.setAttribute("erreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/nouvelleEnchere.jsp" ).forward( request, response );
		}
		/*if (request.getParameter( "sdebutEnchere" ).compareTo(request.getParameter( "sfinEnchere" ))>0){
			message = "Erreur - La date de fin d'enchère doit être supérieure à la date de début d'enchère.";
			request.setAttribute("erreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/nouvelleEnchere.jsp" ).forward( request, response );
		}
		if (request.getParameter( "sdebutEnchere" ).compareTo(request.getParameter( "sfinEnchere" ))==0){
			message = "Erreur - La date de fin d'enchère doit être supérieure à la date de début d'enchère.";
			request.setAttribute("erreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/nouvelleEnchere.jsp" ).forward( request, response );
		}*/
		if (request.getParameter( "srue" ).trim().isEmpty()) {
			message = "Erreur - Veuillez renseigner une rue.";
			request.setAttribute("erreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/nouvelleEnchere.jsp" ).forward( request, response );
		}
		if (request.getParameter( "scodePostal" ).trim().isEmpty() ) {
			message = "Erreur - Veuillez renseigner un code postal.";
			request.setAttribute("erreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/nouvelleEnchere.jsp" ).forward( request, response );
		}

		if (request.getParameter( "sville" ).trim().isEmpty()) {
			message = "Erreur - Veuillez renseigner une ville.";
			request.setAttribute("erreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/nouvelleEnchere.jsp" ).forward( request, response );
		}
		 /*
         * Récupération des données saisies, envoyées en tant que paramètres de
         * la requête POST générée à la validation du formulaire
         */
		enchere.setNomArticle(request.getParameter( "sarticle"));
		enchere.setDescription(request.getParameter( "sdescription" ));
		//Cast des paramètres en Integer afin de parser mon int en String
		enchere.setNoCategorie(Integer.parseInt(request.getParameter( "scategorie" )));
		enchere.setMiseAPrix(Integer.parseInt(request.getParameter("smiseAPrix")));
		enchere.setPrixVente(Integer.parseInt(request.getParameter("smiseAPrix")));
		/*
		 * recupération de la date dans la jsp au format année-mois-jour pour chrome
		 * si probleme avec internet explorer et firefox
		 * demander a l'utilisateur d'inserer au format "yyyy-mm-dd" 
		 * puis parse la chaine recuperer en date pour l'inserer dans le bean ArticleVendu
		 * setDateDebutEncheres
		 */
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sDebutenchere = request.getParameter("sdebutEnchere");
		Date debutEnchere;
		try {
			debutEnchere = sdf.parse(sDebutenchere);
			enchere.setDateDebutEncheres(debutEnchere);
		} catch (ParseException e1) {
			e1.getMessage();
		}
		
		/*
		 * meme chose pour setDateFinEnchere
		 */
		String sFinEnchere = request.getParameter("sfinEnchere");
		Date finEnchere;
		try {
			finEnchere = sdf.parse(sFinEnchere);
			enchere.setDateFinEncheres(finEnchere);
		} catch (ParseException e1) {
			e1.getMessage();
		}
		/* 
		 * Appel vers ArticleVenduDAO et sa methode ajouter
		 * 
		 * */
		
		try {
			ArticleVenduDAO.ajouter(enchere);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* 
		 * Récupération des données de lieu de retrait
		 * 
		 * */
		retrait.setRue(request.getParameter("srue"));
		retrait.setCode_postal(request.getParameter("scodePostal"));
		retrait.setVille(request.getParameter("sville"));
		
		/* 
		 * Appel vers RetraitDAO et sa methode ajouter
		 * 
		 * */
		
		try {
			RetraitDAO.ajouter(retrait);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
