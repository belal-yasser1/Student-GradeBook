import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;


public class Gradebook extends JFrame{
    JTextField NameField,GradeField,IDField;
    JComboBox<String> Subjects;
    JButton AddButton , ClearButton,DeleteButton;
    JTable Table;
    DefaultTableModel TableModel;
    JLabel ResultLabel;
    

    Gradebook(){
//..............frame................//   
    setTitle("GRADE BOOK");
   setSize(800,500);
   setResizable(false);
   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   setLocationRelativeTo(null);
   setLayout(new BorderLayout(10,10));


//..............top panel..............//
   JPanel TopPanel = new JPanel(new FlowLayout());
   NameField = new JTextField(10);
   GradeField = new JTextField(6);
   IDField = new JTextField(10);
   Subjects = new JComboBox<>(new String[]{
    "Math 3",
   "Creative and scientific ",
   "DBMS 1",
   "Computer Security",
   "Computer Archeticture",
      });


AddButton = new JButton("Add");
ClearButton = new JButton("Clear");
DeleteButton = new JButton("Delete");

TopPanel.add(new JLabel("Name:"));
TopPanel.add(NameField);

TopPanel.add(new JLabel("ID:"));
TopPanel.add(IDField);

TopPanel.add(Subjects);

TopPanel.add(new JLabel("Grade:"));
TopPanel.add(GradeField);

TopPanel.add(AddButton);
TopPanel.add(DeleteButton);
TopPanel.add(ClearButton);

//.............center panel............//
String Columns[] = {"Name","ID","Subject","Grade","Letter"};
 TableModel = new DefaultTableModel(Columns,0);
Table = new JTable(TableModel);
JScrollPane scrollPane = new JScrollPane(Table);

//............south panel.............//
ResultLabel = new JLabel("Average: --   |   Letter: --");
ResultLabel.setHorizontalAlignment(SwingConstants.CENTER);

add(TopPanel,BorderLayout.NORTH);
add(scrollPane,BorderLayout.CENTER);
add(ResultLabel,BorderLayout.SOUTH);

//.............actions performed.............//

//..............add button....................//
AddButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e){
        GetGrade();
    }
});

//............clear button...................//
ClearButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e){
        TableModel.setRowCount(0);
        ResultLabel.setText("Average:-- | Letter:--");
    }
});

//...........delete button..................//
DeleteButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e){
    int SelectedRow = Table.getSelectedRow();
    if(SelectedRow == -1){
        JOptionPane.showMessageDialog(null, "please select a row first before deleting");
        return;
    }
    TableModel.removeRow(SelectedRow);
    if(TableModel.getRowCount()==0){
        ResultLabel.setText("Average:-- | Letter:--");
    }else{
        Average();
    }
}    
});

setVisible(true);
    }
void GetGrade(){
    //initializing all the variables in the table//
    String name = NameField.getText().trim();
    String ID = IDField.getText().trim();
    String Grade = GradeField.getText().trim();
    String Subject = (String) Subjects.getSelectedItem();
    
    //if statment to solve if one of the fields is empty//
    if(name.isEmpty()||Grade.isEmpty()||ID.isEmpty()){
    JOptionPane.showMessageDialog(this, "u have to fill all fields");
    return;
   }


//try catch statement to solve if the grade is inputted wrong//
int grade;
try {
    grade = Integer.parseInt(Grade);
    if(grade<0||grade>100){
        JOptionPane.showMessageDialog(this, "the grade put must be between 0 and 100");
        return;
    }
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this,"grade inputted must be a number");
   return;
}


//try catch statement to solve if the ID inputted is a string not an integer//
try {
    Integer.parseInt(ID);
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "the ID inputted must be a Number");
    return;
}


//......putting the values inside the table...//
String letter = ToLetter(grade);
TableModel.addRow(new Object[]{name,ID,Subject,grade,letter});
IDField.setText("");
GradeField.setText("");
Average();
}


//......function to change the grade to a letter based on the degree itself...//
String ToLetter(int grade){
if (grade >=90) return "A";
if (grade>=80) return "B";
if (grade>=70) return "C";
if (grade>=60) return "D";
return "F";
}


//.....function to get the average of the grade....//
void Average(){
    int total = 0;
    int count = TableModel.getRowCount();
    for (int i = 0 ; i < count ; i++){
        total += (int) TableModel.getValueAt(i, 3);
    }
    double average = (double) total/count;
    String Letter = ToLetter((int) average);
    ResultLabel.setText(String.format("Average: %.1f | Letter: %s", average,Letter));
}


public static void main(String[] args) {
    //....creating an instance inside the main for the program to work....//
    new Gradebook();
}
}