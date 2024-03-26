
package application;
	
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;



public class RentalSystem extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {		
		ArrayList<Customer> customers = new ArrayList<>(); 
		ArrayList<Media> media = new ArrayList<>();
		MediaRentalManger mr;
		File file = new File("Files/Customers.txt");
		File file2 = new File("Files/Media.txt");
		File file3 = new File("Files/cartForCustomer.txt");
		File file4 = new File("Files/ProcessedRequests.txt");
		
		if(!file.exists()){
			System.out.println("There is no file");
			System.exit(0);
		}
	
		Scanner sc = new Scanner(file);
		File file5 = new File("Files/CustomersOutput.txt");
		File file6 = new File("Files/MediaOutput.txt");
		
		while(sc.hasNext()){
			String a = sc.nextLine(); 
			String[] arr = a.split(": ");
			String id = arr[0].trim();
			String name = arr[1].trim();
			String address = arr[2].trim();
			int number = Integer.parseInt(arr[3].trim());
			String plan = arr[4].trim();
			System.out.println();
			customers.add(new Customer(id, name, address, number, plan));
		}
			
		if(!(file2.exists())){
			System.out.println("Their is no file");
			System.exit(0);
		}
		
		sc.close();
		Scanner sc2 = new Scanner(file2);
		
		while(sc2.hasNext()){
			String b = sc2.nextLine();
			String[] arr2 = b.split(":");
			String type = arr2[0].trim();
			String code = arr2[1].trim();
			String title = arr2[2].trim();
			int numberOfCopies = Integer.parseInt(arr2[3].trim());
			if(type.equalsIgnoreCase("game") || type.equalsIgnoreCase("movie") || type.equalsIgnoreCase("album")) {
				if(type.equalsIgnoreCase("game")) {
					double weight = Double.parseDouble(arr2[4].trim());
					Media game = new Game(code, title, numberOfCopies, weight);
					media.add(game);
				}else if(type.equalsIgnoreCase("movie")) {
					String rating = arr2[4].trim();
					Media movie = new Movie(code, title, numberOfCopies, rating);
					media.add(movie);
				}else if(type.equalsIgnoreCase("album")) {
					String artist = arr2[4].trim();
					String songs = arr2[5].trim();
					Media album = new Album(code, title, numberOfCopies, artist, songs);
					media.add(album);
				}else {
					System.out.println("type should be: album, game, or movie");;
				}
			}
		}
		
		sc2.close();
		
		if(!(file3.exists())){
			System.out.println("Their is no file");
			System.exit(0);
		}
		
		Scanner sc3 = new Scanner(file3);
		int customerIndex = 0;
		
		while(sc3.hasNext()) {
			String s = sc3.nextLine();
			String[] arr3 = s.split(":");
			
			for(int i=0; i < arr3.length; i++) {
				customers.get(customerIndex).requestedMedia.add(arr3[i].trim());
			}
			customerIndex++;
		}
		
		sc3.close();
		
		if(!(file4.exists())){
			System.out.println("Their is no file");
			System.exit(0);
		}
		
		Scanner sc4 = new Scanner(file4);
		int index2 = 0;
		int max2 = customers.size();
		
		while(sc4.hasNext()) {
			String s = sc4.nextLine();
			String[] arr = s.split(":");
			for(int i=0; i < arr.length; i++) {
				if(index2 != max2 || index2 < max2) {
					customers.get(index2).rentedMedia.add(arr[i].trim());
				}
			}
			index2++;
		}
		for(int i=0; i < customers.size(); i++) {
			System.out.println(customers.get(i));
		}
		for(int i=0; i < media.size(); i++) {
			System.out.println(media.get(i));
		}
		
		sc4.close();
		mr =  new MediaRentalManger(media, customers);
		
		//------------------------------------------------------
		Font myfont = Font.font("Century Gothic", 30);
		
		//MAIN SCENE
		
		HBox all = new HBox(400);
		all.setStyle("-fx-background-color:transparent");
		
		VBox controlB = new VBox(90);
		controlB.setAlignment(Pos.CENTER_LEFT);
		controlB.setStyle("-fx-background-color:transparent");
			
		Image img1 = new Image("customer.png");
		ImageView customerIcon = new ImageView();
		customerIcon.setFitHeight(80);
		customerIcon.setPreserveRatio(true);
		customerIcon.setImage(img1);
			
		Image img2 = new Image("media.png");
		ImageView mediaIcon = new ImageView();
		mediaIcon.setFitHeight(80);
		mediaIcon.setPreserveRatio(true);
		mediaIcon.setImage(img2);
			
		Image img3 = new Image("rent.png");
		ImageView rentIcon = new ImageView();
		rentIcon.setFitHeight(80);
		rentIcon.setPreserveRatio(true);
		rentIcon.setImage(img3);
			
		Button b1 = new Button("Customer", customerIcon);
		b1.setStyle("-fx-background-color:#4a2b7a"); 
		b1.setFont(myfont);
		b1.setTextFill(Color.WHITE);
		b1.setTranslateX(225);
			
		Button b2 = new Button("  Media", mediaIcon);
		b2.setStyle("-fx-background-color:#4a2b7a");
		b2.setFont(myfont);
		b2.setTextFill(Color.WHITE);
		b2.setTranslateX(225);
		
		Button b3 = new Button("    Rent     ", rentIcon);
		b3.setStyle("-fx-background-color:#4a2b7a");
		b3.setFont(myfont);
		b3.setTextFill(Color.WHITE);
		b3.setTranslateX(225);
		
		controlB.getChildren().addAll(b1, b2, b3);
		
		VBox sysImg = new VBox(60);
		sysImg.setAlignment(Pos.CENTER_RIGHT);
		sysImg.setStyle("-fx-background-color:transparent");
			
		Image img4 = new Image("ecommerce-icon.png");
		ImageView system = new ImageView();
		system.setImage(img4); 
		system.setTranslateX(10);
			
		Text txt1 = new Text("Media Rental System");
		txt1.setFont(Font.font("Century Gothic", 57));
		txt1.setFill(Color.WHITE);
		
		sysImg.getChildren().addAll(system, txt1);
		
		all.getChildren().addAll(controlB, sysImg);

		Scene scene = new Scene(all);
		scene.setFill(Color.web("#0A0A0A"));
		
		//------------------------------------------------------
		//CUSTOMER BUTTON SCENE
		
		StackPane customerMenu = new StackPane();
		customerMenu.setStyle("-fx-background-color:transparent");
		
		Image img5 = new Image("background3.jpg");
		ImageView img5_v = new ImageView();
		img5_v.setImage(img5);
		img5_v.fitWidthProperty().bind(primaryStage.widthProperty());
		img5_v.fitHeightProperty().bind(primaryStage.heightProperty());
		
		Rectangle r1 = new Rectangle(530, 600);
		r1.setFill(Color.web("#0A0A0A"));
		r1.setStroke(Color.BLACK);
		
		VBox buttons = new VBox(30);
		buttons.setStyle("-fx-background-color:transparent");
		
		Button b4 = new Button("     Add new Customer     ");
		b4.setStyle("-fx-background-color:#4a2b7a");
		b4.setFont(myfont);
		b4.setTextFill(Color.WHITE);
		
		Button b5 = new Button("       Delete Customer       ");
		b5.setStyle("-fx-background-color:#4a2b7a");
		b5.setFont(myfont);
		b5.setTextFill(Color.WHITE);
		
		Button b6 = new Button("     Update Information    \n        about Customer");
		b6.setStyle("-fx-background-color:#4a2b7a");
		b6.setFont(myfont);
		b6.setTextFill(Color.WHITE);
		
		Button b7 = new Button("Search a Customer by id");
		b7.setStyle("-fx-background-color:#4a2b7a");
		b7.setFont(myfont);
		b7.setTextFill(Color.WHITE);
		
		HBox name_back = new HBox(20);
		name_back.setStyle("-fx-background-color:transparent");
		
		Image img6 = new Image("back.png");
		ImageView img6_v = new ImageView();
		img6_v.setFitHeight(40);
		img6_v.setPreserveRatio(true);
		img6_v.setImage(img6);
		
		Button b8 = new Button("", img6_v);
		b8.setStyle("-fx-background-color:transparent");
		Text txt2 = new Text("Customer");
		txt2.setFont(Font.font("Century Gothic", 40));
		txt2.setFill(Color.WHITE);
		name_back.getChildren().addAll(b8, txt2);
		name_back.setTranslateX(520);
		
		VBox allComponent = new VBox(80);
		allComponent.setStyle("-fx-background-color:transparent");
		allComponent.setAlignment(Pos.CENTER);
			
		buttons.getChildren().addAll(b4, b5, b6, b7);
		allComponent.getChildren().addAll(name_back, buttons);
		buttons.setAlignment(Pos.CENTER);
		
		customerMenu.getChildren().addAll(img5_v, r1, allComponent);

		Scene sceneCustomer = new Scene(customerMenu);
		sceneCustomer.setFill(Color.web("#0A0A0A"));
		
		//---------------------------------------------------------------------
		//MEDIA BUTTON SCENE
		
		StackPane mediaMenu = new StackPane();
		mediaMenu.setStyle("-fx-background-color:transparent");
		
		Image img7 = new Image("background3.jpg");
		ImageView img7_v = new ImageView();
		img7_v.setImage(img7);
		img7_v.fitWidthProperty().bind(primaryStage.widthProperty());
		img7_v.fitHeightProperty().bind(primaryStage.heightProperty());
		
		Scene sceneMedia = new Scene(mediaMenu);
		sceneMedia.setFill(Color.web("#0A0A0A"));
		
		Rectangle r2 = new Rectangle(530, 730);
		r2.setFill(Color.web("#0A0A0A"));
		r2.setStroke(Color.BLACK);
		
		VBox buttons2 = new VBox(30);
		buttons2.setStyle("-fx-background-color:transparent");
		
		Button b9 = new Button("        Add new Media        ");
		b9.setStyle("-fx-background-color:#4a2b7a");
		b9.setFont(myfont);
		b9.setTextFill(Color.WHITE);
		
		Button b10 = new Button("          Delete Media           ");
		b10.setStyle("-fx-background-color:#4a2b7a");
		b10.setFont(myfont);
		b10.setTextFill(Color.WHITE);
		
		Button b11 = new Button("    Update Information      \n         about Media");
		b11.setStyle("-fx-background-color:#4a2b7a");
		b11.setFont(myfont);
		b11.setTextFill(Color.WHITE);
		
		Button b12 = new Button("Search a Media by code");
		b12.setStyle("-fx-background-color:#4a2b7a");
		b12.setFont(myfont);
		b12.setTextFill(Color.WHITE);
		
		Button b13 = new Button("         Print All Media          \n            information");
		b13.setStyle("-fx-background-color:#4a2b7a");
		b13.setFont(myfont);
		b13.setTextFill(Color.WHITE);
		
		HBox name_back2 = new HBox(20);
		name_back2.setStyle("-fx-background-color:transparent");
		
		Image img8 = new Image("back.png");
		ImageView img8_v = new ImageView();
		img8_v.setFitHeight(40);
		img8_v.setPreserveRatio(true);
		img8_v.setImage(img8);
		
		Button b14 = new Button("", img8_v);
		b14.setStyle("-fx-background-color:transparent");
		Text txt3 = new Text("Media");
		txt3.setFont(Font.font("Century Gothic", 40));
		txt3.setFill(Color.WHITE);
		name_back2.getChildren().addAll(b14, txt3);
		name_back2.setTranslateX(520);
		
		VBox allComponent2 = new VBox(100);
		allComponent2.setStyle("-fx-background-color:transparent");
		allComponent2.setAlignment(Pos.CENTER);
			
		buttons2.getChildren().addAll(b9, b10, b11, b12, b13);
		allComponent2.getChildren().addAll(name_back2, buttons2);
		buttons2.setAlignment(Pos.CENTER);
		
		mediaMenu.getChildren().addAll(img7_v, r2, allComponent2);
		
		//---------------------------------------------------------------------
		//CUSTOMER - ADD NEW CUSTOMER SCENE
		
		GridPane addC = new GridPane();
		addC.setStyle("-fx-background-color:transparent");
		addC.setAlignment(Pos.CENTER);
		addC.setHgap(20);
		addC.setVgap(20);
		
		StackPane components = new StackPane();
		components.setStyle("-fx-background-color:transparent");
		components.setAlignment(Pos.CENTER);
		
		Image img9 = new Image("background3.jpg");
		ImageView img9_v = new ImageView();
		img9_v.setImage(img9);
		img9_v.fitWidthProperty().bind(primaryStage.widthProperty());
		img9_v.fitHeightProperty().bind(primaryStage.heightProperty());
		
		Rectangle r3 = new Rectangle(680, 650);
		r3.setFill(Color.web("#0A0A0A"));
		r3.setStroke(Color.BLACK);
		
		HBox name_back3 = new HBox(20);
		name_back3.setStyle("-fx-background-color:transparent");
		
		Image img10 = new Image("back.png");
		ImageView img10_v = new ImageView();
		img10_v.setFitHeight(40);
		img10_v.setPreserveRatio(true);
		img10_v.setImage(img10);
		
		Button b15 = new Button("", img10_v);
		b15.setStyle("-fx-background-color:transparent");
		Text txt4 = new Text("Add Customer");
		txt4.setFont(Font.font("Century Gothic", 40));
		txt4.setFill(Color.WHITE);
		name_back3.getChildren().addAll(b15, txt4);
		name_back3.setTranslateX(450);
		
		Font font = Font.font("Century Gothic", 25);
		
		Label lb1 = new Label("Cutomer ID:");
		lb1.setFont(font);
		lb1.setTextFill(Color.WHITE);
		addC.add(lb1, 0, 0);
		
		Label lb2 = new Label("Customer Name:");
		lb2.setFont(font);
		lb2.setTextFill(Color.WHITE);
		addC.add(lb2, 0, 1);
		
		Label lb3 = new Label("Customer Address:");
		lb3.setFont(font);
		lb3.setTextFill(Color.WHITE);
		addC.add(lb3, 0, 2);
		
		Label lb4 = new Label("Customer Number:");
		lb4.setFont(font);
		lb4.setTextFill(Color.WHITE);
		addC.add(lb4, 0, 3);
		
		Label lb5 = new Label("Customer Plan:");
		lb5.setFont(font);
		lb5.setTextFill(Color.WHITE);
		addC.add(lb5, 0, 4);
		
		TextField tf1 = new TextField();
		tf1.setFont(font);
		tf1.setStyle("-fx-background-color:#8e8e8e");
		addC.add(tf1, 1, 0);
		
		TextField tf2 = new TextField();
		tf2.setFont(font);
		tf2.setStyle("-fx-background-color:#8e8e8e");
		addC.add(tf2, 1, 1);
		
		TextField tf3 = new TextField();
		tf3.setFont(font);
		tf3.setStyle("-fx-background-color:#8e8e8e");
		addC.add(tf3, 1, 2);
		
		TextField tf4 = new TextField();
		tf4.setFont(font);
		tf4.setStyle("-fx-background-color:#8e8e8e");
		addC.add(tf4, 1, 3);
		
		HBox for_radiobunttons = new HBox(25);
		RadioButton rb1 = new RadioButton("LIMITED");
		rb1.setTextFill(Color.WHITE);
		rb1.setFont(font);
		RadioButton rb2 = new RadioButton("UNLIMITED");
		rb2.setTextFill(Color.WHITE);
		rb2.setFont(font);
		ToggleGroup tg1 = new ToggleGroup();
		rb1.setToggleGroup(tg1);
		rb2.setToggleGroup(tg1);
		for_radiobunttons.getChildren().addAll(rb1, rb2);
		addC.add(for_radiobunttons, 1, 4);
		
		Image img11 = new Image("add.png");
		ImageView img11_v = new ImageView();
		img11_v.setFitHeight(40);
		img11_v.setPreserveRatio(true);
		img11_v.setImage(img11);
		Button b16 = new Button(" ADD", img11_v);
		b16.setStyle("-fx-background-color:#4a2b7a");
		b16.setFont(font);
		b16.setTextFill(Color.WHITE);
		b16.setTranslateX(225);
		addC.add(b16, 0, 5);
		
		VBox allComponent3 = new VBox(100);
		allComponent3.setStyle("-fx-background-color:transparent");
		allComponent3.setAlignment(Pos.CENTER);
		
		allComponent3.getChildren().addAll(name_back3, addC);
		
		components.getChildren().addAll(img9_v, r3, allComponent3);
		
		Scene sceneAddC = new Scene(components);
		sceneAddC.setFill(Color.web("#0A0A0A"));
		
		//---------------------------------------------------------------------
		//CUSTOMER - DELETE CUSTOMER
		
		GridPane delC = new GridPane();
		delC.setStyle("-fx-background-color:transparent");
		delC.setAlignment(Pos.CENTER);
		delC.setHgap(20);
		delC.setVgap(20);
		
		StackPane components2 = new StackPane();
		components2.setStyle("-fx-background-color:transparent");
		components2.setAlignment(Pos.CENTER);
		
		Image img12 = new Image("background3.jpg");
		ImageView img12_v = new ImageView();
		img12_v.setImage(img12);
		img12_v.fitWidthProperty().bind(primaryStage.widthProperty());
		img12_v.fitHeightProperty().bind(primaryStage.heightProperty());
		
		Rectangle r4 = new Rectangle(680, 650);
		r4.setFill(Color.web("#0A0A0A"));
		r4.setStroke(Color.BLACK);
		
		HBox name_back4 = new HBox(20);
		name_back4.setStyle("-fx-background-color:transparent");
		
		Image img13 = new Image("back.png");
		ImageView img13_v = new ImageView();
		img13_v.setFitHeight(40);
		img13_v.setPreserveRatio(true);
		img13_v.setImage(img13);
		
		Button b17 = new Button("", img13_v);
		b17.setStyle("-fx-background-color:transparent");
		Text txt5 = new Text("Delete Customer");
		txt5.setFont(Font.font("Century Gothic", 40));
		txt5.setFill(Color.WHITE);
		name_back4.getChildren().addAll(b17, txt5);
		name_back4.setTranslateX(450);
		
		Label lb6 = new Label("Cutomer ID:");
		lb6.setFont(font);
		lb6.setTextFill(Color.WHITE);
		delC.add(lb6, 0, 0);
		
		Label lb7 = new Label("Customer Name:");
		lb7.setFont(font);
		lb7.setTextFill(Color.WHITE);
		delC.add(lb7, 0, 1);
		
		Label lb8 = new Label("Customer Address:");
		lb8.setFont(font);
		lb8.setTextFill(Color.WHITE);
		delC.add(lb8, 0, 2);
		
		Label lb9 = new Label("Customer Number:");
		lb9.setFont(font);
		lb9.setTextFill(Color.WHITE);
		delC.add(lb9, 0, 3);
		
		Label lb10 = new Label("Customer Plan:");
		lb10.setFont(font);
		lb10.setTextFill(Color.WHITE);
		delC.add(lb10, 0, 4);
		
		TextField tf5 = new TextField();
		tf5.setFont(font);
		tf5.setStyle("-fx-background-color:#8e8e8e");
		delC.add(tf5, 1, 0);
		
		TextField tf6 = new TextField();
		tf6.setFont(font);
		tf6.setStyle("-fx-background-color:#8e8e8e");
		delC.add(tf6, 1, 1);
		
		TextField tf7 = new TextField();
		tf7.setFont(font);
		tf7.setStyle("-fx-background-color:#8e8e8e");
		delC.add(tf7, 1, 2);
		
		TextField tf8 = new TextField();
		tf8.setFont(font);
		tf8.setStyle("-fx-background-color:#8e8e8e");
		delC.add(tf8, 1, 3);
		
		HBox for_radiobunttons2 = new HBox(25);
		RadioButton rb3 = new RadioButton("LIMITED");
		rb3.setTextFill(Color.WHITE);
		rb3.setFont(font);
		JRadioButton rb4 = new JRadioButton("UNLIMITED");
		//rb4.setTextFill(Color.WHITE);
		//rb4.setFont(font);
		ToggleGroup tg2 = new ToggleGroup();
		rb3.setToggleGroup(tg2);
		//rb4.setToggleGroup(tg2);
		for_radiobunttons2.getChildren().addAll(rb3/*, rb4*/);
		JPanel pane1 = new JPanel();
		String name = "radio buttons";
		pane1.setBorder(BorderFactory.createTitledBorder(name));
		pane1.add(rb4);
		delC.add(for_radiobunttons2, 1, 4);
		
		Image img14 = new Image("trash2.png");
		ImageView img14_v = new ImageView();
		img14_v.setFitHeight(40);
		img14_v.setPreserveRatio(true);
		img14_v.setImage(img14);
		Button b18 = new Button(" Delete", img14_v);
		b18.setStyle("-fx-background-color:#4a2b7a");
		b18.setFont(font);
		b18.setTextFill(Color.WHITE);
		b18.setTranslateX(40);
		delC.add(b18, 1, 5);
		
		Image img15 = new Image("find.png");
		ImageView img15_v = new ImageView();
		img15_v.setFitHeight(40);
		img15_v.setPreserveRatio(true);
		img15_v.setImage(img15);
		Button b19 = new Button(" Find", img15_v);
		b19.setStyle("-fx-background-color:#4a2b7a");
		b19.setFont(font);
		b19.setTextFill(Color.WHITE);
		b19.setTranslateX(130);
		delC.add(b19, 0, 5);
		
		VBox allComponent4 = new VBox(100);
		allComponent4.setStyle("-fx-background-color:transparent");
		allComponent4.setAlignment(Pos.CENTER);
		
		allComponent4.getChildren().addAll(name_back4, delC);
		
		components2.getChildren().addAll(img12_v, r4, allComponent4);
		
		Scene sceneDelC = new Scene(components2);
		sceneDelC.setFill(Color.web("#0A0A0A"));
		
		//---------------------------------------------------------------------
		//CUSTOMER - Update Information about Customer
		
		GridPane updateC = new GridPane();
		updateC.setStyle("-fx-background-color:transparent");
		updateC.setAlignment(Pos.CENTER);
		updateC.setHgap(20);
		updateC.setVgap(20);
		
		StackPane components3 = new StackPane();
		components3.setStyle("-fx-background-color:transparent");
		components3.setAlignment(Pos.CENTER);
		
		Image img16 = new Image("background3.jpg");
		ImageView img16_v = new ImageView();
		img16_v.setImage(img16);
		img16_v.fitWidthProperty().bind(primaryStage.widthProperty());
		img16_v.fitHeightProperty().bind(primaryStage.heightProperty());
		
		Rectangle r5 = new Rectangle(680, 650);
		r5.setFill(Color.web("#0A0A0A"));
		r5.setStroke(Color.BLACK);
		
		HBox name_back5 = new HBox(20);
		name_back5.setStyle("-fx-background-color:transparent");
		
		Image img17 = new Image("back.png");
		ImageView img17_v = new ImageView();
		img17_v.setFitHeight(40);
		img17_v.setPreserveRatio(true);
		img17_v.setImage(img17);
		
		Button b20 = new Button("", img17_v);
		b20.setStyle("-fx-background-color:transparent");
		Text txt6 = new Text("Update Customer Info");
		txt6.setFont(Font.font("Century Gothic", 40));
		txt6.setFill(Color.WHITE);
		name_back5.getChildren().addAll(b20, txt6);
		name_back5.setTranslateX(450);

		Label lb11 = new Label("Cutomer ID:");
		lb11.setFont(font);
		lb11.setTextFill(Color.WHITE);
		updateC.add(lb11, 0, 0);
		
		Label lb12 = new Label("Customer Name:");
		lb12.setFont(font);
		lb12.setTextFill(Color.WHITE);
		updateC.add(lb12, 0, 1);
		
		Label lb13 = new Label("Customer Address:");
		lb13.setFont(font);
		lb13.setTextFill(Color.WHITE);
		updateC.add(lb13, 0, 2);
		
		Label lb14 = new Label("Customer Number:");
		lb14.setFont(font);
		lb14.setTextFill(Color.WHITE);
		updateC.add(lb14, 0, 3);
		
		Label lb15 = new Label("Customer Plan:");
		lb15.setFont(font);
		lb15.setTextFill(Color.WHITE);
		updateC.add(lb15, 0, 4);
		
		TextField tf9 = new TextField();
		tf9.setFont(font);
		tf9.setStyle("-fx-background-color:#8e8e8e");
		updateC.add(tf9, 1, 0);
		
		TextField tf10 = new TextField();
		tf10.setFont(font);
		tf10.setStyle("-fx-background-color:#8e8e8e");
		updateC.add(tf10, 1, 1);
		
		TextField tf11 = new TextField();
		tf11.setFont(font);
		tf11.setStyle("-fx-background-color:#8e8e8e");
		updateC.add(tf11, 1, 2);
		
		TextField tf12 = new TextField();
		tf12.setFont(font);
		tf12.setStyle("-fx-background-color:#8e8e8e");
		updateC.add(tf12, 1, 3);
		
		HBox for_radiobunttons3 = new HBox(25);
		RadioButton rb5 = new RadioButton("LIMITED");
		rb5.setTextFill(Color.WHITE);
		rb5.setFont(font);
		RadioButton rb6 = new RadioButton("UNLIMITED");
		rb6.setTextFill(Color.WHITE);
		rb6.setFont(font);
		ToggleGroup tg3 = new ToggleGroup();
		rb5.setToggleGroup(tg3);
		rb6.setToggleGroup(tg3);
		for_radiobunttons3.getChildren().addAll(rb5, rb6);
		updateC.add(for_radiobunttons3, 1, 4);
		
		Image img18 = new Image("update.jpg");
		ImageView img18_v = new ImageView();
		img18_v.setFitHeight(40);
		img18_v.setPreserveRatio(true);
		img18_v.setImage(img18);
		Button b21 = new Button(" Update", img18_v);
		b21.setStyle("-fx-background-color:#4a2b7a");
		b21.setFont(font);
		b21.setTextFill(Color.WHITE);
		b21.setTranslateX(200);
		updateC.add(b21, 0, 5);
		
		VBox allComponent5 = new VBox(100);
		allComponent5.setStyle("-fx-background-color:transparent");
		allComponent5.setAlignment(Pos.CENTER);
		
		allComponent5.getChildren().addAll(name_back5, updateC);
		
		components3.getChildren().addAll(img16_v, r5, allComponent5);
		
		Scene sceneUpdateC = new Scene(components3);
		sceneUpdateC.setFill(Color.web("#0A0A0A"));
		
		//--------------------------------------------------------------------
		//CUSTOMER - Search a Customer by id
		
		GridPane searchC = new GridPane();
		searchC.setStyle("-fx-background-color:transparent");
		searchC.setAlignment(Pos.CENTER);
		searchC.setHgap(20);
		searchC.setVgap(20);
		
		StackPane components4 = new StackPane();
		components4.setStyle("-fx-background-color:transparent");
		components4.setAlignment(Pos.CENTER);
		
		Image img19 = new Image("background3.jpg");
		ImageView img19_v = new ImageView();
		img19_v.setImage(img19);
		img19_v.fitWidthProperty().bind(primaryStage.widthProperty());
		img19_v.fitHeightProperty().bind(primaryStage.heightProperty());
		
		Rectangle r6 = new Rectangle(680, 650);
		r6.setFill(Color.web("#0A0A0A"));
		r6.setStroke(Color.BLACK);
		
		HBox name_back6 = new HBox(20);
		name_back6.setStyle("-fx-background-color:transparent");
		
		Image img20 = new Image("back.png");
		ImageView img20_v = new ImageView();
		img20_v.setFitHeight(40);
		img20_v.setPreserveRatio(true);
		img20_v.setImage(img20);
		
		Button b22 = new Button("", img20_v);
		b22.setStyle("-fx-background-color:transparent");
		Text txt7 = new Text("Search For Customer");
		txt7.setFont(Font.font("Century Gothic", 40));
		txt7.setFill(Color.WHITE);
		name_back6.getChildren().addAll(b22, txt7);
		name_back6.setTranslateX(450);

		Label lb16 = new Label("Cutomer ID:");
		lb16.setFont(font);
		lb16.setTextFill(Color.WHITE);
		searchC.add(lb16, 0, 0);
		
		Label lb17 = new Label("Customer Name:");
		lb17.setFont(font);
		lb17.setTextFill(Color.WHITE);
		searchC.add(lb17, 0, 1);
		
		Label lb18 = new Label("Customer Address:");
		lb18.setFont(font);
		lb18.setTextFill(Color.WHITE);
		searchC.add(lb18, 0, 2);
		
		Label lb19 = new Label("Customer Number:");
		lb19.setFont(font);
		lb19.setTextFill(Color.WHITE);
		searchC.add(lb19, 0, 3);
		
		Label lb20 = new Label("Customer Plan:");
		lb20.setFont(font);
		lb20.setTextFill(Color.WHITE);
		searchC.add(lb20, 0, 4);
		
		TextField tf13 = new TextField();
		tf13.setFont(font);
		tf13.setStyle("-fx-background-color:#8e8e8e");
		searchC.add(tf13, 1, 0);
		
		TextField tf14 = new TextField();
		tf14.setFont(font);
		tf14.setStyle("-fx-background-color:#8e8e8e");
		searchC.add(tf14, 1, 1);
		
		TextField tf15 = new TextField();
		tf15.setFont(font);
		tf15.setStyle("-fx-background-color:#8e8e8e");
		searchC.add(tf15, 1, 2);
		
		TextField tf16 = new TextField();
		tf16.setFont(font);
		tf16.setStyle("-fx-background-color:#8e8e8e");
		searchC.add(tf16, 1, 3);
		
		HBox for_radiobunttons4 = new HBox(25);
		RadioButton rb7 = new RadioButton("LIMITED");
		rb7.setTextFill(Color.WHITE);
		rb7.setFont(font);
		RadioButton rb8 = new RadioButton("UNLIMITED");
		rb8.setTextFill(Color.WHITE);
		rb8.setFont(font);
		ToggleGroup tg4 = new ToggleGroup();
		rb7.setToggleGroup(tg4);
		rb8.setToggleGroup(tg4);
		for_radiobunttons4.getChildren().addAll(rb7, rb8);
		searchC.add(for_radiobunttons4, 1, 4);
		
		Image img21 = new Image("find.png");
		ImageView img21_v = new ImageView();
		img21_v.setFitHeight(40);
		img21_v.setPreserveRatio(true);
		img21_v.setImage(img21);
		Button b23 = new Button(" Search", img21_v);
		b23.setStyle("-fx-background-color:#4a2b7a");
		b23.setFont(font);
		b23.setTextFill(Color.WHITE);
		b23.setTranslateX(200);
		searchC.add(b23, 0, 5);
		
		VBox allComponent6 = new VBox(80);
		allComponent6.setStyle("-fx-background-color:transparent");
		allComponent6.setAlignment(Pos.CENTER);
		
		allComponent6.getChildren().addAll(name_back6, searchC);
		
		components4.getChildren().addAll(img19_v, r6, allComponent6);
		
		Scene sceneSearchC = new Scene(components4);
		sceneSearchC.setFill(Color.web("#0A0A0A"));
		
		//---------------------------------------------------------------------
		//MEDIA - Add media
		
		GridPane addM = new GridPane();
		addM.setStyle("-fx-background-color:transparent");
		addM.setAlignment(Pos.CENTER);
		addM.setHgap(20);
		addM.setVgap(20);
		
		StackPane components5 = new StackPane();
		components5.setStyle("-fx-background-color:transparent");
		components5.setAlignment(Pos.CENTER);
		
		Image img22 = new Image("background3.jpg");
		ImageView img22_v = new ImageView();
		img22_v.setImage(img22);
		img22_v.fitWidthProperty().bind(primaryStage.widthProperty());
		img22_v.fitHeightProperty().bind(primaryStage.heightProperty());
		
		Rectangle r7 = new Rectangle(680, 700);
		r7.setFill(Color.web("#0A0A0A"));
		r7.setStroke(Color.BLACK);
		
		HBox name_back7 = new HBox(20);
		name_back7.setStyle("-fx-background-color:transparent");
		
		Image img23 = new Image("back.png");
		ImageView img23_v = new ImageView();
		img23_v.setFitHeight(40);
		img23_v.setPreserveRatio(true);
		img23_v.setImage(img23);
		
		Button b24 = new Button("", img23_v);
		b24.setStyle("-fx-background-color:transparent");
		Text txt8 = new Text("Add Media");
		txt8.setFont(Font.font("Century Gothic", 40));
		txt8.setFill(Color.WHITE);
		name_back7.getChildren().addAll(b24, txt8);
		name_back7.setTranslateX(450);
		
		Label lb21 = new Label("Media Type:");
		lb21.setFont(font);
		lb21.setTextFill(Color.WHITE);
		addM.add(lb21, 0, 0);
		
		Label lb22 = new Label("Media Code:");
		lb22.setFont(font);
		lb22.setTextFill(Color.WHITE);
		addM.add(lb22, 0, 1);
		
		Label lb23 = new Label("Media Title:");
		lb23.setFont(font);
		lb23.setTextFill(Color.WHITE);
		addM.add(lb23, 0, 2);
		
		Label lb24 = new Label("Number Of Copies:");
		lb24.setFont(font);
		lb24.setTextFill(Color.WHITE);
		addM.add(lb24, 0, 3);
		
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("Movie", "Game", "Album");
		comboBox.setMinWidth(330);
		comboBox.setStyle("-fx-background-color:#8e8e8e; -fx-font: 25px \"Century Gothic\";");
		addM.add(comboBox, 1, 0);
		
		TextField tf17 = new TextField();
		tf17.setFont(font);
		tf17.setStyle("-fx-background-color:#8e8e8e");
		addM.add(tf17, 1, 1);
		
		TextField tf18 = new TextField();
		tf18.setFont(font);
		tf18.setStyle("-fx-background-color:#8e8e8e");
		addM.add(tf18, 1, 2);
		
		TextField tf19 = new TextField();
		tf19.setFont(font);
		tf19.setStyle("-fx-background-color:#8e8e8e");
		addM.add(tf19, 1, 3);
		
		StackPane labels1 = new StackPane();
		labels1.setStyle("-fx-background-color:transparent");
		labels1.setAlignment(Pos.CENTER_LEFT);
		
		Label lb25 = new Label("Movie Rating:");
		lb25.setFont(font);
		lb25.setTextFill(Color.WHITE);
		lb25.setVisible(false);
		
		Label lb26 = new Label("Game Weight:");
		lb26.setFont(font);
		lb26.setTextFill(Color.WHITE);
		lb26.setVisible(false);
		
		Label lb27 = new Label("Album Artist:");
		lb27.setFont(font);
		lb27.setTextFill(Color.WHITE);
		lb27.setVisible(false);
		
		labels1.getChildren().addAll(lb25, lb26, lb27);
		addM.add(labels1, 0, 4);
		
		StackPane tf_Rating = new StackPane();
		tf_Rating.setStyle("-fx-background-color:transparent");
		
		HBox for_radiobuttons5 = new HBox(40);
		for_radiobuttons5.setAlignment(Pos.CENTER);
		for_radiobuttons5.setStyle("-fx-background-color:transparent");
		RadioButton rb9 = new RadioButton("DR");
		rb9.setTextFill(Color.WHITE);
		rb9.setFont(font);
		RadioButton rb10 = new RadioButton("HR");
		rb10.setTextFill(Color.WHITE);
		rb10.setFont(font);
		RadioButton rb11 = new RadioButton("AC");
		rb11.setTextFill(Color.WHITE);
		rb11.setFont(font);
		
		ToggleGroup tg5 = new ToggleGroup();
		rb9.setToggleGroup(tg5);
		rb10.setToggleGroup(tg5);
		rb11.setToggleGroup(tg5);
		for_radiobuttons5.getChildren().addAll(rb9, rb10, rb11);
		for_radiobuttons5.setVisible(false);
		
		TextField tf20 = new TextField();
		tf20.setFont(font);
		tf20.setStyle("-fx-background-color:#8e8e8e");
		tf20.setVisible(false);
		
		tf_Rating.getChildren().addAll(tf20, for_radiobuttons5);
		addM.add(tf_Rating, 1, 4);
		
		Label lb28 = new Label("Album Songs");
		lb28.setFont(font);
		lb28.setTextFill(Color.WHITE);
		addM.add(lb28, 0, 5);
		lb28.setVisible(false);
		
		TextField tf21 = new TextField();
		tf21.setFont(font);
		tf21.setStyle("-fx-background-color:#8e8e8e");
		addM.add(tf21, 1, 5);
		tf21.setVisible(false);
		
		Image img24 = new Image("add.png");
		ImageView img24_v = new ImageView();
		img24_v.setFitHeight(40);
		img24_v.setPreserveRatio(true);
		img24_v.setImage(img24);
		Button b25 = new Button(" ADD", img24_v);
		b25.setStyle("-fx-background-color:#4a2b7a");
		b25.setFont(font);
		b25.setTextFill(Color.WHITE);
		b25.setTranslateX(225);
		addM.add(b25, 0, 6);
		
		VBox allComponent7 = new VBox(80);
		allComponent7.setStyle("-fx-background-color:transparent");
		allComponent7.setAlignment(Pos.CENTER);
		
		allComponent7.getChildren().addAll(name_back7, addM);
		
		components5.getChildren().addAll(img22_v, r7, allComponent7);
		
		Scene sceneAddM = new Scene(components5);
		sceneAddM.setFill(Color.web("#0A0A0A"));
		
		//---------------------------------------------------------------------
		//Media - Delete Media
		
		GridPane delM = new GridPane();
		delM.setStyle("-fx-background-color:transparent");
		delM.setAlignment(Pos.CENTER);
		delM.setHgap(20);
		delM.setVgap(20);
		
		StackPane components6 = new StackPane();
		components6.setStyle("-fx-background-color:transparent");
		components6.setAlignment(Pos.CENTER);
		
		Image img25 = new Image("background3.jpg");
		ImageView img25_v = new ImageView();
		img25_v.setImage(img25);
		img25_v.fitWidthProperty().bind(primaryStage.widthProperty());
		img25_v.fitHeightProperty().bind(primaryStage.heightProperty());
		
		Rectangle r8 = new Rectangle(680, 700);
		r8.setFill(Color.web("#0A0A0A"));
		r8.setStroke(Color.BLACK);
		
		HBox name_back8 = new HBox(20);
		name_back8.setStyle("-fx-background-color:transparent");
		
		Image img26 = new Image("back.png");
		ImageView img26_v = new ImageView();
		img26_v.setFitHeight(40);
		img26_v.setPreserveRatio(true);
		img26_v.setImage(img26);
		
		Button b26 = new Button("", img26_v);
		b26.setStyle("-fx-background-color:transparent");
		Text txt9 = new Text("Delete Media");
		txt9.setFont(Font.font("Century Gothic", 40));
		txt9.setFill(Color.WHITE);
		name_back8.getChildren().addAll(b26, txt9);
		name_back8.setTranslateX(450);
		
		Label lb29 = new Label("Media Type:");
		lb29.setFont(font);
		lb29.setTextFill(Color.WHITE);
		delM.add(lb29, 0, 0);
		
		Label lb30 = new Label("Media Code:");
		lb30.setFont(font);
		lb30.setTextFill(Color.WHITE);
		delM.add(lb30, 0, 1);
		
		Label lb31 = new Label("Media Title:");
		lb31.setFont(font);
		lb31.setTextFill(Color.WHITE);
		delM.add(lb31, 0, 2);
		
		Label lb32 = new Label("Number Of Copies:");
		lb32.setFont(font);
		lb32.setTextFill(Color.WHITE);
		delM.add(lb32, 0, 3);
		
		ComboBox<String> comboBox2 = new ComboBox<>();
		comboBox2.getItems().addAll("Movie", "Game", "Album");
		comboBox2.setMinWidth(330);
		comboBox2.setStyle("-fx-background-color:#8e8e8e; -fx-font: 25px \"Century Gothic\";");
		delM.add(comboBox2, 1, 0);
		
		TextField tf22 = new TextField();
		tf22.setFont(font);
		tf22.setStyle("-fx-background-color:#8e8e8e");
		delM.add(tf22, 1, 1);
		
		TextField tf23 = new TextField();
		tf23.setFont(font);
		tf23.setStyle("-fx-background-color:#8e8e8e");
		delM.add(tf23, 1, 2);
		
		TextField tf24 = new TextField();
		tf24.setFont(font);
		tf24.setStyle("-fx-background-color:#8e8e8e");
		delM.add(tf24, 1, 3);
		
		StackPane labels2 = new StackPane();
		labels2.setStyle("-fx-background-color:transparent");
		labels2.setAlignment(Pos.CENTER_LEFT);
		
		Label lb33 = new Label("Movie Rating:");
		lb33.setFont(font);
		lb33.setTextFill(Color.WHITE);
		lb33.setVisible(false);
		
		Label lb34 = new Label("Game Weight:");
		lb34.setFont(font);
		lb34.setTextFill(Color.WHITE);
		lb34.setVisible(false);;
		
		Label lb35 = new Label("Album Artist:");
		lb35.setFont(font);
		lb35.setTextFill(Color.WHITE);
		lb35.setVisible(false);
		
		labels2.getChildren().addAll(lb33, lb34, lb35);
		delM.add(labels2, 0, 4);
		
		StackPane tf_Rating2 = new StackPane();
		tf_Rating2.setStyle("-fx-background-color:transparent");
		
		HBox for_radiobuttons6 = new HBox(40);
		for_radiobuttons6.setVisible(false);
		for_radiobuttons6.setAlignment(Pos.CENTER);
		for_radiobuttons6.setStyle("-fx-background-color:transparent");
		RadioButton rb12 = new RadioButton("DR");
		rb12.setTextFill(Color.WHITE);
		rb12.setFont(font);
		RadioButton rb13 = new RadioButton("HR");
		rb13.setTextFill(Color.WHITE);
		rb13.setFont(font);
		RadioButton rb14 = new RadioButton("AC");
		rb14.setTextFill(Color.WHITE);
		rb14.setFont(font);
		
		ToggleGroup tg6 = new ToggleGroup();
		rb12.setToggleGroup(tg6);
		rb13.setToggleGroup(tg6);
		rb14.setToggleGroup(tg6);
		for_radiobuttons6.getChildren().addAll(rb12, rb13, rb14);
		
		TextField tf25 = new TextField();
		tf25.setFont(font);
		tf25.setStyle("-fx-background-color:#8e8e8e");
		tf25.setVisible(false);
		
		tf_Rating2.getChildren().addAll(tf25, for_radiobuttons6);
		delM.add(tf_Rating2, 1, 4);
		
		Label lb36 = new Label("Album Songs");
		lb36.setFont(font);
		lb36.setTextFill(Color.WHITE);
		delM.add(lb36, 0, 5);
		lb36.setVisible(false);
		
		TextField tf37 = new TextField();
		tf37.setFont(font);
		tf37.setStyle("-fx-background-color:#8e8e8e");
		delM.add(tf37, 1, 5);
		tf37.setVisible(false);
		
		Image img27 = new Image("trash2.png");
		ImageView img27_v = new ImageView();
		img27_v.setFitHeight(40);
		img27_v.setPreserveRatio(true);
		img27_v.setImage(img27);
		Button b27 = new Button(" Delete", img27_v);
		b27.setStyle("-fx-background-color:#4a2b7a");
		b27.setFont(font);
		b27.setTextFill(Color.WHITE);
		b27.setTranslateX(60);
		delM.add(b27, 1, 6);
		
		Image img28 = new Image("find.png");
		ImageView img28_v = new ImageView();
		img28_v.setFitHeight(40);
		img28_v.setPreserveRatio(true);
		img28_v.setImage(img28);
		Button b28 = new Button(" Search", img28_v);
		b28.setStyle("-fx-background-color:#4a2b7a");
		b28.setFont(font);
		b28.setTextFill(Color.WHITE);
		b28.setTranslateX(90);
		delM.add(b28, 0, 6);
		
		VBox allComponent8 = new VBox(80);
		allComponent8.setStyle("-fx-background-color:transparent");
		allComponent8.setAlignment(Pos.CENTER);
		
		allComponent8.getChildren().addAll(name_back8, delM);
		
		components6.getChildren().addAll(img25_v, r8, allComponent8);
		
		Scene sceneDelM = new Scene(components6);
		sceneDelM.setFill(Color.web("#0A0A0A"));
		
		//--------------------------------------------------------------------
		//Media - Update Information about Media
		
		GridPane updateM = new GridPane();
		updateM.setStyle("-fx-background-color:transparent");
		updateM.setAlignment(Pos.CENTER);
		updateM.setHgap(20);
		updateM.setVgap(20);
		
		StackPane components7 = new StackPane();
		components7.setStyle("-fx-background-color:transparent");
		components7.setAlignment(Pos.CENTER);
		
		Image img29 = new Image("background3.jpg");
		ImageView img29_v = new ImageView();
		img29_v.setImage(img29);
		img29_v.fitWidthProperty().bind(primaryStage.widthProperty());
		img29_v.fitHeightProperty().bind(primaryStage.heightProperty());
		
		Rectangle r9 = new Rectangle(680, 700);
		r9.setFill(Color.web("#0A0A0A"));
		r9.setStroke(Color.BLACK);
		
		HBox name_back9 = new HBox(20);
		name_back9.setStyle("-fx-background-color:transparent");
		
		Image img30 = new Image("back.png");
		ImageView img30_v = new ImageView();
		img30_v.setFitHeight(40);
		img30_v.setPreserveRatio(true);
		img30_v.setImage(img30);
		
		Button b29 = new Button("", img30_v);
		b29.setStyle("-fx-background-color:transparent");
		Text txt10 = new Text("Update Media Info");
		txt10.setFont(Font.font("Century Gothic", 40));
		txt10.setFill(Color.WHITE);
		name_back9.getChildren().addAll(b29, txt10);
		name_back9.setTranslateX(450);
		
		Label lb37 = new Label("Media Type:");
		lb37.setFont(font);
		lb37.setTextFill(Color.WHITE);
		updateM.add(lb37, 0, 0);
		
		Label lb38 = new Label("Media Code:");
		lb38.setFont(font);
		lb38.setTextFill(Color.WHITE);
		updateM.add(lb38, 0, 1);
		
		Label lb39 = new Label("Media Title:");
		lb39.setFont(font);
		lb39.setTextFill(Color.WHITE);
		updateM.add(lb39, 0, 2);
		
		Label lb40 = new Label("Number Of Copies:");
		lb40.setFont(font);
		lb40.setTextFill(Color.WHITE);
		updateM.add(lb40, 0, 3);
		
		ComboBox<String> comboBox3 = new ComboBox<>();
		comboBox3.getItems().addAll("Movie", "Game", "Album");
		comboBox3.setMinWidth(330);
		comboBox3.setStyle("-fx-background-color:#8e8e8e; -fx-font: 25px \"Century Gothic\";");
		updateM.add(comboBox3, 1, 0);
		
		TextField tf38 = new TextField();
		tf38.setFont(font);
		tf38.setStyle("-fx-background-color:#8e8e8e");
		updateM.add(tf38, 1, 1);
		
		TextField tf39 = new TextField();
		tf39.setFont(font);
		tf39.setStyle("-fx-background-color:#8e8e8e");
		updateM.add(tf39, 1, 2);
		
		TextField tf40 = new TextField();
		tf40.setFont(font);
		tf40.setStyle("-fx-background-color:#8e8e8e");
		updateM.add(tf40, 1, 3);
		
		StackPane labels3 = new StackPane();
		labels3.setStyle("-fx-background-color:transparent");
		labels3.setAlignment(Pos.CENTER_LEFT);
		
		Label lb41 = new Label("Movie Rating:");
		lb41.setFont(font);
		lb41.setTextFill(Color.WHITE);
		lb41.setVisible(false);
		
		Label lb42 = new Label("Game Weight:");
		lb42.setFont(font);
		lb42.setTextFill(Color.WHITE);
		lb42.setVisible(false);
		
		Label lb43 = new Label("Album Artist:");
		lb43.setFont(font);
		lb43.setTextFill(Color.WHITE);
		lb43.setVisible(false);
		
		labels3.getChildren().addAll(lb41, lb42, lb43);
		updateM.add(labels3, 0, 4);
		
		StackPane tf_Rating3 = new StackPane();
		tf_Rating3.setStyle("-fx-background-color:transparent");
		
		HBox for_radiobuttons7 = new HBox(40);
		for_radiobuttons7.setVisible(false);
		for_radiobuttons7.setAlignment(Pos.CENTER);
		for_radiobuttons7.setStyle("-fx-background-color:transparent");
		RadioButton rb15 = new RadioButton("DR");
		rb15.setTextFill(Color.WHITE);
		rb15.setFont(font);
		RadioButton rb16 = new RadioButton("HR");
		rb16.setTextFill(Color.WHITE);
		rb16.setFont(font);
		RadioButton rb17 = new RadioButton("AC");
		rb17.setTextFill(Color.WHITE);
		rb17.setFont(font);
		
		ToggleGroup tg7 = new ToggleGroup();
		rb15.setToggleGroup(tg7);
		rb16.setToggleGroup(tg7);
		rb17.setToggleGroup(tg7);
		for_radiobuttons7.getChildren().addAll(rb15, rb16, rb17);
		
		TextField tf41 = new TextField();
		tf41.setFont(font);
		tf41.setStyle("-fx-background-color:#8e8e8e");
		tf41.setVisible(false);
		
		tf_Rating3.getChildren().addAll(tf41, for_radiobuttons7);
		updateM.add(tf_Rating3, 1, 4);
		
		Label lb44 = new Label("Album Songs");
		lb44.setFont(font);
		lb44.setTextFill(Color.WHITE);
		updateM.add(lb44, 0, 5);
		lb44.setVisible(false);
		
		TextField tf42 = new TextField();
		tf42.setFont(font);
		tf42.setStyle("-fx-background-color:#8e8e8e");
		updateM.add(tf42, 1, 5);
		tf42.setVisible(false);
		
		Image img31 = new Image("update.jpg");
		ImageView img31_v = new ImageView();
		img31_v.setFitHeight(40);
		img31_v.setPreserveRatio(true);
		img31_v.setImage(img31);
		Button b30 = new Button(" Update", img31_v);
		b30.setStyle("-fx-background-color:#4a2b7a");
		b30.setFont(font);
		b30.setTextFill(Color.WHITE);
		b30.setTranslateX(90);
		updateM.add(b30, 0, 6);
		
		Image img44 = new Image("find.png");
		ImageView img44_v = new ImageView();
		img44_v.setFitHeight(40);
		img44_v.setPreserveRatio(true);
		img44_v.setImage(img44);
		Button b41 = new Button(" Show type", img44_v);
		b41.setStyle("-fx-background-color:#4a2b7a");
		b41.setFont(font);
		b41.setTextFill(Color.WHITE);
		b41.setTranslateX(60);
		updateM.add(b41, 1, 6);
		
		VBox allComponent9 = new VBox(80);
		allComponent9.setStyle("-fx-background-color:transparent");
		allComponent9.setAlignment(Pos.CENTER);
		
		allComponent9.getChildren().addAll(name_back9, updateM);
		
		components7.getChildren().addAll(img29_v, r9, allComponent9);
		
		Scene sceneUpdateM = new Scene(components7);
		sceneUpdateM.setFill(Color.web("#0A0A0A"));
		
		//---------------------------------------------------------------------
		//Media - Search a Media by code
		
		GridPane searchM = new GridPane();
		searchM.setStyle("-fx-background-color:transparent");
		searchM.setAlignment(Pos.CENTER);
		searchM.setHgap(20);
		searchM.setVgap(20);
		
		StackPane components8 = new StackPane();
		components8.setStyle("-fx-background-color:transparent");
		components8.setAlignment(Pos.CENTER);
		
		Image img32 = new Image("background3.jpg");
		ImageView img32_v = new ImageView();
		img32_v.setImage(img32);
		img32_v.fitWidthProperty().bind(primaryStage.widthProperty());
		img32_v.fitHeightProperty().bind(primaryStage.heightProperty());
		
		Rectangle r10 = new Rectangle(680, 700);
		r10.setFill(Color.web("#0A0A0A"));
		r10.setStroke(Color.BLACK);
		
		HBox name_back10 = new HBox(20);
		name_back10.setStyle("-fx-background-color:transparent");
		
		Image img33 = new Image("back.png");
		ImageView img33_v = new ImageView();
		img33_v.setFitHeight(40);
		img33_v.setPreserveRatio(true);
		img33_v.setImage(img33);
		
		Button b31 = new Button("", img33_v);
		b31.setStyle("-fx-background-color:transparent");
		Text txt11 = new Text("Search For Media");
		txt11.setFont(Font.font("Century Gothic", 40));
		txt11.setFill(Color.WHITE);
		name_back10.getChildren().addAll(b31, txt11);
		name_back10.setTranslateX(450);
		
		Label lb45 = new Label("Media Type:");
		lb45.setFont(font);
		lb45.setTextFill(Color.WHITE);
		searchM.add(lb45, 0, 0);
		
		Label lb46 = new Label("Media Code:");
		lb46.setFont(font);
		lb46.setTextFill(Color.WHITE);
		searchM.add(lb46, 0, 1);
		
		Label lb47 = new Label("Media Title:");
		lb47.setFont(font);
		lb47.setTextFill(Color.WHITE);
		searchM.add(lb47, 0, 2);
		
		Label lb48 = new Label("Number Of Copies:");
		lb48.setFont(font);
		lb48.setTextFill(Color.WHITE);
		searchM.add(lb48, 0, 3);
		
		ComboBox<String> comboBox4 = new ComboBox<>();
		comboBox4.getItems().addAll("Movie", "Game", "Album");
		comboBox4.setMinWidth(330);
		comboBox4.setStyle("-fx-background-color:#8e8e8e; -fx-font: 25px \"Century Gothic\";");
		searchM.add(comboBox4, 1, 0);
		
		TextField tf43 = new TextField();
		tf43.setFont(font);
		tf43.setStyle("-fx-background-color:#8e8e8e");
		searchM.add(tf43, 1, 1);
		
		TextField tf44 = new TextField();
		tf44.setFont(font);
		tf44.setStyle("-fx-background-color:#8e8e8e");
		searchM.add(tf44, 1, 2);
		
		TextField tf45 = new TextField();
		tf45.setFont(font);
		tf45.setStyle("-fx-background-color:#8e8e8e");
		searchM.add(tf45, 1, 3);
		
		StackPane labels4 = new StackPane();
		labels4.setStyle("-fx-background-color:transparent");
		labels4.setAlignment(Pos.CENTER_LEFT);
		
		Label lb49 = new Label("Movie Rating:");
		lb49.setFont(font);
		lb49.setTextFill(Color.WHITE);
		lb49.setVisible(false);
		
		Label lb50 = new Label("Game Weight:");
		lb50.setFont(font);
		lb50.setTextFill(Color.WHITE);
		lb50.setVisible(false);
		
		Label lb51 = new Label("Album Artist:");
		lb51.setFont(font);
		lb51.setTextFill(Color.WHITE);
		lb51.setVisible(false);
		
		labels4.getChildren().addAll(lb49, lb50, lb51);
		searchM.add(labels4, 0, 4);
		
		StackPane tf_Rating4 = new StackPane();
		tf_Rating4.setStyle("-fx-background-color:transparent");
		
		HBox for_radiobuttons8 = new HBox(40);
		for_radiobuttons8.setVisible(false);
		for_radiobuttons8.setAlignment(Pos.CENTER);
		for_radiobuttons8.setStyle("-fx-background-color:transparent");
		RadioButton rb18 = new RadioButton("DR");
		rb18.setTextFill(Color.WHITE);
		rb18.setFont(font);
		RadioButton rb19 = new RadioButton("HR");
		rb19.setTextFill(Color.WHITE);
		rb19.setFont(font);
		RadioButton rb20 = new RadioButton("AC");
		rb20.setTextFill(Color.WHITE);
		rb20.setFont(font);
		
		ToggleGroup tg8 = new ToggleGroup();
		rb18.setToggleGroup(tg8);
		rb19.setToggleGroup(tg8);
		rb20.setToggleGroup(tg8);
		for_radiobuttons8.getChildren().addAll(rb18, rb19, rb20);
		
		TextField tf46 = new TextField();
		tf46.setFont(font);
		tf46.setStyle("-fx-background-color:#8e8e8e");
		tf46.setVisible(false);
		
		tf_Rating4.getChildren().addAll(tf46, for_radiobuttons8);
		searchM.add(tf_Rating4, 1, 4);
		
		Label lb52 = new Label("Album Songs");
		lb52.setFont(font);
		lb52.setTextFill(Color.WHITE);
		searchM.add(lb52, 0, 5);
		lb52.setVisible(false);
		
		TextField tf47 = new TextField();
		tf47.setFont(font);
		tf47.setStyle("-fx-background-color:#8e8e8e");
		searchM.add(tf47, 1, 5);
		tf47.setVisible(false);
		
		Image img34 = new Image("find.png");
		ImageView img34_v = new ImageView();
		img34_v.setFitHeight(40);
		img34_v.setPreserveRatio(true);
		img34_v.setImage(img34);
		Button b32 = new Button(" Search", img34_v);
		b32.setStyle("-fx-background-color:#4a2b7a");
		b32.setFont(font);
		b32.setTextFill(Color.WHITE);
		b32.setTranslateX(210);
		searchM.add(b32, 0, 6);
		
		VBox allComponent10 = new VBox(80);
		allComponent10.setStyle("-fx-background-color:transparent");
		allComponent10.setAlignment(Pos.CENTER);
		
		allComponent10.getChildren().addAll(name_back10, searchM);
		
		components8.getChildren().addAll(img32_v, r10, allComponent10);
		
		Scene sceneSearchM = new Scene(components8);
		sceneSearchM.setFill(Color.web("#0A0A0A"));
		
		//---------------------------------------------------------------------
		//Media - Print All Media information
		
		StackPane components9 = new StackPane();
		components9.setStyle("-fx-background-color:transparent");
		components9.setAlignment(Pos.CENTER);
		
		Image img35 = new Image("background3.jpg");
		ImageView img35_v = new ImageView();
		img35_v.setImage(img35);
		img35_v.fitWidthProperty().bind(primaryStage.widthProperty());
		img35_v.fitHeightProperty().bind(primaryStage.heightProperty());
		
		Rectangle r11 = new Rectangle(680, 750);
		r11.setFill(Color.web("#0A0A0A"));
		r11.setStroke(Color.BLACK);
		
		HBox name_back11 = new HBox(20);
		name_back11.setStyle("-fx-background-color:transparent");
		
		Image img36 = new Image("back.png");
		ImageView img36_v = new ImageView();
		img36_v.setFitHeight(40);
		img36_v.setPreserveRatio(true);
		img36_v.setImage(img36);	
		Button b33 = new Button("", img36_v);
		b33.setStyle("-fx-background-color:transparent");
		Text txt12 = new Text("All Media Info.");
		txt12.setFont(Font.font("Century Gothic", 40));
		txt12.setFill(Color.WHITE);
		name_back11.getChildren().addAll(b33, txt12);
		name_back11.setTranslateX(450);
		
		TextArea area = new TextArea();
		area.setStyle("-fx-control-inner-background: #8e8e8e");
		area.setFont(font);
		area.setPrefHeight(500);
	    area.setMaxWidth(600);
	    area.setWrapText(true);
	    
	    Button b40 = new Button("Print All Media Info");
		b40.setStyle("-fx-background-color:#4a2b7a");
		b40.setFont(font);
		b40.setMaxHeight(30);
		b40.setTextFill(Color.WHITE);
		b40.setAlignment(Pos.CENTER);
		
		VBox c1 = new VBox(30);
		c1.setStyle("-fx-background-color:transparent");
		c1.setAlignment(Pos.CENTER);
		c1.getChildren().addAll(area, b40);
		
		VBox allComponent11 = new VBox(60);
		allComponent11.setStyle("-fx-background-color:transparent");
		allComponent11.setAlignment(Pos.CENTER);
		
		allComponent11.getChildren().addAll(name_back11, c1);
		
		components9.getChildren().addAll(img35_v, r11, allComponent11);
		
		Scene scenePrintAllM = new Scene(components9);
		scenePrintAllM.setFill(Color.web("#0A0A0A"));
		
		//---------------------------------------------------------------------
		//Rent Form.
		
		HBox rentF = new HBox(20);
		rentF.setStyle("-fx-background-color:transparent");
		rentF.setAlignment(Pos.CENTER);
		
		StackPane components10 = new StackPane();
		components10.setStyle("-fx-background-color:transparent");
		components10.setAlignment(Pos.CENTER);
		
		Image img37 = new Image("background3.jpg");
		ImageView img37_v = new ImageView();
		img37_v.setImage(img37);
		img37_v.fitWidthProperty().bind(primaryStage.widthProperty());
		img37_v.fitHeightProperty().bind(primaryStage.heightProperty());
		
		Rectangle r12 = new Rectangle(950, 700);
		r12.setFill(Color.web("#0A0A0A"));
		r12.setStroke(Color.BLACK);
		
		HBox name_back12 = new HBox(20);
		name_back12.setStyle("-fx-background-color:transparent");
		
		Image img38 = new Image("back.png");
		ImageView img38_v = new ImageView();
		img38_v.setFitHeight(40);
		img38_v.setPreserveRatio(true);
		img38_v.setImage(img38);
		
		Button b34 = new Button("", img38_v);
		b34.setStyle("-fx-background-color:transparent");
		Text txt13 = new Text("Rent Form.");
		txt13.setFont(Font.font("Century Gothic", 40));
		txt13.setFill(Color.WHITE);
		name_back12.getChildren().addAll(b34, txt13);
		name_back12.setTranslateX(320);
		
		GridPane areas = new GridPane();
	    areas.setStyle("-fx-background-color:transparent");
		areas.setAlignment(Pos.CENTER);
		areas.setHgap(20);
		areas.setVgap(20);
		
		Label lb53 = new Label("Customer ID:");
		lb53.setFont(font);
		lb53.setTextFill(Color.WHITE);
		
		TextField tf48 = new TextField();
		tf48.setFont(font);
		tf48.setMaxWidth(250);
		tf48.setStyle("-fx-background-color:#8e8e8e");
		
		TextArea area2 = new TextArea();
		area2.setStyle("-fx-control-inner-background: #8e8e8e");
		area2.setFont(font);
		area2.setPrefHeight(150);
	    area2.setMaxWidth(430);
	    area2.setWrapText(true);
	    areas.add(area2, 0, 1);
	    
	    Label lb54 = new Label("Media Code:");
		lb54.setFont(font);
		lb54.setTextFill(Color.WHITE);
		
		TextField tf49 = new TextField();
		tf49.setFont(font);
		tf49.setMaxWidth(250);
		tf49.setStyle("-fx-background-color:#8e8e8e");
		
		rentF.getChildren().addAll(lb53, tf48, lb54, tf49);
		
		TextArea area3 = new TextArea();
		area3.setStyle("-fx-control-inner-background: #8e8e8e");
		area3.setFont(font);
		area3.setPrefHeight(150);
	    area3.setMaxWidth(430);
	    area3.setWrapText(true);
	    areas.add(area3, 1, 1);
	    
	    Label lb55 = new Label("Customer needed Info.:      ");
	    lb55.setFont(font);
		lb55.setTextFill(Color.WHITE);
		areas.add(lb55, 0, 0);
		
		Label lb56 = new Label("Media Info.:");
		lb56.setFont(font);
		lb56.setTextFill(Color.WHITE);
		areas.add(lb56, 1, 0);
		
		HBox b = new HBox(40);
		b.setStyle("-fx-background-color:transparent");
		b.setAlignment(Pos.CENTER);
	    
	    Image img39 = new Image("tocart.jpg");
		ImageView img39_v = new ImageView();
		img39_v.setFitHeight(40);
		img39_v.setPreserveRatio(true);
		img39_v.setImage(img39);
		Button b35 = new Button(" Add To Cart", img39_v);
		b35.setStyle("-fx-background-color:#4a2b7a");
		b35.setFont(font);
		b35.setTextFill(Color.WHITE);
		
		Image img40 = new Image("processRent.png");
		ImageView img40_v = new ImageView();
		img40_v.setFitHeight(40);
		img40_v.setPreserveRatio(true);
		img40_v.setImage(img40);
		Button b36 = new Button(" Process Cart", img40_v);
		b36.setStyle("-fx-background-color:#4a2b7a");
		b36.setFont(font);
		b36.setTextFill(Color.WHITE);
		
		Image img41 = new Image("customer.png");
		ImageView img41_v = new ImageView();
		img41_v.setFitHeight(40);
		img41_v.setPreserveRatio(true);
		img41_v.setImage(img41);
		Button b37 = new Button(" Customer requestes\n           Media", img41_v);
		b37.setStyle("-fx-background-color:#4a2b7a");
		b37.setFont(font);
		b37.setTextFill(Color.WHITE);
		b37.setTranslateX(50);
		areas.add(b37, 0, 2);
		
		Image img42 = new Image("customer.png");
		ImageView img42_v = new ImageView();
		img42_v.setFitHeight(40);
		img42_v.setPreserveRatio(true);
		img42_v.setImage(img42);
		Button b38 = new Button("   Customer rented   \n           Media", img42_v);
		b38.setStyle("-fx-background-color:#4a2b7a");
		b38.setFont(font);
		b38.setTextFill(Color.WHITE);
		b38.setTranslateX(50);
		areas.add(b38, 1, 2);
		
		Image img43 = new Image("return.jpg");
		ImageView img43_v = new ImageView();
		img43_v.setFitHeight(40);
		img43_v.setPreserveRatio(true);
		img43_v.setImage(img43);
		Button b39 = new Button(" Return media", img43_v);
		b39.setStyle("-fx-background-color:#4a2b7a");
		b39.setFont(font);
		b39.setTextFill(Color.WHITE);
		
		b.getChildren().addAll(b35, b36, b39);
		
		VBox whole = new VBox(30);
		whole.setStyle("-fx-background-color:transparent");
		whole.setAlignment(Pos.CENTER);
		whole.getChildren().addAll(rentF, areas, b);
		
		VBox allComponent12 = new VBox(80);
		allComponent12.setStyle("-fx-background-color:transparent");
		allComponent12.setAlignment(Pos.CENTER);
		
		allComponent12.getChildren().addAll(name_back12, whole);
		
		components10.getChildren().addAll(img37_v, r12, allComponent12);
		
		Scene sceneRentF = new Scene(components10);
		sceneRentF.setFill(Color.web("#0A0A0A"));
		//---------------------------------------------------------------------
		//buttons scenes.
		b1.setOnAction(e -> primaryStage.setScene(sceneCustomer));
		b2.setOnAction(e -> primaryStage.setScene(sceneMedia));
		b3.setOnAction(e -> primaryStage.setScene(sceneRentF));
		
		b4.setOnAction(e -> primaryStage.setScene(sceneAddC));
		b5.setOnAction(e -> primaryStage.setScene(sceneDelC));
		b6.setOnAction(e -> primaryStage.setScene(sceneUpdateC));
		b7.setOnAction(e -> primaryStage.setScene(sceneSearchC));
		b8.setOnAction(e -> primaryStage.setScene(scene));
		
		b9.setOnAction(e -> primaryStage.setScene(sceneAddM));
		b10.setOnAction(e -> primaryStage.setScene(sceneDelM));
		b11.setOnAction(e -> primaryStage.setScene(sceneUpdateM));
		b12.setOnAction(e -> primaryStage.setScene(sceneSearchM));
		b13.setOnAction(e -> primaryStage.setScene(scenePrintAllM));
		b14.setOnAction(e -> primaryStage.setScene(scene));
		
		b15.setOnAction(e -> primaryStage.setScene(sceneCustomer));
		b16.setOnAction(e -> {
			if(rb1.isSelected()) {
				Customer c = new Customer(tf1.getText().trim(), tf2.getText().trim(), tf3.getText().trim(), Integer.parseInt(tf4.getText().trim()), "LIMITED");					
				mr.customers.add(c);
			}else if(rb2.isSelected()) {
				Customer c = new Customer(tf1.getText().trim(), tf2.getText().trim(), tf3.getText().trim(), Integer.parseInt(tf4.getText().trim()), "UNLIMITED");
				mr.customers.add(c);
			}			
			AlertType type = AlertType.CONFIRMATION;
			Alert alert = new Alert(type , "");
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.getDialogPane().setContentText("Customer Added");				
			alert.getDialogPane().setHeaderText("Customer Addition Info!");
			alert.show();
						
			try {
				FileWriter fw = new FileWriter(file5, true);
				fw.write("Customers in DATA:\n");
				fw.write(mr.getAllCustomersInfo());
				fw.write("\n\n");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		});
		
		b17.setOnAction(e -> primaryStage.setScene(sceneCustomer));
		b19.setOnAction(e -> {
			boolean flag = false;
			for(int i=0; i < mr.customers.size(); i++) {
				if(mr.customers.get(i).getId().equals(tf5.getText().trim())) {
					flag = true;
				}
			}
			if(flag == true) {
				AlertType type = AlertType.CONFIRMATION;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Customer Found");
				alert.getDialogPane().setHeaderText("Customer Found or not Found message!");
				alert.show();
			}else {
				AlertType type = AlertType.WARNING;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Customer Not Found");
				alert.getDialogPane().setHeaderText("Customer Found or not Found message!");
				alert.show();
			}
		});
		b18.setOnAction(e -> {
			int index = -1;
			boolean flag = false;
			for(int i=0; i < mr.customers.size(); i++) {
				if(mr.customers.get(i).getId().equals(tf5.getText().trim())) {
					index = i;
					flag = true;
				}
			}
			
			if(flag == true) {
				mr.customers.remove(index);
				AlertType type = AlertType.CONFIRMATION;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Customer Deleted");
				alert.getDialogPane().setHeaderText("Customer Deleting Info!");
				alert.show();
			}else {
				AlertType type = AlertType.WARNING;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Customer Not Found");
				alert.getDialogPane().setHeaderText("Customer Deleting Info!");
				alert.show();
			}
			
			try {
				FileWriter fw = new FileWriter(file5, true);
				fw.write("Customers in DATA:\n");
				fw.write(mr.getAllCustomersInfo());
				fw.write("\n\n");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		
		b20.setOnAction(e -> primaryStage.setScene(sceneCustomer));
		b21.setOnAction(e -> {
			boolean flag = false;
			int indexI = -1;
			for(int i=0; i < mr.customers.size(); i++) {
				if(mr.customers.get(i).getId().equals(tf9.getText().trim())) {
					flag = true;
					indexI = i;
				}
			}
			if(flag == true) {
				mr.customers.get(indexI).setName(tf10.getText().trim());
				mr.customers.get(indexI).setAddress(tf11.getText().trim());
				mr.customers.get(indexI).setNumber(Integer.parseInt(tf12.getText().trim()));
				if(rb5.isSelected()) {
					mr.customers.get(indexI).setPlan("LIMITED");
				}else if(rb6.isSelected()) {
					mr.customers.get(indexI).setPlan("UNLIMITED");
				}
				AlertType type = AlertType.CONFIRMATION;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Customer Updated");
				alert.getDialogPane().setHeaderText("Customer Update Info!");
				alert.show();
			}else {
				AlertType type = AlertType.WARNING;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Customer Not Found");
				alert.getDialogPane().setHeaderText("Customer Update Info!");
				alert.show();
			}
			
			try {
				FileWriter fw = new FileWriter(file5, true);
				fw.write("Customers in DATA:\n");
				fw.write(mr.getAllCustomersInfo());
				fw.write("\n\n");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		b22.setOnAction(e -> primaryStage.setScene(sceneCustomer));
		b23.setOnAction(e -> {
			boolean flag = false;
			for(int i=0; i < mr.customers.size(); i++) {
				if(mr.customers.get(i).getId().equals(tf13.getText().trim())) {
					flag = true;
				}
			}
			if(flag == true) {
				AlertType type = AlertType.CONFIRMATION;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Customer Found");
				alert.getDialogPane().setHeaderText("Customer Found or not Found message!");
				alert.show();
			}else {
				AlertType type = AlertType.WARNING;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Customer Not Found");
				alert.getDialogPane().setHeaderText("Customer Found or not Found message!");
				alert.show();
			}
		});
		
		b24.setOnAction(e -> primaryStage.setScene(sceneMedia));
		//-----------------------------------
		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			if(comboBox.getSelectionModel().getSelectedItem().equalsIgnoreCase("Game")) {
				lb26.setVisible(true);
				tf20.setVisible(true);
				for_radiobuttons5.setVisible(false);
				lb27.setVisible(false);
				lb28.setVisible(false);
				tf21.setVisible(false);
				lb25.setVisible(false);
			}else if(comboBox.getSelectionModel().getSelectedItem().equalsIgnoreCase("Album")) {
				lb27.setVisible(true);	
				tf20.setVisible(true);
				lb28.setVisible(true);
				tf21.setVisible(true);
				for_radiobuttons5.setVisible(false);
				lb26.setVisible(false);
				lb25.setVisible(false);
			}else if(comboBox.getSelectionModel().getSelectedItem().equalsIgnoreCase("Movie")) {
				lb25.setVisible(true);
				for_radiobuttons5.setVisible(true);
				lb26.setVisible(false);
				lb27.setVisible(false);
				lb28.setVisible(false);
				tf21.setVisible(false);
				tf20.setVisible(false);
			}
		});
		
		comboBox2.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			if(comboBox2.getSelectionModel().getSelectedItem().equalsIgnoreCase("Game")) {
				lb34.setVisible(true);
				tf25.setVisible(true);
				for_radiobuttons6.setVisible(false);
				lb33.setVisible(false);
				lb35.setVisible(false);
				tf37.setVisible(false);
				lb36.setVisible(false);
			}else if(comboBox2.getSelectionModel().getSelectedItem().equalsIgnoreCase("Album")) {
				lb35.setVisible(true);	
				tf25.setVisible(true);
				lb36.setVisible(true);
				tf37.setVisible(true);
				for_radiobuttons6.setVisible(false);
				lb34.setVisible(false);
				lb33.setVisible(false);
			}else if(comboBox2.getSelectionModel().getSelectedItem().equalsIgnoreCase("Movie")) {
				lb33.setVisible(true);
				for_radiobuttons6.setVisible(true);
				lb23.setVisible(false);
				lb35.setVisible(false);
				lb36.setVisible(false);
				tf37.setVisible(false);
				tf25.setVisible(false);
			}
		});
		
		comboBox3.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			if(comboBox3.getSelectionModel().getSelectedItem().equalsIgnoreCase("Game")) {
				lb42.setVisible(true);
				tf41.setVisible(true);
				for_radiobuttons7.setVisible(false);
				lb41.setVisible(false);
				lb43.setVisible(false);
				tf42.setVisible(false);
				lb44.setVisible(false);
			}else if(comboBox3.getSelectionModel().getSelectedItem().equalsIgnoreCase("Album")) {
				lb43.setVisible(true);	
				tf41.setVisible(true);
				lb44.setVisible(true);
				tf42.setVisible(true);
				for_radiobuttons7.setVisible(false);
				lb41.setVisible(false);
				lb42.setVisible(false);
			}else if(comboBox3.getSelectionModel().getSelectedItem().equalsIgnoreCase("Movie")) {
				lb41.setVisible(true);
				for_radiobuttons7.setVisible(true);
				lb42.setVisible(false);
				lb43.setVisible(false);
				lb44.setVisible(false);
				tf41.setVisible(false);
				tf42.setVisible(false);
			}
		});
		
		comboBox4.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			if(comboBox4.getSelectionModel().getSelectedItem().equalsIgnoreCase("Game")) {
				lb50.setVisible(true);
				tf46.setVisible(true);
				for_radiobuttons8.setVisible(false);
				lb49.setVisible(false);
				lb51.setVisible(false);
				tf47.setVisible(false);
				lb52.setVisible(false);
			}else if(comboBox4.getSelectionModel().getSelectedItem().equalsIgnoreCase("Album")) {
				lb51.setVisible(true);	
				tf46.setVisible(true);
				lb52.setVisible(true);
				tf47.setVisible(true);
				for_radiobuttons8.setVisible(false);
				lb49.setVisible(false);
				lb50.setVisible(false);
			}else if(comboBox4.getSelectionModel().getSelectedItem().equalsIgnoreCase("Movie")) {
				lb49.setVisible(true);
				for_radiobuttons8.setVisible(true);
				lb50.setVisible(false);
				lb51.setVisible(false);
				lb52.setVisible(false);
				tf46.setVisible(false);
				tf47.setVisible(false);
			}
		});
		//---------------------------------
		b25.setOnAction(e -> {
			if(comboBox.getValue().equals("Album")) {
				Media album = new Album(tf17.getText().trim(), tf18.getText().trim(), Integer.parseInt(tf19.getText().trim()), tf20.getText().trim(), tf21.getText().trim());
				mr.media.add(album);
			}else if(comboBox.getValue().equals("Game")){
				Media game = new Game(tf17.getText().trim(), tf18.getText().trim(), Integer.parseInt(tf19.getText().trim()), Double.parseDouble(tf20.getText().trim()));
				mr.media.add(game);
			}
			else if(comboBox.getValue().equals("Movie")) {
				if(rb9.isSelected()) {
					Media movie = new Movie(tf17.getText().trim(), tf18.getText().trim(), Integer.parseInt(tf19.getText().trim()), "DR");
					mr.media.add(movie);
				}else if(rb10.isSelected()) {
					Media movie = new Movie(tf17.getText().trim(), tf18.getText().trim(), Integer.parseInt(tf19.getText().trim()), "HR");
						mr.media.add(movie);
				}else if(rb11.isSelected()) {
					Media movie = new Movie(tf17.getText().trim(), tf18.getText().trim(), Integer.parseInt(tf19.getText().trim()), "AC");
						mr.media.add(movie);
				}
			}
			AlertType type = AlertType.CONFIRMATION;
			Alert alert = new Alert(type , "");
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.getDialogPane().setContentText("Media Added");
			alert.getDialogPane().setHeaderText("Media Addition Info!");
			alert.show();
			
			try {
				FileWriter fw = new FileWriter(file6, true);
				fw.write("Media info in Data:\n");
				fw.write(mr.getAllMediaInfo());
				fw.write("\n\n");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		
		b26.setOnAction(e -> primaryStage.setScene(sceneMedia));
		b27.setOnAction(e -> {
			int index = -1;
			boolean flag = false;
			for(int i=0; i < mr.media.size(); i++) {
				if(mr.media.get(i).getCode().equals(tf22.getText().trim())) {
					flag = true;
					index = i;
				}
			}
			if(flag == true) {
				mr.media.remove(index);
				AlertType type = AlertType.CONFIRMATION;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Media Deleted");
				alert.getDialogPane().setHeaderText("Media Deleting Info!");
				alert.show();
			}else {
				AlertType type = AlertType.WARNING;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Media Not Found");
				alert.getDialogPane().setHeaderText("Media Deleting Info!");
				alert.show();
			}
			
			try {
				FileWriter fw = new FileWriter(file6, true);
				fw.write("Media info in Data:\n");
				fw.write(mr.getAllMediaInfo());
				fw.write("\n\n");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		b28.setOnAction(e-> {
			boolean flag = false;
			for(int i=0; i < mr.media.size(); i++) {
				if(mr.media.get(i).getCode().equals(tf22.getText().trim())) {
					flag = true;
				}
			}
			if(flag == true) {
				AlertType type = AlertType.CONFIRMATION;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Media Found");
				alert.getDialogPane().setHeaderText("Media Info!");
				alert.show();
			}else {
				AlertType type = AlertType.WARNING;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Media Not Found");
				alert.getDialogPane().setHeaderText("Media Info");
				alert.show();
			}
		});

		b29.setOnAction(e -> primaryStage.setScene(sceneMedia));
		b41.setOnAction(e -> {
			boolean flag = false;
			int indexI = -1;
			for(int i=0; i < mr.media.size(); i++) {
				if(mr.media.get(i).getCode().equals(tf38.getText().trim())) {
					flag = true;
					indexI = i;
				}
			}
			if(flag == true) {
				if(mr.media.get(indexI) instanceof Album) {
					comboBox3.getSelectionModel().select(2);
				}else if(mr.media.get(indexI) instanceof Game) {
					comboBox3.getSelectionModel().select(1);
				}else if(mr.media.get(indexI) instanceof Movie) {
					comboBox3.getSelectionModel().select(0);	
				}
				AlertType type = AlertType.CONFIRMATION;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Media type determined.");
				alert.getDialogPane().setHeaderText("Media type Info!");
				alert.show();
			}else {
				AlertType type = AlertType.WARNING;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Media does not exist.");
				alert.getDialogPane().setHeaderText("Media type Info!");
				alert.show();
			}
		});
		b30.setOnAction(e -> {
			boolean flag = false;
			int indexI = -1;
			for(int i=0; i < mr.media.size(); i++) {
				if(mr.media.get(i).getCode().equals(tf38.getText().trim())) {
					flag = true;
					indexI = i;
				}
			}
			if(flag == true) {
				String code = tf38.getText().trim();
				String title = tf39.getText().trim();
				int num = Integer.parseInt(tf40.getText().trim());
				if(comboBox3.getValue().equals("Album")) {
					String artist = tf41.getText().trim();
					String songs = tf42.getText().trim();
					Album album = new Album(code, title, num, artist, songs);
					mr.media.remove(indexI);
					mr.media.add(album);
				}else if(comboBox3.getValue().equals("Game")){
					double weight = Double.parseDouble(tf41.getText().trim());
					Game game = new Game(code, title, num, weight);
					mr.media.remove(indexI);
					mr.media.add(game);
				}
				else if(comboBox3.getValue().equals("Movie")) {
					if(rb15.isSelected()) {
						Media movie = new Movie(code, title, num, "DR");
						mr.media.remove(indexI);
						mr.media.add(movie);
					}else if(rb16.isSelected()) {
						Media movie = new Movie(code, title, num, "HR");
						mr.media.remove(indexI);
						mr.media.add(movie);
					}else if(rb17.isSelected()) {
						Media movie = new Movie(code, title, num, "AC");
						mr.media.remove(indexI);
						mr.media.add(movie);
					}
				}
				AlertType type = AlertType.CONFIRMATION;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Media Updated");
				alert.getDialogPane().setHeaderText("Media Update Info!");
				alert.show();
			}else {
				AlertType type = AlertType.WARNING;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Media Not Found");
				alert.getDialogPane().setHeaderText("Media Update Info!");
				alert.show();
			}
			
			try {
				FileWriter fw = new FileWriter(file6, true);
				fw.write("Media info in Data:\n");
				fw.write(mr.getAllMediaInfo());
				fw.write("\n\n");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		b31.setOnAction(e -> primaryStage.setScene(sceneMedia));
		b32.setOnAction(e -> {
			boolean flag = false;
			for(int i=0; i < mr.media.size(); i++) {
				if(mr.media.get(i).getCode().equals(tf43.getText().trim())) {
					flag = true;
				}
			}
			if(flag == true) {
				AlertType type = AlertType.CONFIRMATION;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Media Found");
				alert.getDialogPane().setHeaderText("Media Info!");
				alert.show();
			}else {
				AlertType type = AlertType.WARNING;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Media Not Found");
				alert.getDialogPane().setHeaderText("Media Info");
				alert.show();
			}
		});
		
		b40.setOnAction(e -> {
			String info = mr.getAllMediaInfo();
			area.setText(info);
		});
		b33.setOnAction(e -> primaryStage.setScene(sceneMedia));
		
		b34.setOnAction(e -> primaryStage.setScene(scene));
		b37.setOnAction(e -> {
			boolean flag = false;
			int in = -1;
			for(int i=0; i < mr.customers.size(); i++) {
				if(mr.customers.get(i).getId().equals(tf48.getText().trim())) {
					flag = true;
					in =i;
				}
			}
			if(flag == true) {
				String requested = mr.customers.get(in).getRequestedMedia().toString();
				area2.setText("Requested media:\n" + requested);
			}else {
				AlertType type = AlertType.WARNING;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Customer Not Found");
				alert.getDialogPane().setHeaderText("Customer Found or not Found message!");
				alert.show();
			}
		});
		b38.setOnAction(e -> {
			boolean flag = false;
			int in = -1;
			for(int i=0; i < mr.customers.size(); i++) {
				if(mr.customers.get(i).getId().equals(tf48.getText().trim())) {
					flag = true;
					in =i;
				}
			}
			if(flag == true) {
				String rented = mr.customers.get(in).getRentedMedia().toString();
				area2.setText("Rented media:\n" + rented);
			}else {
				AlertType type = AlertType.WARNING;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Customer Not Found");
				alert.getDialogPane().setHeaderText("Customer Found or not Found message!");
				alert.show();
			}
		});
		b35.setOnAction(e -> {
			boolean flag = false, flag2 = false;
			int in = -1, in2 = -1;
			for(int i=0; i < mr.customers.size(); i++) {
				if(mr.customers.get(i).getId().equals(tf48.getText().trim())) {
					flag = true;
					in =i;
				}
			}
			for(int i=0; i < mr.media.size(); i++) {
				if(mr.media.get(i).getCode().equals(tf49.getText().trim())) {
					flag2 = true;
					in2 =i;
				}
			}
			
			if(flag && flag2) {
				mr.addToCart(mr.customers.get(in).getId(), mr.media.get(in2).getCode());
				AlertType type = AlertType.CONFIRMATION;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Media Added successfully!");
				alert.getDialogPane().setHeaderText("Add to cart statment!");
				alert.show();
			}else{
				AlertType type = AlertType.WARNING;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Could not add, customer id or media code error");
				alert.getDialogPane().setHeaderText("Add to cart statment!");
				alert.show();
			}
			
			try {
				FileWriter fw = new FileWriter(file5, true);
				fw.write("Customers in DATA:\nAfter ADDING TO CART:\n");
				fw.write(mr.getAllCustomersInfo());
				fw.write("\n\n");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				FileWriter fw = new FileWriter(file6, true);
				fw.write("Media info in Data:\nAfter ADDING TO CART:\n");
				fw.write(mr.getAllMediaInfo());
				fw.write("\n\n");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		b39.setOnAction(e -> {
			boolean flag = false, flag2 = false;
			int in = -1, in2 = -1;
			for(int i=0; i < mr.customers.size(); i++) {
				if(mr.customers.get(i).getId().equals(tf48.getText().trim())) {
					flag = true;
					in =i;
				}
			}
			for(int i=0; i < mr.media.size(); i++) {
				if(mr.media.get(i).getCode().equals(tf49.getText().trim())) {
					flag2 = true;
					in2 =i;
				}
			}
			
			if(flag && flag2) {
				mr.returnMedia((mr.customers.get(in).getId()), mr.media.get(in2).getCode());
				boolean f = mr.returnMedia((mr.customers.get(in).getId()), mr.media.get(in2).getCode());
				if(!f) {
					AlertType type = AlertType.CONFIRMATION;
					Alert alert = new Alert(type , "");
					alert.initModality(Modality.APPLICATION_MODAL);
					alert.getDialogPane().setContentText("Media returned successfully!");
					alert.getDialogPane().setHeaderText("Return media statment!");
					alert.show();
				}else {
					AlertType type = AlertType.WARNING;
					Alert alert = new Alert(type , "");
					alert.initModality(Modality.APPLICATION_MODAL);
					alert.getDialogPane().setContentText("media doesn't exist in the rented media of customer");
					alert.getDialogPane().setHeaderText("Return media statment!");
					alert.show();
				}
			}else{
				AlertType type = AlertType.WARNING;
				Alert alert = new Alert(type , "");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.getDialogPane().setContentText("Could not return, customer id or media code error");
				alert.getDialogPane().setHeaderText("Return media statment!");
				alert.show();
			}
			
			try {
				FileWriter fw = new FileWriter(file5, true);
				fw.write("Customers in DATA:\nAfter RETURNING:\n");
				fw.write(mr.getAllCustomersInfo());
				fw.write("\n\n");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				FileWriter fw = new FileWriter(file6, true);
				fw.write("Media info in Data:\nAfter RETURNING:\n");
				fw.write(mr.getAllMediaInfo());
				fw.write("\n\n");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		b36.setOnAction(e -> {
			String info = mr.processRequests();
			area2.setText("Process requests:\n" + info);
			
			try {
				FileWriter fw = new FileWriter(file5, true);
				fw.write("Customers in DATA:\n\nAfter PROCESSING:\n");
				fw.write(mr.getAllCustomersInfo());
				fw.write("\n\n");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				FileWriter fw = new FileWriter(file6, true);
				fw.write("Media info in Data:\nAfter PROCESSING:\n");
				fw.write(mr.getAllMediaInfo());
				fw.write("\n\n");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		//---------------------------------------------------------------------

		primaryStage.setScene(scene);
		primaryStage.setTitle("Main Page");
		primaryStage.setMaximized(true);
		Image img = new Image("icon.png");
		primaryStage.getIcons().add(img);
		primaryStage.show();
	}
	
	//----------------------------------------------------------------------------------------------
		
	public static void main(String[] args){
		launch(args);
	}
	
}