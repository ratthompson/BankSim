//
// Member.java
//
// Simulates the members of the credit union
//
// Created in 2020 by Robert Thompson
//

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Member implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	float amtInput;
	int continueSearch;
	int input, accessNbr;
	String dateOfBirth = "";
	String socSecNbr = "";
	String firstName = "";
	String lastName = "";
	String phoneNbr = "";
	String street = "";
	String city = "";
	String state = "";
	String emailAddress = "";
	String zipCode = "";
	
	static List<Object> acctList = new ArrayList<Object>();
	
	transient Scanner scan = new Scanner(System.in);
	transient ToolKit tool = new ToolKit();
	
	public void mainRecord() {
		continueSearch = 1;
		while(continueSearch == 1) {
			System.out.println("Member Record");
			System.out.println("Access Number: " + accessNbr);
			System.out.println("Name: " + firstName + " " + lastName);
			System.out.println("");
			System.out.println("Account List:");
			System.out.println("");
			Account currentAcct = new Account();
			for(int i = 0; i < acctList.size(); ++i) {
				currentAcct = (Account)acctList.get(i);
				System.out.println(currentAcct.acctNbr + "   " + currentAcct.bal);
			}
			System.out.println("");
			System.out.println("Enter 1 to view member details.");
			System.out.println("Enter 2 to to access a specific account.");
			System.out.println("Enter 3 to perform a transaction.");
			System.out.println("Enter 4 to create a new account.");
			System.out.println("Enter 5 to return to member lookup.");
			input = scan.nextInt();
			if(input == 1) {
				mbrDetails();
			}
			else if(input == 2) {
				System.out.println("Enter the account number to access:");
			}
			else if(input == 3) {
				System.out.println("Enter 1 to perform a deposit.");
				System.out.println("Enter 2 to perform a withdrawal.");
				System.out.println("Enter 3 to perform a transfer.");
				System.out.println("Enter 4 to cancel.");
				input = scan.nextInt();
				if(input == 1) {
					System.out.println("Select an account to add funds to:");
					for(int i = 0; i < acctList.size(); ++i) {
						currentAcct = (Account)acctList.get(i);
						System.out.println(i + " " + currentAcct.acctNbr + "   " + currentAcct.bal);
						}
					input = scan.nextInt();
					currentAcct = (Account)acctList.get(input);
					System.out.println("Enter an amount to add:");
					amtInput = scan.nextFloat();
					currentAcct.deposit(amtInput);
					System.out.println("The new balance of account " + currentAcct.acctNbr + " is " + currentAcct.bal + ".");
				}
				else if(input == 2) {
					System.out.println("Select an account to remove funds from:");
					for(int i = 0; i < acctList.size(); ++i) {
						currentAcct = (Account)acctList.get(i);
						System.out.println(i + " " + currentAcct.acctNbr + "   " + currentAcct.bal);
						}
					input = scan.nextInt();
					currentAcct = (Account)acctList.get(input);
					System.out.println("Enter an amount to remove:");
					amtInput = scan.nextFloat();
					currentAcct.withdrawal(amtInput);
					System.out.println("The new balance of account " + currentAcct.acctNbr + " is " + currentAcct.bal + ".");
				}
				else if(input == 3) {
					Account recAcct = new Account();
					
					System.out.println("Select an account to remove funds from:");
					for(int i = 0; i < acctList.size(); ++i) {
						currentAcct = (Account)acctList.get(i);
						System.out.println(i + " " + currentAcct.acctNbr + "   " + currentAcct.bal);
						}
					input = scan.nextInt();
					currentAcct = (Account)acctList.get(input);
					System.out.println("Select an account to add funds to:");
					for(int i = 0; i < acctList.size(); ++i) {
						recAcct = (Account)acctList.get(i);
						System.out.println(i + " " + recAcct.acctNbr + "   " + recAcct.bal);
						}
					input = scan.nextInt();
					recAcct = (Account)acctList.get(input);
					System.out.println("Enter an amount to transfer:");
					amtInput = scan.nextFloat();
					currentAcct.withdrawal(amtInput);
					recAcct.deposit(amtInput);
					System.out.println("The new balance of account " + currentAcct.acctNbr + " is " + currentAcct.bal + ".");
					System.out.println("The new balance of account " + recAcct.acctNbr + " is " + recAcct.bal + ".");
				}
			}
			else if(input == 4) {
				System.out.println("Create account");
				System.out.println("Enter 1 for a savings account.");
				System.out.println("Enter 2 for a checking account.");
				input = scan.nextInt();
				createAcct(input);
			}
			else if(input == 5) {
				continueSearch = 0;
				BankSim.mbrList.add(this);
			}
			else {
				System.out.println("Invalid");
			}
		}
	}
	public void mbrDetails() {
		System.out.println("Member Details");
		System.out.println("Enter 1 to modify the member's information.");
		System.out.println("Enter 2 to delete the member record.");
		System.out.println("Enter 3 to return the main member record/");
	}
	public void modifyInfo() {
		System.out.println("Modify Info");
		System.out.println("");
	}
	public void deleteMbr() {
		System.out.println("Delete member record");
	}
	public void createAcct(int acctType) {
		Account currentAcct = new Account();
		currentAcct.acctNbr = tool.generateAcctNbr();
		currentAcct.accessNbr = accessNbr;
		if(acctType == 1) {
			currentAcct.acctType = "Savings";
			currentAcct.rate = (float)0.25;
		}
		else if(acctType == 2) {
			currentAcct.acctType = "Checking";
			currentAcct.rate = (float)0.05;
		}
		acctList.add(currentAcct);
		System.out.println(currentAcct.acctNbr + "   " + currentAcct.bal);
	}
	public static void loadAccounts() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream("accounts.txt");
		ObjectInputStream ois = new ObjectInputStream(fis);
		List<Object> read = (List<Object>)ois.readObject();
		acctList = read;
		ois.close();
	}
	public void saveAccounts() throws IOException {
		FileOutputStream fos = new FileOutputStream("accounts.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(acctList);
		oos.close();
	}
}