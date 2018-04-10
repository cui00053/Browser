/**
 * File name: MyJavaFxBrowser.java
 * Author: Chenxiao Cui, 040879113
 * Course: CST8284 - OOP
 * Assignment: 3
 * Date: 12/01/2018
 * Professor: David Houtmad
 * Purpose: The purpose of this class is to load all the nodes to the scene, then load the scene to the stage and show stage.
 * 
 */
package assignment2;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * This class has the purpose to load nodes into a scene.  It also shows a web view in the center of the scene, a menu bar (including 4 menus) on the top of the scene. 
 * On the right hand side there are a history list and two buttons, which are hidden by default and are toggleable. On the bottom of the scene there is a toggleable 
 * VBox which can show the HTML/JavaScript code for the current web page.
 *  
 * @author Chenxiao Cui, David Houtmad
 * @version 2.0
 * @since 2018-01-12
 *
 */
public class MyJavaFXBrowser extends Application {

	/**
	 * This overriden method will start the application. It will pop up a scene. 
	 * @param primaryStage the primary Stage.
	 */
	@Override
	public void start(Stage primaryStage){
	    WebPage currentPage = new WebPage();
	    WebView webView = currentPage.getWebView();
	    WebEngine webEngine = currentPage.createWebEngine(primaryStage);
		File defaultWeb = new File("default.web");
		File bookMarks = new File("bookmarks.web");
		webEngine.load(Menus.getDefaultWeb(defaultWeb));			
		
		VBox vb = new VBox();
		vb.getChildren().addAll(Menus.getMenuBar(webEngine,defaultWeb,bookMarks),Menus.getAdressBar(webEngine));		
		
		BorderPane root = new BorderPane();
		root.setCenter(webView);
		root.setTop(vb);
		root.setRight(Menus.getHistory(webEngine));
		
		root.setBottom(Menus.getWebpageCode(webEngine));
		
		Scene scene = new Scene(root, 800, 500);
		primaryStage.setScene(scene);
		primaryStage.show();	
	}	
	/**
	 * Close the browser.
	 */
	public void stop() {
		System.out.println("The browser is closed.");			
	}	
	/**
	 * The main method of the class that has the Application.launch, which replaces this main method with the method Start from the javafx.application.Application.
	 * @param args the array of strings args.	
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

}
