package concatonator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class Concatonator {

	public static void main(String[]args) throws IOException{
		String path = "./ImprovementResults/AmountOfRounds200/PartyDomain/"; 		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(new MyFilter()); 
		
		System.out.println(listOfFiles.length);
		
		
		int size = 0;
		for(File file:listOfFiles){	
			String fileName = file.getName();
			String lowFileName = fileName.toLowerCase();
			
			if(lowFileName.contains("collaborative")){
				addFileToCollaborative(file);
			}
			else if(lowFileName.contains("mixed")){
				addFileToMixed(file);
			}
			else if(lowFileName.contains("competitive")){				
				addFileToCompetitive(file);
			}			
		}
	}
	
	public static void addFileToCollaborative(File file) throws IOException{
		Path file1path =  Paths.get("Collaborative Party.csv");
		Path file2path = file.toPath();
		combine(file1path,file2path);
	}
	
	public static void addFileToMixed(File file) throws IOException{
		Path file1path =  Paths.get("Mixed Party.csv");
		Path file2path = file.toPath();
		combine(file1path,file2path);
	}
	
	public static void addFileToCompetitive(File file) throws IOException{
		Path file1path =  Paths.get("Competitive Party.csv");
		Path file2path = file.toPath();
		combine(file1path,file2path);
	}
	
	
	public static void combine(Path file1path, Path file2path)throws IOException{
		Charset charset = StandardCharsets.UTF_8;
		
		List<String> lines = Files.readAllLines(file2path, charset);
		lines.remove(1);
		lines.remove(0);		
		Files.write(file1path, lines, charset, StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
	}
}
