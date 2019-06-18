package Objects.Fruits;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

public class Pineapple extends Fruit{
    public Pineapple(){
        setPoint();
        setImage();
        setPieceLeft();
        setPieceRight();
    }

    private void setImage(){
        Image image=new Image("Contents/Images/pineapple.png");
        this.setImage(image);
    }

    private void setPoint(){
        point=8;
    }

    private void setPieceLeft(){
        pieceLeft=new Image("Contents/Images/pineapplepieceleft.png");
    }

    private void setPieceRight(){
        pieceRight=new Image("Contents/Images/pineapplepieceright.png");
    }

    @Override
    public Pane slice(double X, double Y) {
        Pane pane=super.slice(X,Y);
        circle.setFill(new ImagePattern(new Image("Contents/Images/pineapplesplash.png")));
        return pane;
    }
}
