package myPackage;

import java.awt.Button;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Interface extends JFrame implements ActionListener{
	 
		private JPanel getDataWayPane,randomPanel,filePanel,inputPanelr,inputPanelf,cards;
		private Button randomButton,fileButton,randomGetRltButton,fileGetRltButton;
		private JTextArea inputTextArear,inputTextAreaf;
		private JLabel inputLabelr,inputLabelf;
		private JLabel rlt;
		private CardLayout cl;
		
		public Interface()
		{
			setSize(510,230);
			setLocation(400, 300);
			setTitle("Automated Matching");
			
			//getDataWayPane: choose the way to get data
			randomButton = new Button("Using random data");
			randomButton.addActionListener(this);
			
			fileButton = new Button("Using data from specific file");
			fileButton.addActionListener(this);
			
			getDataWayPane = new JPanel();
			getDataWayPane.setLayout(new FlowLayout());
			getDataWayPane.add(randomButton);
			getDataWayPane.add(fileButton);
			//getDataWayPane
			
			//filePanel:using data from file
		
				//inputPanelf:to get the player number
				inputTextAreaf = new JTextArea();
				
				inputLabelf = new JLabel("<html>Please enter the player number in the file play.txt:" +
						"(the number is from 1 to 100)</html>");
				
				inputPanelf = new JPanel();
				inputPanelf.setLayout(new GridLayout(2,1));
				inputPanelf.add(inputLabelf);
				inputPanelf.add(inputTextAreaf);
				//inputPanel
			
			
			fileGetRltButton = new Button("Get the result using data from file");
			fileGetRltButton.addActionListener(this);
			
			filePanel = new JPanel();
			filePanel.setLayout(new FlowLayout());
			filePanel.add(inputPanelf);
			filePanel.add(new JPanel().add(fileGetRltButton));
			//filePanel
			
			//randomPanel:using random data
			
				//inputPanelr:to get the player data
				inputTextArear = new JTextArea();
				
				inputLabelr = new JLabel("<html>Please enter the player data <br>" +
						"([Format:]gender,looks,character,wealth,exceptLooks,expectCharacter,expectWealth)</html>");
				
				inputPanelr = new JPanel();
				inputPanelr.setLayout(new GridLayout(2,1));
				inputPanelr.add(inputLabelr);
				inputPanelr.add(inputTextArear);
				//inputPanel
		
		
			randomGetRltButton = new Button("Get the result using random data");
			randomGetRltButton.addActionListener(this);
			
			randomPanel = new JPanel();
			randomPanel.setLayout(new FlowLayout());
			randomPanel.add(inputPanelr);
			randomPanel.add(randomGetRltButton);
			//randomPanel
			
			
			add("North", getDataWayPane);
			
			rlt = new JLabel("<html>[Format:]id,looks,character,wealth,exceptLooks," +
					"expectCharacter,expectWealth<br> </html>");
			add("South",rlt);
			
			cl = new CardLayout();
			cards = new JPanel(cl);	
			cards.add("random data",randomPanel);
			cards.add("data from file",filePanel);
		    add("Center",cards);
		    
		}

		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand()=="Using random data"){
				cl.first(cards);
			}
			
			else if(e.getActionCommand()=="Using data from specific file"){
				cl.last(cards);				
			}
			else if(e.getActionCommand()=="Get the result using random data"){
				String attribution = inputTextArear.getText();
				if(attribution!=null && !attribution.trim().equals("") && verifyInput(attribution)){
					MakePair.usingRandomData(attribution);
					Person matcher = MakePair.getMatcher();
					if(matcher!=null){
						rlt.setText("<html>[Format:]id,looks,character,wealth,exceptLooks," +
							"expectCharacter,expectWealth<br>"+"The player's matcher is "+
							matcher+"</html>");
					}
					else{
						rlt.setText("<html>[Format:]id,looks,character,wealth,exceptLooks," +
								"expectCharacter,expectWealth<br>"+"The player is so pool," +
								"he has no macther</html>");
					}
					MakePair.clearData();
					return;
				}
				rlt.setText("the input is invalid");
				
			}
			else if(e.getActionCommand()=="Get the result using data from file"){
				String attribution = inputTextAreaf.getText();
				int lineNumber;
				if(attribution!=null && !attribution.trim().equals("") && attribution.matches("[0-9]*")){
					lineNumber = Integer.parseInt(attribution.trim());
					if(lineNumber<=100 && lineNumber>=1){
						MakePair.usingFileData(lineNumber);
						Person matcher = MakePair.getMatcher();
						if(matcher!=null){
							rlt.setText("<html>[Format:]id,looks,character,wealth,exceptLooks," +
									"expectCharacter,expectWealth<br>"+"The player's matcher is "+
									matcher+"</html>");
						}
						else{
							rlt.setText("<html>[Format:]id,looks,character,wealth,exceptLooks," +
								"expectCharacter,expectWealth<br>"+"The player is so pool" +
								"he has no macther</html>");
						}
						MakePair.clearData();
						return;
					}
				}
				rlt.setText("the input is invalid");
				
				
			}
			
		}
		
		/**
		 * Verify the user's input is valid or not with specific rule
		 * @param input
		 * @return
		 */
		private boolean verifyInput(String input){
			String regex = "[0-1],\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2}";
			String value[] = new String[7];
			boolean isValid = false;
			if(input.matches(regex)){
				value = input.split(",");
				for(int i=1;i<4;i++){//the attribution should be in [1-98]
					int vi = Integer.parseInt(value[i]);
					if(vi<=98&&vi>=1){
						isValid = true;
					}
					else{
						isValid = false;
						return isValid;
					}
				}
				if(Integer.parseInt(value[4])+Integer.parseInt(value[5])
						+Integer.parseInt(value[6])==100){//the summary exception should be 100
					isValid = true;
				}
				else{
					isValid = false;
					return isValid;
				}
			}
			
			return isValid;
		}
	
}
