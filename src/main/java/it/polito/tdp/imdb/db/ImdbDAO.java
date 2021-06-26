package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> getVertici(int year){
		String sql = "SELECT DISTINCT d.id, d.first_name, d.last_name "
				+ "FROM directors d, movies_directors m, movies mo "
				+ "WHERE d.id=m.director_id AND m.movie_id=mo.id AND mo.year=? ";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("d.id"), res.getString("d.first_name"), res.getString("d.last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void listAllDirectors(Map<Integer, Director> idMap){
		String sql = "SELECT * "
				+ "FROM directors ";
				
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!idMap.containsKey(res.getInt("id"))) {
				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				idMap.put(director.getId(), director);
			}
			conn.close();
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}
	
	public void listaArchi(int year, Map<Integer, Director> idMap){
		String sql = "SELECT d.id, r.actor_id "
				+ "FROM directors d, movies_directors m, movies mo, roles r "
				+ "WHERE d.id=m.director_id AND m.movie_id=mo.id AND mo.year=? AND m.movie_id=r.movie_id ";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				idMap.get(res.getInt("d.id")).setIdAttori(res.getInt("r.actor_id"));
				
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public List<Adiacenza> listaFilm(int year, Map<Integer, Director> idMap){
		String sql = "SELECT m1.director_id, m2.director_id, COUNT(r1.actor_id) as peso "
				+ "FROM movies_directors m1, movies_directors m2, roles r1, roles r2, movies mm1, movies mm2 "
				+ "WHERE m1.director_id<m2.director_id AND mm1.year=? AND mm2.year=? AND m1.movie_id=mm1.id  AND m2.movie_id=mm2.id "
				+ "AND r1.movie_id=m1.movie_id "
				+ "AND r2.movie_id=m2.movie_id AND r1.actor_id=r2.actor_id "
				+ "GROUP BY m1.director_id, m2.director_id ";
		List<Adiacenza> lista=new LinkedList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			st.setInt(2, year);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				lista.add(new Adiacenza(idMap.get(res.getInt("m1.director_id")),idMap.get(res.getInt("m2.director_id")),res.getInt("peso")));
							}
			conn.close();
			return lista;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		}
	}
	
	
	
}
