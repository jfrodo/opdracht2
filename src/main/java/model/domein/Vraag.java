package model.domein;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Representeert een vraag uit een enquete.
 * Het vraagnummer is niet uniek; een vraag hoort altijd bij 
 * één specifieke enquete.
 */
public class Vraag implements Serializable {
  
  private int vraagnummer = 0;
  private int vraagtype = 0;  // OPEN, RADIO of LIJST
  private String tekst = null;
  private ArrayList<String> alternatieven = new ArrayList<String>();
  
  /**
   * Constructor voor nieuwe vraag
   * @param vraagnummer  het nummer van de vraag binnen de enquete waar deze bijhoort
   * @param vraagtype  het type van de vraag (Const.OPEN, Const.RADIO of Const.LIJST)
   * @param tekst  de tekst van de vraag
   * @throws EnqueteException  als de vraagtekst leeg is of als deze te lang is
   */
  public Vraag(int vraagnummer, int vraagtype, String tekst) throws EnqueteException {
    if (tekst.equals("")) {
      throw new EnqueteException(Const.legeVraagtekst(vraagnummer));
    }
    else if (tekst.length() > Const.MAXVRAAGLENGTE) {
      throw new EnqueteException(Const.teLangeVraagtekst(vraagnummer));
    } 
    this.vraagnummer = vraagnummer;
    this.vraagtype = vraagtype;
    this.tekst = tekst;
  }
  
  /**
   * Geeft het nummer van de vraag binnen de enquete
   */
  public int getVraagnummer() {
    return vraagnummer;
  }
  
  /**
   * Geeft het type van de vraag
   */
  public int getVraagtype() {
    return vraagtype;
  }
  
  /**
   * Geeft de tekst van de vraag
   */
  public String getTekst() {
    return tekst;
  }
  
  /**
   * Geeft de lijst met alternatieven (deze kan leeg zijn)
   */
  public ArrayList<String> getAlternatieven() {
    return alternatieven;
  }
  
  /**
   * Geeft het aantal alternatieven
   */
  public int getAantalAlternatieven() {
    return alternatieven.size();
  }
  
  /**
   * Voegt een alternatief toe aan deze vraag
   * @param tekst  de tekst van het alternatief
   * @throws EnqueteException  als het aantal alternatieven te groot wordt
   *         voor het type vraag of als de tekst te lang is 
   */
  public void voegAlternatiefToe(String tekst) throws EnqueteException {
    if (getAantalAlternatieven() == Const.MAXALTERNATIEVEN[vraagtype]) {
      throw new EnqueteException(Const.teveelAlternatieven(vraagtype));
    }
    int altNummer = getAantalAlternatieven() + 1;
    int maxLengte = Const.MAXALTERNATIEFLENGTE[vraagtype];
    if (tekst.length() > maxLengte) {
      throw new EnqueteException(Const.teLangAlternatief(altNummer, maxLengte));
    }
    alternatieven.add(tekst);
  } 
}
