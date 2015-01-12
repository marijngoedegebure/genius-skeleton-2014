package negotiator.group12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import negotiator.AgentID;
import negotiator.Bid;
import negotiator.DeadlineType;
import negotiator.Timeline;
import negotiator.actions.Accept;
import negotiator.actions.Action;
import negotiator.actions.Offer;
import negotiator.parties.AbstractNegotiationParty;
import negotiator.utility.UtilitySpace;

/**
 * This is your negotiation party.
 */
public class Group12 extends AbstractNegotiationParty {
	
	ArrayList<Bid> previousBids = new ArrayList<Bid>();
	ArrayList<BidWithSender> previousBidsWithSender = new ArrayList<BidWithSender>();
	Preference preference;
	UtilityOracle oracle;
	int round = 0;
	HashMap<String, Preference> otherAgentsPreference = new HashMap<String, Preference>();
	
	/**
	 * Please keep this constructor. This is called by genius.
	 *
	 * @param utilitySpace Your utility space.
	 * @param deadlines The deadlines set for this negotiation.
	 * @param timeline Value counting from 0 (start) to 1 (end).
	 * @param randomSeed If you use any randomization, use this seed for it.
	 */
	public Group12(UtilitySpace utilitySpace,
				  Map<DeadlineType, Object> deadlines,
				  Timeline timeline,
				  long randomSeed) {
		// Make sure that this constructor calls it's parent.
		super(utilitySpace, deadlines, timeline, randomSeed);
		preference = new Preference(utilitySpace);
		oracle = new UtilityOracle(0.05);
	}

	/**
	 * Each round this method gets called and ask you to accept or offer. The first party in
	 * the first round is a bit different, it can only propose an offer.
	 *
	 * @param validActions Either a list containing both accept and offer or only offer.
	 * @return The chosen action.
	 */
	@Override
	public Action chooseAction(List<Class> validActions) {
		System.out.println("Agent in turn: " + this.getPartyId());
		double acceptingValue = oracle.getAcceptingValue(round);
		double bidValue = 0;
		try {
			bidValue = this.utilitySpace.getUtility(previousBids.get(previousBids.size()-1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		round++;		
		// with 50% chance, counter offer
		// if we are the first party, also offer.
		if (!validActions.contains(Accept.class) || acceptingValue > bidValue) {
			Bid bid = BidGenerator.generateBid(this.utilitySpace, this.preference);
			return new Offer(bid);
		}
		else {
			System.out.println("Accept");
			return new Accept();
		}
	}


	/**
	 * All offers proposed by the other parties will be received as a message.
	 * You can use this information to your advantage, for example to predict their utility.
	 *
	 * @param sender The party that did the action.
	 * @param action The action that party did.
	 */
	@Override
	public void receiveMessage(Object sender, Action action) {
		// Here you can listen to other parties' messages
		Group12 castedSender = (Group12) sender;
		System.out.println("Agent " + this.getPartyId() + " receives bid");
		System.out.println(action.toString());
		action.getAgent();
		if(Action.getBidFromAction(action) != null) {
			Bid bid = Action.getBidFromAction(action);
			try {
				if(!otherAgentsPreference.containsKey(castedSender.partyId.toString())) {
					Preference pref = new Preference(this.utilitySpace, bid);
					otherAgentsPreference.put(castedSender.partyId.toString(), pref);
				}
				else {
					ArrayList<Bid> previousbidsOfSender = getBidsOfSender(castedSender.partyId.toString());
					otherAgentsPreference.get(castedSender.partyId.toString()).updatePreferenceOrder(bid);
					otherAgentsPreference.get(castedSender.partyId.toString()).updateIssueWeights(previousbidsOfSender, bid);
				}
				Double utilityOfBid = this.utilitySpace.getUtility(bid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BidWithSender bidWS = new BidWithSender(bid, castedSender.partyId.toString());
			previousBidsWithSender.add(bidWS);
			previousBids.add(bid);
		}
	}

	private ArrayList<Bid> getBidsOfSender(String sender) {
		ArrayList<Bid> previousBids = new ArrayList<Bid>();
		for(BidWithSender bidWS : previousBidsWithSender) {
			if(bidWS.sender.equals(sender)) {
				previousBids.add(bidWS.bid);
			}
		}
		return previousBids;
	}

}
