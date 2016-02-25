import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.text.*;

class MenuHandling extends TextEditor implements ActionListener{

   String[] fileLocation = new String[50];
   String fileName, fileDirectory, fileContent,selectedText;
   //File file;
   JTextField findWhatTextField, replaceWhatTextField, replaceWithTextField,countWhatTextField;
   JCheckBox caseCheckBox, wrapCheckBox, wholeCheckBox;
   JButton findNext, findPrevious, findAllInThis, findAllInAll, replaceNext,replacePrevious, 
           replaceAllInThis, replaceAllInAll,countLineInThis,countLineInAll,countAllInThis,countAllInAll;
   JFrame countFrame;
   JPanel countPanel;
   JLabel countDisplay=new JLabel();
   int tempOffset,currentPos,counter=0,fontSize=12;
   

// implementing the method of action listener interface
    public void actionPerformed(ActionEvent ae){

        // storing the string associated with the action performed
        String actionMenu=ae.getActionCommand();
        System.out.println(actionMenu);

        /****************************** FILE MENU **********************************/
        
        if(actionMenu.equals("New"))  {
            newTab();
        }

        if(actionMenu.equals("Open"))  {
            try  {
                    FileDialog fd=new FileDialog(frame,"Select File",FileDialog.LOAD);
                    fd.setVisible(true);
                    System.out.print(FileDialog.LOAD);
                    
                                      
                    int index=tabbedPane.getTabCount();
                    System.out.println("tab count index :"+index);
                    
                    fileContent="";
                    fileName=fd.getFile();
                    System.out.println(fileName);
                    
                    fileDirectory=fd.getDirectory();
                    System.out.println(fileDirectory);
                    
                    fileLocation[index]=fileDirectory + fileName;
                    System.out.println("file location : "+fileLocation[index]);
                    
                    File file=new File(fileLocation[index]);
                    //File file=new File(fileLocation2);
                    
                    FileInputStream inputFile=new FileInputStream(file);
                    int len=(int)file.length();
                    for(int j=0;j<len;j++ )
                    {
                        char fileContentChar=(char)inputFile.read();
                        fileContent+=fileContentChar;
                    }
                    System.out.println(fileContent);
                    inputFile.close();
                    
                     // creating a new tab
                    newTab();
                    
                    // opening file (setting textarea of new tab with selected file)
                    textArea[tabbedPane.getSelectedIndex()].setText(fileContent);
                    
                    // giving name to opened file (new tab)
                    nameLabel[tabbedPane.getSelectedIndex()].setText(fileName);
                
            }//try block ends here
        
            catch(IOException e)  {
                System.out.println("cannot open file due to following exception : "+e);
            }
        }  //if block ends here


        if(actionMenu.equals("Open Containing Folder"))  {
            int index=tabbedPane.getSelectedIndex();
            try {
                File file=new File(fileLocation[index]);
                Desktop.getDesktop().open(file.getParentFile());
            }
            catch(IOException e)  {
                System.out.println("Cannot open file due to following exception: "+e);
            }
            
        }
        
        if(actionMenu.equals("Save"))  {
            try  {
                    int index=tabbedPane.getSelectedIndex();
                    fileContent=textArea[tabbedPane.getSelectedIndex()].getText();

                    byte buf[]=fileContent.getBytes();
                    File file=new File(fileLocation[index]);
                    FileOutputStream outputFile=new FileOutputStream(file);
                    int len=(int)fileContent.length();
                    for(int j=0;j<len;j++ )
                    {
                        outputFile.write(buf[j]);

                    }
                    System.out.println("\nfile saved\n");

                    textArea[tabbedPane.getSelectedIndex()]=newTabTextArea;

            }
            catch(Exception e)  {
               System.out.println("cannot save file due to following exception : "+e);
            }
        }
        
        if(actionMenu.equals("Save As"))  {
            try  {
                    boolean ext=true;
                    int index=tabbedPane.getSelectedIndex();
                    FileDialog fd=new FileDialog(frame,"Save As",FileDialog.SAVE);
                    fd.setVisible(true);
                    System.out.println("save "+FileDialog.SAVE);
                    fileName=fd.getFile();
                    fileDirectory=fd.getDirectory();
                    if(fileName.contains("."))  {
                        if(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())==null) {
                            fileLocation[index]=fileDirectory + fileName + "txt";
                        }
                        else  {
                            fileLocation[index]=fileDirectory + fileName ;
                        }
                    }
                    else  {
                        fileLocation[index]=fileDirectory + fileName + ".txt";
                    }    
                    
                    System.out.println("file location: "+fileLocation[index]);
                    fileContent=textArea[tabbedPane.getSelectedIndex()].getText();
                    byte buf[]=fileContent.getBytes();

                    File file=new File(fileLocation[index]);
                    FileOutputStream outputFile=new FileOutputStream(file);
                    int len=(int)fileContent.length();
                    for(int j=0;j<len;j++ )
                    {
                        outputFile.write(buf[j]);

                    }

                    System.out.println("\nfile saved\n");
                    outputFile.close();
                    // giving name to saved file
                    nameLabel[tabbedPane.getSelectedIndex()].setText(fileName);

            }
            catch(Exception e)  {
               System.out.println("cannot save file due to following exception : "+e);
            }
        }//if block ends here
        
        if(actionMenu.equals("Rename"))  {
            try  {
                    int index=tabbedPane.getSelectedIndex();
                    FileDialog fd=new FileDialog(frame,"Rename",FileDialog.SAVE);
                    fd.setVisible(true);
                    File oldFile=new File(fileLocation[index]);
                    System.out.println("old file name : "+oldFile);
                    
                    fileName=fd.getFile();
                    System.out.println("new file name : "+fileName);
                    
                    fileDirectory=fd.getDirectory();
                    System.out.println("file directory : "+fileDirectory);
                    
                    fileLocation[index]=fileDirectory + fileName + ".txt";
                    System.out.println("file location : "+fileLocation[index]);
                    
                    File fileRename=new File(fileLocation[index]);
                    
                    oldFile.renameTo(fileRename);


                    if(fileName==null)  {
                        System.out.println("rename cancelled by user");
                    }
                    else{
                        System.out.println("\nfile renamed\n");
                        //outputFile.close();

                        // giving name to file to be renamed
                        nameLabel[tabbedPane.getSelectedIndex()].setText(fileName);
                     }
            }
            catch(Exception e)  {
               System.out.print("cannot rename file due to following exception : "+e);
            }
        }//if block ends here

        if (actionMenu.equals("Close")){
                   System.out.println("working");
                       //int i = tabbedPane.indexOfTabComponent(newTabPanel);
                   int i=tabbedPane.getSelectedIndex();
                   System.out.println("index "+i);
                        if (i != -1) {
                        tabbedPane.removeTabAt(i);
                        }
        }
        if(actionMenu.equals("Close All"))  {
            tabbedPane.removeAll();
        }
        
        if(actionMenu.equals("Print"))  {
            int index=tabbedPane.getSelectedIndex();
            File file= new File(fileLocation[index]);
            try  {
                Desktop.getDesktop().print(file);
            }
            catch(IOException ioe) {
                System.out.println(ioe);
            }
        }
        
         if(actionMenu.equals("Exit"))  {
            frame.dispose();
        }
    
        /****************************** EDIT MENU **********************************/
         
        if(actionMenu.equals("Delete"))  {
              textArea[tabbedPane.getSelectedIndex()].replaceSelection("");
        }
        
        if(actionMenu.equals("Select All"))  {

              textArea[tabbedPane.getSelectedIndex()].selectAll();
        }
        
        if(actionMenu.equals("Zoom In"))  {
            fontSize+=2;
            Font font=new Font("Verdana",Font.PLAIN,fontSize);
            for(int index=0;index<tabbedPane.getTabCount();index++)  {
                textArea[index].setFont(font);
            }
        }
        
        if(actionMenu.equals("Zoom Out"))  {
            fontSize-=2;
            Font font=new Font("Verdana",Font.PLAIN,fontSize);
            for(int index=0;index<tabbedPane.getTabCount();index++)  {
                textArea[index].setFont(font);
            }
        }
        
        if(actionMenu.equals("Night Mode On"))  {
            for(int index=0;index<tabbedPane.getTabCount();index++)  {
                textArea[index].setForeground(Color.white);
                textArea[index].setBackground(Color.darkGray);
            }
        }
        
        if(actionMenu.equals("Night Mode Off"))  {
            for(int index=0;index<tabbedPane.getTabCount();index++)  {
                textArea[index].setForeground(Color.black);
                textArea[index].setBackground(Color.white);
            }
        }

        if(actionMenu.equals("Time Stamp"))  {
             String months[]={"January","February","March","April","May","June","July","August","September","October","November","December"};

             GregorianCalendar gcalendar=new GregorianCalendar();
             String h=String.valueOf(gcalendar.get(Calendar.HOUR));
             String m=String.valueOf(gcalendar.get(Calendar.MINUTE));
             String s=String.valueOf(gcalendar.get(Calendar.SECOND));
             String date=String.valueOf(gcalendar.get(Calendar.DATE));
             String mon=months[gcalendar.get(Calendar.MONTH)];
             String year=String.valueOf(gcalendar.get(Calendar.YEAR));
             String hms="Time"+" - "+h+":"+m+":"+s+"\t\t\t\t Date"+" - "+date+" "+mon+" "+year+" \n\n";
             
             textArea[tabbedPane.getSelectedIndex()].selectAll();
             String allContent=textArea[tabbedPane.getSelectedIndex()].getSelectedText();
             textArea[tabbedPane.getSelectedIndex()].setText(hms + allContent);
         }
        
        /****************************** SEARCH MENU **********************************/

        if(actionMenu.equals("Find...")) {

            JFrame findFrame= new JFrame("Find");
            JPanel p=new JPanel();
            JLabel label = new JLabel("Find What:");
            findWhatTextField = new JTextField();
                    caseCheckBox = new JCheckBox("Match Case");
                    wrapCheckBox = new JCheckBox("Start From Beginning");
                    wholeCheckBox = new JCheckBox("Whole Words");
                    
                    findNext = new JButton("Find Next");
                    findPrevious = new JButton("Find Previous");
                    findAllInThis = new JButton("Find All In This Tab");
                    findAllInAll = new JButton("Find All In All Tabs");
                  
                    
                    findNext.addActionListener(this);
                    findPrevious.addActionListener(this);
                    findAllInThis.addActionListener(this);
                    findAllInAll.addActionListener(this);

                    // remove redundant default border of check boxes - they would hinder
                    // correct spacing and aligning (maybe not needed on some look and feels)
                    caseCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                    wrapCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                    wholeCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                    

                    GroupLayout layout = new GroupLayout(p);
                    p.setLayout(layout);
                    layout.setAutoCreateGaps(true);
                    layout.setAutoCreateContainerGaps(true);

                    layout.setHorizontalGroup(layout.createSequentialGroup()
                        .addComponent(label)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(findWhatTextField)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(caseCheckBox)
                                    .addComponent(wholeCheckBox)
                                    )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(wrapCheckBox)
                                    )
                                )                           )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(findNext)
                            .addComponent(findPrevious)
                            .addComponent(findAllInThis)
                            .addComponent(findAllInAll))
                    );

                    layout.linkSize(SwingConstants.HORIZONTAL, findNext, findPrevious, findAllInThis, findAllInAll );

                    layout.setVerticalGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label)
                            .addComponent(findWhatTextField)
                            .addComponent(findNext)
                         )
                        
                            
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(caseCheckBox)
                                    .addComponent(wrapCheckBox))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(wholeCheckBox)
                                    )
                                 )
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(findPrevious)
                                .addComponent(findAllInThis)
                                .addComponent(findAllInAll))
                          )
                            
                    );  

                    findFrame.add(p);
                    p.setVisible(true);
                    findFrame.setVisible(true);
                    findFrame.pack();

        }
        
        if(actionMenu.equals("Replace...")) {

            JFrame findFrame= new JFrame("Replace");
            JPanel p=new JPanel();
                    JLabel repWhatLabel = new JLabel("Replace What:");
                    JLabel repWithLabel = new JLabel("Replace With:");
                    replaceWhatTextField = new JTextField();
                    replaceWithTextField = new JTextField();
                    caseCheckBox = new JCheckBox("Match Case");
                    wrapCheckBox = new JCheckBox("Start From Beginning");
                    wholeCheckBox = new JCheckBox("Whole Words");
                    
                    replaceNext = new JButton("Replace Next");
                    replacePrevious = new JButton("Replace Previous");
                    replaceAllInThis = new JButton("Replace All In This Tab");
                    replaceAllInAll = new JButton("Replace All In All Tabs");


                    replaceNext.addActionListener(this);
                    replacePrevious.addActionListener(this);
                    replaceAllInThis.addActionListener(this);
                    replaceAllInAll.addActionListener(this);

                    // remove redundant default border of check boxes - they would hinder
                    // correct spacing and aligning (maybe not needed on some look and feels)
                    caseCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                    wrapCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                    wholeCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                   

                    GroupLayout layout = new GroupLayout(p);
                    p.setLayout(layout);
                    layout.setAutoCreateGaps(true);
                    layout.setAutoCreateContainerGaps(true);

                    layout.setHorizontalGroup(layout.createSequentialGroup()
                      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(repWhatLabel)
                        .addComponent(repWithLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(replaceWhatTextField)
                            .addComponent(replaceWithTextField)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(caseCheckBox)
                                    .addComponent(wholeCheckBox))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(wrapCheckBox)
                                    )))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(replaceNext)
                            .addComponent(replacePrevious)
                            .addComponent(replaceAllInThis)
                            .addComponent(replaceAllInAll))
                    );

                    layout.linkSize(SwingConstants.HORIZONTAL, replaceNext, replacePrevious, replaceAllInThis, replaceAllInAll );

                    layout.setVerticalGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(repWhatLabel)
                            .addComponent(replaceWhatTextField)
                            .addComponent(replaceNext)
                         )
                         .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(repWithLabel)
                            .addComponent(replaceWithTextField)
                            .addComponent(replacePrevious)
                         )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(caseCheckBox)
                                    .addComponent(wrapCheckBox))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(wholeCheckBox)
                                    )
                                 )
                                
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(replaceAllInThis)
                                 .addComponent(replaceAllInAll))
                          )
                     );
                    
                    findFrame.add(p);
                    p.setVisible(true);
                    findFrame.setVisible(true);
                    findFrame.pack();
                    
        }
        
        if(actionMenu.equals("Count...")) {

            countFrame= new JFrame("Count");
            JPanel p=new JPanel();
            countPanel=new JPanel(new FlowLayout());
            JLabel label = new JLabel("Count What:");
            countWhatTextField = new JTextField();
            caseCheckBox = new JCheckBox("Match Case");
            wholeCheckBox = new JCheckBox("Whole Words");
                   
            countAllInThis = new JButton("Count In This Tab");
            countAllInAll = new JButton("Count In All Tabs");
            countLineInThis = new JButton("Count Lines In This Tab");
            countLineInAll = new JButton("Count Lines In All Tabs");
                    
                    countAllInThis.addActionListener(this);
                    countAllInAll.addActionListener(this);
                    countLineInThis.addActionListener(this);
                    countLineInAll.addActionListener(this);

                    // remove redundant default border of check boxes - they would hinder
                    // correct spacing and aligning (maybe not needed on some look and feels)
                    caseCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                    wholeCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                    

                    GroupLayout layout = new GroupLayout(p);
                    p.setLayout(layout);
                    layout.setAutoCreateGaps(true);
                    layout.setAutoCreateContainerGaps(true);

                    layout.setHorizontalGroup(layout.createSequentialGroup()
                        .addComponent(label)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(countWhatTextField)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(caseCheckBox)
                                    .addComponent(wholeCheckBox)
                                    )
                                )                           )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(countAllInThis)
                            .addComponent(countAllInAll)
                            .addComponent(countLineInThis)
                            .addComponent(countLineInAll))
                    );

                    layout.linkSize(SwingConstants.HORIZONTAL, countLineInThis, countLineInAll ,countAllInThis, countAllInAll );

                    layout.setVerticalGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label)
                            .addComponent(countWhatTextField)
                            .addComponent(countAllInThis)
                         )
                       
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(caseCheckBox))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(wholeCheckBox)
                                    )
                                 )
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(countAllInAll)
                                .addComponent(countLineInThis)
                                .addComponent(countLineInAll))
                          )
                            
                    );  
                    
                   
                    countFrame.setSize(430,190);
                    countFrame.add(p);
                    p.setVisible(true);
                    countFrame.add(countPanel,BorderLayout.SOUTH);
                    countPanel.setVisible(true);
                    countFrame.setVisible(true);
                    countFrame.pack();
                   
        }

        /****************************** HELP MENU **********************************/
        
        if(actionMenu.equals("About Texteditor"))  {
             //AboutDialog d1=new AboutDialog(this,"About Texteditor");
             //JOptionPane.showConfirmDialog(null, "Notepad made by Gurpreet", "Confirmation Dialog", 
              //     JOptionPane.INFORMATION_MESSAGE);
           
             //////////////////////////// panel
            final JDialog aboutDialog = new JDialog(frame, "About GPad");
            JPanel aboutPanel = new JPanel();
            Font boldFont=new Font("Verdana",Font.BOLD,fontSize);
            Font plainFont=new Font("Verdana",Font.PLAIN,fontSize);
            
            JLabel authorLabel=new JLabel(" Created By :    Gurpreet Singh");
            authorLabel.setFont(boldFont);
             
            JTextArea about=new JTextArea();
            about.setFont(plainFont);
            about.setText("This program is free software; you can redistribute\n"
                    + "it and/or modify it under the terms of the GNU\n"
                     + "General Public License as published by the Free\n"
                     + "Software Foundation; either version 2 of the License,\n"
                     + "or (at your option) any later version.\n"
                    + "This program is distributed in the hope that it will \n"
                     + "be useful, but WITHOUT ANY WARRANTY; without even the \n"
                     + "implied warranty of MERCHANTABILITY or FITNESS FOR A \n"
                     + "PARTICULAR PURPOSE. ");
             about.setEditable(false);
             
            JButton okButton = new JButton("  OK  ");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    aboutDialog.dispose();// declared as final because it is accessed from within inner class
                }
            });
            
            ImageIcon icon = new ImageIcon("src/gpad.png");
            Image gpadIconImg = icon.getImage() ;
            Image gpadIcon = gpadIconImg.getScaledInstance( 160, 70,  java.awt.Image.SCALE_SMOOTH ) ;
            icon = new ImageIcon(gpadIcon);
            JLabel iconLabel = new JLabel();
            iconLabel.setIcon(icon);
            
             GroupLayout layout = new GroupLayout(aboutPanel);
             aboutPanel.setLayout(layout);
             layout.setAutoCreateGaps(true);
             layout.setAutoCreateContainerGaps(true);

                    layout.setHorizontalGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(iconLabel)
                            .addComponent(authorLabel)
                            .addComponent(about))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(okButton))
                    );                    

                    layout.setVerticalGroup(layout.createSequentialGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(iconLabel)
                            .addComponent(authorLabel)
                            .addComponent(about)
                            .addComponent(okButton))
                    );
                     
                    aboutPanel.setVisible(true);
                    aboutPanel.setBackground(Color.white);
                    aboutDialog.add(aboutPanel);
                    aboutDialog.pack();
                    aboutDialog.setVisible(true);
        }
        
        /****************************** REMAINING SEARCH ACTION EVENTS **********************************/
        
        if(actionMenu.equals("Find Next"))  {
          
            String searchWord = findWhatTextField.getText();
            int index=tabbedPane.getSelectedIndex();
            currentPos=textArea[index].getCaretPosition();
            System.out.println("current pos : "+currentPos);
            System.out.println("Search : "+searchWord);
            
            if(currentPos==textArea[index].getText().toLowerCase().lastIndexOf(""))  {
                textArea[index].setCaretPosition(0);
            }
            int offset=findOffset(index, searchWord,"next");
            /*if(offset==-1)  {
                JOptionPane.showMessageDialog((Component) ae.getSource(), "Can't find the text \""+searchWord+"\"", "Not Found",
                JOptionPane.WARNING_MESSAGE);
            }*/
            System.out.println("Offset : "+offset);
                    
            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );
            
            //System.out.println("tempOffset : "+tempOffset);
       
                try
                {
                    Highlighter hl=textArea[index].getHighlighter();
                    hl.removeAllHighlights();
                    hl.addHighlight(offset, offset + searchWord.length(), painter);
                    
                    tempOffset=findTempOffset(index,searchWord,tempOffset,"next");
                    
                }
                catch(BadLocationException ble)
                    { System.out.println(ble); }
            
         }

          if(actionMenu.equals("Find Previous"))  {
          
            String searchWord = findWhatTextField.getText();
            int index=tabbedPane.getSelectedIndex();
            currentPos=textArea[index].getCaretPosition();
            System.out.println("Search : "+searchWord);
            
            int offset=findOffset(index, searchWord,"previous");
            /*if(offset==-1)  {
                JOptionPane.showMessageDialog((Component) ae.getSource(), "Can't find the text \""+searchWord+"\"", "Not Found",
                JOptionPane.WARNING_MESSAGE);
            }   */     
            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );

            if(tempOffset>offset) 
            {   if(tempOffset<currentPos)  {
                    tempOffset=textArea[index].getText().toLowerCase().lastIndexOf(" ") + searchWord.length() + 1; 
                }
                System.out.println("tempoffset bigger than offset");
               // tempOffset=offset;
            }
            if(tempOffset<offset)  {   
                if(tempOffset==-1)  {
                    tempOffset=textArea[index].getText().toLowerCase().lastIndexOf(" ") + searchWord.length() + 1; 
                }
                System.out.println("tempoffset smaller than offset");
               // tempOffset=offset;
            }
            if(tempOffset==0)  {
                //tempOffset=offset;
                System.out.println("tempoffset is 0");
            }
            int length = searchWord.length();

            System.out.println("Offset : "+tempOffset);
            System.out.println("Length : "+length);

            
                try
                {   
                    Highlighter hl=textArea[index].getHighlighter();
                    hl.removeAllHighlights();
                    hl.addHighlight(offset, offset +length, painter);
                    
                    tempOffset=findTempOffset(index,searchWord,tempOffset,"previous");
                    
                }
                catch(BadLocationException ble)
                    { System.out.println(ble); }
            

         }
        
        if(actionMenu.equals("Find All In This Tab")||actionMenu.equals("Count In This Tab"))  {
            String searchWord;
            counter=0;
            
            if(actionMenu.equals("Count In This Tab"))  {
                searchWord = countWhatTextField.getText();
            }
            else  {
                searchWord = findWhatTextField.getText();
            }

            System.out.println("Search : "+searchWord);
            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );

            int index=tabbedPane.getSelectedIndex();
            int offset=findTempOffset(index, searchWord,-1,"next");
            if(offset==-1)  {
                JOptionPane.showMessageDialog((Component) ae.getSource(), "Can't find the text \""+searchWord+"\"", "Not Found",
                JOptionPane.WARNING_MESSAGE);
            }
            int length = searchWord.length();

            Highlighter hl=textArea[index].getHighlighter();
            hl.removeAllHighlights();

            System.out.println("Offset : "+offset);
            System.out.println("Length : "+length);

            while ( offset != -1)
            {
                counter++;
                try
                {
                    hl.addHighlight(offset, offset + length, painter);
                    offset=findTempOffset(index,searchWord,offset,"next");
                }
                catch(BadLocationException ble)
                    { System.out.println(ble); }
            }
            if(actionMenu.equals("Count In This Tab"))  {
                
                countDisplay.setText("Count : "+counter+" matches");
                countPanel.add(countDisplay);
                
            }
         }

        if(actionMenu.equals("Find All In All Tabs")||actionMenu.equals("Count In All Tabs"))  {
            
            String searchWord;
            counter=0;
            
            if(actionMenu.equals("Count In All Tabs"))  {
                searchWord = countWhatTextField.getText();
            }
            else  {
                searchWord = findWhatTextField.getText();
            }

            System.out.println("Search : "+searchWord);
            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );

            int length = searchWord.length();
            
            for(int index=0;index<tabbedPane.getTabCount();index++)   {
                Highlighter hl=textArea[index].getHighlighter();
                hl.removeAllHighlights();
                int offset=findTempOffset(index, searchWord,-1,"next");
                
                while ( offset != -1)
                {
                    counter++;
                    try
                    {
                        hl.addHighlight(offset, offset + length, painter);
                        offset=findTempOffset(index,searchWord,offset,"next");
                    }
                    catch(BadLocationException ble)
                        { System.out.println(ble); }
                }
            }
            if(actionMenu.equals("Count In All Tabs"))  {
                countDisplay.setText("Count : "+counter+" matches");
                countPanel.add(countDisplay);
                
            }
         }

         if(actionMenu.equals("Count Lines In This Tab"))  {
                int lines=textArea[tabbedPane.getSelectedIndex()].getLineCount();
                countDisplay.setText("Count : "+lines+" lines");
                countPanel.add(countDisplay);
         }
         if(actionMenu.equals("Count Lines In All Tabs"))  {
                int lines=0;
                for(int index=0;index<tabbedPane.getTabCount();index++)  {
                    lines+=textArea[index].getLineCount();
                }
                countDisplay.setText("Count : "+lines+" lines");
                countPanel.add(countDisplay);
          }
        
         if(actionMenu.equals("Replace Next"))  {

            String searchWord = replaceWhatTextField.getText();
            String replaceWord = replaceWithTextField.getText();

            int index=tabbedPane.getSelectedIndex();
            int offset=findOffset(index, searchWord,"next");
            
            if (offset >= 0 && searchWord.length() > 0)
                textArea[index].replaceRange(replaceWord , offset, offset + searchWord.length());

            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );

                try
                {
                    Highlighter hl=textArea[index].getHighlighter();
                    hl.removeAllHighlights();
                    hl.addHighlight(offset, offset + replaceWord.length(), painter);
                    
                }
                catch(BadLocationException ble)
                    { System.out.println(ble); }
         }
         
         if(actionMenu.equals("Replace Previous"))  {

            String searchWord = replaceWhatTextField.getText();
            String replaceWord = replaceWithTextField.getText();

            int index=tabbedPane.getSelectedIndex();
            int offset=findOffset(index, searchWord,"previous");
            
            if (offset >= 0 && searchWord.length() > 0)
                textArea[index].replaceRange(replaceWord , offset, offset + searchWord.length());

            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );

                try
                {
                    Highlighter hl=textArea[index].getHighlighter();
                    hl.removeAllHighlights();
                    hl.addHighlight(offset, offset + replaceWord.length(), painter);
                    
                }
                catch(BadLocationException ble)
                    { System.out.println(ble); }


         }

        if(actionMenu.equals("Replace All In This Tab"))  {

            String searchWord = replaceWhatTextField.getText();
            String replaceWord = replaceWithTextField.getText();

            int index=tabbedPane.getSelectedIndex();
            int offset=findTempOffset(index, searchWord,-1,"next");

            Highlighter hl=textArea[index].getHighlighter();
            hl.removeAllHighlights();
            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );
            while(offset!=-1)  {
                try
                {  
                    textArea[index].replaceRange(replaceWord , offset, offset + searchWord.length());
                    
                    hl.addHighlight(offset, offset + replaceWord.length(), painter);
                    offset=findTempOffset(index,searchWord,offset,"next");  
                }
                catch(BadLocationException ble)
                    { System.out.println(ble); }
            }
         }

        if(actionMenu.equals("Replace All In All Tabs"))  {

            String searchWord = replaceWhatTextField.getText();
            String replaceWord = replaceWithTextField.getText();

            for(int index=0;index<tabbedPane.getTabCount();index++)   {
                Highlighter hl=textArea[index].getHighlighter();
                hl.removeAllHighlights();
                Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );
                int offset=findTempOffset(index, searchWord,-1,"next");
                while(offset!=-1)  {
                    try
                    {
                        textArea[index].replaceRange(replaceWord , offset, offset + searchWord.length());

                        hl.addHighlight(offset, offset + replaceWord.length(), painter);
                        offset=findTempOffset(index,searchWord,offset,"next");  
                        
                    }
                    catch(BadLocationException ble)
                        { System.out.println(ble); }
                }

         }
       }
        
   }    //  end of action performed
    
/****************************** REMAINING SEARCH METHODS **********************************/


// to search the next or previous matching word
    public int findTempOffset(int index,String searchWord,int nextOffset,String direction)  {
        int prevOffset;
        
        if(direction.equals("next"))  {
            if(caseCheckBox.isSelected())  {
                nextOffset = textArea[index].getText().indexOf(searchWord, nextOffset+1);  
            }
            else  {
                nextOffset = textArea[index].getText().toLowerCase().indexOf(searchWord.toLowerCase(), nextOffset+1);
            }
            return nextOffset;
        }
        else  {             // previous
            if(caseCheckBox.isSelected())  {
                if(wrapCheckBox.isSelected())  {
                    prevOffset = textArea[index].getText().lastIndexOf(searchWord);
                }
                else  {
                    currentPos=textArea[index].getCaretPosition();
                    System.out.println("current pos : "+currentPos);
                    prevOffset = textArea[index].getText().lastIndexOf(searchWord, nextOffset-1);
                }
            }
            else  {         // case checkbox not selected
                //if(wrapCheckBox.isSelected())  {
                //    prevOffset = textArea[index].getText().toLowerCase().lastIndexOf(searchWord.toLowerCase()); 
                //}
                //else  {             //no checkbox selected
                    currentPos=textArea[index].getCaretPosition();
                    System.out.println("current pos : "+currentPos);
                    prevOffset=textArea[index].getText().toLowerCase().lastIndexOf(searchWord.toLowerCase(),tempOffset-1);
                    
                //}
            }
            return prevOffset;
        }
    }   
    
//to search the first matching word from current position
    public int findOffset(int index,String searchWord,String direction)  {
        int nextOffset,prevOffset;
        
        if(direction.equals("next"))  {
            if(caseCheckBox.isSelected())  {
                
                    currentPos=textArea[index].getCaretPosition();
                    System.out.println("current pos : "+currentPos);
                    nextOffset = textArea[index].getText().indexOf(searchWord,currentPos); 
                    textArea[index].setCaretPosition(nextOffset+1);
            }
            
            else  {             // case checkbox not selected
                    currentPos=textArea[index].getCaretPosition();
                    nextOffset=textArea[index].getText().toLowerCase().indexOf(searchWord.toLowerCase(),currentPos); 
                    System.out.println("current pos : "+currentPos);
                    textArea[index].setCaretPosition(nextOffset+1);
                
            }
            return nextOffset;
        }
        else  {                         // to return previous word
             if(caseCheckBox.isSelected())  {
                
                    currentPos=textArea[index].getCaretPosition();
                    System.out.println("current pos : "+currentPos);
                    prevOffset = textArea[index].getText().lastIndexOf(searchWord,currentPos);
                    try {
                        textArea[index].setCaretPosition(prevOffset-1);
                    }
                    catch(IllegalArgumentException iae)  {
                        textArea[index].setCaretPosition(textArea[index].getText().lastIndexOf(""));
                    }
                
            }
            else  {                 //case checkbox not selected
                //if(wrapCheckBox.isSelected())  {
                    //prevOffset = textArea[index].getText().toLowerCase().lastIndexOf(searchWord.toLowerCase()); 
                //}
                //else  {             //no checkbox selected
                    currentPos=textArea[index].getCaretPosition();
                    System.out.println("current pos : "+currentPos);
                    prevOffset=textArea[index].getText().toLowerCase().lastIndexOf(searchWord.toLowerCase(),currentPos);
                    try {
                        textArea[index].setCaretPosition(prevOffset-1);
                    }
                    catch(IllegalArgumentException iae)  {
                        textArea[index].setCaretPosition(textArea[index].getText().lastIndexOf(""));
                    }
                //}

            }
            return prevOffset;
        }
    }
    
    public static void main(String[] args) {
        try  {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
           catch(Exception e)
           {

           }
            TextEditor te = new MenuHandling();
            
       }
}
//removing tempoffset method will result in jump of next search
// password lock - not to edit/change files