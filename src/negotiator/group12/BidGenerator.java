package negotiator.group12;

import java.util.HashMap;

import negotiator.Bid;
import negotiator.issue.Value;
import negotiator.issue.ValueDiscrete;
import negotiator.utility.UtilitySpace;

public class BidGenerator {
	public static Bid generateBid(UtilitySpace utilitySpace) {
		//utilitySpace.getDomain().getIssues().get(0).
		ValueDiscrete val = new ValueDiscrete("asdfasdf");
		Value value = (Value) val;
		
		HashMap<Integer, Value> hsmp = new HashMap<Integer, Value>();
		hsmp.put(0, value);
		
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
