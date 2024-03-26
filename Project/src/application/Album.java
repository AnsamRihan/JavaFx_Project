package application;

import java.util.Scanner;

public class Album extends Media{
	protected String artist, songs;

	public Album(String code, String title, int number_of_copies, String artist, String songs){
		super(code, title, number_of_copies);
		this.artist = artist;
		this.songs = songs;
	}

	public String getArtist() {
		return artist;
	}

	public String getSongs() {
		return songs;
	}
	
	@Override
	public String toString() {
		return "Album [code=" + code +", title =" + title + ", number of copies=" + number_of_copies + ", artist=" + artist + ", songs=" + songs + "]";
	}
	
	public static String[] readAlbumInfo(){
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter album Info.");
		System.out.println("--------------------------------------------");
		System.out.println("write it as Title, number of copies, artist, songs ( song1 - song2 - ..)");
		String[] all = in.nextLine().split(",");
		in.close();
		if(all.length == 5) {
			return all;
		}else {
			return null;
		}
	}
	
}

