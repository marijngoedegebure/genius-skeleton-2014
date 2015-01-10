package negotiator.group12;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;

import negotiator.Domain;
import negotiator.issue.Issue;
import negotiator.issue.IssueDiscrete;
import negotiator.issue.Objective;
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
			Evaluator evalor = utilitySpace.getEvaluator(i);
			System.out.println(evalor);
			PreferenceBlock prefBlock = new PreferenceBlock(evalor.toString(), issue.getName());
			preferenceList.add(prefBlock);
		}
	}
}
