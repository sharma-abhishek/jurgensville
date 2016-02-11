package sharma.abhishek.jurgensville;

import java.util.Comparator;
import java.util.Set;

/**
 * POJO for storing the RestaurantMenu containing all the item details and price
 * @author abhisheksharma
 */
public class RestaurantMenu implements Comparator<RestaurantMenu> {
	
	private int restaurantId;
	private int menuId;
	private float price;
	private Set<String> menu;
	
	public RestaurantMenu() {}
	
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Set<String> getMenu() {
		return menu;
	}

	public void setMenu(Set<String> menu) {
		this.menu = menu;
	}
	
	
	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	

	@Override
	public String toString() {
		return "RestaurantMenu [id=" + restaurantId + ", menuId=" + menuId + ", price="
				+ price + ", menu=" + menu + "]";
	}
	
	

	@Override
	public boolean equals(Object obj) {
		RestaurantMenu restaurantMenu = (RestaurantMenu) obj;
		if(restaurantMenu.restaurantId == restaurantId && menu.size() == restaurantMenu.menu.size() && menu.containsAll(restaurantMenu.menu)) {
			return true;
		}
		return false;
	}


	@Override
	public int compare(RestaurantMenu r1, RestaurantMenu r2) {
		if(r1.getPrice() == r2.getPrice()) return 0;
		if(r1.getPrice() < r2.getPrice()) return -1;
		else return 1;
	}
	
}
