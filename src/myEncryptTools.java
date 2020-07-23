import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

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
            // 暂时写死此Key
            String Key = "1234567890";
            StringBuilder hexString = new StringBuilder();
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(Key.getBytes());
                byte[] hash = md.digest();
                for (byte b : hash) {
                    if ((0xff & b) < 0x10) {
                        hexString.append("0").append(Integer.toHexString((0xFF & b)));
                    } else {
                        hexString.append(Integer.toHexString(0xFF & b));
                    }
                }
                System.out.println(hexString);
                String content = "abc";
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(256, new SecureRandom(hexString.toString().getBytes()));
                SecretKey secretKey = kgen.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] result = cipher.doFinal(byteContent);
                System.out.println(Arrays.toString(result));

                //解密
                kgen = KeyGenerator.getInstance("AES");
                kgen.init(256, new SecureRandom(hexString.toString().getBytes()));
                cipher = Cipher.getInstance("AES");// 创建密码器
                cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
                result = cipher.doFinal(result);
                System.out.println(Arrays.toString(result));

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}