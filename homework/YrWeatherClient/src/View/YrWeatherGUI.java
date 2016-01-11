package View;

import javax.swing.*;

import Model.LocationForecast;

import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * Everything that is concerned with the GUI of the application is maintained
 * within this class
 */
public class YrWeatherGUI {

	private JFrame frame;
	private JButton runButton;
	private JPanel outputPanel, inputPanel;
	private JTextField locationField, hourSelectField, ttlField;
	private JTextArea outputTextBox;
	private JScrollPane scrollOutputBox;
	private JLabel labelCityButton, labelTimeButton;
	private JComboBox<String> cityDropDownBox;
	private LocationForecast[] places;
	private JFrame frameTTL;

	/**
	 * Use this to create and setup all GUI components and show the GUI.
	 */
	public void createAndShowGUI() {
		String cities[] = new String[] { places[0].getLocationName(),
				places[1].getLocationName(), places[2].getLocationName() };

		// Create initialize the main GUI objects
		frame = new JFrame("YR.no Weather Client");
		frameTTL = new JFrame("TTL");

		inputPanel = new JPanel();
		outputPanel = new JPanel();

		runButton = new JButton("Compute");
		locationField = new JTextField();
		hourSelectField = new JTextField();
		outputTextBox = new JTextArea();
		scrollOutputBox = new JScrollPane(outputTextBox);
		labelCityButton = new JLabel("City");
		labelTimeButton = new JLabel("Time(HH)");
		cityDropDownBox = new JComboBox<String>(cities);

		locationField.setPreferredSize(new Dimension(100, 25));
		hourSelectField.setPreferredSize(new Dimension(100, 25));
		scrollOutputBox.setPreferredSize(new Dimension(400, 400));
		scrollOutputBox
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outputTextBox.setLineWrap(true);
		// outputTextBox.setEnabled(false);

		// Use setText for writing output console

		// Set the layout of the window
		frame.setLayout(new BorderLayout());

		// Add components to the Window
		frame.add(inputPanel, BorderLayout.NORTH);
		frame.add(outputPanel, BorderLayout.SOUTH);

		// Add components to INPUT panel
		inputPanel.add(runButton);

		inputPanel.add(labelTimeButton);
		inputPanel.add(hourSelectField);
		inputPanel.add(labelCityButton);
		inputPanel.add(locationField);
		// inputPanel.add(cityDropDownBox);

		// Add components to OUTPUT panel
		outputPanel.add(scrollOutputBox);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display the window.
		frame.setVisible(true);
		frame.pack();
		frame.setSize(500, 500);
		// CACHE WINDOW
		ttlField = new JTextField();
		ttlField.setPreferredSize(new Dimension(50, 50));
		frameTTL.add(ttlField);
		frameTTL.setVisible(true);
		frameTTL.pack();
		frameTTL.setSize(200, 75);
		// Event handling
	}

	public int getTtlField() throws NumberFormatException {
		return Integer.parseInt(ttlField.getText());
	}

	public JButton getRunButton() {
		return runButton;
	}

	public int getTime() throws NumberFormatException {
		return Integer.parseInt(hourSelectField.getText());
	}

	public String getLocation() {
		return locationField.getText();
	}

	public void setOutput(String s) {
		outputTextBox.setText(s);
	}
	
	
	/**Display an error message
	 * @Param errorMessage The error message string shown to the user.
	*/
	public void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(frame, errorMessage);
	}

	public String getLocationField() {
		return locationField.getText();
	}

	public String getHourSelectField() {
		return hourSelectField.getText();
	}

	public JComboBox<String> getCityDropDownBox() {
		return cityDropDownBox;
	}

	public void setPlaces(LocationForecast[] places) {
		this.places = places;
	}

	public void setTtlField(int ttl) {
		this.ttlField.setText(Integer.toString(ttl));
	}

}