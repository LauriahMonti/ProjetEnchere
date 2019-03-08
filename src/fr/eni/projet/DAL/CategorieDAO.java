/**
 * 
 */
package fr.eni.projet.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.eni.projet.beans.Categorie;
import fr.eni.projet.beans.Utilisateur;
import fr.eni.projet.utils.DBConnectPool;

/**
 * Methode DAO pour les objets de type Categorie qui comprend les méthodes :
 * <ul>
 * <li><font color="green">lister()</font> - retourne une ArayList de Categories
 * -</li>
 * <li><font color="green">modifier() </font>- modifie une Categoie déjà placée
 * en base de donée -</li>
 * <li><font color="green">selectParNumero() </font>- selectione une Catégorie
 * de la base de donné par son numéro -</li>
 * </ul>
 * 
 * @author aauffret2018
 */
public class CategorieDAO {

	private final static String LISTER = "select * from CATEGORIES;";
	private final static String INSERER = "insert into CATEGORIES (libelle) values (?);";
	// private final static String SUPPRIMER = "delete from CATEGORIES where libelle =?;";
	// private final static String MODIFIER = "update CATEGORIES set libelle = ?";
	private final static String SELECTBY = "select no_categorie, libelle from CATEGORIES where libelle = ?;";
	private final static String SELECTBYID = "select no_categorie, libelle from CATEGORIES where no_categorie = ?;";

	public static ArrayList<Categorie> lister() throws DALException {
		Connection cnx = null;
		Statement stmt = null;
		ResultSet rs = null;

		ArrayList<Categorie> listeCategorie = new ArrayList<Categorie>();
		try {
			cnx = DBConnectPool.seConnecter();
			stmt = cnx.createStatement();
			rs = stmt.executeQuery(LISTER);
			Categorie categorie;
			while (rs.next()) {
				categorie = new Categorie();
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.getLibelle();
				if (rs.wasNull())
					categorie.setLibelle("inconnu");
				else
					categorie.setLibelle(rs.getString("libelle"));
				listeCategorie.add(categorie);
			}
			
		} catch (SQLException e) {
			throw new DALException("Probleme - listeCategorie -" + e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - FermerConnexion - " + e.getMessage());
			}
		}
		return listeCategorie;
	}

	public static void ajouter(String libelle) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;

		try {
			cnx = DBConnectPool.seConnecter();
			pstmt = cnx.prepareStatement(INSERER);
			pstmt.setString(1, libelle);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Probleme - ajouterCategorie - " + e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - fermerConnexion - " + e.getMessage());
			}
		}
	}

	public static Categorie selectParNumero(int noCategorie) throws DALException{
		Connection cnx=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Categorie categorie = null;
		try{
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(SELECTBY);
			pstmt.setInt(1, noCategorie);
			rs=pstmt.executeQuery();
			if (rs.next()){
				categorie = new Categorie();
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
			}
		}catch (SQLException e){
			throw new DALException ("Probleme - rechercherUtilisateur - " + e.getMessage());
		}finally{
			try{
				if (pstmt!=null) pstmt.close();
				if (cnx!=null) cnx.close();
			} catch (SQLException e){
				throw new DALException ("Probleme - FermerConnexion - " + e.getMessage());
			}
		}
		return categorie;
	}
	public static Categorie selectParId(int noCategorie) throws DALException{
		Connection cnx=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Categorie categorie = null;
		try{
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(SELECTBYID);
			pstmt.setInt(1, noCategorie);
			rs=pstmt.executeQuery();
			if (rs.next()){
				categorie = new Categorie();
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
			}
		}catch (SQLException e){
			throw new DALException ("Probleme - rechercherUtilisateur - " + e.getMessage());
		}finally{
			try{
				if (pstmt!=null) pstmt.close();
				if (cnx!=null) cnx.close();
			} catch (SQLException e){
				throw new DALException ("Probleme - FermerConnexion - " + e.getMessage());
			}
		}
		return categorie;
	}

}
