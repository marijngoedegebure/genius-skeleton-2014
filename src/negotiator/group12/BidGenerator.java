package negotiator.group12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

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
		//bidCombinations = combinations(preference);
		
		System.out.println("End combinations");		
		
		List<Set<Node>> blocks = new ArrayList<Set<Node>>();
		
		for(int i = 0; i<preference.preferenceList.size();i++){
			PreferenceBlock block = preference.preferenceList.get(i);
			Set<Node> nodes = new HashSet<Node>();
			for(int j = 0; j<block.nodeList.size();j++){
				nodes.add(block.nodeList.get(j));
			}
			blocks.add(nodes);
		}
		
		
		Set<List<Node>> result = Sets.cartesianProduct(blocks);
		Iterator<List<Node>> iterator = result.iterator();
		while(iterator.hasNext()) {
			List<Node> list = iterator.next();
			HashMap<Integer, Value> hsmp = new HashMap<Integer, Value>();
			Bid bid = new Bid();
			for(int i = 0; i<list.size();i++){
				Node node = list.get(i);
				ValueDiscrete val = new ValueDiscrete(node.getName());
				Value value = (Value) val;
				hsmp.put(i+1, value);
				
				bid = new Bid(utilitySpace.getDomain(), hsmp);												
			}			
			bidCombinations.add(bid);
		}		
		
		for(Bid bid:bidCombinations){
			System.out.println(bid);
		}
		
		ArrayList<Bid> filteredBidCombinations = filterCombos(acceptingValue,8);
			
		return filteredBidCombinations.get((int)filteredBidCombinations.size()/2);
		

		
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
				ArrayList<Bid> bidding = new ArrayList<Bid>();
				bidding.add(bid);
				return bidding;	
				
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
