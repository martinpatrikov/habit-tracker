import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

public class HabitTracker extends JFrame {

    public static void activityButtonsAction(String check, JComboBox<String> cb, DefaultTableModel model, JComboBox<String> dropDownMenu, DefaultListModel<String> builtHabits) {
        if(cb.getSelectedItem() != null){
            String chosenHabit = cb.getSelectedItem().toString();

            for (int i = 0; i < model.getRowCount(); i++) {

                if (model.getValueAt(i, 0).equals(chosenHabit)) {
                    int miss = -1;
                    for (int j = 0; j < model.getColumnCount() - 1; j++) {
                        if(model.getValueAt(i, j + 1) == "-"){
                            miss = j;
                        }
                        if (model.getValueAt(i, j + 1) == null) {
                            model.setValueAt(check, i, j + 1);
                            if(j  >= 20 && check.equals("+") && j - miss > 20){
                                builtHabits.addElement(chosenHabit);
                                JOptionPane.showMessageDialog(null, "You have successfully built a new healthy habit!");
                                dropDownMenu.removeItemAt(dropDownMenu.getSelectedIndex());
                                model.removeRow(i);
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        }else{
            JOptionPane.showMessageDialog(new JFrame("temp"), "Please select a habit from the list");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Habit Tracker");
        frame.setSize(1400, 600);
        frame.getContentPane().setBackground(Color.decode("#354f52"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultListModel<String> builtHabits = new DefaultListModel<String>();

        JPanel basePanel = new JPanel();
        frame.add(basePanel);
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.decode("#674747"));
        titlePanel.setPreferredSize(new Dimension(100, 100));
        JLabel title = new JLabel("Habit Tracker");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);
        basePanel.add(titlePanel);

        JPanel mainPanel = new JPanel();
        basePanel.add(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JPanel leftBorderPanel = new JPanel();
        mainPanel.add(leftBorderPanel);
        leftBorderPanel.setBackground(Color.LIGHT_GRAY);
        leftBorderPanel.setPreferredSize(new Dimension(10, 300));

        JPanel tablePanel = new JPanel();
        mainPanel.add(tablePanel);
        tablePanel.setPreferredSize(new Dimension(1200, 300));
        tablePanel.setBackground(Color.LIGHT_GRAY);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

        JPanel rightBorderPanel = new JPanel();
        mainPanel.add(rightBorderPanel);
        rightBorderPanel.setBackground(Color.LIGHT_GRAY);
        rightBorderPanel.setPreferredSize(new Dimension(10, 300));

        JPanel rightPanel = new JPanel();
        mainPanel.add(rightPanel);
        rightPanel.setBackground(Color.decode("#674747"));
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel upperBorderLabel = new JLabel(" ");
        tablePanel.add(upperBorderLabel);

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable habitsTable = new JTable(tableModel) {
            public boolean isCellEditable(int data, int columns) {
                return false;
            }
            public Component prepareRenderer(TableCellRenderer r, int row, int column) {

                Component cell = super.prepareRenderer(r, row, column);
                Object value = getModel().getValueAt(row, column);
                if (value != null) {
                    if (value.equals("-")) {
                        cell.setBackground(Color.decode("#FF6464"));
                    } else if (value.equals("+")) {
                        cell.setBackground(Color.decode("#B3FFAE"));
                    } else {
                        cell.setBackground(Color.decode("#e9e9e9"));
                    }
                } else {
                    cell.setBackground(Color.decode("#f8f4e6"));
                }
                return cell;
            }

        };

        habitsTable.setRowSelectionAllowed(false);
        habitsTable.getTableHeader().setReorderingAllowed(false);

        tableModel.addColumn("Habits\\Days");
        habitsTable.getColumnModel().getColumn(0).setMinWidth(300);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        for (int i = 0; i < 31; i++) {
            tableModel.addColumn(String.valueOf(i + 1));
        }
        for (int i = 0; i < 31; i++) {
            habitsTable.getColumnModel().getColumn(i + 1).setMaxWidth(50);
            habitsTable.getColumnModel().getColumn(i + 1).setCellRenderer( centerRenderer );;
        }
        tablePanel.add(new JScrollPane(habitsTable));

        JLabel downBorderLabel = new JLabel(" ");
        tablePanel.add(downBorderLabel);

        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.insets = new Insets(5, 5, 5, 5);

        JLabel builtHabitsLabel = new JLabel("Built habits: ");
        builtHabitsLabel.setForeground(Color.WHITE);
        gridConstraints.gridwidth = 2;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(builtHabitsLabel, gridConstraints);

        JList<String> builtHabitsPane = new JList<>(builtHabits);
        builtHabitsPane.setPreferredSize(new Dimension(50,100));
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 2;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(builtHabitsPane, gridConstraints);

        JLabel checkLabel = new JLabel("Choose habit: ");
        checkLabel.setForeground(Color.WHITE);
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 2;
        gridConstraints.gridwidth = 2;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(checkLabel, gridConstraints);

        ArrayList<String> menuHabits = new ArrayList<>();

        JComboBox<String> dropDownMenu = new JComboBox<>();

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 2;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(dropDownMenu, gridConstraints);

        JButton doneButton = new JButton("Done");
        doneButton.setBackground(Color.decode("#cad2c5"));
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 4;
        gridConstraints.gridwidth = 1;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(doneButton, gridConstraints);

        doneButton.addActionListener(e -> activityButtonsAction("+", dropDownMenu, tableModel, dropDownMenu, builtHabits));

        JButton missedButton = new JButton("Missed");
        missedButton.setBackground(Color.decode("#cad2c5"));

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 4;
        gridConstraints.gridwidth = 1;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(missedButton, gridConstraints);

        missedButton.addActionListener(e -> activityButtonsAction("-", dropDownMenu, tableModel, dropDownMenu, builtHabits));

        JLabel addLabel = new JLabel("New habit:");
        addLabel.setForeground(Color.WHITE);
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 5;
        gridConstraints.gridwidth = 2;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(addLabel, gridConstraints);

        JTextField addHabit = new JTextField();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 6;
        gridConstraints.gridwidth = 2;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(addHabit, gridConstraints);

        JButton addButton = new JButton("Add");
        addButton.setBackground(Color.decode("#cad2c5"));

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 7;
        gridConstraints.gridwidth = 2;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(addButton, gridConstraints);

        addButton.addActionListener(e -> {
            String habitToAdd = addHabit.getText();
            tableModel.insertRow(tableModel.getRowCount(), new Object[] { habitToAdd });
            dropDownMenu.addItem(habitToAdd);
            menuHabits.add(habitToAdd);
            addHabit.setText(null);

        });

        frame.setVisible(true);
    }
}
