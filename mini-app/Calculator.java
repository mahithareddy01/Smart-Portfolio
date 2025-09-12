import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String operator = "";
    private double firstNum = 0;

    private JComboBox<String> fromUnit, toUnit;
    private JTextField inputField, outputField;

    public Calculator() {
        setTitle("Calculator + Converter");
        setSize(350, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Calculator", makeCalculator());
        tabs.add("Converter", makeConverter());
        add(tabs);

        setVisible(true);
    }

    private JPanel makeCalculator() {
        JPanel panel = new JPanel(new BorderLayout());
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(display, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(4, 4, 5, 5));
        String[] keys = {"7","8","9","/","4","5","6","*","1","2","3","-","0",".","=","+"};
        for (String k : keys) {
            JButton b = new JButton(k);
            b.addActionListener(this);
            buttons.add(b);
        }
        panel.add(buttons, BorderLayout.CENTER);
        return panel;
    }

    private JPanel makeConverter() {
    JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
    String[] units = {"cm", "m", "km", "inch", "ft"};
    fromUnit = new JComboBox<>(units);
    toUnit = new JComboBox<>(units);
    inputField = new JTextField();
    outputField = new JTextField(); 
    outputField.setEditable(false);
    JButton convert = new JButton("Convert");
    
    convert.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            doConvert();
        }
    });

    panel.add(new JLabel("From:")); panel.add(fromUnit);
    panel.add(new JLabel("To:"));   panel.add(toUnit);
    panel.add(new JLabel("Value:"));panel.add(inputField);
    panel.add(convert);             panel.add(outputField);
    return panel;
}


    @Override
    public void actionPerformed(ActionEvent e) {
        String key = e.getActionCommand();
        if (key.matches("[0-9.]")) {
            display.setText(display.getText() + key);
        } else if (key.matches("[+\\-*/]")) {
            firstNum = Double.parseDouble(display.getText());
            operator = key;
            display.setText("");
        } else if (key.equals("=")) {
            double secondNum = Double.parseDouble(display.getText());
            double result = switch (operator) {
                case "+" -> firstNum + secondNum;
                case "-" -> firstNum - secondNum;
                case "*" -> firstNum * secondNum;
                case "/" -> (secondNum != 0) ? firstNum / secondNum : 0;
                default -> 0;
            };
            display.setText(String.valueOf(result));
        }
    }

    private void doConvert() {
        try {
            double val = Double.parseDouble(inputField.getText());
            String from = (String) fromUnit.getSelectedItem();
            String to = (String) toUnit.getSelectedItem();

            double meters = switch (from) {
                case "cm" -> val / 100;
                case "m"  -> val;
                case "km" -> val * 1000;
                case "inch" -> val * 0.0254;
                case "ft" -> val * 0.3048;
                default -> val;
            };

            double result = switch (to) {
                case "cm" -> meters * 100;
                case "m"  -> meters;
                case "km" -> meters / 1000;
                case "inch" -> meters / 0.0254;
                case "ft" -> meters / 0.3048;
                default -> meters;
            };

            outputField.setText(String.valueOf(result));
        } catch (Exception ex) {
            outputField.setText("Invalid input");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}