package main.java.interfaces;

import java.util.List;

import javafx.collections.ObservableList;
import main.java.entities.*;

public interface IFilterResults {

	/**
	 * 
	 * @param name - the author name to filter the result list by
	 * @param authors - list of authors to be filtered
	 * @return List<Author>
	 */
	public List<Author> filterByName(String name, ObservableList<Author> authors);

	/**
	 * 
	 * @param num - # of research papers to filter the author list by
	 * @param authors - list of authors to be filtered
	 * @return List<Author>
	 */
	public List<Author> filterByResearchPaper(String num, ObservableList<Author> authors);

	/**
	 * 
	 * @param exp - # of years of experience to be used as the filter criterion
	 * @param authors - list of authors to be filtered
	 * @return List<Author>
	 */
	public List<Author> filterByPastExperience(String exp, ObservableList<Author> authors);

}
