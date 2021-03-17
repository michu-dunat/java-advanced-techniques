package modulAplikacji;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Graphics2D;
import javax.swing.JTextArea;


public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() throws IOException {
		initialize();
	}
	
	Set<Path> paths = null;
	WeakHashMap<Path, File> weakRefs = new WeakHashMap<>();
	JTextArea textArea;
	
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 1400, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		JButton btnOpenJFileChooser = new JButton("Wybierz folder");
		btnOpenJFileChooser.setBounds(625, 20, 150, 25);
		
		DefaultListModel<Path> defaultListModel = new DefaultListModel<>();
		JList<Path> list = new JList<Path>(defaultListModel);
		list.setBounds(20, 70, 450, 650);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(500, 70, 850, 650);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(500, 70, 850, 650);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(20, 20, 500, 25);
		
		
		btnOpenJFileChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser("C:\\Users\\konta\\Desktop");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    int returnVal = chooser.showOpenDialog(frame);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	defaultListModel.clear();
					String pathToDirectory = chooser.getSelectedFile().getAbsolutePath();
					   
					Path path = Paths.get(pathToDirectory);
					
					try {
						paths = getFilesInDirectory(path);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					for (Path path2 : paths) {
						defaultListModel.addElement(path2);
					}
					
			       
			    } else {
			    	textArea.setText("Nie wybrano folderu.");
			    }
			}
		});
		
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

			        if (list.getSelectedIndex() == -1) {
			       
			        } else {
			        	Path selectedPath = list.getSelectedValue();
			        	String extensionString = getExtensionByStringHandling(selectedPath.toString()).get();
			        	if (extensionString.equals("txt")) {
			        		lblNewLabel.setVisible(false);
							textPane.setVisible(true);
							
							
							File file = loadFile(selectedPath);
							String text = null;
							try {
								text = readTextFromFile(file);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
							textPane.setText(text);
							
							
						} else if (extensionString.equals("png")) {
							lblNewLabel.setVisible(true);
							textPane.setVisible(false);
							
							File file = loadFile(selectedPath);
							
							BufferedImage image = null;
							try {
								image = ImageIO.read(file);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							
							if (image.getHeight() > 650 || image.getWidth() > 850) {
								try {
									image = scaleImageToFillLabel(image, 850, 650);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
							
							lblNewLabel.setIcon(new ImageIcon(image));
							
						} else {
							JOptionPane.showMessageDialog(frame,
								    "Wybrano plik o niekompatybilnym rozszerzeniu.",
								    "Warning",
								    JOptionPane.WARNING_MESSAGE);
						}
			        }
			    }
			}
		});
		
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnOpenJFileChooser);
		frame.getContentPane().add(list);
		frame.getContentPane().add(textPane);
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(textArea);
	}
	
	private String readTextFromFile(File file) throws FileNotFoundException {
		Scanner reader = new Scanner(file);
		StringBuilder builder = new StringBuilder();
		while (reader.hasNextLine()) {
			String data = reader.nextLine();
			builder.append(data + "\n");
		}
		reader.close();
		return builder.toString();
	}
	
	private File loadFile(Path selectedPath) {
		File file;
		
		if (weakRefs.containsKey(selectedPath)) {
			file = weakRefs.get(selectedPath);
			textArea.setText("Plik odczytano z pamiêci.");
		} else {
			file = new File(selectedPath.toString());
			weakRefs.put(selectedPath, file);
			textArea.setText("Plik odczytano z dysku.");
		}
		
		return file;
	}
	
	private Set<Path> getFilesInDirectory(Path pathToDirectory) throws IOException {
	    Set<Path> allFilesInDirectory = Files.list(pathToDirectory)
	          .filter(Files::isRegularFile)
	          .collect(Collectors.toSet());
	    
	    return allFilesInDirectory;
	}
	
	private BufferedImage scaleImageToFillLabel(BufferedImage image, int width, int height) throws IOException {
	    BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    
	    Graphics2D graphics2D = scaledImage.createGraphics();
	    graphics2D.drawImage(image, 0, 0, width, height, null);
	    graphics2D.dispose();
	    
	    return scaledImage;
	}
	
	private Optional<String> getExtensionByStringHandling(String filename) {
	    return Optional.ofNullable(filename)
	      .filter(f -> f.contains("."))
	      .map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
}
