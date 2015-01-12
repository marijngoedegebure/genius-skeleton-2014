package negotiator.group12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import negotiator.Bid;
import negotiator.issue.Value;
import negotiator.issue.ValueDiscrete;
import negotiator.utility.UtilitySpace;

public class BidGenerator {
	static ArrayList<Bid> bidCombinations;
	static UtilitySpace utilitySpace;
	
	public static Bid generateBid(UtilitySpace ut, Preference preference,double acceptingValue) throws Exception {
		
		
		System.out.println("Begin combinations");
		utilitySpace = ut;	
		bidCombinations  = new ArrayList<Bid>();
		bidCombinations = combinations(preference);
		for(Bid bid: bidCombinations){
			System.out.println(bid);
		}
		
		System.out.println("End combinations");		
		
		
		ArrayList<Bid> filteredBidCombinations = filterCombos(acceptingValue,8);
			
		return filteredBidCombinations.get((int)filteredBidCombinations.size()/2);
		
		
		/*
		HashMap<Integer, Value> hsmp = new HashMap<Integer, Value>();
		for(int i = 0; i < preference.preferenceList.size(); i++) {
			Node n = preference.preferenceList.get(i).getHighestPreference();
			ValueDiscrete val = new ValueDiscrete(n.getName());
			Value value = (Value) val;
			hsmp.put(i+1, value);
		}
		Bid bid = new Bid();
		try {
			bid = new Bid(ut.getDomain(), hsmp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("bidgenerator tostring:" + bid.toString());
		return bid;
		*/
		
	}
	
	public static ArrayList<Bid> combinations(Preference preference){
		HashMap<Integer, Value> hsmp = new HashMap<Integer, Value>();
		return combinationsW(preference,hsmp, 0);
	}
	public static ArrayList<Bid> combinationsW(Preference preference, HashMap<Integer, Value> hsmp, int part){
		if(part == preference.preferenceList.size()){
			Bid bid = new Bid();
			try {
				bid = new Bid(utilitySpace.getDomain(), hsmp);	
				ArrayList<Bid> bids = new ArrayList<Bid>();
				bids.add(bid);
				return bids;	
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		else{
			PreferenceBlock block = preference.preferenceList.get(part);
			ArrayList<Node> nodeList = block.getList();
			
			ArrayList<Bid> bids = new ArrayList<Bid>();
			
			for(int i = 0; i < nodeList.size() ; i++) {
				Node n = nodeList.get(i);
				ValueDiscrete val = new ValueDiscrete(n.getName());
				Value value = (Value) val;
				hsmp.put(part+1, value);
				bids = concatenate(bids,combinationsW(preference,hsmp,part + 1));
			}
			return bids;
		}
		return null;
	}
	
	public static ArrayList<Bid> concatenate(ArrayList<Bid> left, ArrayList<Bid> right){
		ArrayList<Bid> bids = new ArrayList<Bid>();
		for(Bid bid: left){
			bids.add(bid);
		}
		for(Bid bid: right){
			bids.add(bid);
		}
		return bids;
	}
	
	public static ArrayList<Bid> filterCombos(double acceptingValue, int range) throws Exception{
		ArrayList<Bid> goodBidCombos = new ArrayList<Bid>();
		
		double minValue = acceptingValue - (acceptingValue/range);
		double maxValue = acceptingValue + (acceptingValue/range);
				
		for(int i = 0; i<bidCombinations.size();i++){
			Bid bid = bidCombinations.get(i);
			double value = utilitySpace.getUtility(bid);
			if(minValue < value && maxValue > value){
				//System.out.println(bid);
				goodBidCombos.add(bid);
				
			}
		}
		
		if(goodBidCombos.size() != 0){
			return goodBidCombos;
		}
		else
			return filterCombos(acceptingValue, range-1);
		
	}
}
