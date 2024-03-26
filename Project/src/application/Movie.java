package application;

import java.util.Scanner;

public class Movie extends Media{
	
	protected String rating;

	public Movie(String code, String title, int number_of_copies, String rating){
		super(code, title, number_of_copies);
		this.rating = rating; 
	}

	public String getRating() {
		return rating;
	}

	@Override
	public String toString() {
		return "Movie [code=" + code + ", title =" + title + ", number of copies=" + number_of_copies + ", rating=" + rating + "]";
	}
	
	public static String[] readMovieInfo() throws WrongInputException {
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter Movie Info.");
		System.out.println("--------------------------------------------");
		System.out.println("write it as Title, number of copies, rating");
		String[] all = in.nextLine().split(",");
		in.close();
		if(all.length == 4) {
			String rating = all[3].trim();
			if(rating.equalsIgnoreCase("AC") || rating.equalsIgnoreCase("DR") || rating.equalsIgnoreCase("HR")) {
				return all;
			}else {
				throw new WrongInputException("should be DR or HR or AC");
			}
		}else {
			return null;
		}
	}
}
