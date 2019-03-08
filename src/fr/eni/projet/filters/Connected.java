package fr.eni.projet.filters;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projet.beans.Utilisateur;

/**
 * Servlet Filter implementation class Connected
 * La page d'accueil
 * Filtes sur Encheres - vendre un article -Mon profil -Deconnexion
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE
		}
					, urlPatterns = { "/GestionDeconnexion", "/encheresCo", "/creationEnchere", "/encherir", "/detailVente"})
public class Connected implements Filter {

    /**
     * Default constructor. 
     */
    public Connected() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);
		if (session !=null  && session.getAttribute("utilisateurConnecte")!=null) {
			chain.doFilter(request, response);
		}else {
			RequestDispatcher navig = request.getRequestDispatcher("/encheres");
			navig.forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
