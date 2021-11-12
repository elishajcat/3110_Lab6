// Elisha Catherasoo
// 101148507

// random text added
// change after making a branch

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AddressBook {
    private ArrayList<BuddyInfo> myBuddies;

    public AddressBook(){
        this.myBuddies = new ArrayList<>();
    }
    // add a new Buddy
    public void addBuddy(BuddyInfo aBuddy){
        if (aBuddy != null) {
            myBuddies.add(aBuddy);
        }
    }

    // remove a Buddy
    public BuddyInfo removeBuddy(int index){
        if (index >= 0 && index < myBuddies.size()) {
            return myBuddies.remove(index);
        }
        return null;
    }

    public void displayGUI(){
        // initialize frame, panels and labels
        JFrame frame = new JFrame("Address Book");
        JPanel mainPanel = new JPanel();
        JPanel bodyPanel = new JPanel();
        JLabel buddyName = new JLabel("");
        JLabel buddyAddress = new JLabel("");
        JLabel buddyPhone = new JLabel("");

        // initialize menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem addABuddy = new JMenuItem("Add A Buddy");
        JMenuItem removeABuddy = new JMenuItem("Remove the Selected Buddy");
        JMenuItem newAddressBook = new JMenuItem("Create A New Address Book");

        DefaultListModel buddyNamesList = new DefaultListModel();

        // set frame title and size
        frame.setTitle("Address Book");
        frame.setSize(500,500);

        // set menu
        menu.add(newAddressBook);
        menu.add(addABuddy);
        menu.add(removeABuddy);

        // set type of layouts
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));

        // make a list of buddy names
        for(int i = 0; i < myBuddies.size(); i++) {
            buddyNamesList.addElement(myBuddies.get(i).getName());
        }

        // add buddy names to the list
        JList buddyList = new JList(buddyNamesList);

        // make scroll panes
        JScrollPane scrollPanelList = new JScrollPane(buddyList);
        scrollPanelList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JScrollPane scrollPanelInfo = new JScrollPane(bodyPanel);
        scrollPanelInfo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // set sizes of panels
        bodyPanel.setSize(new Dimension(200,140));
        bodyPanel.setBackground(Color.white);

        scrollPanelList.setPreferredSize(new Dimension(40,0));
        scrollPanelList.setBackground(Color.white);

        mainPanel.setSize(new Dimension(100,100));
        mainPanel.setBackground(Color.white);

        // set what happens when you click on an item in the list
        ListSelectionListener listSelectionListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int j = 0;
                JList list = (JList) e.getSource();
                int selections[] = list.getSelectedIndices();

                // find which buddy was clicked on
                for (int i = 0, n = selections.length; i < n; i++) {
                    j = selections[i];
                }

                // show the information of the buddy clicked
                buddyName.setText("Name: " + myBuddies.get(j).getName() + "\n");
                buddyAddress.setText("Address: " + myBuddies.get(j).getAddress() + "\n");
                buddyPhone.setText("Phone Number: " + myBuddies.get(j).getPhoneNumber() + "\n");
            }
        };

        addABuddy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = (String) JOptionPane.showInputDialog(
                        frame,
                        "Enter Name",
                        "Add a new Buddy",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        ""
                );
                String address = (String) JOptionPane.showInputDialog(
                        frame,
                        "Enter Address",
                        "Add a new Buddy",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        ""
                );
                String phoneNumber = (String) JOptionPane.showInputDialog(
                        frame,
                        "Enter Phone Number",
                        "Add a new Buddy",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        ""
                );

                BuddyInfo newestBuddy = new BuddyInfo(name, address, phoneNumber);
                addBuddy(new BuddyInfo(name, address, phoneNumber));

                buddyNamesList.addElement(newestBuddy.getName());

                JList buddyList = new JList(buddyNamesList);
            }
        });

        removeABuddy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = buddyList.getSelectedIndex();
                removeBuddy(i);
                buddyNamesList.remove(i);

                JList buddyList = new JList(buddyNamesList);
            }
        });

        newAddressBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddressBook newAddressBook = new AddressBook();
                DefaultListModel newBuddyNamesList = new DefaultListModel();

                newAddressBook.displayGUI();
            }
        });

        // add listen the list
        buddyList.addListSelectionListener(listSelectionListener);

        // add panels to layout
        bodyPanel.add(buddyName, BorderLayout.PAGE_START);
        bodyPanel.add(buddyAddress, BorderLayout.CENTER);
        bodyPanel.add(buddyPhone, BorderLayout.PAGE_END);

        mainPanel.add(scrollPanelList, BorderLayout.WEST);
        mainPanel.add(scrollPanelInfo, BorderLayout.EAST);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        frame.add(mainPanel);

        // frame doesn't close immediately when trying to quit
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args){
        AddressBook addressBook = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("Tom", "Carleton", "613");
        BuddyInfo newBuddy = new BuddyInfo("Homer", "1234 Carleton Street", "613-123-4567");
        addressBook.addBuddy(buddy);
        addressBook.addBuddy(newBuddy);

        addressBook.displayGUI();
    }
}
