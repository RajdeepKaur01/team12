package main.java.search;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import main.java.entities.Author;
import main.java.interfaces.IFilterResults;
import main.java.view.SearchResultView;

public class FilterSearch implements IFilterResults {

	SearchResultView sv = new SearchResultView();

	/**
	 * 
	 * @param name - the author name to filter the result list by
	 * @param authors - list of authors to be filtered
	 * @return List<Author>
	 */
	@Override
	public List<Author> filterByName(String name, ObservableList<Author> authors) {
		List<Author> filterAuth = new ArrayList<Author>();
		for (Author a : authors) {
			if (a.getName().toLowerCase().contains(name.toLowerCase()))
				filterAuth.add(a);
		}
		return filterAuth;
	}

	/**
	 * 
	 * @param num - # of research papers to filter the author list by
	 * @param authors - list of authors to be filtered
	 * @return List<Author>
	 */
	@Override
	public List<Author> filterByResearchPaper(String num, ObservableList<Author> authors) {
		List<Author> filterAuth = new ArrayList<Author>();
		//Check if input is a valid number between 0 and 1000
		if (num.matches("[0-9]+") && Integer.parseInt(num) > 0 && Integer.parseInt(num) <= 1000) {
			System.out.println("number");
			for (Author a : authors) {
				if (a.getPaperKeys().size() == Integer.parseInt(num))
					filterAuth.add(a);
			}
			return filterAuth;
		}
		
		//If not a valid number, display an alert mentioning the same to the user
		sv.generateAlert("Enter numeric value for No of research paper(0-1000)!");
		return authors;

	}

	/**
	 * 
	 * @param exp - # of years of experience to be used as the filter criterion
	 * @param authors - list of authors to be filtered
	 * @return List<Author>
	 */
	@Override
	public List<Author> filterByPastExperience(String exp, ObservableList<Author> authors) {
		List<Author> filterAuth = new ArrayList<Author>();
		//Check if # of years are valid, between 0 - 100
		if ((!exp.matches("[0-9]+")) || Integer.parseInt(exp) < 0 || Integer.parseInt(exp) > 99) {
			//If not a valid number, display an alert mentioning the same to the user
			sv.generateAlert("Enter numeric value for Past Experience(0-99)!");
			return authors;
		}
		for (Author a : authors) {
			if (a.getCommitteeMemberInfo().size() == Integer.parseInt(exp))
				filterAuth.add(a);
		}
		return filterAuth;
	}

}
