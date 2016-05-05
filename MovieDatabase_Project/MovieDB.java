import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB {
	
	MyLinkedList<Genre> list;
	
    public MovieDB() {
        // FIXME implement this
    	list = new MyLinkedList<Genre>();
    	// HINT: MovieDBGenre 클래스를 정렬된 상태로 유지하기 위한 
    	// MyLinkedList 타입의 멤버 변수를 초기화 한다.
    }

    public void insert(MovieDBItem item) {
        // FIXME implement this
        // Insert the given item to the MovieDB.
    	
    	Genre newGenre = new Genre(item.getGenre());
    	newGenre.getNext().insertNext(item.getTitle());
    	Node<Genre> curr = list.head;
    	
    	if(list.isEmpty()){
    		curr.insertNext(newGenre);
    	}else{
    		while(curr.getNext() != null && curr.getNext().getItem().compareTo(newGenre) < 0){
    			curr = curr.getNext();
    		}
    		/*
    		If curr gets out of while loop, there is 3 cases.
    		  1. curr.getNext() == null
    		  2. newGenre < next node
    		  3. newGenre == next node
    		*/
    		if(curr.getNext() == null){ //all node < newGenre
    			curr.insertNext(newGenre);
    		}
    		else if(curr.getNext().getItem().equals(newGenre)){ //genre of item is already added in the list
    			curr.getNext().getItem().movie.add(item.getTitle());
    		}else{
    			//newGenre < next node
    			curr.insertNext(newGenre);
    		}
    	}
    	
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        //System.err.printf("[trace] MovieDB: INSERT [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public void delete(MovieDBItem item) {
        // FIXME implement this
        // Remove the given item from the MovieDB.
    	Genre deleteGenre = new Genre(item.getGenre());
    	Genre temp = new Genre(null);
    	String s;
    	
    	Iterator<Genre> itr = list.iterator();
    	while(itr.hasNext()){
    		temp = itr.next();
    		if(temp.equals(deleteGenre)){
    			break;
    		}
    	}
    	if(!temp.equals(null)){
	    	Iterator<String> itr2 = temp.movie.iterator();
	    	while(itr2.hasNext()){
	    		s = itr2.next();
	    		if(s.equals(item.getTitle())){ itr2.remove(); }
	    	}
    	}
    	
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        //System.err.printf("[trace] MovieDB: DELETE [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public MyLinkedList<MovieDBItem> search(String term) {
        // FIXME implement this
        // Search the given term from the MovieDB.
        // You should return a linked list of MovieDBItem.
        // The search command is handled at SearchCmd class.
    	
    	// Printing search results is the responsibility of SearchCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.
    	
        // This tracing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
    	//System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);
    	
    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        
        Genre g;
    	String s;
    	MovieDBItem item;
    	Iterator<Genre> itr = list.iterator();
    	while(itr.hasNext()){
    		g = itr.next();
    		Iterator<String> itr2 = g.movie.iterator();
    		while(itr2.hasNext()){
    			s = itr2.next();
    			if(s.contains(term)){
    				item = new MovieDBItem(g.getItem(), s);
    				results.add(item);
    			}
    		}
    	}
        
        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
        // FIXME implement this
        // Search the given term from the MovieDatabase.
        // You should return a linked list of QueryResult.
        // The print command is handled at PrintCmd class.

    	// Printing movie items is the responsibility of PrintCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        //System.err.printf("[trace] MovieDB: ITEMS\n");

    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        
        Genre temp;
    	MovieDBItem item;
    	Iterator<Genre> itr = list.iterator();
    	while(itr.hasNext()){
    		temp = itr.next();
    		Iterator<String> itr2 = temp.movie.iterator();
    		while(itr2.hasNext()){
				item = new MovieDBItem(temp.getItem(), itr2.next());
				results.add(item);
    		}
    	}
        
    	return results;
    }
}

class Genre extends Node<String> implements Comparable<Genre> {
	
	MovieList movie = new MovieList();
	
	public Genre(String name) {
		super(name);
		super.setNext(movie.head);
	}
	
	@Override
	public int compareTo(Genre o) {
		return this.getItem().compareTo(o.getItem());
	}

	@Override
	public int hashCode() {
		return this.getItem().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Genre other = (Genre) obj;
        if (this.getItem() == null) {
            if (other.getItem() != null)
                return false;
        } else if (!this.getItem().equals(other.getItem()))
            return false;
        return true;
	}
}

class MovieList implements ListInterface<String> {	
	//dummy
	Node<String> head;
	int numItems;
	
	public MovieList() {
		head = new Node<String>(null);
	}

	@Override
	public Iterator<String> iterator() {
		return new MovieListIterator(this);
	}

	@Override
	public boolean isEmpty() {
		return head.getNext() == null;
	}

	@Override
	public int size() {
		return numItems;
	}

	@Override
	public void add(String item) {
		Node<String> last = head;
		
		if(this.isEmpty()){
			last.insertNext(item);
			numItems += 1;
		}else{
			while(last.getNext() != null && last.getNext().getItem().compareTo(item) < 0){
				last = last.getNext();
			}
			if(last.getNext()==null || last.getNext().getItem().compareTo(item) > 0){
				last.insertNext(item);
				numItems += 1;
			}
			else if(last.getNext().getItem().equals(item)){ //item is already existed
				//System.out.println("ADD NOTHING");
			}
			else{
				last.insertNext(item);
				numItems += 1;
			}
		}
	}

	@Override
	public String first() {
		return head.getNext().getItem();
	}

	@Override
	public void removeAll() {
		head.setNext(null);
	}
}


class MovieListIterator implements Iterator<String> {
	
	private MovieList list;
	private Node<String> prev;
	private Node<String> curr;
	
	public MovieListIterator(MovieList l){
		list = l;
		curr = l.head;
		prev = null;
	}
	
	public boolean hasNext(){
		return curr.getNext() != null;
	}
	
	public String next(){
		if(!this.hasNext()){
			throw new NoSuchElementException();
		}
		prev = curr;
		curr = curr.getNext();
		
		return curr.getItem();
	}
	
	public void remove(){
		if(prev == null){
			throw new IllegalStateException("next() should be called first");
		}
		if(curr == null){
			throw new NoSuchElementException();
		}
		
		prev.removeNext();
		list.numItems -= 1;
		curr = prev;
		prev = null;
	}
}

