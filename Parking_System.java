import java.io.*;
import java.util.Scanner;
import java.util.Vector;

class Vehicle {
    private String ownerName;
    private String vehicleType;
    private String vehicleNumber;
    private String mobileNumber;
    private double moneyCharged;

    public Vehicle(String ownerName, String vehicleType, String vehicleNumber, String mobileNumber /*int age*/, double moneyCharged) {
        this.ownerName = ownerName;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.mobileNumber = mobileNumber;
        this.moneyCharged = moneyCharged;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public double getMoneyCharged() {
        return moneyCharged;
    }

    // @Override
    public String toString() {
        return "Owner Name: " + ownerName + "\n" +
                "Vehicle Type: " + vehicleType + "\n" +
                "Vehicle Number: " + vehicleNumber + "\n" +
                "Mobile Number: " + mobileNumber + "\n" +
              "Money Charged: " + moneyCharged;
    }
}

public class Parking_System{
    
    // private static boolean isSlotAvailable(int slot) {
    //     Vector<Integer> numberVector = new Vector<>();
    //     if(numberVector.size()==0){
    //         numberVector.add(slot);
    //         return true;
    //     }
    //     for (int i = 0; i < numberVector.size(); i++) {
    //         if (numberVector.get(i) == slot) {
    //             return  false; 
    //         }
    //     }
    //     numberVector.add(slot);
    //     return false;
    // }
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Are you a customer or owner? (customer/owner/exit): ");
            String userType = scanner.nextLine();

            if (userType.equalsIgnoreCase("owner")) {
                System.out.print("Enter login id: ");
                String loginId = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                // Check login credentials (replace with your own authentication logic)
                if (isValidOwner(loginId, password)) {
                    manageOwnerSystem();
                } else {
                    System.out.println("Invalid login credentials.");
                }
            } else if (userType.equalsIgnoreCase("customer")) {
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();
                showCustomerDetails(customerName);
            } else if (userType.equalsIgnoreCase("exit")) {
                break;
            } else {
                System.out.println("Invalid input. Please choose 'customer', 'owner', or 'exit'.");
            }
        }
    }

    private static boolean isValidOwner(String loginId, String password) {
        // Replace with your own authentication logic (e.g., check against a database)
        return loginId.equals("admin") && password.equals("password");
    }

    private static void manageOwnerSystem() {
        Scanner scanner = new Scanner(System.in);
        Vector<Integer> slotVector = new Vector<>();
        while (true) {
            System.out.print("Enter 'new' to input customer details, 'show' to display details, or 'quit' to exit owner mode: ");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("new")) {
                // Read and store customer details
                System.out.print("Enter owner name: ");
                String ownerName = scanner.nextLine();
                System.out.print("Enter vehicle type: ");
                String vehicleType = scanner.nextLine();
                System.out.print("Enter vehicle number: ");
                String vehicleNumber = scanner.nextLine();
                System.out.print("Enter mobile number: ");
                String mobileNumber = scanner.nextLine();
                System.out.print("Enter money to be charged: ");
                double moneyCharged = Double.parseDouble(scanner.nextLine());
                System.out.print("Enter slot allotted to the customer (1-10): ");
                int slot = Integer.parseInt(scanner.nextLine());
                if(slotVector.size()==10){
                    System.out.println("No Empty Slots Available for Parking!!!!!");
                    return;
                }

                if(slotVector.size()==0)    slotVector.add(slot);
                else{
                    for (int i = 0; i < slotVector.size(); i++) {
                        while (slotVector.get(i) == slot) {
                            System.out.println("Slot is already Occupied!!!!");
                            System.exit(0);
                        }
                    slotVector.add(slot);
                    }
                }
        
        
                saveCustomerDetails(ownerName, vehicleType, vehicleNumber, mobileNumber, moneyCharged, slot);
                System.out.println("Data is saved successfully");



                // if (isSlotAvailable(slot)) {
                //     saveCustomerDetails(ownerName, vehicleType, vehicleNumber, mobileNumber, moneyCharged, slot);
                //     System.out.println("Data is saved successfully");
                // } else {
                //     System.out.println("Slot has already been allotted to another customer.");
                // }
            } else if (command.equalsIgnoreCase("show")) {
                // Display customer details
                displayCustomerDetails();
            } else if (command.equalsIgnoreCase("quit")) {
                break;
            } else {
                System.out.println("Invalid command. Please enter 'new', 'show', or 'quit'.");
            }
        }
    }

    

    private static void saveCustomerDetails(String ownerName, String vehicleType, String vehicleNumber, String mobileNumber, double moneyCharged, int slot) {
        try {
            FileWriter fileWriter = new FileWriter("customer_data.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            
            printWriter.println(new Vehicle(ownerName, vehicleType,  vehicleNumber, mobileNumber,moneyCharged));
            printWriter.println("Slot: " + slot);
            printWriter.println();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayCustomerDetails() {
        try {
            FileReader fileReader = new FileReader("customer_data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showCustomerDetails(String customerName) {
        try {
            FileReader fileReader = new FileReader("customer_data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            boolean customerFound = false;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("Owner Name: " + customerName)) {
                    customerFound = true;
                    System.out.println("Customer Details:");
                    while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                        System.out.println(line);
                    }
                }
            }

            if (!customerFound) {
                System.out.println("Customer not found.");
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
