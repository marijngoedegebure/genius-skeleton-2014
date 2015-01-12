package negotiator.group12;

import java.util.ArrayList;

public class PreferenceBlock {
	ArrayList<Node> nodeList = new ArrayList<Node>();
	String issue;
	Double weight;
	
	public PreferenceBlock(String block, String issueName, Double weight){
		this.issue = issueName;
		this.weight = weight;
		block = block.replace("{", "");
		block = block.replace("}", "");
		String[] splitted = block.split(", ");
		for(String i : splitted){
			Node node;
			String[] nodeString = i.split("=");	
			String value = nodeString[1].replace(",", ".");
			double val = Double.parseDouble(value);
			if(val == 1.0) {
				node = new Node(nodeString[0], val, true);
			}
			else {
				node = new Node(nodeString[0], val, false);
			}
			nodeList.add(node);
		}
		orderNodesLowToHigh();
	}
	
	public Node getHighestPreference() {
		double max = 0;
		Node rtn = new Node("", 0, false);
		for(Node n : nodeList) {
			if(n.getValue() > max) {
				rtn = n;
				max = n.getValue();
			}
		}
		return rtn;
	}
	
	public int indexOf(String s) {
		int index = 0;
		for(int i = 0; i < nodeList.size(); i++) {
			if(nodeList.get(i).getName().equals(s)) {
				index = i;
			}
		}
		return index;
	}
	
	public void orderNodesLowToHigh() {
		nodeList = quicksort(nodeList);
	}
	
	public ArrayList<Node> quicksort(ArrayList<Node> input) {
		if(input.size() <= 1){
			return input;
		}
		
		int middle = (int) Math.ceil((double)input.size() / 2);
		Node pivot = input.get(middle);
 
		ArrayList<Node> less = new ArrayList<Node>();
		ArrayList<Node> greater = new ArrayList<Node>();
		
		for (int i = 0; i < input.size(); i++) {
			if(input.get(i).getValue() <= pivot.getValue()){
				if(i == middle){
					continue;
				}
				less.add(input.get(i));
			}
			else{
				greater.add(input.get(i));
			}
		}
		
		return concatenate(quicksort(less), pivot, quicksort(greater));
	}
	
	private ArrayList<Node> concatenate(ArrayList<Node> less, Node pivot, ArrayList<Node> greater){
		
		ArrayList<Node> list = new ArrayList<Node>();
		
		for (int i = 0; i < less.size(); i++) {
			list.add(less.get(i));
		}
		
		list.add(pivot);
		
		for (int i = 0; i < greater.size(); i++) {
			list.add(greater.get(i));
		}
		
		return list;
	}
	
	public ArrayList<Node> getList(){
		return nodeList;
	}

	public ArrayList<Node> getValuesWithoutFlag() {
		ArrayList<Node> rtn = new ArrayList<Node>();
		for(int i = 0; i<nodeList.size(); i++) {
			if(!nodeList.get(i).getFlag()) {
				rtn.add(nodeList.get(i));
			}
		}
		return rtn;
	}

	public int getHighestIndexWithoutFlag() {
		int rtnIndex = -1;
		for(int i = 0; i<nodeList.size(); i++) {
			if(!nodeList.get(i).getFlag()) {
				return rtnIndex;
			}
		}
		return rtnIndex;
	}
	
}
