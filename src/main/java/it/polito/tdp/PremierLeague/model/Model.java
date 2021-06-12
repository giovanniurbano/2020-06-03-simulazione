package it.polito.tdp.PremierLeague.model;

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
		
		
		return String.format("Grafo creato con %d vertici e %d archi\n", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
	}
	
	
}
