/**
 * File name: WebPage.java
 * Author: Chenxiao Cui, 040879113
 * Course: CST8284 - OOP
 * Assignment: 3
 * Date: 12/01/2018
 * Professor: David Houtmad
 * Purpose: The purpose of this class is to create the WebEngine and WebView.
 * 
 */

package assignment2;

import javafx.beans.value.ChangeListener;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * The purpose of this class is to create the WebEngine and WebView. 
 * @author Chenxiao Cui, David Houtmad
 * @version 2.0
 * @since 2018-01-12
 *
 */
public class WebPage {
	/** WebView */
	private WebView webview = new WebView();
	/** WebEngine */
	private WebEngine engine;
	
	/**
	 * Reference: https://stackoverflow.com/questions/32486758/detect-url-changes-in-javafx-webview
	 * @param stage Stage
	 * @return WebEngine
	 */
	public WebEngine createWebEngine(Stage stage) {
		
		WebView wv = getWebView();
		engine = wv.getEngine();
		
		engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
				if (newState == Worker.State.RUNNING) {
					stage.setTitle(engine.getLocation());
				}
				
				if (newState == Worker.State.SUCCEEDED) {
					Menus.tfAddress.setText(engine.getLocation());
				}
			}
		});
		return engine;
	}
	
	/**
	 * Get WebView
	 * @return WebView
	 */
	public WebView getWebView() {
		return webview;
	}	
	
	/**
	 * Get WebEngine
	 * @return WebEngine
	 */
	public WebEngine getWebEngine() {
		return engine;
	}
}
