package View;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;


public class ButtonGrid {

	JFrame frame = new JFrame();
	JButton[][] grid;
	
	public ButtonGrid(int width, int length){
		frame.setLayout(new GridLayout(width,length));
		
		grid = new JButton[width][length];
		for(int y = 0; y < length; y++){
			for (int x = 0; x < width; x++){
				
				grid[x][y] = new JButton("(" + x + "," + y +")");
				grid[x][y].addActionListener(new ButtonActionListener(x,y));
				
				frame.add(grid[x][y]);
			}
		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();frame.setVisible(true);
	}
	public static void main(String[] args) {
		new ButtonGrid(10,10);
	}
}
