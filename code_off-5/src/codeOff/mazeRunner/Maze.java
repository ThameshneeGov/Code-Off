package codeOff.mazeRunner;

import codeOff.mazeRunner.mazeObjects.*;

import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thameshnee.govender on 2016/03/18.
 */
public class Maze {

    private int dimension;
    private int row = 0;
    private int col = 0;
    private List<List<MazeObject>> mazeObjects;
    private Point startPoint = null;
    private Point coffeePosition = null;

    public Maze(int dimension){
        this.dimension = dimension;
        this.mazeObjects =  new ArrayList<List<MazeObject>>();
        this.mazeObjects.add(new ArrayList<MazeObject>());
    }

    public int getDimension(){
        return this.dimension;
    }

    public Point getStartPoint(){
        return this.startPoint;
    }

    public Point getCoffeePosition(){
        return this.coffeePosition;
    }

    public void addObject(char object){

        switch(object){
            case '#': mazeObjects.get(row).add(new Wall());
                break;
            case '@': mazeObjects.get(row).add(new StartPoint());
                if(startPoint == null){
                    startPoint = new Point(row, mazeObjects.get(row).size() - 1);
                }
                break;
            case 'U': mazeObjects.get(row).add(new Coffee());
                if(coffeePosition == null){
                    coffeePosition = new Point(row, mazeObjects.get(row).size() - 1);
                }
                break;
            case ' ' : mazeObjects.get(row).add(new EmptySpace());
                break;
            case '.' : mazeObjects.get(row).add(new Path());
                break;
            default:
                System.out.println("You messed up...just decaf for you");
                break;
        }
        if(++col == this.dimension){
            nextLine();
        }
    }

    private void nextLine(){
        this.mazeObjects.add(new ArrayList<MazeObject>());
        col = 0;
        row++;
    }

    public void outputMaze(){
        try{
            File outputFile = new File("output.txt");
            PrintWriter printWriter = new PrintWriter(outputFile);

            for(List<MazeObject> row : mazeObjects){
                for(MazeObject col : row){
                    printWriter.write(col.getCharacter());
                }
                printWriter.println();
            }
            printWriter.close();
        }
        catch(Exception ex){
            System.err.println("The maze blew up :/");
        }
    }

    public void walkHere(Point move) throws InvalidMoveException{
        if(!(mazeObjects.get(move.x).get(move.y) instanceof EmptySpace)){
            throw new InvalidMoveException("You shall not pass!");
        }
        mazeObjects.get(move.x).remove(move.y);
        mazeObjects.get(move.x).add(move.y, new Path());
    }

    public void unwalkHere(Point move){
        if(mazeObjects.get(move.x).get(move.y) instanceof Path){
            mazeObjects.get(move.x).remove(move.y);
            mazeObjects.get(move.x).add(move.y, new EmptySpace());
        }
    }
}
