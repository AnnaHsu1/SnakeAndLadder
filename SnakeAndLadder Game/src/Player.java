
public class Player {
	int x_position;
	int y_position;
	boolean currently_playing;
	//player constructor
	public Player() {
		this.x_position = 0;
		this.y_position = 0;
		this.currently_playing= false;
	}
	// move the player and set his coordinates
	public void move_to(int x, int y) {
		this.x_position = x;
		this.y_position = y;
	}
	// set only the x coordinate
	public int get_x_position() {
		return this.x_position;
	}
	// set the y coordinate
	public int get_y_position() {
		return this.y_position;
	}
	public void set_x_position(int x) {
		this.x_position = x;
	}
	public void set_y_position(int y) {
		this.y_position = y;
	}
	public void is_player_turn(boolean playing) {
		this.currently_playing = playing;
	}
	public int get_square_position(int board_len) {
		return this.x_position + board_len * this.y_position;
	}
}
