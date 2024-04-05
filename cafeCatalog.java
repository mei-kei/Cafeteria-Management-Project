/*
 * Course: Computer Science II, 201
 * Assignment: PMU Cafeteria Facilities for Faculty and Students 
 * Student Names: Furat Al Omran - 20221054
                  Manhah Iftikhar - 202100796
                  Maryam Alsaif - 202101179
                  Munira Altheeb - 202200822
 */
import java.util.Scanner;

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

    admin(){
        this.head = null;
    }

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
    }

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

    //method to delete food item from the menu
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
            return;
        }

        //iterating through the menu to find the food item to be deleted
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

    //method to check the number of food varieties
    public int checkFood() {
        Food temp = head;

        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    //method to generate reports based on the report type
    void generateReports(String reportType){
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
}

//class user extends admin as the user class utilises certain functions from admin class
class user extends admin{
    //method to inquire about the specific food including the information
    void inquireFood(Scanner input){
        System.out.println("Enter the title of the food to inquire about: ");
        String title = input.next();

        Food found = searchFood(title);
        if(found != null){
            System.out.println("Food found: ");
            System.out.println("Title: " + found.title);
            System.out.println("Country of Origin: " + found.countryOfOrigin);
            System.out.println("Info: " + found.info);
        } else {
            System.out.println("Food not found.");
        }
    }

    void requestFood(Scanner input){
        System.out.println(" Enter the title of food you want to request");
        String title =input.next();
        System.out.println(" Enter the food's Country of origin that you would like to request");
        String countryOfOrigin = input.next();
        System.out.println(" Enter the food's info you would like to request ");
        String info = input.next();
        System.out.println("Your request for Title: " + title + "\nCountry of Origin: " + countryOfOrigin + "\nInfo: " + info + " has been registered.");
    }

    void foodComplaint(Scanner input){
        System.out.println("Enter the title of the food to complain about: ");
        String title = input.next();
        System.out.println("Enter your complaint about the non-availability of '" + title + "': ");
        System.out.println("Your complaint has been registered.\n");
    }
}

public class cafeCatalog {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            admin adminMenu = new admin();
            user userMenu = new user();

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
                    while (true) {
                        System.out.println("Administrator Menu:");
                        System.out.println("a. Add new food");
                        System.out.println("b. Search food on the menu");
                        System.out.println("c. Delete information of the food");
                        System.out.println("d. Check the number of food varieties");
                        System.out.println("e. Generate the reports of the food based on:");
                        System.out.println("\t1. Food 'Title'");
                        System.out.println("\t2. Food 'Info'");
                        System.out.println("\t3. Food 'Country'");
                        System.out.println("f. Exit admin menu");
                        System.out.println();
                        
                        String adminChoice = input.next();
                        
                        switch (adminChoice) {
                            case "a":
                            System.out.println("Enter food title: ");
                            String title = input.next();
                            System.out.println("Enter country of origin: ");
                            String country = input.next();
                            System.out.println("Enter additional info: ");
                            String info = input.next();
                            adminMenu.addFood(title, country, info);
                            break;
        
                            case "b":
                            System.out.println("\nEnter food title to search: ");
                            String searchTitle = input.next();
                            adminMenu.searchFood(searchTitle);
                            break;
                            
                            case "c":
                            System.out.print("\nEnter title of the food to delete: ");
                            String deleteTitle = input.next();
                            adminMenu.deleteFood(deleteTitle);
                            break;
                            
                            case "d":
                            int foodCount = adminMenu.checkFood();
                            System.out.println("Number of food varieties: " + foodCount);
                            break;
                            
                            case "e":
                            System.out.println("Generate report based on:");
                            String report = input.next();
                            adminMenu.generateReports(report);
                            break;
                            
                            case "f":
                            System.out.println("Exiting Admin Menu...");
                            break;
                            
                            default:
                            System.out.println("Invalid choice.");
                            break;
                        }
                        
                        if (adminChoice.equals("f")) {
                            break;
                        }
                    }
                    break;
                    
                    //if user choice was 2. User
                    case 2:
                    while (true) {
                        System.out.println("User Menu:");
                        System.out.println("a. Inquire/Get food information");
                        System.out.println("b. Request for a new variety of food");
                        System.out.println("c. Raise a complaint about the non-availability of a food");
                        System.out.println("d. Exit User Menu");
                        System.out.println();
        
                        String user = input.next();
                        
                        switch (user) {
                            case "a":
                            userMenu.inquireFood(input);
                            break;
                            
                            case "b":
                            userMenu.requestFood(input);
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