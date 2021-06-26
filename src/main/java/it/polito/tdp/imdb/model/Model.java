package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


import it.polito.tdp.imdb.db.ImdbDAO;


public class Model {
	private int pesoMigliore = 0;
	private Map<Integer,Director> idMap;
	private ImdbDAO dao;
	private SimpleWeightedGraph<Director, DefaultWeightedEdge> grafo;
	private List<Director> percorsoBest;
	
	public Model(){
		dao=new ImdbDAO();
		idMap=new HashMap<Integer,Director>();
		dao.listAllDirectors(idMap);	
		
	}
	
	
	
	public void CreaGrafo(int year) {
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		//aggiungo vertici
		Graphs.addAllVertices(grafo,dao.getVertici(year));
		//aggiungo archi
		for(Adiacenza a:dao.listaFilm(year, idMap)) { 
			Graphs.addEdge(grafo, a.uno, a.due, a.peso);
		}
	}
		
		
		
		
		
		/*for(Director d:dao.getVertici(year)) {
			for(Director d2:dao.getVertici(year)) {
				int count=0;
				for(Integer i:d.idAttori) {
					for(Integer e:d2.idAttori) {
						if(i==e)
						count++;
					}
				}
				if(count>0) {
					Graphs.addEdge(grafo, d, d2, count);
				}
			}
		}
		for(Director d:dao.getVertici(year)) {
			for(Director d2:dao.getVertici(year)) {
				int count=0;
				for(Integer i:d.movies) {
					for(Integer e:d.movies) {
						if(i==e)
							count++;
					}
				}
				if(count>0) {
					Graphs.addEdge(grafo, d, d2, count);
				}
			}
		}
		
		
	}*/
	
	public int NVert() {
		return this.grafo.vertexSet().size();
	}
	
	public int NARCHI() {
		return this.grafo.edgeSet().size();
	}
	
	public Set<Director> GetVertici(){
		return this.grafo.vertexSet();
	}
	public List<Adiacenti> adiacenti(Director d, int year){
		List<Director> vicini=Graphs.neighborListOf(grafo, d);
		List<Adiacenti> adiacenti=new LinkedList<Adiacenti>();
		for(Director b:vicini) {
				adiacenti.add(new Adiacenti(b,(int) this.grafo.getEdgeWeight(this.grafo.getEdge(d, b))));
				}
			
		Collections.sort(adiacenti);
		return adiacenti;
	}
	
	public List<Director> percorso(Director partenza, int N) {
		this.percorsoBest=new ArrayList<>();
		
		List<Director> parziale=new ArrayList<Director>();
		parziale.add(partenza);
		cerca(parziale,0,N);
		return this.percorsoBest;
	}
	
	private void cerca(List<Director> parziale, int sommaAttori, int N) {
		//caso terminale: ho trovato l'arrivo
		if(parziale.size()>this.percorsoBest.size()&& this.pesoMigliore<sommaAttori && sommaAttori<=N) {
			this.percorsoBest = new ArrayList<>(parziale);
			this.pesoMigliore= sommaAttori;
		}
		
		//generazione dei percorsi
    for(Director vicino: Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			
			int pesoDaAggiungere= (int)grafo.getEdgeWeight(grafo.getEdge(vicino,parziale.get(parziale.size()-1)));
			
			if(!parziale.contains(vicino)&& sommaAttori+pesoDaAggiungere<N) {
				parziale.add(vicino);
				sommaAttori+=pesoDaAggiungere;
				
				cerca(parziale,sommaAttori, N);
				
				sommaAttori-=pesoDaAggiungere;
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	public int getAttoriCondivisi() {
		return this.pesoMigliore;
	}
	
	}

	


