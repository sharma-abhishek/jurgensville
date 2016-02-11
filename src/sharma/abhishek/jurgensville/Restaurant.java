package sharma.abhishek.jurgensville;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Restaurant class for storing and calculating the best offer within a specific restaurant
 * @author abhisheksharma
 */
public class Restaurant {
	
	private int id;
	private Set<RestaurantMenu> menu;
	private Set<String> items;
	
	protected Restaurant(int id, Set<RestaurantMenu> menu) {
		this.id = id;
		this.menu = menu;
		getItems();
	}
	
	protected int getId() {
		return id;
	}
	
	protected void setId(int id) {
		this.id = id;
	}
	
	protected Set<RestaurantMenu> getMenu() {
		return menu;
	}
	
	protected void setMenu(Set<RestaurantMenu> menu) {
		this.menu = menu;
	}
	
	protected Set<String> getItems() {
		if(items == null) {
			items = new HashSet<String>();
			for(RestaurantMenu item : menu) {
				items.addAll(item.getMenu());
			}
		}
		return items;
	}
	
	/**
	 * Method which checks whether the specified restaurant provide the given set of items
	 * @param restaurant, restaurant object
	 * @param items, items to be searched
	 * @return true if the restaurant provides else false
	 */
	private boolean provideItems(String[] items) {
		return getItems().containsAll(new HashSet<String>(Arrays.asList(items)));
	}
	
	/**
	 * Return all the RestaurantMenu at specified restaurant which contains the desired item.
	 * @param restaurant, restaurant object
	 * @param desiredItem, desired item
	 * @return
	 */
	private Set<RestaurantMenu> searchRestaurantMenuForDesiredItem(String desiredItem) {
		//create an empty hash set to store all the menu which contains the desired item.
		Set<RestaurantMenu> restaurantMenuSet = new HashSet<RestaurantMenu>();
		//iterate on all the menus of the restaurant
		for(RestaurantMenu restaurantMenu : menu) {
			//if the specific menu contains the item, add it in the restaurantMenuSet
			if(restaurantMenu.getMenu().contains(desiredItem)) {
				restaurantMenuSet.add(restaurantMenu);
			}
		}
		//return the restaurantMenuSet 
		return restaurantMenuSet;
	}
	
	
	
		
	
	/**
	 * Get the best offer of desiredItemSet at a this restaurant
	 * @param desiredItemSet set of items
	 * @return best {@link Offer} at this restaurant for given set of items, else null
	 */
	public Offer getOffer(String[] desiredItemArr, Offer bestOffer) {
		//if this restaurant does not provide the desired item set, then no offer exists, so return null
		if(! provideItems(desiredItemArr)) {
			return null;
		}
		//create a restaurant menu list
		List<RestaurantMenu> restMenuList = new ArrayList<RestaurantMenu>();
		//create a map to store the mapping between menu and item it provides
		Map<Integer, Set<Integer>> mappingBetweenMenuAndItem = new HashMap<Integer, Set<Integer>>();
		//Integer set to store all the items.
		Set<Integer> allItems = new HashSet<Integer>();
		
		//Iterate on the desired item array
		for(int i = 0; i < desiredItemArr.length; i++) {
			//add the index of this desired item in all item set
			allItems.add(i);
			//get the name of this desiredItem from desiredItemArr
			String desiredItem = desiredItemArr[i];
			//get all the menu which provides this desired item.
			Set<RestaurantMenu> restaurantMenuSet = searchRestaurantMenuForDesiredItem(desiredItem);
			//Iterate on the restaurant menu set to populate the restaurant menu list
			for(RestaurantMenu restMenu : restaurantMenuSet) {
				//if the restaurant menu list does not already contain restaurant menu, then add it to the list
				if(! restMenuList.contains(restMenu)) {
					//add the restaurant menu to the list
					restMenuList.add(restMenu);
				}
				//create an item and menu set to create a mapping between menu and item
				Set<Integer> itemAndMenuSet = mappingBetweenMenuAndItem.get(restMenu.getMenuId());
				//if itemAndMenuSet is null, instantiate the empty hashset
				if(itemAndMenuSet == null) itemAndMenuSet = new HashSet<Integer>();
				//add this index to the itemAndMenuSet
				itemAndMenuSet.add(i);
				//update the map
				mappingBetweenMenuAndItem.put(restMenu.getMenuId(), itemAndMenuSet);
			}
		}
		
		//sorting the restMenu list based on the price.
		Collections.sort(restMenuList, new RestaurantMenu());
		
		//if best offer is not null and if the first index price (which is least) is greater than best offer price, then no need to proceed with this restaurant.
		if(bestOffer != null && restMenuList.get(0).getPrice() >= bestOffer.getPrice()) {
			return null;
		}
		
		//get the size of the restaurant menu(s)
		int restMenuListSize = restMenuList.size();
		
		//create the results which stores all the possible combinations in which desiredItems can be served.
		ArrayList<ArrayList<RestaurantMenu>> results = new ArrayList<ArrayList<RestaurantMenu>>();
		
		//create an emtpy array list to store intermediate result
		ArrayList<RestaurantMenu> intermediateResult = new ArrayList<RestaurantMenu>();
		//trackElements set to keep track of the items that we have covered from desired items
		Set<Integer> trackElements = new HashSet<Integer>();
		
		//iterate on the restMenuList
		for(int i = 0; i < restMenuListSize; i++) {
			//clear the intermediate Results
			intermediateResult = new ArrayList<RestaurantMenu>();
			//get the Restaurant Menu at index i
			RestaurantMenu restMenuAtI = restMenuList.get(i);
			//get the set of indexes of items which this restaurant menu has.
			Set<Integer> itemIndexAtI = mappingBetweenMenuAndItem.get(restMenuAtI.getMenuId());
			//add this to the intermediate result.
			intermediateResult.add(restMenuAtI);
			
			//if this menu has all the desired items, then just add it to the results arraylist and continue other iterations to find better offer.
			if(itemIndexAtI.containsAll(allItems)) {
				results.add(intermediateResult);
				continue;
			} 
			
			//clear the trackElements
			trackElements = new HashSet<Integer>();
			//add the current index items to the trackElements
			trackElements.addAll(itemIndexAtI);
			//inner for loop to create combination over same restMenuList
			for(int j = 0; j < restMenuListSize; j++) {
						//if condition to avoid processing for the same indices.
						if(j != i) {
							//get the restaurant menu at jth index
							RestaurantMenu restMenuAtJ = restMenuList.get(j);
							//get the mapping between items offered by this menu
							Set<Integer> itemIndexAtJ = mappingBetweenMenuAndItem.get(restMenuAtJ.getMenuId());
							//calculate the difference between the trackElements and this jth index to check whether this menu adds up to the desired items.
							Set<Integer> difference = RestaurantUtils.symDifference(trackElements, itemIndexAtJ);
							//if difference is non zero, then there are few elements which could help us achieve desired items.
							if(difference.size() != 0 && !trackElements.containsAll(difference)) {
								//add the indices of item at this restaurant menu (jth) to track elements
								trackElements.addAll(itemIndexAtJ);
								//add this restaurant menu to the intermediate result.
								intermediateResult.add(restMenuAtJ);
								//if the trackElements contains all the desired items
								if(trackElements.containsAll(allItems)) {
									//add it to the resultant array list
									results.add(intermediateResult);
									//reset the intermediate result for further processing
									intermediateResult = new ArrayList<RestaurantMenu>();
									//clear the trackElements to start trackign fresh elements
									trackElements.clear();
									//reset intermediateResult and trackElements to its previous states (the one in outer for loop)
									intermediateResult.add(restMenuAtI);
									trackElements.addAll(itemIndexAtI);
								}
							}
						}
			}
		}		
		
		//let min be the max value the float could afford.
		float min = Float.MAX_VALUE;
		
		
		//iterate on the resultant set containing all the list of menus which can offer the desired items
		for(ArrayList<RestaurantMenu> restaurantMenuSet : results) {
			//System.err.println(restaurantMenuSet);
			//let the sum be null to calculate the sum 
			float sum = 0;
			//iterate on individual restaurant menu to calculate the sum.
			for(RestaurantMenu restaurantMenu : restaurantMenuSet) {
				sum += restaurantMenu.getPrice();
			}
			//if this sum is less than or equal to min, then udpate the min.
			if(sum <= min) {
				min = sum;
			}
		}
		
		//create an offer object
		Offer offer = new Offer();
		//set the restaurant id in offer object
		offer.setId(id);
		//set the min price of all the items in a specific restaurant
		offer.setPrice(min);
		//return the offer object.
		return offer;
	}
	
	
	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", menu=" + menu + ", items=" + items
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		Restaurant restaurant = (Restaurant) obj;
		if(restaurant.id == id) {
			return true;
		}
		return false;
	}
	
	
}
