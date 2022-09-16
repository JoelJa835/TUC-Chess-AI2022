
public class Pair<T1,T2>{
	private T1 move;
	private T2 eval;
	
	public Pair(T1 move, T2 eval) {
		this.move = move;
		this.eval = eval;
	}
	
	public T1 getMove() {
		return move;
	}
	
	public T2 getEval() {
		return eval;
	}
}