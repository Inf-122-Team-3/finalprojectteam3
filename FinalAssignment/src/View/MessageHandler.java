package View;
import java.util.List;
import javax.swing.JOptionPane;

import Model.Message;

public class MessageHandler
{
	public static void DisplayMessages(List<Message> messages)
	{
		if(messages != null)
			for(Message m : messages)
				JOptionPane.showMessageDialog(null, m.getContent());
	}
}
