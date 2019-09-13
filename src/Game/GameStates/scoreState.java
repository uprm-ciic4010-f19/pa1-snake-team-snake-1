package Game.GameStates;

public class scoreState {
	
	public static double currscore;
	
	public scoreState() {
		currscore = 0;
	}
	
	public Double addscore() {
		
		
		double temp = Math.sqrt(2 * scoreState.currscore+1);
		java.lang.Math.floor(temp);

		
		return temp;
		
	}

}
