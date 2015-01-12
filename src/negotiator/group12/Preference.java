package negotiator.group12;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;

import negotiator.Bid;
import negotiator.Domain;
import negotiator.issue.Issue;
import negotiator.issue.IssueDiscrete;
import negotiator.issue.Objective;
import negotiator.issue.Value;
import negotiator.issue.ValueDiscrete;
import negotiator.utility.Evaluator;
import negotiator.utility.UtilitySpace;
import negotiator.xml.SimpleElement;

public class Preference {
	ArrayList<PreferenceBlock> preferenceList = new ArrayList<PreferenceBlock>();
			
	public Preference(UtilitySpace utilitySpace){
		Domain domain = utilitySpace.getDomain();
		int size = utilitySpace.getEvaluators().size();
		System.out.println("size: "+size);
		ArrayList<Objective> objectives = domain.getObjectives();
		for(int i = 1; i<= size; i++){
			IssueDiscrete issue = (IssueDiscrete) objectives.get(i);
			Double weight = utilitySpace.getWeight(i);
			Evaluator evalor = utilitySpace.getEvaluator(i);
			System.out.println(evalor);
			// Define each issue with every weight attached
			PreferenceBlock prefBlock = new PreferenceBlock(evalor.toString(), issue.getName(), weight);
			preferenceList.add(prefBlock);
		}
		// Possibility to randomize the other values of each issue.
	}
	
	public Preference(UtilitySpace utilitySpace, Bid bid){
		Domain domain = utilitySpace.getDomain();
		int size = utilitySpace.getEvaluators().size();
		System.out.println("size: "+size);
		ArrayList<Objective> objectives = domain.getObjectives();
		for(int i = 1; i<= size; i++){
			IssueDiscrete issue = (IssueDiscrete) objectives.get(i);
			double weight = 1.0/size;
			Evaluator evalor = utilitySpace.getEvaluator(i);
			System.out.println(evalor);
			// Define an issue with all weights the same 
			PreferenceBlock prefBlock = new PreferenceBlock(evalor.toString(), issue.getName(), weight);
			preferenceList.add(prefBlock);
		}
		setHighestIssuePreference(bid);
		// Possibility to randomize the other values of each issue.
	}
	
	/**
	 * Updates the preference of a party given the current bid.
	 * For now it thinks that the new bid is all top priority
	 * @param bid
	 */
	public void setHighestIssuePreference(Bid bid) {
		HashMap<Integer, Value> values = bid.getValues();
		for(int i = 1; i <= values.size(); i++) {
			ValueDiscrete value = (ValueDiscrete) values.get(i);
			PreferenceBlock preferBlock = preferenceList.get(i-1);
			Node currentHighest = preferBlock.getHighestPreference();
			if(!currentHighest.getName().equals(value.getValue())) {
				//preferBlock.orderNodesLowToHigh();
				ArrayList<Node> nodes = preferBlock.nodeList;
				
				int index = preferBlock.indexOf(value.getValue());
				
				for(int j = index; j < nodes.size()-1; j++) {
					nodes.get(j).setName(nodes.get(j+1).getName());
				}
				nodes.get(nodes.size()-1).setName(value.getValue());
			}			
		}
	}
	
	public void updatePreference(Bid bid) {
		// First update the sequence of the values of an issue
		HashMap<Integer, Value> values = bid.getValues();
		for(int i = 1; i <= values.size(); i++) {
			ValueDiscrete value = (ValueDiscrete) values.get(i);
			PreferenceBlock preferBlock = preferenceList.get(i-1);
			ArrayList<Node> nodesWithoutFlag = preferBlock.getValuesWithoutFlag();
			for(int j = 0; i< nodesWithoutFlag.size(); i++) {
				// If not flagged yet, flag it and set it to the next value after the last flagged
				if(nodesWithoutFlag.get(i).getName().equals(value.getValue())) {
					int highestIndexWithoutFlag = preferBlock.getHighestIndexWithoutFlag();
					
					ArrayList<Node> nodes = preferBlock.nodeList;
					int index = preferBlock.indexOf(value.getValue());
					
					for(int k = index; k < nodes.size()-1; k++) {
						nodes.get(k).setName(nodes.get(k+1).getName());
					}
					nodes.get(highestIndexWithoutFlag).setName(value.getValue());
				}
			}		
		}
	}
}
