package application;

import java.util.ArrayList;
import java.util.Scanner;

public class Customer implements Comparable<Object>{

	protected int number;
	protected String id, name, address, plan = "LIMITED";
	protected ArrayList<String> requestedMedia = new ArrayList<>();
	protected ArrayList<String> rentedMedia = new ArrayList<>(); 
	
	public Customer(String id, String name, String address, int number, String plan){
		this.id = id;
		this.name = name;
		this.address = address;
		this.number = number;
		this.plan = plan;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getId() {
		return id;
	}
	
	public ArrayList<String> getRequestedMedia() {
		return requestedMedia;
	}

	public ArrayList<String> getRentedMedia() {
		return rentedMedia;
	}

	public void setRequestedMedia(ArrayList<String> requestedMedia) {
		this.requestedMedia = requestedMedia;
	}

	public void setRentedMedia(ArrayList<String> rentedMedia) {
		this.rentedMedia = rentedMedia;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}
	
	public String getPlan() {
		return plan;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", address=" + address + ", number =" + number + ", plan=" + plan + "\n        , Rented Media =" + rentedMedia +"\n        , Media intrested in=" + requestedMedia + "]";
	}            
	
	@Override
	public int compareTo(Object o) {

		Customer temp = (Customer) o;

		return this.getId().compareTo(temp.getId());
	}
	
	public static String[] readCustomerInfo() throws WrongInputException {
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter customer Info.");
		System.out.println("--------------------------------------------");
		System.out.println("Please write it as id, name, address, plan");
		String[] all = in.nextLine().split(",");
		in.close();
		if(all.length == 4) {
			/*String name = all[0].trim();
			String address = all[1].trim();*/
			String plan = all[4].trim();
			if(plan.equalsIgnoreCase("LIMITED") || plan.equalsIgnoreCase("UNLIMITED")) {
				return all;
			}else {
				throw new WrongInputException("must be limited or unlimited");
			}
		}else {
			return null;
		}	
	}
}
