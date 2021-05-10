
public class Board {
	int board_len;
	public Board(int board_len) {
		this.board_len = board_len;
		this.create_board(board_len);
	}
	public int[][] create_board(int board_length) { // the board is 2D array
		// TODO Auto-generated method stub
		int[][] my_board = new int[board_length][board_length];
		return my_board;
	}

}
