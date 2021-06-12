package it.polito.tdp.PremierLeague.model;

public class GiocatoreTitolarita {
	private Player p;
	private int gradoTitolarita;
	public GiocatoreTitolarita(Player p, int gradoTitolarita) {
		super();
		this.p = p;
		this.gradoTitolarita = gradoTitolarita;
	}
	public Player getP() {
		return p;
	}
	public void setP(Player p) {
		this.p = p;
	}
	public int getGradoTitolarita() {
		return gradoTitolarita;
	}
	public void setGradoTitolarita(int gradoTitolarita) {
		this.gradoTitolarita = gradoTitolarita;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p == null) ? 0 : p.hashCode());
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
		GiocatoreTitolarita other = (GiocatoreTitolarita) obj;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		return true;
	}
	
	
}
