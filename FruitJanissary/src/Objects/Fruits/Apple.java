package Objects.Fruits;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

public class Apple extends Fruit {

    public Apple(){
        setPoint();
        setImage();
        setPieceLeft();
        setPieceRight();
    }

    private void setImage(){
            Image image=new Image("Contents/Images/apple.png");
          this.setImage(image);
    }

    private void setPoint(){
        point=6;
    }

    private void setPieceLeft(){
        pieceLeft=new Image("Contents/Images/applepieceleft.png");
    }

    private void setPieceRight(){
        pieceRight=new Image("Contents/Images/applepieceright.png");
    }

    @Override
    public Pane slice(double X, double Y) {
        Pane pane=super.slice(X,Y);
        circle.setFill(new ImagePattern(new Image("Contents/Images/applesplash.png")));

        return pane;
    }
}

