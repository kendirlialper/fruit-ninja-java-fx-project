package Objects.Fruits;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

public class Pear extends Fruit {
    public Pear(){
        setPoint();
        setImage();
        setPieceLeft();
        setPieceRight();
    }

    private void setImage(){
        Image image=new Image("Contents/Images/pear.png");
        this.setImage(image);
    }

    private void setPoint(){
        point=9;
    }

    private void setPieceLeft(){
        pieceLeft=new Image("Contents/Images/pearpieceleft.png");
    }

    private void setPieceRight(){
        pieceRight=new Image("Contents/Images/pearpieceright.png");
    }

    @Override
    public Pane slice(double X, double Y) {
        Pane pane=super.slice(X,Y);
        circle.setFill(new ImagePattern(new Image("Contents/Images/pearsplash.png")));
        return pane;
    }
}
