/**
 * 
 */
package fr.eni.projet.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.projet.beans.ArticleVendu;
import fr.eni.projet.beans.Retrait;
import fr.eni.projet.utils.DBConnectPool;

/**
 * @author cgoncalv2018
 *
 */
public class RetraitDAO {
	
	private final static String INSERER = "insert into RETRAITS(rue,code_postal,ville,) values (?,?,?);";
	private final static String SELECTBYID = "select rue, code_postal, ville from RETRAIT where no_categorie = ?;";
	
	public static void ajouter(Retrait retrait) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;

		try {
			cnx = DBConnectPool.seConnecter();
			pstmt = cnx.prepareStatement(INSERER);
			pstmt.setString(1, retrait.getRue());
			pstmt.setString(2, retrait.getCode_postal());
			pstmt.setString(3, retrait.getVille());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Probleme - ajouterRetrait - " + e.getMessage());
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
	/**
	 * Méthode qui sert à recupérer <b>un lieu de retrait</b> par <font color=green>un noArticle</font>
	 * @param noArticle
	 * @return un objet de type Retrait nommé lieuRetrait
	 * @throws DALException
	 */
	public static Retrait selectParNoArticle(int noArticle) throws DALException{
		Connection cnx=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Retrait lieuRetrait = null;
		try{
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(SELECTBYID);
			pstmt.setInt(1, noArticle);
			rs=pstmt.executeQuery();
			if (rs.next()){
				lieuRetrait = new Retrait();
				lieuRetrait.setRue(rs.getString("rue"));
				lieuRetrait.setCode_postal(rs.getString("code_postal"));
				lieuRetrait.setVille(rs.getString("ville"));
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
		return lieuRetrait;
	}
}
