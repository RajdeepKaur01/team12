package main.java.frontend;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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

import main.java.interfaces.IFrontEnd;

public class FrontEndParser implements IFrontEnd {

	private static final String INPROCEEDINGS = "inproceeedings";
	private static final String PROCEEDINGS = "proceedings";
	private static final String ARTICLE = "articlee";
	private static final String WWW = "wwww";
	private static final String CONFERENCE = "conference";
	private static final String JOURNALARTICLE = "journalarticle";
	private static final String CONFERENCEARTICLE = "conferencearticle";
	private static final String AUTHORWWW = "authorwww";
	private static final String EMPTY = "empty";
	private static final String IGNORE = "ignore";
	private static final int BATCHLIMIT = 20000;
	private static final int LOWERYEAR = 1970;
	private static final Logger LOGGER = Logger.getLogger(FrontEndParser.class.getName());

	private String parentElement = EMPTY;
	private String childElement = EMPTY;
	private boolean testFlag = true;
	private String testTableName = "-test";
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
	private int counter = 0;
	private List<String> committeeResults;

	public FrontEndParser(){
		setupResources();
	}
	private class CustomConfigHandler extends DefaultHandler {

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {

			if (qName.equals(PROCEEDINGS)) {
				parentElement = PROCEEDINGS;
				childElement = CONFERENCE;
				LOGGER.info("created");
				conference = new Conference();
				conference.key = attributes.getValue("key");
				conference.mdate = attributes.getValue("mdate");
			} else if (qName.equals(INPROCEEDINGS)) {
				parentElement = INPROCEEDINGS;
				childElement = CONFERENCEARTICLE;
				inproceedings = new InProceedings();
				inproceedings.key = attributes.getValue("key");
				inproceedings.mdate = attributes.getValue("mdate");
			} else if (qName.equals(ARTICLE)) {
				parentElement = ARTICLE;
				childElement = JOURNALARTICLE;
				article = new Article();
				article.key = attributes.getValue("key");
				article.mdate = attributes.getValue("mdate");
			} else if (qName.equals(WWW)) {
				parentElement = WWW;
				childElement = AUTHORWWW;
				authorWWW = new WWW();
				authorWWW.key = attributes.getValue("key");
			}
			processChildElements(qName);
		}

		public void processChildElements(String qName) {
			if (parentElement.equals(INPROCEEDINGS) || parentElement.equals(PROCEEDINGS)
					|| parentElement.equals(ARTICLE) || parentElement.equals(WWW)) {
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

			String input = new String(ch, start, length).trim();
			if (parentElement == PROCEEDINGS) {
				conference.populateAttributes(childElement, input);
			} else if (parentElement.equals(INPROCEEDINGS)) {
				inproceedings.populateAttributes(childElement, input);
			} else if (parentElement.equals(ARTICLE)) {
				article.populateAttributes(childElement, input);
			} else if (parentElement.equals(WWW)) {
				authorWWW.populateAttributes(childElement, input);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {

			if (qName.equals(PROCEEDINGS)) {
				processProceedings();
			} else if (qName.equals(INPROCEEDINGS)) {
				processInproceedings();
			} else if (qName.equals(ARTICLE)) {
				processArticles();
			} else if (qName.equals(WWW)) {
				processAuthorWWW();
			}
			commitIfBatchLimitReached();
		}

		public void commitIfBatchLimitReached() {
			if (counter % BATCHLIMIT == 0) {
				try {
					LOGGER.info("Batch limit reached, commmiting records now");
					proceedingsStmt.executeBatch();
					LOGGER.info("Executed Proceedings");
					inproceedingsStmt.executeBatch();
					LOGGER.info("Executed InProceedings");
					journalStmt.executeBatch();
					LOGGER.info("Executed Journals");
					authorStmt.executeBatch();
					LOGGER.info("Executed Authors");
					wwwStmt.executeBatch();
					mySQLConnectionObject.commit();
				} catch (SQLException e) {
					LOGGER.info(e.getMessage());
				}
			}
		}

		public void processProceedings() {
			parentElement = EMPTY;
			LOGGER.info(conference.toString());
			if (conference.key.equals("") || conference.booktitle.equals("") || 
					validateYear(conference.year)) {
				LOGGER.info("line is empty");
				return;
			}
			try {
				proceedingsStmt = batchCreaterObj.createConferenceRecordsBatch(proceedingsStmt, conference);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void processInproceedings() {
			parentElement = EMPTY;
			LOGGER.info(inproceedings.toString());
			if (inproceedings.key.equals("") || inproceedings.booktitle.equals("")||
					validateYear(inproceedings.year)) {
				System.out.print("record is not processed:" + inproceedings);
				return;
			}
			try {
				inproceedingsStmt = batchCreaterObj.createInProceedingsRecordBatch(inproceedingsStmt, inproceedings);
				if (!inproceedings.authors.isEmpty()) {
					for (String author : inproceedings.authors) {
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

		public void processArticles() {
			parentElement = EMPTY;
			LOGGER.info(article.toString());
			if (article.key.equals("") || article.title.equals("") ||
					validateYear(article.year)) {
				LOGGER.info("record is not processed:" + article);
				return;
			}
			try {
				journalStmt = batchCreaterObj.createArticleRecordBatch(journalStmt, article);
				if (!article.authors.isEmpty()) {
					for (String author : article.authors) {
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

		public void processAuthorWWW() {
			parentElement = EMPTY;
			LOGGER.info(authorWWW.toString());
			if (authorWWW.key.equals("")) {
				LOGGER.info("record is not processed:" + authorWWW.toString());
				return;
			}
			try {
				wwwStmt = batchCreaterObj.createWWWRecordBatch(wwwStmt, authorWWW);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public boolean validateYear(int year){
			int inputYear = year;
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			return (inputYear >= LOWERYEAR && inputYear <= currentYear) ;
	}
	}

	public void setupResources() {
		batchCreaterObj = new RecordsBatchCreator();
		committeInstance = new CommitteesInfoParser();
		try {
			setUpDBConnection();
			createPreparedStatements();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public boolean initializeAndRunSAXParser(String filePath) {
		boolean flag = false;
		if (filePath.equals("")) {
			return flag;
		} else {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			try {
				SAXParser saxParser = factory.newSAXParser();
				saxParser.getXMLReader().setFeature("http://xml.org/sax/features/validation", true);
				CustomConfigHandler handlerObj = new CustomConfigHandler();
				saxParser.parse(new File(filePath), handlerObj);
				flag = true;

			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	@Override
	public boolean initializeAndRunCommitteParser(String folderPath) {
		if (folderPath.equals("")) {
			return false;
		} else {
			return setCommitteeRecords(committeInstance.runCommitteeParser(folderPath));
		}
	}

	public boolean setUpDBConnection() throws SQLException {
		mySQLConnectionObject = DBConnector.getConnection();
		mySQLConnectionObject.setAutoCommit(false);
		return mySQLConnectionObject != null;
	}

	public boolean createPreparedStatements() {
		boolean status = false;
		if(!testFlag) {
			testTableName ="";
		}
		try {
			proceedingsStmt = mySQLConnectionObject.prepareStatement(
					"insert into proceedings"+testTableName+"(_key,mdate,title,booktitle,year,volume,series,publisher,editors) values (?,?,?,?,?,?,?,?,?)");
			inproceedingsStmt = mySQLConnectionObject.prepareStatement(
					"insert into inproceedings"+testTableName+"(_key,mdate,title,booktitle,year,pages,crossref) values (?,?,?,?,?,?,?)");
			journalStmt = mySQLConnectionObject.prepareStatement(
					"insert into journals"+testTableName+"(_key,mdate,title,volume,year,pages,journal,crossref) values (?,?,?,?,?,?,?,?)");
			authorStmt = mySQLConnectionObject.prepareStatement("insert into author"+testTableName+" (_key,name) values (?,?)");
			wwwStmt = mySQLConnectionObject
					.prepareStatement("insert into authorwww"+testTableName+"(_key,title,url,crossref,authors) values (?,?,?,?,?)");
			committeeInfoStmt = mySQLConnectionObject
					.prepareStatement("UPDATE author"+testTableName+" SET confName = ? ,confYear = ? ,title = ? WHERE name=?");
			status = true;
			LOGGER.info(" PreparedStatements initalized");
		} catch (SQLException e) {
			LOGGER.warning("Unable to create Prepared Statements");
			e.printStackTrace();
		}
		return status;
	}

	public boolean setCommitteeRecords(List<String> commRecrods) {
		boolean flag = false;
		int c = 0;
		try {

			for (String s : commRecrods) {
				String[] output = s.split("->");
				committeeInfoStmt.setString(1, output[0]);
				committeeInfoStmt.setInt(2, Integer.parseInt(output[1]));
				committeeInfoStmt.setString(3, output[3]);
				committeeInfoStmt.setString(4, output[2]);
				committeeInfoStmt.addBatch();
				c++;
				if (c % 100 == 0) {
					LOGGER.info("Executing batch of 100");
					committeeInfoStmt.executeBatch();
					mySQLConnectionObject.commit();
					LOGGER.info("Executed batch of 100 " + System.currentTimeMillis());
				}
			}
			LOGGER.info("committeInfoBatch batch updated");
			flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean insertRecordsInDatabase() {
		boolean status = false;
		try {
			proceedingsStmt.executeBatch();
			LOGGER.info("Executed Proceedings");
			inproceedingsStmt.executeBatch();
			LOGGER.info("Executed InProceedings");
			journalStmt.executeBatch();
			LOGGER.info("Executed Journals");
			authorStmt.executeBatch();
			LOGGER.info("Executed Authors");
			wwwStmt.executeBatch();
			LOGGER.info("Executed WWWW");
			committeeInfoStmt.executeBatch();
			LOGGER.info("Executed WWWW");
			mySQLConnectionObject.commit();
			status = true;
			LOGGER.info("records committed in DB Successfull");
			mySQLConnectionObject.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String arg[]) {
		 long lStartTime = System.nanoTime();
		LOGGER.setLevel(Level.INFO);
		if (arg.length == 2 && !arg[0].isEmpty() && !arg[1].isEmpty()) {
			FrontEndParser parserObj = new FrontEndParser();
			parserObj.testFlag=false;
			parserObj.initializeAndRunSAXParser(arg[0]);
			parserObj.initializeAndRunCommitteParser(arg[1]);
			parserObj.insertRecordsInDatabase();
			long lEndTime = System.nanoTime();
			long output = lEndTime - lStartTime;
			System.out.println(("Elapsed time in milliseconds: " + output / 1000000));
		} else {
			LOGGER.severe("Insufficent number of arguments");
		}

	}
}
