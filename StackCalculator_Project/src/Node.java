import java.util.NoSuchElementException;

public class Node<T> {
	
	private T item;
	private Node<T> next;
	
	//constructor
	public Node(T newItem){
		this.item = newItem;
		this.next = null;
	}
	
	public Node(T newItem, Node<T> next){
		this.item = newItem;
		this.next = next;
	}
	
	//getItem, setItem, setNext, getNext, insertNext, removeNext
	public T getItem(){
		return this.item;
	}
	
	public void setItem(T obj){
		this.item = obj;
	}
	
	public Node<T> getNext(){
		return this.next;
	}
	
	public void setNext(Node<T> next){
		this.next = next;
	}
	
	public final void insertNext(T obj){
		Node<T> newNode = new Node<T>(obj, this.getNext().getNext());
		this.setNext(newNode);
	}
	
	public final void deleteNext(){
		if(this.getNext()!=null){
			this.setNext(this.getNext().getNext());
		}else{
			throw new NoSuchElementException();
		}
	}
}
