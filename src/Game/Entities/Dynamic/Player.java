package Game.Entities.Dynamic;


import Main.Handler;
import Game.GameStates.scoreState;
import Game.GameStates.State;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

    public int lenght;
    public boolean justAte;
                 
    private Handler handler;
    private scoreState score;
    private boolean pause; 
    public boolean isalive;
    public boolean BadApple;                      //apple variable

    public int xCoord;
    public int yCoord;
    int speed;              //speed variable//
    public int moveCounter;
    public String direction;//is your first name one?
    public int  moves;			//spaces moved variable

    public Player(Handler handler){
        this.handler = handler;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        direction= "Right";
        justAte = false;
       
        lenght= 1;

        speed=5;          //basic speed
        score = new scoreState();
        isalive = true;
        pause = false;

       

    }
    public void tick(){
        moveCounter++;
        moves++;
        if(moveCounter>=speed) {
            checkCollisionAndMove();
            moveCounter=0;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
        	if(direction.equals("Down")) {
        		;
        	}
        	else {
            direction="Up";
        	}
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
        	if(direction.equals("Up")) {
        		;
        	}
        	else {
        		direction="Down";
        	}
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
        	if(direction.equals("Right")) {
        		;
        	}
        	else {
        		direction="Left";
        	}
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)){

        	if(direction.equals("Left")) {
        		;
        	}
        	else {
        		direction="Right";
        	}
        } if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)) {
        	speed++;
        } if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)) {
        	speed--;
        } if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
        	 handler.getWorld().body.add(new Tail(xCoord, yCoord,handler));
        } if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
    		State.setState(handler.getGame().pauseState);

        }

    }

    public void checkCollisionAndMove(){
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        int x = xCoord;
        int y = yCoord;
        switch (direction){
            case "Left":
                if(xCoord==0){
                    xCoord = handler.getWorld().GridWidthHeightPixelCount-1;
                }
                else{
                    xCoord--;
                }
                break;
            case "Right":
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    xCoord = 0;
                }
                else{
                    xCoord++;
                }
                break;
            case "Up":
                if(yCoord==0){
                    yCoord = handler.getWorld().GridWidthHeightPixelCount-1;
                }else{
                    yCoord--;
                }
                break;
            case "Down":
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    yCoord = 0;
                }else{
                    yCoord++;
                }
                break;
        }
        handler.getWorld().playerLocation[xCoord][yCoord]=true;
        															               //Good or bad apple
        
        if (moves<800) {
       	 BadApple=false;
       	 
        }else { BadApple=true;
        }



        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            Eat();
        }
        
        for (int i = 0; i< handler.getWorld().body.size(); i++) {
        	if(xCoord == handler.getWorld().body.get(i).x && yCoord == handler.getWorld().body.get(i).y) {
        		isalive = false;
        	}
        }

        if(!handler.getWorld().body.isEmpty()) {
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
            
        
         
        }

    }

    public void render(Graphics g,Boolean[][] playeLocation){
        Random r = new Random();
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
        	if (this.isalive == false) {
        		g.clearRect(0, 0, 800, 800);
        		g.setColor(Color.red);
                g.setFont(new Font("ComicSans", Font.PLAIN, 64));
                g.drawString("Game Over", 225, 400);
                break;
        	}
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                g.setColor(Color.green);

                if(playeLocation[i][j]||handler.getWorld().appleLocation[i][j]){
                    g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
               
                }
               g.setColor(Color.red);
               if(handler.getWorld().appleLocation[i][j]){
                    g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
                String score_str = Double.toString(Game.GameStates.scoreState.currscore);
                g.setFont(new Font("ComicSans", Font.PLAIN, 32));
                g.drawString(score_str, 155, 40);
                g.drawString("Score:", 40, 40);
               }
               if ((handler.getWorld().appleLocation[i][j]) && (BadApple)) {              //change color
            	   g.setColor(Color.LIGHT_GRAY);
            	   g.fillRect((i*handler.getWorld().GridPixelsize),
                           (j*handler.getWorld().GridPixelsize),
                           handler.getWorld().GridPixelsize,
                           handler.getWorld().GridPixelsize);
            	   
               }
               
       

                    
            }
      
        
        }
        
    } 
    public void Eat(){
        lenght++;
        speed = speed - 1;
        Game.GameStates.scoreState.currscore += Math.sqrt( 2 * Game.GameStates.scoreState.currscore +1);
        Tail tail= null;
        handler.getWorld().appleLocation[xCoord][yCoord]=false;
        handler.getWorld().appleOnBoard=false;
        switch (direction){
            case "Left":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                        }
                    }

                }
                break;
            case "Right":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=0){
                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                        }
                    }

                }
                break;
            case "Up":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
            case "Down":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=0){
                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        } System.out.println("Tu biscochito");
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
        }
        handler.getWorld().body.addLast(tail);
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
        moves=0;
    
    														//start counting moves again
    }

    public void kill(){
        lenght = 0;
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

                handler.getWorld().playerLocation[i][j]=false;

            }
           
            
        }
    }

    public boolean isJustAte() {
    	
        return justAte;
    }

    public void setJustAte(boolean justAte) {
        this.justAte = justAte;
    }  
    
}
