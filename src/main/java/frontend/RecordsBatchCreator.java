package main.java.frontend;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RecordsBatchCreator {

	Conference conferenceObj;
	InProceedings inproceedingsObj;
	Article articleObj;
	AuthorInfo wwwObj;
	PreparedStatement pStatement;

	public PreparedStatement createConferenceRecordsBatch(PreparedStatement pStmt, Conference ob) throws SQLException {
		conferenceObj = ob;
		pStatement = pStmt;
		pStatement.setString(1, conferenceObj.key);
		pStatement.setString(2, conferenceObj.mdate);
		pStatement.setString(3, conferenceObj.title);
		pStatement.setString(4, conferenceObj.booktitle);
		pStatement.setInt(5, conferenceObj.year);
		pStatement.setString(6, conferenceObj.volume);
		pStatement.setString(7, conferenceObj.series);
		pStatement.setString(8, conferenceObj.publisher);
		pStatement.setString(9, conferenceObj.editors.isEmpty() ? "" : String.join(",", conferenceObj.editors));
		pStatement.addBatch();
		return pStatement;
	}

	public PreparedStatement createInProceedingsRecordBatch(PreparedStatement pStmt, InProceedings ob)
			throws SQLException {
		inproceedingsObj = ob;
		pStatement = pStmt;
		pStatement.setString(1, inproceedingsObj.key);
		pStatement.setString(2, inproceedingsObj.mdate);
		pStatement.setString(3, inproceedingsObj.title);
		pStatement.setString(4, inproceedingsObj.booktitle);
		pStatement.setInt(5, inproceedingsObj.year);
		pStatement.setString(6, inproceedingsObj.pages);
		pStatement.setString(7, inproceedingsObj.crossref);
		pStatement.addBatch();
		return pStatement;
	}

	public PreparedStatement createArticleRecordBatch(PreparedStatement pStmt, Article ob) throws SQLException {
		articleObj = ob;
		pStatement = pStmt;
		pStatement.setString(1, articleObj.key);
		pStatement.setString(2, articleObj.mdate);
		pStatement.setString(3, articleObj.title);
		pStatement.setString(4, articleObj.volume);
		pStatement.setInt(5, articleObj.year);
		pStatement.setString(6, articleObj.pages);
		pStatement.setString(7, articleObj.journal);
		pStatement.setString(8, articleObj.crossref);
		pStatement.addBatch();
		return pStatement;
	}

	public PreparedStatement createWWWRecordBatch(PreparedStatement pStmt, AuthorInfo ob) throws SQLException {
		wwwObj = ob;
		pStatement = pStmt;
		pStatement.setString(1, wwwObj.key);
		pStatement.setString(2, wwwObj.title);
		pStatement.setString(3, wwwObj.url);
		pStatement.setString(4, wwwObj.crossref);
		pStatement.setString(5, wwwObj.authors.isEmpty() ? "" : String.join(",", wwwObj.authors));
		pStatement.addBatch();
		return pStatement;
	}

}
