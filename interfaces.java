public interface Person{
	int key;
	string name;
	String email;
	String address;
	
	public int getKey();
	public int setKey();
	
	public string getName();
	public string setName();
	
	public string getEmail();
	public string setEmail();
	
	public string getAddress();
	public string setAddress();
}

public interface Author extends Person{
	int key;
	String homepage;
	String alias;
	Note note;
	
	public int getKey();
	public int setKey();
	
	public string getHomepage();
	public string setHomepage();
	
	public string getAlias();
	public string setAlias();
	public boolean hasAlias();
	
	}
public interface User extends Person{
	String userID;
	String password;
	
	public string getUserID();
	public string setUserID();
	
	public string getPassword();
	public string setPassword();
}

public interface InProceedings {
	int key;
	string bookTitle;
	
	public int getKey();
	public int setKey();
	
	public string getBookTitle();
	public string setBookTitle();
}
public interface Proceedings extends InProceedings{
	int key;
	string title;
	int year;
	string editor;
	int volume;
	string series;
	string address;
	int month;
	string publisher;
	string organization;
	
	public int getKey();
	public int setKey();
	
	public string getTitle();
	public string setTitle();
	
	public int getYear();
	public int setYear();
	
	public string getEditor();
	public string setEditor();
	
	public int getVolume();
	public int setVolume();
	
	public string getSeries();
	public string setSeries();
	
	public string getAddress();
	public string setAddress();
	
	public int getMonth();
	public int setMonth();
	
	public string getPublisher();
	public string setPublisher();
	
	public string getOrganization();
	public string setOrganization();
	
}


public interface ResearchPaper{
	int key;
	Author author;
	string title;
	int year;
	string content;
	int pages;
	int month;
	string editor;
	Note note;
	
	public int getKey();
	public int setKey();
	
	public string getTitle();
	public string setTitle();
	
	public int getYear();
	public int setYear();
	
	public string getContent();
	public string setContent();
	
	public int getPages();
	public int setPages();
	
	public int getMonth();
	public int setMonth();
	
	public string getEditor();
	public string setEditor();
}