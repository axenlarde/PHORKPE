package com.alex.phorkpe.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.alex.phorkpe.action.Action;
import com.alex.phorkpe.utils.LanguageManagement;
import com.alex.phorkpe.utils.Position;
import com.alex.phorkpe.utils.Variables;

public class MainWindow extends JFrame implements ActionListener, WindowListener
	{
	/************
	 * Variables
	 ************/
	//Dimensions
	public Dimension dimInfo;
	
	//Menu
	public JMenuBar myMenuBar;
	public JMenu menu;
	public JMenu send;
	public JMenuItem keys;
	public JMenu control;
	public JMenuItem calls;
	public JMenuItem scriptCalls;
	public JMenuItem fullControl;
	public JMenuItem exit;
	public JMenu tools;
	public JMenuItem option;
	public JMenuItem genCollectionFile;
	public JMenuItem genTemplateFile;
	public JMenu help;
	public JMenuItem about;
	private JLabel logo;
	private JPanel main;
	private Action action;
	
	/***************
	 * Constructor
	 ***************/
	public MainWindow()
		{
		/*************
		 * Variables
		 *************/
		//Menu
		myMenuBar = new JMenuBar();
		menu = new JMenu(LanguageManagement.getString("guimenu"));
		send = new JMenu(LanguageManagement.getString("guisend"));
		keys = new JMenuItem(LanguageManagement.getString("guikeys"));
		control = new JMenu(LanguageManagement.getString("guicontrol"));
		calls = new JMenuItem(LanguageManagement.getString("guicalls"));
		scriptCalls = new JMenuItem(LanguageManagement.getString("guiscriptcalls"));
		fullControl = new JMenuItem(LanguageManagement.getString("guifullcontrol"));
		exit = new JMenuItem(LanguageManagement.getString("exit"));
		tools = new JMenu(LanguageManagement.getString("tools"));
		option = new JMenuItem(LanguageManagement.getString("option"));
		genCollectionFile = new JMenuItem(LanguageManagement.getString("gencollectionfile"));
		genTemplateFile = new JMenuItem(LanguageManagement.getString("gentemplatefile"));
		help = new JMenu(LanguageManagement.getString("help"));
		about = new JMenuItem(LanguageManagement.getString("about"));
		
		//Logo & icon
		try
			{
			//Aspect.load(this, getClass().getResource("/art/IconSofuto.png").getPath(), Variables.getSoftwareName());
			//logo = new JLabel(new ImageIcon(getClass().getResource("/art/LogoSofuto.png")));
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		
		main = new JPanel();
		main.setLayout(new BoxLayout(main,BoxLayout.X_AXIS));
		main.setBackground(Color.WHITE);
		
		//Dimensions
		dimInfo = new Dimension(this.getWidth(), 100);
		
		//Titre
		setTitle(Variables.getSoftwareName()+" - "+Variables.getSoftwareVersion());
		
		//Positionnement
		this.setSize(new Dimension(600,400));
		Position.center(this);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		
		//Assignation
		myMenuBar.add(menu);
		myMenuBar.add(tools);
		myMenuBar.add(help);
		send.add(keys);
		menu.add(send);
		control.add(calls);
		control.add(scriptCalls);
		control.add(fullControl);
		menu.add(control);
		menu.add(exit);
		tools.add(option);
		tools.add(genCollectionFile);
		tools.add(genTemplateFile);
		help.add(about);
		setJMenuBar(myMenuBar);
		
		//Logo
		this.getContentPane().setBackground(Color.WHITE);
		main.add(Box.createHorizontalGlue());
		//main.add(logo);
		main.add(Box.createHorizontalGlue());
		add(Box.createVerticalGlue());
		add(main);
		add(Box.createVerticalGlue());
		
		
		//Events
		keys.addActionListener(this);
		calls.addActionListener(this);
		fullControl.addActionListener(this);
		exit.addActionListener(this);
		option.addActionListener(this);
		genCollectionFile.addActionListener(this);
		genTemplateFile.addActionListener(this);
		help.addActionListener(this);
		about.addActionListener(this);
		
		this.addWindowListener(this);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setResizable(false);
		setVisible(true);
		
		Variables.getLogger().info("Main window started");
		}

	public void actionPerformed(ActionEvent evt)
		{
		if(evt.getSource() == this.keys)
			{
			Variables.getLogger().info("Keys button pressed");
			
			this.action = new Action();
			
			this.getContentPane().removeAll();
			this.repaint();
			this.validate();
			}
		else if(evt.getSource() == this.calls)
			{
			Variables.getLogger().info("Calls button pressed");
			
			this.getContentPane().removeAll();
			this.getContentPane().add(new CallControlPanel(this));
			this.repaint();
			this.validate();
			}
		else if(evt.getSource() == this.fullControl)
			{
			Variables.getLogger().info("Full controle button pressed");

			//To be written
			
			this.getContentPane().removeAll();
			this.repaint();
			this.validate();
			}
		else if(evt.getSource() == this.exit)
			{
			this.dispose();
			}
		else if(evt.getSource() == this.about)
			{
			Variables.getLogger().info("About button pressed");
			new WindowApropos(LanguageManagement.getString("about")+" : "+Variables.getSoftwareName(),
					LanguageManagement.getString("softwarename")+" : "+Variables.getSoftwareName(),
					LanguageManagement.getString("softwareversion")+" : "+Variables.getSoftwareVersion(),
					LanguageManagement.getString("author")+" : Alexandre RATEL",
					LanguageManagement.getString("contact")+" : alexandre.ratel@gmail.com");
			}
		}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent arg0)
		{
		Variables.getLogger().info("The user ask to exit the application");
		
		if((this.action != null) && (this.action.isAlive()))
			{
			Variables.getLogger().debug("The program is still running so we ask to stop it");
			this.action.shutDown();
			}
		else
			{
			System.exit(0);
			}
		}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}
	
	/*2012*//*RATEL Alexandre 8)*/
	}
