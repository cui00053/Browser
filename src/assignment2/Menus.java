/**
 * File name: Menus.java
 * Author: Chenxiao Cui, 040879113
 * Course: CST8284 - OOP
 * Assignment: 3
 * Date: 12/01/2018
 * Professor: David Houtmad
 * Purpose: The purpose of this class is to generate Menus and MenuItems as well as their functions.
 * 
 */

package assignment2;

import java.io.File;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;

/**
 * Purpose: The purpose of this class is to generate Menus and MenuItems as well as their functions. *  
 * @author Chenxiao Cui
 * @version 6.0
 * @since  2018-01-12
 *
 */
public class Menus {
	/** A menu bar contains all the menu items */
	private static MenuBar menuBar = new MenuBar();
	/** The File menu which contains the 'Fresh' menu item and the 'Exit' menu item  */
	private static Menu mnuFile = new Menu("File");
	/** The Setting menu which contains four menu items */
	private static Menu mnuSettings = new Menu("Setting");
	/** The Bookmark menu allows to add and display bookmarks  */
	private static Menu mnuBookmarks = new Menu("Bookmark");;
	/** The Help menu */
	private static Menu mnuHelp = new Menu("Help");	
	/** The Refresh menu item which allows user to refresh the current page */
	private static MenuItem mnuItmRefresh = new MenuItem("_Refresh");
	/** The Exit menu item allows to close the application*/
	private static MenuItem mnuItmExit = new MenuItem("_Exit");
	/** The ToggleAddressBar menu item allows to show and hide address bar */
	private static MenuItem mnuItmToggleAddressBar = new MenuItem("_Toggle Address Bar");
	/** The ChangeStartup menu item allow to change the startup web page */
	private static MenuItem mnuItmChangeStartup = new MenuItem("_Change Start-up Page");
	/** The AddBookmark menu item allows to save current web page as a bookmark */
	private static MenuItem mnuItmAddBookmark = new MenuItem("_Add Bookmark");
	/** The About menu item allows to show a pop up window which contains information of this application */
	private static MenuItem mnuItmAbout = new MenuItem("_About");		
	/** The address extField which can enter and show the URL */
	protected static TextField tfAddress = new TextField();
	/** The History VBox which contains the web engine's history ListView and forward and backward buttons */
	private static VBox vbHistory = new VBox();
	/** The Address HBox which contains a label, a address TextField and a go button */
	private static HBox hbAddress = new HBox();
	/** The WebpageCode VBox which allows to show the HTML/PHP code of current web page */
	private static VBox webpageCode = new VBox();
	
	/**
	 * Get menuBar MenuBar
	 * @param we WebEngine
	 * @param defaultWeb file
	 * @param bookMarks file
	 * @return MenuBar contains all the MenuItems
	 */
	public static MenuBar getMenuBar(WebEngine we,  File defaultWeb,File bookMarks) {
		mnuItmRefresh.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
		menuBar.getMenus().addAll(getmnuFile(we),getmnuSettings(we, defaultWeb),getBookMarks(we,bookMarks),getMnuHelp());
		return menuBar;
	}
		
	/**
	 * Get mnuFile MenuItem which contains the 'Fresh' menu item and the 'Exit' menu item
	 * @param we WebEngine
	 * @return Menu File Menu contains the 'Fresh' menu item and the 'Exit' menu item 
	 */
	public static Menu getmnuFile(WebEngine we) {
		mnuFile.getItems().addAll(getMnuItmRefresh(we),getMnuItmExit());
		return mnuFile;
	}
	
	/**
	 * Get mnuSettings Menu
	 * @param we WebEngine
	 * @param defaultWeb file
	 * @return Menu Settings
	 */
	public static Menu getmnuSettings(WebEngine we, File defaultWeb) {		
		mnuSettings.getItems().addAll(getmnuItmToggleAddressBar(),mnuItmChangeStartup(we, defaultWeb),getmnuItmHistory(we),getDisplayCode());
		return mnuSettings;
	}
	
	/**
	 * Get mnuBookmarks Menu
	 * @param we WebEngine
	 * @param bookMarks file
	 * @return Menu BookMarks allows to add and display bookmarks
	 */
	public static Menu getBookMarks(WebEngine we,File bookMarks) {						
		mnuBookmarks.getItems().add(getAddBookMarks(we, bookMarks));
		if(FileUtils.fileExists(bookMarks)) {
			for(MenuItem mi:getlistOfBookMarks(we, bookMarks)) {
				mnuBookmarks.getItems().add(mi);				 
			}				 
		}		
		
		mnuBookmarks.setOnMenuValidation(e -> {					
			mnuBookmarks.getItems().clear();    
			mnuBookmarks.getItems().add(getAddBookMarks(we, bookMarks));
			if(FileUtils.fileExists(bookMarks)) {
				for(MenuItem mi:getlistOfBookMarks(we, bookMarks)) {
					 mnuBookmarks.getItems().add(mi);				 
				}					 
			}				 		 
		});	
		return mnuBookmarks;	
	}	
	
	/**
	 * Get mnuHelp Menu
	 * @return Menu Help
	 */
	public static Menu getMnuHelp() {
		mnuHelp.getItems().addAll(getmnuItmAbout());
		return mnuHelp;
	}	
	
	/**
	 * Get the Refresh MenuItem which allows user to refresh the current page
	 * Reference: http://java-buddy.blogspot.ca/2012/02/javafx-20-set-accelerator.html
	 * @param we WebEngine
	 * @return MenuItem Refresh 
	 */
	public static MenuItem getMnuItmRefresh(WebEngine we) {
		mnuItmRefresh.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
		mnuItmRefresh.setOnAction( e -> {
			we.reload();
		});			
		return mnuItmRefresh;
 	}	

	/**
	 * Get Exit MenuItem
	 * @return MenuItem Exit
	 */
	public static MenuItem getMnuItmExit() {		 
		mnuItmExit.setAccelerator(KeyCombination.keyCombination("Ctrl+E")); 
		mnuItmExit.setOnAction(e -> Platform.exit());		
		 return mnuItmExit;
	}
		
	/**
	 * Load the URL to the WebEngine, when catch the MalformedURLException and IllegalArgumentException, alert a information box.
	 * @param we WebEngine
	 * @param url String
	 * @throws IllegalArgumentException catch the MalformedURLException and alert a information box
	 */
	public static void loadWebEngine(WebEngine we,String url) throws IllegalArgumentException  {		
		try {
			URL url2 = new URL(url);
			we.load(url2.toString());
		} catch (MalformedURLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Invalid URL");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a valid URL ");
			alert.showAndWait();
		} catch(IllegalArgumentException e)	{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Illegal arguments");
			alert.setHeaderText(null);
			alert.setContentText("Invalid URL");
			alert.showAndWait();
		}		
		
	}	
	
	/**
	 * Get Address Bar. tfAddress TextField allows user to enter a URL and go button loads the URL 
	 * Reference: https://stackoverflow.com/questions/23363222/how-to-set-placeholder-in-javafx
	 * @param we WebEngine
	 * @return HBox hbAddress
	 */
	public static HBox getAdressBar(WebEngine we) {
		Label label = new Label("Enter Address");			
		tfAddress.setPromptText("Enter address here"); 
		tfAddress.setOnKeyPressed(e ->{
			if(e.getCode() == KeyCode.ENTER)
				loadWebEngine(we,tfAddress.getText());
		});
		Button bt = new Button("Go");
		bt.setOnAction(e -> loadWebEngine(we,tfAddress.getText()));			
			
		hbAddress.getChildren().addAll(label,tfAddress,bt);		
		hbAddress.setHgrow(tfAddress, Priority.ALWAYS); 
		hbAddress.setVisible(false);
		hbAddress.setManaged(false);
		
		return hbAddress;
	}
				
	/**
	 * Get the ToggleAddressBar MenuItem which allows to show and hide address bar
	 * @return MenuItem mnuItmToggleAddressBar
	 */
	public static MenuItem getmnuItmToggleAddressBar() {
		mnuItmToggleAddressBar.setAccelerator(KeyCombination.keyCombination("Ctrl+T"));
		mnuItmToggleAddressBar.setOnAction(e -> {
			hbAddress.setManaged(!hbAddress.isVisible());
			hbAddress.setVisible(!hbAddress.isVisible());
		});
		return mnuItmToggleAddressBar;
	}
	
	/**
	 * Get the ChangeStartup MenuItem which allows to save the current web page as the startup page. 
	 * @param we WebEngine
	 * @param defaultWeb file
	 * @return MenuItem mnuItmChangeStartup
	 */
	public static MenuItem mnuItmChangeStartup(WebEngine we, File defaultWeb) {		
		mnuItmChangeStartup.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
		mnuItmChangeStartup.setOnAction(e ->{
			ArrayList<String> arrayDefault=new ArrayList<>();
			arrayDefault.add(we.getLocation());
			FileUtils.saveFileContents(defaultWeb, arrayDefault);
		});		
		return mnuItmChangeStartup;
	}
		
	/**
	 * Get the startup web page's URL from defaultWeb.web. If doesn't exist, return google website by default.
	 * @param defaultWeb file
	 * @return String startup webpage's URL
	 */
	public static String getDefaultWeb(File defaultWeb) {			
		if(FileUtils.fileExists(defaultWeb)) {
			return FileUtils.getFileContentAsArrayList(defaultWeb).get(FileUtils.getFileContentAsArrayList(defaultWeb).size()-1);
		}
		else {
			return ("http://www.google.ca");			
		}		
	}	
	 
	/**
	 * Get History MenuItem which allows to hide and show history VBox.
	 * @param we WebEngine
	 * @return MenuItem mnuItmHistory
	 */
	public static MenuItem getmnuItmHistory(WebEngine we) {
		MenuItem mnuItmHistory = new MenuItem("_History");
		mnuItmHistory.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));	
		final WebHistory history = we.getHistory();		
		mnuItmHistory.setOnAction(e ->{			
			if(!vbHistory.isVisible()) {				
				we.load(history.getEntries().get(history.getEntries().size()-1).getUrl());				
			}			
			vbHistory.setManaged(!vbHistory.isVisible());
			vbHistory.setVisible(!vbHistory.isVisible());
				
		});
		return mnuItmHistory;		
	}
	
	
	/**
	 * Get History VBox which contains the web engine's history ListView and forward and backward buttons.
	 * @param we WebEngine
	 * @return VBox vbHistory
	 */
	public static VBox getHistory(WebEngine we) {
		
		Button backwards = new Button("Backwards");
		Button forwards = new Button("Forwards");
		HBox historyButton = new HBox();
		vbHistory.setManaged(false);
		vbHistory.setVisible(false);
		backwards.setDisable(true);
		forwards.setDisable(true);
					
		final WebHistory history = we.getHistory();
		ObservableList<WebHistory.Entry> historyList = history.getEntries();
		ListView<WebHistory.Entry> historyView = new ListView<WebHistory.Entry>(historyList);				
		historyView.setPrefHeight(1000);
		backwards.setOnAction(e ->{ goBack(we);	});		
		forwards.setOnAction(e ->{ goForward(we); });
		
		we.setOnStatusChanged(e ->{
			backwards.setDisable((historyList.size() == 0 || history.getCurrentIndex() == 0) );
			forwards.setDisable(historyList.size() == 0 || history.getCurrentIndex() == (historyList.size() - 1));
			webpageCode.getChildren().clear();
    		webpageCode.getChildren().add(getWebCode(we));     	
		});	
		
		historyButton.getChildren().addAll(backwards,forwards);
		vbHistory.getChildren().addAll(historyView,historyButton);
		return vbHistory;		
	}
	
	/**
	 * Load the previous web page in history list if there has one.
	 * @param we WebEngine
	 */
	public static void goBack(WebEngine we) {
		final WebHistory history = we.getHistory();		
		int currentIndex = history.getCurrentIndex();
		
		if(currentIndex > 0){           
	        Platform.runLater( () -> {
	        	history.go(-1);	        
	        });	  	           
	    }
	}
	
	/**
	 * Load the next web page in history list if there has one.
	 * @param we WebEngine
	 */
	public static void goForward(WebEngine we) {
		final WebHistory history = we.getHistory();
		ObservableList<WebHistory.Entry> historyList = history.getEntries();
		int currentIndex = history.getCurrentIndex();
		
		if(currentIndex + 1 < historyList.size()){				
			Platform.runLater( () -> { 
				history.go(1);				
			});
		}    
	}

	
	/**
	 * Get DisplayCode MenuItem which can show and hide the webpageCode VBox.
	 * @return MenuItem mnuItmDisplayCode
	 */
	public static MenuItem getDisplayCode() {
		MenuItem mnuItmDisplayCode = new MenuItem("_Display Code");		
		mnuItmDisplayCode.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));		
		mnuItmDisplayCode.setOnAction(e ->{
			webpageCode.setManaged(!webpageCode.isVisible());
			webpageCode.setVisible(!webpageCode.isVisible());
			
		});		
		return mnuItmDisplayCode;
	}
	

	/**
	 * Get webCode TextArea. Load the HTML/PHP code to the webCode TextArea and return it.
	 * Reference: http://www.java2s.com/Tutorials/Java/JavaFX/1500__JavaFX_WebEngine.htm
	 * @param we WebEngine
	 * @return TextArea webCode
	 */
	public static TextArea getWebCode(WebEngine we) {
		TextArea webCode= new TextArea();
		try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(we.getDocument()),new StreamResult(stringWriter));
            String xml = stringWriter.getBuffer().toString();
            webCode = new TextArea(xml);                         
          } catch (Exception e) {
            e.printStackTrace();
          }		
		return webCode;
		
	}
	
	/**
	 * Get webpageCode VBox which contains the webCode TextArea.
	 * @param we WebEngine
	 * @return VBox webpageCode
	 */
	public static VBox getWebpageCode(WebEngine we) {		
		webpageCode.getChildren().add(getWebCode(we));	
		webpageCode.setManaged(false);
		webpageCode.setVisible(false);
		return webpageCode;
	}		
	
	/**
	 * Get mnuItmAddBookmark MenuItem which allows to add current web page to a bookmark MenuItem.
	 * @param we WebEngine
	 * @param bookMarks file
	 * @return MenuItem mnuItmAddBookmark 
	 */
	public static MenuItem getAddBookMarks(WebEngine we, File bookMarks) {
		mnuItmAddBookmark.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		ArrayList<String> arrayBookMarks= new ArrayList<>();
		mnuItmAddBookmark.setOnAction(e ->{
			arrayBookMarks.add(we.getLocation());
			FileUtils.saveFileContents(bookMarks, arrayBookMarks);						
		});
		return mnuItmAddBookmark;
	}		
	
	/**
	 * Get ArrayList of bookmarks which contain right click and left click functions.
	 * Reference: stackoverflow.com/questions/48159213/update-menus-items-each-time-menu-is-opened-javafx
	 * @param we WebEngine
	 * @param bookMarks file
	 * @return ArrayList listOfBookMarks
	 */
   public static ArrayList<CustomMenuItem> getlistOfBookMarks(WebEngine we, File bookMarks){
	   ContextMenu contextMenu = new ContextMenu();
	   MenuItem delete = new MenuItem("Delete");
		contextMenu.getItems().add(delete);
		
		ArrayList<CustomMenuItem> listOfBookMarks = new ArrayList<>();
		if(FileUtils.fileExists(bookMarks)) {
			ArrayList<String> arrayBookMarks = FileUtils.getFileContentAsArrayList(bookMarks);
			for(int i=0;i<arrayBookMarks.size();i++){
				String str= arrayBookMarks.get(i);
				Label url = new Label(str);
				CustomMenuItem bm =new CustomMenuItem(url,false);				
				bm.getContent().setOnMouseClicked(new EventHandler<MouseEvent>(){
					 @Override
			            public void handle(MouseEvent event) {
						 MouseButton button = event.getButton();
			        	if ( button == MouseButton.SECONDARY){
			                contextMenu.show(bm.getContent(), event.getScreenX(),event.getScreenY());
			                delete.setOnAction(e ->{			                	
			                	FileUtils.deleteFileContents(bookMarks, str);
			                	bm.getParentMenu().getItems().remove(bm);
			                });
			            }
			            else{
			            	loadWebEngine(we,str);
			            }
			        }
				});
				
				listOfBookMarks.add(bm);				
			}
		}
		else listOfBookMarks=null;		
		return listOfBookMarks;
	}		
	  
   /**
    * Get About MenuItem allows to show a pop up window which contains information of this application.
    * @return MenuItem mnuItmAbout
    */
	public static MenuItem getmnuItmAbout() {
		mnuItmAbout.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));
		mnuItmAbout.setOnAction(e ->{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Name: Chenxiao Cui\nStudent Number: 040879113\nProject: CST8284_17F_Assignment2\nProfessor: Dave Houtman ");
			alert.showAndWait();
		});
		return mnuItmAbout;
	}	
}
