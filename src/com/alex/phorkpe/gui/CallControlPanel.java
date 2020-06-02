package com.alex.phorkpe.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.alex.phorkpe.jtapi.JTAPILine;
import com.alex.phorkpe.jtapi.JTAPITools;
import com.alex.phorkpe.utils.LanguageManagement;
import com.alex.phorkpe.utils.Variables;

/**********************************
 * Used to define a call control panel
 * 
 * This panel is used by the user to launch call
 * action to the targeted phone
 * 
 * @author RATEL Alexandre
 **********************************/
public class CallControlPanel extends JPanel implements ActionListener
	{
	/**
	 * Variables
	 */
	private JPanel main, title, center;
	private JButton control, call, answer, hold, transfer, conference, end;
	private JTextField lineToControl;
	private JLabel titleLabel, status;
	private JFrame mainFrame;
	private boolean controlInProgress, callInProgress;
	private String lineNumber, lineStatus;
	private JTAPILine controlledLine;
	
	/***************
	 * Constructor
	 ***************/
	public CallControlPanel(JFrame mainFrame)
		{
		this.mainFrame = mainFrame;
		control = new JButton(LanguageManagement.getString("guicontrolbutton"));
		call = new JButton(LanguageManagement.getString("guicallbutton"));
		call.setPreferredSize(new Dimension(100, 100));
		answer = new JButton(LanguageManagement.getString("guianswerbutton"));
		hold = new JButton(LanguageManagement.getString("guiholdbutton"));
		transfer = new JButton(LanguageManagement.getString("guitransferbutton"));
		conference = new JButton(LanguageManagement.getString("guiconferencebutton"));
		end = new JButton(LanguageManagement.getString("guiendbutton"));
		
		lineToControl = new JTextField(20);
		lineToControl.setMaximumSize(new Dimension(100,25));
		
		titleLabel = new JLabel(LanguageManagement.getString("guilinetocontrol"));
		status = new JLabel("Waiting");
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		main = new JPanel();
		main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
		title = new JPanel();
		title.setLayout(new BoxLayout(title,BoxLayout.X_AXIS));
		title.setMaximumSize(new Dimension(600,25));
		title.setBackground(Color.LIGHT_GRAY);
		center = new JPanel();
		center.setLayout(new GridLayout(2, 3));
		
		//Assignment
		title.add(Box.createHorizontalGlue());
		title.add(titleLabel);
		title.add(lineToControl);
		title.add(control);
		title.add(Box.createHorizontalGlue());
		title.add(status);
		title.add(new JLabel(" "));
		
		center.add(call);
		center.add(answer);
		center.add(hold);
		center.add(transfer);
		center.add(conference);
		center.add(end);
		
		main.add(title);
		main.add(Box.createVerticalGlue());
		main.add(center);
		main.add(Box.createVerticalGlue());
		//main.add(new JLabel(" "));
		
		this.add(main);
		
		//Events
		control.addActionListener(this);
		call.addActionListener(this);
		answer.addActionListener(this);
		hold.addActionListener(this);
		transfer.addActionListener(this);
		conference.addActionListener(this);
		end.addActionListener(this);
		}

	@Override
	public void actionPerformed(ActionEvent evt)
		{
		if(evt.getSource() == this.control)
			{
			if(controlInProgress)
				{
				//We stop the control in progress
				Variables.getLogger().debug("The user ask to stop the control in progress on phone : "+lineNumber);
				
				try
					{
					JTAPITools.stopLineControl(lineNumber);
					}
				catch (Exception e)
					{
					Variables.getLogger().error("ERROR : Unable to stop control on phone "+lineNumber+" : "+e.getMessage(),e);
					JOptionPane.showMessageDialog(null,LanguageManagement.getString("error")+" : "+e.getMessage(),LanguageManagement.getString("error"),JOptionPane.ERROR_MESSAGE);
					}
				
				lineToControl.setEnabled(true);//We enable the JTextField
				//We change the control button text and color
				control.setText(LanguageManagement.getString("guicontrolbutton"));
				control.setBackground(Color.GRAY);
				controlInProgress = false;
				lineNumber = "";
				}
			else
				{
				//We start a new phone control
				lineNumber = lineToControl.getText();
				Variables.getLogger().debug("The user ask to control the following phone : "+lineNumber);
				
				try
					{
					controlledLine = JTAPITools.startLineControl(lineNumber, this);
					
					lineToControl.setEnabled(false);//We disable the JTextField
					//We change the control button text and color
					control.setText(LanguageManagement.getString("stop"));
					control.setBackground(Color.green);
					controlInProgress = true;
					Variables.getLogger().debug(lineNumber+" : Control initiated with success");
					}
				catch (Exception e)
					{
					Variables.getLogger().error("ERROR : Unable to start control on phone "+lineNumber+" : "+e.getMessage(),e);
					JOptionPane.showMessageDialog(null,LanguageManagement.getString("error")+" : "+e.getMessage(),LanguageManagement.getString("error"),JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		else if(evt.getSource() == this.call)
			{
			
			}
		else if(evt.getSource() == this.answer)
			{
			
			}
		else if(evt.getSource() == this.hold)
			{
			
			}
		else if(evt.getSource() == this.transfer)
			{
			
			}
		else if(evt.getSource() == this.conference)
			{
			
			}
		else if(evt.getSource() == this.end)
			{
			
			}
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}

