package it.prova.gestioneappartamento.models;

import java.util.Date;

public class Appartamento {
	// TODO
	private long id;
	private String quartiere;
	private int metriQuadrati;
	private int prezzo;
	private Date dataCreazione;

	public Appartamento() {
		super();
	}

	public Appartamento(String quartiere, int metriQuadrati, int prezzo, Date dataCreazione) {
		super();
		this.quartiere = quartiere;
		this.metriQuadrati = metriQuadrati;
		this.prezzo = prezzo;
		this.dataCreazione = dataCreazione;
	}

	public Appartamento(long id, String quartiere, int metriQuadrati, int prezzo, Date dataCreazione) {
		super();
		this.id = id;
		this.quartiere = quartiere;
		this.metriQuadrati = metriQuadrati;
		this.prezzo = prezzo;
		this.dataCreazione = dataCreazione;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuartiere() {
		return quartiere;
	}

	public void setQuartiere(String quartiere) {
		this.quartiere = quartiere;
	}

	public int getMetriQuadrati() {
		return metriQuadrati;
	}

	public void setMetriQuadrati(int metriQuadrati) {
		this.metriQuadrati = metriQuadrati;
	}

	public int getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

}
