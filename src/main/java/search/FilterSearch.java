package main.java.search;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import main.java.entities.Author;
import main.java.interfaces.IFilterResults;
import main.java.view.SearchResultView;

public class FilterSearch implements IFilterResults {

	SearchResultView sv = new SearchResultView();

	@Override
	public List<Author> filterByName(String name, ObservableList<Author> authors) {
		List<Author> filterAuth = new ArrayList<Author>();
		for (Author a : authors) {
			if (a.getName().toLowerCase().contains(name.toLowerCase()))
				filterAuth.add(a);
		}
		return filterAuth;
	}

	@Override
	public List<Author> filterByResearchPaper(String num, ObservableList<Author> authors) {
		List<Author> filterAuth = new ArrayList<Author>();
		if (num.matches("[0-9]+") && Integer.parseInt(num) > 0 && Integer.parseInt(num) <= 1000) {
			System.out.println("number");
			for (Author a : authors) {
				if (a.getPaperKeys().size() == Integer.parseInt(num))
					filterAuth.add(a);
			}
			return filterAuth;
		}
		sv.generateAlert("Enter numeric value for No of research paper(0-1000)!");
		return authors;

	}

	@Override
	public List<Author> filterByPastExperience(String exp, ObservableList<Author> authors) {
		List<Author> filterAuth = new ArrayList<Author>();
		if ((!exp.matches("[0-9]+")) || Integer.parseInt(exp) < 0 || Integer.parseInt(exp) > 99) {
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
