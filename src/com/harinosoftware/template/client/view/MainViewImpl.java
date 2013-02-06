package com.harinosoftware.template.client.view;

import java.util.HashMap;
import java.util.Map;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.Model;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.harinosoftware.core.client.event.LoggedOutEvent;
import com.harinosoftware.core.client.event.ResponsePermit;
import com.harinosoftware.core.client.presenter.MainPresenter;
import com.harinosoftware.core.client.shared.Util;
import com.harinosoftware.core.client.view.MainTabItem;
import com.harinosoftware.core.client.view.MainView;
import com.harinosoftware.core.client.view.PermitList;


public class MainViewImpl<T> extends Viewport implements MainView<T>,ValueChangeHandler<String>,ResponsePermit, Listener<BaseEvent> {

	private Presenter<T> presenter;
	private BorderLayoutData centerdata;
	private TabPanel center;
    private Map<String,Object[]>listcomonet;
    private TreeStore<Model> store;
    private boolean isRefreshMain;
    private LayoutContainer north;
    private BorderLayoutData northData;
    private Button logout,usuario;
    private String user,serviceURL;
    private ToolBar toolBar;
    private Menu menuroles;
	private MainTabItem itemactual;
	private ContentPanel west;
	private BorderLayoutData westData;
	private TreePanel<Model> tree;
	private Button barra;
	private Menu tabRolAndPermi;
	private MenuItem crear_user;
	private MenuItem role;
	private String rolactivo;
	
	

	public MainViewImpl(String serviceURL) {
		this.serviceURL=serviceURL;
		listcomonet=new HashMap<String, Object[]>();
		History.addValueChangeHandler(this);	
	    new PermitList(serviceURL).getListRoles(this);
	} 

	
	
	@Override
	protected void onRender(Element parent , int index) {		
		super.onRender(parent, index);
		init_instancia();	
   		init_addObject();		
		init_addEvent();  
		init_confi();  
	    security();
	}

	@Override
	public void setPresenter(Presenter<T> presenter) {		
		this.presenter = presenter;		
	}
   
	private void security() {
		/****Codigo de seguriadad********************************************/
		//PermitList permitList=new PermitList(serviceURL);
		//permitList.setHide(new Component[]{this},null);
		//Model[] bpifp=new BaseModel[]{(BaseModel) wsi};
	   	//listcomonet.put("catalogo",new BaseModel[]{(BaseModel)ref});
		//permitList.getListpermit(this,"main",listcomonet,store); 		
		/*****Codigo de seguridad*******************************************/
	}

	private void init_confi() {
		setLayout(new BorderLayout());	
		toolBar.setAlignment(HorizontalAlignment.RIGHT);
		center.setResizeTabs(true);
		center.setAnimScroll(true);
		centerdata.setMargins(new Margins(5,0,5,5));
		west.setAnimCollapse(false);  
		west.setHeading("your field");
		west.setBodyStyle("padding: 6px");
		west.setLayout(new FitLayout());  
	    westData.setMargins(new Margins(5,0,5,5));
		westData.setCollapsible(true);
		toolBar.setAlignment(HorizontalAlignment.RIGHT);
		toolBar.setWidth("100%");
	}

	private void init_addEvent() {
		logout.addListener(Events.OnClick, this);
	}

	private void init_addObject() {
		tabRolAndPermi.add(crear_user);   
		tabRolAndPermi.add(role); 
		barra.setMenu(tabRolAndPermi);
		toolBar.add(barra);
		toolBar.add(logout);
		toolBar.add(usuario);
		usuario.setMenu(menuroles);
		west.add(tree);
		north.add(toolBar);
		add(north , northData);
		add(center, centerdata);			
		add(west, westData);		
	}

	private void init_instancia(){
		center = new TabPanel();
		toolBar=new ToolBar();
	   	user=Util.getUserFormat();
	   	north = new LayoutContainer();
	    west = new ContentPanel();  
		northData = new BorderLayoutData(LayoutRegion.NORTH, 55);
	    westData = new BorderLayoutData(LayoutRegion.WEST);
	   	logout = new Button("Log out", IconHelper.createStyle("add16-sign-out"));
	   	usuario=new Button(user,IconHelper.createStyle("add16-usuarioactual"));
	   	centerdata = new BorderLayoutData(LayoutRegion.CENTER);
	   	menuroles = new Menu();
	   	store = new TreeStore<Model>(); 
	   	tree = new TreePanel<Model>(store);
	   	barra = new Button("Security", IconHelper.createStyle("add16-lock")); 
	    tabRolAndPermi = new Menu();  
	    crear_user = new MenuItem("Users", IconHelper.createStyle("add16-main-user"));
	    role = new MenuItem("Roles and Permissions", IconHelper.createStyle("add16-roles"));
	}

	@Override
	public Widget asWidget() {
	isRefreshMain=true;
	return this;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event){
		 verificartoken(event.getValue());
	}


	public void verificartoken(String token){
		token = URL.decode(token);		
		if (token != null && Util.tabexiste(token,center)==false) {
			
		}
	}	

	
	public void addTab(String title ,String token , Widget widget) {
		MainTabItem item = new MainTabItem();
		item.setClosable(true);
		item.setText(title);
		item.setToken(token);
		item.setLayout(new FitLayout());
		//item.setToolTip(token);
		item.add(widget);
		itemactual=item;
		addItem(item);

	}
	

	private void addItem(MainTabItem item){
	   if(isRefreshMain==false && itemactual!=null){
		   center.add(item);
		   center.setSelection(item);
		}
    }
	
	@Override
	public void setListPermit(Component[] components, Object opcional){		
		for(Component cm:components){
			cm.show();
			}
    isRefreshMain=false;
    addItem(itemactual);
	}

	@Override
	public void handleEvent(BaseEvent be){ 
		 if(logout==be.getSource())((MainPresenter)presenter).onLogout(new LoggedOutEvent());       	
	}

	@Override
	public void setListRoles(Map<String, Object> config){	  
		JSONArray roles=(JSONArray) config.get("roles");
		String roleactual=(String) config.get("roleactual");
		for(int i=0;i<roles.size();i++){
	    final MenuItem btnroles;
	    if(roles.get(i).isString().stringValue().equals(roleactual)){
	    	rolactivo=roles.get(i).isString().stringValue();
	    	btnroles = new MenuItem("<b>"+roles.get(i).isString().stringValue()+"</b>",IconHelper.createStyle("add16-listroles"));
	    }		    
	    else
	    	btnroles = new MenuItem(roles.get(i).isString().stringValue(),IconHelper.createStyle("add16-sinuso"));
	   
	    btnroles.setId(roles.get(i).isString().stringValue());
	    menuroles.add(btnroles);	
	    
	    btnroles.addListener(Events.Select, new Listener<BaseEvent>() {		
			@Override
			public void handleEvent(BaseEvent ce) {
			if(rolactivo.equals(btnroles.getId())==false)	
				((MainPresenter)presenter).updateRolactual(btnroles.getId());
			}});}
		
		
		
	}

	
}
