package negotiator.group12;

import java.util.ArrayList;

public class PreferenceBlock {
	ArrayList<Node> nodeList = new ArrayList<Node>();
	
	public PreferenceBlock(String block){
		String[] splitted = block.split(", ");
		for(String i : splitted){
			String[] nodeString = i.split("=");	
			Node node = new Node(nodeString[0],Double.parseDouble(nodeString[1]));
			nodeList.add(node);
		}
	}
	
	public ArrayList<Node> getList(){
		return nodeList;
	}
	
}
