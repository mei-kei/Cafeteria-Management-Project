/*
 * Course: Computer Science II
 * Section: 201
 * Assignment: PMU Cafeteria Facilities for Faculty and Students 
 * Student Names & IDs: Furat Al Omran - 20221054
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
        //limit of upto a 100 requests
        this.requests = new String[100];
        this.next = 0;
    }

    //method to save the added/deleted food by the admin into a text file
    void writeToFile(String fileName) {
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

            FileWriter writer = new FileWriter(fileName);
            ///initialising added item
            int itemNumber = 1;           
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

    //method to add new food and its information on the menu
    void addFood(String title, String country, String info, String fileName) {
        //implementing a counter
        foodCount++;

        //creating an object for the new food item
        Food newFood = new Food(title, country, info);

        //adding the food details to the file using a help method
        addItemToFile(title, country, info, fileName);
        System.out.println("\nFood item has been added successfully to the file menu.");
    }

    //help method for addFood() to write the new food item into the text file
    void addItemToFile(String title, String country, String info, String fileName){
        //creating an object and writing new food item(s) into the file without overriding
        try(FileWriter writer = new FileWriter(fileName, true)){
            writer.write("\nItem #" + foodCount + "\n");
            writer.write("-------------------------\n");
            writer.write("Title: " + title + "\n");
            writer.write("Country of Origin: " + country + "\n");
            writer.write("Info: " + info + "\n");
        } catch(IOException e){
            System.out.println("An error occurred while adding the food item to the file menu.");
            System.out.println("Error: " + e);
        }
    }
    
    //method to inquire about the specific food including the information
    void inquireFood(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Scanner input = new Scanner(System.in);

            //to process each line in the file
            String line;
            boolean found = false;

            System.out.print("Enter the title of the food to inquire about: ");
            String searchTitle = input.nextLine();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Title:")) {
                    String title = line.substring("Title: ".length());
                    if (title.equalsIgnoreCase(searchTitle)) {

                        //once the food is found, print its info
                        found = true;

                        System.out.println("\nFood found");
                        System.out.println("-------------");
                        System.out.println("Title: " + title);

                        //reading the rest of the lines i.e. country of origin and info and prints them
                        for (int i = 0; i < 2; i++) {
                            line = reader.readLine();
                            System.out.println(line);
                        }
                        System.out.println("-------------");
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


     //method to request for a new food item
     void requestFood(Scanner input, String fileName){
        try(FileWriter writer = new FileWriter(fileName)){
            System.out.println("Enter the title of the food you want to request: ");
            String title =input.next();
            System.out.println("Enter the country of origin: ");
            String country = input.next();
            input.nextLine(); // Consume newline
            System.out.println("Enter additional info: ");
            String info = input.nextLine();
            
            writer.write("\nRequested Food\n");
            writer.write("------------------\n");
            writer.write("Title: " + title + "\n");
            writer.write("Country of Origin: " + country + "\n");
            writer.write("Info: " + info + "\n");

            System.out.println("Your request for... " + "\n-------------"
                    + "\nTitle: " + title + 
                    "\nCountry of Origin: " + country + "\nInfo: " + info + 
                    "\n-------------" + " \nhas been registered.");      
            System.out.println();
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

            input.nextLine();
            System.out.println("Enter the title of the food item: ");
            String foodItem = input.nextLine();
            input.nextLine();
            System.out.println("Enter the new request or requirement: ");
            String newRequest = input.nextLine();

            File tempFile = new File("temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            //creating a temporary file to store the modified contents of the original file while updating the requests
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            //to process each line in the file
            String line = reader.readLine();
            //to track whether the current line being processed contains the info of the food item in the file or not
            boolean foodInLine = false;
            //to indicate whether the requests of for the food item has been updated or not
            boolean updated = false;

            while(line != null){
                if(line.startsWith("Title: ") && line.substring(line.indexOf(":") + 2).equals(foodItem)){
                    foodInLine = true;
                } else if(line.startsWith("Request: ") && foodInLine){
                    writer.write(line + "\n");
                    writer.write(newRequest + "\n");
                    updated = true;
                } else if(line.isEmpty() && foodInLine){
                    foodInLine = false;
                }

                writer.write(line + "\n");
                line = reader.readLine(); // Read the next line
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
        }//method to file a complaint if any
    }
    
    void foodComplaint(Scanner input, String title, String fileName){
        input.nextLine();
        System.out.println("Enter your complaint about the non-availability of '" + title + "': ");
        String complaint = input.nextLine();

        try(FileWriter writer = new FileWriter(fileName, true)){
            writer.write("\n----------------------------");
            writer.write("\nTitle: " + title + "\n");
            writer.write("Complaint: " + complaint + "\n");
            writer.write("------------------------------\n");
            
            System.out.println("Your complaint has been registered.");
        
        } catch(IOException e){
            System.out.println("An error occurred while submitting your complaint.");
            System.out.println("Error: " + e);
        }
    }

    //method to search for a food item on the menu
    void searchFood(String fileName, String title) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            //to process each line in the file
            String line;
            boolean found = false;

            System.out.println("Searching for food item: " + title + "...");

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Title: ")) {
                    String itemTitle = line.substring(line.indexOf(":") + 2);
                    if (itemTitle.equalsIgnoreCase(title)) {
                        found = true;
                        System.out.println("\nFood found:");
                        System.out.println("-------------\n");
                        System.out.println(line);

                        //for loop to print the next two lines after the title
                        for (int i = 0; i < 2; i++) {
                            System.out.println(reader.readLine());
                        }
                        break;
                    }
                }
            }
            if (!found) {
            System.out.println("Food not found.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while searching for the food item.");
            System.out.println("Error: " + e);
        }
    }

    //method to delete food item from the menu (manhah)
    void deleteFood(String title, String fileName) {
        //using boolean to indicate whether the food item was found and deleted or not
        boolean deleted = false;
    
        //checking if the menu is empty or not
        if (head == null || foodCount == 0) {
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
        writeToFile(fileName);
    }

    //method to check the number of food varieties (furat)
    public int checkFood(String fileName) {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            //to process each line in the file
            String line;
            Food temp = head;
            int count = 0;
            
            System.out.println("Displaying food varieties...\n");
            while((line = reader.readLine()) != null){
                if(line.startsWith("Title: ")){

                    // +2 so that the extracted substring starts from the actual value after the colon
                    String title = line.substring(line.indexOf(":") + 2);
                    String countryOfOrigin = reader.readLine().substring(line.indexOf(":") + 2);
                    String info = reader.readLine().substring(line.indexOf(":") + 2);

                    System.out.println("Title: " + title);
                    System.out.println("Country of Origin: " + countryOfOrigin);
                    System.out.println("Info: " + info + "\n");

                    count++;
                    temp = temp.next;
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
            String line;
            boolean foodLine = false;
    
            while ((line = reader.readLine()) != null) {
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
    
    //method to display specific information of a food item (generateReport)
    void foodInfo(Food food, String reportType){
        switch(reportType){
            case "title":
            System.out.println("Title: " + food.title);
            break;

            case "country":
            System.out.println("Title: " + food.title);
            System.out.println("Country of Origin: " + food.countryOfOrigin);
            break;

            case "info":
            System.out.println("Title: " + food.title);
            System.out.println("Country of Origin: " + food.countryOfOrigin);
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
        System.out.println("Enter the title of the unavailable food item: ");
        String complaint = input.nextLine();

        System.out.println("Your complaint has been registered successfully.");
    }

    //implementing the same method to search for existing food items on the menu
    protected Food searchFood(String title) {
        Food current = head;
        while (current != null) {
            if (current.title.equals(title)){
                System.out.println("Title: " + current.title);
                System.out.println("Country of Origin: " + current.countryOfOrigin);
                System.out.println("Info: " + current.info + "\n");
                
                //return food item that's found
                return current;
            }
            current = current.next;
        }
        System.out.println(title + " not found.\n");
        
        //if no food item is found
        return null;
    }


    //implementing the same method to check for existing food varieties on the menu
    @Override
    public int checkFood(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int count = 0;
            System.out.println("Displaying food varieties...");
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Title: ")) {
                    // Extract and print the food information
                    String title = line.substring("Title: ".length());
                    String countryOfOrigin = reader.readLine().substring("Country of Origin: ".length());
                    String info = reader.readLine().substring("Info: ".length());

                    System.out.println("Title: " + title);
                    System.out.println("Country of Origin: " + countryOfOrigin);
                    System.out.println("Info: " + info);
                    System.out.println();

                    // Increment the count
                    count++;
                }
            }
            System.out.println("You have access to " + count + " food varieties.");
            return count;
        } catch (IOException e) {
            System.out.println("An error occurred while checking the food items on the menu.");
            System.out.println("Error: " + e);
            return 0;
        }
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

            adminMenu.writeToFile(filename);
            studentMenu.writeToFile(filename);

            while (true) {
                System.out.println("\n------------------------");
                System.out.println("Please select the user: ");
                System.out.println("1. Administrator");
                System.out.println("2. Student");
                System.out.println("3. Exit");
                System.out.println();
                
                int choice = input.nextInt();
                
                //nested switch cases considering two types of users: admin and students
                switch (choice) {
                    //if user choice was 1. Admin
                    case 1:
                        System.out.println("\n-------------------");
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
                            //calling the addFood method
                            case "a":
                            System.out.println("Enter food title: ");
                            String title = input.next();
                            System.out.println("Enter country of origin: ");
                            String country = input.next();
                            System.out.println("Enter additional info: ");
                            String info = input.next();
                            adminMenu.addFood(title, country, info, filename);                            
                            break;

                            //calling the inquireFood method
                            case "b":
                            adminMenu.inquireFood(filename);
                            break;

                            //calling the requestFood method
                            case "c":
                            adminMenu.requestFood(input, filename);
                            break;
                            
                            //calling the updateRequests method
                            case "d":
                            adminMenu.updateRequests(filename, input);
                            break;

                            //calling the foodComplaint method
                            case "e":
                            System.out.println("Enter the title of the food you would like to add: ");
                            String titleToComplain = input.next();
                            adminMenu.foodComplaint(input, titleToComplain, filename);
                            break;

                            //calling the deleteFood method
                            case "f":
                            System.out.print("\nEnter title of the food to delete: ");
                            String deleteTitle = input.next();
                            adminMenu.deleteFood(deleteTitle, filename);
                            break;

                            //calling the searchFood method
                            case "g":
                            System.out.println("\nEnter food title to search: ");
                            String searchTitle = input.next();
                            adminMenu.searchFood(filename, searchTitle);
                            break;
                            
                            //calling the checkFood method
                            case "h":
                            adminMenu.checkFood(filename);                         
                            break;
                            
                            //calling the generateReport method
                            case "i":
                            System.out.println("Enter the type of report to generate: ");
                            String report = input.next();
                            adminMenu.generateReport(report, filename);
                            break;
                            
                            case "j":
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
                        System.out.println("d. Search food on the menu");
                        System.out.println("e. Check the number of food varieties");
                        System.out.println("f. Exit Student Menu");
                        System.out.println();
        
                        String student = input.next();
                        
                        switch (student) {
                            //calling the inquireFood() method
                            case "a":
                            studentMenu.inquireFood(filename);
                            break;
                            
                            //calling the foodRequest() method
                            case "b":
                            studentMenu.requestFood(input, filename);
                            break;
        
                            //calling the foodComplain() method
                            case "c":
                            System.out.println("Enter the title of the food to complain about: ");
                            String titleToComplain = input.next();
                            studentMenu.foodComplaint(input, titleToComplain, filename);
                            break;

                            //calling the searchFood() method
                            case "d":
                            System.out.println("\nEnter food title to search: ");
                            String searchTitle = input.next();
                            studentMenu.searchFood(searchTitle);
                            break;

                            //calling the checkFood method
                            case "e":
                            studentMenu.checkFood(filename);
                            break;
        
                            case "f":
                            System.out.println("Exiting Student Menu...");
                            break;
        
                            default:
                            System.out.println("Invalid choice.");
                            break;
                        }
                        
                        if (student.equals("f")) {
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