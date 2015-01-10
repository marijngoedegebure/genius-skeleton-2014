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
			PreferenceBlock prefBlock = new PreferenceBlock(evalor.toString(), issue.getName(), weight);
			preferenceList.add(prefBlock);
		}
	}
	
	/**
	 * Updates the preference of a party given the current bid.
	 * For now it thinks that the new bid is all top priority
	 * @param bid
	 */
	public void updatePreference(Bid bid) {
		HashMap<Integer, Value> values = bid.getValues();
		for(int i = 1; i <= values.size(); i++) {
			ValueDiscrete value = (ValueDiscrete) values.get(i);
			PreferenceBlock preferBlock = preferenceList.get(i-1);
			Node currentHighest = preferBlock.getHighestPreference();
			if(!currentHighest.getName().equals(value.getValue())) {
				preferBlock.orderNodesLowToHigh();
				ArrayList<Node> nodes = preferBlock.nodeList;
				
				int index = preferBlock.indexOf(value.getValue());
				
				for(int j = index; j < nodes.size()-1; j++) {
					nodes.get(j).setName(nodes.get(j+1).getName());
				}
				nodes.get(nodes.size()-1).setName(value.getValue());
			}			
		}
	}
}
