package com.harinosoftware.injection;

import java.io.InputStream;
import org.w3c.dom.Document;
import com.harinosoftware.core.dao.MyDAO;
import com.harinosoftware.core.security.ClassCacheUser;
import com.harinosoftware.core.security.ClassCreateUser;
import com.harinosoftware.core.security.ITZCache;
import com.harinosoftware.core.security.ITZCreateUser;
import com.harinosoftware.core.security.ITZDao;
import com.harinosoftware.core.security.ITZMethod;
import com.harinosoftware.core.security.MembershipUser;
import com.harinosoftware.core.server.Hash;
import com.harinosoftware.core.shared.GlobalConstant;



public class Injection implements ITZMethod {

@Override
public ITZCache	newClassCacheUser(){return new ClassCacheUser(Hash.HORAS,new MyDAO());}
@Override
public ITZDao newClassDao(){return new MyDAO();}
@Override
public ITZCreateUser newClassCreateUser(){return new  ClassCreateUser(new MyDAO());}
@Override
public Document newClassFileSecurity(String role){
	System.out.println("reflexion y rol "+role);
	InputStream in = Hash.getInput(role+GlobalConstant.RULES_CONCAT, null);
	return Hash.getXml(null,in);	
}
@Override
public String newHeadFromNewPass(MembershipUser user){return "Solicitud de nueva contraseña de su cuenta Swiss Sport CORP.";}
@Override
public String newEmailOrigen(){return "ridiaz@harinosoftware.com";}
@Override
public String newMessageFromNewPass(MembershipUser user_){
	 StringBuffer mensaje=new StringBuffer();
	    mensaje.append("<h1> Bienvenido/a "+user_.getName()+" "+user_.getLastName()+"</h1>");
	    mensaje.append("<br>");
	    mensaje.append("<br><h4>Usted a obtenido un password nuevo en su cuenta Swiss Sport CORP.</h4> ");
	    mensaje.append("<br><h4>Su nueva contraseña es: "+user_.getPassword()+"</h4> ");
	    mensaje.append("<br><h4>Acceda : <a href=\""+Hash.URL_BACKEND+"\">aqui</a></h4> ");
	    mensaje.append("<br><br><h4>Saludos</h4> ");
	    return mensaje.toString();
}
@Override
public String newHeadFromNewUser(MembershipUser user ){return "Nueva Cuenta de usuario Swiss Sport CORP.";}
@Override
public String newMessageFromNewUser(MembershipUser  user_){
    StringBuffer mensaje=new StringBuffer();
    mensaje.append("<h1> Bienvenido/a "+user_.getName()+" "+user_.getLastName()+"</h1>");
    mensaje.append("<br>");
    mensaje.append("<br><h4>Le damos la bienvenida como nuevo usurio al sistema de administracion de Swiss Sport CORP.</h4> ");
    mensaje.append("<br><h4>Su nombre de usuario es: "+user_.getUserName()+"</h4> ");
    mensaje.append("<br><h4>Su contraseña es: "+user_.getPassword()+"</h4> ");
    mensaje.append("<br><h4>Acceda : <a href=\""+Hash.URL_BACKEND+"\">aqui</a></h4> ");
    mensaje.append("<br><br><h4>Saludos</h4> ");
    return mensaje.toString();
}
@Override
public Class<?> newClassClient() {
	return MembershipUser[].class;}




	
}
