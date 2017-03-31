package main.java.search;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import main.java.entities.Author;
import main.java.interfaces.IFilterResults;
import main.java.view.SearchResultView;

public class FilterSearch implements IFilterResults{

	List<Author> filterAuth = new ArrayList<Author>();
	SearchResultView sv = new SearchResultView();
	
	
	@Override
	public List<Author> filterByName(String name, ObservableList<Author> authors) {
		for(Author a: authors){
			if(a.getName().toLowerCase().contains(name.toLowerCase()))
					filterAuth.add(a);
		}
		return filterAuth;
	}
	
	
	@Override
	public List<Author> filterByResearchPaper(String num, ObservableList<Author> authors) {
		if(!num.matches("[0-9]+")){
			sv.generateAlert("Enter numeric value for No of research paper!");
			return authors;
		}
		for(Author a: authors){
			if(a.getNumberOfResearchPapers() == Integer.parseInt(num))
				filterAuth.add(a);
		}
		return filterAuth;
	}
	
	
	@Override
	public List<Author> filterByPastExperience(String exp, ObservableList<Author> authors) {
		if(!exp.matches("[0-9]+")){
			sv.generateAlert("Enter numeric value for Past Experience!");
			return authors;
		}
		
		for(Author a: authors){
			if(a.getCommitteeMemberInfo().size() == Integer.parseInt(exp))
				filterAuth.add(a);
		}
		return filterAuth;
	}

}
