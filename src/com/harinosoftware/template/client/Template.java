package com.harinosoftware.template.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Template implements EntryPoint {

	@Override
 	public void onModuleLoad() {
		HandlerManager eventBus = new HandlerManager(this);
	    AppControllerCA appViewer = new AppControllerCA(GWT.getHostPageBaseURL(),eventBus);
	    appViewer.go((HasWidgets)RootPanel.get()); 
	}}
