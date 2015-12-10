package model.domein;

public class Const {
  /**
   * Constante voor open vragen (invuller formuleert
   * zelf antwoord)
   */
  public static final int OPEN = 0;
  
  /**
   * Constante voor meerkeuzevragen waarbij de alternatieven
   * naast elkaar worden weergegeven met radiobuttons voor de keuze
   */
  public static final int RADIO = 1;
  
  /**
   * Constante voor meerkeuzevragen waarbij de alternatieven
   * worden gekozen uit een drop-down lijst. 
   */
  public static final int LIJST = 2;
  
  /**
   * Constante voor melding dat de ingevulde enquete is ontvangen
   * (dat de resultaten niet verwerkt worden, staat er niet bij).
   */
  public static final String ENQUETEVERWERKT = 
    "Hartelijk dank voor uw medewerking.";
  
  /**
   * Constante voor melding dat de opgevraagde enquete niet bestaat
   */
  public static String enqueteBestaatNiet(int enquetenr) {
    return "De link die u heeft ontvangen, is onjuist. " +
    "Er bestaat geen enquete met nummer " + enquetenr;
  }

  /* 
   * De overige constanten en meldingen zijn alleen binnen de
   * package beschikbaar 
   */
  
  // Constanten voor de maximale lengte van verschillende velden
  static final int MAXVRAAGLENGTE = 100;
  static final int MAXTITELLENGTE = 40;
  static final int MAXKOPTEKSTLENGTE = 250;
  
  // Constanten voor het maximale aantal alternatieven per vraagtype
  static final int[] MAXALTERNATIEVEN = {0, 5, 10};
  
  // Constanten voor de maximale lengte van een antwoord per vraagtype
  // Bij Rdaio buttons moeten de antwoorden kort zijn; anders passen ze
  // niet naast elkaar op een regel.
  static final int[] MAXALTERNATIEFLENGTE = {0, 20, 100};
  
    
  /**
   * Geeft tekst voor foutmelding voor het geval de titel  
   * van een enqute of de koptekst of beide te lang zijn.  
   * Wordt alleen aangeroepen als minstens een van beide te lang is.
   * @param titelTeLang  true als de titel te lang is
   * @param kopTeLang  true als de koptekst te lang is.
   * @return  een geschikte foutmelding
   */
  static String titelOfKopTeLang(boolean titelTeLang, boolean kopTeLang) {
    if (titelTeLang && kopTeLang) {
      return "Zowel de titel (max " + MAXTITELLENGTE + ") als de koptekst (max " +
      + MAXKOPTEKSTLENGTE + ") van deze enquete zijn te lang";
    }
    else if (titelTeLang) {
      return "De titel van deze enquete (max " + MAXTITELLENGTE + ") is te lang.";
    }
    else if (kopTeLang) {
      return "De koptekst van deze enquete (max " +
      + MAXKOPTEKSTLENGTE + ") is te lang."; 
    }
    else {
      // dit geval komt niet voor
      return ""; 
    }
  }
  
  /**
   * Geeft tekst voor foutmelding als een vraag te lang is
   * @param vraagnummer  het nummer van de vraag
   * @return  een foutmelding
   */
  static String teLangeVraagtekst(int vraagnummer) {
    return "De tekst van vraag " + vraagnummer + " is te lang (max " +
      + MAXVRAAGLENGTE + ")";
  }
  
  /**
   * Geeft tekst voor foutmelding als een vraag geen tekst heeft
   * @param vraagnummer
   * @return  een foutmelding
   */
  static String legeVraagtekst(int vraagnummer) {
    return "Deze tekst van vraag " + vraagnummer + " is leeg. " +
           "NB: Vink \"laatste vraag\" aan als u alle vragen heeft ingevoerd";
  }
  
  
  /**
   * Geeft tekst voor foutmelding als het aantal ingevoerde 
   * alternatieven te groot is
   * @param vraagtype  het type vraag
   * @return  een foutmelding die past bij het type vraag
   */
  static String teveelAlternatieven(int vraagtype) {
    if (vraagtype == Const.OPEN) {
      return "Bij een open vraag kunnen geen alternatieven worden ingevuld";
    }
    else if (vraagtype == Const.RADIO) {
      return "Een meerkeuzevraag met radio-buttons kan maximaal "
             + Const.MAXALTERNATIEVEN[vraagtype] + " alternatieven hebben";
    }
    else {
      return "Een meerkeuze vraag met een lijst kan maximaal "
      + Const.MAXALTERNATIEVEN[vraagtype] + " alternatieven hebben";
    }
  }

  /**
   * Geeft tekst voor foutmelding als een alternatief te lang is
   * @param altnummer  het nummer van het alternatief
   * @return  een foutmelding
   */
  static String teLangAlternatief(int altnummer, int maxLengte) {
    return "De tekst van het " + altnummer + 
           "-e alternatief is te lang (max " + maxLengte + ")";
  }
  

}
