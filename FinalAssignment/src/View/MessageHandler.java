package View;

import java.util.List;

import javax.swing.JOptionPane;

import Model.Message;

public class MessageHandler 
{
	public static void DisplayMessages(List<String> messages)
	{
//		if(messages != null)
//			for(Message m: messages)
//				JOptionPane.showMessageDialog(null, m.getContent());
//		
		if(messages != null && messages.size() > 0) {
			for(String message : messages) {
				JOptionPane.showMessageDialog(null, message);
			}
		}

	}

}
