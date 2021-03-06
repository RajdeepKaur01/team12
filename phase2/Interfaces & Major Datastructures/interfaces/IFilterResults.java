package interfaces;

import java.util.List;

import entities.Author;

public interface IFilterResults {

	public List<Author> filterByLocation (String address, List<Author> authors);
	
	public List<Author> filterByAge (int age, List<Author> authors);
	
	public List<Author> filterByGender (String gender, List<Author> authors);
	
	public List<Author> filterByYearsOnCommittee (int yearsAsCommitteeMem, List<Author> authors);
	
	public List<Author> filterByAreaOfExpertise (String areaOfExpertise , List<Author> authors);

}
