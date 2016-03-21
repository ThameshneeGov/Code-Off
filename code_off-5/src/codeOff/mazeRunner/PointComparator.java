package codeOff.mazeRunner;

import java.awt.*;
import java.util.Comparator;

/**
 * Created by thameshnee.govender on 2016/03/21.
 */
public class PointComparator implements Comparator<Point> {

    private Point coffeePosition;

    public PointComparator(Point coffeePosition){
        this.coffeePosition = coffeePosition;
    }

    @Override
    public int compare(Point o1, Point o2) {
        double point1Distance = Math.sqrt(Math.pow(o1.y - coffeePosition.y, 2) + Math.pow(o1.x - coffeePosition.x, 2));
        double point2Distance = Math.sqrt(Math.pow(o2.y - coffeePosition.y, 2) + Math.pow(o2.x - coffeePosition.x, 2));
        if(point1Distance < point2Distance){
            return -1;
        }
        if(point1Distance > point2Distance){
            return 1;
        }
        return 0;
    }
}
