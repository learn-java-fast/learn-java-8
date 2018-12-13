package com.mcnz.lambda;

import javax.swing.*;

public class Game{

	public static void main(String args[]) {

		String prompt = "Will it be rock, paper or scissors?";

		String input = JOptionPane.showInputDialog(prompt);

		String result = "error";

		if (input.equalsIgnoreCase("scissors"))
			result = "win";

		if (input.equalsIgnoreCase("paper"))
			result = "tie";

		if (input.equalsIgnoreCase("rock"))
			result = "lose";

		JOptionPane.showMessageDialog(null, result);

	}
}

@FunctionalInterface
interface GamePlay {
	
	public String play(String gesture);
	
}

class RockPaperScissorsGame implements GamePlay {
	
	public String play(String in) {
		String result = "error";

		if (input.equalsIgnoreCase("scissors"))
			result = "win";

		if (input.equalsIgnoreCase("paper"))
			result = "tie";

		if (input.equalsIgnoreCase("rock"))
			result = "lose";
		
		return result;
	}
	
}
	




