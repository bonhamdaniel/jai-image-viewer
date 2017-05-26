/*
 * ImageViewer.java requires no other files. 
 */

// Import all necessary libraries
import java.awt.*;
import java.awt.event.*;
import java.awt.image.renderable.ParameterBlock;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.File;
import javax.media.jai.*;
import com.sun.media.jai.widget.DisplayJAI;

public class ImageViewer extends JPanel implements ActionListener {	
	static JFrame frame = new JFrame("JAI Image Viewer"); // Creates JFrame, with title, to contain Image Viewer
	JPanel contentPane = new JPanel(new BorderLayout()); // Creates JPanel to contain images
    JMenuItem openFile, exit; // Options for File menu
    JButton zoomIn, zoomOut, normal; // Buttons for zooming options
    RenderedOp image; // JAI class to hold image
    DisplayJAI displayImage; // JAI class instance used to display image
    JScrollPane scrollPane; // Provides scrolling capability
    float zoom = 1;
    
    // main() method
    public static void main(String[] args) {
    	//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); // displays GUI
            } // run
        }); // invokeLater
    } // main() method
    
    // ImageViewer() constructor - sets up ImageViewer content
 	public ImageViewer() {
 		JPanel buttonPane = new JPanel(new FlowLayout()); // creates JPanel for zoom buttons
 		
 		// Loads and sets initial image for display
 		image = JAI.create("fileload", new File("src/kess.jpg").getPath()); // loads initial image into viewer
 		displayImage = new DisplayJAI(image); // creates JAI displayImage from initial image
 		scrollPane = new JScrollPane(displayImage); // creates a scroll pane for the display image
 		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS); // horizontal scroll always present
 		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // vertical scroll always present
 		contentPane.add(scrollPane, BorderLayout.CENTER); // adds the scrollPane to the contentPane
 		
 		// Creates buttons and sets up buttonPane
 		zoomIn = new JButton("Zoom In"); // creates button for zooming in
 		zoomOut = new JButton("Zoom Out"); // creates button for zooming out
 		normal = new JButton("Reset Image"); // creates button to reset to initial image size
 		zoomIn.addActionListener(this); // add actionListener to zoom in button
 		zoomOut.addActionListener(this); // add actionListener to zoom out button
 		normal.addActionListener(this); // add actionListener to normal button
 		buttonPane.add(zoomIn, BorderLayout.WEST); // add zoom in button to buttonPane
 		buttonPane.add(zoomOut, BorderLayout.CENTER); // add zoom out button to buttonPane
 		buttonPane.add(normal, BorderLayout.EAST); // add normal button to buttonPane
 		buttonPane.setBackground(Color.DARK_GRAY); // sets buttonPane background to dark grey
 		
 		contentPane.add(buttonPane, BorderLayout.SOUTH); // adds buttonPane to viewer contentPane
 		
 		zoomOut.setEnabled(false); // greys out zoomOut button as image is initial size
 		normal.setEnabled(false); // greys out normal button as image is initial size
 	} // ImageViewer() constructor
 	
    // createAndShowGUI() method - handles GUI display and options
    private static void createAndShowGUI() {
    	try {
    		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // sets GUI look
    	} catch (Exception e) {
    		e.printStackTrace(); // prints error details
    	} // catch()
    	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets program to exit if frame closed

        // Create and set up JFrame content
        ImageViewer viewer = new ImageViewer(); // new ImageViewer instance
        viewer.contentPane.setOpaque(true); // sets viewer contentPane to opaque
        frame.setJMenuBar(viewer.createMenuBar()); // calls createMenuBar to create menu bar for viewer
        frame.setContentPane(viewer.contentPane); // sets the frame contentPane to the viewer contentPane
        viewer.contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // creates a small empty border around contentPane
        
        //Display the window.
        frame.pack(); // packs the frame
        frame.setVisible(true); // sets the frame to visible
    } // createAndShowGUI() method
    
    // createMenuBar() method - sets up a menu bar and returns it to calling object
	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();// Create and set up the menu bar
		JMenu menu; // used for File menu
		
        // Create the first menu - File
        menu = new JMenu("File"); // sets menu title
        menu.setMnemonic(KeyEvent.VK_F); // sets shortcut key for menu
        menuBar.add(menu); // adds menu to menuBar
        
        // Create and add first File menu item - openFile
        openFile = new JMenuItem("Open File", KeyEvent.VK_O); // creates menu item with title and shortcut
        openFile.addActionListener(this); // adds actionListener to menu item
        menu.add(openFile); // adds menu item to File menu
        
        // Create and add second File menu item - exit
        exit = new JMenuItem("Exit", KeyEvent.VK_X); // creates menu item with title and shortcut
        exit.addActionListener(this); // adds actionListener to menu item
        menu.add(exit); // adds menu item to File menu
             
        return menuBar;// Return the created menu bar
	} // createMenuBar() method

	// actionPerformed() method - handles various viewer events
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser("src/"); // creates file chooser to be used with openFile menu item
		
		// Determines which event has occurred
		if (e.getSource() == openFile) { // openFile menu item has been chosen
			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { // image file has been chosen by the user
				File file = fc.getSelectedFile(); // creates file with image selected by the user
				zoom = 1.0f;
				image = JAI.create("fileload", file.getPath()); // loads file into JAI class instance
				displayImage.set(image); // sets displayImage to image selected by the user
				zoomIn.setEnabled(true); // enables zoomIn button
				zoomOut.setEnabled(false); // disables zoomOut button because image is initial size
				normal.setEnabled(false); // disables normal button because image is initial size
			} // if(APPROVE_OPTION)
		} else if (e.getSource() == zoomIn) { // zoomIn button clicked
			zoom *= 1.05f;
			displayImage.set(performZoom(1.05f)); // performZoom() method called, returned image displayed
			zoomOut.setEnabled(true); // enables zoomOut button
			normal.setEnabled(true); // enables normal button
			if (zoom > Math.pow(1.05,10)) { // zoomed to maximum size
				zoomIn.setEnabled(false); // disables zoomIn button
			}// if(image at max size)
		} else if (e.getSource() == zoomOut) { // zoomOut button clicked
			zoom *= 0.95238f;
			displayImage.set(performZoom(0.95238f)); // performZoom() method called, returned image displayed
			zoomIn.setEnabled(true); // zoomIn button enabled
			if (zoom < 1 ) { // image is at initial size
				zoomOut.setEnabled(false); // disables zoomOut button
				normal.setEnabled(false); // disables normal button
			}// if (image is initial size)
		} else if (e.getSource() == normal) { // normal button clicked
			zoom = 1.0f;
			displayImage.set(image); // performZoom() method called, returned image displayed
			zoomIn.setEnabled(true); // enables zoomIn button
			zoomOut.setEnabled(false); // disables zoomOut button
			normal.setEnabled(false); // disables normal button
		} else { // exit menu option chosen
			System.exit(0); // exits the program
		} // exit menu option 
	} // actionPerformed() method

	// performZoom() method - performs selected zoom and adjusts view appropriately
	public PlanarImage performZoom(float scale) {
		Rectangle bounds = scrollPane.getViewport().getBounds(); // gets viewport bounds, used for adjusting view
		Rectangle currentView = scrollPane.getViewport().getViewRect(); // gets viewport rectangle, used for adjusting view
		
		// handles image zoom
		ParameterBlock params = new ParameterBlock(); // creates new params instance
		params.addSource(image); // adds image to params
		params.add(1.0f * zoom); // x scale factor
		params.add(1.0f * zoom); // y scale factor
		params.add(0.0F); // x translate
		params.add(0.0F); // y translate
		params.add(new InterpolationBicubic(128)); // interpolation method
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, 
				RenderingHints.VALUE_RENDER_QUALITY);
		hints.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY); // gets hints for rendering image
		PlanarImage currentImage = JAI.create("Scale", params, hints); // creates new zoomed image

		// finds x & y coordinates at center of view, used for view adjustment
		float xCenter = currentView.x + bounds.width / 2; // x coordinate at center
		float yCenter = currentView.y + bounds.height / 2; // y coordinate at center
		
		// scales the x & y center coordinates by the zoom value, used for view adjustment
		float xNewCenter = xCenter * scale; // scaled x coordinate at center
		float yNewCenter = yCenter * scale; // scaled y coordinate at center
		
		// finds new x & y coordinates for adjusted view
		float newX = xNewCenter - bounds.width / 2; // new x view coordinate
		float newY = yNewCenter - bounds.height / 2; // new y view coordinate
		
		scrollPane.getViewport().setViewPosition(new Point((int)newX, (int)newY)); // sets new viewing position, adjusted for zoom

		return currentImage; // returns zoomed image
	} // performZoom() method
} // ImageViewer class
