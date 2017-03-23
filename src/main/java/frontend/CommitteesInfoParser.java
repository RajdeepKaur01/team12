package main.java.frontend;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringEscapeUtils;

public class CommitteesInfoParser {
	
	private String folder = "";
	private String confName="";
	private String confYear="";
	private String author = "";
	private String title = "";
	private List<String> results = new ArrayList<String>();

	public void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(folder+"\\"+fileEntry.getName());
	           parseFile(folder+"\\"+fileEntry.getName(),fileEntry.getName());
	        }
	    }
	}
	
	public void parseFile(String filePath,String fileName){
		FileInputStream input;
		confName = fileName.replaceAll("[0-9]", " ").split(" ")[0];
		confYear = fileName.replaceAll("[^0-9]", "");
		//System.out.println(confName+","+confYear);
	    try {
	        input = new FileInputStream(new File(filePath));
	        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
	        decoder.onMalformedInput(CodingErrorAction.IGNORE);
	        InputStreamReader reader = new InputStreamReader(input, decoder);
	        BufferedReader bufferedReader = new BufferedReader( reader );
	        String line = bufferedReader.readLine();
	        while( line != null ) {
	        	String[] splits = line.split(":");
	        	if(splits.length>1){
	        		title=splits[0];
	        		author = StringEscapeUtils.unescapeHtml4(splits[1]).replaceAll("[^\\x20-\\x7e]", "");
	        		results.add(confName+"->"+confYear+"->"+author+"->"+title);
	        	}
	            line = bufferedReader.readLine();
	        }
	        bufferedReader.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch( IOException e ) {
	        e.printStackTrace();
	    }
}
	public List<String> readFilesInDirectory(String filepath){
		this.folder = filepath;
		listFilesForFolder(new File(filepath));
		return results;
	}
	
	public static void main(String argp[]){
		
		CommitteesInfoParser ob = new CommitteesInfoParser();
		ob.readFilesInDirectory("C:\\Users\\MOHIT\\Documents\\MSD\\committees\\committees\\");
		ob.results.forEach(System.out::println);
		System.out.println(ob.results.size());
	}
}
