package com.techelevator.models;

import java.util.ArrayList;
import java.util.List;

import com.techelevator.models.file_io.FileProductLoader;
import com.techelevator.models.product.Product;

public class Inventory 
{
	private List<Product> products = new ArrayList<Product>();
	
	public Inventory()
	{
		loadInventory();
	}
	

    private void loadInventory()
    {
        // Inventory should NOT know how to read from a file
        // we will use the FileProductLoader to get the products
    	FileProductLoader loader = new FileProductLoader();
    	products = loader.getProducts();
    }
    
    public List<Product> getProducts()
    {
    	return products;
    }
    
    public Product getProductById(String productId)
    {
        Product product = null;
    	for (Product p : products)
		{
			if(p.getId().equals(productId))
			{
				product = p;
				break;
			}
        }
        
        return product;
    }
    
}
