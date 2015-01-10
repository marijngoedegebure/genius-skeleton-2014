package negotiator.group12;

import java.util.HashMap;

import negotiator.Bid;
import negotiator.issue.Value;
import negotiator.issue.ValueDiscrete;
import negotiator.utility.UtilitySpace;

public class BidGenerator {
	public static Bid generateBid(UtilitySpace utilitySpace, Preference preference) {
		HashMap<Integer, Value> hsmp = new HashMap<Integer, Value>();
		for(int i = 0; i < preference.preferenceList.size(); i++) {
			Node n = preference.preferenceList.get(i).getHighestPreference();
			ValueDiscrete val = new ValueDiscrete(n.getName());
			Value value = (Value) val;
			hsmp.put(i+1, value);
		}
		
		Bid bid = new Bid();
		try {
			bid = new Bid(utilitySpace.getDomain(), hsmp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		System.out.println(bid.toString());
		
		return bid;
	}
}
