/*
 * Course: Computer Science II, 201
 * Assignment: PMU Cafeteria Facilities for Faculty and Students 
 * Student Names: Furat Al Omran - 20221054
                  Manhah Iftikhar - 202100796
                  Maryam Alsaif - 202101179
                  Munira Altheeb - 202200822
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Food{
    String title;
    String countryOfOrigin;
    String info;
    Food next;

    Food(String title, String countryOfOrigin, String info){
        this.title = title;
        this.countryOfOrigin = countryOfOrigin;
        this.info = info;
        this.next = null;
    }
}

class admin{
    Food head;

    String[] requests;
    int next;

    admin(){
        this.head = null;
        this.requests = new String[100];
        this.next = 0;
    }

    //method to add new food and its information on the menu (munira)
    void addFood(String title, String countryOfOrigin, String info){
        Food newFood = new Food(title, countryOfOrigin, info);
        if(head == null){
            head = newFood;
        } else {
            Food temp = head;
            while(temp.next != null){
                temp = temp.next;
        }
        temp.next = newFood;
        }
        System.out.println("\nFood added successfully.\n");
        addToFile("menu.txt");
    }

    //method to inquire about the specific food including the information (munira)
    void inquireFood(){
        
    }
    //method to request for a new food item (maryam)
    void requestFood(Scanner input){
        System.out.println(" Enter the title of food you want to request");
        String title =input.next();
        System.out.println(" Enter the food's country of origin: ");
        String country = input.next();
        System.out.println(" Enter the food's info : ");
        String info = input.nextLine();
        System.out.println("Your request for Title: " + title + "\nCountry of Origin: " + country + "\nInfo: " + info + " has been registered.");
    }

    //method to update the requests or new requirements from the faculty or the students
    void updateRequests(Scanner input){
        
    }

    //method to file a complaint if any (manhah)
    void foodComplaint(Scanner input){
        System.out.println("Enter the title of the food to complain about: ");
        String title = input.next();
        System.out.println("Enter your complaint about the non-availability of '" + title + "': ");
        System.out.println("Your complaint has been registered.\n");
    }

    //method to search for a food item on the menu (maryam)
    Food searchFood(String title){
        Food current = head;
        while (current != null) {
            if (current.title.equals(title)){
                System.out.println("Title: " + current.title);
                System.out.println("Country of Origin: " + current.countryOfOrigin);
                System.out.println("Info: " + current.info + "\n");
                return current;
            }
            current = current.next;
        }
        System.out.println(title + " not found.\n");
        return null;
    }

    //method to delete food item from the menu (manhah)
    void deleteFood(String title){
        //to check if the menu is empty
        if(head == null){
            System.out.println("This list is empty\n");
            return;
        }

        //to check if the food item is in the head of the menu or not
        if(head.title.equals(title)){
            head = head.next;
            System.out.println("\n" + title + " was successfully deleted from the menu.\n");
            addToFile("menu.txt");
            return;
        }

        //iterating through the menu to find the next food item to place as the 'head'
        Food current = head;
        Food previous = null;

        while(current != null && !current.title.equals(title)){
            previous = current; //storing the reference variable to the previous item on the menu
            current = current.next; //moving to the next item on the menu
        }

        //displaying a message to indicate if the food was not found
        if(current == null){
            System.out.println("Food not found");
            return;
        }
        //making the next item on the menu as the top item after deletion of the previous one
        previous.next = current.next;
    }

    //method to check the number of food varieties (furat)
    public int checkFood() {
        Food temp = head;

        int count = 0;
        while (temp != null) {
            System.out.println("Displaying food varieties...");
            System.out.println("Title: " + temp.title);
            System.out.println("Country of Origin: " + temp.countryOfOrigin);
            System.out.println("Info: " + temp.info + "\n");

            count++;
            temp = temp.next;
        }
        return count;
    }

    //method to generate reports based on the report type (manhah)
    void generateReport(String reportType){
        //creating a temporary pointer to the head
        Food temp = head;

        //iterating through the menu until the final item
        while(temp != null){
            
            //switch case to handle reports based on the given type
            switch(reportType){
                case "title":
                System.out.println("Title: " + temp.title + "\n");
                break;

                case "country":
                System.out.println("Title: " + temp.title);
                System.out.println("Country of origin: " + temp.countryOfOrigin + "\n");
                break;

                case "info":
                System.out.println("Title: " + temp.title);
                System.out.println("Info: " + temp.info + "\n");
                break;

                default:
                System.out.println("Invalid report type\n");
                return;
            }

            //moving to the next item on the menu
            temp = temp.next;
        }
    }

    void addToFile(String fileName){
        try{
            File file = new File(fileName);
            if(!file.exists()){
                System.out.println("The file '" + fileName + "' doesn't exist.\n");
                System.out.println("Create new file? (Y/N)");

                Scanner input = new Scanner(System.in);
                String newFile = input.nextLine();

                if (newFile.equalsIgnoreCase("y") || newFile.equalsIgnoreCase("yes")){
                    file.createNewFile();
                    System.out.println("File '" + fileName + "' created successfully.");
                } else{
                    System.out.println("Failed to create file. Exiting...");
                    return;
                }
            }

            FileWriter writer = new FileWriter(fileName);
            Food temp = head;

            while(temp != null){
                writer.write("Title: " + temp.title + "\n");
                writer.write("Country of Origin: " + temp.countryOfOrigin + "\n");
                writer.write("Info: " + temp.info + "\n");
                temp = temp.next;
            }

            writer.close();
        } catch(IOException e){
            System.out.println("An error has occured while writing to the file.");
            System.out.println("Error: " + e);
        }
    }
}

//class user extends admin as the user class utilises certain functions from admin class (manhah)
class user extends admin{
    //implementing the same method to inquire about the specific food for the user
    void inquireFood(){
        super.inquireFood();
    }

    //implementing the same method to request for a new food item for the user
    void requestFood(Scanner input){
        super.requestFood(input);
    }

    //implementing the same method to file a complaint if any for the user
    void foodComplaint(Scanner input){
        super.foodComplaint(input);
    }
}

//a main method to run the catalog menu for both the admin and the user (manhah)
public class cafeCatalog {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            admin adminMenu = new admin();
            user userMenu = new user();

            System.out.println("Enter the file name to create or use for storing the menu: ");
            String filename = input.nextLine();

            adminMenu.addToFile(filename);

            while (true) {
                System.out.println("Please select the user: ");
                System.out.println("1. Administrator");
                System.out.println("2. User");
                System.out.println("3. Exit");
                
                int choice = input.nextInt();
                
                //nested switch cases considering two types of users: admin and students
                switch (choice) {
                    //if user choice was 1. Admin
                    case 1:
                        System.out.println("Administrator Menu:");
                        System.out.println("a. Add new food");
                        System.out.println("b. Search food on the menu");
                        System.out.println("c. Delete information of the food");
                        System.out.println("d. Update requests or requirements from faculty or students");
                        System.out.println("e. Check the number of food varieties");
                        System.out.println("f. Generate the reports of the food based on:");
                        System.out.println("\t1. Food 'Title'");
                        System.out.println("\t2. Food 'Info'");
                        System.out.println("\t3. Food 'Country'");
                        System.out.println("g. Exit admin menu");
                        System.out.println();
                        
                        String admin = input.next();
                        
                        switch (admin) {
                            //calling the addFood method (munira)
                            case "a":
                            System.out.println("Enter food title: ");
                            String title = input.next();
                            System.out.println("Enter country of origin: ");
                            String country = input.next();
                            System.out.println("Enter additional info: ");
                            String info = input.next();
                            adminMenu.addFood(title, country, info);                            
                            break;
        
                            //calling the searchFood method (maryam)
                            case "b":
                            System.out.println("\nEnter food title to search: ");
                            String searchTitle = input.next();
                            adminMenu.searchFood(searchTitle);                            
                            break;
                            
                            //calling the deleteFood method
                            case "c":
                            System.out.print("\nEnter title of the food to delete: ");
                            String deleteTitle = input.next();
                            adminMenu.deleteFood(deleteTitle);
                            break;

                            //calling the updateRequests method
                            case "d":
                            adminMenu.updateRequests(input);
                            break;
                            
                            //calling the checkFood method (furat)
                            case "e":
                            int foodCount = adminMenu.checkFood();
                            System.out.println("Number of food varieties: " + foodCount + "\n");                            
                            break;
                            
                            //calling the generateReport method
                            case "f":
                            System.out.println("Generate report based on:");
                            String report = input.next();
                            adminMenu.generateReport(report);
                            break;
                            
                            case "g":
                            System.out.println("Exiting Admin Menu...");
                            break;
                            
                            default:
                            System.out.println("Invalid choice.");
                            break;
                        }
                        
                        if (admin.equals("f")) {
                            break;
                        }
                    break;
                    
                    //if user choice was 2. User
                    case 2:
                        System.out.println("User Menu:");
                        System.out.println("a. Inquire/Get food information");
                        System.out.println("b. Request for a new variety of food");
                        System.out.println("c. Raise a complaint about the non-availability of a food");
                        System.out.println("d. Exit User Menu");
                        System.out.println();
        
                        String user = input.next();
                        
                        switch (user) {
                            //calling the inquireFood method (munira)
                            case "a":
                            
                            break;
                            
                            //calling the request method (maryam)
                            case "b":

                            break;
        
                            case "c":
                            userMenu.foodComplaint(input);
                            break;
        
                            case "d":
                            System.out.println("Exiting User Menu...");
                            break;
        
                            default:
                            System.out.println("Invalid choice.");
                            break;
                        }
                        
                        if (user.equals("d")) {
                            break;
                        }
                    break;

                    //if user choice was 3. Exit
                    case 3:
                    System.out.println("Exiting menu...");
                    System.exit(0);
                    break;
                    
                    default:
                    System.out.println("Invalid choice.");
                    break;
                }
            }
        }
    }
}