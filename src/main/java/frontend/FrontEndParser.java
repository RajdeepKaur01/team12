package main.java.frontend;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class FrontEndParser {
	
	private static final String INPROCEEDINGS = "inproceeedings";
	private static final String PROCEEDINGS = "proceeeeeeeedings";
	private static final String ARTICLE = "articlee";
	private static final String WWW = "www";
	private static final String CONFERENCE = "conference";
	private static final String JOURNALARTICLE = "journalarticle";
	private static final String CONFERENCEARTICLE ="conferencearticle";
	private static final String AUTHORWWW = "authorwww";
	private static final String EMPTY ="empty";
	private static final String IGNORE="ignore";
	private static final int BATCHLIMIT = 20000;
	
	private  String parentElement=EMPTY;
	private  String childElement=EMPTY;
	private Article article;
	private InProceedings inproceedings;
	private Conference conference;
	private WWW authorWWW;
	private StringBuilder journalAuthors, conferenceAuthors;
	private RecordsBatchCreator batchCreaterObj;
	PreparedStatement proceedingsStmt;
	PreparedStatement authorStmt;
	PreparedStatement wwwStmt;
	PreparedStatement journalStmt;
	PreparedStatement inproceedingsStmt;
	private Connection mySQLConnectionObject;
	private int counter=0;
	
	private class CustomConfigHandler extends DefaultHandler{
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
	
			if(qName.equals(PROCEEDINGS)){
				parentElement = PROCEEDINGS;
				childElement = CONFERENCE;
				System.out.println("created");
				conference = new Conference();
				conference.key = attributes.getValue("key");
				conference.mdate = attributes.getValue("mdate");
				} else if(qName.equals(INPROCEEDINGS)){
					parentElement = INPROCEEDINGS;
					childElement = CONFERENCEARTICLE;
					inproceedings = new InProceedings();
					inproceedings.key = attributes.getValue("key");
					inproceedings.mdate = attributes.getValue("mdate");
				} else if(qName.equals(ARTICLE)){
					parentElement = ARTICLE;
					childElement = JOURNALARTICLE;
					article = new Article();
					article.key = attributes.getValue("key");
					article.mdate = attributes.getValue("mdate");
				}else if(qName.equals(WWW)){
					parentElement = WWW;
					childElement = AUTHORWWW;
					authorWWW = new WWW();
					authorWWW.key = attributes.getValue("key");
				}
			processChildElements(qName);
		}
		
		public void processChildElements(String qName){
		if(parentElement.equals(INPROCEEDINGS) || parentElement.equals(PROCEEDINGS) || parentElement.equals(ARTICLE) 
				|| parentElement.equals(WWW	) ){
			childElement = qName;
		} else if (parentElement.equalsIgnoreCase(EMPTY)) {
			parentElement = IGNORE;
			childElement = IGNORE;
		} else {
			childElement = IGNORE;
		}
		
		counter++;
	}
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			
			String input = new String(ch,start, length).trim();
			if(parentElement == PROCEEDINGS){
				conference.populateAttributes(childElement,input);
			} else if(parentElement.equals(INPROCEEDINGS)){
				inproceedings.populateAttributes(childElement,input);
			} else if(parentElement.equals(ARTICLE)){
				article.populateAttributes(childElement,input);
			} else if(parentElement.equals(WWW)){
				authorWWW.populateAttributes(childElement,input);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			
			if(qName.equals(PROCEEDINGS)){
				processProceedings();
			} else if (qName.equals(INPROCEEDINGS)){
				processInproceedings();
			} else if (qName.equals(ARTICLE)){
				processArticles();
			} else if(qName.equals(WWW)){
				processAuthorWWW();
			}
			commitIfBatchLimitReached();
		}
		
		public void commitIfBatchLimitReached(){
			if (counter % BATCHLIMIT == 0) {
				try {
					proceedingsStmt.executeBatch();
					inproceedingsStmt.executeBatch();
					journalStmt.executeBatch();
					authorStmt.executeBatch();
					wwwStmt.executeBatch();
					mySQLConnectionObject.commit();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		
		public void processProceedings(){
			parentElement = EMPTY;	
			System.out.println(conference);
			if(conference.key.equals("")||conference.booktitle.equals("")){
				System.out.println("line is empty");
				return;
			}
			try {
				proceedingsStmt = batchCreaterObj.createConferenceRecordsBatch(proceedingsStmt, conference);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public void processInproceedings(){
			parentElement = EMPTY;
			System.out.println(inproceedings);
			if(inproceedings.key.equals("")||inproceedings.booktitle.equals("")){
				System.out.print("record is not processed:"+inproceedings);
				return;
			}
			try {
				inproceedingsStmt = batchCreaterObj.createInProceedingsRecordBatch(inproceedingsStmt, inproceedings);
				if(!inproceedings.authors.isEmpty()){
					for (String author: inproceedings.authors) {
						authorStmt.setString(1, inproceedings.key); 
						authorStmt.setString(2, author);
						authorStmt.addBatch();
					}	
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void processArticles(){
			parentElement = EMPTY;
			System.out.println(article);
			if(article.key.equals("")||article.title.equals("")){
				System.out.print("record is not processed:"+article);
				return;
			}
			try {
				journalStmt = batchCreaterObj.createArticleRecordBatch(journalStmt, article);
				if(!article.authors.isEmpty()){
				for (String author: article.authors) {
					authorStmt.setString(1, article.key); 
					authorStmt.setString(2, author);
					authorStmt.addBatch();
				}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void processAuthorWWW(){
			parentElement = EMPTY;
			System.out.println(authorWWW);
			if(authorWWW.key.equals("")){
				System.out.print("record is not processed:"+authorWWW);
				return;
			}
			try {
				wwwStmt = batchCreaterObj.createWWWRecordBatch(wwwStmt, authorWWW);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		
	}
	
	public FrontEndParser(String filename) {
		batchCreaterObj = new RecordsBatchCreator();
		try {
			setUpDBConnection();
			createDBInsertStatements();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			initializeAndRunParser(filename);
	}

	public boolean initializeAndRunParser(String filename){
		boolean flag = false;
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.getXMLReader().setFeature(
					"http://xml.org/sax/features/validation", true);
			CustomConfigHandler handlerObj= new CustomConfigHandler();
			saxParser.parse(new File(filename), handlerObj);
			flag = commitRecords();
			mySQLConnectionObject.close();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean setUpDBConnection() throws SQLException{
		mySQLConnectionObject = DBConnector.getConnection();
		mySQLConnectionObject.setAutoCommit(false);
		return mySQLConnectionObject!=null;
	}
	
	public boolean createDBInsertStatements(){
		boolean status = false;
		try {
			System.out.println("initialize");
			proceedingsStmt = mySQLConnectionObject
					.prepareStatement("insert into proceedings(_key,mdate,title,booktitle,year,volume,series,publisher,editors) values (?,?,?,?,?,?,?,?,?)");
			inproceedingsStmt = mySQLConnectionObject
					.prepareStatement("insert into inproceedings(_key,mdate,title,booktitle,year,pages,crossref) values (?,?,?,?,?,?,?)");
			journalStmt = mySQLConnectionObject
					.prepareStatement("insert into journals(_key,mdate,title,volume,year,pages,journal,crossref) values (?,?,?,?,?,?,?,?)");
			authorStmt = mySQLConnectionObject.prepareStatement("insert into author (_key,name) values (?,?)");
			wwwStmt = mySQLConnectionObject
					.prepareStatement("insert into authorwww(_key,title,url,crossref,authors) values (?,?,?,?,?)");
			status = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	public boolean commitRecords(){
		boolean status = false;
		try {
			proceedingsStmt.executeBatch();
			inproceedingsStmt.executeBatch();
			journalStmt.executeBatch();
			authorStmt.executeBatch();
			wwwStmt.executeBatch();
			mySQLConnectionObject.commit();
			status = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mySQLConnectionObject.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String arg[]){
		
		String filename = arg[0];
		
		FrontEndParser parserInstance = new FrontEndParser(filename);
		
	}
}
