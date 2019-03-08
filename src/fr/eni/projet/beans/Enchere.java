package fr.eni.projet.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe Enchere,
 * comprenant getters et setters.
 * @author cgoncalves2018
 *
 */

public class Enchere implements Serializable{
	
	/**
	 * ID par défaut
	 */
	private static final long serialVersionUID = 1L;
	private Date dateEnchere;
	private int montantEnchere;
	private Utilisateur encherisseur;
	
	//Constructeur par défaut
	public Enchere() {
		super();
	}

	//Constructeur surchargé
	public Enchere(Date dateEnchere, int montantEnchere, Utilisateur encherisseur) {
		super();
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.encherisseur = encherisseur;
	}

	//Getters et Setters
	public Date getDateEnchere() {
		return dateEnchere;
	}


	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}


	public int getMontantEnchere() {
		return montantEnchere;
	}


	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}


	public Utilisateur getEncherisseur() {
		return encherisseur;
	}


	public void setEncherisseur(Utilisateur encherisseur) {
		this.encherisseur = encherisseur;
	}

	//Méthode toString
	@Override
	public String toString() {
		return String.format("Enchere [dateEnchere=%s, montantEnchere=%s, encherisseur=%s]", dateEnchere,
				montantEnchere, encherisseur);
	}
	
	

}
