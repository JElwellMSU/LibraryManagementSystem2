
// Travis Tan
// 10-23-23
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class MainWindow {
  JFrame frame;
  JPanel leftPanel;
  JPanel rightPanel;

  private JButton addBook;
  private JButton addUser;
  private JButton checkIn;
  private JButton checkOut;

  private DefaultTableModel model;

  private JTextField mainSearch;

  MainWindow() {
    // Main Window
    frame = new JFrame("Library Management System");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(892, 653);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setResizable(false);

    // Main Page
    JPanel main = new JPanel(new BorderLayout());
    frame.add(main);

    // Left Panel
    leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    leftPanel.setPreferredSize(new Dimension(140, 0));
    leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
    main.add(leftPanel, BorderLayout.WEST);

    // Right Panel
    rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    rightPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
    main.add(rightPanel, BorderLayout.CENTER);

    // Buttons for the Left Panel
    // Add Book Button
    addBook = new JButton("Add Book");
    addBook.setPreferredSize(new Dimension(100, 50));
    addBook.setAlignmentX(Component.CENTER_ALIGNMENT);
    addBook.setMaximumSize(new Dimension(100, addBook.getMinimumSize().height));

    // Add User Button
    addUser = new JButton("Add User");
    addUser.setPreferredSize(new Dimension(100, 50));
    addUser.setAlignmentX(Component.CENTER_ALIGNMENT);
    addUser.setMaximumSize(new Dimension(100, addUser.getMinimumSize().height));

    // Check-Out Button
    checkOut = new JButton("Check-Out");
    checkOut.setPreferredSize(new Dimension(100, 50));
    checkOut.setAlignmentX(Component.CENTER_ALIGNMENT);
    checkOut.setMaximumSize(new Dimension(100, checkOut.getMinimumSize().height));

    // Action Listener for Check-Out Button\
    // Pops up the Check Out Form
    checkOut.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            new CheckOutForm(frame);
          }
        });
      }
    });

    // Check-In Button
    checkIn = new JButton("Check-In");
    checkIn.setPreferredSize(new Dimension(100, 50));
    checkIn.setAlignmentX(Component.CENTER_ALIGNMENT);
    checkIn.setMaximumSize(new Dimension(100, checkIn.getMinimumSize().height));

    // Adding Buttons to the Left Panel
    // Box.createRigidArea is just for padding (spacing) between the buttons
    leftPanel.add(Box.createRigidArea(new Dimension(0, 150)));
    leftPanel.add(addBook);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    leftPanel.add(addUser);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    leftPanel.add(checkOut);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    leftPanel.add(checkIn);

    // Panel for containing the search bar and Table
    JPanel TABLE = new JPanel();
    TABLE.setLayout(new BoxLayout(TABLE, BoxLayout.Y_AXIS));
    rightPanel.add(TABLE);

    // Search Bar
    mainSearch = new JTextField("Search");
    mainSearch.setPreferredSize(new Dimension(250, 30));
    // "Search" text prompt. Goes away after user clicks search box
    mainSearch.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        JTextField source = (JTextField) e.getComponent();
        source.setText("");
        source.removeFocusListener(this);
      }
    });

    // Adding Search Bar to TABLE pane
    TABLE.add(Box.createRigidArea(new Dimension(0, 30)));
    TABLE.add(mainSearch);
    TABLE.add(Box.createRigidArea(new Dimension(0, 10)));

    // Book Table Column Names
    String[] columnNames = { "Book ID", "Title", "Author", "Genre", "ISBN" };

    // Creating Book Table
    JTable bookTable = new JTable(new DefaultTableModel(null, columnNames));
    model = (DefaultTableModel) bookTable.getModel();
    // Adding Book Table to the Scroll Pane
    JScrollPane bookPane = new JScrollPane(bookTable);
    bookTable.getTableHeader().setReorderingAllowed(false);

    // User Table Column Names
    String[] columnNames1 = { "User ID", "Name", "Phone Number" };
    // Creating User Table
    JTable userTable = new JTable(new DefaultTableModel(null, columnNames1));
    model = (DefaultTableModel) userTable.getModel();
    // Adding User Table to the Scroll Pane
    JScrollPane userPane = new JScrollPane(userTable);
    userTable.getTableHeader().setReorderingAllowed(false);

    // Adding Tables to Tabs then to the TABLE Frame
    JTabbedPane tablePane = new JTabbedPane();
    tablePane.add("Books", bookPane);
    tablePane.add("Users", userPane);
    tablePane.setPreferredSize(new Dimension(700, 470));
    TABLE.add(tablePane);

    // Panel for containing the Edit and Remove buttons
    JPanel bottomRight = new JPanel();
    bottomRight.setLayout(new BoxLayout(bottomRight, BoxLayout.X_AXIS));
    rightPanel.add(bottomRight);

    // Creating the Edit and Remove buttons
    JButton edit = new JButton("Edit");
    edit.setEnabled(false);
    JButton remove = new JButton("Remove");
    remove.setEnabled(false);

    // Adding and placing the buttons
    bottomRight.add(Box.createRigidArea(new Dimension(552, 0)));
    bottomRight.add(edit);
    bottomRight.add(Box.createRigidArea(new Dimension(10, 0)));
    bottomRight.add(remove);

  }
}