package application;

import java.util.ArrayList;
import java.util.Collections;

public class MediaRentalManger implements MediaRentalInt{
	
	ArrayList<Media> media = new ArrayList<>();  
	ArrayList<Customer> customers = new ArrayList<>();
	private int limitMax = 2;

	public MediaRentalManger(ArrayList<Media> media, ArrayList<Customer> customers) {
		super();
		this.limitMax = 2;
		this.media = media;
		this.customers = customers; 
	}

	@Override
	public void addCustomer(String id, String name, String address ,int number, String plan) { 
			Customer c = new Customer(id, name, address, number, plan);
			customers.add(c); 
	}

	@Override
	public void addMovie(String code, String title, int copiesAvailable, String rating) {
		Media m = new Movie(code, title, copiesAvailable, rating);
		media.add(m);
	}

	@Override
	public void addGame(String code, String title, int copiesAvailable, double weight) {
		Media g = new Game(code, title, copiesAvailable, weight);
		media.add(g);		
	}

	@Override
	public void addAlbum(String code, String title, int copiesAvailable, String artist, String songs) {
		Media a = new Album(code, title, copiesAvailable, artist, songs);
		media.add(a);
	}

	@Override
	public void setLimitedPlanLimit(int value) {
		this.limitMax = value;
	}

	@Override
	public String getAllCustomersInfo() {
		String info = "\t\t -Customers' Information- \t\t\n";
		Collections.sort(customers);
		for(int i=0; i < customers.size(); i++) {
			info += customers.get(i).toString() + "\n-----------------------\n";
		}
		return info;
	}

	@Override
	public String getAllMediaInfo() {
		String info = "\t\t -Media Information- \t\t\n";
		Collections.sort(media);
		for(int i=0; i < media.size(); i++) {
			info += media.get(i).toString() + "\n-----------------------\n";
		}
		return info;
	}

	@Override
	public boolean addToCart(String customerid, String mediacode) {
		int iOfC = -1;
		boolean flag = false;
		
		for(int i=0; i < customers.size(); i++) {
			if(customers.get(i).getId().trim().equals(customerid)) {
				iOfC = i;
			}
		}
	
		if(iOfC != -1) {
			for(int i=0; i< customers.get(iOfC).getRequestedMedia().size(); i++){
				if(customers.get(iOfC).getRequestedMedia().get(i).trim().equals(mediacode)) {
					return false;
				}	
			}
			customers.get(iOfC).getRequestedMedia().add(mediacode);
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean removeFromCart(String customerid, String mediacode){
		int iOfC = -1;
		boolean flag = false;
		
		for(int i=0; i < customers.size(); i++) {
			if(customers.get(i).getId().trim().equals(customerid)) {
				iOfC = i;
			}
		}
		
		if(iOfC != -1) {
			for(int i=0; i< customers.get(iOfC).getRequestedMedia().size(); i++) {
				if(customers.get(iOfC).getRequestedMedia().get(i).trim().equals(mediacode)) {
					customers.get(iOfC).getRequestedMedia().remove(i);
					flag = true;
				}
			}
		}
		return flag;
	}

	@Override
	public String processRequests() {
		Collections.sort(customers);
		String printingProcess = "";
		
		for(int i=0; i < customers.size(); i++) {
			ArrayList<String> tempRequested = customers.get(i).getRequestedMedia();
			
			for(int j=0; j < tempRequested.size(); j++) {
				if(customers.get(i).getPlan().equalsIgnoreCase("LIMITED") && customers.get(i).getRentedMedia().size() >= limitMax) {
					break;
				}
				String code = tempRequested.get(j);
				for(int k=0; k < media.size(); k++) {
					if(media.get(k).getCode().equals(code.trim()) && media.get(k).getNumber_of_copies() > 0) {
						if(customers.get(i).getPlan().equalsIgnoreCase("LIMITED") && customers.get(i).getRentedMedia().size() >= limitMax) {
							ArrayList<String> tempRented = customers.get(i).getRentedMedia();
							tempRented.add(code);
							customers.get(i).setRentedMedia(tempRented);
							tempRequested.remove(j);
							j--;
							media.get(k).adjustCopiesAvailable(false);
							printingProcess += "Sending media" + code + " to customer: " + customers.get(i).getId() + "\n";
							break;
						}else if(customers.get(i).getPlan().equalsIgnoreCase("UNLIMITED")){
							ArrayList<String> tempRented = customers.get(i).getRentedMedia();
							tempRented.add(code);
							customers.get(i).setRentedMedia(tempRented);
							tempRequested.remove(j);
							j--;
							media.get(k).adjustCopiesAvailable(false);
							printingProcess += "Sending media" + code + " to customer: " + customers.get(i).getId() + "\n";
							break;

						}
					}
				}
			}
		}
			
		return printingProcess;
	}

	@Override
	public boolean returnMedia(String customerid, String mediacode) {
		int iOfC = -1;
		boolean flag = false, flag2 =false;
		
		for(int i=0; i < customers.size(); i++) {
			if(customerid.trim().equalsIgnoreCase(customers.get(i).getId().trim())) {
				iOfC = i;
				flag2 = true;
			}
		}
		if(!flag2) {
			return false;
		}
		
		Customer c = customers.get(iOfC);
		
		for(int i=0; i < c.rentedMedia.size(); i++) {
			if(mediacode.equalsIgnoreCase(c.rentedMedia.get(i).trim())) {
				flag = true;
				c.rentedMedia.remove(i);
				for(int j=0; j < media.size(); j++) {
					if(media.get(j).getCode().equals(mediacode)) {
						media.get(j).adjustCopiesAvailable(true);
					}
				}
				if(flag) {
					return true;
				}
			}
		}
		return flag;
	}

	@Override
	public ArrayList<String> searchMedia(String code, String title, String rating, String artist, String songs) {
		ArrayList<String> sortedArray = new ArrayList<>();
		
		boolean initialTitle = false, initialRating = false, initialArtist = false, initialSongs = false, initialCode = false;
		
		for(int i=0; i < media.size(); i++) {
			Media m = media.get(i);
			initialCode = (code == null || m.getCode().equalsIgnoreCase(code));
			initialTitle = (title==null || m.getTitle().equalsIgnoreCase(title));
			if(m instanceof Album) {
				Album a = (Album)m;
				initialRating = (rating == null);
				initialArtist = (artist == null || a.getArtist().equalsIgnoreCase(artist));
				initialSongs = (songs == null || a.getSongs().equalsIgnoreCase(songs));
			}else if(m instanceof Game) {
				initialRating = (rating == null);
				initialArtist = (rating == null);
				initialSongs = (songs == null);
			}else if(m instanceof Movie) {
				Movie v = (Movie)m;
				initialRating = (rating == null || v.getRating().equalsIgnoreCase(rating));
				initialArtist = (artist == null);
				initialSongs = (songs == null);
			}
			
			if(initialCode && initialTitle && initialRating && initialArtist && initialSongs) {
				sortedArray.add(m.getCode());
			}
		}
		
		Collections.sort(sortedArray);
		return sortedArray;
	}
}
