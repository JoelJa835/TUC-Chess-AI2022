import java.util.Collections;
import java.util.Comparator;
public class UCT {
	public static double uctVal(int visitCount, double childReward, int childVisitCount) {
		        if (childVisitCount == 0) {
		            return Double.MAX_VALUE;
		        }
		        return ((double) childReward / (double) childVisitCount) 
		          + 1.41 * Math.sqrt(Math.log(visitCount) / (double) childVisitCount);
		    }

		    public static Node findBestNodeWithUCT(Node node) {
		        int parentVisit = node.getVisitCount();
		        return Collections.max(node.getChildArray(),Comparator.comparing(c -> uctVal(parentVisit, c.getChildReward(), c.getVisitCount())));
		    }

}
