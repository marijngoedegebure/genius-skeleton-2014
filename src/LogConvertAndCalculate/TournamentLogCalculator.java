package LogConvertAndCalculate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TournamentLogCalculator {
	// Need to be adjusted to have the
/*	public static String[] logNames = {
		"ConvertedLogGroup6CollaborativeProfile123Large.csv",
		"ConvertedLogGroup6CollaborativeProfile123Small.csv",
		"ConvertedLogGroup6MixedProfile456Large.csv",
		"ConvertedLogGroup6MixedProfile456Small.csv",
		"ConvertedLogGroup6CompetitiveProfile789Large.csv",
		"ConvertedLogGroup6CompetitiveProfile789Small.csv",
		"ConvertedLogPartyDomainCollaborativeProfile123Large.csv",
		"ConvertedLogPartyDomainCollaborativeProfile123Small.csv",
		"ConvertedLogPartyDomainMixedProfile456Large.csv",
		"ConvertedLogPartyDomainMixedProfile456Small.csv",
		"ConvertedLogPartyDomainCompetitiveProfile789Large.csv",
		"ConvertedLogPartyDomainCompetitiveProfile789Small.csv"
	};*/
	
	public static String[] logNames = {
		"ConvertedLogCollaborative Party.csv",
		"ConvertedLogCollaborative Tram.csv",
		"ConvertedLogCompetitive Party.csv",
		"ConvertedLogCompetitive Tram.csv",
		"ConvertedLogMixed Party.csv",
		"ConvertedLogMixed Tram.csv"
	};

	public static String splitByCharacter = ";";
	public static String[] headersSplit = { "Session", "Time (s)", "Rounds",
			"Agreement", "Discounted", "Approval", "Min. utility",
			"Max. utility", "Distance to pareto", "Distance to Nash",
			"Social welfare", "Agent name 1", "Agent name 2", "Agent name 3",
			"Agent utility 1", "Agent utility 2", "Agent utility 3" };

	public static String[] outputHeadersGeneralStatistics = { "Filename",
			"Amount of rounds", "Average distance to Nash",
			"Average social welfare", "% of agreements",
			"Average amount of rounds needed", "Average distance to Pareto",
			"Average difference between min and max utility" };

	public static String[] outputHeaderAgentStatistics = { "File name",
			"Agent name", "Amount of rounds", "Average distance to Nash",
			"Average social welfare", "% of agreements",
			"Average amount of rounds needed", "Average distance to Pareto",
			"Average difference between min and max utility" };

	public static ArrayList<String> agentNames = new ArrayList<String>();

	public static void main(String[] args) {
		ArrayList<String[]> output = new ArrayList<String[]>();
		for (String log : logNames) {
			ArrayList<Session> currentlyReadSessions = readLog(log);
			for(String agentName : agentNames) {
				output.add(calculateAgentStatistics(currentlyReadSessions, log, agentName));
			}
			//output.add(calculateGeneralStatistics(currentlyReadSessions, log));
		}
		saveLogAgentStatistics(output, "outputLog.csv");
	}

	public static String[] calculateAgentStatistics(ArrayList<Session> input,
			String filename, String agentName) {
		double sumDistanceNash = 0.0;
		double sumSocialWelfare = 0.0;
		double sumAgreements = 0.0;
		double sumAmountOfRoundsNeeded = 0.0;
		double sumDistancePareto = 0.0;
		double sumDifferenceMinMaxUtility = 0.0;
		int amountOfSessions = 0;

		for (Session session : input) {
			if (session.firstAgentName.equals(agentName)
					|| session.secondAgentName.equals(agentName)
					|| session.thirdAgentName.equals(agentName)) {
				sumDistanceNash += session.distanceToNash;
				sumSocialWelfare += session.socialWelfare;
				if (session.agreement) {
					sumAgreements += 1;
				}
				sumAmountOfRoundsNeeded += session.rounds;
				sumDistancePareto += session.distanceToPareto;
				sumDifferenceMinMaxUtility += session.maxUtility
						- session.minUtility;
				amountOfSessions++;
			}
		}

		String[] output = new String[outputHeaderAgentStatistics.length];
		output[0] = filename;
		output[1] = agentName;
		output[2] = sumAmountOfRoundsNeeded + "";
		output[3] = sumDistanceNash / amountOfSessions + "";
		output[4] = sumSocialWelfare / amountOfSessions + "";
		output[5] = sumAgreements / amountOfSessions + "";
		output[6] = sumAmountOfRoundsNeeded / amountOfSessions + "";
		output[7] = sumDistancePareto / amountOfSessions + "";
		output[8] = sumDifferenceMinMaxUtility / amountOfSessions + "";
		return output;
	}

	public static String[] calculateGeneralStatistics(ArrayList<Session> input,
			String filename) {
		double sumDistanceNash = 0.0;
		double sumSocialWelfare = 0.0;
		double sumAgreements = 0.0;
		double sumAmountOfRoundsNeeded = 0.0;
		double sumDistancePareto = 0.0;
		double sumDifferenceMinMaxUtility = 0.0;
		int amountOfSessions = 0;

		for (Session session : input) {
			sumDistanceNash += session.distanceToNash;
			sumSocialWelfare += session.socialWelfare;
			if (session.agreement) {
				sumAgreements += 1;
			}
			sumAmountOfRoundsNeeded += session.rounds;
			sumDistancePareto += session.distanceToPareto;
			sumDifferenceMinMaxUtility += session.maxUtility
					- session.minUtility;
			amountOfSessions++;
		}

		String[] output = new String[outputHeadersGeneralStatistics.length];
		output[0] = filename;
		output[1] = sumAmountOfRoundsNeeded + "";
		output[2] = sumDistanceNash / amountOfSessions + "";
		output[3] = sumSocialWelfare / amountOfSessions + "";
		output[4] = sumAgreements / amountOfSessions + "";
		output[5] = sumAmountOfRoundsNeeded / amountOfSessions + "";
		output[6] = sumDistancePareto / amountOfSessions + "";
		output[7] = sumDifferenceMinMaxUtility / amountOfSessions + "";
		return output;
	}
	
	public static void saveLogAgentStatistics(ArrayList<String[]> output, String exportFileName) {
		String exportString = "";
		// Add headers
		for (int i = 0; i < outputHeaderAgentStatistics.length; i++) {
			if (i == outputHeaderAgentStatistics.length - 1) {
				exportString += outputHeaderAgentStatistics[i];
			} else {
				exportString += outputHeaderAgentStatistics[i] + ";";
			}
		}

		exportString += "\n";

		// Add all of the lines
		for (int i = 0; i < output.size(); i++) {
			for (int k = 0; k < output.get(i).length; k++) {
				if (k == output.get(i).length - 1) {
					exportString += output.get(i)[k];
				} else {
					exportString += output.get(i)[k] + ";";
				}
			}
			exportString += "\n";
		}
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(
					exportFileName));
			br.write(exportString);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveLogGeneralStatistics(ArrayList<String[]> output, String exportFileName) {
		String exportString = "";
		// Add headers
		for (int i = 0; i < outputHeadersGeneralStatistics.length; i++) {
			if (i == outputHeadersGeneralStatistics.length - 1) {
				exportString += outputHeadersGeneralStatistics[i];
			} else {
				exportString += outputHeadersGeneralStatistics[i] + ";";
			}
		}

		exportString += "\n";

		// Add all of the lines
		for (int i = 0; i < output.size(); i++) {
			for (int k = 0; k < output.get(i).length; k++) {
				if (k == output.get(i).length - 1) {
					exportString += output.get(i)[k];
				} else {
					exportString += output.get(i)[k] + ";";
				}
			}
			exportString += "\n";
		}
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(
					exportFileName));
			br.write(exportString);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int countLines(String fileName) {
		BufferedReader br = null;
		int count = 0;
		try {
			br = new BufferedReader(new FileReader(fileName));
			Scanner sc = new Scanner(br);
			sc.nextLine();
			sc.nextLine();
			while (sc.hasNextLine()) {
				sc.nextLine();
				count++;
			}
		} catch (Exception e) {

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return count;
	}

	public static ArrayList<Session> readLog(String fileName) {
		BufferedReader br = null;
		ArrayList<Session> rtn = new ArrayList<Session>();
		try {
			br = new BufferedReader(new FileReader(fileName));
			Scanner sc = new Scanner(br);
			// Skip first row
			String headers = sc.nextLine();
			// String[] headersSplit = headers.split(splitByCharacter);
			int i = 0;
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] lineSplit = line.split(splitByCharacter);
				for (int j = 0; j < lineSplit.length; j++) {
					lineSplit[j] = lineSplit[j].replace(",", ".");
				}
				Session temp = new Session(lineSplit);
				rtn.add(temp);

				if (!agentNames.contains(lineSplit[11])) {
					agentNames.add(lineSplit[11]);
				}
				if (!agentNames.contains(lineSplit[12])) {
					agentNames.add(lineSplit[12]);
				}
				if (!agentNames.contains(lineSplit[13])) {
					agentNames.add(lineSplit[13]);
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.toString());
				}
			}
		}
		return rtn;
	}
}
