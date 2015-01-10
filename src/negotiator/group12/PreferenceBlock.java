package negotiator.group12;

import java.util.ArrayList;

public class PreferenceBlock {
	ArrayList<Node> nodeList = new ArrayList<Node>();
	String issue;
	
	public PreferenceBlock(String block, String issueName){
		this.issue = issueName;
		block = block.replace("{", "");
		block = block.replace("}", "");
		String[] splitted = block.split(", ");
		for(String i : splitted){
			String[] nodeString = i.split("=");	
			String value = nodeString[1].replace(",", ".");
			Node node = new Node(nodeString[0],Double.parseDouble(value));
			nodeList.add(node);
		}
	}
	
	public Node getHighestPreference() {
		double max = 0;
		Node rtn = new Node("", 0);
		for(Node n : nodeList) {
			if(n.getValue() > max) {
				rtn = n;
				max = n.getValue();
			}
		}
		return rtn;
	}
	
	public ArrayList<Node> getList(){
		return nodeList;
	}
	
}
