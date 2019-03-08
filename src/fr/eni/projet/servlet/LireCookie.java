package fr.eni.projet.servlet;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LireCookie
 */
@WebServlet("/LireCookie")
public class LireCookie extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LireCookie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String derniereConnexion = getCookieValue( request,ConnexionUtilisateurs.COOKIE_DERNIERE_CONNEXION );
		request.setAttribute("sidentifiant",derniereConnexion );
		this.getServletContext().getRequestDispatcher("/connexion").forward( request, response );
	}

	private static String getCookieValue( HttpServletRequest request, String nom ) {
        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( Cookie cookie : cookies ) {                    	
            	cookie.setMaxAge(1);
            	
                if ( cookie != null && nom.equals( cookie.getName() ) ) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
