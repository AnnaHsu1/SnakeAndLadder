// -----------------------------------------------------
// Assignment (1)
// Question: 1
// Written by: Wassim Nijaoui - 40121366
//             
// -----------------------------------------------------
// Algorithm and explanation:
// 1. there are three main classes, a class for the Board, one for the player and this class that uses all of them
// 2. we start by creating the number of players after checking the correctness of the user's input
// 3. then we create an array of players in order to make it dynamic and easy to change
// 4. the board class was created dynamically too in the sense of that you can change the length of the board
// 5. the game starts by throwing the dice in order to determine who starts first.
// 6. we get a list of throws after we check that every throw is unique and then we sort it and determine who starts first
// 7. after that the game actually start
// 8. as a bonus and in order to make it more interesting, I added a sleep function so we can watch the game while it's playing rather than after the end
// 9. I also added a bet system to make it more interesting and I handled the different user's inputs 
//10. The game keeps track of each player's turn and position while they are playing as mentioned bellow 

import java.util.Scanner; 

public class LadderAndSnake {
	int num_of_players;
	int board_length=10 ;
	int game_round = 0;
	int player_turn = 0;
	public LadderAndSnake (int num_of_players){
		if ((num_of_players >= 2) || (num_of_players <= 2)){
			this.num_of_players = num_of_players;
			this.player_turn = 0;
			System.out.println("number of players : "+ String.valueOf(this.num_of_players));
		}else {System.out.println("Invalid number of players, the number should be between 2 and 4");}
	}
	public int flipDice(){
		int random_play = (int)(Math.random() * 6) + 1; 
		return random_play;
	}
	public int play() {
		boolean game_running = true;
		boolean special_event = false;
		String winner = "None";
		// Creating the Board / initializing the game
		Board board = new Board(board_length);
		
		// Creating the players
		Player[] players = new Player[this.num_of_players];
		for(int i = 0; i<this.num_of_players; i++) {
			players[i] = new Player();
		}
		// Determining who is starting to play first:
		int[] turn_order = new int[this.num_of_players];
		turn_order = this.determine_turns();
		String direction = "up";
		while(game_running) { // this is the main game loop
			// add a delay so we can watch the game in real time
			LadderAndSnake.sleep(1);
			// give the turn to the player with the highest dice roll
			//System.out.println();
			players[turn_order[this.player_turn]].is_player_turn(true);
			System.out.println("Player "+String.valueOf(turn_order[this.player_turn]+1)+" is playing now");
			
			// roll dice
			int dice_roll = this.flipDice();
			int square_pos = players[turn_order[this.player_turn]].get_square_position(this.board_length);
			
			// move depending on the number
			int x = players[turn_order[this.player_turn]].get_x_position();
			int y = players[turn_order[this.player_turn]].get_y_position();
			if ((y == this.board_length-1)){//&&(x+6 >= this.board_length)) {
				int difference = (x + dice_roll); 
				if((difference-10)>0) {
					players[turn_order[this.player_turn]].move_to((20-difference), (y));
				}else {
					players[turn_order[this.player_turn]].move_to((difference), (y));
				}
			}else if (((x + dice_roll)>this.board_length)&&(y < this.board_length)) {
				players[turn_order[this.player_turn]].move_to((x+dice_roll-10), (y+1)); 
			}else {
				players[turn_order[this.player_turn]].move_to((x+dice_roll), y); 
			}
			square_pos = players[turn_order[this.player_turn]].get_square_position(this.board_length);
			
			// check if it is a special position
			check_special_positions(players[turn_order[this.player_turn]]);
			
			// printing the player number and current position
			System.out.println("Player "+String.valueOf(turn_order[this.player_turn]+1)+" got a dice value of "+String.valueOf(dice_roll));
			if (special_event == true) {
				System.out.println("gone to square 4 then "+direction+" to square "+ String.valueOf(square_pos));
				// pause a bit
				LadderAndSnake.sleep(2);
			}else{
				System.out.println("now in square "+String.valueOf(square_pos));
			}
			
			// check if something is wrong with the game : NOT NECESSARY
			if (square_pos > this.board_length*this.board_length) {
				winner = String.valueOf(turn_order[this.player_turn]+1);
				System.out.println("Something is wrong, adjusting the player position!");
				players[turn_order[this.player_turn]].move_to(6, 9);
				
			}			
			// check for winner
			if (square_pos == this.board_length*this.board_length) {
				winner = String.valueOf(turn_order[this.player_turn]+1);
				System.out.println("Game over; Player "+winner+" Won!");
				break;
				
			}else {
				winner = "None"; 
				System.out.println("Game not over; flipping again");		 // the game continues	
			}
			for (int i=0; i<this.num_of_players; i++) {
				System.out.println("Player "+String.valueOf(i+1)+" is at square "+String.valueOf(players[i].get_square_position(this.board_length)));
			}
			// end of player turn
			players[turn_order[this.player_turn]].is_player_turn(false);
			
			// preparing who is going to play next
			if ((this.player_turn % (this.num_of_players-1) == 0)&&(this.player_turn != 0)) {
				this.player_turn = 0;
			}else{
				this.player_turn += 1;
			}
		}
		return Integer.parseInt(winner); // return the number of the winner
	}
	private int[] determine_turns() { // find out who starts first by throwing the dice
		int[] turn_order = new int[this.num_of_players];
		int[] ordered_turns = new int[this.num_of_players];	
		//int dice_roll = 0;
		boolean reroll;
		for (int i = 0; i< this.num_of_players; i++) {
			reroll = true;
			System.out.println("Player "+String.valueOf(i+1)+" is throwing the dice");
			while(reroll == true) {
				reroll = false; // assume that we got a unique dice roll at first
				turn_order[i] = this.flipDice();
				System.out.println("Player "+String.valueOf(i+1)+" got a dice value of "+String.valueOf(turn_order[i]));
				for (int j = 0; j< i; j++) {
					if (turn_order[i] == turn_order[j]) {
						reroll = true;
						System.out.println("A tie was achieved between Player "+String.valueOf(i+1)+" and Player "+String.valueOf(j+1)+". Attempting to break the tie");
					}
				}
			}
		}
		ordered_turns = this.sort_list(turn_order);
		System.out.println("Game is starting");
		
		return ordered_turns;
	}
	// sleep function to delay the game a bit
	private static void sleep(int i) {
		try {
			Thread.sleep(i*500); // in m.seconds
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
	private int[] sort_list(int[] my_list) { // transfer the number of throws into a list of who starts first
		int[] list_sorted_index = new int[my_list.length];
		int max=0;
		int max_index = 0;
		int max_to_avoid = 10;
		for (int j = 0; j< my_list.length; j++) {
			for (int i = 0; i< my_list.length; i++) {
				if ((my_list[i] > max)&&(my_list[i]<max_to_avoid)) {
					max_index = i;
					max = my_list[i];
					}
			}
			max_to_avoid = max;
			list_sorted_index[j] = max_index;
			max = 0; max_index=0;
		}
		return list_sorted_index;
	}
	private void check_special_positions(Player player) { // setting up manually the special cases of snakes and laders
		int x = player.get_x_position();
		int y = player.get_y_position();
		int d_x = x;
		int d_y = y;
		// snakes :
		if ((x == 5)&&(y == 1)) {
			d_x = 6;d_y = 0;
		}else if ((x == 7)&&(y == 4)) {
			d_x = 9;d_y = 2;
		}else if ((x == 3)&&(y == 6)) {
			d_x = 0;d_y = 5;
		}else if ((x == 1)&&(y == 6)) {
			d_x = 1;d_y = 1;
		}else if ((x == 7)&&(y == 9)) {
			d_x = 7;d_y = 6;
		}else if ((x == 5)&&(y == 9)) {
			d_x = 3;d_y = 2;
		}else if ((x == 3)&&(y == 9)) {
			d_x = 4;d_y = 7;
		}else if ((x == 2)&&(y == 9)) {
			d_x = 2;d_y = 7;
		// laders :
		}else if ((x == 3)&&(y == 0)) {
			d_x = 6;d_y = 1;
		}else if ((x == 8)&&(y == 0)) {
			d_x = 9;d_y = 3;
		}else if ((x == 1)&&(y == 0)) {
			d_x = 3;d_y = 2;
		}else if ((x == 7)&&(y == 2)) {
			d_x = 3;d_y = 8;
		}else if ((x == 4)&&(y == 3)) {
			d_x = 3;d_y = 4;
		}else if ((x == 0)&&(y == 2)) {
			d_x = 1;d_y = 5;
		}else if ((x == 9)&&(y == 5)) {
			d_x = 6;d_y = 6;
		}else if ((x == 9)&&(y == 7)) {
			d_x = 0;d_y = 9;
		}else if ((x == 1)&&(y == 8)) {
			d_x = 10;d_y = 10;
		}
		if ((x != d_x)||(y != d_y)) {
			player.move_to(d_x, d_y);
			//player.set_x_position(d_x);
			//player.set_x_position(d_y);
			System.out.println(" gone to square "+String.valueOf(x +(y)*10)+" then up to square "+String.valueOf(d_x +10*(d_y)));
			
		}
	}
	public static void PlayLadderAndSnake() { //main driver function
		Scanner keyIn = new Scanner(System.in);
		int number_of_players;
		int attempts = 0;
		boolean bet_off = false;
		int winner_pred = 1;
		int amount = 0;
		
		System.out.println("Enter the # of players for your game – Number must be between 2 and 4 inclusively:");
		number_of_players = keyIn.nextInt();
		boolean valid_number = false;
		if ((number_of_players>=2)&&(number_of_players<=4)) {
			valid_number = true;
		}
		if (valid_number == false) {
			attempts+=1;
			while(valid_number == false) {
				if (attempts >=4) {
					System.out.println("Bad Attempt 4! You have exhausted all your chances. Program will terminate! ");					
					break;
				}
				System.out.println("Bad Attempt "+String.valueOf(attempts)+" - Invalid # of players. Please enter a # between 2 and 4 inclusively:");
				number_of_players = keyIn.nextInt();
				if ((number_of_players>=2)&&(number_of_players<=4)) {
					valid_number = true;
				}
				attempts+=1;
			}
		}
		if (attempts <4) {
			try {
				System.out.println("Do you want to bet on which player is going to win to make it more fun? - Y / N");
				String respounce = keyIn.next();
				if (respounce.toLowerCase().equals("y")) {
					System.out.println("Perfect, how much do you want to bet? ");
					amount = keyIn.nextInt();
					System.out.println("Please enter the player number you are beating on");
					winner_pred = keyIn.nextInt();
					if ((winner_pred>number_of_players)||(winner_pred<=0)) {
						bet_off = true;
					}
				}
				
			} catch (Exception e) {
				System.out.println("Something went wrong... \n"+e+"\ngame starting..");
				bet_off = true;
			}
			LadderAndSnake game = new LadderAndSnake(number_of_players);
			int winner = game.play();
			if (bet_off == false) {
				if (winner == winner_pred) {
					System.out.println("Congrats! you have won the bet! "+ String.valueOf(amount));
				}
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayLadderAndSnake();
		//game.play();
	}


}

