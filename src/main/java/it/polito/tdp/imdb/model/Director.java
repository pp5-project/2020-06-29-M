package it.polito.tdp.imdb.model;

import java.util.LinkedList;
import java.util.List;

public class Director {
	Integer id;
	String firstName;
	String lastName;
	List<Integer> idAttori;
	List<Integer> movies;
	
	public Director(Integer id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idAttori=new LinkedList<Integer>();
		this.movies=new LinkedList<Integer>();
	}

	public List<Integer> getIdAttori() {
		return idAttori;
	}

	public void setIdAttori(Integer idAttori) {
		this.idAttori.add(idAttori);
	}
	public void setmovies(Integer idAttori) {
		this.movies.add(idAttori);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return id + " - " + firstName + " " + lastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Director other = (Director) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
