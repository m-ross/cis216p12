/*	Program Name:	Lab12 Audio Receiver
	Programmer:		Marcus Ross
	Date Due:		06 Dec 2013
	Description:	This program shows a radio-like interface.	*/

package lab12;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
	public static void main(String args[]) {
		new Radio();
	}
}

class Radio extends Frame implements WindowListener {
	private boolean band;
	private double freqFM, presetFM[];
	private short freqAM, presetAM[];
	private byte bala, fade, bass, treb, volu;

	public Radio() {
		band = false; // false = AM; true = FM
		freqFM = 100.0; // initial arbitrary station
		freqAM = 1000; // initial arbitrary station
		bala = 50; // these five attributes go from 0 to 100, like percentages
		fade = 50;
		bass = 50;
		treb = 50;
		volu = 50;
		double [] temp = {100.0, 100.0, 100.0, 100.0, 100.0};
		presetFM = temp; // arbitrary values just so they're never 0
		short [] tmp = {1000, 1000, 1000, 1000, 1000};
		presetAM = tmp; // pesky--don't know why they allow "var = {};" only when initializing

		SpringLayout layout = new SpringLayout();
		Panel panel = new Panel(layout);

		Label freqL = new Label("Freq", Label.CENTER);
		Label balaL = new Label("Bal", Label.CENTER);
		Label fadeL = new Label("Fade", Label.CENTER);
		Label bassL = new Label("Bass", Label.CENTER);
		Label trebL = new Label("Treb", Label.CENTER);
		Label voluL = new Label("Vol", Label.CENTER);
		Label freqD = new Label(Short.toString(freqAM), Label.CENTER);
		Label balaD = new Label(Byte.toString(bala), Label.CENTER);
		Label fadeD = new Label(Byte.toString(fade), Label.CENTER);
		Label bassD = new Label(Byte.toString(bass), Label.CENTER);
		Label trebD = new Label(Byte.toString(treb), Label.CENTER);
		Label voluD = new Label(Byte.toString(volu), Label.CENTER);
		Label presetL = new Label("Preset", Label.RIGHT);
		Label saveL = new Label("Save", Label.RIGHT); Label loadL = new Label("Load", Label.RIGHT);
		Button btnAM = new Button("AM");		Button btnFM = new Button("FM");
		Button btnIncFreq = new Button("+");	Button btnDecFreq = new Button("-");
		Button btnIncBala = new Button("+");	Button btnDecBala = new Button("-");
		Button btnIncFade = new Button("+");	Button btnDecFade = new Button("-");
		Button btnIncBass = new Button("+");	Button btnDecBass = new Button("-");
		Button btnIncTreb = new Button("+");	Button btnDecTreb = new Button("-");
		Button btnIncVolu = new Button("+");	Button btnDecVolu = new Button("-");
		Button btnSave1 = new Button("1");		Button btnSave2 = new Button("2");
		Button btnSave3 = new Button("3");		Button btnSave4 = new Button("4");
		Button btnSave5 = new Button("5");		Button btnLoad1 = new Button("1");
		Button btnLoad2 = new Button("2");		Button btnLoad3 = new Button("3");
		Button btnLoad4 = new Button("4");		Button btnLoad5 = new Button("5");

		btnAM.addActionListener(new PickBand(freqD, btnAM, btnFM));
		btnFM.addActionListener(new PickBand(freqD, btnAM, btnFM));
		btnIncFreq.addActionListener(new IncFreq(freqD));
		btnDecFreq.addActionListener(new DecFreq(freqD));
		btnIncBala.addActionListener(new IncBala(balaD));
		btnDecBala.addActionListener(new DecBala(balaD));
		btnIncFade.addActionListener(new IncFade(fadeD));
		btnDecFade.addActionListener(new DecFade(fadeD));
		btnIncBass.addActionListener(new IncBass(bassD));
		btnDecBass.addActionListener(new DecBass(bassD));
		btnIncTreb.addActionListener(new IncTreb(trebD));
		btnDecTreb.addActionListener(new DecTreb(trebD));
		btnIncVolu.addActionListener(new IncVolu(voluD));
		btnDecVolu.addActionListener(new DecVolu(voluD));
		btnSave1.addActionListener(new SavePreset()); btnSave2.addActionListener(new SavePreset());
		btnSave3.addActionListener(new SavePreset()); btnSave4.addActionListener(new SavePreset());
		btnSave5.addActionListener(new SavePreset());
		btnLoad1.addActionListener(new LoadPreset(freqD));
		btnLoad2.addActionListener(new LoadPreset(freqD));
		btnLoad3.addActionListener(new LoadPreset(freqD));
		btnLoad4.addActionListener(new LoadPreset(freqD));
		btnLoad5.addActionListener(new LoadPreset(freqD));

		panel.add(freqL);		panel.add(balaL);		panel.add(fadeL);	panel.add(bassL);
		panel.add(trebL);		panel.add(voluL);		panel.add(freqD);	panel.add(balaD);
		panel.add(fadeD);		panel.add(bassD);		panel.add(trebD);	panel.add(voluD);
		panel.add(presetL);		panel.add(btnAM);		panel.add(btnFM);
		panel.add(btnIncFreq);	panel.add(btnDecFreq);	panel.add(btnIncBala);
		panel.add(btnDecBala);	panel.add(btnIncFade);	panel.add(btnDecFade);
		panel.add(btnIncBass);	panel.add(btnDecBass);	panel.add(btnIncTreb);
		panel.add(btnDecTreb);	panel.add(btnIncVolu);	panel.add(btnDecVolu);
		panel.add(saveL);		panel.add(loadL);		panel.add(btnSave1);
		panel.add(btnSave2);	panel.add(btnSave3);	panel.add(btnSave4);
		panel.add(btnSave5);	panel.add(btnLoad1);	panel.add(btnLoad2);
		panel.add(btnLoad3);	panel.add(btnLoad4);	panel.add(btnLoad5);

		layout.putConstraint("North", btnAM, 15, "North", panel);
		layout.putConstraint("West", btnAM, 15, "West", panel);
		layout.putConstraint("North", btnFM, 0, "West", btnAM);
		layout.putConstraint("West", btnFM, 0, "East", btnAM);
		layout.putConstraint("North", freqD, 0, "South", btnAM);
		layout.putConstraint("HorizontalCenter", freqD, 0, "East", btnAM);
		layout.putConstraint("North", freqL, 0, "South", freqD);
		layout.putConstraint("HorizontalCenter", freqL, 0, "HorizontalCenter", freqD);
		layout.putConstraint("North", btnDecFreq, 0, "South", freqL);
		layout.putConstraint("West", btnDecFreq, -21, "HorizontalCenter", freqL);
		layout.putConstraint("East", btnDecFreq, 0, "HorizontalCenter", freqL);
		layout.putConstraint("North", btnIncFreq, 0, "North", btnDecFreq);
		layout.putConstraint("West", btnIncFreq, 0, "East", btnDecFreq);
		layout.putConstraint("North", balaL, 0, "North", voluL);
		layout.putConstraint("HorizontalCenter", balaL, 0, "HorizontalCenter", btnIncBala);
		layout.putConstraint("North", btnIncBala, 0, "South", balaL);
		layout.putConstraint("East", btnIncBala, -20, "West", btnIncFade);
		layout.putConstraint("North", btnDecBala, 0, "South", btnIncBala);
		layout.putConstraint("West", btnDecBala, 0, "West", btnIncBala);
		layout.putConstraint("East", btnDecBala, 0, "East", btnIncBala);
		layout.putConstraint("North", balaD, 0, "South", btnDecBala);
		layout.putConstraint("HorizontalCenter", balaD, 0, "HorizontalCenter", btnDecBala);
		layout.putConstraint("North", fadeL, 0, "North", voluL);
		layout.putConstraint("HorizontalCenter", fadeL, 0, "HorizontalCenter", btnIncFade);
		layout.putConstraint("North", btnIncFade, 0, "South", fadeL);
		layout.putConstraint("East", btnIncFade, -20, "West", btnIncBass);
		layout.putConstraint("North", btnDecFade, 0, "South", btnIncFade);
		layout.putConstraint("West", btnDecFade, 0, "West", btnIncFade);
		layout.putConstraint("East", btnDecFade, 0, "East", btnIncFade);
		layout.putConstraint("North", fadeD, 0, "South", btnDecFade);
		layout.putConstraint("HorizontalCenter", fadeD, 0, "HorizontalCenter", btnDecFade);
		layout.putConstraint("North", bassL, 0, "North", voluL);
		layout.putConstraint("HorizontalCenter", bassL, 0, "HorizontalCenter", btnIncBass);
		layout.putConstraint("North", btnIncBass, 0, "South", bassL);
		layout.putConstraint("East", btnIncBass, -20, "West", btnIncTreb);
		layout.putConstraint("North", btnDecBass, 0, "South", btnIncBass);
		layout.putConstraint("West", btnDecBass, 0, "West", btnIncBass);
		layout.putConstraint("East", btnDecBass, 0, "East", btnIncBass);
		layout.putConstraint("North", bassD, 0, "South", btnDecBass);
		layout.putConstraint("HorizontalCenter", bassD, 0, "HorizontalCenter", btnDecBass);
		layout.putConstraint("North", trebL, 0, "North", voluL);
		layout.putConstraint("HorizontalCenter", trebL, 0, "HorizontalCenter", btnIncTreb);
		layout.putConstraint("North", btnIncTreb, 0, "South", trebL);
		layout.putConstraint("East", btnIncTreb, -20, "West", btnIncVolu);
		layout.putConstraint("North", btnDecTreb, 0, "South", btnIncTreb);
		layout.putConstraint("West", btnDecTreb, 0, "West", btnIncTreb);
		layout.putConstraint("East", btnDecTreb, 0, "East", btnIncTreb);
		layout.putConstraint("North", trebD, 0, "South", btnDecTreb);
		layout.putConstraint("HorizontalCenter", trebD, 0, "HorizontalCenter", btnDecTreb);
		layout.putConstraint("North", voluL, 15, "North", panel);
		layout.putConstraint("HorizontalCenter", voluL, 0, "HorizontalCenter", btnIncVolu);
		layout.putConstraint("East", btnIncVolu, -15, "East", panel);
		layout.putConstraint("North", btnIncVolu, 0, "South", voluL);
		layout.putConstraint("North", btnDecVolu, 0, "South", btnIncVolu);
		layout.putConstraint("West", btnDecVolu, 0, "West", btnIncVolu);
		layout.putConstraint("East", btnDecVolu, 0, "East", btnIncVolu);
		layout.putConstraint("North", voluD, 0, "South", btnDecVolu);
		layout.putConstraint("HorizontalCenter", voluD, 0, "HorizontalCenter", btnDecVolu);
		layout.putConstraint("VerticalCenter", saveL, 0, "VerticalCenter", btnSave1);
		layout.putConstraint("East", saveL, -5, "West", btnSave1);
		layout.putConstraint("VerticalCenter", loadL, 0, "VerticalCenter", btnLoad1);
		layout.putConstraint("East", loadL, -5, "West", btnLoad1);
		layout.putConstraint("VerticalCenter", presetL, 0, "South", saveL);
		layout.putConstraint("East", presetL, 0, "West", saveL);
		layout.putConstraint("South", btnSave1, 0, "North", btnLoad1);
		layout.putConstraint("West", btnSave1, 0, "HorizontalCenter", panel);
		layout.putConstraint("South", btnSave2, 0, "South", btnSave1);
		layout.putConstraint("West", btnSave2, 0, "East", btnSave1);
		layout.putConstraint("South", btnSave3, 0, "South", btnSave2);
		layout.putConstraint("West", btnSave3, 0, "East", btnSave2);
		layout.putConstraint("South", btnSave4, 0, "South", btnSave3);
		layout.putConstraint("West", btnSave4, 0, "East", btnSave3);
		layout.putConstraint("South", btnSave5, 0, "South", btnSave4);
		layout.putConstraint("West", btnSave5, 0, "East", btnSave4);
		layout.putConstraint("South", btnLoad1, -15, "South", panel);
		layout.putConstraint("West", btnLoad1, 0, "HorizontalCenter", panel);
		layout.putConstraint("North", btnLoad2, 0, "North", btnLoad1);
		layout.putConstraint("West", btnLoad2, 0, "East", btnLoad1);
		layout.putConstraint("North", btnLoad3, 0, "North", btnLoad2);
		layout.putConstraint("West", btnLoad3, 0, "East", btnLoad2);
		layout.putConstraint("North", btnLoad4, 0, "North", btnLoad3);
		layout.putConstraint("West", btnLoad4, 0, "East", btnLoad3);
		layout.putConstraint("North", btnLoad5, 0, "North", btnLoad4);
		layout.putConstraint("West", btnLoad5, 0, "East", btnLoad4);

		btnAM.setForeground(Color.RED);

		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/sound.gif")));
		setSize(325, 220);
		setResizable(false);
		setTitle("Radio Receiver");
		addWindowListener(this);
		add(panel);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private class PickBand implements ActionListener {
		private Label freqD;
		private Button btnAM, btnFM;
		public PickBand(Label freqD, Button btnAM, Button btnFM) {
			this.freqD = freqD;
			this.btnAM = btnAM;
			this.btnFM = btnFM;
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "AM") {
				band = false;
				btnAM.setForeground(Color.RED);
				btnFM.setForeground(Color.BLACK);
				freqD.setText(Short.toString(freqAM));
			} else {
				band = true;
				btnFM.setForeground(Color.RED);
				btnAM.setForeground(Color.BLACK);
				freqD.setText(String.format("%.1f", freqFM));
			}
		}
	}

	private class IncFreq implements ActionListener {
		private Label freqD;
		public IncFreq(Label freqD) {
			this.freqD = freqD;
		}
		public void actionPerformed(ActionEvent e) {
			if(band) {
				if(freqFM < 108.0) {
					freqFM += 0.2;
				} else {
					freqFM = 88.0;
				}
				freqD.setText(String.format("%.1f", freqFM));
			} else {
				if(freqAM < 1700) {
					freqAM += 10;
				} else {
					freqAM = 540;
				}
				freqD.setText(Short.toString(freqAM));
			}
		}
	}

	private class DecFreq implements ActionListener {
		private Label freqD;
		public DecFreq(Label freqD) {
			this.freqD = freqD;
		}
		public void actionPerformed(ActionEvent e) {
			if(band) {
				if(freqFM > 88.0) {
					freqFM -= 0.2;
				} else {
					freqFM = 108.0;
				}
				freqD.setText(String.format("%.1f", freqFM));
			} else {
				if(freqAM > 540) {
					freqAM -= 10;
				} else {
					freqAM = 1700;
				}
				freqD.setText(Short.toString(freqAM));
			}
		}
	}

	private class SavePreset implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = Integer.parseInt(e.getActionCommand());
			if(band)
				switch(index) {
					case 1:
						presetFM[0] = freqFM;
						return;
					case 2:
						presetFM[1] = freqFM;
						return;
					case 3:
						presetFM[2] = freqFM;
						return;
					case 4:
						presetFM[3] = freqFM;
						return;
					case 5:
						presetFM[4] = freqFM;
				}
			else
				switch(index) {
					case 1:
						presetAM[0] = freqAM;
						return;
					case 2:
						presetAM[1] = freqAM;
						return;
					case 3:
						presetAM[2] = freqAM;
						return;
					case 4:
						presetAM[3] = freqAM;
						return;
					case 5:
						presetAM[4] = freqAM;
				}
		}
	}

	private class LoadPreset implements ActionListener {
		private Label freqD;
		public LoadPreset(Label freqD) {
			this.freqD = freqD;
		}
		public void actionPerformed(ActionEvent e) {
			int index = Integer.parseInt(e.getActionCommand());
			if(band) {
				switch(index) {
					case 1:
						freqFM = presetFM[0];
						break;
					case 2:
						freqFM = presetFM[1];
						break;
					case 3:
						freqFM = presetFM[2];
						break;
					case 4:
						freqFM = presetFM[3];
						break;
					case 5:
						freqFM = presetFM[4];
				}
				freqD.setText(String.format("%.1f", freqFM));
			} else {
				switch(index) {
					case 1:
						freqAM = presetAM[0];
						break;
					case 2:
						freqAM = presetAM[1];
						break;
					case 3:
						freqAM = presetAM[2];
						break;
					case 4:
						freqAM = presetAM[3];
						break;
					case 5:
						freqAM = presetAM[4];
				}
				freqD.setText(Short.toString(freqAM));
			}
		}
	}

	private class IncBala implements ActionListener {
		private Label balaD;
		public IncBala(Label balaD) {
			this.balaD = balaD;
		}
		public void actionPerformed(ActionEvent e) {
			if(bala < 100) {
				bala += 10;
				balaD.setText(Byte.toString(bala));
			}
		}
	}

	private class DecBala implements ActionListener {
		private Label balaD;
		public DecBala(Label balaD) {
			this.balaD = balaD;
		}
		public void actionPerformed(ActionEvent e) {
			if(bala > 0) {
				bala -= 10;
				balaD.setText(Byte.toString(bala));
			}
		}
	}

	private class IncFade implements ActionListener {
		private Label fadeD;
		public IncFade(Label fadeD) {
			this.fadeD = fadeD;
		}
		public void actionPerformed(ActionEvent e) {
			if(fade < 100) {
				fade += 10;
				fadeD.setText(Byte.toString(fade));
			}
		}
	}

	private class DecFade implements ActionListener {
		private Label fadeD;
		public DecFade(Label fadeD) {
			this.fadeD = fadeD;
		}
		public void actionPerformed(ActionEvent e) {
			if(fade > 0) {
				fade -= 10;
				fadeD.setText(Byte.toString(fade));
			}
		}
	}

	private class IncBass implements ActionListener {
		private Label bassD;
		public IncBass(Label bassD) {
			this.bassD = bassD;
		}
		public void actionPerformed(ActionEvent e) {
			if(bass < 100) {
				bass += 10;
				bassD.setText(Byte.toString(bass));
			}
		}
	}

	private class DecBass implements ActionListener {
		private Label bassD;
		public DecBass(Label bassD) {
			this.bassD = bassD;
		}
		public void actionPerformed(ActionEvent e) {
			if(bass > 0) {
				bass -= 10;
				bassD.setText(Byte.toString(bass));
			}
		}
	}

	private class IncTreb implements ActionListener {
		private Label trebD;
		public IncTreb(Label trebD) {
			this.trebD = trebD;
		}
		public void actionPerformed(ActionEvent e) {
			if(treb < 100) {
				treb += 10;
				trebD.setText(Byte.toString(treb));
			}
		}
	}

	private class DecTreb implements ActionListener {
		private Label trebD;
		public DecTreb(Label trebD) {
			this.trebD = trebD;
		}
		public void actionPerformed(ActionEvent e) {
			if(treb > 0) {
				treb -= 10;
				trebD.setText(Byte.toString(treb));
			}
		}
	}

	private class IncVolu implements ActionListener {
		private Label voluD;
		public IncVolu(Label voluD) {
			this.voluD = voluD;
		}
		public void actionPerformed(ActionEvent e) {
			if(volu < 100) {
				volu += 10;
				voluD.setText(Byte.toString(volu));
			}
		}
	}

	private class DecVolu implements ActionListener {
		private Label voluD;
		public DecVolu(Label voluD) {
			this.voluD = voluD;
		}
		public void actionPerformed(ActionEvent e) {
			if(volu > 0) {
				volu -= 10;
				voluD.setText(Byte.toString(volu));
			}
		}
	}

	public void windowClosing(WindowEvent e) {
		setVisible(false);
		dispose();
		System.exit(0);
	}
	public void windowOpened(WindowEvent e) { }
	public void windowClosed(WindowEvent e) { }
	public void windowIconified(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowActivated(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
}