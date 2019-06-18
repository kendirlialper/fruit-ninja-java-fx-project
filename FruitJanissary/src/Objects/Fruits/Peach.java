package Objects.Fruits;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

public class Peach extends Fruit {

    public Peach(){
        setPoint();
        setImage();
        setPieceLeft();
        setPieceRight();
    }

    private void setImage(){
        Image image=new Image("Contents/Images/peach.png");
        this.setImage(image);
    }

    private void setPoint(){
        point=7;
    }

    private void setPieceLeft(){
        pieceLeft=new Image("Contents/Images/peachpieceleft.png");
    }

    private void setPieceRight(){
        pieceRight=new Image("Contents/Images/peachpieceright.png");
    }

    @Override
    public Pane slice(double X, double Y) {
        Pane pane=super.slice(X,Y);
        circle.setFill(new ImagePattern(new Image("Contents/Images/peachsplash.png")));
        return pane;
    }
}
