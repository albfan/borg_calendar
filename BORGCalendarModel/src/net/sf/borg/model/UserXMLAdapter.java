// This code was generated by GenerateDataObjects
package net.sf.borg.model;

import net.sf.borg.model.db.*;
import net.sf.borg.model.User;
import net.sf.borg.common.util.XTree;
public class UserXMLAdapter extends BeanXMLAdapter {

	public XTree toXml( KeyedBean b )
	{
		User o = (User) b;
		XTree xt = new XTree();
		xt.name("User");
		xt.appendChild("KEY", Integer.toString(o.getKey()));
		if( o.getUserName() != null && !o.getUserName().equals(""))
			xt.appendChild("UserName", o.getUserName());
		if( o.getPassword() != null && !o.getPassword().equals(""))
			xt.appendChild("Password", o.getPassword());
		if( o.getUserId() != null )
			xt.appendChild("UserId", BeanXMLAdapter.toString(o.getUserId()));
		return( xt );
	}

	public KeyedBean fromXml( XTree xt )
	{
		User ret = new User();
		String ks = xt.child("KEY").value();
		ret.setKey( BeanXMLAdapter.toInt(ks) );
		String val = "";
		val = xt.child("UserName").value();
		if( !val.equals("") )
			ret.setUserName( val );
		val = xt.child("Password").value();
		if( !val.equals("") )
			ret.setPassword( val );
		val = xt.child("UserId").value();
		ret.setUserId( BeanXMLAdapter.toInteger(val) );
		return( ret );
	}
}