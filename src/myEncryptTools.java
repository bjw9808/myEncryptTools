import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class myEncryptTools extends JFrame{

    JLabel filePathLabel = null;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        new myEncryptTools();
    }

    public myEncryptTools() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        setTitle("myEncryptTools");
        setSize(420,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        JPanel jp=new JPanel();
        jp.setLayout(new FlowLayout(FlowLayout.LEADING,20,20));

        this.filePathLabel=new JLabel("File Path:                                                                       ");
        jp.add(filePathLabel);

        JButton selectFileButton = new JButton("select your File");
        JButton encryptButton = new JButton("Encrypt File");
        JButton decryptButton = new JButton("Decrypt File");

        selectFileButton.addActionListener(new selectFileAction());

        jp.add(selectFileButton);
        jp.add(encryptButton);
        jp.add(decryptButton);

        this.add(jp);
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }

    class selectFileAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jFileChoose = new JFileChooser("C:\\");
            int fileChooseResult = jFileChoose.showOpenDialog(null);
            if(fileChooseResult == JFileChooser.APPROVE_OPTION) {
                String fileName = jFileChoose.getSelectedFile().toString();
                filePathLabel.setText(fileName);
            }
        }
    }
}