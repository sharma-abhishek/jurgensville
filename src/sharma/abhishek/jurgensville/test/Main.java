package sharma.abhishek.jurgensville.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import sharma.abhishek.jurgensville.Offer;
import sharma.abhishek.jurgensville.Restaurant;
import sharma.abhishek.jurgensville.RestaurantMenu;
import sharma.abhishek.jurgensville.RestaurantUtils;

/**
 * Main class containing the main method to run the JurgensVille program
 * 
 * @author abhisheksharma
 */

public class Main {
	
    //csv delimiter
	private final static String csvDelimiter = ",";

	/**
	 * Function to load CSV file into the list of Restaurants
	 * @param csvFile
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static Set<RestaurantMenu> loadCSV(File csvFile) throws NumberFormatException, IOException {
		Set<RestaurantMenu> restaurantItems = new HashSet<RestaurantMenu>();
		FileReader fileReader = new FileReader(csvFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringTokenizer stringTokenizer = null;
		String line = null;
		int lineNumber = 0;
		int menuId = 1;
		while((line = bufferedReader.readLine()) != null) {
			line = line.trim();
			lineNumber = lineNumber + 1;
			if(line.equals("")) continue;
			stringTokenizer = new StringTokenizer(line, csvDelimiter);
			if(stringTokenizer.countTokens() >= 3) {
				RestaurantMenu restaurantItem = new RestaurantMenu();
				restaurantItem.setMenuId(menuId);
				menuId++;
				int tokenIndex = 0;
				while(stringTokenizer.hasMoreTokens()) {
					switch(tokenIndex) {
						case 0:
							restaurantItem.setRestaurantId(Integer.parseInt(stringTokenizer.nextToken().trim()));
							tokenIndex++;
							break;
						case 1:
							restaurantItem.setPrice(Float.parseFloat(stringTokenizer.nextToken().trim()));
							tokenIndex++;
							break;
						default:
							Set<String> menu = restaurantItem.getMenu();
							if(menu == null) menu = new HashSet<String>();
							menu.add(stringTokenizer.nextToken().trim());
							restaurantItem.setMenu(menu);
							break;
					}
				}
				restaurantItems.add(restaurantItem);
			} else {
				System.err.println("Error parsing line number = " + lineNumber);
			}
		}
		fileReader.close();
		bufferedReader.close();
		csvFile = null;
		return restaurantItems;
	}
	

	/**
	 * Function to calculate best offer for desired items from CSV file
	 * 
	 * @param csvFile
	 * @param desiredItems
	 * @return {@link Offer} if found else null
	 * @throws NumberFormatException
	 *             , if there is any conversion error
	 * @throws IOException
	 *             , if there is any file I/O error
	 */
	public Offer getBestOffer(File csvFile, String[] desiredItemArray)
			throws NumberFormatException, IOException {
		// let the bestOffer be null
		Offer bestOffer = null;
		// load the restaurants from CSV file to a set (containing all the menus
		// and item details)
		Set<Restaurant> restaurants = RestaurantUtils
				.getRestaurants(loadCSV(csvFile));
		// iterate on available restaurants
		for (Restaurant restaurant : restaurants) {
			// get the best offer for desired items at a specific restaurant
			Offer offerAtThisRestaurant = restaurant.getOffer(desiredItemArray, bestOffer);
			// if offerAtThisRestuarant is null, then don't do anything, as that
			// restaurant does not provide the said items.
			// if offerAtThisRestuarant is not null, then process for bestOffer
			if (offerAtThisRestaurant != null) {
				// update the best offer either if it is null or offer
				// calculated at this restaurant is better than the bestOffer
				//System.out.println("offer at " + offerAtThisRestaurant);
				if (bestOffer == null
						|| (bestOffer != null && bestOffer
								.compareTo(offerAtThisRestaurant) > 0)) {
					bestOffer = offerAtThisRestaurant;
				}
			}
		}
		// return the bestOffer
		return bestOffer;
	}
	
	public static void main(String[] args) {
		// if the args length is less than 1, print error and return.
		if (args.length <= 1) {
			System.err
					.println("Too many less arguments. Expected Args: <SampleDataCSVFilePath> <item_label_1> ...");
			return;
		}
		try {
			//long start = System.currentTimeMillis();
			Main main = new Main();
			File file = new File(args[0]);
			Set<String> desiredItems = new HashSet<String>();
			for (int i = 1; i < args.length; i++) {
				desiredItems.add(args[i]);
			}
			String[] desiredItemArray = desiredItems.toArray(new String[desiredItems.size()]);
			Offer offer = main.getBestOffer(file, desiredItemArray);
			if (offer == null) {
				System.out.println("Nil");
			} else {
				System.out.println(offer);
			}
			//System.out.println("duration = " + (System.currentTimeMillis() - start)/1000.0);
		} catch (NumberFormatException nfe) {
			System.err.println("Error - " + nfe.getMessage());
		} catch (IOException ioe) {
			System.err.println("Error - " + ioe.getMessage());
		}
	}
}
