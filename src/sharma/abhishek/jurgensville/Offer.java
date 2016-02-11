package sharma.abhishek.jurgensville;

/**
 * POJO for storing calculated Offer
 * @author abhisheksharma
 */
public class Offer implements Comparable<Offer>{
	
	private int id;
	private float price;
	
	protected Offer() {}
	
	protected int getId() {
		return id;
	}
	
	protected void setId(int id) {
		this.id = id;
	}
	
	protected float getPrice() {
		return price;
	}
	
	protected void setPrice(float price) {
		this.price = price;
	}
	
	@Override
	public int compareTo(Offer o) {
		if(price < o.price) return -1;
		else if(price > o.price) return 1;
		return 0;
	}
	
	@Override
	public String toString() {
		return id + "," + price;
	}
	
	

}
