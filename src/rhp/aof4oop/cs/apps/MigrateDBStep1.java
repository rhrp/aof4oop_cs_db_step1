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
				db.store(convertPrincipal(p,middle_name,last_name));
			}
			else if(p instanceof Coordinator)
			{
				db.store(convertCoordinator(p,middle_name,last_name));
			}
			else if(p instanceof ExamOfficer)
			{
				db.store(convertExamOfficer(p,middle_name,last_name));
			}
			else if(p instanceof Moderator)
			{
				db.store(convertModerator(p,middle_name,last_name));
			}
			else if(p instanceof Tutor)
			{
				db.store(convertTutor(p,middle_name,last_name));
			}			
			else
			{
				throw new Exception("Unexpected object type!!!");
			}
			db.delete(p);
		}
	}
	private static rhp.aof4oop.cs.datamodel_b.Principal convertPrincipal(Person p,String middle_name,String last_name)
	{
		return  new rhp.aof4oop.cs.datamodel_b.Principal(p.getTitle(),p.getFirstName(),middle_name,last_name,p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
	}
	private static rhp.aof4oop.cs.datamodel_b.Coordinator convertCoordinator(Person p,String middle_name,String last_name)
	{
		return  new rhp.aof4oop.cs.datamodel_b.Coordinator(p.getTitle(),p.getFirstName(),middle_name,last_name,p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
	}
	private static rhp.aof4oop.cs.datamodel_b.ExamOfficer convertExamOfficer(Person p,String middle_name,String last_name)
	{
		return  new rhp.aof4oop.cs.datamodel_b.ExamOfficer(p.getTitle(),p.getFirstName(),middle_name,last_name,p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
	}
	private static rhp.aof4oop.cs.datamodel_b.Moderator convertModerator(Person p,String middle_name,String last_name)
	{
		return  new rhp.aof4oop.cs.datamodel_b.Moderator(p.getTitle(),p.getFirstName(),middle_name,last_name,p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
	}
	private static rhp.aof4oop.cs.datamodel_b.Tutor convertTutor(Person p,String middle_name,String last_name)
	{
		return  new rhp.aof4oop.cs.datamodel_b.Tutor(p.getTitle(),p.getFirstName(),middle_name,last_name,p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
	}
	/**
	 * Drop classes in Version A
	 */
	private static void dropClassesVersionA()
	{
		System.out.print("Dropping classes in version A...");
		//Dropping earlier class definitions in the database configuration
		EmbeddedConfiguration cfg = Db4oEmbedded.newConfiguration();
		dropClassesVersion(cfg,Student.class);
		dropClassesVersion(cfg,Person.class);
		dropClassesVersion(cfg,Principal.class);
		dropClassesVersion(cfg,Coordinator.class);
		dropClassesVersion(cfg,ExamOfficer.class);
		dropClassesVersion(cfg,Moderator.class);
		dropClassesVersion(cfg,Tutor.class);
		
		ObjectContainer db=Db4oEmbedded.openFile(cfg,Utils.DB4OFILENAME);
		db.close();
		System.out.println("done!!!");
	}
	private static void dropClassesVersion(EmbeddedConfiguration cfg,Class<?> clzz)
	{
		System.out.print(clzz.getSimpleName()+"...");
		cfg.common().objectClass(clzz).rename("rhp.trash."+clzz.getSimpleName());
	}
}
