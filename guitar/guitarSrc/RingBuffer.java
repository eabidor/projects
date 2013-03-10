
public class RingBuffer {
	double [] buffer; // array of type double ring buffer elements
	int capacity; // capacity of the ring buffer
	int first;  // int value of the index of the first element
	int last; // int value of the index of the last 
	
	RingBuffer(int capacity){
		this.buffer = new double [capacity];
		this.capacity = capacity;
		this.first = 0;
		this.last = 0;
	}
	
	// return the size of the ring buffer
	public double size(){
		if (this.first > this.last){
			return (this.last + this.capacity - this.first);
		} else { 
			return (this.last - this.first);
		}
	}
	
	// is the buffer empty (size equals zero)?
	public boolean isEmpty(){
		return (this.size() == 0);
	}
	// is the buffer full (size equals capacity)?
	public boolean isFull(){
		return  (this.size() == this.capacity);
	}
	// add item x to the end of the buffer
	public void enqueue(double x){
		int oldLast = this.last;
		if (!this.isFull()){
			this.buffer[this.last] = x;
			if (this.last == this.capacity - 1){
				this.last = 0;
			} else {
				this.last = oldLast + 1;
			}
		}
		else {
			throw new RuntimeException(" cannot add element to full ringbuffer");
		}
	}
	// delete and return item in front
	public double dequeue(){
		int oldFirst = this.first;
		if (!this.isEmpty()){
			if (this.first == this.capacity - 1){
				this.first = 0;
			} else {
				this.first = oldFirst + 1;
			}
			return this.buffer[oldFirst];
		} else {
			throw new RuntimeException("cant take first element of empty ringbuffer");
		}
	}
	// return (but do not delete) item in front
	public double peek(){
		return this.buffer[this.first];
		
	}
	
	public int mod(int i, int cap){
		if (i >= cap){
			return i - cap;
		} else {
			return i;
		}
	}
	public double nth(int n){
		return this.buffer[this.mod(this.first + n, this.capacity)];
	}
}
