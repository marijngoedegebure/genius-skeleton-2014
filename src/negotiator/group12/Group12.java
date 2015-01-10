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
	ArrayList<Action> previousActions = new ArrayList<Action>();
	Preference preference;
	HashMap<AgentID, Preference> otherAgentsPreference = new HashMap<AgentID, Preference>();
	
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
		// with 50% chance, counter offer
		// if we are the first party, also offer.
		if (!validActions.contains(Accept.class) || Math.random() > 0.5) {
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
		System.out.println("Agent " + this.getPartyId() + " receives bid");
		System.out.println(action.toString());
		action.getAgent();
		previousActions.add(action);
		if(Action.getBidFromAction(action) != null) {
			Bid bid = Action.getBidFromAction(action);
			try {
				if(!otherAgentsPreference.containsKey(this.getPartyId())) {
					Preference pref = new Preference(this.utilitySpace);
					pref.updatePreference(bid);
					otherAgentsPreference.put(action.getAgent(), pref);
				}
				else {
					otherAgentsPreference.get(this.getPartyId()).updatePreference(bid);
				}
				Double utilityOfBid = this.utilitySpace.getUtility(bid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			previousBids.add(bid);
		}
	}

}
