import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;


public abstract class TextEditor implements ActionListener  {
        JFrame frame;
        JPanel lineCounterPanel,toolBarPanel,mainPanel=new JPanel(new BorderLayout());
        JPanel[] newTabPanel = new JPanel[50];
        JTabbedPane tabbedPane;
        JLabel newTabNameLabel;
        JLabel[] nameLabel = new JLabel[50];
        JTextArea newTabTextArea,lineCountTextArea;
        JTextArea[] textArea = new JTextArea[50];
        JButton[] closeButton=new JButton[50];
        JButton toolBarButton1,toolBarButton2,toolBarButton3,toolBarButton4,
                toolBarButton5,toolBarButton6,toolBarButton7,toolBarButton8,
                toolBarButton9,toolBarButton10,toolBarButton11,toolBarButton12,
                toolBarButton13,toolBarButton14,toolBarButton15,toolBarButton16;
        int closeCount=0,location=0,maxTabCount=0;
        UndoManager manager=new UndoManager();
        boolean save[] = new boolean[50];
        
    // constructor
    public TextEditor()  {

         // creating frame with title as "My Text Editor"
        frame = new JFrame("GPad");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // to place the frame at center
        //frame.setLocationRelativeTo(null);

        // setting size of frame
        frame.setSize(600, 600);

        // creating menu bar
        JMenuBar menuBar = new JMenuBar();
        


        // creating menus
        JMenu FileMenu = new JMenu("File");
        JMenu EditMenu = new JMenu("Edit");
        JMenu SearchMenu = new JMenu("Search");
        JMenu HelpMenu = new JMenu("Help");

        // adding menus to the menu bar
        menuBar.add(FileMenu);
        menuBar.add(EditMenu);
        menuBar.add(SearchMenu);
        menuBar.add(HelpMenu);


        // CREATING MENU ITEMS

        // creating menu items for file menu
        JMenuItem FileMenuItems[] = {
            new JMenuItem("New"), new JMenuItem("Open"), new JMenuItem("Open Containing Folder"),
            new JMenuItem("Save"), new JMenuItem("Save As"),
            new JMenuItem("Rename"), new JMenuItem("Close"), new JMenuItem("Close All"),
            new JMenuItem("Print"), new JMenuItem("Exit")
        };
    
        
        // creating menu items for edit menu
        JMenuItem EditMenuItems[] = {new JMenuItem(UndoManagerHelper.getUndoAction(manager)), new JMenuItem(UndoManagerHelper.getRedoAction(manager)),
            new JMenuItem(new DefaultEditorKit.CutAction()),
            new JMenuItem(new DefaultEditorKit.CopyAction()),
            new JMenuItem(new DefaultEditorKit.PasteAction()),
            new JMenuItem("Delete"), new JMenuItem("Select All"),
            new JMenuItem("Zoom In"),new JMenuItem("Zoom Out"), 
            new JMenuItem("Night Mode On"),new JMenuItem("Night Mode Off"),
            new JMenuItem("Time Stamp")};

            EditMenuItems[2].setText("Cut");
            EditMenuItems[3].setText("Copy");
            EditMenuItems[4].setText("Paste");


        // creating menu items for search menu
        JMenuItem SearchMenuItems[] = {new JMenuItem("Find..."), 
            //new JMenuItem("Find Next"), new JMenuItem("Find Previous"),
            new JMenuItem("Replace..."), //new JMenuItem("Replace Next"), 
            //new JMenuItem("Replace Previous"), 
            new JMenuItem("Count..."),
           // new JMenuItem("Go To  X"), new JMenuItem("Bookmark  X")
        };

        // creating menu items for help menu
        JMenuItem HelpMenuItems[] = {//new JMenuItem("Report Bugs"),
            new JMenuItem("About Texteditor")};

        // ADDING MENU ITEMS TO CORRESPONDING MENUS

        //adding menu items to file menu
        for (int i = 0; i < 10; i++) {
            FileMenu.add(FileMenuItems[i]);
            // registering interface for handling action events
            FileMenuItems[i].addActionListener(this);
            // adding separator
            if (i == 7 || i==8 || i==10) {
                FileMenu.addSeparator();
            }
        }

        //adding menu items to edit menu
         for (int i = 0; i < 12; i++) {
            EditMenu.add(EditMenuItems[i]);

            // registering interface for handling action events
            EditMenuItems[i].addActionListener(this);

            // adding separator
            if (i == 1 || i==6 || i==8 || i==10) {
                EditMenu.addSeparator();
            }
        }

        //adding menu items to search menu
         for (int i = 0; i < 3; i++) {
            SearchMenu.add(SearchMenuItems[i]);

            // registering interface for handling action events
            SearchMenuItems[i].addActionListener(this);

            // adding separator
            //if (i == 2 || i==3) {
            //    SearchMenu.addSeparator();
            //}
        }

        //adding menu items to help menu
         for (int i = 0; i < 1; i++) {
            HelpMenu.add(HelpMenuItems[i]);

            // registering interface for handling action events
            HelpMenuItems[i].addActionListener(this);
        }

       

        frame.setJMenuBar(menuBar);


        // TOOL BAR
        toolBarPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        frame.add(toolBarPanel,BorderLayout.NORTH);

        JToolBar toolBar = new JToolBar();
        
        ImageIcon newIcon= new ImageIcon("src/new2.png");
        Image newIconImg = newIcon.getImage() ;
        Image newimg = newIconImg.getScaledInstance( 20, 20, java.awt.Image.SCALE_SMOOTH ) ;
        newIcon = new ImageIcon( newimg );
        toolBarButton1=new JButton(newIcon);
        toolBarButton1.setToolTipText("New File");
        toolBarButton1.setActionCommand("New");
        toolBarButton1.addActionListener(this);
        toolBar.add(toolBarButton1);
        
        ImageIcon openIcon= new ImageIcon("src/open.png");
        Image openIconImg = openIcon.getImage() ;
        Image newimg2 = openIconImg.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
        openIcon = new ImageIcon( newimg2 );
        toolBarButton2=new JButton(openIcon);
        toolBarButton2.setToolTipText("Open...");
        toolBarButton2.setActionCommand("Open");
        toolBarButton2.addActionListener(this);
        toolBar.add(toolBarButton2);
        
        ImageIcon saveIcon= new ImageIcon("src/save2.png");
        Image saveIconImg = saveIcon.getImage() ;
        Image newimg3 = saveIconImg.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
        saveIcon = new ImageIcon( newimg3 );
        toolBarButton3=new JButton(saveIcon);
        toolBarButton3.setToolTipText("Save As");
        toolBarButton3.setActionCommand("Save As");
        toolBarButton3.addActionListener(this);
        toolBar.add(toolBarButton3);
        
        ImageIcon closeIcn= new ImageIcon("src/close2.png");
        Image closeIconImg = closeIcn.getImage() ;
        Image newimg4 = closeIconImg.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
        closeIcn = new ImageIcon( newimg4 );
        toolBarButton4=new JButton(closeIcn);
        toolBarButton4.setToolTipText("Close");
        toolBarButton4.setActionCommand("Close");
        toolBarButton4.addActionListener(this);
        toolBar.add(toolBarButton4);
        
        ImageIcon printIcon= new ImageIcon("src/print2.png");
        Image printIconImg = printIcon.getImage() ;
        Image newimg5 = printIconImg.getScaledInstance( 22, 22,  java.awt.Image.SCALE_SMOOTH ) ;
        printIcon = new ImageIcon( newimg5 );
        toolBarButton5=new JButton(printIcon);
        toolBarButton5.setToolTipText("Print");
        toolBarButton5.setActionCommand("Print");
        toolBarButton5.addActionListener(this);
        toolBar.add(toolBarButton5);

        toolBar.addSeparator();
        
        ImageIcon cutIcon= new ImageIcon("src/cut10.png");
        Image cutIconImg = cutIcon.getImage() ;
        Image newimg6 = cutIconImg.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
        cutIcon = new ImageIcon( newimg6 );
        toolBarButton6=new JButton(new DefaultEditorKit.CutAction());
        toolBarButton6.setText("");
        toolBarButton6.setIcon(cutIcon);
        toolBarButton6.setToolTipText("Cut");
        toolBar.add(toolBarButton6);
        
        ImageIcon copyIcon= new ImageIcon("src/copy.png");
        Image copyIconImg = copyIcon.getImage() ;
        Image newimg7 = copyIconImg.getScaledInstance( 22, 22,  java.awt.Image.SCALE_SMOOTH ) ;
        copyIcon = new ImageIcon( newimg7 );
        toolBarButton7=new JButton(new DefaultEditorKit.CopyAction());
        toolBarButton7.setText("");
        toolBarButton7.setIcon(copyIcon);
        toolBarButton7.setToolTipText("Copy");
        toolBar.add(toolBarButton7);
        
        ImageIcon pasteIcon= new ImageIcon("src/paste.png");
        Image pasteIconImg = pasteIcon.getImage() ;
        Image newimg8 = pasteIconImg.getScaledInstance( 22, 22,  java.awt.Image.SCALE_SMOOTH ) ;
        pasteIcon = new ImageIcon( newimg8 );
        toolBarButton8=new JButton(new DefaultEditorKit.PasteAction());
        toolBarButton8.setText("");
        toolBarButton8.setIcon(pasteIcon);
        toolBarButton8.setToolTipText("Paste");
        toolBar.add(toolBarButton8);
        
        toolBar.addSeparator();
        
        ImageIcon undoIcon= new ImageIcon("src/undo.png");
        Image undoIconImg = undoIcon.getImage() ;
        Image newimg9 = undoIconImg.getScaledInstance( 19, 19,  java.awt.Image.SCALE_SMOOTH ) ;
        undoIcon = new ImageIcon( newimg9 );
        toolBarButton9=new JButton(undoIcon);
        toolBarButton9.setToolTipText("Undo");
        toolBarButton9.addActionListener(new UndoAction(manager,"Undo"));
        toolBar.add(toolBarButton9);
        
        ImageIcon redoIcon= new ImageIcon("src/redo.png");
        Image redoIconImg = redoIcon.getImage() ;
        Image newimg10 = redoIconImg.getScaledInstance( 19, 19,  java.awt.Image.SCALE_SMOOTH ) ;
        redoIcon = new ImageIcon( newimg10 );
        toolBarButton10=new JButton(redoIcon);
        toolBarButton10.setToolTipText("Redo");
        toolBarButton10.addActionListener(new RedoAction(manager,"Redo"));
        toolBar.add(toolBarButton10);
        
        toolBar.addSeparator();
        
        ImageIcon findIcon= new ImageIcon("src/find4.png");
        Image findIconImg = findIcon.getImage() ;
        Image newimg11 = findIconImg.getScaledInstance( 21, 21,  java.awt.Image.SCALE_SMOOTH ) ;
        findIcon = new ImageIcon( newimg11 );
        toolBarButton11=new JButton(findIcon);
        toolBarButton11.setToolTipText("Find...");
        toolBarButton11.setActionCommand("Find...");
        toolBarButton11.addActionListener(this);
        toolBar.add(toolBarButton11);
        
        ImageIcon replaceIcon= new ImageIcon("src/replace.png");
        Image replaceIconImg = replaceIcon.getImage() ;
        Image newimg12 = replaceIconImg.getScaledInstance( 24, 22,  java.awt.Image.SCALE_SMOOTH ) ;
        replaceIcon = new ImageIcon( newimg12 );
        toolBarButton12=new JButton(replaceIcon);
        toolBarButton12.setToolTipText("Replace...");
        toolBarButton12.setActionCommand("Replace...");
        toolBarButton12.addActionListener(this);
        toolBar.add(toolBarButton12);
        
        ImageIcon countIcon= new ImageIcon("src/count.png                                  ");
        Image countIconImg = countIcon.getImage() ;
        Image newimg13 = countIconImg.getScaledInstance( 24, 22,  java.awt.Image.SCALE_SMOOTH ) ;
        countIcon = new ImageIcon( newimg13 );
        toolBarButton13=new JButton(countIcon);
        toolBarButton13.setToolTipText("Count...");
        toolBarButton13.setActionCommand("Count...");
        toolBarButton13.addActionListener(this);
        toolBar.add(toolBarButton13);
        
        toolBarPanel.add(toolBar);

        
         
    



        // creating tabbedpane
        tabbedPane = new JTabbedPane();

        // creating a tab when text editor is opened
        newTab();

        mainPanel.add(tabbedPane);
           // adding jpanel to the frame
        frame.add(mainPanel);

        // setting visiblity of frame
        frame.setVisible(true);

        /* use getdocument to count the no of enters pressed
        lineCounterPanel = new JPanel();
        lineCountTextArea = new JTextArea();
        lineCountTextArea.setEditable(false);
        int rows=textArea.getRows();
        for(int i=1;i<rows;i++)  {
            String count=Integer.toString(i);
            lineCountTextArea.append(count);
            lineCountTextArea.append("\n");
        }
        lineCounterPanel.add(lineCountTextArea);
        frame.add(lineCounterPanel,BorderLayout.WEST);
        */
        
        
    }

    
    public void newTab()  {
        try{
            // creating text area and adding it to new tab in tabbed pane
            newTabTextArea=new JTextArea();
            // tabbedPane.setBackground(Color.RED);

            tabbedPane.addTab("new tab"+tabbedPane.getTabCount(), newTabTextArea);

            // creating scroll pane in text area and adding scroll pane to the tabbed pane
            JScrollPane scroll = new JScrollPane (newTabTextArea);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            tabbedPane.add(scroll);

            // giving name to new tab
            newTabNameLabel = new JLabel("New Tab");

            ImageIcon closeIcon= new ImageIcon("src/close1.png");
            Image img = closeIcon.getImage() ;
            Image newimg = img.getScaledInstance( 18, 18,  java.awt.Image.SCALE_SMOOTH ) ;
            closeIcon = new ImageIcon( newimg );

            // creating close button
            closeButton[tabbedPane.getTabCount()-1]=new JButton(closeIcon);
            closeButton[tabbedPane.getTabCount()-1].setPreferredSize(new Dimension(14, 15));

            // setting action command(string) for close button
            closeButton[tabbedPane.getTabCount()-1].setActionCommand(""+String.valueOf(tabbedPane.getTabCount()-1));


            // registering interface for handling close button
            closeButton[tabbedPane.getTabCount()-1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int index=tabbedPane.getSelectedIndex();
                     
                       tabbedPane.removeTabAt(index);
                } 
            } );

            
            
                // creating panel for new tab with label and close button
            newTabPanel[tabbedPane.getTabCount()-1] = new JPanel();
            newTabPanel[tabbedPane.getTabCount()-1].add(newTabNameLabel);
            newTabPanel[tabbedPane.getTabCount()-1].add(closeButton[tabbedPane.getTabCount()-1]);
            newTabPanel[tabbedPane.getTabCount()-1].setOpaque(false);
            System.out.println("no of tabs : "+tabbedPane.getTabCount());

            
            tabbedPane.setTabComponentAt(tabbedPane.getTabCount() -1, newTabPanel[tabbedPane.getTabCount()-1]);
            tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
            
            System.out.println("index "+tabbedPane.getSelectedIndex());

            textArea[tabbedPane.getSelectedIndex()]=newTabTextArea;
            nameLabel[tabbedPane.getSelectedIndex()]=newTabNameLabel;
             maxTabCount = tabbedPane.getTabCount();
            
            int index=tabbedPane.getSelectedIndex();
            System.out.println("index"+index);
            textArea[index].getDocument().addUndoableEditListener(manager);
        }
        catch(Exception e)  {
            System.out.println("Can't create more tabs due to following exception : "+e);
        }
    }

    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
            
        }
        TextEditor te = new MenuHandling();
        
        // add shortcut keys
        //email file
            //drag n drop
            //adjustable tabs
        
            // automatic save with timer
        
            // highlight text
            // in edit menu
            // add begin/end select, indent, auto completion, convert case
            //menu - macro

            // background color,text size,(bold,italic,underline)
            // bookmark file, move to line ,bookmark lines


       }
    
}








abstract class UndoRedoAction extends AbstractAction {
  UndoManager undoManager = new UndoManager();

  String errorMessage = "Cannot undo";

  String errorTitle = "Undo Problem";

  protected UndoRedoAction(UndoManager manager, String name) {
    super(name);
    undoManager = manager;
  }

  public void setErrorMessage(String newValue) {
    errorMessage = newValue;
  }

  public void setErrorTitle(String newValue) {
    errorTitle = newValue;
  }

  protected void showMessage(Object source) {
    if (source instanceof Component) {
      JOptionPane.showMessageDialog((Component) source, errorMessage, errorTitle,
          JOptionPane.WARNING_MESSAGE);
    } else {
      System.err.println(errorMessage);
    }
  }
}

class UndoAction extends UndoRedoAction {
  public UndoAction(UndoManager manager, String name) {
    super(manager, name);
    setErrorMessage("Cannot undo");
    setErrorTitle("Undo Problem");
  }

  public void actionPerformed(ActionEvent actionEvent) {
    try {
      undoManager.undo();
    } catch (CannotUndoException cannotUndoException) {
      showMessage(actionEvent.getSource());
    }
  }
}

class RedoAction extends UndoRedoAction {
  public RedoAction(UndoManager manager, String name) {
    super(manager, name);
    setErrorMessage("Cannot redo");
    setErrorTitle("Redo Problem");
  }

  public void actionPerformed(ActionEvent actionEvent) {
    try {
      undoManager.redo();
    } catch (CannotRedoException cannotRedoExcception) {
      showMessage(actionEvent.getSource());
    }
  }
}


