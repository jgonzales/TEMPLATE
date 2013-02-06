package com.harinosoftware.template.client.view;



import java.util.Random;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Element;
import com.harinosoftware.core.client.view.SignInView;
import com.extjs.gxt.ui.client.widget.Label;


public class SignInViewImpl<T> extends LayoutContainer implements SignInView<T> {
	
	private boolean esperar;	
	private Presenter<T> presenter;
	private TextField<String> firstName;
	private TextField<String> pass;
	private TextField<String> clientid;
	Button conf;
	private Button sendButton;

	@Override
	public void setPresenter(Presenter<T> presenter) {		
	this.presenter = presenter;		
	}

	@Override
	public String getUsername() {		
		return this.firstName.getValue();
	}

	@Override
	public String getPassword() {		
		return this.pass.getValue();
	}
	
	@Override
	public String getClientId() {
		
		return this.clientid.getValue();
	}


  public SignInViewImpl() {
	  
	  //initialize();
  }
  
  @Override
  protected void onRender(Element parent , int index) {
	super.onRender(parent, index);  
	
	VerticalPanel contenedor = new VerticalPanel();
	contenedor.getElement().setId("signin-contenedor");
	contenedor.addStyleName("signin-contenedor");
	
	contenedor.add(new HTML("<p style='text-align:left; font-size:35px; font-family:Impact; '>&nbsp;&nbsp;&nbsp;<i><b><span style='color:white;'>SWISS</span><span style='color:black;'>CENTRAL</span></b></i></p><hr>"));
	
	HorizontalPanel hp = new HorizontalPanel();
	hp.getElement().setId("signin-hp");
    hp.addStyleName("redondear-sign");
    
    String[] list_img = {"01","02","03","04","05","06","07","08","09","10","11","12"};
    Random rand = new Random();
    int x = rand.nextInt(list_img.length);
  
    HTML html = new HTML("<div id='fred' style='float:left; margin:10px; width:714px; height:530px;'><img src='img/login-img"+list_img[x]+".jpg' width='100%' height='100%' style='border:1px solid #000000;'/></div>"); 
    hp.add(html);
	
//	initWidget(hp);
	  
  	VerticalPanel simple = new VerticalPanel();  
  	simple.addStyleName("signin-vp");
 
    Label titulo=new Label("<br><b>Sign in</b><hr><br>");
    simple.add(titulo);
    
    //initWidget(simple);
    Label l1=new Label("Username:");
    simple.add(l1);
    firstName = new TextField<String>();
    firstName.setWidth("193px");
    firstName.setAllowBlank(false);
    firstName.addKeyListener(new KeyListener(){
    	@Override
    	public void componentKeyPress(ComponentEvent event) {
    		if (event.getKeyCode() == 13) {
				pass.focus();
			}
    		super.componentKeyPress(event);
    	}
    });
    //firstName.setToolTip(new ToolTipConfig("Information", "Insert  your user name here"));  
    simple.add(firstName);
    
    Label l2=new Label("Password:");
    simple.add(l2);
    pass = new TextField<String>(); 
    pass.setWidth("193px");
    pass.setAllowBlank(false);
    pass.setPassword(true);
    pass.addKeyListener(new KeyListener(){
    	@Override
    	public void componentKeyPress(ComponentEvent event) {
    		if (event.getKeyCode() == 13) {
				if (presenter != null){				
					presenter.onSignInButtonClicked();
				}
			}
    		super.componentKeyPress(event);
    	}
    });
    simple.add(pass);
    
//    clientid = new TextField<String>();  
//    clientid.setFieldLabel("Client Number");  
//    clientid.setAllowBlank(false);  
//            
//    simple.add(clientid, formData);
    
    conf = new Button("Forgot password ?");
    conf.getElement().setId("signin-conf");

    
    conf.addStyleName("redondear");
    sendButton = new Button("Send");
    sendButton.getElement().setId("signin-sendButton");
    sendButton.addStyleName("redondear");
    simple.add(conf);
    simple.add(sendButton);
    
    sendButton.addClickHandler(new ClickHandler() {		
		@Override
		public void onClick(ClickEvent event) {	

			
			try {
			int error=0;
				if(firstName.getValue()==null){
					firstName.markInvalid("Debe de llenar este campo con su nombre de usuario");
					error=1;
				}
				if(pass.getValue()==null){
					pass.markInvalid("Debe de llenar este campo con su password");
					error=1;
				}
				if(error==0 && esperar==false)
				presenter.onSignInButtonClicked();
				
			} catch (Exception e) {
				System.out.println(e);
			}
		
		}
	});
    
    conf.addClickHandler(new ClickHandler() {		
		@Override
		public void onClick(ClickEvent event) {	
			try {
				presenter.onPassRButtonClicked();
				
			} catch (Exception e) {
				System.out.println("Error :"+e);
			}		
		}
	});
    hp.add(simple);
    hp.addStyleName("shadow");
    contenedor.add(hp);
    
    HorizontalPanel hp2 = new HorizontalPanel();
	hp2.getElement().setId("signin-hp2");
    hp2.addStyleName("redondear-sign2");
    hp2.add(new HTML("<br><p style='text-align:center; font-family:Tahoma; font-size:11px;'><b>©2012 All rights reserved</b></p>"));
    hp2.addStyleName("shadow");
    
    contenedor.add(hp2);
	add(contenedor);  
  }

@Override
public Widget asWidget() {
	
	return this;
}

@Override
public void setBloqueo(boolean bloqueo) {
	esperar=bloqueo;
	if(bloqueo)sendButton.setText("Wait...");
	else if(bloqueo==false)sendButton.setText("Send");
}

}


