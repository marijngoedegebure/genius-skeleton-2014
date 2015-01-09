package negotiator.group12;

public class Node {
	private String name;
	private double value;
	public Node(String st, double va){
		this.name = st;
		this.value = va;
	}

	public String getName(){
		return name;
	}
	public void setName(String nm){
		this.name = nm;
	}
	
	public double getValue(){
		return value;
	}
	public void setValue(double va){
		this.value = va;
	}
}
