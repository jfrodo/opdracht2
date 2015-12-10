package model.domein;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Deze klasse representeert een enquete. Een enquete bevat een titel, een korte
 * inleidende koptekst en lijst vragen. Elke enquete krijgt bij opslaan in de
 * database een uniek nummer.
 */
public class Enquete implements Serializable {
	private int enquetenr = 0;

	private String titel = "";
	private String koptekst = "";
	private ArrayList<Vraag> vragenlijst = new ArrayList<Vraag>();

	/**
	 * Constructor voor nieuwe enquete die nog niet in de database zit. Er wordt
	 * gecontroleerd of titel en koptkekst niet te lang zijn.
	 * 
	 * @param titel
	 *            de titel van de enquete
	 * @param koptekst
	 *            de tekst die boven de eerste vraag getoond moet worden
	 * @throws EnqueteException
	 *             als titel of koptekst te lang zijn
	 */
	public Enquete(String titel, String koptekst) throws EnqueteException {
		boolean titelTeLang = false;
		;
		boolean kopTeLang = false;
		if (titel.length() > Const.MAXTITELLENGTE) {
			titelTeLang = true;
		}
		if (koptekst.length() > Const.MAXKOPTEKSTLENGTE) {
			kopTeLang = true;
		}
		if (!titelTeLang && !kopTeLang) {
			this.titel = titel;
			this.koptekst = koptekst;
		} else {
			throw new EnqueteException(Const.titelOfKopTeLang(titelTeLang,
					kopTeLang));
		}
	}

	/**
	 * Constructor voor enquete ingelezen uit de database
	 * 
	 * @param enquetenr
	 *            de sleutel van de enquete in de database
	 * @param titel
	 *            de titel van de enquete
	 * @param koptekst
	 *            de tekst die boven de eerste vraag getoond moet worden
	 */
	public Enquete(int enquetenr, String titel, String koptekst) {
		this.titel = titel;
		this.koptekst = koptekst;
		this.enquetenr = enquetenr;
	}

	/**
	 * Geeft het nummer van deze enquete
	 */
	public int getEnquetenr() {
		return enquetenr;
	}

	/**
	 * Geeft de titel van deze enquete
	 */
	public String getTitel() {
		return titel;
	}

	/**
	 * Geeft de koptekst van deze enquete
	 */
	public String getKoptekst() {
		return koptekst;
	}

	/**
	 * Geeft de lijst met vragen van deze enquete
	 */
	public ArrayList<Vraag> getVragenlijst() {
		return vragenlijst;
	}

	/**
	 * Geeft het aantal vragen in de enquete
	 */
	public int getAantalVragen() {
		return vragenlijst.size();
	}

	/**
	 * Voegt een vraag aan de vragenlijst van de enquete toe
	 * 
	 * @param vraag
	 *            de toe te voegen vraag
	 */
	public void addVraag(Vraag vraag) {
		vragenlijst.add(vraag);
	}

	public void setEnquetenr(int enquetenr) {
		this.enquetenr = enquetenr;
	}

}
