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
/*
 * FrontEndParser:
 * Parses  bibliography dataset (dblp) and Committee Info dataset provided
 */
public class FrontEndParser implements IFrontEnd {

	private static final String INPROCEEDINGS = "inproceeeeeedings";
	private static final String PROCEEDINGS = "proceedings";
	private static final String ARTICLE = "articleee";
	private static final String WWW = "wwww";
	private static final String CONFERENCE = "conference";
	private static final String JOURNALARTICLE = "journalarticle";
	private static final String CONFERENCEARTICLE = "conferencearticle";
	private static final String AUTHORWWW = "authorwww";
	private static final String EMPTY = "empty";
	private static final String IGNORE = "ignore";
	private static final int BATCHLIMIT = 20000;
	private static final int LOWERYEAR = 1800;
	private static final Logger LOGGER = Logger.getLogger(FrontEndParser.class.getName());

	private String parentElement = EMPTY;
	private String childElement = EMPTY;
	private boolean testFlag = true;
	private boolean dbConnectionStatus = false;
	private String testTableName = "test";
	private Article article;
	private InProceedings inproceedings;
	private Conference conference;
	private AuthorInfo authorWWW;
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

	/*
	 * constructor that sets the initial resources required before 
	 * instantiating parsers
	 * It takes boolean flag that indicates if the object is created
	 */
	public FrontEndParser(boolean testindicator){
		this.testFlag = testindicator;
		setupResources();
	}
	/*
	 * CustomConfigHandler provides necessary methods required by 
	 *  SAX parser invokes in response to various parsing events. 
	 */
	private class CustomConfigHandler extends DefaultHandler {

		/*
		 * Determines the parentElement and childElelement of a  xml being parsed
		 * based on the element name and compares against predefined set of constants
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
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
				authorWWW = new AuthorInfo();
				authorWWW.key = attributes.getValue("key");
			}
			processChildElements(qName);
		}
		/*
		 * process child elements within a parent element and sets the child element to the 
		 * qName received from the calller 
		 */
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
		/*
		 * Extracts contents of a element and populates the various entities created 
		 * based on the parentELement
		 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
		 */
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
		/*
		 * creates preparedStatement batch for each of the following element type once end of element encountered in xml
		 * Proceedings
		 * Inproceedings
		 * Article
		 * WWW
		 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
		 */
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
		/*
		 * Executes and Commits insert statements if BATCHLIMIT reached 
		 */
		public void commitIfBatchLimitReached() {
			if (counter % BATCHLIMIT == 0) {
				try {
					LOGGER.info("Batch limit reached, commmiting records now");
					proceedingsStmt.executeBatch();
					LOGGER.info("Executed proceedingsStmt");
					mySQLConnectionObject.commit();
					proceedingsStmt.executeBatch();
					LOGGER.info("Executed Proceedings");
					inproceedingsStmt.executeBatch();
					LOGGER.info("Executed InProceedings");
					authorStmt.executeBatch();
					LOGGER.info("Executed Authors");
					journalStmt.executeBatch();
					LOGGER.info("Executed Journals");
					wwwStmt.executeBatch();
					mySQLConnectionObject.commit();
				} catch (SQLException e) {
					LOGGER.info(e.getMessage());
				}
			}
		}
		/*
		 *  validates the populated proceedings/conference object and creates
		 *  insert record batch statements for the conferences that are valid
		 */
		public void processProceedings() {
			parentElement = EMPTY;
			if (conference.key.equals("") || conference.booktitle.equals("") || 
					!validateYear(conference.year)) {
				LOGGER.severe("Skipping record not valid as per valid rules set:"+conference.toString());
				return;
			}
			try {
				proceedingsStmt = batchCreaterObj.createConferenceRecordsBatch(proceedingsStmt, conference);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		/*
		 *  validates the populated inproceedings object and creates
		 *  insert record batch statements for the inproceedings that are valid
		 */
		public void processInproceedings() {
			parentElement = EMPTY;
			if (inproceedings.key.equals("") || inproceedings.booktitle.equals("")||
					!validateYear(inproceedings.year)) {
				LOGGER.severe("Skipping record not valid as per valid rules set:" + inproceedings.toString());
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
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}
		}

		/*
		 *  validates the populated article object and creates
		 *  insert record batch statements for the articles that are valid
		 */
		public void processArticles() {
			parentElement = EMPTY;
			//LOGGER.info(article.toString());
			if (article.key.equals("") || article.title.equals("") ||
					!validateYear(article.year)) {
				LOGGER.severe("Skipping record not valid as per valid rules set:" + article.toString());
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
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}
		}

		/*
		 *  validates the populated authorWWW object and creates
		 *  insert record batch statements for the www objects that are valid
		 */
		public void processAuthorWWW() {
			parentElement = EMPTY;
			LOGGER.info(authorWWW.toString());
			if (authorWWW.key.equals("")) {
				LOGGER.severe("record is not processed:" + authorWWW.toString());
				return;
			}
			try {
				wwwStmt = batchCreaterObj.createWWWRecordBatch(wwwStmt, authorWWW);

			} catch (SQLException e) {
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}
		}
		/*
		 *  helper method to validate a given year
		 */
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
			if(setUpDBConnection())createPreparedStatements();
		} catch (SQLException e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}
	}
	/*
	 * Intializes SAX parser to parse a given xml and build inproceedings,
	 * proceedings, article, author objects that are stored in database
	 * @see main.java.interfaces.IFrontEnd#initializeAndRunSAXParser(java.lang.String)
	 */
	@Override
	public boolean initializeAndRunSAXParser(String filePath) {
		boolean flag = false;
		if (filePath.equals("") || !dbConnectionStatus) {
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
				LOGGER.warning(e.getMessage());
			//	e.printStackTrace();
			} catch (SAXException e) {
				LOGGER.warning(e.getMessage());
				//e.printStackTrace();
			} catch (IOException e) {
				LOGGER.warning(e.getMessage());
				//e.printStackTrace();
			}
		}
		return flag;
	}
	/*
	 * Initializes java committee parser and takes folder path that contains
	 * the files to be parsed
	 * @see main.java.interfaces.IFrontEnd#initializeAndRunCommitteParser(java.lang.String)
	 */
	@Override
	public boolean initializeAndRunCommitteeParser(String folderPath) {
		if (folderPath.equals("") || !dbConnectionStatus) {
			return false;
		} else {
			return setCommitteeRecords(committeInstance.runCommitteeParser(folderPath));
		}
	}
	/*
	 * Gets a DB connection object
	 */
	public boolean setUpDBConnection() throws SQLException {
		mySQLConnectionObject = DBConnector.getConnection();
		if(mySQLConnectionObject!=null){
			mySQLConnectionObject.setAutoCommit(false);
			this.dbConnectionStatus = true;
		}
		return dbConnectionStatus;
	}
	/*
	 * Defines the PreparedStatements for various entities to be stored in 
	 * Database
	 */
	public boolean createPreparedStatements() {
		boolean status = false;
		try {
			if(!testFlag){
				testTableName="";
			}
			proceedingsStmt = mySQLConnectionObject.prepareStatement(
					"insert into proceedings"+testTableName+"(_key,mdate,title,booktitle,year,volume,series,publisher,editors) values (?,?,?,?,?,?,?,?,?)");
			inproceedingsStmt = mySQLConnectionObject.prepareStatement(
					"insert into inproceedings"+testTableName+"(_key,mdate,title,booktitle,year,pages,crossref) values (?,?,?,?,?,?,?)");
			journalStmt = mySQLConnectionObject.prepareStatement(
					"insert into journals"+testTableName+"(_key,mdate,title,volume,year,pages,journal,crossref) values (?,?,?,?,?,?,?,?)");
			authorStmt = mySQLConnectionObject.prepareStatement("insert into author"+testTableName+" (_key,name) values (?,?)");
			wwwStmt = mySQLConnectionObject
					.prepareStatement("insert into authorinfo"+testTableName+"(_key,urltype,url,crossref,authors) values (?,?,?,?,?)");
			committeeInfoStmt = mySQLConnectionObject
					.prepareStatement("UPDATE author"+testTableName+" SET conferenceName = ? ,conferenceYear = ? ,title = ? WHERE name=?");
			status = true;
			LOGGER.info(" PreparedStatements initalized");
		} catch (SQLException e) {
			LOGGER.warning("Unable to create Prepared Statements");
			e.printStackTrace();
		}
		return status;
	}
	/*
	 * sets prepared statements for committee records
	 * and commits to DB if  batch size excceded
	 */
	public boolean setCommitteeRecords(List<String> commRecrods) {
		boolean flag = false;
		int c = 0;
		try {
			if(commRecrods==null) return flag;
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
			LOGGER.warning(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * Executes prepared statements and commits to be DB
	 * (non-Javadoc)
	 * @see main.java.interfaces.IFrontEnd#insertRecordsInDatabase()
	 */
	@Override
	public boolean insertRecordsInDatabase() {
		if(dbConnectionStatus){
			boolean status = false;
			try {

				proceedingsStmt.executeBatch();
				LOGGER.info("Executed proceedingsStmt");
				proceedingsStmt.executeBatch();
				LOGGER.info("Executed Proceedings");
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
				LOGGER.info("records committed in"+testTableName+" DB Successfull");
				mySQLConnectionObject.close();
			} catch (SQLException e) {
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}
			return true;
		} else return false;

	}
	/**
	 * Driver Program that starts the parsing process
	 * and commits records in DB
	 * @param 
	 */
	public static void main(String arg[]) {
		 long lStartTime = System.nanoTime();
		LOGGER.setLevel(Level.INFO);
		if (arg.length == 2 && !arg[0].isEmpty() && !arg[1].isEmpty()) {
			FrontEndParser parserObj = new FrontEndParser(true);
			parserObj.initializeAndRunSAXParser(arg[0]);
			parserObj.initializeAndRunCommitteeParser(arg[1]);
			parserObj.insertRecordsInDatabase();
			long lEndTime = System.nanoTime();
			long output = lEndTime - lStartTime;
			System.out.println(("Elapsed time in milliseconds: " + output / 1000000));
		} else {
			LOGGER.severe("Insufficent number of arguments");
		}

	}
}
