package fr.eni.projet.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe Utilisateur,
 * comprenant getters et setters.
 * @author ecaniac2018
 *
 */
public class Utilisateur implements Serializable{

	/**
	 * ID par defaut
	 */
	private static final long serialVersionUID = 1L;
	private int noUtilisateur,credit;
	private String pseudo,nom,prenom,email,telephone,rue,codePostal,ville,motDePasse;
	private boolean administrateur;
	private ArrayList<Enchere> encherisseur = null;
	private ArrayList<ArticleVendu> vendeur = null;
	private ArrayList<ArticleVendu> acheteur = null;

	public Utilisateur () {}
	
	public Utilisateur(int noUtilisateur, int credit, String pseudo, String nom, String prenom, String email,
			String telephone, String rue, String codePostal, String ville, String motDePasse, boolean administrateur) {
		this.noUtilisateur = noUtilisateur;
		this.credit = credit;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.administrateur = administrateur;
	}

	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}


	
	
	
	
}
