
public class GuitarString {
	public RingBuffer ring;
	public int tics;
	// create a guitar string of the given frequency, using a sampling rate of 44,100
	public GuitarString(double frequency){
		this.ring = new RingBuffer((int)Math.ceil(StdAudio.SAMPLE_RATE / frequency));
		this.tics = 0;
	}
	// set the buffer to white noise
	public double[] pluck() {
		int n = this.ring.capacity;
		for(int i=1; i<n ; i++){
			this.ring.buffer[i] = 2 * Math.random() - 1;
		}
		return this.ring.buffer;
	}
	
	// advance the simulation one time step
	public void tic() {
		int oldTics = this.tics;
		int n = this.ring.capacity;
		double karplus = .994 * .5 * (this.ring.peek() + this.ring.nth(1));
		this.ring.enqueue(karplus);
		this.ring.dequeue();
		this.tics = oldTics + 1;
	}
	// return the current sample
	public double sample() {
		return this.ring.peek();
	}
	// return number of tics
	public int time() {
		return this.tics;
	}
	
	public double[] give() {
		return this.ring.buffer;
	}
	
	public static double[] guitarNote(double hz, int duration) {
         int N = StdAudio.SAMPLE_RATE * duration;
         double[] x = new double[N+1];
         GuitarString stringx = new GuitarString(hz);
         stringx.pluck();
         x[0] = stringx.sample();
         for (int i = 0; i < N; i++) {
         	stringx.tic();
            x[i] = stringx.sample();
         }
         return x;
     }
}
