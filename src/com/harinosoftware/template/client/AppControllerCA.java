package com.harinosoftware.template.client;

import java.util.ArrayList;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.History;
import com.harinosoftware.core.client.AppController;
import com.harinosoftware.core.client.event.ITZAppController;
import com.harinosoftware.core.client.event.ITZAppControllerHandler;
import com.harinosoftware.core.client.presenter.ChangePassPresenter;
import com.harinosoftware.core.client.presenter.MainPresenter;
import com.harinosoftware.core.client.presenter.PassPresenter;
import com.harinosoftware.core.client.presenter.Presenter;
import com.harinosoftware.core.client.presenter.SignInPresenter;
import com.harinosoftware.template.client.view.ChangepassViewImpl;
import com.harinosoftware.template.client.view.MainViewImpl;
import com.harinosoftware.template.client.view.PassImp;
import com.harinosoftware.template.client.view.SignInViewImpl;


public class AppControllerCA extends AppController{
	
	private HandlerManager eventBus;
	private int status=0;
	
	public AppControllerCA(String serviceURL, HandlerManager eventBus) {
		super(serviceURL, eventBus);
		this.eventBus=eventBus;
		init_event();
	}

	private void init_event(){		
		eventBus.addHandler(ITZAppController.TYPE, new ITZAppControllerHandler(){			
			@Override
			public void escucha(ITZAppController obj) {
				String redirect="";
				
			     Presenter presenter=null;
				if(obj.isEs()){	
					  if ("p=main".equals(obj.getToken()))presenter = new MainPresenter(obj.getEventBus(), new MainViewImpl<JSONValue>(obj.getServiceURL()) , obj.getServiceURL());
 					  else if("New-Password".equals(obj.getToken()))presenter = new ChangePassPresenter(obj.getEventBus() , new ChangepassViewImpl<JSONValue>(),obj.getServiceURL());	    
				      else if(status==0)presenter = new MainPresenter(obj.getEventBus() , new MainViewImpl<JSONValue>(obj.getServiceURL()) , obj.getServiceURL());		
				}
				else  if(obj.isEs()==false){	 
					if(obj.getToken().equals("p=forgot")){
						 presenter = new PassPresenter(obj.getEventBus() , new PassImp<JSONValue>() , obj.getServiceURL());
						 redirect=obj.getToken();   
					 }
					 else{ 
					 presenter = new SignInPresenter(obj.getEventBus() , new SignInViewImpl<JSONValue>() ,obj.getServiceURL());
					 redirect="p=signin"; 
					 }
					 History.newItem(redirect, false);
				  } 
				  if (presenter != null ) {
				  presenter.go(getContainer());				
				  if(status==0){
				 	       ArrayList<String> actual=new ArrayList<String>();
				           actual.add(obj.getToken());    	  
				           presenter.item_actual(actual);
				 	 } 
				      status=1;				 	  
				  }
				
	
				} 
			});
		}
}
