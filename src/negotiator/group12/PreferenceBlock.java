package negotiator.group12;

import java.util.ArrayList;

public class PreferenceBlock {
	ArrayList<Node> nodeList = new ArrayList<Node>();
	
	public PreferenceBlock(String block){
		String[] splitted = block.split(", ");
		for(String i : splitted){
			String[] nodeString = i.split("=");	
			String value = nodeString[1].replace(",", ".");
			value = value.substring(0, 3);
			Node node = new Node(nodeString[0],Double.parseDouble(value));
			nodeList.add(node);
		}
	}
	
	public ArrayList<Node> getList(){
		return nodeList;
	}
	
}
