package it.polito.tdp.imdb.model;

public class Adiacenti implements Comparable<Adiacenti>{
	Director uno;
	int adiacenti;
	public Adiacenti(Director uno, int adiacenti) {
		super();
		this.uno = uno;
		this.adiacenti = adiacenti;
	}
	@Override
	public int compareTo(Adiacenti arg0) {
		// TODO Auto-generated method stub
		return -(this.adiacenti-arg0.adiacenti);
	}
	@Override
	public String toString() {
		return "Adiacenti [uno=" + uno + ", adiacenti=" + adiacenti + "]";
	}
	
	
}
