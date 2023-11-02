
// Travis Tan
// 10-23-23
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.w3c.dom.events.MouseEvent;

public class MainWindow {
  JFrame frame;
  JPanel leftPanel;
  JPanel rightPanel;

  private JButton addBook;
  private JButton addUser;
  private JButton checkIn;
  private JButton checkOut;

  private DefaultTableModel bookModel;
  private DefaultTableModel userModel;

  private JTextField mainSearch;

  JTable userTable;
  JTable bookTable;

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
    main.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
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

    // Add Book Button Action Listener
    addBook.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            AddBook.openBookGUI(frame);
          }
        });
      }
    });

    // Add User Button
    addUser = new JButton("Add User");
    addUser.setPreferredSize(new Dimension(100, 50));
    addUser.setAlignmentX(Component.CENTER_ALIGNMENT);
    addUser.setMaximumSize(new Dimension(100, addUser.getMinimumSize().height));

    // Add User Button Action Listener
    addUser.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            AddUser.openAddUserGUI(frame);
          }
        });
      }
    });

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

    // Action Listener for Check-In Button
    // Pops up the Check Out Form
    checkIn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            new CheckInForm(frame);
          }
        });
      }
    });

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
    // Adding funtion to Search Bar DW 11/2/2023
    Searching search = new Searching();
    KeyListener keylistener = new KeyListener(){
      public void keyPressed(KeyEvent keyEvent) {
      }
      @Override
      public void keyTyped(KeyEvent e) {
      }
      @Override
      public void keyReleased(KeyEvent e) {
        search.search(mainSearch.getText(), bookModel, bookTable);
      }
    };
    mainSearch.addKeyListener(keylistener);
    
    // Adding Search Bar to TABLE pane
    TABLE.add(Box.createRigidArea(new Dimension(0, 30)));
    TABLE.add(mainSearch);
    TABLE.add(Box.createRigidArea(new Dimension(0, 10)));
    
    // Book Table Column Names
    String[] columnNames = { "Book ID", "Title", "Author", "Genre", "ISBN" };
    
    // Creating Book Table
    bookTable = new JTable(new DefaultTableModel(null, columnNames));
    bookModel = (DefaultTableModel) bookTable.getModel();
    // column header is not draggable
    bookTable.getTableHeader().setReorderingAllowed(false);
    bookTable.setDefaultEditor(Object.class, null);
    // can only highlight one row at a time
    bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    // Adding Book Table to the Scroll Pane
    JScrollPane bookPane = new JScrollPane(bookTable);
    
    // Centering Text on each cell on Book Table
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < columnNames.length; i++) {
      bookTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    // Adding data to Book Table DW 10/25/2023
    Book book = new Book();
    try {
      book.displayBooks(bookModel);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // User Table Column Names
    String[] columnNames1 = { "User ID", "Name", "Phone Number" };

    // Creating User Table
    userTable = new JTable(new DefaultTableModel(null, columnNames1));
    userModel = (DefaultTableModel) userTable.getModel();
    // column headers is not draggable
    userTable.getTableHeader().setReorderingAllowed(false);
    userTable.setDefaultEditor(Object.class, null);
    // can only highlight one row at a time
    userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // Adding User Table to the Scroll Pane
    JScrollPane userPane = new JScrollPane(userTable);

    // Centering Text on each cell on User Table
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < columnNames1.length; i++) {
      userTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    // Adding data to User Table
    User user = new User();
    try {
      user.displayUsers(userModel);
    } catch (SQLException e1) {
      e1.printStackTrace();
    }

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

    // Action Listener for Edit button
    edit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // if User Table is in focus, pops up an edit form for users
        int userTableFocus = userTable.getSelectedRow();
        if (userTableFocus >= 0) {
          JOptionPane.showMessageDialog(null, "EDIT User", "Error Message", JOptionPane.ERROR_MESSAGE);
          // Clears focus of the User Table
          userTable.clearSelection();
        }

        // if Book Table is in focus, pops up an edit form for books
        int bookTableFocus = bookTable.getSelectedRow();
        if (bookTableFocus >= 0) {
          JOptionPane.showMessageDialog(null, "EDIT Book", "Error Message", JOptionPane.ERROR_MESSAGE);
          bookTable.clearSelection();
        }
      }
    });

    // Enabling Buttons and Clearing Focus on the User Table
    userTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent event) {

        // enables the Edit and Remove button if a row is selected in the User Table
        edit.setEnabled(true);
        remove.setEnabled(true);

        // disables the Edit and Remove buttons when it loses focus
        if (userTable.getSelectionModel().isSelectionEmpty()) {
          edit.setEnabled(false);
          remove.setEnabled(false);
        }

        // clears the focus on the Book Table when they select a row from the User Table
        if (!userTable.getSelectionModel().isSelectionEmpty()) {
          bookTable.clearSelection();
        }
      }
    });

    // Enabling Buttons and Clearing Focus on the Book Table
    bookTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent event) {
        // enables the Edit and Remove button if a row is selected in the Book Table
        edit.setEnabled(true);
        remove.setEnabled(true);

        // disables the Edit and Remove buttons when it loses focus
        if (bookTable.getSelectionModel().isSelectionEmpty()) {
          edit.setEnabled(false);
          remove.setEnabled(false);
        }

        // clears the focus on the User Table when they select a row from the Book Table
        if (!bookTable.getSelectionModel().isSelectionEmpty()) {
          userTable.clearSelection();
        }
      }
    });

    // Adding and placing the buttons
    bottomRight.add(Box.createRigidArea(new Dimension(552, 0)));
    bottomRight.add(edit);
    bottomRight.add(Box.createRigidArea(new Dimension(10, 0)));
    bottomRight.add(remove);

  }
}
