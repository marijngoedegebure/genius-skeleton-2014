package LogConvertAndCalculate;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TournamentLogConverter {

	public static String[] logNames = {
		"Group6CollaborativeProfile123Large.csv",
		"Group6CollaborativeProfile123Small.csv",
		"Group6MixedProfile456Large.csv",
		"Group6MixedProfile456Small.csv",
		"PartyDomainCollaborativeProfile123Large.csv",
		"PartyDomainCollaborativeProfile123Small.csv",
		"PartyDomainMixedProfile456Large.csv",
		"PartyDomainMixedProfile456Small.csv"
	};
	
	public static String splitByCharacter = ";";
	public static String[] headersSplit = {
			"Session", "Time (s)", "Rounds", "Agreement", "Discounted", 
			"Approval", "Min. utility", "Max. utility", "Distance to pareto", "Distance to Nash", 
			"Social welfare", "Agent name 1", "Agent name 2", "Agent name 3", "Agent utility 1",
			"Agent utility 2", "Agent utility 3"
		};

	
	public static void main(String[] args) {
		for(String log : logNames) {
			String[][] renamedLog = renameAgentNames(log);
			saveLog(renamedLog, "ConvertedLog" + log);
		}
	}
	
	public static void saveLog(String[][] output, String exportFileName) {
		String exportString = "";
		// Add headers
		for (int i = 0; i < headersSplit.length; i++) {
			if (i == headersSplit.length -1) {
				exportString += headersSplit[i];
			}
			else {
				exportString += headersSplit[i] + ",";
			}
		}
		
		exportString += "\n";
		
		// Add all of the lines
		for (int i = 0; i < output.length; i++) {
			for (int k = 0; k < output[i].length; k++) {
				if (k == output[i].length - 1) {
					exportString += output[i][k];
				} else {
					exportString += output[i][k] + ";";
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
			while(sc.hasNextLine()) {
				sc.nextLine();
				count++;
			}
		}
		catch (Exception e){
			
		}
		finally {
			if(br != null) {
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
	
	public static String[][] renameAgentNames(String fileName) {
		BufferedReader br = null;
		int lineCount = countLines(fileName);
		String[][] rtn = new String[lineCount][headersSplit.length];
		try {
			br = new BufferedReader(new FileReader(fileName));
			Scanner sc = new Scanner(br);
			// Skip first row
			sc.nextLine();
			String headers = sc.nextLine();
			//String[] headersSplit = headers.split(splitByCharacter);
			int i = 0;
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] lineSplit = line.split(splitByCharacter);
				String[] namesAgent = {lineSplit[11], lineSplit[12], lineSplit[13]};
				ArrayList<String> newNames = new ArrayList<String>();
				for(String name : namesAgent) {
					if(!name.substring(23, 24).equals(".")) {
						newNames.add(name.substring(17, 24));
					}
					else {
						newNames.add(name.substring(17, 23));
					}
				}
				lineSplit[11] = newNames.get(0);
				lineSplit[12] = newNames.get(1);
				lineSplit[13] = newNames.get(2);
				
				for(int j = 0; j<lineSplit.length;j++) {
					rtn[i][j] = lineSplit[j];
				}
				i++;
			}
		}
		catch (Exception e){
			
		}
		finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return rtn;
	}
	
	public void readLog(String fileName) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			br.readLine();
			Scanner sc = new Scanner(br);
			String headers = sc.nextLine();
			//String[] headersSplit = headers.split(splitByCharacter);
			while(sc.hasNextLine()) {
				
			}
		}
		catch (Exception e){
			
		}
		finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
