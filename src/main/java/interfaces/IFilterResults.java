package main.java.interfaces;

import java.util.List;

import javafx.collections.ObservableList;
import main.java.entities.*;

public interface IFilterResults {

	public List<Author> filterByName(String name, ObservableList<Author> authors);

	public List<Author> filterByResearchPaper(String num, ObservableList<Author> authors);

	public List<Author> filterByPastExperience(String exp, ObservableList<Author> authors);

	// public List<Author> filterByYearsOnCommittee (int yearsAsCommitteeMem,
	// List<Author> authors);

	// public List<Author> filterByAreaOfExpertise (String areaOfExpertise ,
	// List<Author> authors);

}
