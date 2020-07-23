import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class myEncryptTools extends JFrame{

    JLabel filePathLabel = null;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        new myEncryptTools();
    }

    public myEncryptTools() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        setTitle("myEncryptTools");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel jp=new JPanel();
        jp.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5));

        this.filePathLabel=new JLabel("File Path:");
        jp.add(filePathLabel);

        JLabel title1 = new JLabel("Input Password:");
        jp.add(title1);

        JButton selectFileButton = new JButton("SelectFile");
        JButton encryptButton = new JButton("EncryptFile");
        JButton decryptButton = new JButton("DecryptFile");

        selectFileButton.addActionListener(new selectFileAction());
        encryptButton.addActionListener(new encryptFile());

        jp.add(selectFileButton);
        jp.add(encryptButton);
        jp.add(decryptButton);

        this.add(jp);
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }

    class selectFileAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser thisFileChooser = new fileChooser();
            Thread thread = new Thread(thisFileChooser);
            thread.start();
        }

        class fileChooser implements Runnable {
            @Override
            public void run() {
                JFileChooser jFileChoose = new JFileChooser("C:\\");
                int fileChooseResult = jFileChoose.showOpenDialog(null);
                if(fileChooseResult == JFileChooser.APPROVE_OPTION) {
                    String fileName = jFileChoose.getSelectedFile().toString();
                    filePathLabel.setText(fileName);
                }
            }
        }
    }

    class encryptFile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String Key = "123456";
            StringBuilder hexString = new StringBuilder();
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(Key.getBytes());
                byte[] hash = md.digest();
                for (int i = 0; i < hash.length; i++) {
                    if ((0xff & hash[i]) < 0x10) {
                        hexString.append("0").append(Integer.toHexString((0xFF & hash[i])));
                    } else {
                        hexString.append(Integer.toHexString(0xFF & hash[i]));
                    }
                }
                System.out.println(hexString.toString());
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                noSuchAlgorithmException.printStackTrace();
            }
        }
    }
}