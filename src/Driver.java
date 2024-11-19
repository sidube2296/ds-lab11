import java.io.IOException;
import java.io.OutputStreamWriter;

import edu.uwm.cs351.Person;
import edu.colorado.collections.Table;


public class Driver {
	
	public static void main(String[] args) {
		
		Table gradebook = new Table(11);
		
		Person p1 = new Person("Christian","Yelich");
		Person p2 = new Person("Hernan","Perez");
		Person p3 = new Person("Lorenzo","Cain");
		Person p4 = new Person("Mike","Moustakas");
		Person p5 = new Person("Ryan","Braun");
		
		gradebook.put(p1, "B");
		gradebook.put(p2, "A-");
		gradebook.put(p3, "C-");
		gradebook.put(p4, "B+");
		gradebook.put(p5, "A+");
		
		try {
			gradebook.printContents(new OutputStreamWriter(System.out));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
