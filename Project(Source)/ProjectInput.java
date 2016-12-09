import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class ProjectInput extends Animation implements KeyListener {

	public static int xsize;
	public static int ysize;
	private static Sprite solution;
	private static Sprite[] mySprites = new Sprite[9];
	
	public ProjectInput() {
		super(xsize * 6 + 100, ysize * 3);
		addKeyListener(this);
		for (int i = 0; i < mySprites.length; i++){
			addSprite(mySprites[i]);
		}
		solution.setPosition(xsize * 3 + 100, 0);
		addSprite(solution);
		randomizeSprites();
		frameFinished();
	}
	
	//TODO extra: add a next button for the next puzzle
	//TODO extra: add a timer displaying the amount of time taken until the puzzle is solved
	
	
	//Sprites should never occupy the same location, rather switch locations
	public void keyPressed(KeyEvent e) {
		try{
			if(e.getKeyCode() == KeyEvent.VK_UP && mySprites[0].getYposition() - ysize >= 0){
				getSpriteAt(mySprites[0].getXposition(), mySprites[0].getYposition() - ysize).
					setPosition(mySprites[0].getXposition(),  mySprites[0].getYposition());
				mySprites[0].setPosition(mySprites[0].getXposition(), mySprites[0].getYposition() - ysize);
				frameFinished();
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN && mySprites[0].getYposition() + ysize <= ysize * 2){
				getSpriteAt(mySprites[0].getXposition(), mySprites[0].getYposition() + ysize).
					setPosition(mySprites[0].getXposition(),  mySprites[0].getYposition());
				mySprites[0].setPosition(mySprites[0].getXposition(), mySprites[0].getYposition() + ysize);
				frameFinished();
			}
			else if(e.getKeyCode() == KeyEvent.VK_LEFT && mySprites[0].getXposition() + xsize <= xsize  * 2){
				getSpriteAt(mySprites[0].getXposition() + xsize, mySprites[0].getYposition()).
					setPosition(mySprites[0].getXposition(),  mySprites[0].getYposition());
				mySprites[0].setPosition(mySprites[0].getXposition() + xsize, mySprites[0].getYposition());
				frameFinished();
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT && mySprites[0].getXposition() - xsize >= 0){
				getSpriteAt(mySprites[0].getXposition() - xsize, mySprites[0].getYposition()).
					setPosition(mySprites[0].getXposition(),  mySprites[0].getYposition());
				mySprites[0].setPosition(mySprites[0].getXposition() - xsize, mySprites[0].getYposition());
				frameFinished();
			}
			
			if (victoryCondition()){
				setBackgroundImage("victory.png");
				frameFinished();
				System.out.println("winner, winner, chicken dinner!");
			}
		}
		catch (NullPointerException f){
			//should never happen
		}		
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
		
	private Sprite getSpriteAt(int x, int y){	
		for (int i = 0; i < mySprites.length; i++){
			if (mySprites[i].getXposition() == x && mySprites[i].getYposition() == y)
				return mySprites[i];
		}	
		//should never happen
		return null;
	}
	
	private int getSpriteNumberAt(int x, int y){	
		for (int i = 1; i < mySprites.length; i++){
			if (mySprites[i].getXposition() == x && mySprites[i].getYposition() == y)
				return i;
		}	
		return 0;
	}
	
	private void randomizeSprites(){		
		//randomize the (x, y) values
		for (int i = 0; i < mySprites.length; i++){
			boolean duplicateCheck;
			do{
				duplicateCheck = false;
				int randomX = (int)(Math.random() * 10 % 3) * xsize;
				int randomY = (int)(Math.random() * 10 % 3) * ysize;
				mySprites[i].setPosition(randomX, randomY);
				for (int i2 = 0; i2 < mySprites.length; i2++){
					if (mySprites[i].getXposition() == mySprites[i2].getXposition()
							 && mySprites[i].getYposition() == mySprites[i2].getYposition()
							 && i != i2){
						duplicateCheck = true;
					}
				}
			} while (duplicateCheck);
		}
		//with the isSolvable() method it should not be possible to generate an unsolvable puzzle
		if (!isSolvable()){ //switch two horizontal values
			//take middle value and switch it with the left or right values
			//also moving the value to be switched off the board so getSpriteAt()
			//returns the right value
			try{
				if (getSpriteNumberAt(xsize, ysize) != 0){
					if (getSpriteNumberAt(xsize * 2, ysize) != 0){
						getSpriteAt(xsize * 2, ysize).setPosition(-xsize, -ysize);
						getSpriteAt(xsize, ysize).setPosition(xsize * 2, ysize);
						getSpriteAt(-xsize, -ysize).setPosition(xsize, ysize);			
					}
					else{
						getSpriteAt(0, ysize).setPosition(-xsize, -ysize);
						getSpriteAt(xsize, ysize).setPosition(0, ysize);
						getSpriteAt(-xsize, -ysize).setPosition(xsize, ysize);
					}
				}
				else{
					getSpriteAt(xsize * 2, ysize * 2).setPosition(-xsize, -ysize);
					getSpriteAt(xsize, ysize * 2).setPosition(xsize * 2, ysize * 2);
					getSpriteAt(-xsize, -ysize).setPosition(xsize, ysize * 2);					
				}
			}
			catch(NullPointerException e){
				//should never happen
			}
		}
	}
	
	public static void setCanvasSize(){
		xsize = 0;
		ysize = 0;
		//only generating two random numbers since there are only two puzzles
		//the random generation should be configured to match the number of puzzles
		//in the 'database'
		int randomPuzzle = ((int)(Math.random() * 10) % 2) + 97;
		
		for (int i = 1; i < mySprites.length; i++){
			mySprites[i] = new Sprite("Sprite" + String.valueOf(Character.toChars(randomPuzzle)) + String.valueOf(i) + ".png");
		}
		solution = new Sprite(String.valueOf(Character.toChars(randomPuzzle)) + "Sol.png");
		mySprites[0] = new Sprite("Blank_sprite.png");
		
		mySprites[0].setSize(mySprites[1].getXsize(), mySprites[1].getYsize());
		xsize = mySprites[1].getXsize();
		ysize = mySprites[1].getYsize();
	}
	
	private boolean victoryCondition(){
		if (	(mySprites[0].getXposition() == xsize * 2 && mySprites[0].getYposition() == 0)
			&&	(mySprites[1].getXposition() == 0 && mySprites[1].getYposition() == ysize * 2)
			&&	(mySprites[2].getXposition() == xsize && mySprites[2].getYposition() == ysize * 2)
			&&	(mySprites[3].getXposition() == xsize * 2 && mySprites[3].getYposition() == ysize * 2)
			&& 	(mySprites[4].getXposition() == 0 && mySprites[4].getYposition() == ysize)
			&&	(mySprites[5].getXposition() == xsize && mySprites[5].getYposition() == ysize)
			&& 	(mySprites[6].getXposition() == xsize * 2 && mySprites[6].getYposition() == ysize)
			&& 	(mySprites[7].getXposition() == 0 && mySprites[7].getYposition() == 0)
			&&	(mySprites[8].getXposition() == xsize && mySprites[8].getYposition() == 0))
			return true;
		else{
			return false;
		}
	}
	
	private int numberOfInversions(int tileNumber){
		int xPos, yPos;
		
		if (tileNumber > 5){
			yPos = ysize * 2;
		}
		else if (tileNumber < 6 && tileNumber > 2){
			yPos = ysize;
		}
		else{
			yPos = 0;
		}
		xPos = -ysize * tileNumber + 3 * yPos + ysize * 2;
		
		int inversion = 0;
		int compare = getSpriteNumberAt(xPos, yPos);
		if (compare != 0){
			for (int y = yPos; y >= 0; y -= ysize){
				for (int x = xPos; x <= xsize * 2; x += xsize){
					if (compare > getSpriteNumberAt(x, y) && 
							getSpriteNumberAt(x, y) != 0){
						inversion++;
					}
				}
				xPos = 0;
			}
			return inversion;
		}
		else{
			return 0;
		}
	}
	
	private int sumOfInversions(){
		int sum = 0;
		for (int i = 8; i > 0; i--){
			sum = sum + numberOfInversions(i);
		}
		return sum;
	}
	
	private boolean isSolvable(){
		if (sumOfInversions() % 2 == 0)
			return true;
		else
			return false;
	}
}