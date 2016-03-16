package rhp.aof4oop.cs.apps;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

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
		ObjectContainer db=Utils.openDB();
		try 
		{
			// check previously created database
			System.out.println("Before");
			Utils.showDB(db);
			System.out.println();
			
			//Students
			ObjectSet<Student> result_students_a = db.queryByExample(Student.class);
			System.out.println("Moving Students from A to B: "+result_students_a.size());
			for(Student s:result_students_a)
			{
				rhp.aof4oop.cs.datamodel_b.Student s_b=new rhp.aof4oop.cs.datamodel_b.Student(s.getFirstName(),s.getMiddleName(),s.getLastName(),s.getBirth(),s.getSex());
				db.store(s_b);
				db.delete(s);
			}
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
			// Show database after data migration
			System.out.println("After");
			Utils.showDB(db);
		}
		finally 
		{
		    db.close();
		}
	}
}
