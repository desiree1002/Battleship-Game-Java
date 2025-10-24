/*
Written by:	Maeve Carr
Date:
Desc: 
Filename:
*/

/////NO COMMENTS - FIGURE IT OUT


import java.util.*;

public class BattleshipsGame
{
   //instance fields
   private BattleshipCell[][] grid; 
   private int lives;
   private int hits; 
   private int noOfShips; 
  
	
   //constructor - default no parameters
   public BattleshipsGame() {
      grid = new BattleshipCell[4][4]; 
   	
      initGrid();  
      lives = 7; 
      setNoOfShips(6); 
   }
   
   //constructor - w/ parameters
   public BattleshipsGame(int livesIn, int shipsIn) {
      grid = new BattleshipCell[4][4];
   	
      initGrid();   
      lives = livesIn;
      setNoOfShips(shipsIn);
   }
   
   public void initGrid()  {
      for(int r = 0; r < 4; r++)
         for(int c = 0; c < 4; c++)
            grid[r][c] = new BattleshipCell();
   }
      
	//this is only for testing
   public void showGrid() {
      for(int r = 0; r < 4; r++)
      {
         for(int c = 0; c < 4; c++)
            System.out.print(grid[r][c] +" ");
         System.out.println();
      }
   }
	
   //
	public int getNoOfShips()
   {
      return noOfShips;
   }
   
   //
   //get the number of ships that are not hit yet
   public int getShipsRemaining() 
   {
      int remainingShips = 0;
      for (int r = 0; r < 4; r++) 
      {
         for (int c = 0; c < 4; c++) 
         {
            if (grid[r][c].isShip() && !grid[r][c].isHit()) {
               remainingShips++;
            }
         }
      }
      
      return remainingShips;
   }
   
   public void setNoOfShips(int noOfShips) {
      Random noGen = new Random();
      int count = 0;	
      do
      {
         int r = noGen.nextInt(4);
         int c = noGen.nextInt(4);
         if(!checkIfShip(r,  c))   
         {
            grid[r][c].setToShip(); 
            count++; 
         }
      }while(count < noOfShips);
   }
	
   public boolean checkIfShip(int r, int c)
   {
      return grid[r][c].isShip(); 
   }
	
   public int getLives()
   {
      return lives;
   }
	
   public int getHits()
   {
      return hits;
   }	
	
   public String shoot(int r, int c)  
   {												
      String s; 
      if(grid[r][c].isHit())
         s = "Already chosen";  
      else
      {
         if(grid[r][c].isShip())
         {
            s = "HIT - ship sunk!";
            hits++;
         }
         else 
         {
            s = "Miss!";
            lives--;
         }   
         grid[r][c].setToHit();
      }
      
      // Check if the game is over
      if (lives <= 0 || getShipsRemaining() == 0) 
      {
           s += "\nGame Over";       
      }      
      return s;		
   }
   
   //returns 2d array of the games grid
   public BattleshipCell[][] getGrid() 
   {
    return grid;
   }
   
   
}//end class



