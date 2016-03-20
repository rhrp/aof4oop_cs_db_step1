package rhp.aof4oop.cs.apps;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;

import rhp.aof4oop.cs.datamodel.Coordinator;
import rhp.aof4oop.cs.datamodel.ExamOfficer;
import rhp.aof4oop.cs.datamodel.Moderator;
import rhp.aof4oop.cs.datamodel.Person;
import rhp.aof4oop.cs.datamodel.Principal;
import rhp.aof4oop.cs.datamodel.Student;
import rhp.aof4oop.cs.datamodel.Tutor;

/**
 * This application moves objects from version A to temporary classes with a structure in version B 
 * @author rhp
 */
public class MigrateDBStep1 
{
	public static void main(String[] args) throws Exception
	{
		adaptAndMoveObjectsToTemporaryB();
		dropClassesVersionA();
	}
	/**
	 * Move objects from version A to a temporary one in version B
	 * @throws Exception
	 */
	private static void adaptAndMoveObjectsToTemporaryB() throws Exception
	{
		ObjectContainer db=Utils.openDB();
		try 
		{
			// check previously created database
			System.out.println("Before");
			Utils.showDB(db);
			System.out.println();
			
			adaptAndMoveStudentsToTemporaryB(db);
			adaptAndMoveStaffToTemporaryB(db);			
			
			// Show database after data migration
			System.out.println("After");
			Utils.showDB(db);
		}
		finally 
		{
		    db.close();
		}
	}
	/**
	 * Move Student objects from version A to a temporary one in version B
	 * @param db
	 */
	private static void adaptAndMoveStudentsToTemporaryB(ObjectContainer db)
	{
		//Students
		ObjectSet<Student> result_students_a = db.queryByExample(Student.class);
		System.out.println("Moving Students from A to B: "+result_students_a.size());
		for(Student s:result_students_a)
		{
			rhp.aof4oop.cs.datamodel_b.Student s_b=new rhp.aof4oop.cs.datamodel_b.Student(s.getFirstName(),s.getMiddleName(),s.getLastName(),s.getBirth(),s.getSex());
			db.store(s_b);
			db.delete(s);
		}
	}
	/**
	 * Move Person objects from version A to a temporary one in version B
	 * @param db
	 * @throws Exception
	 */
	private static void adaptAndMoveStaffToTemporaryB(ObjectContainer db) throws Exception
	{
		//Staff
		ObjectSet<Person> result_staff_a = db.queryByExample(Person.class);
		System.out.println("Migrating Staff from A to B: "+result_staff_a.size());
		for(Person p:result_staff_a)
		{
			String middle_name;
			String last_name;
			if(p.getSurname().lastIndexOf(' ')>0)
			{
				last_name=p.getSurname().substring(p.getSurname().lastIndexOf(' '));
				middle_name=p.getSurname().substring(0,p.getSurname().lastIndexOf(' '));
			}
			else
			{
				middle_name="";
				last_name=p.getSurname();
			}
			if(p instanceof Principal)
			{
				rhp.aof4oop.cs.datamodel_b.Principal p_b=new rhp.aof4oop.cs.datamodel_b.Principal(p.getTitle(),p.getFirstName(),middle_name,last_name,p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
				db.store(p_b);
			}
			else if(p instanceof Coordinator)
			{
				rhp.aof4oop.cs.datamodel_b.Coordinator p_b=new rhp.aof4oop.cs.datamodel_b.Coordinator(p.getTitle(),p.getFirstName(),middle_name,last_name,p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
				db.store(p_b);
			}
			else if(p instanceof ExamOfficer)
			{
				rhp.aof4oop.cs.datamodel_b.ExamOfficer p_b=new rhp.aof4oop.cs.datamodel_b.ExamOfficer(p.getTitle(),p.getFirstName(),middle_name,last_name,p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
				db.store(p_b);
			}
			else if(p instanceof Tutor)
			{
				rhp.aof4oop.cs.datamodel_b.Tutor p_b=new rhp.aof4oop.cs.datamodel_b.Tutor(p.getTitle(),p.getFirstName(),middle_name,last_name,p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
				db.store(p_b);
			}
			else if(p instanceof Moderator)
			{
				rhp.aof4oop.cs.datamodel_b.Moderator p_b=new rhp.aof4oop.cs.datamodel_b.Moderator(p.getTitle(),p.getFirstName(),middle_name,last_name,p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
				db.store(p_b);
			}
			else
			{
				throw new Exception("Unexpected object type!!!");
			}
			db.delete(p);
		}
	}
	/**
	 * Drop classes in Version A
	 */
	private static void dropClassesVersionA()
	{
		System.out.print("Dropping classes in version A...");
		//Dropping earlier class definitions in the database configuration
		EmbeddedConfiguration cfg = Db4oEmbedded.newConfiguration();
		cfg.common().objectClass(Student.class).rename("rhp.trash.Student");
		cfg.common().objectClass(Person.class).rename("rhp.trash.Person");
		cfg.common().objectClass(Principal.class).rename("rhp.trash.Principal");
		cfg.common().objectClass(Coordinator.class).rename("rhp.trash.Coordinator");
		cfg.common().objectClass(ExamOfficer.class).rename("rhp.trash.ExamOfficer");
		cfg.common().objectClass(Tutor.class).rename("rhp.trash.Tutor");
		cfg.common().objectClass(Moderator.class).rename("rhp.trash.Moderator");
		
		ObjectContainer db=Db4oEmbedded.openFile(cfg,Utils.DB4OFILENAME);
		db.close();
		System.out.println("done!!!");
	}
}
