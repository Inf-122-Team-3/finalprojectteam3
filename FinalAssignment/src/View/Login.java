package View;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Client.Client;
public class Login extends JFrame
{
	private JLabel usernameLabel;
	private JTextField usernameTextField;
	private JLabel serverIPLabel;
	private JTextField serverIP;
	private JButton submitButton;
	private JPanel contentPane;
	private Client client;
	
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
		serverIPLabel = new JLabel("Server IP Address: ");
		serverIP = new JTextField();
		
		
		submitButton.setText("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				submitButton_actionPerformed(e);
			}
		});

		//setting the layout
		contentPane.setLayout(null);
		addComponent(contentPane, usernameLabel, 5,10,125,18);
		addComponent(contentPane, usernameTextField, 150,10,143,22);
		addComponent(contentPane, serverIPLabel, 5, 50, 125, 18);
		addComponent(contentPane, serverIP, 150, 50, 143, 22);
		addComponent(contentPane, submitButton, 150, 90, 83,28);
		
		
		//title	
		this.setTitle("Login");
		this.setLocation(new Point(76, 182));
		this.setSize(new Dimension(335, 175));
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
		//System.out.println("\nsubmitButton_actionPerformed(ActionEvent e) called.");
		String username = new String(usernameTextField.getText());
		client = new Client(username, this, serverIP.getName());
	}
	
	public void loadLobbyView() {
		LobbyView lobbyView = new LobbyView(client, this);
		client.setLobbyView(lobbyView);
		this.dispose();
	}
	
	public void popupMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public static void main(String[] args)
	{
		new Login();
	};
}