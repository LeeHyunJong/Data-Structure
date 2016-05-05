public class StackReferenceBased implements StackInterface {
	
	private Node<String> top;
	
	//constructor
	public StackReferenceBased(){
		top = null;
	}

	@Override
	public boolean isEmpty() {
		return top == null;
	}
	
	@Override
	public void push(Object newItem) {
		top = new Node<String>((String)newItem, top);
	}

	@Override
	public String pop() {
		String obj = top.getItem();
		top = top.getNext();
		return obj;
	}
	
	@Override
	public void popAll() {
		top = null;
	}

	@Override
	public String peek() {
		return top.getItem();
	}
	
	/*
	public void print(){
		Node<String> node = top;
		while(node.getNext()!=null){
			System.out.println(node.getItem());
			node = node.getNext();
		}
	}
	*/
}
