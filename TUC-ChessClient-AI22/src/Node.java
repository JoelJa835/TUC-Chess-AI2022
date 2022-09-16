import java.util.ArrayList;


public class Node {
	  private  int visitCount;
	  private ArrayList<Node> childArray;
	  private String[][] board;
	  private int player;
	  private double childReward;
	  private Node parent;
	  private String move;
	  private int score;
	 

	public Node( String[][] board,int player,Node parent, String move) {
			this.visitCount = 0;
			this.childArray = new ArrayList<Node>();
			this.board = board;
			this.player = player;
			this.childReward = 0;
			this.parent = parent;
			this.move = move;
			this.score = 0;
		}



	//Setters
    public void setVisitCount(int visitCount) {
	    this.visitCount = visitCount;
	 }

	public void addChildToArray(Node childNode) {
	    this.childArray.add(childNode);
	 }
	
	public void setBoard(String[][] board) {
		this.board = board;
	}	  
	
	public void setPlayer(int player) {
		this.player = player;
	}
	
	public void setChildReward(double childReward) {
		this.childReward = childReward;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public void setMove(String move) {
		this.move = move;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	
	

//Getters

	public int getVisitCount() {
		return this.visitCount;
	}


	public ArrayList<Node> getChildArray() {
		return this.childArray;
	}

	public double getChildReward() {
		
		return this.childReward;
	}
	
	public String[][] getBoard() {
		return board;
	}


	public int getPlayer() {
		return player;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public String getMove() {
		return move;
	}
	
	public int getScore() {
		return score;
	}
	
	


	public Node getRandomChildNode() {
		int randomChildIndex = (int) (Math.random()*this.childArray.size());
		return this.getChildArray().get(randomChildIndex);
	}


	public Node getChildWithMaxScore() {
		
		double max =  Double.MIN_VALUE;
		Node nodeSel = null;
		System.out.println(this.childArray.size());
		for(int i=0; i < this.childArray.size(); i++) {
			if (this.childArray.get(i).getChildReward() > max) {
				max = this.childArray.get(i).getChildReward();
				nodeSel = this.childArray.get(i);
			}
		}
		return nodeSel;
	}


	public void incrementVisit() {
		this.visitCount++;
		
	}


	public void updateChildReward(int playoutResult) {
		this.childReward += (double)playoutResult;
		
	}
}
