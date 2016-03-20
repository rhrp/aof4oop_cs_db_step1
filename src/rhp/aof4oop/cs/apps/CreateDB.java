package rhp.aof4oop.cs.apps;

import java.util.Date;

import com.db4o.ObjectContainer;
import rhp.aof4oop.cs.datamodel.Coordinator;
import rhp.aof4oop.cs.datamodel.ExamOfficer;
import rhp.aof4oop.cs.datamodel.Moderator;
import rhp.aof4oop.cs.datamodel.Person;
import rhp.aof4oop.cs.datamodel.Principal;
import rhp.aof4oop.cs.datamodel.Student;
import rhp.aof4oop.cs.datamodel.Tutor;

/**
 * This application creates a database for testing porposes
 * 
 * @author rhp
 *
 */
public class CreateDB
{
	public static void main(String[] args) throws Exception
	{
	
		ObjectContainer db=Utils.openDB();
		try 
		{
			// check previously created database
			if(Utils.populatedDB(db))
			{
				System.out.println("The database was already created. If you intend to create a new one, please delete the \""+Utils.DB4OFILENAME+"\" file!!!");
				return;
			}

			
			System.out.println("Students...");
			for(int i=0;i<2000;i++)
			{
				Student student = new Student();
				student.setFirstName("firstname#"+i);
				student.setMiddleName("middleName#"+i);
				student.setLastName("middleName#"+i);
				student.setBirth(new Date());
				student.setSex(i%2==0?"M":"F");
				
				db.store(student);
			}
			System.out.println("Staff...");
			for(int i=0;i<200;i++)
			{
				Person staff;
				if(i%5==1)
				{
					staff=new Principal();
				}
				else if(i%5==2)
				{
					staff=new Coordinator();
				}
				else if(i%5==3)
				{
					staff=new ExamOfficer();
				}
				else if(i%5==4)
				{
					staff=new Tutor();
				}
				else
				{
					staff=new Moderator();
				}
				staff.setFirstName("firstname#"+i);
				staff.setSurname("middlename#"+i+" lastname#"+i);
				staff.setAddress("address#"+i);
				staff.setPostCode("postCode#"+i);
				staff.setTelephoneNumber("telephoneNumber#"+i);
				staff.setMobileNumber("mobileNumber#"+i);
				
				db.store(staff);
			}
			System.out.println("The database was successfully created!");
		} 
		finally 
		{
		    db.close();
		}
	}
}
