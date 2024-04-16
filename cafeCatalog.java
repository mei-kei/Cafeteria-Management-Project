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
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
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
    int foodCount;

    String[] requests;
    int next;

    admin(){
        this.head = null;
        this.foodCount = 0;
        this.requests = new String[100];
        this.next = 0;
    }

    //method to save the added/deleted food by the admin into a text file
    void addToFile(String fileName) {
        try {
            File file = new File(fileName);
            if(!file.exists()){
                System.out.println("The file '" + fileName + "' doesn't exist.\n");
                System.out.println("Create new file? (Y/N)");

                Scanner input = new Scanner(System.in);
                String newFile = input.nextLine();

                if (newFile.equalsIgnoreCase("y") || newFile.equalsIgnoreCase("yes")){
                    file.createNewFile();
                    System.out.println("File '" + fileName + "' created successfully.\n");
                } else{
                    System.out.println("Failed to create file. Exiting...");
                    return;
                }
            }

            ///initialising added item
            int itemNumber = 1;

            FileWriter writer = new FileWriter(fileName);
            Food temp = head;

            while (temp != null) {
                writer.write("\nItem #" + itemNumber + "\n");
                writer.write("-------------------------\n");
                writer.write("Title: " + temp.title + "\n");
                writer.write("Country of Origin: " + temp.countryOfOrigin + "\n");
                writer.write("Info: " + temp.info + "\n");
                temp = temp.next;

                //incrementing after the addition of more food items
                itemNumber++;
            }

            writer.close();
        } catch(IOException e){
            System.out.println("An error has occured while writing to the file.");
            System.out.println("Error: " + e);
        }
    }

        //the following is a help method to save the data added/deleted by the admin in a text file
        private int itemInFile(String fileName) {
            int count = 0;
    
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line = reader.readLine();

                while (line != null) {
                    if (line.startsWith("Item #")) {
                        count++;
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred while counting items in file.");
                System.out.println("\nError: " + e);
            }
            return count;
        }

    //method to add new food and its information on the menu (munira)
    void addFood(String fileName) {
        try {
            File file = new File(fileName);
            boolean exist = file.exists();
    
            FileWriter writer = new FileWriter(fileName, true);
    
            if (!exist) {
                System.out.println("The entered file doesn't exist.\n");
                System.out.println("Would you like to create a new one? (Y/N)");
    
                Scanner input = new Scanner(System.in);
                String newFile = input.nextLine();
    
                if (newFile.equalsIgnoreCase("y") || newFile.equalsIgnoreCase("yes")) {
                    file.createNewFile();
                    System.out.println("File '" + fileName + "' has been created successfully.");
                } else {
                    System.out.println("There was an error creating the file.");
                    return;
                }
            }
    
            /*adding a conditional operator to calculate the itemNumber based on whether the file exists or not. if it does, counts the existing item(s) and increments by 1*/
            int itemNumber = exist ? itemInFile(fileName) + 1 : 1;
    
            Food temp = head;
    
            while (temp != null) {
                writer.write("\nItem #" + itemNumber + "\n");
                writer.write("-------------------------\n");
                writer.write("Title: " + temp.title + "\n");
                writer.write("Country of Origin: " + temp.countryOfOrigin + "\n");
                writer.write("Info: " + temp.info + "\n");
                temp = temp.next;
                itemNumber++;
            }
    
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred while adding item to file.");
            System.out.println("\nError: " + e);
        }
    }
    
    //method to inquire about the specific food including the information (manhah)
    void inquireFood(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Scanner input = new Scanner(System.in);
            String line = reader.readLine();

            boolean found = false;

            System.out.print("Enter the title of the food to inquire about: ");
            String searchTitle = input.nextLine();

            while (line != null) {
                if (line.startsWith("Title:")) {
                    String title = line.substring("Title: ".length());
                    if (title.equalsIgnoreCase(searchTitle)) {

                        //once the food is found, print its info
                        found = true;

                        System.out.println("Food found:");
                        System.out.println("Title: " + title);

                        //reading the rest of the lines i.e. country of origin and info and prints them
                        for (int i = 0; i < 2; i++) {
                            line = reader.readLine();
                            System.out.println(line);
                        }
                        break;
                    }
                }
            }
            if (!found) {
            System.out.println("Food not found.");
        }
    } catch (IOException e) {
        System.out.println("An error has occurred while reading the menu file.");
        System.out.println("Error: " + e);
    }
}

    //method to request for a new food item (maryam)
    void requestFood(Scanner input, String fileName){
        try(FileWriter writer = new FileWriter(fileName, true)){
        System.out.println(" Enter the title of food you want to request");
        String title =input.next();
        System.out.println(" Enter the food's country of origin: ");
        String country = input.next();
        System.out.println(" Enter the food's info : ");
        String info = input.nextLine();
        
        writer.write("\nRequested Food\n");
        writer.write("------------------");
        writer.write("Title: " + title + "\n");
        writer.write("Country of Origin: " + country + "\n");
        writer.write("Info: " + info + "\n");

        System.out.println("Your request for Title: " + title + "\nCountry of Origin: " + country + "\nInfo: " + info + " has been registered.");        
        } catch(IOException e){
            System.out.println("An error occurred while adding your request to the file.");
            System.out.println("Error: " + e);
        }
    }

    //method to update the requests or new requirements from the faculty or the students
    void updateRequests(String fileName, Scanner input){
        try{
            File file = new File(fileName);

            if(!file.exists()){
                System.out.println("This file doesn't exist.");
                return;
            }

            System.out.println("Enter the title of the food item: ");
            String foodItem = input.nextLine();
            System.out.println("Enter the new request or requirement: ");
            String newRequest = input.nextLine();

            File tempFile = new File("temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line = reader.readLine();
            boolean foodInLine = false;
            boolean updated = false;

            while(line != null){
                if(line.startsWith("Title: ") && line.substring(line.indexOf(":") + 2).equals(foodItem)){
                    foodInLine = true;
                } else if(line.startsWith("Request: ") && foodInLine){
                    writer.write(line + "\n");
                    writer.write(newRequest + "\n");
                    updated = true;
                } else if(line.isEmpty() && foodInLine){
                    foodInLine = true;
                }

                writer.write(line + "\n");
            }

            reader.close();
            writer.close();

            if(!updated){
                System.out.println("Food item: " + foodItem + ", not found.");
                tempFile.delete();
            }
            System.out.println("Requests updated successfully for '" + foodItem + "'");
        } catch(IOException e){
            System.out.println("An error occurred while updating your requests.");
            System.out.println("Error: " + e);
        }
    }

    //method to file a complaint if any (manhah)
    void foodComplaint(Scanner input, String title, String fileName){
        System.out.println("Enter your complaint about the non-availability of '" + title + "': ");
        String complaint = input.nextLine();

        try(FileWriter writer = new FileWriter(fileName, true)){
            writer.write("/nComplaint for: " + title + "\n");
            writer.write("Complaint: " + complaint + "\n");
            writer.write("------------------------------");

            System.out.println("Your complaint has been registered.");
        } catch(IOException e){
            System.out.println("An error occurred while submitting your complaint.");
            System.out.println("Error: " + e);
        }
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
    void deleteFood(String title, String fileName) {
        //using boolean to indicate whether the food item was found and deleted or not
        boolean deleted = false;
    
        //checking if the menu is empty or not
        if (head == null) {
            System.out.println("There are currently no food items in the menu.\n");
            return;
        }
    
        Food current = head;
        Food previous = null;
    
        //iterating through the food items in the menu to delete the entered item
        while (current != null) {
            if (current.title.equalsIgnoreCase(title)) {
                //deleting the food item
                if (previous == null) {
                    //moving the next item at the top of the list if the deleted item was the head
                    head = head.next;
                } else {
                    previous.next = current.next;
                }
                foodCount--;
                deleted = true;
                break;
            }
    
            //moving on to the next item on the menu
            previous = current;
            current = current.next;
        }
    
        if(deleted) {
            System.out.println("Food item was successfully deleted from the menu.\n");
            updateMenuFile(fileName);
        } else {
            System.out.println("Food not found.\n");
        }
    }

    //method to update the text file after the deletion of a food item
    void updateMenuFile(String fileName) {
        addToFile(fileName);
    }

    //method to check the number of food varieties (furat)
    public int checkFood(String fileName) {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line = reader.readLine();
            int count = 0;
            
            System.out.println("Displaying food varieties...");
            while(line != null){
                if(line.startsWith("Title: ")){

                    // +2 so that the extracted substring starts from the actual value after the colon
                    String title = line.substring(line.indexOf(":") + 2);
                    String countryOfOrigin = reader.readLine().substring(line.indexOf(":") + 2);
                    String info = reader.readLine().substring(line.indexOf(":") + 2);

                    System.out.println("Title: " + title);
                    System.out.println("Country of Origin: " + countryOfOrigin);
                    System.out.println("Info: " + info + "\n");

                    count++;
                }
            }
            return count;

        }catch(IOException e){
            System.out.println("An error occurred while checking the food item on the menu.");
            System.out.println("Error: " + e);
            return 0;
        }
    }

    //method to generate reports based on the report type (manhah)
    void generateReport(String reportType, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            //to process each line in the file 
            String line = reader.readLine();
            boolean foodLine = false;
    
            while (line != null) {
                if (line.startsWith("Title:") && reportType.equals("title")) {
                    System.out.println(line.substring(line.indexOf(":") + 2));
                    foodLine = true;
                } else if (line.startsWith("Country of Origin:") && reportType.equals("country")) {
                    System.out.println(line.substring(line.indexOf(":") + 2));
                } else if (line.startsWith("Info:") && reportType.equals("info")) {
                    System.out.println(line.substring(line.indexOf(":") + 2));
                }else if (line.isEmpty() && foodLine) {
                    foodLine = false; 
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while generating the report.");
            System.out.println("Error: " + e);
        }
    }
    
    void foodInfo(Food food, String reportType){
        switch(reportType){
            case "title":
            System.out.println("Title: " + food.title);
            break;

            case "country":
            System.out.println("Country of Origin: " + food.countryOfOrigin);
            break;

            case "info":
            System.out.println("Info: " + food.info);
            break;

            default:
            System.out.println("Invalid report type.");
        }
    }
}


//class user extends admin as the user class utilises certain functions from admin class (manhah)
class user extends admin{
    //implementing the same method to inquire about the specific food for the user
    @Override
    void inquireFood(String fileName){
        super.inquireFood(fileName);
    }

    //implementing the same method to request for a new food item for the user
    @Override
    void requestFood(Scanner input, String fileName){
        super.requestFood(input, fileName);
    }

    //implementing the same method to file a complaint if any for the user
    void foodComplaint(Scanner input){
        System.out.println("Enter your complaint about the non-availability of the food: ");
        String complaint = input.nextLine();

        System.out.println("Your complaint has been registered successfully.");
    }
}

//a main method to run the catalog menu for both the admin and the user (manhah)
public class cafeCatalog {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            admin adminMenu = new admin();
            user studentMenu = new user();

            System.out.println("Enter the file name to create or use for storing the menu: ");
            String filename = input.nextLine();

            adminMenu.addToFile(filename);

            while (true) {
                System.out.println("Please select the user: ");
                System.out.println("1. Administrator");
                System.out.println("2. Student");
                System.out.println("3. Exit");
                
                int choice = input.nextInt();
                
                //nested switch cases considering two types of users: admin and students
                switch (choice) {
                    //if user choice was 1. Admin
                    case 1:
                        System.out.println("Administrator Menu:");
                        System.out.println("a. Add new food");
                        System.out.println("b. Inquire regarding a food item");
                        System.out.println("c. Request for a new food item");
                        System.out.println("d. Update requests or requirements from faculty or students");
                        System.out.println("e. Raise a complaint about the non-availability of a food");
                        System.out.println("f. Delete information of the food");
                        System.out.println("g. Search food on the menu");
                        System.out.println("h. Check the number of food varieties");
                        System.out.println("i. Generate the reports of the food based on:");
                        System.out.println("\t1. Food 'Title'");
                        System.out.println("\t2. Food 'Info'");
                        System.out.println("\t3. Food 'Country'");
                        System.out.println("j. Exit admin menu");
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
                            adminMenu.addFood(filename);                            
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
                            adminMenu.deleteFood(deleteTitle, filename);
                            break;

                            //calling the inquireFood method
                            case "t":
                            adminMenu.inquireFood(filename);
                            break;

                            //calling the requestFood method
                            case "r":
                            adminMenu.requestFood(input, filename);
                            break;

                            //calling the foodComplaint method
                            case "h":
                            System.out.println("Enter the title of the food to complain about: ");
                            String titleToComplain = input.next();
                            adminMenu.foodComplaint(input, titleToComplain, filename);
                            break;

                            //calling the updateRequests method
                            case "d":
                            adminMenu.updateRequests(filename, input);
                            break;
                            
                            //calling the checkFood method (furat)
                            case "e":
                            int foodCount = adminMenu.checkFood(filename);
                            System.out.println("Number of food varieties: " + foodCount + "\n");                            
                            break;
                            
                            //calling the generateReport method
                            case "f":
                            System.out.println("Generate report based on:");
                            String report = input.next();
                            adminMenu.generateReport(report, admin);
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
                    
                    //if user choice is 2. Student
                    case 2:
                        System.out.println("Student Menu:");
                        System.out.println("a. Inquire regarding a food item");
                        System.out.println("b. Request for a new variety of food");
                        System.out.println("c. Raise a complaint about the non-availability of a food");
                        System.out.println("d. Exit Student Menu");
                        System.out.println();
        
                        String student = input.next();
                        
                        switch (student) {
                            //calling the inquireFood method (munira)
                            case "a":
                            studentMenu.inquireFood(filename);
                            break;
                            
                            //calling the request method (maryam)
                            case "b":
                            System.out.println("Enter the file name to store the request: ");
                            String fileName = input.nextLine();
                            studentMenu.requestFood(input, fileName);
                            break;
        
                            //calling the complain method (manhah)
                            case "c":
                            studentMenu.foodComplaint(input);
                            break;
        
                            case "d":
                            System.out.println("Exiting Student Menu...");
                            break;
        
                            default:
                            System.out.println("Invalid choice.");
                            break;
                        }
                        
                        if (student.equals("d")) {
                            break;
                        }
                    break;

                    //if user choice is 3. Exit
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