package negotiator.group12;

import java.util.ArrayList;

/**
 * PreferenceBlock is our representation of the Issue
 *
 */
public class PreferenceBlock {
	ArrayList<Node> nodeList = new ArrayList<Node>();
	String issue;
	Double weight;
	
	/**
	 * Constructor for the PreferenceBlock
	 * @param block, string to parse to get the values of the issue out.
	 * @param issueName
	 * @param weight
	 */
	public PreferenceBlock(String block, String issueName, Double weight){
		this.issue = issueName;
		this.weight = weight;
		block = block.replace("{", "");
		block = block.replace("}", "");				
		String[] splitted = block.split("(?<=,[0-9]{2})");
		for(String i : splitted){
			Node node;
			String[] nodeString = i.split("=");			
			if(nodeString[0].charAt(0) == ','){
				nodeString[0] = nodeString[0].substring(2);
			}
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
	
	/**
	 * Returns the node that is currently the highest for this issue
	 * @return
	 */
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
	
	/**
	 * Searches the nodeList of this issue for the value name of a node
	 * @param s
	 * @return index of the found node
	 */
	public int indexOf(String s) {
		int index = 0;
		for(int i = 0; i < nodeList.size(); i++) {
			if(nodeList.get(i).getName().equals(s)) {
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * Method to order the issue's nodes from low to high
	 */
	public void orderNodesLowToHigh() {
		nodeList = quicksort(nodeList);
	}

	/**
	 * Basic quicksort algorithm
	 * @param input
	 * @return sorted arraylist of nodes
	 */
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
	
	/**
	 * Helper method for the quicksort algorithm, is used to concatenate the two arraylists to eachother
	 * @param less
	 * @param pivot
	 * @param greater
	 * @return concatenated arraylist
	 */
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
	
	/**
	 * Nodelist getter
	 * @return
	 */
	public ArrayList<Node> getList(){
		return nodeList;
	}

	/**
	 * Method to retrieve all the nodes that have not yet been flagged
	 * @return
	 */
	public ArrayList<Node> getValuesWithoutFlag() {
		ArrayList<Node> rtn = new ArrayList<Node>();
		for(int i = 0; i<nodeList.size(); i++) {
			if(!nodeList.get(i).getFlag()) {
				rtn.add(nodeList.get(i));
			}
		}
		return rtn;
	}

	/**
	 * Method to retrieve the index of the highest ordered value that is not yet flagged.
	 * @return
	 */
	public int getHighestIndexWithoutFlag() {
		int rtnIndex = -1;
		for(int i = 0; i<nodeList.size(); i++) {
			if(nodeList.get(i).getFlag()) {
				return i-1;
			}
		}
		return rtnIndex;
	}
	
}
