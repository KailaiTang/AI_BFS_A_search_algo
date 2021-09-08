import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

  /**
   * Calls the parent class constructor.
   * 
   * @see Searcher
   * @param maze initial maze.
   */
  public AStarSearcher(Maze maze) {
    super(maze);
  }

  /**
   * Main a-star search algorithm.
   * 
   * @return true if the search finds a solution, false otherwise.
   */
  public boolean search() {

    // explored list is a Boolean array that indicates if a state associated with a given position in
    // the maze has already been explored.
    boolean[][] explored =
        new boolean[maze.getNoOfRows()][maze.getNoOfCols()];

    PriorityQueue<StateFValuePair> frontier =
        new PriorityQueue<StateFValuePair>();

    double hValueForPlayerState = getHValue(maze.getPlayerSquare());
    frontier.add(new StateFValuePair(
        new State(maze.getPlayerSquare(), null, 0, 0),
        0 + hValueForPlayerState));
    // f = g+h, where g is 0 for the start player node

    while (!frontier.isEmpty()) {

      // update the maxSizeOfFrontier to the maximum size of the frontier
      if (frontier.size() > maxSizeOfFrontier) {
        maxSizeOfFrontier = frontier.size();
      }

      // poll out the minimum stateFValuePair of the PriorityQueue
      // 1. update the maximum depth searched
      // 2. update the number of nodes expanded
      // 3. update the boolean[][] explored


      StateFValuePair current = frontier.poll();
      State cur = current.getState();

      if (cur.getDepth() > maxDepthSearched) {
        maxDepthSearched = cur.getDepth();
      }
      noOfNodesExpanded++;
      explored[cur.getX()][cur.getY()] = true;

      // if the X and Y coordinates correspond to each other for cur and goalState,
      // then the goal is found
      // 1. update the maze
      // 2. update the cost
      // 3. return true
      if (cur.isGoal(maze)) {
        cost = cur.getGValue();
        // update the maze by tracing all the parent sate
        while (cur.getParent() != null) {
          if (!cur.isGoal(maze)) {
            maze.setOneSquare(cur.getSquare(), '.');
          }
          cur = cur.getParent();
        }
        return true;
      }

      // To this point, it means that the goal state has not yet been found
      // 1. expand and iterate through all successors (successors must not be explored before)
      // 2. if frontier does not contains the successor, offer the state to the queue
      // 3. if frontier contains the successor, and the successor is smaller, than update

      for (State successor : cur.getSuccessors(explored, maze)) {
        boolean alreadyExist = false;
        StateFValuePair duplicate = null;
        double fValueOfSuccessor =
            cur.getGValue() + 1 + getHValue(successor.getSquare());

        for (StateFValuePair element : frontier) {
          if (element.getState().getX() == successor.getX()
              && element.getState().getY() == successor.getY()) {
            alreadyExist = true;
            duplicate = element;
          }
        }
        if (!alreadyExist) {
          // the gValue of successor is the gValue of cur plus 1
          // the fValue = gValue + hValue
          frontier
              .add(new StateFValuePair(successor, fValueOfSuccessor));
        } else if (fValueOfSuccessor < duplicate.getFValue()) {
          frontier.remove(duplicate);
          frontier
              .add(new StateFValuePair(successor, fValueOfSuccessor));
        }

      }
    }
    return false;
    // TODO return false if no solution
  }

  /**
   * Calculate the H value between two squares
   * 
   * @param square1
   * @param square2
   * @return
   */
  private double getHValue(Square square1) {
    double horizontal =
        Math.pow(square1.X - maze.getGoalSquare().X, 2);
    double vertical = Math.pow(square1.Y - maze.getGoalSquare().Y, 2);
    return Math.sqrt(horizontal + vertical);
  }

}
