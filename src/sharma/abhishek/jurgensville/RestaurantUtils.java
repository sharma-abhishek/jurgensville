package sharma.abhishek.jurgensville;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Utility class containing convenience methods for calculation and processing.
 * 
 * @author abhisheksharma
 */
public class RestaurantUtils {
	
	/**
	 * Get the list of Restaurants
	 * 
	 * @param restaurantMenu
	 * @return
	 */
	public static Set<Restaurant> getRestaurants(
			Set<RestaurantMenu> restaurantMenu) {
		// group the restaurant menu by restaurant ids
		Map<Integer, Set<RestaurantMenu>> restaurantData = RestaurantUtils
				.groupById(restaurantMenu);
		// create an emtpy hashset to store the restaurant object
		Set<Restaurant> restaurants = new HashSet<Restaurant>();
		// iterate on the grouped restaurant data and create the restaurant
		// object with all the menu and item details
		for (Integer restaurantId : restaurantData.keySet()) {
			restaurants.add(new Restaurant(restaurantId, restaurantData
					.get(restaurantId)));
		}
		// return the unique set of restaurants
		return restaurants;
	}

	/**
	 * Group the {@link RestaurantMenu} by {@link Restaurant} ids
	 * 
	 * @param restaurantItems
	 * @return the map containing all the restaurant menu against its restaurant
	 *         id
	 */
	public static Map<Integer, Set<RestaurantMenu>> groupById(
			Set<RestaurantMenu> restaurantItems) {
		// create a new hash map
		Map<Integer, Set<RestaurantMenu>> groupedRestaurantMap = new HashMap<Integer, Set<RestaurantMenu>>();
		// iterate on all the restaurant menu items
		for (RestaurantMenu restaurantItem : restaurantItems) {
			// get the restaurant items from the grouped map
			Set<RestaurantMenu> restaurantsAtIndex = groupedRestaurantMap
					.get(restaurantItem.getRestaurantId());
			// if the map does not contain the set, then create an empty hash
			// set to push one in map
			if (restaurantsAtIndex == null)
				restaurantsAtIndex = new HashSet<RestaurantMenu>();
			// add this item in the restaurantAtIndex set
			restaurantsAtIndex.add(restaurantItem);
			// update the map at this restaurant id
			groupedRestaurantMap
					.put(restaurantItem.getRestaurantId(), restaurantsAtIndex);
		}
		return groupedRestaurantMap;
	}

	  /**
	   * Function to calculate the symmetric difference between two sets
	   * @param setA
	   * @param setB
	   * @return
	   */
	  public static <T> Set<T> symDifference(Set<T> setA, Set<T> setB) {
		  Set<T> symmetricDiff = new HashSet<T>(setA);
		  symmetricDiff.addAll(setB);
		  Set<T> tmp = new HashSet<T>(setA);
		  tmp.retainAll(setB);
		  symmetricDiff.removeAll(tmp);
		  return symmetricDiff;
	  }
}
