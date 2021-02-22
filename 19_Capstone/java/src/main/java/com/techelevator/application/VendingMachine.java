package com.techelevator.application;

import java.math.BigDecimal;


import com.techelevator.models.Change;
import com.techelevator.models.Inventory;
import com.techelevator.models.Transactions;
import com.techelevator.models.exceptions.InsuficientFundsException;
import com.techelevator.models.file_io.Logger;
import com.techelevator.models.product.Product;
import com.techelevator.ui.UserInput;
import com.techelevator.ui.UserOutput;

//THIS IS OUR PAIR PROGRAM CODE!!!!

public class VendingMachine 
{
	private Inventory inventory = new Inventory();
	private Transactions transactions = new Transactions();
	
	private Logger errorLogger = new Logger("errors");
    private Logger activityLogger = new Logger("logs");
    
    private BigDecimal money;
	
    public void run() 
    {
    	
    	while(true)
        {
    	UserOutput.displayHomeScreen();        

    	String option = UserInput.getHomeScreenOption();

            if(option.equals("display"))
            {
                displayScreen();
            }
            else if(option.equals("purchase"))
            {
               purchaseScreen();
            }
            else if(option.equals("exit"))
            {
            	exit();
                break;
            }
            else
            {
                // invalid option try again
                UserOutput.displayMessage("You selected an invalid option, please select again");
            }
        }
    }
    
    public void displayScreen()
    {
    	System.out.println();
    	System.out.println("Here are the products we have available");
    	UserOutput.displayInventory(inventory);
    	//break;
    }
    
    public void purchaseScreen() 
    {
    	while(true)
        {
    		String option = UserInput.getPurchaseOptions();
    		
    		if (option.equals("Feed Money"))
    		{
    			feedMoney();
    		}
    		else if (option.equals("Select Product"))
    		{
    			selectProduct();
    		}
    		else if (option.equals("Exit Transaction"))
    		{
    			exitTransaction();
    			break;
    		}
    		else
    		{
    			UserOutput.displayMessage("You selected an invalid option, please select again!");
			}
        }
    }
    
	public void selectProduct() 
	{
	   UserOutput.displayInventory(inventory);
		try
		{
			// get product selection
			String idString = UserInput.selectProduct();
			
			// find product by ID
			Product product = inventory.getProductById(idString);
			System.out.println(product.toString());
			
			//insert soldOut() method
			// try to purchase (do they have enough money?) - if no, ask for more
				if(product.getQuantity() == 0)
				{
					System.out.println("THIS PRODUCT IS SOLD OUT!");
				}
				else if(transactions.getMoney().compareTo(product.getPrice()) < 0) //< inventory.getProductById(idString))
				{
					 //money is being subtracted
					System.out.println("Not enough money, Please add more!");
					
				}
		 
				else
				{
					//throw new Exception();
					product.purchase(); // quantity is reduced!
					transactions.purchase(product);
					System.out.println(product.getSound());
					
					activityLogger.logMessage("Product Purchased " + product + ", $" + transactions.getMoney());
				}

		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			errorLogger.logMessage(e.getMessage());
		}	
					
					
	}		
		
//				transactions.purchase(product); //money is being subtracted
//				product.purchase(); // quantity is reduced!
//				//System.out.println(product.getSound());
	
			
//			if(transactions.getMoney().compareTo(BigDecimal.ZERO) < 0)
//			{
//				System.out.println("PLEASE ADD MORE MONEY");
//			}
//			else
//			{
//				transactions.purchase(product); //money is being subtracted
//				product.purchase(); // quantity is reduced!
//				//System.out.println(product.getSound());
//			}
//			
			//product.soldOut();
			// get sound
			//System.out.println(product.getSound());
		

    
    public void feedMoney()
    {
   		money = UserInput.displayFeedMoneyOption();
		//save money to wallet
		transactions.add(money);
		System.out.println("Current Money added: $" + transactions.getMoney());
	
		activityLogger.logMessage("FEED MONEY $" + money + ", $" + transactions.getMoney());
    }
    
    public void exitTransaction()
    {
    	// return change to customer
		Change change = new Change();
		money = transactions.getMoney();
		System.out.println();
		System.out.println("You have $" + money + " remaining.");
		System.out.println();
		System.out.println("Transaction complete, your change amount is: " + change.getChange(money));	
    }
    
    public void exit()
    {
    	System.out.println();
    	System.out.println("Thanks for your business!");
    	System.out.println();
    }
    
}
