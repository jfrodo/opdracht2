package model.data;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Singletonklasse die verbinding maakt met de database en
 * verbindingen opvraagt en vrijgeeft.
 * @author Medewerker OUNL
 *
 */
public class ConnectionPool {
  // constante voor de JNDI databasenaam
  private static final String DBNAAM = "java:/comp/env/jdbc/WebEnquetes";
  
  // de enige instantie van deze klasse
  private static ConnectionPool pool = null;
  
  // de datasource
  private DataSource dataSource = null; 

  /**
   * De (private) constructor maakt verbinding met de database.
   * @throws DatabaseException
   */
  private ConnectionPool() throws DatabaseException {
    try {
      Context initCtx = new InitialContext() ;
      dataSource = (DataSource) initCtx.lookup(DBNAAM);
    }
    catch (NamingException e) {
      e.printStackTrace();
      throw new DatabaseException(
          "Door een systeemfout bij het openen van de database " +
          "kan niet voldaan worden aan dit verzoek");
    }
  }
  
  /**
   * Geeft de enige instantie terug
   * @return  de enige instantie van ConnectionPool
   * @throws DatabaseException
   */
  static synchronized ConnectionPool getInstance() throws DatabaseException {
    if (pool == null) {
      pool = new ConnectionPool();
    }
    return pool;
  }
  
  /**
   * Vraagt om een beschikbare connectie uit de pool die wordt
   * bijgehouden door de datasource
   * @return  een connectie met de datasource
   * @throws DatabaseException
   */
  Connection getConnection() throws DatabaseException {
    try {
      return dataSource.getConnection();
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new DatabaseException(
          "Door een systeemfout bij het verbinden met de database " +
          "kan niet voldaan worden aan dit verzoek");
    }
  }
  
  /**
   * Geeft een connectie terug aan de pool
   * @param c  de connectie die vrijgegeven kan worden
   * @throws DatabaseException
   */
  void freeConnection(Connection c) throws DatabaseException {
    try {
      c.close();
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new DatabaseException(
          "Door een systeemfout bij het sluiten van een databaseverbinding " +
          "kan niet voldaan worden aan dit verzoek");
    }
  }
    
}
