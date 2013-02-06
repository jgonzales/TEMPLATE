package com.harinosoftware.template.client.view;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.harinosoftware.core.client.presenter.PassPresenter;
import com.harinosoftware.core.client.view.PassInView;

public class PassImp<T> extends LayoutContainer implements PassInView<JSONValue> {
	
	private Label lvp;
	private TextField<String> textBox;
	private RadioGroup radioGroup;
	private Radio radio1, radio2, radio3;
	private String valor1, valor2;
	private VerticalPanel vp;
	private Label titulo, lo;
	private Button ba, bvp;
	private Presenter<?> presenter;
	private String opcion;
	HTML html;
	
	HorizontalPanel p;
	
	@Override
	protected void onRender(Element parent , int index) {
		super.onRender(parent, index);
	    p = new HorizontalPanel();
	    
	    ba = new Button("<b>Atras</b>");
	    ba.setStyleName("passimp-ba redondear");
	    //evento boton
	    ba.addClickHandler(new ClickHandler() {		
			@Override
			public void onClick(ClickEvent event) {
				 History.newItem("p=signin");
			}
		});
	    p.add(ba);
	    
	    titulo = new Label("<hr><b>No puedes iniciar sesion?</b>");
	    //titulo.getElement().setId("passimp-titulo");
	    titulo.setStyleName("passimp-titulo");
	    lo = new Label("Selecione una de la siguientes opciones.");
	    //lo.getElement().setId("passimp-lo");
	    lo.setStyleName("passimp-lo");
	    p.add(titulo);
	    p.add(lo);
	    
	    radio1 = new Radio();
	    radio1.setFieldLabel("radio1");
	    radio1.setBoxLabel("Introducir mi nombre de usuario.<br>");
	    //radio1.setStyleName("");
	  
	    radio2 = new Radio();
	    radio2.setFieldLabel("radio2");
	    radio2.setBoxLabel("Introducir mi direccion de email");
	    //radio2.setStyleName("");
	    
	    radio3 = new Radio();
	    radio3.setFieldLabel("radio3");
	    radio3.setBoxLabel("Introducir mi direccion de email.");
	    //radio3.setStyleName("");
	    
	    radioGroup = new RadioGroup();   
	    radioGroup.add(radio1);
	    radioGroup.add(radio2);
	    //radioGroup.add(radio3);
	    radioGroup.setOrientation(Orientation.VERTICAL);
	    radioGroup.setName("grupo1");
	    radioGroup.getElement().setId("passimp-radiogroup");
	    radioGroup.setStyleName("passimp-radiogroup");
	    //evento radio
	    radioGroup.addListener(Events.Change, new Listener<FieldEvent>(){
			@Override
			public void handleEvent(FieldEvent be) {
				if(radio1.getFieldLabel()==radioGroup.getValue().getFieldLabel()){
					lvp.setVisible(true);
					textBox.setVisible(true);
					lvp.setText(valor1);
					opcion="1";
				}
				if(radio2.getFieldLabel()==radioGroup.getValue().getFieldLabel()){
					lvp.setVisible(true);
					textBox.setVisible(true);
					lvp.setText(valor2);
					opcion="2";
				}
				/*if(radio3.getFieldLabel()==radioGroup.getValue().getFieldLabel()){
					lvp.setVisible(true);
					textBox.setVisible(true);
				}	*/	
			}      
	    });
	    
	    p.add(radioGroup);
	    
	    p.getElement().setId("passimp-p");
	    p.addStyleName("redondear");
	    p.setSpacing(4);
	    
	    textBox = new TextField<String>();
	    textBox.setWidth("250px");
	    textBox.setHeight("25px");
	    //textBox.setStyleName("redondear");
	    //p.add(textBox);
	    
	    vp = new VerticalPanel();
	    vp.getElement().setId("passimp-vp");
	    vp.addStyleName("redondear");
	    vp.setSpacing(4);
	    
	    valor1 = "<span class='passimp-vp-valor'>Para restablecer tu contraseña, introduzca su nombre de  usuario SWISS SPORT.</span><hr>Nombre de Usuario:";
	    valor2 = "<span class='passimp-vp-valor'>Para restablecer tu contraseña, introduce el correo electronico que utilices en SWISS SPORT.</span><hr>Dirección de correo electrónico:";
	    lvp = new Label();
	    bvp = new Button("<b>Continuar</b>");
	    bvp.setStyleName("passimp-vp-bvp redondear");
	    //evento boton
	    bvp.addClickHandler(new ClickHandler() {		
			@Override
			public void onClick(ClickEvent event) {
				if(radioGroup.getValue()==null){
					Window.alert("Seleccione una de la opciones.");
					//MessageBox.alert("Alerta", "Seleccione una de la opciones.", null);				
				}
				else{
					//aqui todo cuano se selecciono algo
					//Window.alert("Realizando Procesos");
					if(textBox.getRawValue().toString().trim().length()>0){
						ocultarMostrartodo(false);
						imgenviando();
					}
					else{
						lo.setText("Debe de llenar los datos de forma correcta.");
					}
				}
			}
		});
	    
	    vp.add(lvp);
	    vp.add(textBox);
	    vp.add(bvp);
	    p.add(vp);
	    
	    lvp.setVisible(false);
	    textBox.setVisible(false);
	    
	    getElement().setId("passimp-this");
	    //getBodyElement().addClassName("rootpanel");
	    add(p);
	}
	
	public void ocultarMostrartodo(Boolean bol){
		vp.setVisible(bol);
		ba.setVisible(bol);
		titulo.setVisible(bol);
		lo.setVisible(bol);
		radioGroup.setVisible(bol);
	}
	
	public void imgenviando(){
		html = new HTML( "<div id='fred' style='position:absolute; top:175px; left:145px; text-align: center;'><center><b>Enviando Email. Favor aguardar.</b><br><img src='img/enviando5.gif' width='300'/><center></div>"); 
	    p.add(html);
	    presenter.onPassRButtonClicked();	    
	}
	
	@Override
	public String borrar(){
	p.remove(html);	
	ocultarMostrartodo(true);
	textBox.setRawValue("");
	return "";
	}
   
	@Override
	public void setPresenter(PassPresenter passPresenter) {
	this.presenter=passPresenter;		
	}

	@Override
	public Widget asWidget() {
		return this;
		
	}

	@Override
	public String getOpcion() {
	return opcion;
	}

	@Override
	public String getValor() {
		return textBox.getRawValue();
	}
}
