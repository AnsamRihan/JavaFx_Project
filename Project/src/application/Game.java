package application;

import java.util.Scanner;

public class Game extends Media {
	protected double weight;

	public Game(String code, String title, int number_of_copies, double weight) {
		super(code, title, number_of_copies);
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return "Game [code=" + code +", title =" + title + ", number of copies=" + number_of_copies + ", weight=" + weight + "]";
	}
	
	public static String[] readGameInfo(){
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter Game Info.");
		System.out.println("--------------------------------------------");
		System.out.println("write it as Title, number of copies, weight");
		String[] all = in.nextLine().split(",");
		in.close();
		if(all.length == 4) {
			return all;
		}else {
			return null;
		}
	}
	
}
