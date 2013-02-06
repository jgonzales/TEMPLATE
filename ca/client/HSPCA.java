package com.harinosoftware.ca.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HSPCA implements EntryPoint {

	@Override
 	public void onModuleLoad() {
		HandlerManager eventBus = new HandlerManager(this);
	    AppControllerCA appViewer = new AppControllerCA("",eventBus);
	    //appViewer.go((HasWidgets)RootPanel.get()); 
	}}
