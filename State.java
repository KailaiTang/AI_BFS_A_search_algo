import java.util.ArrayList;

/**
 * A state in the search represented by the (x,y) coordinates of the square and the parent. In other
 * words a (square,parent) pair where square is a Square, parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

  private Square square;
  private State parent;

  // Maintain the gValue (the distance from start)
  // You may not need it for the BFS but you will
  // definitely need it for AStar
  private int gValue;

  // States are nodes in the search tree, therefore each has a depth.
  private int depth;

  /**
   * @param square current square
   * @param parent parent state
   * @param gValue total distance from start
   */
  public State(Square square, State parent, int gValue, int depth) {
    this.square = square;
    this.parent = parent;
    this.gValue = gValue;
    this.depth = depth;
  }

  /**
   * @param visited explored[i][j] is true if (i,j) is already explored
   * @param maze initial maze to get find the neighbors
   * @return all the successors of the current state
   */
  public ArrayList<State> getSuccessors(boolean[][] explored,
      Maze maze) {

    ArrayList<State> successors = new ArrayList<>();

    Square l = new Square(square.X, square.Y - 1);
    Square d = new Square(square.X + 1, square.Y);
    Square r = new Square(square.X, square.Y + 1);
    Square u = new Square(square.X - 1, square.Y);
    
    State left = new State(l, this, gValue + 1, depth + 1);
    State down = new State(d, this, gValue + 1, depth + 1);
    State right = new State(r, this, gValue + 1, depth + 1);
    State up = new State(u, this, gValue + 1, depth + 1);

    if (explored[left.square.X][left.square.Y] == false && maze
        .getMazeMatrix()[left.square.X][left.square.Y] != '%') {
      successors.add(left);
    }
    if (explored[down.square.X][down.square.Y] == false && maze
        .getMazeMatrix()[down.square.X][down.square.Y] != '%') {
      successors.add(down);
    }
    if (explored[right.square.X][right.square.Y] == false && maze
        .getMazeMatrix()[right.square.X][right.square.Y] != '%') {
      successors.add(right);
    }
    if (explored[up.square.X][up.square.Y] == false
        && maze.getMazeMatrix()[up.square.X][up.square.Y] != '%') {
      successors.add(up);
    }

    return successors;

  }

  /**
   * @return x coordinate of the current state
   */
  public int getX() {
    return square.X;
  }

  /**
   * @return y coordinate of the current state
   */
  public int getY() {
    return square.Y;
  }

  /**
   * @param maze initial maze
   * @return true is the current state is a goal state
   */
  public boolean isGoal(Maze maze) {
    if (square.X == maze.getGoalSquare().X
        && square.Y == maze.getGoalSquare().Y)
      return true;

    return false;
  }

  /**
   * @return the current state's square representation
   */
  public Square getSquare() {
    return square;
  }

  /**
   * @return parent of the current state
   */
  public State getParent() {
    return parent;
  }

  /**
   * You may not need g() value in the BFS but you will need it in A-star search.
   * 
   * @return g() value of the current state
   */
  public int getGValue() {
    return gValue;
  }

  /**
   * @return depth of the state (node)
   */
  public int getDepth() {
    return depth;
  }
}
