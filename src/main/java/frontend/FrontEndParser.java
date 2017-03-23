package main.java.frontend;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.omg.CORBA.FREE_MEM;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class FrontEndParser {
	
	private static final String INPROCEEDINGS = "inproceeedings";
	private static final String PROCEEDINGS = "proceedings";
	private static final String ARTICLE = "articlee";
	private static final String WWW = "wwww";
	private static final String CONFERENCE = "conference";
	private static final String JOURNALARTICLE = "journalarticle";
	private static final String CONFERENCEARTICLE ="conferencearticle";
	private static final String AUTHORWWW = "authorwww";
	private static final String EMPTY ="empty";
	private static final String IGNORE="ignore";
	private static final int BATCHLIMIT = 20000;
	private static final Logger LOGGER = Logger.getLogger(FrontEndParser.class.getName());

	
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
	PreparedStatement committeeInfoStmt;
	private Connection mySQLConnectionObject;
	private CommitteesInfoParser committeInstance;
	private int counter=0;
	private List<String> committeeResults ;
	
	private class CustomConfigHandler extends DefaultHandler{
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
	
			if(qName.equals(PROCEEDINGS)){
				parentElement = PROCEEDINGS;
				childElement = CONFERENCE;
				LOGGER.info("created");
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
					LOGGER.info(e.getMessage());
				}
			}
		}
		
		public void processProceedings(){
			parentElement = EMPTY;	
			LOGGER.info(conference.toString());
			if(conference.key.equals("")||conference.booktitle.equals("")){
				LOGGER.info("line is empty");
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
			LOGGER.info(inproceedings.toString());
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
			LOGGER.info(article.toString());
			if(article.key.equals("")||article.title.equals("")){
				LOGGER.info("record is not processed:"+article);
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
			LOGGER.info(authorWWW.toString());
			if(authorWWW.key.equals("")){
				LOGGER.info("record is not processed:"+authorWWW.toString());
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
	
	public FrontEndParser(String fileName1, String fileName2) {
		batchCreaterObj = new RecordsBatchCreator();
		try {
			setUpDBConnection();
			createDBInsertStatements();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			initializeAndRunParser(fileName1,fileName2);
	}

	public boolean initializeAndRunParser(String fileName1,String fileName2){
		boolean flag = false;
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.getXMLReader().setFeature(
					"http://xml.org/sax/features/validation", true);
			CustomConfigHandler handlerObj= new CustomConfigHandler();
			saxParser.parse(new File(fileName1), handlerObj);
			// TODO Uncomment the below call 
			/*committeInstance = new CommitteesInfoParser();
			committeeResults = committeInstance.readFilesInDirectory(fileName2);
			commitCommitteeRecords(committeeResults);
			flag = commitRecords();*/
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
			LOGGER.info("initialize");
			proceedingsStmt = mySQLConnectionObject
					.prepareStatement("insert into proceedings(_key,mdate,title,booktitle,year,volume,series,publisher,editors) values (?,?,?,?,?,?,?,?,?)");
			inproceedingsStmt = mySQLConnectionObject
					.prepareStatement("insert into inproceedings(_key,mdate,title,booktitle,year,pages,crossref) values (?,?,?,?,?,?,?)");
			journalStmt = mySQLConnectionObject
					.prepareStatement("insert into journals(_key,mdate,title,volume,year,pages,journal,crossref) values (?,?,?,?,?,?,?,?)");
			authorStmt = mySQLConnectionObject.prepareStatement("insert into author (_key,name) values (?,?)");
			wwwStmt = mySQLConnectionObject
					.prepareStatement("insert into authorwww(_key,title,url,crossref,authors) values (?,?,?,?,?)");
			committeeInfoStmt = mySQLConnectionObject
					.prepareStatement("UPDATE author SET confName = ? ,confYear = ? ,title = ? WHERE name=?");
			status = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	public boolean commitCommitteeRecords(List<String> commRecrods){
	      int c=0;
		try {

			  for(String s: commRecrods){
				  String[] output = s.split("->");
				  committeeInfoStmt.setString(1, output[0]);
				  committeeInfoStmt.setInt(2, Integer.parseInt(output[1]));
				  committeeInfoStmt.setString(3, output[3]);
				  committeeInfoStmt.setString(4, output[2]);
				  committeeInfoStmt.addBatch();
				  c++;
				  if(c%100==0){
					  LOGGER.info("Executing WWWW 100");
						committeeInfoStmt.executeBatch();
						mySQLConnectionObject.commit();
						LOGGER.info("Executed WWWW 100 "+System.currentTimeMillis());
				  }
			  }
			  LOGGER.info("committeInfoBatch batch updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public boolean commitRecords(){
		boolean status = false;
		try {
			proceedingsStmt.executeBatch();
		 LOGGER.info("Executed Proceedings"+System.currentTimeMillis());
			inproceedingsStmt.executeBatch();
			LOGGER.info("Executed InProceedings"+System.currentTimeMillis());
			journalStmt.executeBatch();
			LOGGER.info("Executed Journals"+System.currentTimeMillis());
			authorStmt.executeBatch();
			LOGGER.info("Executed Authors"+System.currentTimeMillis());
			wwwStmt.executeBatch();
			LOGGER.info("Executed WWWW"+System.currentTimeMillis());
			committeeInfoStmt.executeBatch();
			LOGGER.info("Executed WWWW"+System.currentTimeMillis());
			mySQLConnectionObject.commit();
			LOGGER.info("Committed Successfull "+System.currentTimeMillis());
			status = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mySQLConnectionObject.close();
		} catch (SQLException e) {
			LOGGER.severe("Exception occured");
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String arg[]){
		
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Calling Parser"+System.currentTimeMillis());
		new FrontEndParser(arg[0],arg[1]);
		
	}
}
