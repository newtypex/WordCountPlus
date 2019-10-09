package wordCountPlus;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import batchWordCount.FileDrop.Listener;

public class WordCount {

    public static void main( String[] args )
    {
    	JFrame frame = new JFrame("月華爛漫字数统计器v1.01  By不爱幼女的大苹果" );
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int frame_width = 905;
        int frame_height = 775;
        frame.setBounds((int)(dim.getWidth()-frame_width)/2, (int)(dim.getHeight()-frame_height)/2, frame_width, frame_height );
        frame.setLayout(null); //guarantees that setBounds is processed
        Container ct = frame.getContentPane();
   
        // Set Fonts
        Font font1 = new Font("微软雅黑",Font.PLAIN,15);
        Font font2 = new Font("微软雅黑",Font.PLAIN,13);
        UIManager.put("OptionPane.messageFont", new FontUIResource(font2));
        UIManager.put("Menu.font", new FontUIResource(font1));
        UIManager.put("MenuItem.font", new FontUIResource(font2));
        UIManager.put("Label.font", new FontUIResource(font2));
        UIManager.put("CheckBox.font", new FontUIResource(font2));
        UIManager.put("Button.font", new FontUIResource(font1));
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("帮助");
        JMenuItem item1 = new JMenuItem("关于本工具");
        JMenuItem item2 = new JMenuItem("BUG报告");
        JMenuItem item3 = new JMenuItem("恢复默认选项");
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "RingoWordCount v1.00\nBy 不爱幼女的大苹果\n\n仅供内部交流使用，请勿作他用", "关于本工具", JOptionPane.INFORMATION_MESSAGE);
			}
		});
        
        item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "请直接联系大苹果！", "BUG报告", JOptionPane.INFORMATION_MESSAGE);
			}
		});
                
        // The text area for file list. The only way to edit is Drag & Drop.
        JTextArea ta = new JTextArea();
        ta.setEditable(false); 
        JScrollPane jsp1 = new JScrollPane( ta );
        jsp1.setBounds(340, 40, 540, 300);
        ct.add(jsp1);
        
        // Labels
        JLabel label1 = new JLabel("请拖拽文本或文件夹到此处：");
        label1.setBounds(340, 10, 200, 30);
        ct.add(label1);
        
        JLabel label2 = new JLabel("忽略以下成对符号间的内容：");
        label2.setBounds(30, 560, 250, 30);
        ct.add(label2);
        
        // Checkboxs
        final JCheckBox cbDelDuplicate = new JCheckBox("忽略相邻重复行（建议开启）");
        cbDelDuplicate.setName("cbDelDuplicate");
        cbDelDuplicate.setBounds(10, 500, 250, 30);
        ct.add(cbDelDuplicate);
        
        final JCheckBox cbDelDigits = new JCheckBox("忽略半角数字");
        cbDelDigits.setName("cbDelDigits");
        cbDelDigits.setBounds(10, 530, 250, 30);
        ct.add(cbDelDigits);
        
        final JCheckBox cbDelBracketContents1 = new JCheckBox("方括号[　]");
        cbDelBracketContents1.setName("cbDelBracketContents1");
        cbDelBracketContents1.setBounds(60, 590, 100, 30);
        ct.add(cbDelBracketContents1);
        
        final JCheckBox cbDelBracketContents2 = new JCheckBox("方头括号【　】");
        cbDelBracketContents2.setName("cbDelBracketContents2");
        cbDelBracketContents2.setBounds(180, 590, 150, 30);
        ct.add(cbDelBracketContents2);
        
        // This is an array list used for saving and loading all options.
        ArrayList<JCheckBox> optionList = new ArrayList<JCheckBox>();
        optionList.add(cbDelDuplicate);
        optionList.add(cbDelDigits);
        optionList.add(cbDelBracketContents1);
        optionList.add(cbDelBracketContents2);
        
        try {
        	FileInputStream input = new FileInputStream(WordCount.class.getResource("resource/config.properties").getPath()); //依赖于bin\config.properties
        	Properties properties = new Properties();
        	properties.load(input);
            for (JCheckBox option : optionList) {
            	System.out.println(option.getName() + "=====" + properties.getProperty( option.getName())  );
            	if (properties.getProperty((option.getName())).equals("true")) {
            		option.setSelected(true);
            	}
            	else {
            		option.setSelected(false);
            	}
            }
            input.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error occurred when loading configurations", "Error", JOptionPane.ERROR_MESSAGE);
		}
        
        item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbDelDuplicate.setSelected(true);
				cbDelDigits.setSelected(true);
				cbDelBracketContents1.setSelected(true);
				cbDelBracketContents2.setSelected(true);
			}
		});
        
        ActionListener storePropertiesUponClick = new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		try {
        			FileOutputStream out = new FileOutputStream(WordCount.class.getResource("resource/config.properties").getPath());
					Properties properties = new Properties();
					
					for (JCheckBox option: optionList) {
						properties.setProperty(option.getName(), option.isSelected() ? "true" : "false" );
					}
					properties.store(out, null);
					out.close();
					
				} catch (Exception e2) {					
					JOptionPane.showMessageDialog(null, "Error occurred when saving configurations", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		
		cbDelDuplicate.addActionListener(storePropertiesUponClick);
		cbDelDigits.addActionListener(storePropertiesUponClick);
		cbDelBracketContents1.addActionListener(storePropertiesUponClick);
		cbDelBracketContents2.addActionListener(storePropertiesUponClick);
        
        // Panel for image display
        BackgroundPanel bgp;        
        bgp = new BackgroundPanel(new ImageIcon(WordCount.class.getResource("resource/logo.png").getPath()).getImage());
        // would search bin/ProjectName/resource/logo.jpg
        bgp.setBounds(10,10,320,460);  
        ct.add(bgp);
        
        // Output Table
        Object[][] rowData = new Object[1][2];
        Object[] columnNames = {"文件名","字数"};
        JTable table = new JTable( rowData, columnNames);
        JScrollPane jsp2 = new JScrollPane( table );
        jsp2.setBounds(340, 400, 540, 300);
        ct.add(jsp2);
        
        // Buttons
        final JButton button1 = new JButton("开始统计");
        button1.setBounds(480, 355, 100, 30);
        ct.add(button1);
        
        // Button to clear the file list
        final JButton button2 = new JButton("清空列表");
        button2.setBounds(660, 355, 100, 30);
        ct.add(button2);
        
        button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta.setText("");
			}
		});
        
        button1.addActionListener(new ActionListener() {
        	
        	// Set these as class members to avoid passing them between actionPerformed(), visit() and wordCount().    	
        	DefaultTableModel model;
        	InputStreamReader is;
        	boolean isDelBracketContents1;
        	boolean isDelBracketContents2;
        	boolean isDelDuplicate;
        	boolean isDelDigits;      	
        	char[] chs = new char[4096];
			int len;
			String text;
			String charset = "UTF-8"; // Alternatives: UTF-8, MS932, Shift_JIS，...
			
			public void wordCount(File file) {
				
				try {					
					is = new InputStreamReader(new FileInputStream(file), charset);
					text ="";
					while ((len = is.read(chs))!=-1) {
						String temp = new String(chs, 0, len);
						text += temp;
					}
					is.close();

					if (isDelBracketContents1) {
						text = text.replaceAll("\\[.*\\]","");
					}
					else {
						text = text.replaceAll("[\\[\\]]","");
					}
					
					if (isDelBracketContents2) {
						text = text.replaceAll("【.*】", ""); 
					}
					else {
						text = text.replaceAll("[【】]","");
					}
					
					if (isDelDigits) text = text.replaceAll("[0123456789]", "");
					
					text = text.replaceAll("[:：\\(\\)\\-\\/><（）「」『』＜＞“”‘’！？、。・―…]", "");
					
					if (isDelDuplicate) {
						String regex = "\r\n(.*)\r\n";
						Pattern p = Pattern.compile(regex);
						Matcher m = p.matcher(text);
						while (m.find()) {
							text = text.replaceAll(m.group()+m.group(1), "\r\n"+m.group(1));
						}
					}

					text = text.replaceAll("[\n\r]", "");
					int count = text.length();
					
//						// The following shows the results of every line:
//						int count=0;
//						int totalcount=0;
//						for (char c: text.toCharArray()) {
//							if (c=='\r') {
//								System.out.print(":" + count);
//								totalcount += count;
//								count=0;
//							}
//							else if (c=='\n') {
//								System.out.print("\r\n");
//							}
//							else {
//								System.out.print(c);
//								count +=1;
//							}
//						}
//						System.out.println("Total:   "+ totalcount);

					String fileName = file.getName();
					model.addRow(new Object[] {fileName.substring(fileName.lastIndexOf('\\')+1) ,count});						
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "发生错误，请记录情况并联系大苹果", "BUG报告", JOptionPane.INFORMATION_MESSAGE);
				}
			} // end wordCount()
						
			// traverse (fileList) will traverse the input file list recursively, and count all the txt files.
			public void traverse (File[] fileList) {
				for (File file: fileList) {
					if (file.isDirectory()) { 
						traverse(file.listFiles());
					}
					else if (file.getName().endsWith("txt")) { //should add support to other formats...
						wordCount(file);
					}
				}
			}
			
			// actionPerformed(e) is called when button is clicked. 
        	public void actionPerformed(ActionEvent e) {
        		// Step 1. Get all the option settings
        		isDelBracketContents1 = cbDelBracketContents1.isSelected();
        		isDelBracketContents2 = cbDelBracketContents2.isSelected();
        		isDelDuplicate = cbDelDuplicate.isSelected();
        		isDelDigits = cbDelDigits.isSelected();
        		
        		// Step 2. Reformat the table into an empty 0x2 table. Data will be inserted later
				model = new DefaultTableModel(columnNames, 0); 
				table.setModel(model);
				
				// Step 3. Get the file list, and call the traverse() method
        		String[] fileNameList = ta.getText().split("\n");
        		File[] fileList = new File[fileNameList.length];
				for (int i=0; i<fileNameList.length; i++) {
					fileList[i] = new File(fileNameList[i]);
				}
				traverse(fileList);
				
				// Step 4. Repaint the table to update the changes 
				table.repaint();
        	}
				
			} // end new ActionListener
		); // end addActionListener

        
        new FileDrop( System.out, ta, /*dragBorder,*/ new FileDrop.Listener()
        {   public void filesDropped( java.io.File[] files )
            {   for( int i = 0; i < files.length; i++ )
                {   try
                    {   ta.append( files[i].getCanonicalPath() + "\n" );
                    } 
                    catch( java.io.IOException e ) {}
                }   // end for: through each dropped file
            }   // end filesDropped
        }); // end FileDrop.Listener
        
        frame.setDefaultCloseOperation( frame.EXIT_ON_CLOSE );
        frame.setVisible(true);
    }   // end main

}
