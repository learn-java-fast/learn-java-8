package com.mcnz.lambda;

import javax.swing.*;
import java.util.function.*;


public class Game{


	public static void main(String args[]) {

		String prompt = "Will it be rock, paper or scissors?";

		String input = JOptionPane.showInputDialog(prompt);

		//GamePlay gamePlay = Game.playRockPaperScissors();
		//String result = gamePlay.play(input);
		
		Function<String,String> function = Game.playRockPaperScissors();
		
		String result = function.apply(input);
		

		JOptionPane.showMessageDialog(null, result);

	}
	
	public static Function<String,String> playRockPaperScissors() {
		

		Function<String,String> gamePlay = (data) -> {
					String result = "error";
					//System.out.println(this.id);
					if (data.equalsIgnoreCase("scissors"))
						result = "win";

					if (data.equalsIgnoreCase("paper"))
						result = "tie";

					if (data.equalsIgnoreCase("rock"))
						result = "lose";
					
					return result;
		};
		
		return gamePlay;
		
	}
	
}

@FunctionalInterface
interface GamePlay {
	
	public String play(String gesture);
	
}
