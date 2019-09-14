package Game.Entities.Static;


import Main.Handler;




/**
 * Created by AlexVR on 7/2/2018.
 */
public class Apple {

    private Handler handler;
     public boolean isGood;    //is good variable
    public int xCoord;
    public int yCoord;
    public float score= (float) Game.GameStates.scoreState.currscore;

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
        
        
    	if (handler.getWorld().player.BadApple) {        //determine if its good
    		isGood=false;
    		 handler.getWorld().body.removeLast();
                Game.GameStates.scoreState.currscore =(score- (Math.sqrt( 2 * (Math.abs(score)) +1))); //score change
                 if(Game.GameStates.scoreState.currscore <0) {
                	 Game.GameStates.scoreState.currscore=0;	 
                 }
                 
        }if (!handler.getWorld().player.BadApple) {
    		isGood=true;
    	}  
  
}}
