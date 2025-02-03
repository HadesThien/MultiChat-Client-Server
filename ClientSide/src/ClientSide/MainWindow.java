package ClientSide;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea chatTextArea;
	private JPanel controlPanel;
	private JTextArea typingTextArea;
	private JButton fileButton;
	private JPanel avatar;
	private Container titlePanel;
	private JLabel clientJLabel;
	private JButton sendButton;
	private ConnectServer connection;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
			        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        frame.setSize(888, 600);
			        frame.setResizable(false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		
		setName("mainWindowJFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		setSize(939,611);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(214, 214, 214));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		this.chatTextArea = new JTextArea();
		chatTextArea.setBounds(5, 52, 860, 363);
		chatTextArea.setBackground(Color.GRAY);
		chatTextArea.setForeground(Color.BLACK);
		chatTextArea.setAutoscrolls(rootPaneCheckingEnabled);
		chatTextArea.setEnabled(false);
		chatTextArea.setEditable(false);
		chatTextArea.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 15));
		chatTextArea.setText("Server: Welcome Client! You would give me a message and I'm going to send you a crypted message\nServer: Let's talk!\n");
		
		controlPanel = new JPanel();
		controlPanel.setBounds(5, 426, 860, 130);
		controlPanel.setBackground(new Color(255, 255, 255));
		controlPanel.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 13));
		controlPanel.setLayout(null);
		
		typingTextArea = new JTextArea();
		typingTextArea.setMargin(new Insets(10,10,10,10));
		typingTextArea.setLocation(0, 5);
		typingTextArea.setRows(3);
		typingTextArea.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 15));
		typingTextArea.setSize(new Dimension(637, 114));
		typingTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.isShiftDown()) {
                        typingTextArea.append("\n");
                    } else {
                        send();
                        e.consume();
                    }
                }
            }
		});
		controlPanel.add(typingTextArea);
		
		fileButton = new JButton("File");
		BufferedImage originalIcon = null;
		try {
			originalIcon = ImageIO.read(MainWindow.class.getResource("/ClientSide/FileIcon.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		Image scaledIcon = originalIcon.getScaledInstance(25 , 25, Image.SCALE_SMOOTH);
		Icon icon = new ImageIcon(scaledIcon);
		
		fileButton.setIcon(icon);
		fileButton.setHorizontalTextPosition(SwingConstants.RIGHT);
		fileButton.setVerticalTextPosition(SwingConstants.CENTER);
		fileButton.setIconTextGap(10);
		fileButton.setBackground(new Color(8, 150, 229));
		fileButton.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 15));
		fileButton.setBounds(647, 73, 118, 42);
		controlPanel.add(fileButton);
		
		titlePanel = new JPanel();
		titlePanel.setBounds(5, 5, 860, 41);
		titlePanel.setBackground(new Color(8, 150, 219));
		titlePanel.setLayout(null);
		
		JLabel nameChatterLabel = new JLabel("Server Chatter");
		nameChatterLabel.setForeground(new Color(255, 255, 255));
		nameChatterLabel.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 22));
		nameChatterLabel.setBounds(73, 11, 185, 19);
		titlePanel.add(nameChatterLabel);
		
		avatar = new JPanel() {
			
			private BufferedImage image;
			{
				try {
					image = ImageIO.read(getClass().getResourceAsStream("robot2.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
			@Override
            protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				int diameter = Math.min(getWidth(), getHeight());
				int scaleFactor = 1;
				g.setColor(Color.WHITE);
				g.fillOval(0, 0, diameter, diameter);

				if (image != null) {
					Graphics2D g2d = (Graphics2D) g;
					g2d.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, diameter, diameter));

					int x = (getWidth() - diameter * scaleFactor) / 2;
					int y = (getHeight() - diameter * scaleFactor) / 2;
					g2d.drawImage(image, x, y, diameter, diameter, this);
					
				}
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 200);
            
		}
		};
		avatar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		avatar.setBackground(new Color(8, 150, 219));
		avatar.setBounds(10, 0, 42, 41);
		titlePanel.add(avatar);
		
		clientJLabel = new JLabel("Client");
		clientJLabel.setForeground(new Color(255, 255, 255));
		clientJLabel.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 20));
		clientJLabel.setBounds(0, 91, 87, 28);
		controlPanel.add(clientJLabel);
		
		sendButton = new JButton("Send");
		originalIcon = null;
		try {
			originalIcon = ImageIO.read(MainWindow.class.getResource("/ClientSide/SendIcon.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		scaledIcon = originalIcon.getScaledInstance(25 , 25, Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaledIcon);
		
		sendButton.setBounds(647, 11, 118, 42);
		fileButton.setBounds(647, 73, 118, 42);
		controlPanel.add(sendButton);
		sendButton.setIcon(icon);
		sendButton.setHorizontalTextPosition(SwingConstants.RIGHT);
		sendButton.setVerticalTextPosition(SwingConstants.CENTER);
		sendButton.setIconTextGap(10);
		sendButton.setBackground(new Color(8, 150, 229));
		sendButton.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 15));
		sendButton.addActionListener(e -> send());
		contentPane.setLayout(null);
		contentPane.add(titlePanel);
		contentPane.add(controlPanel);
		contentPane.add(chatTextArea);
		setTitle("Chat To Server");
		setSize(1342, 611);
		setLocationRelativeTo(null);
		
		connection = new ConnectServer(this);
		connection.start();
		
	}
	public void send() {
		String message = typingTextArea.getText() ;
		if(!message.trim().isEmpty()) {
			chatTextArea.append("You: " + message + "\n");
			typingTextArea.setText("");
		}
		//Send message to server
		if(connection != null) {
			this.connection.sendMessageToServer(message.trim());
		} else{
			System.out.println("Connection is null");
		}
	}
	public void updateChat(String message) {
		chatTextArea.append(message+"\n");
	}
	
}
