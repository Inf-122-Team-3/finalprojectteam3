package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Login extends JFrame
{
	private JLabel usernameLabel;
	private JTextField usernameTextField;
	private JButton submitButton;
	private JPanel contentPane;

	public Login()
	{
		super();
		create();
		this.setVisible(true);
	}

	private void create()
	{
		usernameLabel = new JLabel();
		usernameTextField = new JTextField();
		submitButton = new JButton();
		contentPane = (JPanel)this.getContentPane();
		usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		usernameLabel.setText("Username:");
		
		
		submitButton.setText("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				submitButton_actionPerformed(e);
			}
		});

		//setting the layout
		contentPane.setLayout(null);
		addComponent(contentPane, usernameLabel, 5,10,106,18);
		addComponent(contentPane, usernameTextField, 110,10,183,22);
		addComponent(contentPane, submitButton, 150,75,83,28);
		
		//title
		this.setTitle("Login");
		this.setLocation(new Point(76, 182));
		this.setSize(new Dimension(335, 141));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	//helper method to add component with absolute positioning
	private void addComponent(Container container,Component c,int x,int y,int width,int height)
	{
		c.setBounds(x,y,width,height);
		container.add(c);
	}

	private void submitButton_actionPerformed(ActionEvent e)
	{
		System.out.println("\nsubmitButton_actionPerformed(ActionEvent e) called.");
		String username = new String(usernameTextField.getText());

	}


	public static void main(String[] args)
	{
		new Login();
	};
}