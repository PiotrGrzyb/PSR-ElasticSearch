package pl.kielce.tu.neo4j.straz;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import pl.kielce.tu.neo4j.ogm.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestNeo4J {

	public static void main(String[] args) throws IOException {
		
		Configuration configuration = new Configuration.Builder().uri("bolt://localhost:7687").credentials("neo4j", "neo4jpassword").build();
	    SessionFactory sessionFactory = new SessionFactory(configuration, "pl.kielce.tu.neo4j.straz");

		Session session = sessionFactory.openSession();
		
		session.purgeDatabase();
			
		FiretruckService firetruckService = new FiretruckService(session);
		
		Fireman fireman1 = new Fireman("Adam Nowak");
		Firetruck firetruck1 = new Firetruck("Firetruck01");
		firetruck1.addFireman(fireman1);
		firetruckService.createOrUpdate(firetruck1);

		Fireman fireman2 = new Fireman("Maciej Kowalski");
		Fireman fireman3 = new Fireman("Jan Nowy");
		Firetruck firetruck2 = new Firetruck("Firetruck02");
		firetruck2.addFireman(fireman2);
		firetruck2.addFireman(fireman3);
		firetruckService.createOrUpdate(firetruck2);

		Action action1 = new Action("Sienkiewicza 10");
		action1.addFiretruck(firetruck2);
		ActionService actionService = new ActionService(session);
		actionService.createOrUpdate(action1);

		FiremanService firemanService = new FiremanService(session);


		int choice = 0;
		Scanner myInput = new Scanner( System.in );
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		do{
			System.out.println("[1] Add new firetruck");
			System.out.println("[2] Get all fireman");
			System.out.println("[3] Get all firetrucks");
			System.out.println("[4] Add new action");
			System.out.println("[5] Get all actions");
			System.out.println("[6] Delete action by ID");
			System.out.println("[7] View action by location");
			System.out.println("[9] Quit");
			choice = myInput.nextInt();
			if (choice == 1){
				System.out.println("Fireman name:");
				String name =  reader.readLine();
				System.out.println("Firetruck name:");
				String truck =  reader.readLine();
				Fireman fireman = new Fireman(name);
				Firetruck firetruck = new Firetruck(truck);
				firetruck.addFireman(fireman);
				firetruckService.createOrUpdate(firetruck);
			}
			if (choice == 2){
				System.out.println("All fireman:");
				for(Fireman b : firemanService.readAll())
					System.out.println(b);
			}
			if (choice == 3){
				System.out.println("All firetrucks:");
				for(Firetruck b : firetruckService.readAll())
					System.out.println(b);
			}
			if (choice == 4){
				System.out.println("Action location:");
				String location =  reader.readLine();

				System.out.println("All firetrucks:");
				for(Firetruck b : firetruckService.readAll())
					System.out.println(b);


				System.out.println("Firetruck ID :");
				int id = myInput.nextInt();
				Firetruck firetruckByID = firetruckService.read((long) id);

				Action action = new Action(location);
				action.addFiretruck(firetruckByID);
				actionService.createOrUpdate(action);
			}
			if (choice == 5){
				System.out.println("All actions:");
				for(Action b : actionService.readAll())
					System.out.println(b);

			}
			if (choice == 6){
				System.out.println("All actions:");
				for(Action b : actionService.readAll())
					System.out.println(b);

				System.out.println("Action ID to delete:");
				int id = myInput.nextInt();

				for(Action b : actionService.readAll()) {
					if(b.getId().equals((long)id)) {
						actionService.delete((long)id);
						break;
					}
				}
			}
			if (choice == 7){
				System.out.println("Action location to search by:");
				String truck =  reader.readLine();

				for(Action b : actionService.readAll()) {
					if(b.getName().equals(truck)) {
						System.out.println(b);
						break;
					}
				}

			}
			if(choice == 8){

			}
		}while(choice != 9);
		sessionFactory.close();
	}
}
