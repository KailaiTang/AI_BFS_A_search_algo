import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

  /**
   * Calls the parent class constructor.
   * 
   * @see Searcher
   * @param maze initial maze.
   */
  public BreadthFirstSearcher(Maze maze) {
    super(maze);
  }

  /**
   * Main breadth first search algorithm.
   * 
   * @return true if the search finds a solution, false otherwise.
   */
  public boolean search() {

    // explored list is a 2D Boolean array that indicates if a state associated with a given position in
    // the maze has already been explored.
    boolean[][] explored =
        new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
    // Queue implementing the Frontier list
    LinkedList<State> queue = new LinkedList<State>();

    explored[maze.getPlayerSquare().X][maze.getPlayerSquare().Y] =
        true;
    queue.add(new State(maze.getPlayerSquare(), null, 0, 0));
    // Initial gValue is 0, and initial depth is 0
    // Initial parent state is null

    while (!queue.isEmpty()) {

      // update the maximum size of the frontier
      if (queue.size() > maxSizeOfFrontier) {
        maxSizeOfFrontier = queue.size();
      }

      // pop out the minimum state of the PriorityQueue
      // 1. update the maximum depth searched
      // 2. update the number of nodes expanded
      // 3. update the boolean[][] explored
      State cur = queue.pop();
      if (cur.getDepth() > maxDepthSearched) {
        maxDepthSearched = cur.getDepth();
      }
      noOfNodesExpanded++;
      explored[cur.getX()][cur.getY()] = true;


      // If the goal is found
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
      // 2. if queue does not contains the successor, offer the state to the queue
      for (State successor : cur.getSuccessors(explored, maze)) {
        boolean alreadyExist = false;
        for (State frontier : queue) {
          if (frontier.getX() == successor.getX()
              && frontier.getY() == successor.getY()) {
            alreadyExist = true;
          }
        }
        if (!alreadyExist) {
          queue.add(successor);
        }
      }

    }
    return false;
  }
}
