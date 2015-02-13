package LogConvertAndCalculate;

public class Session {
	public int rounds;
	public boolean agreement;
	public double minUtility;
	public double maxUtility;
	public double distanceToPareto;
	public double distanceToNash;
	public double socialWelfare;
	public String firstAgentName;
	public String secondAgentName;
	public String thirdAgentName;
	public double utilityFirstAgent;
	public double utilitySecondAgent;
	public double utilityThirdAgent;
	
	public Session(String[] line) {
		this.rounds = Integer.parseInt(line[2]);
		if(line[3].equals("Yes")) {
			this.agreement = true;
		}
		else { 
			this.agreement = false;
		}
		
		if(!line[6].equals("")) {
			this.minUtility = Double.parseDouble(line[6]);
		}
		if(!line[7].equals("")) {
			this.maxUtility = Double.parseDouble(line[7]);
		}
		if(!line[8].equals("")) {
			this.distanceToPareto = Double.parseDouble(line[8]);
		}
		if(!line[9].equals("")) {
			this.distanceToNash = Double.parseDouble(line[9]);
		}
		if(!line[10].equals("")) {
			this.socialWelfare = Double.parseDouble(line[10]);
		}
		if(!line[11].substring(23, 24).equals(".")) {
			this.firstAgentName = line[11].substring(17, 24);
		}
		else {
			this.firstAgentName = line[11].substring(17, 23);
		}
		if(!line[12].substring(23, 24).equals(".")) {
			this.firstAgentName = line[12].substring(17, 24);
		}
		else {
			this.firstAgentName = line[12].substring(17, 23);
		}
		if(!line[13].substring(23, 24).equals(".")) {
			this.firstAgentName = line[13].substring(17, 24);
		}
		else {
			this.firstAgentName = line[13].substring(17, 23);
		}
		this.utilityFirstAgent = Double.parseDouble(line[14]);
		this.utilitySecondAgent = Double.parseDouble(line[15]);
		this.utilityThirdAgent = Double.parseDouble(line[16]);
	}
}
