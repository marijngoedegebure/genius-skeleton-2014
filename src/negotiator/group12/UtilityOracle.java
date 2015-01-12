package negotiator.group12;

public class UtilityOracle {
	private double decrease;

	public UtilityOracle(double decrease){
		this.decrease = decrease;
	}
	
	public double getAcceptingValue(int round){
		return (1.00 - (round * decrease));
	}
}
