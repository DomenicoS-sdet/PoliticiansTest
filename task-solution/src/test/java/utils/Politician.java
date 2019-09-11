package utils;

//This class is used as container for the data of the politician
//Also by using Json it is possible to serialize the class into JSon payload
public class Politician {
	
	private String name;
	private String country;
	private String yob;
	private String position;
	private int risk;
	private String createdAt;
	private String id;
	
	public Politician(String name, String country, String yob, String position, String risk) {
		this.name = name;
		this.country = country;
		this.yob = yob;
		this.position = position;
		this.risk = Integer.parseInt(risk);
		this.createdAt = "0";
		this.id = "N/A"; //The id is not assigned until the politician is inserted inside the DB
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getYob() {
		return yob;
	}
	
	public String getPosition() {
		return position;
	}
	
	public int getRisk() {
		return risk;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		
		if(!(o instanceof Politician)) return false;
		
		Politician p = (Politician) o;
		
		return this.id.equalsIgnoreCase(p.id);
	}
	
}
