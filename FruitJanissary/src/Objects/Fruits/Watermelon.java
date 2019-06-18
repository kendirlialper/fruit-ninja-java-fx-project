package Objects.Fruits;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
public class Watermelon extends Fruit {

    public Watermelon(){
        setPoint();
        setImage();
        setPieceLeft();
        setPieceRight();
    }

    private void setImage(){
        Image image=new Image("Contents/Images/watermelon.png");
        this.setImage(image);
    }

    private void setPoint(){
        point=5;
    }

    private void setPieceLeft(){
        pieceLeft=new Image("Contents/Images/watermelonpieceleft.png");
    }

    private void setPieceRight(){
        pieceRight=new Image("Contents/Images/watermelonpieceright.png");
    }

    @Override
    public Pane slice(double X, double Y) {
        Pane pane=super.slice(X,Y);
        circle.setFill(new ImagePattern(new Image("Contents/Images/watermelonsplash.png")));

        return pane;

    }
}
