package model.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.domein.*;

/**
 * Singletonklasse die verbinding maakt met de database. Er zijn methoden om
 * <ul>
 * <li>een enquete weg te schrijven</li>
 * <li>een enquete in te lezen, gegeven een sleutel</li>
 * </ul>
 * 
 * @author hjp
 * 
 */
public final class DAO {

	public static final String ERROR2 = "Door een systeemfout kon uw enquete helaas niet worden opgeslagen; er wordt een rollback uitgevoerd";
	public static final String ERROR1 = "Door een systeemfout is de enquete helaas niet beschikbaar. "
			+ "Probeer het later opnieuw.";

	// constanten voor de gebruikte SQL-opdrachten
	private static final String SQLADDENQUETE = "insert into Enquete (titel, koptekst) values (?,?)";
	private static final String SQLADDVRAAG = "insert into Vraag (enquete, volgnr, vraagtype, tekst) values (?,?,?,?)";
	private static final String SQLADDALTERNATIEF = "insert into Alternatief (vraag, volgnr, tekst) values (?,?,?)";
	private static final String SQLGETENQUETE = "select E.nr,E.titel,E.koptekst,V.volgnr,V.vraagtype,V.tekst,A.volgnr,A.tekst"
			+ " from Enquete E inner join Vraag V ON V.enquete = E.nr left outer join Alternatief A ON A.vraag = V.nr "
			+ " where E.nr = ?";

	private static DAO instance = null;
	
	/**
	 * Haalt de enige instantie op
	 * 
	 * @return de enige instantie van DAO
	 */
	public static synchronized DAO getInstance() {
		if (instance == null) {
			instance = new DAO();
		}
		return instance;
	}

	/**
	 * Private constructor
	 */
	private DAO() {
	}

	
	
	/**
	 * Schrijft een enquete naar de database. Indien er een fout optreedt
	 * worden de wijzigingen in de database ongedaan gemaakt
	 * 
	 * @param enquete
	 *            de toe te voegen enquete
	 * @throws DatabaseException
	 */
	public int schrijfEnquete(Enquete enquete) throws SQLException,
			DatabaseException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection con = pool.getConnection();
		PreparedStatement prepAddEnquete = null;
		PreparedStatement prepAddVraag = null;
		PreparedStatement prepAddAlternatief = null;
		try {
			con.setAutoCommit(false); // begin transaction
			prepAddEnquete = con.prepareStatement(SQLADDENQUETE,
					PreparedStatement.RETURN_GENERATED_KEYS);
			prepAddVraag = con.prepareStatement(SQLADDVRAAG,
					PreparedStatement.RETURN_GENERATED_KEYS);
			prepAddAlternatief = con.prepareStatement(SQLADDALTERNATIEF,
					PreparedStatement.RETURN_GENERATED_KEYS);
			int enquetenr = schrijfEnquete(prepAddEnquete, enquete); 
			prepAddVraag.setInt(1, enquetenr);
			for (Vraag vraag : enquete.getVragenlijst()) {
				int vraagnr = schrijfVraag(prepAddVraag, vraag);		
				prepAddAlternatief.setInt(1, vraagnr);
				for (int altnr = 1; altnr <= vraag.getAantalAlternatieven(); altnr++) {
					schrijfAlternatief(prepAddAlternatief, altnr, vraag
							.getAlternatieven().get(altnr - 1));
				}
			}
			con.commit(); //commit
			return enquetenr;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if (con != null) {
					con.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DatabaseException(
					ERROR2);
		} finally {
			con.setAutoCommit(true);
			pool.freeConnection(con);
			if (prepAddEnquete !=null){
			  prepAddEnquete.close();
			} 
			if (prepAddVraag !=null){
			  prepAddVraag.close();
			}
			if (prepAddAlternatief !=null){
			  prepAddAlternatief.close();
			}
		}
	}

	
	/**
	 * Leest de enquete met het gegeven enquetenummer in uit de database
	 * 
	 * @param enquetenr
	 *            het nummer van de enquete
	 * @return de gezochte enquete of null als die er niet is
	 * @throws DatabaseException
	 */
	public Enquete leesEnquete(int enquetenr) throws DatabaseException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection con = pool.getConnection();
		try {
			Enquete enquete = null;
			Vraag vraag = null;
			int volgnr = 0, type = 0;
			PreparedStatement prep = con.prepareStatement(SQLGETENQUETE,
					PreparedStatement.RETURN_GENERATED_KEYS);
			prep.setInt(1, enquetenr);
			ResultSet res = prep.executeQuery();
			while (res.next()) {
				if (enquete == null) {
					enquete = new Enquete(res.getInt("E.nr"),
							res.getString("E.titel"),
							res.getString("E.koptekst"));
					volgnr = 0;
				}
				if (res.getInt("V.volgnr") != volgnr) { // nieuwe vraag
					volgnr = res.getInt("V.volgnr");
					type = res.getInt("V.vraagtype");
					vraag = new Vraag(volgnr, type, res.getString("V.tekst"));
					enquete.addVraag(vraag);
				}
				if (type != Const.OPEN) { //nieuw alternatief
					vraag.voegAlternatiefToe(res.getString("A.tekst"));
				}
			}
			return enquete;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(
					ERROR1);
		} finally {
			pool.freeConnection(con);
		}

	}

	// Private hulpmethoden voor het wegschrijven van een enquete

	/**
	 * Hulpmethode; maakt een nieuw record in tabel enquete
	 * 
	 * @param prep
	 *            het benodigde preparedstatement
	 * @param enquete
	 *            de enquete die wordt weggeschreven
	 * @return de sleutel van de nieuwe rij (gegenereerd door de database)
	 * @throws SQLException
	 */
	private int schrijfEnquete(PreparedStatement prep, Enquete enquete)
			throws SQLException {
		prep.setString(1, enquete.getTitel());
		prep.setString(2, enquete.getKoptekst());
		prep.executeUpdate();
		return getAutoKey(prep);
	}

	/**
	 * Hulpmethode; schrijft een vraag naar de database
	 * @param prep
	 *        preparedstatement
	 * @param vraag
	 *            de vraag die wordt weggeschreven
	 * @return de sleutel van de vraag (gegenereerd door de database)
	 * @throws SQLException
	 */
	private int schrijfVraag(PreparedStatement prep, Vraag vraag)
			throws SQLException {
		prep.setInt(2, vraag.getVraagnummer());
		prep.setInt(3, vraag.getVraagtype());
		prep.setString(4, vraag.getTekst());
		prep.executeUpdate();
		return getAutoKey(prep);
	}

	/**
	 * Hulpemethode; schrijft een alternatief naar de database
	 * 
	 * @param prep
	 *            het benodigde preparestatemenet
	 * @param altnr
	 *            het nummer binnen de vraag van dit alternatief (1..10)
	 * @param alt
	 *            de tekst voor dit alternatief
	 * @return de sleutel van het alternatief (gegenereerd door de database)
	 * @throws SQLException
	 */
	private void schrijfAlternatief(PreparedStatement prep, int altnr,
			String alt) throws SQLException {
		prep.setInt(2, altnr);
		prep.setString(3, alt);
		prep.executeUpdate();
	}

	/**
	 * Hulpmethode; haalt een gegenereerde sleutel op 
	 * 
	 * @param ps
	 *            het statement
	 * @return de gegenereerde sleutel
	 * @throws SQLException
	 */
	private int getAutoKey(PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		int sleutel = 0;
		if (rs.next()) {
			sleutel = rs.getInt(1);
		}
		return sleutel;
	}

	

	
}
