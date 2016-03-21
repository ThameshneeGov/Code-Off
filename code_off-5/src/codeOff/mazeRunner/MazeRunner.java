package codeOff.mazeRunner;

import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by thameshnee.govender on 2016/03/18.
 */
public class MazeRunner {

    private Scanner sc;
    private Maze maze;
    private Point currentPosition;
    private List<Point> possibleMoves;
    private List<Point> pathTaken = new ArrayList<Point>();
    private List<Point> pointsOfNoReturn = new ArrayList<Point>();

    public MazeRunner(String filePath){
        try{
            File file = new File(filePath);
            FileReader reader = new FileReader(file);
            sc = new Scanner(reader);
        }
        catch(Exception ex) {
            System.err.println("The maze could not be located :/");
        }
    }

    public void getMyCoffee(){
        createMaze();
        currentPosition = maze.getStartPoint();
        getPossibleMoves();
        while(possibleMoves.get(0).x != maze.getCoffeePosition().x || possibleMoves.get(0).y != maze.getCoffeePosition().y){
            walkToCoffee();
            getPossibleMoves();
        }
            System.out.println("Yay coffee!");
        maze.outputMaze();
    }

    private void createMaze(){
          String firstRow = sc.nextLine();
          maze = new Maze(firstRow.length());
          for(char c : firstRow.toCharArray()){
              maze.addObject(c);
          }
          while(sc.hasNextLine()){
              for(char c : sc.nextLine().toCharArray()){
                  maze.addObject(c);
              }
          }
    }

    private void walkToCoffee(){
        try{
            if(possibleMoves.size() > 0){
                maze.walkHere(possibleMoves.get(0));
                pathTaken.add(possibleMoves.get(0));
                currentPosition = possibleMoves.get(0);
            }
            else{
                backTrack();
            }
        }
        catch(InvalidMoveException ex){
            possibleMoves.remove(0);
            walkToCoffee();
        }
    }

    private void getPossibleMoves(){
        possibleMoves = new ArrayList();
        int x = currentPosition.x;
        int y = currentPosition.y;

        Point pointToAdd = new Point(x, y-1);
        if(y != 0 && !isAPointOfNoReturn(pointToAdd)){
            possibleMoves.add(pointToAdd);
        }
        pointToAdd = new Point(x, y+1);
        if(y != maze.getDimension()-1  && !isAPointOfNoReturn(pointToAdd)){
            possibleMoves.add(pointToAdd);
        }
        pointToAdd = new Point(x-1, y);
        if(x != 0 && !isAPointOfNoReturn(pointToAdd)){
            possibleMoves.add(pointToAdd);

        }
        pointToAdd = new Point(x+1, y);
        if(x != maze.getDimension()-1 && !isAPointOfNoReturn(pointToAdd)){
            possibleMoves.add(pointToAdd);
        }

        Collections.sort(possibleMoves, new PointComparator(maze.getCoffeePosition()));
    }

    private void backTrack(){
        maze.unwalkHere(currentPosition);
        pathTaken.remove(pathTaken.size()-1);
        pointsOfNoReturn.add(currentPosition);
        if(pathTaken.isEmpty()){
            currentPosition = maze.getStartPoint();
        }
        else{
            currentPosition = pathTaken.get(pathTaken.size()-1);
        }
        getPossibleMoves();
        walkToCoffee();
    }

    private boolean isAPointOfNoReturn(Point point){
        for(Point pointOfNoReturn : pointsOfNoReturn){
            if(point.equals(pointOfNoReturn)){
                return true;
            }
        }
        return false;
    }

}
