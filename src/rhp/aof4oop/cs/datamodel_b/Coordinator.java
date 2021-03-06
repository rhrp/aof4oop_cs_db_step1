package rhp.aof4oop.cs.datamodel_b;

/**
 * Version B
 * @author rhp
 *
 */
public class Coordinator extends Staff 
{
	public Coordinator()
	{
		super();
	}

	public Coordinator(String title, String firstName, String middleName,
			String lastName, String address, String postCode,
			String telephoneNumber, String faxNumber, String mobileNumber,
			boolean passedD32Qualification, boolean passedD34Qualification,
			boolean passedD36Qualification) 
	{
		super(title, firstName, middleName, lastName, address, postCode,
				telephoneNumber, faxNumber, mobileNumber, passedD32Qualification,
				passedD34Qualification, passedD36Qualification);
	}
	
}
