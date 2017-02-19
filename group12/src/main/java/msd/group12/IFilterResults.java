package msd.group12;

import java.util.List;

public interface IFilterResults {

	public List<Author> filterByLocation (String address, List<Author> listOfAuthors);
	public List<Author> filterByAge (int age, List<Author> listOfAuthors);
	public List<Author> filterByGender (String gender, List<Author> listOfAuthors);
	public List<Author> filterByYearOnCommittee (int yearsAsCommitteeMem, List<Author> listOfAuthors);
	public List<Author> filterByAreaOfExpertise (String areaOfExpertise , List<Author> listOfAuthors);

}
