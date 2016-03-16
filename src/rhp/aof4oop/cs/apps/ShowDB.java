package rhp.aof4oop.cs.apps;

import com.db4o.ObjectContainer;

/**
 * 
 * @author rhp
 *
 */
public class ShowDB 
{
	public static void main(String[] args) throws Exception
	{
	
		ObjectContainer db=Utils.openDB();
		try 
		{
			Utils.showDB(db);
		}
		finally 
		{
		    db.close();
		}
	}
}
