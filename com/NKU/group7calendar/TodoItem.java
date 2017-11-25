/*
 * This class represents encapsulates an item present in a todo list.
 * Whenever the user adds a task to his todo list, a new TodoItem is created. 
 */



package group7calendar;

public class TodoItem {
	private String item;
	private boolean isCompleted;
	
	public String getItem() { return item; }
	public boolean getIsCompleted() { return isCompleted; }
	
	public TodoItem(String item) {
		this.item = item;
	} 
	
	@Override
	public String toString() {
		return item;
	}
}