package application;

import java.util.ArrayList;

public interface MediaRentalInt {
	void addCustomer(String id, String name,String address, int number, String plan);
	void addMovie(String code, String title, int copiesAvailable,String rating);
	void addGame(String code, String title, int copiesAvailable,double weight);
	void addAlbum(String code, String title,int copiesAvailable,String artist,String songs);
	void setLimitedPlanLimit(int value);
	String getAllCustomersInfo();
	String getAllMediaInfo();
	boolean addToCart(String customerid,String mediacode);
	boolean removeFromCart(String customerid, String mediacode);
	String processRequests();
	boolean returnMedia(String customerid,String mediacode);
	ArrayList<String> searchMedia(String code, String title,String rating, String artist,String songs);
}
