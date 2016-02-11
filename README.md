This repository contains the solution to Jurgensville Restaurant Puzzle in Java. 


Below is the problem statement:

Jurgensville Restaurant Puzzle:
--------------------------------
Because it is the Internet Age, but also it is a recession, the Comptroller of the town of Jurgensville has decided to publish the prices of every item on every menu of every restaurant in town, all in a single CSV file (Jurgensville is not quite up to date with modern data serialization methods). In addition, the restaurants of Jurgensville also offer Value Meals, which are groups of several items, at a discounted price. The Comptroller has also included these Value Meals in the file. The file's format is:
 
for lines that define a price for a single item:
            restaurant ID, price, item_label
for lines that define the price for a Value Meal (there can be any number of items in a value meal)
            restaurant ID, price, item_1_label, item_2_label, ...
 All restaurant IDs are integers, all item labels are underscore(no space) separated letters, and the price is a decimal number.
 
Because you are an expert software engineer, you decide to write a program that accepts the town's price file, and a list of item labels that someone wants to eat for dinner, and outputs the restaurant they should go to, and the total price it will cost them.  It is okay to purchase extra items, as long as the total cost is minimized. If multiple restaurants are found, output any restaurant ID with the minimum price. DON'T OUTPUT ALL RESTAURANT IDs AND PRICEs. You can safely assume that SINGLE QUANTITY for any menu item will be asked by the user.
 
Please solve the puzzle above using the development language that you are being interviewed for.
We have seen many solutions that work for the attached data-set. However the solutions fail with many other data-sets we use to check the boundary conditions. Keeping this in mind, kindly assess all the possibilities, including but not limited to:
 
1. Menu directly available(1 item or all items).
2. Menu available but distributed over multiple items.
3. Menu need not be present in all restaurants listed.
4. Menu not available at all.
 
###### Here are some sample data sets, program inputs, and the expected result:
----------------------------

Data File sample_data.csv

1, 4.00, burger  
1, 8.00, tofu_log  
2, 5.00, burger  
2, 6.50, tofu_log  

Program Input:  
            program sample_data.csv burger tofu_log  
Expected Output:  2, 11.5  
            
----------------------------
----------------------------
Data File sample_data.csv  

3, 4.00, chef_salad  
3, 8.00, steak_salad_sandwich  
4, 5.00, steak_salad_sandwich  
4, 2.50, wine_spritzer  

Program Input  
            program sample_data.csv chef_salad wine_spritzer  
Expected Output:  Nil (to indicate that no matching restaurant could be found)  

----------------------------  

----------------------------  
Data File sample_data.csv  

5, 4.00, extreme_fajita  
5, 8.00, fancy_european_water  
6, 5.00, fancy_european_water  
6, 6.00, extreme_fajita, jalapeno_poppers, extra_salsa  
Program Input:  
            program sample_data.csv fancy_european_water extreme_fajita  
Expected Output:  6, 11.0 

----------------------------


To Run this program, Follow the below steps

To compile the source code, execute the below command from "src" folder.

```
javac sharma/abhishek/jurgensville/*.java sharma/abhishek/jurgensville/test/*.java
```

After compilation, the following command should run successfully:

```
java sharma.abhishek.jurgensville.test.Main ../sample_data.csv burger tofu_log
```






