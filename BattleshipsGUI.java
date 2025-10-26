
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.image.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.scene.input.KeyCombination;
import java.util.Optional;

public class BattleshipsGUI extends Application
{
   //variables for the game components---------------------------------------------
   
   //game grid size
   private int gridSize = 4;
   
   //2d array for buttons and images for each cell
   private Button[][] cells = new Button[gridSize][gridSize];
   private ImageView[][] imageGrid = new ImageView[gridSize][gridSize];

   private BattleshipsGame game;
   
   //components
   private Label titleLbl, livesLbl, shipsRemainingLbl, statusLbl;   
   private Button newGameBtn, giveUpBtn, quitBtn, customiseBtn, okBtn;
   private ComboBox<Integer> livesComBox;
   private ComboBox<Integer> shipsComBox;
   
   private int customLives = 7;
   private int customShips = 6;
  
   //main and popup windows
   private Stage stage; 
   private Stage newWindow;
   
   //start() method ---------------------------------------------------------------
   @Override
   public void start(Stage primaryStage)
   {
      stage = primaryStage;
      game = new BattleshipsGame(); //create new game
      
      //create layout
      BorderPane borderPane = new BorderPane();
      borderPane.setPadding(new Insets(20));
      borderPane.setTop(createLabelPanel());
      borderPane.setCenter(createBattleshipGrid());
      borderPane.setBottom(createButtonPanel());
      
      borderPane.setStyle("-fx-background-color: lightblue;"); //style background color
      
      //create scene and add style sheet 
      Scene scene = new Scene(borderPane);
      scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
      
      primaryStage.setTitle("Battleship");
      primaryStage.setScene(scene);
      primaryStage.setResizable(false);
      primaryStage.show();
   }
   
   //helper methods ------------------------------------------------------------------
   
   //create label panel at the top
   public VBox createLabelPanel()
   {
      //create labels and button
      titleLbl = new Label("BATTLESHIP");
      livesLbl = new Label("Lives: " + customLives);
      shipsRemainingLbl = new Label("Ships Remaining: " + customShips);
      statusLbl = new Label("Click a cell to fire!");
      customiseBtn = new Button("Customise");
      
      //assign ids for CSS styling
      titleLbl.setId("titleLbl");      
      livesLbl.setId("lbl");
      shipsRemainingLbl.setId("lbl");
      statusLbl.setId("statusLbl");
      customiseBtn.setId("customiseBtn");
      
      customiseBtn.setPrefSize(100, 35); 
      
      customiseBtn.setOnAction(e -> btnClick());
      
      VBox vbox = new VBox(10, titleLbl, livesLbl, shipsRemainingLbl, statusLbl, customiseBtn);
      vbox.setAlignment(Pos.CENTER);
      vbox.setPadding(new Insets(20));
      return vbox;
   }
   
   //opens the customise popup window for selecting custom lives and ships
   public void btnClick()
   {
      newWindow = new Stage();
      
      BorderPane pane = new BorderPane();
      pane.setCenter(createCustomisePane());
      pane.setPadding(new Insets(20));
      pane.setStyle("-fx-background-color: lightblue;");   
     
      //create scene and add style sheet 
      Scene scene = new Scene(pane);
   
      newWindow.setTitle("Customise");
      newWindow.setScene(scene);
      newWindow.setResizable(false);
      newWindow.show();
      
   }
   
   //creates customise panel with comboboces for lives and ships
   public VBox createCustomisePane()
   {
      Label livesLbl = new Label("Select Lives: ");
      Label shipsLbl = new Label("Select Ships: ");
      
      livesComBox = new ComboBox<>();
      livesComBox.getItems().addAll(7, 6, 5, 4, 3);
      livesComBox.setValue(7);
      livesComBox.setPromptText("7");
      
      shipsComBox = new ComboBox<>();
      shipsComBox.getItems().addAll(8, 7, 6, 5, 4, 3);
      shipsComBox.setValue(6);
      shipsComBox.setPromptText("6");
      
      okBtn = new Button("OK");
      okBtn.setOnAction(e -> okBtnClick());
      
      Button cancelBtn = new Button("Cancel");
      cancelBtn.setOnAction(e -> cancelClick());
      
      HBox livesHbox = new HBox(20, livesLbl, livesComBox);    
      HBox shipsHBox = new HBox(20, shipsLbl, shipsComBox);   
         
      VBox inputVbox = new VBox(30, livesHbox, shipsHBox);
      inputVbox.setPadding(new Insets(20));
      
      HBox btnHbox = new HBox(20, okBtn, cancelBtn);
      btnHbox.setPadding(new Insets(20));
      btnHbox.setAlignment(Pos.CENTER);
      
      VBox mainVbox = new VBox(40, inputVbox, btnHbox);
      return mainVbox;
   } 
   
   //
   public void okBtnClick()
   {
      Integer lives = livesComBox.getValue();
      Integer ships = shipsComBox.getValue();
      
      customLives = lives;
      customShips = ships;
      game = new BattleshipsGame(customLives, customShips); 
         
      updateStatusLabels();
      statusLbl.setText("Click New Game to start.");
  
      newGameBtn.setDisable(false);
   }

   //closes customise window
   public void cancelClick()
   {
      newWindow.close();
   }
   
   //create battleship grid (4x4 grid with buttons and imageViews for each cell)
   public GridPane createBattleshipGrid()
   {
      GridPane gridPane = new GridPane();
      gridPane.setAlignment(Pos.CENTER);
      gridPane.setPadding(new Insets(10));
     
      //loop through the grid and add buttons and imageViews
      for (int row = 0; row < gridSize; row++)
      {
         for (int col = 0; col < gridSize; col++)
         {
            imageGrid[row][col] = new ImageView(); //initialize ImageView for each cell
            Button btn = new Button(); //create button for each cell
            btn.setPrefSize(160, 160); 
            gridPane.add(btn, col, row); //add button to grid
            btn.getStyleClass().add("grid-button"); //add css class for styling
            
            //store button reference and set button click action
            final int r = row;
            final int c = col;
            cells[r][c] = btn; 
            btn.setOnAction(e -> handleCellClick(r, c));          
         }
      }
      return gridPane;
   }

   //handles the click event on a grid cell
   private void handleCellClick(int r, int c)
   {
      String result = game.shoot(r, c); //call shoot() to check if shot hits or misses
      updateStatusLabels(); //update labels based on result
      
      ImageView imageView = imageGrid[r][c];
      Button cell = cells[r][c];
      
      Image image = null;

      cell.getStyleClass().add("cell-button");
      
      newGameBtn.setDisable(true);
      customiseBtn.setDisable(true);
      
      //if the shot hit a ship, show ship gif
      if (result.equals("HIT - ship sunk!")) 
      {
          image = new Image("file:resources/shipwreck.gif");
          imageView.setFitWidth(140);
          imageView.setFitHeight(140);
          
          cell.setGraphic(imageView); //set image to button
          statusLbl.setText(result); //update status label        
          cell.setDisable(true); //disable button after shot
      } 
      //if shot missed, show miss image
      else if (result.equals("Miss!")) 
      {
          image = new Image("file:resources/miss2.png");
          imageView.setFitWidth(140);
          imageView.setFitHeight(140);     
              
          cell.setGraphic(imageView); //set image to button
          statusLbl.setText(result); //update status label
          cell.setDisable(true); //disable button after shot
      }
      
      imageView.setImage(image);
       
      //check if game is over (either out of lives or ships remaining)
      if (game.getLives() <= 0) 
      {
          statusLbl.setText("Game Over");
          showGameOverAlert(); //show game overalert
      } 
      else if (game.getShipsRemaining() == 0) 
      {
          statusLbl.setText("You Won");
          showGameWonAlert(); //show victory alert
      }
   }
   
   //update the game status labels (lives, ships remaining and status)
   private void updateStatusLabels()
   {
      livesLbl.setText("Lives: " + game.getLives());
      shipsRemainingLbl.setText("Ships Remaining: " + game.getShipsRemaining());    
      statusLbl.setText("Click a cell to fire!");
   }
   
   //create button panel
   public HBox createButtonPanel()
   {
      newGameBtn = new Button("New Game");
      quitBtn = new Button("Quit");
      giveUpBtn = new Button("Give Up");
      
      newGameBtn.setPrefSize(100, 50);  
      quitBtn.setPrefSize(100, 50); 
      giveUpBtn.setPrefSize(100, 50); 
      
      //set button actions 
      newGameBtn.setOnAction(e -> newGame());
      quitBtn.setOnAction(e -> quitGame());
      giveUpBtn.setOnAction(e -> giveUp());
      
      //set ids for styling
      newGameBtn.setId("newGameBtn");
      quitBtn.setId("quitBtn");
      giveUpBtn.setId("giveUpBtn");

      HBox hbox = new HBox(20, newGameBtn, giveUpBtn, quitBtn);
      hbox.setAlignment(Pos.CENTER);
      hbox.setPadding(new Insets(20));
      
      return hbox;
   }
      
   //show game over alert when player losses
   private void showGameOverAlert()
   {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Game Over");
      alert.setHeaderText(null);
      alert.setContentText("You lost! Game Over. Better luck next time brother.");
      alert.showAndWait();
      
      disableCells(); 
      revealAllShips();    
      giveUpBtn.setDisable(true); //disable give up button
      newGameBtn.setDisable(false); //enable new game button
      customiseBtn.setDisable(false);
   }
   
   //show game won alert when player wins
   private void showGameWonAlert()
   {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Victory");
      alert.setHeaderText(null);
      alert.setContentText("Yeyyyyyyyyy! You Win.");   
      alert.showAndWait();
            
      disableCells();
      revealAllShips();     
      giveUpBtn.setDisable(true);
      newGameBtn.setDisable(false);
      customiseBtn.setDisable(false);
   }

   
   //disable all cells after the game ends
   private void disableCells() 
   {
      for (int row = 0; row < gridSize; row++) 
      {
           for (int col = 0; col < gridSize; col++) 
           {
               Button cell = cells[row][col]; 
               cell.setDisable(true);  //disable button
               cell.getStyleClass().add("cell-button"); //add style
           }
       }
   }

   //reveals all ships at the end of the game
   private void revealAllShips() 
   {
      for (int row = 0; row < gridSize; row++) 
      {
         for (int col = 0; col < gridSize; col++) 
         {  
            //get the cell at current position 
            BattleshipCell cell = game.getGrid()[row][col];
            
            //check if cell contains a ship
            if (cell.isShip()) 
            {
               ImageView imageView = imageGrid[row][col];
               Image image;
               
               //use corresponding gif if hit or not
               if (cell.isHit()) 
               {
                  image = new Image("file:resources/shipwreck1.gif"); //show shipwreck
               }
               else 
               {
                  image = new Image("file:resources/ship22.gif"); //show intact ship
               }
               
               //set size and assign image to ImageView
               imageView.setFitWidth(140);
               imageView.setFitHeight(140);
               imageView.setImage(image);
               cells[row][col].setGraphic(imageView); // set image on button
            }
         }
      }       
   }

   //resets the game to start a new game
   private void newGame()
   {
      game = new BattleshipsGame(customLives, customShips);   
      updateStatusLabels();   
      newGameBtn.setDisable(true); //disable new game button during game
      
      //enable all buttons(cells) for the new game
      for (int row = 0; row < gridSize; row++) 
      {
          for (int col = 0; col < gridSize; col++) 
          {
              Button cell = cells[row][col];
              cell.setDisable(false); //enable cell button
              imageGrid[row][col].setImage(null); //clear previous images
          }
      }
      
      giveUpBtn.setDisable(false); //enable give up button      
   }  
   
   //handles give up action
   private void giveUp()
   {
      disableCells();  //disable all cells
      revealAllShips(); //reveal all ships
      statusLbl.setText("Game Over! You gave up. All ships are revealed.");      
      newGameBtn.setDisable(false); //enable new game button
      customiseBtn.setDisable(false);
   }
   
   // Quits the game
   private void quitGame() 
   {
      stage.close();
   }
}
