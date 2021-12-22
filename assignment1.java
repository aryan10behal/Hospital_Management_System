package assignment1;

import java.util.*;

class Patient   //the blueprint for an individual patient
{
	// Attributes
	private String name;                            //patient name
	private int age;  								//patient age
	private float oxygen_level;						//oxygen levels of patient
	private float body_temperature;					//patient body temperature
	private int ID;									//patient iD assigned by the system
	private int Recovery_Days;						// patient recovery date
	private String institute;						// institute where patient admitted
	private boolean Admitted_or_not;				// tells if patient admitted or not
	
	// Methods:  
	
	public Patient() //default constructor (needless to make)
	{
		}
	public int set_record(String record,int i)  			// to set the data of patient, an alternate for parameterized constructor. 
	{
		try													// to check if correct input entered... any record entered after wrong input will be entered again..
		{
			String[] a=record.trim().split(" ");
			name=a[0];
			body_temperature=Float.parseFloat(a[1]);
			oxygen_level=Float.parseFloat(a[2]);
			age=Integer.parseInt(a[3]);
			ID=i;
			Admitted_or_not=false;
			return 0;
		}
		catch(Exception e)
		{
			System.out.print("\n\nWrong Data input, ");
			return 1;
		}
	}
	public void display_patient()			//to display patient data as per Query 7
	{
		System.out.print("ID: ");
		System.out.println(this.ID);
		System.out.println("Name: "+this.name);
		System.out.println("Temperature is "+Float.toString(this.body_temperature));
		System.out.println("Oxygen Levels is "+Float.toString(this.oxygen_level));
		if(this.Admitted_or_not==true)
			{
			System.out.println("Admission Status: Admitted");
			
			}
		else
		{
			System.out.println("Admission Status: Not Admitted");
		}
		System.out.println("Admitting institute: "+institute);
	}
	
	public void display_all_patients()    // to display data as per query 8
	{
		System.out.print(this.ID);
		System.out.println(" "+this.name);
	}
	
	//getter functions
	public int get_recovery_time()
		{return this.Recovery_Days;}
	public float get_oxygen_level()
		{return this.oxygen_level;}
	public float get_temp_level()
		{return this.body_temperature;}
	public boolean admit_or_not()
		{return this.Admitted_or_not;}
	public int get_ID()
		{return this.ID;}
	public String get_name()
		{return this.name;}
	
	
	// setter functions
	public void set_admit(boolean a)
		{this.Admitted_or_not=a;}	
	public void set_institute(String a)
		{this.institute=a;}
	public void set_recovery_day(int x)
		{this.Recovery_Days=x;}
	

	
}

class All_patients  // deals with all the patients admitted to the camp
{
	//attributes
	private ArrayList<Patient> patients;   			//list of all patients 
	private int ID_to_assign;						//generates the id to be assigned
	private int total_patients;						//total patient count
	private int patients_in_camp;					//patients left in camp
	private int patients_in_centre;					//patients admitted to the institutes
	Scanner in=new Scanner(System.in);
	
	
	//methods: 
	
	public All_patients(int n)    // To create the initial patient list
	{
		patients=new ArrayList<Patient>(n);
		ID_to_assign=0;
		for(int i=0;i<n;i++)
		{
			String record=in.nextLine();
			patients.add(new Patient());
			int x=patients.get(i).set_record(record,i+1);
			if(x==1)
			{   
				System.out.println("At line "+Integer.toString(i+1)+"....");
				System.out.println("Enter all the records after that line again...");
				patients.remove(i);
				i--;
			}
		}
		ID_to_assign=n;
		total_patients=n;
		patients_in_camp=n;
		patients_in_centre=0;
	}
	
	public void add_new_patient()   //Add new patient later if desired (though needless here)
	{
		patients.add(new Patient());
		String record=in.nextLine();
		patients.get(total_patients).set_record(record,++ID_to_assign);
		patients_in_camp++;
		total_patients++;
	}
	
	
	public ArrayList<Patient> eligible_patients(Health_care_centre centre)     // decides which patients can be admitted to a particular institute when it is created.
	{
		ArrayList<Patient> patient_list=new ArrayList<Patient>();
		System.out.println("\n");
		for(int i=0;i<this.total_patients;i++)
		{
			if(centre.get_vacant_beds()>0)
			{
				if(this.patients.get(i).get_oxygen_level()>=centre.get_oxygen_criteria() && this.patients.get(i).admit_or_not()==false)
				{
					System.out.print("Recovery days for admitted patient ID "+Integer.toString(this.patients.get(i).get_ID())+ "- ");
					int a=in.nextInt();
					this.patients.get(i).set_recovery_day(a);
					patient_list.add(this.patients.get(i));
					this.patients.get(i).set_admit(true);
					this.patients.get(i).set_institute(centre.get_name());
					this.patients_in_camp--;
					this.patients_in_centre++;
					centre.set_vacant_beds(centre.get_vacant_beds()-1);
				}
			}
		}
		if(centre.get_vacant_beds()>0)
		{
			for(int i=0;i<this.total_patients;i++)
			{
				if(centre.get_vacant_beds()>0)
				{
					if(this.patients.get(i).get_temp_level()<=centre.get_temp_criteria() && this.patients.get(i).admit_or_not()==false)
					{
						System.out.print("Recovery days for admitted patient ID "+Integer.toString(this.patients.get(i).get_ID())+ "- ");
						int a=in.nextInt();
						this.patients.get(i).set_recovery_day(a);
						patient_list.add(this.patients.get(i));
						this.patients.get(i).set_admit(true);
						this.patients.get(i).set_institute(centre.get_name());
						this.patients_in_camp--;
						this.patients_in_centre++;
						centre.set_vacant_beds(centre.get_vacant_beds()-1);
					}
				}
			}
		}
		 return patient_list;
	}
	
	
	public void remove_admitted_patients()                                          //removes the record of admitted patients from camp list.. Query 2
	{
		System.out.println("Account ID removed of admitted patients: ");
		int flag=0;
		for(int i=0;i<this.total_patients;i++)
		{
			if(this.patients.get(i).admit_or_not()==true)
			{
				System.out.println(this.patients.get(i).get_ID());
				flag=1;
				this.patients.remove(i);
				this.total_patients--;
				i--;
			}
		}
		if(flag==0)
		{
			System.out.println("-->> None...");
		}
	}
	
	public void displaying_patients(int id)                         // to help display the patient details... for query 7 and query 8
	{
		int flag=0;
		int flag2=0;
		for(int i=0;i<this.total_patients;i++)
		{
			if(id==0)
			{
				flag=1;
				flag2=1;
				this.patients.get(i).display_all_patients();
			}
				
			if(id==this.patients.get(i).get_ID())
			{
				flag=1;
				flag2=1;
				this.patients.get(i).display_patient();
			}
		}
		if(flag==0 && id!=0)
		{
			System.out.print("No such patient exist...");
		}
		if(flag2==0 && id==0)
		{
			System.out.println("No patients exist...");
		}
	}
	
	//getter
	public int get_patients_in_camp()
			{return this.patients_in_camp;}
	
}


class Health_care_centre						// blueprint for an individual health center
{
	private String name;										//center name
	private int total_beds;										//beds in center
	private int vacant_beds;									// vacant beds in center
	private float oxygen_criteria;								// centre's oxygen criteria
	private float body_temperature_criteria;					// centre's temperature criteria
	private ArrayList<Patient> patient_list;					// centre's own admitted patient list
	Scanner in=new Scanner(System.in);
	
	public int set_up_centre(All_patients patients, ArrayList<String> centre_names)   //to set-up center
	{
		try {																					//to check if correct input entered
				System.out.print("Enter the name of institute: ");
				this.name=in.next(); 
				if(centre_names.contains(this.name))											//to check if same center being created again so to stop the user....
				{
					return 2;	
				}
				System.out.print("Temperature criteria (Max Temperature): ");				
				this.body_temperature_criteria=in.nextFloat();
				System.out.print("Oxygen criteria (Minimum Oxygen levels): ");
				this.oxygen_criteria=in.nextFloat();
				System.out.print("Available Beds: ");
				this.total_beds=in.nextInt();
				this.vacant_beds=this.total_beds;
				this.display();
				patient_list=new ArrayList<Patient>(patients.eligible_patients(this));
				if(patient_list.size()==0)
				{
					System.out.println("No patient is eligible to get admit..");
				}
				return 0;
		}
		catch(Exception e)
		{
			System.out.println("\n Enter correct input...");
			return 1;
		}
	}
	
	
	//getter
	public int get_vacant_beds()
		{return this.vacant_beds;}
	
	public float get_oxygen_criteria()
		{return this.oxygen_criteria;}
	
	public float get_temp_criteria()
		{return this.body_temperature_criteria;}
	
	public String get_name()
		{return this.name;}
	
	
	//setter
	public void set_vacant_beds(int x)
		{this.vacant_beds=x;}
	
	
	
	public void display()                                // displaying as asked by query 1
	{
		System.out.println("\n");
		System.out.println(this.name);
		System.out.println("Temperature should be <= "+Float.toString(this.body_temperature_criteria));
		System.out.println("Oxygen level should be >= "+Float.toString(this.oxygen_criteria));
		System.out.println("Available beds: "+Integer.toString(this.vacant_beds));
		if(this.get_vacant_beds()>0)
		{
			System.out.println("Admission Status – OPEN");
		}
		else
			System.out.println("Admission Status – CLOSED");
	}
	
	
	void display_patients_admitted()                  //display for query 9
	{
		if(patient_list.size()==0)
		{
			System.out.println("No patient yet....");
		}
		else
		{
			for(int i=0;i<patient_list.size();i++)
			{
				System.out.print(this.patient_list.get(i).get_name()+", recovery time is ");
				System.out.println(this.patient_list.get(i).get_recovery_time());
			}	
		}
	}
}



class Health_institutes                                  // deals with all health_institutes together
{
	//attributes
	private ArrayList<Health_care_centre> centres;						//list of centers
	private int centres_admitting;										//number of centers admitting patients
	private int total_centres;											//number of centers in list
	
	
	//methods
	
	public Health_institutes()									//constructor
	{
		centres=new ArrayList<Health_care_centre>();
		centres_admitting=0;
		total_centres=0;
	}
	public void add_new_centre(All_patients patients)				//to add new center
	{
		ArrayList<String> centre_names=new ArrayList<String>();
		centres.add(new Health_care_centre());
		for(int i=0;i<total_centres;i++)
		{
			centre_names.add(centres.get(i).get_name());
		}
		int x=centres.get(total_centres).set_up_centre(patients,centre_names);
		if(x==0)																		// if center added is new and data entered is correct
		{
			if(centres.get(total_centres).get_vacant_beds()>0)
			{
				centres_admitting++;
			}
			total_centres++;
		}
		else if(x==2)																	// if duplicate center added
		{
			System.out.println("Centre name already exists...");
			centres.remove(total_centres);
		}
		else     																		// if wrong data entered
		{
			centres.remove(total_centres);
		}
	}
	
	public void admitted_patients_detail(String b)                                          //serving query 9
	{	int flag=0;
		for(int i=0;i<this.total_centres;i++)
		{
			if(this.centres.get(i).get_name().equalsIgnoreCase(b))
			{
				this.centres.get(i).display_patients_admitted();
				flag=1;
			}
		}
		if(flag==0)    //if no such institute present in camp list..
		{
			System.out.println("No Such institute exists...");
		}
	}
	
	
	public void remove_centres() 																	//query 3
	{
		System.out.println("Accounts removed of Institute whose admission is closed: ");
		int flag=0;
		for(int i=0;i<this.total_centres;i++)
		{
			if(this.centres.get(i).get_vacant_beds()==0)
			{
				System.out.println(this.centres.get(i).get_name());
				flag=1;
				this.centres.remove(i);
				this.total_centres--;
				i--;
			}
		}
		if(flag==0)			//if no center removed
		{
			System.out.println("-->>  None...");
		}
	}
	
	
	public void display_details(String a)                       //query 6.. details of centers
	{
		int flag=0;
		for(int i=0;i<this.total_centres;i++)
		{
			if(this.centres.get(i).get_name().equalsIgnoreCase(a))
			{
				flag=1;
				this.centres.get(i).display();
			}
		}
		if(flag==0)											// if no such center exists...
		{
			System.out.println("\nNo Such institute exists...");
		}
	}
	
	
	//getter
	public int get_centres_admitting()
		{	return this.centres_admitting;}
}



//////////////////////////////////////////////////////////////////////////////////
class covid_camp                                             //blueprint a covid camp
{
	
	//attribute
	private All_patients patients;							//for patients in the camp
	private Health_institutes health_centres;				// for all hospitals dealing with camp and in camp's records
	Scanner in=new Scanner(System.in);
	
	
	//methods
	public covid_camp()										//constructor
	{
		System.out.println("Welcome to Covid Camp...\n");
		System.out.print("Enter the number of patients: ");
		int n;
		n=in.nextInt();
		patients=new All_patients(n);
		health_centres=new Health_institutes();
	}
	public void begin()										//to begin the application....
	{
		String query="yes";
		while(query.equalsIgnoreCase("yes"))
		{
			System.out.print("\n---------------------------------\nEnter the query: ");
			try
			{
				int q=in.nextInt();
				System.out.print("\n");
				switch(q)
				{
				case 1:	health_centres.add_new_centre(patients);
						break;
				case 2:	patients.remove_admitted_patients();
						break;
				case 3:	health_centres.remove_centres();
						break;
				case 4:	System.out.print(patients.get_patients_in_camp());
						System.out.println(" patients..");
						break;
				case 5:	System.out.print(health_centres.get_centres_admitting());
						System.out.println(" institutes are admitting patients currently....");
						break;
				case 6: String a=in.next();
						health_centres.display_details(a);
						break;
				case 7:	patients.displaying_patients(in.nextInt());
						break;
				case 8: patients.displaying_patients(0);
						break;
				case 9: String b=in.next();
						health_centres.admitted_patients_detail(b);
						break;
				default:System.out.println("Wrong query raised.. Enter correct query number(1-9) ...");
				}
			}
			catch(Exception e)
			{
				System.out.println("Wrong query...");
			}
			in.nextLine();
			System.out.print("\n\nMore queries? (yes/no[any key])- ");
			query=in.nextLine();
		}
	}
}

public class assignment1 {
	public static void main(String[] args)
	{
		covid_camp application=new covid_camp();						//covid camp object
		application.begin();
		System.out.println("\nxxxxxx  Application closed xxxxxxx");
	}

}
