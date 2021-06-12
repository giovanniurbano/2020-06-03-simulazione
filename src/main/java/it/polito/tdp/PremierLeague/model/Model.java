package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private PremierLeagueDAO dao;
	private List<Player> vertici;
	private Map<Integer, Player> idMap;
	private Graph<Player, DefaultWeightedEdge> grafo;
	private Player top;
	
	public Model() {
		this.dao = new PremierLeagueDAO();
	}

	public String creaGrafo(Double minGoals) {
		this.grafo = new SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//vertici
		this.idMap = new HashMap<Integer, Player>();
		this.vertici = this.dao.getPlayersByAvgGoals(minGoals);
		Graphs.addAllVertices(this.grafo, this.vertici);
		for(Player p : this.vertici)
			this.idMap.put(p.getPlayerID(), p);
		
		//archi
		this.setArchi(minGoals);
		
		return String.format("Grafo creato con %d vertici e %d archi\n", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
	}
	
	private void setArchi(Double minGoals) {
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(minGoals, this.idMap);
		List<Adiacenza> nuove = new ArrayList<Adiacenza>();
		for(Adiacenza a1 : adiacenze) {
			for(Adiacenza a2: adiacenze) {
				if(a1.getP1().getPlayerID() > a2.getP1().getPlayerID() && a2.getP2().getPlayerID() > a2.getP2().getPlayerID())
					if(a1.getP1().equals(a2.getP2()) && a1.getP2().equals(a2.getP1())) {
						Adiacenza nuova = new Adiacenza(a1.getP1(), a1.getP2(), (a1.getPeso()-a2.getPeso()));
						nuove.add(nuova);
					}
			}
			nuove.add(a1);
		}
		
		for(Adiacenza a : nuove) {
			if(!this.grafo.containsEdge(a.getP1(), a.getP2()) && !this.grafo.containsEdge(a.getP2(), a.getP1())) {
				if(a.getPeso() > 0) {
					//da p1 a p2
					Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(), a.getPeso());
				}
				else if(a.getPeso() < 0) {
					//da p2 a p1
					Graphs.addEdgeWithVertices(this.grafo, a.getP2(), a.getP1(), (a.getPeso()*-1));
				}
			}
		}
	}
	
	public List<Adiacenza> getTopPlayerList() {
		this.top = null;
		Player best = null;
		int max = 0;
		for(Player p : this.grafo.vertexSet()) {
			if(this.grafo.outDegreeOf(p) > max) {
				max = this.grafo.outDegreeOf(p);
				best = p;
			}
		}
		
		this.top = best;
		
		List<Adiacenza> result =  new ArrayList<>();
		for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(top)) {
			result.add(new Adiacenza(top, Graphs.getOppositeVertex(this.grafo, e, top), this.grafo.getEdgeWeight(e)));
		}
		
		Collections.sort(result);
		return result;
	}

	public Player getTop() {
		return this.top;
	}

	public Graph<Player, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
}
