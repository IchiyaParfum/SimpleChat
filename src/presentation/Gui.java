package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import communication.Observer;
import entity.Model;
import entity.User;

public class Gui implements View{
	private JFrame frame;
	private JButton send;
	private JTextField text;
	private JList<User> users;
	private ChatPanel chat;
	
	private Model data;
	private ArrayList<ViewObserver> observers;
	
	public Gui(Model data) {
		//Initialize data model
		this.data = data;
		
		//Initialize subject
		observers = new ArrayList<>();
		
		//Set up frame layout
		frame = new JFrame("Chat");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				for(ViewObserver vo : observers) {
					vo.closeWindowClicked(e);
				}
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		//Set up components
		send = new JButton("Send");
		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for(ViewObserver vo : observers	) {
					vo.sendMessageClicked(text.getText(), null);
				}
				
			}
			
		});
		chat = new ChatPanel();
		text = new JTextField();
		text.setPreferredSize(new Dimension(200, 25));
		users = new JList<>();
		
		//Insert components in frame
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(send);
		p.add(text);
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(p, BorderLayout.SOUTH);
		p1.add(chat, BorderLayout.CENTER);
		
		frame.add(p1, BorderLayout.CENTER);
		frame.add(users, BorderLayout.WEST);
		
		//Make frame visible
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
	
	@Override
    public void addObserver(Observer observer) {
    	//Only add observer if observer is a server observer and not already in list
    	if(observer instanceof ViewObserver && !observers.contains(observers)) {
    		observers.add((ViewObserver) observer);
    	}
    }
    
    @Override
    public boolean removeObserver(Observer observer) {
    	if(observer != null) {
        	return observers.remove(observer);
    	}
    	return false;
    }
    
    @Override
	public List<Observer> getObservers(){
    	List<Observer> list = new ArrayList<>();
    	
    	//Copy server observers into observer list
    	for(ViewObserver vo : observers) {
    		list.add(vo);
    	}
    	
    	return list;
    }

	@Override
	public void updateUsers() {
		User[] temp = new User[data.getUsers().size()];
		
		int i = 0;
		for(User u : data.getUsers().values()) {
			temp[i++] = u;
		}
		users.setListData(temp);
		
	}

	@Override
	public void updateChat() {
		if(!data.getChat().isEmpty()) {
			chat.publisch(data.getChat().get(data.getChat().size()-1).toString());
		}		
	}

	@Override
	public Window getWindow() {
		return frame;
	}

}
