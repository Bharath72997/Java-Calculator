import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Stack;

public class Calculator extends JFrame implements ActionListener {

    // Components of the Calculator
    private JTextField display;
    private JButton[] numberButtons = new JButton[10];
    private JButton[] functionButtons = new JButton[10];
    private JButton addButton, subButton, mulButton, divButton;
    private JButton decButton, equButton, delButton, clrButton, sqrtButton, powButton, historyButton;
    private JPanel panel;
    private JTextArea historyArea; // To display calculation history

    // Stores numbers, operator and history
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;
    private Stack<String> history = new Stack<>();

    public Calculator() {
        // Frame setup
        setTitle("Calculator");
        setSize(400, 700);  // Increased size for History
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Display field
        display = new JTextField();
        display.setBounds(50, 25, 300, 50);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setEditable(false);
        add(display);

        // Function buttons setup
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Del");
        clrButton = new JButton("Clr");
        sqrtButton = new JButton("√");
        powButton = new JButton("^");
        historyButton = new JButton("History");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;
        functionButtons[8] = sqrtButton;
        functionButtons[9] = powButton;

        // Adding action listeners to function buttons
        for (int i = 0; i < 10; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            functionButtons[i].setFocusable(false);
        }

        // Number buttons setup
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            numberButtons[i].setFocusable(false);
        }

        // Panel for buttons
        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(5, 4, 10, 10));

        // Adding buttons to panel
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);
        panel.add(sqrtButton);
        panel.add(powButton);

        add(panel);

        // History button
        historyButton.setBounds(50, 430, 145, 50);
        historyButton.setFont(new Font("Arial", Font.PLAIN, 18));
        historyButton.addActionListener(this);
        add(historyButton);

        // History display area
        historyArea = new JTextArea();
        historyArea.setBounds(50, 490, 300, 150);
        historyArea.setFont(new Font("Arial", Font.PLAIN, 18));
        historyArea.setEditable(false);
        add(historyArea);

        // Position for delete and clear buttons
        delButton.setBounds(50, 600, 145, 50);
        clrButton.setBounds(205, 600, 145, 50);
        add(delButton);
        add(clrButton);

        // Show the frame
        setVisible(true);

        // Keyboard support
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                if (key >= '0' && key <= '9') {
                    display.setText(display.getText() + key);
                } else if (key == '+') {
                    addButton.doClick();
                } else if (key == '-') {
                    subButton.doClick();
                } else if (key == '*') {
                    mulButton.doClick();
                } else if (key == '/') {
                    divButton.doClick();
                } else if (key == '=') {
                    equButton.doClick();
                } else if (key == '.') {
                    decButton.doClick();
                }
            }
        });
        setFocusable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle number button clicks
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                display.setText(display.getText().concat(String.valueOf(i)));
            }
        }

        // Handle decimal point
        if (e.getSource() == decButton) {
            display.setText(display.getText().concat("."));
        }

        // Handle function buttons (+, -, *, /)
        if (e.getSource() == addButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '+';
            display.setText("");
        }
        if (e.getSource() == subButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '-';
            display.setText("");
        }
        if (e.getSource() == mulButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '*';
            display.setText("");
        }
        if (e.getSource() == divButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '/';
            display.setText("");
        }

        // Handle square root
        if (e.getSource() == sqrtButton) {
            num1 = Double.parseDouble(display.getText());
            result = Math.sqrt(num1);
            display.setText(String.valueOf(result));
            history.push(display.getText());
        }

        // Handle power operation
        if (e.getSource() == powButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '^';
            display.setText("");
        }

        // Handle equals button
        if (e.getSource() == equButton) {
            try {
                num2 = Double.parseDouble(display.getText());

                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '*':
                        result = num1 * num2;
                        break;
                    case '/':
                        if (num2 == 0) {
                            display.setText("Error");
                            return;
                        }
                        result = num1 / num2;
                        break;
                    case '^':
                        result = Math.pow(num1, num2);
                        break;
                    default:
                        return;
                }
                display.setText(String.valueOf(result));
                history.push(display.getText());
                num1 = result;
            } catch (NumberFormatException ex) {
                display.setText("Error");
            }
        }

        // Handle clear button
        if (e.getSource() == clrButton) {
            display.setText("");
        }

        // Handle delete button
        if (e.getSource() == delButton) {
            String string = display.getText();
            display.setText("");
            for (int i = 0; i < string.length() - 1; i++) {
                display.setText(display.getText() + string.charAt(i));
            }
        }

        // Handle history button
        if (e.getSource() == historyButton) {
            // Display history in historyArea
            StringBuilder historyText = new StringBuilder();
            for (String entry : history) {
                historyText.insert(0, entry + "\n"); // Display from latest to oldest
            }
            historyArea.setText(historyText.toString());
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
