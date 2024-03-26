package application;

public abstract class Media implements Comparable<Object>{
	protected String code, title;
	protected int number_of_copies;	
	
	@Override
	public String toString() {
		return "Media [code=" + code + ", title=" + title + ", number_of_copies=" + number_of_copies + "]";
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Media(String code, String title, int number_of_copies) {
		setTitle(title);
		setNumber_of_copies(number_of_copies); 
		setCode(code);
	}
	
	public Media() {
		
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setNumber_of_copies(int number_of_copies) {
		this.number_of_copies = number_of_copies;
	}

	public String getTitle() {
		return title;
	}

	public int getNumber_of_copies() {
		return number_of_copies;
	}
	
	@Override
	public int compareTo(Object o) {

		Media temp = (Media) o;

		return this.getCode().compareTo(temp.getCode());
	}
	
	public void adjustCopiesAvailable(boolean added) {
		if (added) {
			this.number_of_copies = this.number_of_copies + 1;
		} else {
			this.number_of_copies = this.number_of_copies - 1;
		}
	}
}
