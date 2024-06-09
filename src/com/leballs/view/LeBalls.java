/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.leballs.view;

import com.leballs.controller.MergeSort;
import com.leballs.controller.BinarySearch;
import com.leballs.utils.StringUtil;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.leballs.model.ModelClass;

/**
 *
 * @author Aakarshan Khadka AI3
 */
public class LeBalls extends javax.swing.JFrame {

    private String updateJerseyNumber;
    private ArrayList<ModelClass> arLeballs = new ArrayList<>();

    public void arrayList() {
        arLeballs.clear();
        for (int i = 0; i < tbMainTable.getRowCount(); i++) {
            int JerseyNumber = Integer.parseInt((String) tbMainTable.getValueAt(i, 0));
            String Name = (String) tbMainTable.getValueAt(i, 1);
            String GamesPlayed = (String) tbMainTable.getValueAt(i, 2);
            float PointsPerGame = Float.parseFloat((String) tbMainTable.getValueAt(i, 3));
            float ReboundsPerGame = Float.parseFloat((String) tbMainTable.getValueAt(i, 4));
            float AssistsPerGame = Float.parseFloat((String) tbMainTable.getValueAt(i, 5));
            float BlocksPerGame = Float.parseFloat((String) tbMainTable.getValueAt(i, 6));

            // Using model class constructor 
            ModelClass model = new ModelClass();
            model.setJerseyNumber(String.valueOf(JerseyNumber));
            model.setName(Name);
            model.setGamesPlayed(GamesPlayed);
            model.setPointsPerGame(String.valueOf(PointsPerGame));
            model.setReboundsPerGame(String.valueOf(ReboundsPerGame)); // Convert float to String
            model.setAssistsPerGame(String.valueOf(AssistsPerGame)); // Convert float to String
            model.setBlocksPerGame(String.valueOf(BlocksPerGame)); // Convert float to String

            // Add the ModelClass instance to the cine_list_model ArrayList
            arLeballs.add(model);

        }
    }

    public LeBalls() {

        initComponents();

        pnAddPanel.setVisible(false);
        pnUpdatePanel.setVisible(false);
        pnMainPanel.setVisible(true);
        pnSuccessPanel.setVisible(false);
        arrayList();
    }

    /**
     * Creates new form LeBalls
     */
    private static boolean validateStats(String input) {
        try {

            float floatValue = Float.parseFloat(input);

            return floatValue >= 0 && floatValue < 1000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Check if Jersey Number already exists in the table
    private boolean isJerseyNumberDuplicate(String jerseyNumber) {
        DefaultTableModel tableModel = (DefaultTableModel) tbMainTable.getModel();
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String existingJerseyNumber = (String) tableModel.getValueAt(i, 0);
            if (jerseyNumber.equals(existingJerseyNumber)) {
                return true;
            }
        }
        return false;
    }

// Check if combination of Jersey Number and Name already exists in the table
    private boolean isPlayerDuplicate(String jerseyNumber, String name) {
        DefaultTableModel tableModel = (DefaultTableModel) tbMainTable.getModel();
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String existingJerseyNumber = ((String) tableModel.getValueAt(i, 0)).trim().toLowerCase();
            String existingName = ((String) tableModel.getValueAt(i, 1)).trim().toLowerCase();
            if (jerseyNumber.trim().toLowerCase().equals(existingJerseyNumber)
                    && name.trim().toLowerCase().equals(existingName)) {
                return true;
            }
        }
        return false;
    }

    private void updateTableWithSortedData(ArrayList<ModelClass> sortedList) {

        DefaultTableModel model = (DefaultTableModel) tbMainTable.getModel();
        model.setRowCount(0);

        for (ModelClass sortedModel : sortedList) {
            Object[] rowData = {
                sortedModel.getJerseyNumber(),
                sortedModel.getName(),
                sortedModel.getGamesPlayed(),
                sortedModel.getPointsPerGame(),
                sortedModel.getReboundsPerGame(),
                sortedModel.getAssistsPerGame(),
                sortedModel.getBlocksPerGame(), // Add other columns as needed
            };
            model.addRow(rowData);
        }

        tbMainTable.repaint();

    }

    private void sortByPointsPerGame() {

        // Create an instance of MergeSort
        MergeSort mergeSorter = new MergeSort();

        // Perform the sorting based on points per game
        ArrayList<ModelClass> sortedList = mergeSorter.mergeSort(arLeballs, "Points Per Game");

        // Update the table with sorted data
        updateTableWithSortedData(sortedList);
    }

    private void sortByName() {

        MergeSort mergeSorter = new MergeSort();

        ArrayList<ModelClass> sortedList = mergeSorter.mergeSort(arLeballs, "Name");

        updateTableWithSortedData(sortedList);

    }

    private void sortByAssistsPerGame() {

        // Create an instance of MergeSort
        MergeSort mergeSorter = new MergeSort();

        // Perform the sorting based on assists per game
        ArrayList<ModelClass> sortedList = mergeSorter.mergeSort(arLeballs, "Assists Per Game");

        // Update the table with sorted data
        updateTableWithSortedData(sortedList);
    }

    private void sortByJerseyNumber() {

        MergeSort mergeSorter = new MergeSort();
        ArrayList<ModelClass> sortedList = mergeSorter.mergeSort(arLeballs, "Jersey Number");

        // Update the table with sorted data
        updateTableWithSortedData(sortedList);

    }

    private void searchName() {

        ArrayList<Integer> positionHolder = new ArrayList<>();
        String binaryText = tfSearchField.getText();
        BinarySearch binaryModel = new BinarySearch();
        MergeSort mergesorter = new MergeSort();
        ArrayList<ModelClass> searchedList = new ArrayList<>();

        ArrayList<ModelClass> sortedList = mergesorter.mergeSort(arLeballs, (String) "Name");

        if (binaryText.isEmpty()) {
            JOptionPane.showMessageDialog(null, StringUtil.SEARCH_NAME_EMPTY_MSG);
            return;
        } else {

            binaryModel.searchString(sortedList, 0, arLeballs.size() - 1, binaryText,
                    positionHolder, (String) ModelClass.getSearchField());
            if (!binaryText.matches("[a-zA-Z,\\s.]+")) {
                JOptionPane.showMessageDialog(rootPane, StringUtil.NAME_INVALID_MSG);
                return;
            }
            if (positionHolder.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "No Player Found");
                return;
            }
            for (int i : positionHolder) {
                searchedList.add(sortedList.get(i));
            }

            DefaultTableModel model = (DefaultTableModel) tbMainTable.getModel();

            model.setRowCount(0);

            for (ModelClass sortedModel : searchedList) {
                Object[] rowData = {
                    sortedModel.getJerseyNumber(),
                    sortedModel.getName(),
                    sortedModel.getGamesPlayed(),
                    sortedModel.getPointsPerGame(),
                    sortedModel.getReboundsPerGame(),
                    sortedModel.getAssistsPerGame(),
                    sortedModel.getBlocksPerGame(),};
                model.addRow(rowData);
            }

            tbMainTable.repaint();

        }
    }

    private void searchJerseyNumber() {

        ModelClass.setSearchField("Search by Jersey Number");
        ArrayList<Integer> positionHolder = new ArrayList<>();
        String binaryText = tfSearchField.getText();

        BinarySearch binaryModel = new BinarySearch();
        MergeSort mergesorter = new MergeSort();
        ArrayList<ModelClass> searchedList = new ArrayList<>();

        ArrayList<ModelClass> sortedList = mergesorter.mergeSort(arLeballs, (String) "Jersey Number");

        if (binaryText.isEmpty()) {
            JOptionPane.showMessageDialog(null, StringUtil.SEARCH_JERSEY_EMPTY_MSG);
            return;
        } else {

            binaryModel.searchString(sortedList, 0, arLeballs.size() - 1,
                    binaryText, positionHolder, (String) ModelClass.getSearchField());
            if (!validateStats(binaryText)) {
                JOptionPane.showMessageDialog(rootPane, StringUtil.JERSEY_INVALID_MSG);
                return;
            }
            if (positionHolder.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "No Player Found");
                return;
            }
            for (int i : positionHolder) {
                searchedList.add(sortedList.get(i));
            }

            DefaultTableModel model = (DefaultTableModel) tbMainTable.getModel();

            model.setRowCount(0);

            for (ModelClass sortedModel : searchedList) {
                Object[] rowData = {
                    sortedModel.getJerseyNumber(),
                    sortedModel.getName(),
                    sortedModel.getGamesPlayed(),
                    sortedModel.getPointsPerGame(),
                    sortedModel.getReboundsPerGame(),
                    sortedModel.getAssistsPerGame(),
                    sortedModel.getBlocksPerGame(),};
                model.addRow(rowData);
            }

            tbMainTable.repaint();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnMainPanel = new javax.swing.JPanel();
        pnTitlePanel = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        lbLogo = new javax.swing.JLabel();
        lbLogo1 = new javax.swing.JLabel();
        pnScrollPane = new javax.swing.JScrollPane();
        tbMainTable = new javax.swing.JTable();
        lbSortLabel = new javax.swing.JLabel();
        cbSortComboBox = new javax.swing.JComboBox<>();
        buDeleteButton = new javax.swing.JButton();
        buUpdateButton = new javax.swing.JButton();
        buAddButton = new javax.swing.JButton();
        buRadioButton = new javax.swing.JRadioButton();
        lbSearchLabel = new javax.swing.JLabel();
        tfSearchField = new javax.swing.JTextField();
        buSearchButton = new javax.swing.JButton();
        buResetButton = new javax.swing.JButton();
        pnAddPanel = new javax.swing.JPanel();
        tfRPG = new javax.swing.JTextField();
        buAddInTable = new javax.swing.JButton();
        tfGP = new javax.swing.JTextField();
        tfAPG = new javax.swing.JTextField();
        tfName = new javax.swing.JTextField();
        tfBPG = new javax.swing.JTextField();
        tfPPG = new javax.swing.JTextField();
        bnBackButtonAdd = new javax.swing.JButton();
        tfJN = new javax.swing.JTextField();
        lbAddGIF = new javax.swing.JLabel();
        pnUpdatePanel = new javax.swing.JPanel();
        buUpdateInTable = new javax.swing.JButton();
        tfRPG1 = new javax.swing.JTextField();
        tfGP1 = new javax.swing.JTextField();
        tfAPG1 = new javax.swing.JTextField();
        tfName1 = new javax.swing.JTextField();
        tfBPG1 = new javax.swing.JTextField();
        tfPPG1 = new javax.swing.JTextField();
        bnBackButtonA1 = new javax.swing.JButton();
        lbAddGIF1 = new javax.swing.JLabel();
        pnSuccessPanel = new javax.swing.JPanel();
        lbSuccess = new javax.swing.JLabel();
        buSuccessButton = new javax.swing.JButton();
        lbSuccessGIF = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 950, 480));
        setMaximumSize(new java.awt.Dimension(980, 510));
        setPreferredSize(new java.awt.Dimension(960, 490));
        setSize(new java.awt.Dimension(950, 480));
        getContentPane().setLayout(null);

        pnMainPanel.setBackground(new java.awt.Color(127, 90, 131));
        pnMainPanel.setMaximumSize(new java.awt.Dimension(950, 480));
        pnMainPanel.setPreferredSize(new java.awt.Dimension(950, 480));

        pnTitlePanel.setBackground(new java.awt.Color(127, 90, 131));

        lbTitle.setFont(new java.awt.Font("Snap ITC", 1, 48)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(228, 253, 225));
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setLabelFor(pnTitlePanel);
        lbTitle.setText("!LeBalls!");
        lbTitle.setToolTipText("");

        lbLogo.setBackground(new java.awt.Color(0, 0, 0));
        lbLogo.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Desktop\\LeBalls\\LeFaceCropped.png")); // NOI18N

        lbLogo1.setBackground(new java.awt.Color(0, 0, 0));
        lbLogo1.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Desktop\\LeBalls\\LeFaceCropped.png")); // NOI18N

        javax.swing.GroupLayout pnTitlePanelLayout = new javax.swing.GroupLayout(pnTitlePanel);
        pnTitlePanel.setLayout(pnTitlePanelLayout);
        pnTitlePanelLayout.setHorizontalGroup(
            pnTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTitlePanelLayout.createSequentialGroup()
                .addContainerGap(178, Short.MAX_VALUE)
                .addComponent(lbLogo1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(292, 292, 292))
        );
        pnTitlePanelLayout.setVerticalGroup(
            pnTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTitlePanelLayout.createSequentialGroup()
                .addGroup(pnTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(lbLogo1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tbMainTable.setBackground(new java.awt.Color(0, 0, 0));
        tbMainTable.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(239, 211, 215)));
        tbMainTable.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tbMainTable.setForeground(new java.awt.Color(228, 253, 225));
        tbMainTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"27", "LeBron James", "22", "25.0", "7.5", "6.6", "0.7"},
                {"22", "A. Davis", "22", "22.7", "12.7", "3.2", "2.7"},
                {"21", "D. Russel", "23", "16.6", "3.3", "6.3", "0.5"},
                {"47", "A. Reaves", "23", "14.1", "4.7", "4.7", "0.1"},
                {"59", "R. Hachimura", "14", "11.4", "3.5", "1.1", "0.2"},
                {"48", "T. Prince", "21", "8.8", "2.5", "1.4", "0.4"},
                {"29", "C. Reddish", "19", "7.1", "2.8", "1.2", "0.3"},
                {"81", "C. Wood", "22", "6.9", "5.9", "0.9", "0.5"},
                {"999", "G. Vincent", "04", "6.0", "1.0", "3.0", "0.0"},
                {"101", "M. Christie", "18", "5.0", "2.7", "1.0", "0.2"},
                {"83", "C. Castleton", "04", "3.0", "1.8", "0.8", "0.0"},
                {"1", "J. Hayes", "31", "2.9", "1.8", "0.3", "0.3"},
                {"63", "J. Vanderbilt", "17", "2.5", "4.1", "0.9", "0.2"},
                {"7", "J. Hood-Schifino", "08", "2.3", "1.0", "0.6", "0.1"},
                {"56", "D. Hodge", "07", "2.0", "0.0", "0.7", "0.1"},
                {"65", "A. Fudge", "04", "1.0", "0.5", "0.0", "0.0"}
            },
            new String [] {
                "Jersey Number", "LePlayer", "LeGames Played", "LePoints Per Game", "LeRebounds Per Game", "LeAssists Per Game", "LeBlocks Per Game"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbMainTable.setName(""); // NOI18N
        tbMainTable.setShowGrid(false);
        pnScrollPane.setViewportView(tbMainTable);
        if (tbMainTable.getColumnModel().getColumnCount() > 0) {
            tbMainTable.getColumnModel().getColumn(0).setResizable(false);
            tbMainTable.getColumnModel().getColumn(1).setResizable(false);
            tbMainTable.getColumnModel().getColumn(2).setResizable(false);
            tbMainTable.getColumnModel().getColumn(3).setResizable(false);
            tbMainTable.getColumnModel().getColumn(4).setResizable(false);
            tbMainTable.getColumnModel().getColumn(5).setResizable(false);
            tbMainTable.getColumnModel().getColumn(6).setResizable(false);
        }

        lbSortLabel.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbSortLabel.setForeground(new java.awt.Color(228, 253, 225));
        lbSortLabel.setText("Sort By");

        cbSortComboBox.setBackground(new java.awt.Color(0, 0, 0));
        cbSortComboBox.setFont(new java.awt.Font("Dubai", 1, 12)); // NOI18N
        cbSortComboBox.setForeground(new java.awt.Color(228, 253, 225));
        cbSortComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "LeName", "Jersey Number", "LePoints Per Game", "LeAssists Per Game" }));
        cbSortComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSortComboBoxActionPerformed(evt);
            }
        });

        buDeleteButton.setBackground(new java.awt.Color(0, 0, 0));
        buDeleteButton.setForeground(new java.awt.Color(228, 253, 225));
        buDeleteButton.setText("Delete");
        buDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buDeleteButtonActionPerformed(evt);
            }
        });

        buUpdateButton.setBackground(new java.awt.Color(0, 0, 0));
        buUpdateButton.setForeground(new java.awt.Color(228, 253, 225));
        buUpdateButton.setText("Update");
        buUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buUpdateButtonActionPerformed(evt);
            }
        });

        buAddButton.setBackground(new java.awt.Color(0, 0, 0));
        buAddButton.setForeground(new java.awt.Color(228, 253, 225));
        buAddButton.setText("Add");
        buAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buAddButtonActionPerformed(evt);
            }
        });

        buRadioButton.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        buRadioButton.setForeground(new java.awt.Color(228, 253, 225));
        buRadioButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buRadioButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buRadioButtonMouseExited(evt);
            }
        });
        buRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buRadioButtonActionPerformed(evt);
            }
        });

        lbSearchLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbSearchLabel.setForeground(new java.awt.Color(228, 253, 225));
        lbSearchLabel.setText("Name");

        tfSearchField.setBackground(new java.awt.Color(0, 0, 0));
        tfSearchField.setForeground(new java.awt.Color(228, 253, 225));

        buSearchButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Desktop\\LeBalls\\LeSearchIcon.png")); // NOI18N
        buSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buSearchButtonActionPerformed(evt);
            }
        });

        buResetButton.setBackground(new java.awt.Color(0, 0, 0));
        buResetButton.setForeground(new java.awt.Color(228, 253, 225));
        buResetButton.setText("Reset");
        buResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buResetButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnMainPanelLayout = new javax.swing.GroupLayout(pnMainPanel);
        pnMainPanel.setLayout(pnMainPanelLayout);
        pnMainPanelLayout.setHorizontalGroup(
            pnMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnTitlePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnMainPanelLayout.createSequentialGroup()
                        .addComponent(lbSortLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSortComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainPanelLayout.createSequentialGroup()
                                .addComponent(lbSearchLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buRadioButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainPanelLayout.createSequentialGroup()
                        .addComponent(pnScrollPane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buDeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buUpdateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buAddButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buResetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        pnMainPanelLayout.setVerticalGroup(
            pnMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainPanelLayout.createSequentialGroup()
                .addComponent(pnTitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buSearchButton)
                    .addGroup(pnMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbSearchLabel)
                        .addComponent(tfSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbSortLabel)
                    .addComponent(cbSortComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainPanelLayout.createSequentialGroup()
                        .addComponent(buAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buUpdateButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buDeleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buResetButton)
                        .addGap(77, 77, 77))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainPanelLayout.createSequentialGroup()
                        .addComponent(pnScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
        );

        getContentPane().add(pnMainPanel);
        pnMainPanel.setBounds(0, 0, 950, 480);

        pnAddPanel.setBackground(new java.awt.Color(51, 51, 51));
        pnAddPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Player", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Snap ITC", 1, 36), new java.awt.Color(228, 253, 225))); // NOI18N
        pnAddPanel.setForeground(new java.awt.Color(228, 253, 225));
        pnAddPanel.setMaximumSize(new java.awt.Dimension(950, 480));
        pnAddPanel.setPreferredSize(new java.awt.Dimension(950, 480));

        tfRPG.setBackground(new java.awt.Color(0, 0, 0));
        tfRPG.setForeground(new java.awt.Color(228, 253, 225));
        tfRPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rebounds Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225))); // NOI18N
        tfRPG.setPreferredSize(new java.awt.Dimension(150, 55));
        tfRPG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfRPGKeyPressed(evt);
            }
        });

        buAddInTable.setBackground(new java.awt.Color(0, 0, 0));
        buAddInTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buAddInTable.setForeground(new java.awt.Color(228, 253, 225));
        buAddInTable.setText("Add");
        buAddInTable.setMaximumSize(new java.awt.Dimension(85, 27));
        buAddInTable.setMinimumSize(new java.awt.Dimension(85, 27));
        buAddInTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buAddInTableActionPerformed(evt);
            }
        });

        tfGP.setBackground(new java.awt.Color(0, 0, 0));
        tfGP.setForeground(new java.awt.Color(228, 253, 225));
        tfGP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Games Played", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225))); // NOI18N
        tfGP.setPreferredSize(new java.awt.Dimension(150, 55));
        tfGP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfGPKeyPressed(evt);
            }
        });

        tfAPG.setBackground(new java.awt.Color(0, 0, 0));
        tfAPG.setForeground(new java.awt.Color(228, 253, 225));
        tfAPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Assists Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225))); // NOI18N
        tfAPG.setPreferredSize(new java.awt.Dimension(150, 55));
        tfAPG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfAPGKeyPressed(evt);
            }
        });

        tfName.setBackground(new java.awt.Color(0, 0, 0));
        tfName.setForeground(new java.awt.Color(228, 253, 225));
        tfName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225))); // NOI18N
        tfName.setPreferredSize(new java.awt.Dimension(150, 55));
        tfName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfNameKeyPressed(evt);
            }
        });

        tfBPG.setBackground(new java.awt.Color(0, 0, 0));
        tfBPG.setForeground(new java.awt.Color(228, 253, 225));
        tfBPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Blocks Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225))); // NOI18N
        tfBPG.setPreferredSize(new java.awt.Dimension(150, 55));
        tfBPG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfBPGKeyPressed(evt);
            }
        });

        tfPPG.setBackground(new java.awt.Color(0, 0, 0));
        tfPPG.setForeground(new java.awt.Color(228, 253, 225));
        tfPPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Points Per Game\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225))); // NOI18N
        tfPPG.setPreferredSize(new java.awt.Dimension(150, 55));
        tfPPG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfPPGKeyPressed(evt);
            }
        });

        bnBackButtonAdd.setBackground(new java.awt.Color(0, 0, 0));
        bnBackButtonAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bnBackButtonAdd.setForeground(new java.awt.Color(228, 253, 225));
        bnBackButtonAdd.setText("Back");
        bnBackButtonAdd.setPreferredSize(new java.awt.Dimension(85, 27));
        bnBackButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnBackButtonAddActionPerformed(evt);
            }
        });

        tfJN.setBackground(new java.awt.Color(0, 0, 0));
        tfJN.setForeground(new java.awt.Color(228, 253, 225));
        tfJN.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jersey Number", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225))); // NOI18N
        tfJN.setPreferredSize(new java.awt.Dimension(150, 55));
        tfJN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfJNKeyPressed(evt);
            }
        });

        lbAddGIF.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Desktop\\LeBalls\\AddGIF.gif")); // NOI18N

        javax.swing.GroupLayout pnAddPanelLayout = new javax.swing.GroupLayout(pnAddPanel);
        pnAddPanel.setLayout(pnAddPanelLayout);
        pnAddPanelLayout.setHorizontalGroup(
            pnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAddPanelLayout.createSequentialGroup()
                .addGroup(pnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnAddPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bnBackButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buAddInTable, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(pnAddPanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(pnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnAddPanelLayout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(tfJN, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnAddPanelLayout.createSequentialGroup()
                                .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfGP, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnAddPanelLayout.createSequentialGroup()
                                .addComponent(tfPPG, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfRPG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnAddPanelLayout.createSequentialGroup()
                                .addComponent(tfAPG, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfBPG, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)))
                .addComponent(lbAddGIF, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnAddPanelLayout.setVerticalGroup(
            pnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAddPanelLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(pnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfGP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPPG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfRPG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfAPG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfBPG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tfJN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(pnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bnBackButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buAddInTable, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
            .addGroup(pnAddPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbAddGIF, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnAddPanel);
        pnAddPanel.setBounds(0, 710, 950, 480);

        pnUpdatePanel.setBackground(new java.awt.Color(51, 51, 51));
        pnUpdatePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Update Player", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Snap ITC", 1, 36), new java.awt.Color(228, 253, 225))); // NOI18N
        pnUpdatePanel.setForeground(new java.awt.Color(228, 253, 225));
        pnUpdatePanel.setPreferredSize(new java.awt.Dimension(950, 480));

        buUpdateInTable.setBackground(new java.awt.Color(0, 0, 0));
        buUpdateInTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buUpdateInTable.setForeground(new java.awt.Color(228, 253, 225));
        buUpdateInTable.setText("Update");
        buUpdateInTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buUpdateInTableActionPerformed(evt);
            }
        });

        tfRPG1.setBackground(new java.awt.Color(0, 0, 0));
        tfRPG1.setForeground(new java.awt.Color(228, 253, 225));
        tfRPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rebounds Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225))); // NOI18N
        tfRPG1.setPreferredSize(new java.awt.Dimension(150, 55));
        tfRPG1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfRPG1KeyPressed(evt);
            }
        });

        tfGP1.setBackground(new java.awt.Color(0, 0, 0));
        tfGP1.setForeground(new java.awt.Color(228, 253, 225));
        tfGP1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Games Played", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225))); // NOI18N
        tfGP1.setPreferredSize(new java.awt.Dimension(150, 55));
        tfGP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfGP1KeyPressed(evt);
            }
        });

        tfAPG1.setBackground(new java.awt.Color(0, 0, 0));
        tfAPG1.setForeground(new java.awt.Color(228, 253, 225));
        tfAPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Assists Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225))); // NOI18N
        tfAPG1.setPreferredSize(new java.awt.Dimension(150, 55));
        tfAPG1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfAPG1KeyPressed(evt);
            }
        });

        tfName1.setBackground(new java.awt.Color(0, 0, 0));
        tfName1.setForeground(new java.awt.Color(228, 253, 225));
        tfName1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225))); // NOI18N
        tfName1.setPreferredSize(new java.awt.Dimension(150, 55));
        tfName1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfName1KeyPressed(evt);
            }
        });

        tfBPG1.setBackground(new java.awt.Color(0, 0, 0));
        tfBPG1.setForeground(new java.awt.Color(228, 253, 225));
        tfBPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Blocks Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225))); // NOI18N
        tfBPG1.setPreferredSize(new java.awt.Dimension(150, 55));
        tfBPG1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfBPG1KeyPressed(evt);
            }
        });

        tfPPG1.setBackground(new java.awt.Color(0, 0, 0));
        tfPPG1.setForeground(new java.awt.Color(228, 253, 225));
        tfPPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Points Per Game\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225))); // NOI18N
        tfPPG1.setPreferredSize(new java.awt.Dimension(150, 55));
        tfPPG1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfPPG1KeyPressed(evt);
            }
        });

        bnBackButtonA1.setBackground(new java.awt.Color(0, 0, 0));
        bnBackButtonA1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bnBackButtonA1.setForeground(new java.awt.Color(228, 253, 225));
        bnBackButtonA1.setText("Back");
        bnBackButtonA1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnBackButtonA1ActionPerformed(evt);
            }
        });

        lbAddGIF1.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Desktop\\LeBalls\\AddGIF.gif")); // NOI18N

        javax.swing.GroupLayout pnUpdatePanelLayout = new javax.swing.GroupLayout(pnUpdatePanel);
        pnUpdatePanel.setLayout(pnUpdatePanelLayout);
        pnUpdatePanelLayout.setHorizontalGroup(
            pnUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnUpdatePanelLayout.createSequentialGroup()
                .addGroup(pnUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnUpdatePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bnBackButtonA1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buUpdateInTable, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))
                    .addGroup(pnUpdatePanelLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(pnUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnUpdatePanelLayout.createSequentialGroup()
                                .addComponent(tfName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfGP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnUpdatePanelLayout.createSequentialGroup()
                                .addComponent(tfPPG1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfRPG1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnUpdatePanelLayout.createSequentialGroup()
                                .addComponent(tfAPG1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfBPG1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)))
                .addComponent(lbAddGIF1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnUpdatePanelLayout.setVerticalGroup(
            pnUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnUpdatePanelLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(pnUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnUpdatePanelLayout.createSequentialGroup()
                        .addComponent(tfGP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfRPG1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnUpdatePanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(tfName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfPPG1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfAPG1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfBPG1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buUpdateInTable, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bnBackButtonA1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnUpdatePanelLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(lbAddGIF1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        getContentPane().add(pnUpdatePanel);
        pnUpdatePanel.setBounds(0, 480, 950, 480);

        pnSuccessPanel.setBackground(new java.awt.Color(51, 51, 51));
        pnSuccessPanel.setPreferredSize(new java.awt.Dimension(950, 480));
        pnSuccessPanel.setLayout(null);

        lbSuccess.setBackground(new java.awt.Color(0, 0, 0));
        lbSuccess.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbSuccess.setForeground(new java.awt.Color(228, 253, 225));
        lbSuccess.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbSuccess.setText("!!Success!!");
        lbSuccess.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        pnSuccessPanel.add(lbSuccess);
        lbSuccess.setBounds(40, 20, 344, 69);

        buSuccessButton.setBackground(new java.awt.Color(0, 0, 0));
        buSuccessButton.setForeground(new java.awt.Color(228, 253, 225));
        buSuccessButton.setText("Done");
        buSuccessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buSuccessButtonActionPerformed(evt);
            }
        });
        pnSuccessPanel.add(buSuccessButton);
        buSuccessButton.setBounds(180, 420, 100, 30);

        lbSuccessGIF.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Desktop\\LeBalls\\fastsuccess.gif")); // NOI18N
        lbSuccessGIF.setText("jLabel2");
        pnSuccessPanel.add(lbSuccessGIF);
        lbSuccessGIF.setBounds(6, 0, 462, 480);

        getContentPane().add(pnSuccessPanel);
        pnSuccessPanel.setBounds(0, 0, 950, 480);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buSearchButtonActionPerformed
        // TODO add your handling code here:
        if (!buRadioButton.isSelected()) {
            searchName();
        } else {
            searchJerseyNumber();
        }
    }//GEN-LAST:event_buSearchButtonActionPerformed

    private void cbSortComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortComboBoxActionPerformed
        // TODO add your handling code here:

        // Get the selected item from the combo box
        String sortBy = (String) cbSortComboBox.getSelectedItem();

        // Check the selected item and perform sorting accordingly
        if ("LePoints Per Game".equals(sortBy)) {
            sortByPointsPerGame();
        } else if ("LeAssists Per Game".equals(sortBy)) {
            sortByAssistsPerGame();
        } else if ("LeName".equals(sortBy)) {
            sortByName();
        } else if ("Jersey Number".equals(sortBy)) {
            sortByJerseyNumber();
        } else if ("None".equals(sortBy)) {
            buResetButtonActionPerformed(evt);
        }

    }//GEN-LAST:event_cbSortComboBoxActionPerformed

    private void buAddInTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buAddInTableActionPerformed

        buResetButtonActionPerformed(evt);

        // fetching data
        String JerseyNumber = tfJN.getText();
        String Name = tfName.getText();
        String GamesPlayed = tfGP.getText();
        String PointsPerGame = tfPPG.getText();
        String ReboundsPerGame = tfRPG.getText();
        String AssistsPerGame = tfAPG.getText();
        String BlocksPerGame = tfBPG.getText();

        //validation
        if (Name.isEmpty()) {
            tfName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            JOptionPane.showMessageDialog(rootPane, StringUtil.NAME_EMPTY_MSG);
            return;
        }

        if (!Name.matches("[a-zA-Z,\\s.]+")) {
            JOptionPane.showMessageDialog(rootPane, StringUtil.NAME_INVALID_MSG);
            tfName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));

            return;
        }

        if (JerseyNumber.isEmpty()) {
            tfJN.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jersey Number", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            JOptionPane.showMessageDialog(rootPane, StringUtil.JERSEY_EMPTY_MSG);
            return;
        }

        if (GamesPlayed.isEmpty()) {
            tfGP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Games Played", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            JOptionPane.showMessageDialog(rootPane, StringUtil.GAMES_EMPTY_MSG);
            return;
        }

        if (PointsPerGame.isEmpty()) {
            tfPPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Points Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            JOptionPane.showMessageDialog(rootPane, StringUtil.POINTS_EMPTY_MSG);
            return;
        }

        if (ReboundsPerGame.isEmpty()) {
            tfRPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rebounds Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            JOptionPane.showMessageDialog(rootPane, StringUtil.REBOUNDS_EMPTY_MSG);
            return;
        }

        if (AssistsPerGame.isEmpty()) {
            tfAPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Assists Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            JOptionPane.showMessageDialog(rootPane, StringUtil.ASSISTS_EMPTY_MSG);
            return;
        }

        if (BlocksPerGame.isEmpty()) {
            tfBPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Blocks Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            JOptionPane.showMessageDialog(rootPane, StringUtil.BLOCKS_EMPTY_MSG);
            return;
        }
        if (!validateStats(JerseyNumber)) {
            JOptionPane.showMessageDialog(rootPane, StringUtil.JERSEY_INVALID_MSG);
            tfJN.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jersey Number", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));

            return;
        }
        if (!validateStats(GamesPlayed)) {
            JOptionPane.showMessageDialog(rootPane, StringUtil.GAMES_INVALID_MSG);
            tfGP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Games Played", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            return;
        }
        if (!validateStats(ReboundsPerGame)) {
            JOptionPane.showMessageDialog(rootPane, StringUtil.REBOUNDS_INVALID_MSG);
            tfRPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rebounds Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));

            return;
        }
        if (!validateStats(BlocksPerGame)) {
            JOptionPane.showMessageDialog(rootPane, StringUtil.BLOCKS_INVALID_MSG);
            tfBPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Blocks Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            return;
        }
        if (!validateStats(AssistsPerGame)) {
            JOptionPane.showMessageDialog(rootPane, StringUtil.ASSISTS_INVALID_MSG);
            tfAPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Assists Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            return;
        }
        if (!validateStats(PointsPerGame)) {
            JOptionPane.showMessageDialog(rootPane, StringUtil.POINTS_INVALID_MSG);
            tfPPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Points Per Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            return;
        }
        if (isPlayerDuplicate(JerseyNumber, Name)) {
            JOptionPane.showMessageDialog(rootPane, "Player with the same Jersey Number and Name already exists.");
            tfJN.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jersey Number",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            tfName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            int updateOption = JOptionPane.showConfirmDialog(rootPane, StringUtil.UPDATE_OPTION_MSG);
            if (updateOption == JOptionPane.YES_OPTION) {
                tfName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name",
                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION,
                        new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));
                tfJN.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jersey Number",
                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION,
                        new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));

                updateJerseyNumber = tfJN.getText();

                for (int row = 0; row < tbMainTable.getRowCount(); row++) {
                    int column = 0;
                    Object rowValue = tbMainTable.getValueAt(row, column);
                    if (rowValue.toString().equals(updateJerseyNumber)) {
                        pnMainPanel.setVisible(false);
                        pnAddPanel.setVisible(false);
                        pnUpdatePanel.setVisible(true);
                        tfName1.setText((tbMainTable.getValueAt(row, 1)).toString());
                        tfGP1.setText((tbMainTable.getValueAt(row, 2)).toString());
                        tfPPG1.setText((tbMainTable.getValueAt(row, 3)).toString());
                        tfRPG1.setText((tbMainTable.getValueAt(row, 4)).toString());
                        tfAPG1.setText((tbMainTable.getValueAt(row, 5)).toString());
                        tfBPG1.setText((tbMainTable.getValueAt(row, 6)).toString());
                        pnUpdatePanel.setVisible(true);

                        return;

                    }
                }
            }

            return;
        }
        // Check for duplicate jersey number
        if (isJerseyNumberDuplicate(JerseyNumber)) {
            JOptionPane.showMessageDialog(rootPane, "Cannot add player. Jersey Number already exists.");
            tfJN.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jersey Number",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            return;
        } else {

            // confirmation
            int userResponse = JOptionPane.showConfirmDialog(rootPane, "Are you sure?");
            if (userResponse == JOptionPane.YES_OPTION) {
                DefaultTableModel LeTable
                        = (DefaultTableModel) tbMainTable.getModel();

                LeTable.addRow(new Object[]{JerseyNumber, Name, GamesPlayed, PointsPerGame,
                    ReboundsPerGame, AssistsPerGame, BlocksPerGame});
                JOptionPane.showMessageDialog(rootPane, "Player added successfully!");

                pnSuccessPanel.setVisible(true);
                pnAddPanel.setVisible(false);
                setSize(490, 520);
                arrayList();
            }
        }
    }//GEN-LAST:event_buAddInTableActionPerformed

    private void buDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buDeleteButtonActionPerformed
        // TODO add your handling code here:

        String JerseyNumber = JOptionPane.showInputDialog("Enter Jersey Number");

        if (JerseyNumber == null) {
            return;
        }

        if (JerseyNumber.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter Jersey Number");
        } //return;
        else {
            DefaultTableModel tableModel = (DefaultTableModel) tbMainTable.getModel();
            for (int row = 0; row < tbMainTable.getRowCount(); row++) {
                int column = 0;
                Object rowValue = tbMainTable.getValueAt(row, column);
                if (rowValue.toString().equals(JerseyNumber)) {
                    tableModel.removeRow(row);
                    JOptionPane.showMessageDialog(this, "Player  has been sucessfully Removed");
                    arrayList();
                    return;
                }
            }
            // if jersey number name is incorrect
            JOptionPane.showMessageDialog(this, "Jersy Number not found ");

            //return;
        }


    }//GEN-LAST:event_buDeleteButtonActionPerformed

    private void buUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buUpdateButtonActionPerformed
        // TODO add your handling code here:
        updateJerseyNumber = JOptionPane.showInputDialog("Enter Jersey Number");

        if (this.updateJerseyNumber == null) {
            return;
        }
        if (updateJerseyNumber.isEmpty()) {
            JOptionPane.showMessageDialog(null, "JerseyNumber must be present!!");
            return;
        }

        pnMainPanel.setVisible(false);
        pnUpdatePanel.setVisible(true);
        pnUpdatePanel.setBounds(0, 0, 950, 480);
        pnAddPanel.setVisible(false);

        for (int row = 0; row < tbMainTable.getRowCount(); row++) {
            int column = 0;
            Object rowValue = tbMainTable.getValueAt(row, column);
            if (rowValue.toString().equals(updateJerseyNumber)) {
                pnMainPanel.setVisible(false);
                pnUpdatePanel.setVisible(true);
                tfName1.setText((tbMainTable.getValueAt(row, 1)).toString());
                tfGP1.setText((tbMainTable.getValueAt(row, 2)).toString());
                tfPPG1.setText((tbMainTable.getValueAt(row, 3)).toString());
                tfRPG1.setText((tbMainTable.getValueAt(row, 4)).toString());
                tfAPG1.setText((tbMainTable.getValueAt(row, 5)).toString());
                tfBPG1.setText((tbMainTable.getValueAt(row, 6)).toString());

                return;
            }
        }
    }//GEN-LAST:event_buUpdateButtonActionPerformed

    private void buAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buAddButtonActionPerformed
        // TODO add your handling code here:
        pnAddPanel.setVisible(true);
        pnAddPanel.setBounds(0, 0, 950, 480);
        pnMainPanel.setVisible(false);

        tfJN.setText("");
        tfName.setText("");
        tfGP.setText("");
        tfRPG.setText("");
        tfBPG.setText("");
        tfAPG.setText("");
        tfPPG.setText("");


    }//GEN-LAST:event_buAddButtonActionPerformed

    private void bnBackButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnBackButtonAddActionPerformed
        // TODO add your handling code here:
        pnMainPanel.setVisible(true);
        pnAddPanel.setVisible(false);

    }//GEN-LAST:event_bnBackButtonAddActionPerformed

    private void buUpdateInTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buUpdateInTableActionPerformed

        // fetching data from update panel
        String JerseyNumber = updateJerseyNumber;

        String Name = tfName1.getText();
        String GamesPlayed = tfGP1.getText();
        String PointsPerGame = tfPPG1.getText();
        String ReboundsPerGame = tfRPG1.getText();
        String AssistsPerGame = tfAPG1.getText();
        String BlocksPerGame = tfBPG1.getText();

        if (JerseyNumber.isEmpty()) {
            JOptionPane.showMessageDialog(null, "JerseyNumber must be present!!");
        } //return;
        else //validation
        if (Name.isEmpty()) {
            tfName1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            JOptionPane.showMessageDialog(rootPane, StringUtil.NAME_EMPTY_MSG);
            return;
        }

        if (!Name.matches("[a-zA-Z,\\s.]+")) {
            JOptionPane.showMessageDialog(rootPane, StringUtil.NAME_INVALID_MSG);
            tfName1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));

            return;
        }

        if (GamesPlayed.isEmpty()) {
            tfGP1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Games Played",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                    new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            JOptionPane.showMessageDialog(rootPane, StringUtil.GAMES_EMPTY_MSG);
            return;
        }

        if (PointsPerGame.isEmpty()) {
            tfPPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Points Per Game",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            JOptionPane.showMessageDialog(rootPane, StringUtil.POINTS_EMPTY_MSG);
            return;
        }

        if (ReboundsPerGame.isEmpty()) {
            tfRPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rebounds Per Game",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            JOptionPane.showMessageDialog(rootPane, StringUtil.REBOUNDS_EMPTY_MSG);
            return;
        }

        if (AssistsPerGame.isEmpty()) {
            tfAPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Assists Per Game",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            JOptionPane.showMessageDialog(rootPane, StringUtil.ASSISTS_EMPTY_MSG);
            return;
        }

        if (BlocksPerGame.isEmpty()) {
            tfBPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Blocks Per Game",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            JOptionPane.showMessageDialog(rootPane, StringUtil.BLOCKS_EMPTY_MSG);
            return;
        }

        if (!validateStats(GamesPlayed)) {
            JOptionPane.showMessageDialog(rootPane, StringUtil.GAMES_INVALID_MSG);
            tfGP1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Games Played",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            return;
        }
        if (!validateStats(ReboundsPerGame)) {
            JOptionPane.showMessageDialog(rootPane, StringUtil.REBOUNDS_INVALID_MSG);
            tfRPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rebounds Per Game",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));

            return;
        }
        if (!validateStats(BlocksPerGame)) {
            JOptionPane.showMessageDialog(rootPane, StringUtil.BLOCKS_INVALID_MSG);
            tfBPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Blocks Per Game",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            return;
        }
        if (!validateStats(AssistsPerGame)) {
            JOptionPane.showMessageDialog(rootPane, StringUtil.ASSISTS_INVALID_MSG);
            tfAPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Assists Per Game",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            return;
        }
        if (!validateStats(PointsPerGame)) {
            JOptionPane.showMessageDialog(rootPane, StringUtil.POINTS_INVALID_MSG);
            tfPPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Points Per Game",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12),
                    new java.awt.Color(255, 0, 0)));
            return;
        }

        // Find the row with the specified jersey number
        int foundRow = -1;
        for (int row = 0; row < tbMainTable.getRowCount(); row++) {
            String existingJerseyNumber = tbMainTable.getValueAt(row, 0).toString();
            if (JerseyNumber.equals(existingJerseyNumber)) {
                foundRow = row;
                break;
            }
        }

        if (foundRow == -1) {
            JOptionPane.showMessageDialog(rootPane, "Cannot update. Jersey Number does not exist.");
            return;
        }

        // updating the table 
        DefaultTableModel LeTable = (DefaultTableModel) tbMainTable.getModel();
        LeTable.setValueAt(JerseyNumber, foundRow, 0);
        LeTable.setValueAt(Name, foundRow, 1);
        LeTable.setValueAt(GamesPlayed, foundRow, 2);
        LeTable.setValueAt(PointsPerGame, foundRow, 3);
        LeTable.setValueAt(ReboundsPerGame, foundRow, 4);
        LeTable.setValueAt(AssistsPerGame, foundRow, 5);
        LeTable.setValueAt(BlocksPerGame, foundRow, 6);

        // confirmation
        int userResponse = JOptionPane.showConfirmDialog(rootPane, "Are you sure?");
        if (userResponse == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(rootPane, "Player updated successfully!");
            // Additional code to handle panel visibility or navigation if needed
            pnUpdatePanel.setVisible(false);
            pnMainPanel.setVisible(false);
            pnSuccessPanel.setVisible(true);
            setSize(490, 520);
            arrayList();
        }


    }//GEN-LAST:event_buUpdateInTableActionPerformed

    private void bnBackButtonA1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnBackButtonA1ActionPerformed
        // TODO add your handling code here:
        pnMainPanel.setVisible(true);
        pnUpdatePanel.setVisible(false);
    }//GEN-LAST:event_bnBackButtonA1ActionPerformed

    private void buRadioButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buRadioButtonMouseEntered
        // TODO add your handling code here:
        if (!buRadioButton.isSelected()) {
            lbSearchLabel.setText("JerseyNo.");
        }
    }//GEN-LAST:event_buRadioButtonMouseEntered

    private void buRadioButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buRadioButtonMouseExited
        // TODO add your handling code here:
        if (!buRadioButton.isSelected()) {
            lbSearchLabel.setText("Name");
        }
    }//GEN-LAST:event_buRadioButtonMouseExited

    private void buRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buRadioButtonActionPerformed
        // TODO add your handling code here:
        if (buRadioButton.isSelected()) {
            lbSearchLabel.setText("JerseyNo.");
            ModelClass.setSearchField("Search by Jersey Number");
        } else {
            lbSearchLabel.setText("Name");
            ModelClass.setSearchField("Search by Name");
        }

    }//GEN-LAST:event_buRadioButtonActionPerformed

    private void tfNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNameKeyPressed
        // TODO add your handling code here:
        tfName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));
    }//GEN-LAST:event_tfNameKeyPressed

    private void tfGPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfGPKeyPressed
        // TODO add your handling code here:
        tfGP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Games Played",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));
    }//GEN-LAST:event_tfGPKeyPressed

    private void tfPPGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPPGKeyPressed
        // TODO add your handling code here:
        tfPPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Points Per Game",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));
    }//GEN-LAST:event_tfPPGKeyPressed

    private void tfRPGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfRPGKeyPressed
        // TODO add your handling code here:
        tfRPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rebounds Per Game",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));
    }//GEN-LAST:event_tfRPGKeyPressed

    private void tfAPGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfAPGKeyPressed
        // TODO add your handling code here:
        tfAPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Assists Per Game",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));
    }//GEN-LAST:event_tfAPGKeyPressed

    private void tfBPGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfBPGKeyPressed
        // TODO add your handling code here:
        tfBPG.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Blocks Per Game",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));
    }//GEN-LAST:event_tfBPGKeyPressed


    private void tfJNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfJNKeyPressed
        // TODO add your handling code here:
        tfJN.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jersey Number",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));
    }//GEN-LAST:event_tfJNKeyPressed

    private void buSuccessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buSuccessButtonActionPerformed
        // TODO add your handling code here:
        // pnAddPanel.setVisible(false);
        pnMainPanel.setVisible(true);
        pnSuccessPanel.setVisible(false);
        setSize(960, 490);
    }//GEN-LAST:event_buSuccessButtonActionPerformed

    private void buResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buResetButtonActionPerformed
        // TODO add your handling code here:
        ArrayList<ModelClass> unsortedTable = new ArrayList<>();
        unsortedTable.addAll(arLeballs);
        DefaultTableModel model = (DefaultTableModel) tbMainTable.getModel();
        model.setRowCount(0);

        for (ModelClass sortedModel : unsortedTable) {
            Object[] rowData = {
                sortedModel.getJerseyNumber(),
                sortedModel.getName(),
                sortedModel.getGamesPlayed(),
                sortedModel.getPointsPerGame(),
                sortedModel.getReboundsPerGame(),
                sortedModel.getAssistsPerGame(),
                sortedModel.getBlocksPerGame(), // Add other columns as needed
            };
            model.addRow(rowData);
        }

        tbMainTable.repaint();
    }//GEN-LAST:event_buResetButtonActionPerformed

    private void tfRPG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfRPG1KeyPressed
        // TODO add your handling code here:
        tfRPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rebounds Per Game",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));
    }//GEN-LAST:event_tfRPG1KeyPressed

    private void tfGP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfGP1KeyPressed
        // TODO add your handling code here:
        tfGP1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Games Played",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));
    }//GEN-LAST:event_tfGP1KeyPressed

    private void tfAPG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfAPG1KeyPressed
        // TODO add your handling code here:
        tfAPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Assists Per Game",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));
    }//GEN-LAST:event_tfAPG1KeyPressed

    private void tfName1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfName1KeyPressed
        // TODO add your handling code here:
        tfName1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));
    }//GEN-LAST:event_tfName1KeyPressed

    private void tfBPG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfBPG1KeyPressed
        // TODO add your handling code here:
        tfBPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Blocks Per Game",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));

    }//GEN-LAST:event_tfBPG1KeyPressed

    private void tfPPG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPPG1KeyPressed
        // TODO add your handling code here:
        tfPPG1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Points Per Game",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(228, 253, 225)));
    }//GEN-LAST:event_tfPPG1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LeBalls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LeBalls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LeBalls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LeBalls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new LeBalls().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bnBackButtonA1;
    private javax.swing.JButton bnBackButtonAdd;
    private javax.swing.JButton buAddButton;
    private javax.swing.JButton buAddInTable;
    private javax.swing.JButton buDeleteButton;
    private javax.swing.JRadioButton buRadioButton;
    private javax.swing.JButton buResetButton;
    private javax.swing.JButton buSearchButton;
    private javax.swing.JButton buSuccessButton;
    private javax.swing.JButton buUpdateButton;
    private javax.swing.JButton buUpdateInTable;
    private javax.swing.JComboBox<String> cbSortComboBox;
    private javax.swing.JLabel lbAddGIF;
    private javax.swing.JLabel lbAddGIF1;
    private javax.swing.JLabel lbLogo;
    private javax.swing.JLabel lbLogo1;
    private javax.swing.JLabel lbSearchLabel;
    private javax.swing.JLabel lbSortLabel;
    private javax.swing.JLabel lbSuccess;
    private javax.swing.JLabel lbSuccessGIF;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JPanel pnAddPanel;
    private javax.swing.JPanel pnMainPanel;
    private javax.swing.JScrollPane pnScrollPane;
    private javax.swing.JPanel pnSuccessPanel;
    private javax.swing.JPanel pnTitlePanel;
    private javax.swing.JPanel pnUpdatePanel;
    private javax.swing.JTable tbMainTable;
    private javax.swing.JTextField tfAPG;
    private javax.swing.JTextField tfAPG1;
    private javax.swing.JTextField tfBPG;
    private javax.swing.JTextField tfBPG1;
    private javax.swing.JTextField tfGP;
    private javax.swing.JTextField tfGP1;
    private javax.swing.JTextField tfJN;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfName1;
    private javax.swing.JTextField tfPPG;
    private javax.swing.JTextField tfPPG1;
    private javax.swing.JTextField tfRPG;
    private javax.swing.JTextField tfRPG1;
    private javax.swing.JTextField tfSearchField;
    // End of variables declaration//GEN-END:variables
}
