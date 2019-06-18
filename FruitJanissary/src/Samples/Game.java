package Samples;

import Objects.Bombs.Bomb;
import Objects.Fruits.*;
import Objects.Throw;
import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class Game {
    static String userName;
    private IntegerProperty fallenFruit = new SimpleIntegerProperty(0);
    private IntegerProperty score=new SimpleIntegerProperty(0);
    private BorderPane root=new BorderPane();
    private Pane pausePane =new Pane();
    private int time =0;
    private AudioClip splashSound =new AudioClip(this.getClass().getResource("/Contents/Sounds/splash.wav").toString());
    private AudioClip gameMusic =new AudioClip(this.getClass().getResource("/Contents/Sounds/gameSound.wav").toString());
    private AudioClip unPauseSound =new AudioClip(this.getClass().getResource("/Contents/Sounds/unPause.wav").toString());
    private AudioClip pauseSound =new AudioClip(this.getClass().getResource("/Contents/Sounds/pause.wav").toString());
    private AudioClip pauseMusic =new AudioClip(this.getClass().getResource("/Contents/Sounds/pauseMusic.wav").toString());
    private ImageView background=new ImageView(new Image("Contents/Images/bg.png"));
    private final ImageView x=new ImageView();
    private final ImageView play=new ImageView(new Image("Contents/Images/play.png"));
    private final ImageView mute=new ImageView(new Image("Contents/Images/mute.png"));
    private final ImageView unmute=new ImageView(new Image("Contents/Images/unmute.png"));
    private final ImageView pause=new ImageView(new Image("Contents/Images/pause.png"));
    private VBox vBox=new VBox();
    private VBox vLeftBox=new VBox();
    private VBox vRightBox=new VBox();
    private Button restartButton=new Button("Play Again");
    private Button mainMenuButton=new Button("Main Menu");
    private Button exitButton=new Button("Quit");
    private Label overGameScoreLabel=new Label();
    private Label scoreLabel =new Label("SCORE : 0");
    private Label timerLabel=new Label("Time : "+time+" ");
    private Group group=new Group();
    private Timeline createRandom;
    private Timeline timer;
    private double volume=0.3;

   Game(){

        restartButton.setStyle("-fx-background-color:  green;-fx-font-weight: bold;-fx-text-fill: white");
        mainMenuButton.setStyle("-fx-background-color:  #4da6ff;-fx-font-weight: bold;-fx-text-fill: white");
        exitButton.setStyle("-fx-background-color:  red;-fx-font-weight: bold;-fx-text-fill: white");

        pause.setFitWidth(30);
        pause.setFitHeight(30);
        play.setFitWidth(80);
        play.setFitHeight(80);

        exitButton.setOnAction(e->{
            Utilities utilities=new Utilities();
            utilities.quit(e);
        });

        mainMenuButton.setOnAction(e->{
            try {
                Utilities utilities=new Utilities();
                utilities.goMenu(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        restartButton.setOnAction(e->{
            gameMusic.setVolume(volume);
            gameMusic.play();
            vLeftBox.setVisible(true);
            timerLabel.setText("Time : 0 ");
            timerLabel.setVisible(true);
            timer.play();
            x.setVisible(false);
            fallenFruit.set(0);
            score.set(0);
            scoreLabel.setVisible(true);
            scoreLabel.setText("SCORE : 0");
            createRandom.play();
            vBox.getChildren().removeAll(restartButton,overGameScoreLabel,exitButton);
            root.getChildren().removeAll(vBox);
            pause.setVisible(true);
        });

        fallenFruit.addListener((observableValue, oldValue, newValue) ->
        {System.out.println("oldValue:"+ oldValue + ", newValue = " + newValue);
            if(newValue.equals(1)){
                x.setImage(new Image("Contents/Images/x.png"));
                x.setFitHeight(30);
                x.setFitWidth(30);
                x.setVisible(true);
            }
            if(newValue.equals(2)){
                x.setImage(new Image("Contents/Images/xx.png"));
                x.setFitHeight(30);
                x.setFitWidth(60);

            }
            if(newValue.equals(3)){
                x.setImage(new Image("Contents/Images/xxx.png"));
                x.setFitHeight(30);
                x.setFitWidth(90);
                endGame();
            }
        });

        Timeline fallenControl=new Timeline(new KeyFrame(Duration.millis(10),e->{
            for (Node node:group.getChildren()){
                Throw aThrow=(Throw) node;
                if(aThrow.getY()>615&&aThrow.getX()>700){
                    if(aThrow.type.equals("fruit")){
                        splashSound.setVolume(0.3);
                        splashSound.play();
                        Fruit fruit=(Fruit)aThrow;
                        group.getChildren().remove(fruit);
                        fallenFruit.set(fallenFruit.get()+1);
                        break;
                    }
                    if(aThrow.type.equals("bomb")){
                        Bomb bomb=(Bomb) aThrow;
                        group.getChildren().remove(bomb);
                        break;
                    }
                }
            }
        }));
        fallenControl.setCycleCount(-1);
        fallenControl.play();

        group.setOnMouseDragOver(e->{
            System.out.println("Throw Sliced");
            int counter=0;
            for(Node node:group.getChildren()){
                Throw aThrow=(Throw) node;
                if(aThrow.type.equals("fruit")){
                    Fruit fruit=(Fruit)aThrow;
                    if(fruit.contains(e.getX(),e.getY())){
                        score.set(score.get()+fruit.point);
                        scoreLabel.setText("SCORE : "+score.get());
                        Pane sliceFruitEffect =fruit.slice(e.getX(),e.getY());
                        group.getChildren().remove(counter);
                        root.getChildren().add(sliceFruitEffect);
                        FadeTransition ft=new FadeTransition(Duration.millis(250),sliceFruitEffect);
                        ft.setFromValue(1.0);
                        ft.setToValue(0.4);
                        ft.setCycleCount(1);
                        ft.setAutoReverse(false);
                        ft.play();
                        ft.setOnFinished((ActionEvent event) -> {
                            root.getChildren().remove(sliceFruitEffect); });

                        System.out.println("Fruit Sliced");
                        ImageView piece1=new ImageView(fruit.pieceLeft);
                        piece1.setFitWidth(30);
                        piece1.setFitHeight(60);
                        ImageView piece2=new ImageView(fruit.pieceRight);
                        piece2.setFitWidth(30);
                        piece2.setFitHeight(60);
                        root.getChildren().addAll(piece1,piece2);
                        Timeline rotate=new Timeline(new KeyFrame(Duration.millis(50),event3->{
                            piece1.setRotate(piece1.getRotate()-4);
                            piece2.setRotate(piece2.getRotate()+4);
                        }));
                        rotate.setCycleCount(40);
                        rotate.setAutoReverse(false);
                        rotate.play();
                        PathTransition pt= new PathTransition();
                        pt.setNode(piece1);
                        pt.setPath(new Arc(e.getX()-30,620,50,600-e.getY(),90,90));
                        pt.setCycleCount(1);
                        pt.setDuration(Duration.millis(600));
                        pt.play();
                        pt.setOnFinished(event1->{
                            root.getChildren().remove(piece1);
                        });

                        PathTransition pt1= new PathTransition();
                        pt1.setNode(piece2);
                        pt1.setPath(new Arc(e.getX()+30,620,50,600-e.getY(),90,-90));
                        pt1.setCycleCount(1);
                        pt1.setDuration(Duration.millis(600));
                        pt1.play();

                        pt1.setOnFinished(event2->{
                            root.getChildren().remove(piece2);
                        });
                        break;
                    }
                }
                if(aThrow.type.equals("bomb")){
                    Bomb bomb=(Bomb)aThrow;

                    if(bomb.contains(e.getX(),e.getY())){
                        endGame();
                        System.out.println("Bomb Exploded");
                        group.getChildren().remove(bomb);
                        Pane bombExplodeEffect= bomb.slice(e.getX(),e.getY());
                        root.getChildren().add(bombExplodeEffect);
                        FadeTransition ft=new FadeTransition(Duration.millis(600),bombExplodeEffect);
                        ft.setFromValue(1.0);
                        ft.setToValue(0.1);
                        ft.setCycleCount(1);
                        ft.setAutoReverse(false);
                        ft.play();
                        ft.setOnFinished((ActionEvent event) -> {
                            root.getChildren().remove(bombExplodeEffect);
                        });
                        break;
                    }
                }
                counter++;
            }
        });

        pause.setOnMouseClicked(e->{
            gameMusic.setVolume(volume);
            gameMusic.stop();
            timer.pause();
            System.out.println("clicked");
            pauseSound.play();
            pauseGame();
            play.setX(470);
            play.setY(260);
            Rectangle r1=new Rectangle(0,0,1000,600);
            r1.setFill(Color.WHITE);
            r1.setOpacity(0.4);
            ImageView janissaryImage=new ImageView( new Image("Contents/Images/janissary.png"));
            janissaryImage.setFitWidth(200);
            janissaryImage.setFitHeight(500);
            janissaryImage.setX(70);
            janissaryImage.setY(40);
            pausePane.getChildren().addAll(r1,play,janissaryImage);
            root.getChildren().add(pausePane);
            for(Node node:group.getChildren()){
                Throw aThrow=(Throw)node;
                aThrow.pause();
            }
            pauseMusic.setCycleCount(-1);
            pauseMusic.setVolume(volume);
            pauseMusic.play();

        });
        play.setOnMouseDragOver(e->{
            gameMusic.setVolume(volume);
            gameMusic.play();
            timer.play();
            pausePane.getChildren().removeAll(pausePane.getChildren());
            root.getChildren().remove(pausePane);
            pauseMusic.stop();
            for(Node node:group.getChildren()){
                Throw aThrow=(Throw)node;
                aThrow.play();
                createRandom.play();
                unPauseSound.play();
            }
        });
        root.setOnMouseDragOver(e-> {
               Line line= new Line(e.getX(),e.getY(),e.getX(),e.getY());
               line.setStroke(Color.BLUE);
               line.setStrokeWidth(2);
                root.getChildren().add(line);
                FadeTransition ft=new FadeTransition();
                ft.setNode(line);
                ft.setCycleCount(1);
                ft.setDuration(Duration.millis(300));
                ft.play();
                ft.setOnFinished(event->{
                    root.getChildren().remove(line);
                });
        });

        mute.setOnMouseClicked(e->{
            vLeftBox.getChildren().remove(mute);
            vLeftBox.getChildren().add(unmute);
            volume=0.0;
            gameMusic.stop();
            pauseMusic.setVolume(volume);
          gameMusic.setVolume(volume);
        });

        unmute.setOnMouseClicked(e->{
            vLeftBox.getChildren().remove(unmute);
            vLeftBox.getChildren().add(mute);
            volume=0.3;
            gameMusic.setVolume(volume);
            pauseMusic.setVolume(volume);
            gameMusic.play();
        });
    }

    private Throw createRandom() {
        int randomNumb = (int) (Math.random() * 6);
        switch (randomNumb) {
            case 0: {
                return new Watermelon();
            }
            case 1: {
                return new Pear();
            }
            case 2: {
                return new Peach();
            }
            case 3: {
                return new Pineapple();
            }
            case 4: {
                return new Apple();
            }
            default:{
                System.out.println("Bomb");
                return new Bomb();
            }
        }
    }

    public BorderPane startGame(){
        root.setOnDragDetected(e->root.startFullDrag());
        gameMusic.setCycleCount(-1);
        gameMusic.setVolume(volume);
        gameMusic.play();
        root.getChildren().add(background);
        timerLabel.setFont(Font.font(20));
        timerLabel.setTextFill(Color.GREEN);
        timerLabel.setPadding(new Insets(0,0,5,0));
       timer=new Timeline(new KeyFrame(Duration.millis(1000),e->{
           time++;
           timerLabel.setText("Time : "+time+" ");
       }));
        timer.setCycleCount(-1);
        timer.play();
        root.getChildren().add(group);
        root.setLeft(vLeftBox);
        root.setRight(vRightBox);
        vLeftBox.setMinWidth(150);
        mute.setFitHeight(30);
        mute.setFitWidth(30);
        unmute.setFitHeight(30);
        unmute.setFitWidth(30);
        scoreLabel.setPadding(new Insets(0,0,5,0));
        vLeftBox.getChildren().addAll(scoreLabel,pause,mute);
        vLeftBox.setPadding(new Insets(10,10,10,10));
        vLeftBox.setSpacing(10);
        vRightBox.getChildren().addAll(timerLabel,x);
        vRightBox.setMinWidth(150);
        vRightBox.setAlignment(Pos.TOP_RIGHT);
        vRightBox.setPadding(new Insets(10,10,0,10));
        scoreLabel.setTextFill(Color.RED);
        scoreLabel.setFont(Font.font(25));
        CreateRandomAnimation();

        return root;
    }

    private void CreateRandomAnimation(){
        createRandom=new Timeline(new KeyFrame(Duration.millis(600),e-> group.getChildren().add(createRandom())));
        createRandom.setCycleCount(Timeline.INDEFINITE);
        createRandom.play();
    }

    private void pauseGame(){
        createRandom.pause();
    }

    private void endGame(){

        gameMusic.stop();
        vBox.getChildren().clear();
        createRandom.stop();
        timer.stop();
        timerLabel.setVisible(false);


        Utilities utilities=new Utilities();
        utilities.initializeDB();
        Statement statement=utilities.statement;
        DateFormat now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String date=(now.format(cal.getTime()));

        String query = "INSERT INTO game.scoretable Values('"+Game.userName+"',"+score.get()+",'"+date+"',"+time+")";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        group.getChildren().clear();
        vLeftBox.setVisible(false);
        Label  overGameTimerLabel=new Label();
        overGameTimerLabel.setTextFill(Color.BLACK);
        overGameTimerLabel.setFont(Font.font(30));
        overGameTimerLabel.setText("Your Play Time is : "+time);
        overGameTimerLabel.setStyle("-fx-font-weight: bold");
        overGameTimerLabel.setTextFill(Color.GREEN);
        overGameScoreLabel.setFont(Font.font(30));
        overGameScoreLabel.setTextFill(Color.RED);
        overGameScoreLabel.setStyle("-fx-font-weight: bold");
        String overGameScore="Your Score is : "+score.get();
        overGameScoreLabel.setText(overGameScore);
        vBox.getChildren().add(overGameScoreLabel);
        vBox.getChildren().add( overGameTimerLabel);
        vBox.getChildren().add(restartButton);
        vBox.getChildren().add(mainMenuButton);
        vBox.getChildren().add(exitButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        root.setCenter(vBox);
        time=0;
        BorderPane.setAlignment(vBox,Pos.CENTER);
    }

}

