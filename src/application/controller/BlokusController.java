package application.controller;

import application.Main;
import application.model.BlokusColor;
import application.model.Board;
import application.model.CellSprite;
import application.model.ChosenPieceGrid;
import application.model.Panel;
import application.model.Player;
import application.model.Shape;
import java.io.IOException;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Biggest controller of the game
 */
public class BlokusController extends Scene {

    public int currentColourId;
    public final BlokusColor[] playerColours = {BlokusColor.Blue, BlokusColor.Yellow, BlokusColor.Red, BlokusColor.Green};
    public Player currentPlayer;
    private final ArrayList<Player> players = new ArrayList<>();

    public ChosenPieceGrid piecePreparerM = null;
    public Board boardM = null;

    public Panel currentPanel;
    public Label currentPlayerString;
    public final Panel[] panels;
    private final Pane[] panelHeads;
    private final Pane[] panelBorders;
    private final Pane[] panelAll;
    private final Label[] labelScore;
    private final Label[] labelName;
    private int turn = -1;
    private final boolean[] skip = {false, false, false, false};
    private final Group realRoot;
    private final Main parent;

    /**
     * Creates a new Game, which is a Scene containing all the required graphics
     * to play Blokus
     *
     * @param realRoot the Group to add things too
     * @param width for super()
     * @param height for super()
     * @param parent to access functions from Blokus (class)
     */
    public BlokusController(Group realRoot, double width, double height, Main parent) {
        super(realRoot, width, height, Color.WHITE);
        this.parent = parent;
        Group root = new Group();
        realRoot.getChildren().add(root);
        this.realRoot = realRoot;

        /* Set background image */
        final ImageView imv1 = new ImageView();
        final Image image3 = new Image(Main.class.getResourceAsStream("resources/blokusbg.png"));
        imv1.setImage(image3);
        imv1.setLayoutX(0);
        imv1.setLayoutY(0);
        imv1.setFitWidth(840);
        imv1.setFitHeight(700);
        root.getChildren().add(imv1);

        // BlokusController boardM
        Pane boardPane = new Pane();
        boardPane.setLayoutX(150);
        boardPane.setLayoutY(15);
        boardPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        boardPane.setMinSize(540, 540);
        boardM = new Board(20, 20, 26, BlokusColor.Empty, this);
        GridPane boardV = boardM.getGridBoard().getGridpane();
        boardV.setLayoutX(10);
        boardV.setLayoutY(10);
        boardV.setMinSize(520, 520);
        boardPane.getChildren().add(boardV);
        root.getChildren().add(boardPane);

        /* MENUBAR */
        Pane menubar = new Pane();
        menubar.setMinSize(840, 120);
        menubar.setLayoutY(570);
        menubar.setLayoutX(0);
        menubar.setStyle("-fx-background-color: rgba(0,0,0,0.8);");

        // Shows which piece is picked
        Pane prepPane = new Pane();
        prepPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        prepPane.setMinSize(120, 120);
        piecePreparerM = new ChosenPieceGrid(5, 5, 20, BlokusColor.Empty, this);
        GridPane piecePreparerV = piecePreparerM.getGridPreview().getGridpane();
        piecePreparerV.setLayoutX(10);
        piecePreparerV.setLayoutY(10);
        prepPane.getChildren().add(piecePreparerV);
        menubar.getChildren().add(prepPane);

        // Shows which player's turn it is
        currentPlayerString = new Label();
        currentPlayerString.setLayoutX(140);
        currentPlayerString.setLayoutY(30);
        currentPlayerString.setTextFill(Color.WHITE);
        currentPlayerString.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
        menubar.getChildren().add(currentPlayerString);

        // Buttons
        Button surrendBtn = new Button("Surrender");
        surrendBtn.setOnAction(e -> {
            currentPlayer.surrender(this);
        });
        surrendBtn.setMinSize(80, 40);
        surrendBtn.setLayoutX(240);
        surrendBtn.setLayoutY(70);
        menubar.getChildren().add(surrendBtn);

        Button instrBtn = new Button("Instructions");
        instrBtn.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/view/BlokusRules.fxml"));
                Parent instructions = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(instructions));
                stage.setTitle("Blokus: Instructions");
                stage.show();

            } catch (IOException ex) {
                System.out.println(ex);
            }

        });
        instrBtn.setMinSize(80, 40);
        instrBtn.setLayoutX(385);
        instrBtn.setLayoutY(70);
        menubar.getChildren().add(instrBtn);

        Button quitBtn = new Button("Quit");
        quitBtn.setOnAction(e -> {
            parent.toLibrary();
        });
        quitBtn.setMinSize(80, 40);
        quitBtn.setLayoutX(667);
        quitBtn.setLayoutY(70);
        menubar.getChildren().add(quitBtn);
        root.getChildren().add(menubar);

        /* THE BLUE PANEL */
        Pane blue = new Pane();
        blue.setLayoutX(10);
        blue.setLayoutY(10);
        blue.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        blue.setMinSize(130, 270);
        Pane blueHead = new Pane();
        blueHead.setMinSize(130, 30);
        blueHead.setMaxSize(130, 30);
        blueHead.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        Label blueLabel = new Label("00");
        blueLabel.setTextFill(Color.valueOf("rgba(255,255,255,0.8)"));
        blueLabel.setLayoutX(100);
        blueLabel.setLayoutY(12);

        Label blueName = new Label("Player 1");
        blueName.setTextFill(Color.valueOf("rgba(255,255,255,0.4)"));
        blueName.setLayoutX(10);
        blueName.setLayoutY(12);
        blueHead.getChildren().add(blueLabel);
        blueHead.getChildren().add(blueName);

        Pane bluePane = new Pane();
        bluePane.setLayoutX(0);
        bluePane.setLayoutY(30);
        bluePane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        bluePane.setMinSize(130, 240);
        Panel bluePanelM = new Panel(20, 10, 11, BlokusColor.Blue, this);
        GridPane bluePanelV = bluePanelM.getGridPanel().getGridpane();
        bluePanelV.setLayoutX(10);
        bluePanelV.setLayoutY(10);
        bluePanelV.setMinSize(110, 220);
        bluePane.getChildren().addAll(bluePanelV);
        blue.getChildren().add(blueHead);
        blue.getChildren().add(bluePane);
        root.getChildren().add(blue);


        /* THE YELLOW PANEL */
        Pane yellow = new Pane();
        yellow.setLayoutX(700);
        yellow.setLayoutY(10);
        yellow.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        yellow.setMinSize(130, 270);
        Pane yellowHead = new Pane();
        yellowHead.setMinSize(130, 30);
        yellowHead.setMaxSize(130, 30);
        yellowHead.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        Label yellowLabel = new Label("00");
        yellowLabel.setTextFill(Color.valueOf("rgba(255,255,255,0.8)"));
        yellowLabel.setLayoutX(100);
        yellowLabel.setLayoutY(12);

        Label yellowName = new Label("Player 2");
        yellowName.setTextFill(Color.valueOf("rgba(255,255,255,0.4)"));
        yellowName.setLayoutX(10);
        yellowName.setLayoutY(12);
        yellowHead.getChildren().add(yellowLabel);
        yellowHead.getChildren().add(yellowName);

        Pane yellowPane = new Pane();
        yellowPane.setLayoutX(0);
        yellowPane.setLayoutY(30);
        yellowPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        yellowPane.setMinSize(130, 240);
        Panel yellowPanelM = new Panel(20, 10, 11, BlokusColor.Yellow, this);
        GridPane yellowPanelV = yellowPanelM.getGridPanel().getGridpane();
        yellowPanelV.setLayoutX(10);
        yellowPanelV.setLayoutY(10);
        yellowPanelV.setMinSize(110, 220);
        yellowPane.getChildren().add(yellowPanelV);
        yellow.getChildren().add(yellowHead);
        yellow.getChildren().add(yellowPane);
        root.getChildren().add(yellow);


        /* THE RED PANEL */
        Pane red = new Pane();
        red.setLayoutX(700);
        red.setLayoutY(290);
        red.setMinSize(130, 270);
        Pane redHead = new Pane();
        redHead.setMinSize(130, 30);
        redHead.setMaxSize(130, 30);
        redHead.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        Label redLabel = new Label("00");
        redLabel.setTextFill(Color.valueOf("rgba(255,255,255,0.8)"));
        redLabel.setLayoutX(100);
        redLabel.setLayoutY(12);

        Label redName = new Label("Player 3");
        redName.setTextFill(Color.valueOf("rgba(255,255,255,0.4)"));
        redName.setLayoutX(10);
        redName.setLayoutY(12);
        redHead.getChildren().add(redLabel);
        redHead.getChildren().add(redName);

        Pane redPane = new Pane();
        redPane.setLayoutX(0);
        redPane.setLayoutY(30);
        redPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        redPane.setMinSize(130, 240);
        Panel redPanelM = new Panel(20, 10, 11, BlokusColor.Red, this);
        GridPane redPanelV = redPanelM.getGridPanel().getGridpane();
        redPanelV.setLayoutX(10);
        redPanelV.setLayoutY(10);
        redPanelV.setMinSize(110, 220);
        redPane.getChildren().addAll(redPanelV);
        red.getChildren().add(redHead);
        red.getChildren().add(redPane);
        root.getChildren().add(red);


        /* THE GREEN PANEL */
        Pane green = new Pane();
        green.setLayoutX(10);
        green.setLayoutY(290);
        green.setMinSize(130, 270);
        Pane greenHead = new Pane();
        greenHead.setMinSize(130, 30);
        greenHead.setMaxSize(130, 30);
        greenHead.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        Label greenLabel = new Label("00");
        greenLabel.setTextFill(Color.valueOf("rgba(255,255,255,0.8)"));
        greenLabel.setLayoutX(100);
        greenLabel.setLayoutY(12);

        Label greenName = new Label("Player 4");
        greenName.setTextFill(Color.valueOf("rgba(255,255,255,0.4)"));
        greenName.setLayoutX(10);
        greenName.setLayoutY(12);
        greenHead.getChildren().add(greenLabel);
        greenHead.getChildren().add(greenName);

        Pane greenPane = new Pane();
        greenPane.setLayoutX(0);
        greenPane.setLayoutY(30);
        greenPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        greenPane.setMinSize(130, 240);
        Panel greenPanelM = new Panel(20, 10, 11, BlokusColor.Green, this);
        GridPane greenPanelV = greenPanelM.getGridPanel().getGridpane();
        greenPanelV.setLayoutX(10);
        greenPanelV.setLayoutY(10);
        greenPanelV.setMinSize(110, 220);
        greenPane.getChildren().add(greenPanelV);
        green.getChildren().add(greenHead);
        green.getChildren().add(greenPane);
        root.getChildren().add(green);

        panels = new Panel[]{bluePanelM, yellowPanelM, redPanelM, greenPanelM};
        panelHeads = new Pane[]{blueHead, yellowHead, redHead, greenHead};
        panelBorders = new Pane[]{bluePane, yellowPane, redPane, greenPane};
        panelAll = new Pane[]{blue, yellow, red, green};
        labelScore = new Label[]{blueLabel, yellowLabel, redLabel, greenLabel};
        labelName = new Label[]{blueName, yellowName, redName, greenName};

        for (int i = 0; i < 4; i++) {
            labelScore[i].setVisible(false);
            labelName[i].setVisible(false);
        }

    }

    /**
     * Starts a new game, with 4 human players and timeline
     */
    public void start() {
        for (int i = 0; i < 4; i++) {
            players.add(new Player(this));
            labelScore[i].setVisible(true);
            labelName[i].setVisible(true);
        }

        currentColourId = 3; // When we transition go, it will start with player 0

        boardM.setActive(true);

        // BEGIN!
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(100),
                ae -> transitionMove()));
        timeline.play();
    }

    /**
     * Used to handle passes
     *
     * @param string
     */
    public void makeMove(String string) {
        if (string.equals("")) {
            // DO nothing
        } else if (string == ".") {
            boardM.placePiece(".");
            skip[currentColourId] = true;

            transitionMove();
        } else {
            boardM.placePiece(string);

            int pieceChar = string.charAt(0) - 'A';
            Shape shape = Shape.values()[pieceChar];
            panels[currentColourId].removePiece(shape);

            transitionMove();
        }
    }

    private void updatePanels(int currentColourId) {

        String[] borders = new String[]{"rgba(24,88,196,0.6)", "rgba(237,197,0,0.6)", "rgba(175,2,16,0.6)", "rgba(39,136,37,0.6)"};
        for (int i = 0; i < panels.length; i++) {
            panelBorders[i].setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
            panelHeads[i].setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
            panelAll[i].setStyle("-fx-background-color: rgba(0,0,0,0);-fx-background-insets: 0;");
            labelName[i].setTextFill(Paint.valueOf("rgba(255,255,255,0.4)"));
        }
        panelBorders[currentColourId].setStyle("-fx-background-color: " + borders[currentColourId] + ", #ffffff;  -fx-background-insets: 0,8;");
        panelHeads[currentColourId].setStyle("-fx-background-color: " + borders[currentColourId]);
        panelAll[currentColourId].setStyle("-fx-background-color: rgba(0,0,0,0.6), rgba(255,255,255,0.2);-fx-background-insets: 0, 2;");
        labelName[currentColourId].setTextFill(Paint.valueOf("#ffffff"));

        int[] score = boardM.currentScore();
        for (int i = 0; i < players.size(); i++) {
            String iscore = (score[i]) + "";
            labelScore[i].setText(iscore);
        }
    }

    private Player nextPlayer() {
        currentColourId = currentColourId % players.size();
        return players.get(currentColourId % players.size());
    }

    /**
     * Run between two goes - to set and unset panels and the piece-preparer, to
     * check for game ends, etc...
     */
    private void transitionMove() {

        //BEFORE (UNSET)
        piecePreparerM.setActive(false);
        panels[currentColourId].setActive(false);
        panels[currentColourId].temporary = null;
        piecePreparerM.removePiece();

        currentColourId = (currentColourId + 1) % 4;
        assert (currentColourId == boardM.getCurrentTurn());
        // Check for the end of the game
        if (skip[0] && skip[1] && skip[2] && skip[3]) {
            endGame();
            return;
        }

        turn++;
        currentPlayer = nextPlayer();

        // AFTER (SET)
        if (turn < 4) {
            CellSprite corner = new CellSprite(boardM.getXsize(), boardM.getXsize(), BlokusColor.values()[turn] + "Corner", boardM);
            boardM.getGridBoard().getGridpane().add(corner, (turn == 0 || turn == 3) ? 0 : 19, (turn == 0 || turn == 1) ? 0 : 19);
        }
        updatePanels(currentColourId);

        currentPanel = panels[currentColourId];
        currentPlayerString.setText("It's " + labelName[currentColourId].getText() + "'s turn !");

        if (skip[currentColourId]) {
            makeMove(".");
        } else if (panels[currentColourId].activeShapes.isEmpty()) {

            currentPlayer.surrender(this);

        } else {
            final CountDownLatch latch = new CountDownLatch(1);
            final String[] move = new String[]{"."};
            Thread t = new Thread(new Runnable() {
                public void run() {
                    move[0] = currentPlayer.isPlaying(boardM.toString());
                    latch.countDown();
                }
            });
            t.start();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.interrupt();

            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(10000),
                    ae -> makeMove(move[0])));
            timeline.play();
        }
    }

    /**
     * Shows the game outcome. Is called only when the game ends.
     */
    private void endGame() {
        int[] score = boardM.currentScore();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/application/view/End.fxml"));
            Pane end = (Pane) loader.load();

            EndController controller = loader.getController();
            controller.setMain(this.parent);

            controller.endGame(score, playerColours);

            realRoot.getChildren().add(end);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
