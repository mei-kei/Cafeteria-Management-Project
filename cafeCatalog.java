/*
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

    //method to delete food item from the menu
    void deleteFood(String title){
        //to check if the menu is empty
        if(head == null){
            System.out.println("This list is empty");
            return;
        }

        //to check if the food item is in the head of the menu or not
        if(head.title.equals(title)){
            head = head.next;
            return;
        }

        //iterating through the menu to find the food item to be deleted
        Food current = head;
        Food previous = null;

        while(current != null && !current.title.equals(title)){
            previous = current; //storing the reference variable to the previous item on the menu
            current = current.next; //moving to the next item on the menu
            System.out.println("\n" + title + " was successfully removed from the menu.\n");
        }

        //displaying a message to indicate if the food was not found
        if(current == null){
            System.out.println("Food not found");
            return;
        }
        //commit
        //making the next item on the menu as the top item after deletion of the previous one
        previous.next = current.next;
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
                System.out.println("Title: " + temp.title);
                break;

                case "country":
                System.out.println("Title: " + temp.title);
                System.out.println("Country of origin: " + temp.countryOfOrigin);
                break;

                case "info":
                System.out.println("Title: " + temp.title);
                System.out.println("Info: " + temp.info);
                break;

                default:
                System.out.println("Invalid report type");
                return;
            }

            //moving to the next item on the menu
            temp = temp.next;
        }
    }
}

public class cafeCatalog {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            admin adminMenu = new admin();
            //user userMenu = new user();

            while(true){
                System.out.println("Please select the user: ");
                System.out.println("1. Administrator");
                System.out.println("2. User");
                System.out.println("3. Exit");

                int choice = input.nextInt();

                //nested switch cases considering two types of users: admin and students
                switch(choice){

                    //if user choice was 1. Admin
                    case 1:
                    System.out.println("Administrator Menu:");
                    System.out.println("a. Delete information of the food");
                    System.out.println("b. Search food on the menu");
                    //System.out.println("c. Check the number of food varieties");
                    System.out.println("d. Generate the reports of the food based on:");
                    System.out.println("\t1. Food 'title'");
                    System.out.println("\t2. Food 'info'");
                    System.out.println("\t3. Food 'country'");
                    System.out.println();

                    String admin = input.next();

                    switch(admin){

                        case "a":
                        System.out.print("\nEnter title of the food to delete: ");
                        String deleteTitle = input.next();
                        adminMenu.deleteFood(deleteTitle);
                        break;

                        case "b":
                        System.out.println("\nEnter food title to search: ");
                        String searchTitle = input.next();
                        //foodList.searchFood(searchTitle);
                        break;

                        case "c":
                        System.out.println("Generate report based on:");
                        String report = input.next();
                        adminMenu.generateReports(report);
                        break;
                    }

                    //if user choice was 2. User
                    case 2:
                    System.out.println("User Menu:");
                    System.out.println("a. Search food on the menu");
                    System.out.println("b. Check the number of food varieties");
                    System.out.println("c. Get food information");
                    System.out.println("d. Complain");
                
                    String user = input.next();
                
                    switch(user) {
                        case "a":
                            System.out.println("\nEnter the title of the food to search: ");
                            String title = input.next();
                            //user.searchFood(title, foodList);
                            break;
                                    
                        case "b":
                            System.out.println("\nEnter food title: ");
                            String foodTitle = input.next();
                            System.out.println("Enter country of origin: ");
                            String country = input.next();
                            System.out.println("Enter additional info: ");
                            String info = input.next();
                            //user.searchFood(title, country, info);
                            break;
                
                        case "c":
                            System.out.println("Enter food title to complain about: ");
                            String complaint = input.next();
                            //userMenu.complain(complaint);
                            break;
                
                        case "d":
                            break;
                                        
                        default:
                            System.out.println("Invalid choice.");
                            break;
                    }
                    if(user.equals("d")) {
                        break;
                    }
                    break; // Move the break statement here
                
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
