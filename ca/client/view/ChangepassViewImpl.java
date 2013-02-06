package com.harinosoftware.ca.client.view;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;


import com.harinosoftware.core.client.shared.Util;
import com.harinosoftware.core.client.view.ChangePassInView;


public class ChangepassViewImpl<T> extends LayoutContainer implements ChangePassInView<T>{
	private Presenter<T> presenter;
	private TextField<String> pass1;
	private TextField<String> pass2;
	private Button crear,limpiar;
	private final String CAMPOREQUERIDO="Este campo es obligatorio";	
	private ContentPanel panel;
    private FlexTable table,tablebtn;
	private VerticalPanel contenedor;
	private TextField campos[];
	
    
    
	
	@Override
	protected void onRender(Element parent, int index) {
	super.onRender(parent, index);
	obtener_instancia();
	agregar_config();
	agregar_widgets();
	asignar_evento();
	}
	
	
	
	
	private void obtener_instancia() {
		pass1=new TextField<String>();
		pass2=new TextField<String>();
		crear=new Button("Change password");
		limpiar=new Button("Clear");
		panel=new ContentPanel();
		table = new FlexTable();
		tablebtn = new FlexTable();	
		contenedor=new VerticalPanel();
		contenedor.setHorizontalAlign(HorizontalAlignment.CENTER);
		campos=new TextField[]{pass1,pass2};
	}

	private void agregar_config() {
		pass1.setPassword(true);
		pass2.setPassword(true);
		pass1.setAllowBlank(false);
		pass2.setAllowBlank(false);
		pass1.getMessages().setBlankText(CAMPOREQUERIDO);
		pass2.getMessages().setBlankText(CAMPOREQUERIDO);
	    panel.setWidth(400);
	    table.setCellSpacing(20);
		table.setCellPadding(4);
		tablebtn.setCellSpacing(20);
		panel.setHeading("Change password");
				
	
	}
	private void agregar_widgets(){
		table.setWidget(0, 0, new Label("Password"));
		table.setWidget(0, 1, new Label(":"));
		table.setWidget(0, 2, pass1);
		table.setWidget(1, 0, new Label("Re-type Password"));
		table.setWidget(1, 1, new Label(":"));
		table.setWidget(1, 2, pass2);
		tablebtn.setWidget(0, 0, limpiar);
		tablebtn.setWidget(0, 1, crear);
		contenedor.add(table);
		contenedor.add(tablebtn);
	    panel.add(contenedor);
	    this.getElement().setId("nueva_contra");
	    this.add(panel);
	}




	private void asignar_evento() {
		
		crear.addListener(Events.OnClick, new Listener<BaseEvent>() {
			@Override
			public void handleEvent(BaseEvent be){
				if(Util.campos_vacios(campos)){
				Window.alert("Debe de completar los datos de forma correcta"); 	
				}
				else if(Util.campos_errores(campos)){
				Window.alert("Todavía los campos presentan errores"); 	
				}
				else			
						presenter.onSignInButtonClicked();				
			}		
		});	

		limpiar.addListener(Events.OnClick, new Listener<BaseEvent>() {
			@Override
			public void handleEvent(BaseEvent be) {
				Util.limpiar(campos);
			}
		});			
		
	pass2.addListener(Events.OnKeyUp, new Listener<BaseEvent>() {
		@Override
		public void handleEvent(BaseEvent be){			
		if(pass2.getRawValue().equals(pass1.getRawValue())==false)
			pass2.markInvalid("Las Contraseña no coinciden");	
		else
			pass2.clearInvalid();					
		}
	});		
	
	pass2.addListener(Events.OnBlur, new Listener<BaseEvent>() {
		@Override
		public void handleEvent(BaseEvent be){			
		   if(pass2.getRawValue().trim().length()>0){
		     if(pass2.getRawValue().equals(pass1.getRawValue())==false)
			    pass2.markInvalid("Las Contraseña no coinciden");	
		      else
			     pass2.clearInvalid();		
		   } 
		}
	});		
	
	
	
	}

	@Override
	public void setPresenter(Presenter<T> presenter) {
		this.presenter = presenter;			
	}

	@Override
	public String getPassword() {		
		return pass1.getRawValue();
	}

	@Override
	public String getConfirPass() {		
		return pass2.getRawValue();
	}
	
	@Override
	public Widget asWidget() {	
	return this;
	}
	
	
}
