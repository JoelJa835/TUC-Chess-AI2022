import java.util.ArrayList;

public class MCTS {
	
	static final int end = 200;
	private int noPrize = 9;



    public String findNextMove(String[][] board, int player, World wrld,String receivedMsg) {

    	Node rootNode;
    	if (player==1){
            rootNode = new Node(board, 1, null,null);
        }else{
            rootNode = new Node(board, 0, null,null);
        }
    	int j=0 ;
        while (j< end) {
        	
        	System.out.println("DO i even go in the while");
            Node promisingNode = selectPromisingNode(rootNode);
            if (!wrld.isTerminalState(promisingNode.getBoard(),receivedMsg)) {
            	System.out.println("Am i going in here?");
                expandNode(promisingNode, player, wrld);
            }
            Node nodeToExplore = promisingNode;
            if (promisingNode.getChildArray().size() > 0) {
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            int playoutResult = simulateRandomPlayout(nodeToExplore, player, wrld,receivedMsg);
            backPropagation(nodeToExplore, playoutResult, player);
            j++;
        }

        Node winnerNode = rootNode.getChildWithMaxScore(); 
        if (winnerNode == null) {
        	System.out.println("I am not in the correct if");
        	return wrld.selectRandomAction();
        }
        else {
        	System.out.println("I am in the correct if");
        	return winnerNode.getMove();
        }
    }
    
    
    
    private int simulateRandomPlayout(Node node, int player,World wrld, String receivedMsg) {
        String[][] tmpBoard = node.getBoard();
        ArrayList<String> availableMoves;
 
        while (wrld.isTerminalState(tmpBoard, receivedMsg)) {
         
           availableMoves = this.getMoves(tmpBoard, player, wrld);
           
           if(availableMoves.size() == 0)
        	   break;
           
           int randMoveInd = (int) (Math.random()*availableMoves.size());
           
           String move = availableMoves.get(randMoveInd);
           
	   		int currentx = Character.getNumericValue(move.charAt(0));
	   		int currenty = Character.getNumericValue(move.charAt(1));
	   		int destinationx = Character.getNumericValue(move.charAt(2));
	   		int destinationy = Character.getNumericValue(move.charAt(3));
           
           tmpBoard = makeMove1(tmpBoard, currentx, currenty, destinationx, destinationy,noPrize,noPrize, wrld);
           
        }
        return wrld.evaluate(tmpBoard,1,2);
    }
    
	private Node selectPromisingNode(Node rootNode) {
	    Node node = rootNode;
	    while (node.getChildArray().size() != 0) {
	        node = UCT.findBestNodeWithUCT(node);
	    }
	    return node;
	}
	
	private void expandNode(Node node,int player, World wrld) {
		ArrayList<String> tmpMoves;
	    
		tmpMoves = this.getMoves(node.getBoard(), player, wrld);
	    
	    for (String move : tmpMoves ) {
	 
	   		int currentx = Character.getNumericValue(move.charAt(0));
	   		int currenty = Character.getNumericValue(move.charAt(1));
	   		int destinationx = Character.getNumericValue(move.charAt(2));
	   		int destinationy = Character.getNumericValue(move.charAt(3));
	   		
	   		String [][] tempBoard = makeMove1(node.getBoard(), currentx ,currenty, destinationx, destinationy,noPrize,noPrize, wrld);
	   		
            Node expNode = new Node(tempBoard,player, node, move);
            node.getChildArray().add(expNode);
        }   
	}
	
	
	public String[][] makeMove1(String [][] board,int x1, int y1, int x2, int y2, int prizeX, int prizeY, World wrld)
	{
		String chesspart = Character.toString(board[x1][y1].charAt(1));
		
		boolean pawnLastRow = false;
		
		// check if it is a move that has made a move to the last line
		if(chesspart.equals("P"))
			if( (x1==wrld.getRows()-2 && x2==wrld.getRows()-1) || (x1==1 && x2==0) )
			{
				board[x2][y2] = " ";	// in a case an opponent's chess part has just been captured
				board[x1][y1] = " ";
				pawnLastRow = true;
			}
		
		// otherwise
		if(!pawnLastRow)
		{
			board[x2][y2] = board[x1][y1];
			board[x1][y1] = " ";
		}
		
		// check if a prize has been added in the game
		if(prizeX != noPrize)
			board[prizeX][prizeY] = "P";
		return board;
	}
	



	private void backPropagation(Node nodeToExplore, int playoutResult,int player) {
	    Node tempNode = nodeToExplore;
	    while (tempNode != null) {
	        tempNode.incrementVisit();
	        if (tempNode.getPlayer() == player) {
	            tempNode.updateChildReward(playoutResult);
	        }
	        tempNode = tempNode.getParent();
	    }
	}

	
	private ArrayList<String> getMoves(String[][] board, int player, World wrld){
		ArrayList<String> tmpMoves;
		if (player == 1)
	    	tmpMoves = wrld.whiteMoves1(board);
	    else 
	    	tmpMoves = wrld.blackMoves1(board);
		
		return tmpMoves;
	}
}
