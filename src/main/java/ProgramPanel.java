import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Duration;


public class ProgramPanel extends JPanel {
    WebDriver driver;
    private JButton button;
    private JLabel phoneNumber;
    private JTextField phoneText;
    private JLabel startNumber;
    private JLabel messageLabel;
    private JTextField message;
    private JLabel log;
    private JCheckBox login;
    private JCheckBox sent;

    public ProgramPanel(int x, int y, int width, int height) {
        this.setLayout(null);
        this.setBounds(x, y, width, height);
        createUi();
        this.add(button);
        this.add(phoneNumber);
        this.add(startNumber);
        this.add(phoneText);
        this.add(messageLabel);
        this.add(message);
        this.add(log);
        this.add(login);
        this.add(sent);
        phoneFieldLimiter();
        button.addActionListener(e -> {
            try {
                connectToWhatsapp();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void connectToWhatsapp() throws InterruptedException {
        String link = createChatLink(phoneText);
        String messageToSend = message.getText();
        System.setProperty("webdriver.chrome.driver", Constant.CHROME_DRIVER_PATH);


        if (checkPhoneNumber(phoneText) && !message.getText().equals("")) {
            driver = new ChromeDriver();
            driver.get(link);
            driver.manage().window().maximize();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("side")));
            log.setText("Logged in successfully!");
            login.setSelected(true);
            Thread.sleep(2000);
            WebElement chat = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[4]/div/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[2]"));
            chat.sendKeys(messageToSend);
            WebElement send = driver.findElement(By.cssSelector("button[class=\"tvf2evcx oq44ahr5 lb5m6g5c svlsagor p2rjqpw5 epia9gcq\"]"));
            send.click();
            sent.setSelected(true);
            log.setText("Message sent successfully!");
            Thread.sleep(1000);
            driver.close();
        }

        else if (phoneText.getText().equals("") && message.getText().equals(""))
        {
            log.setText("Phone number and message are empty!");
        }
        else if (phoneText.getText().equals("") && !message.getText().equals(""))
        {
            log.setText("Phone number is empty!");
        }
        else if (checkPhoneNumber(phoneText) && message.getText().equals(""))
        {
            log.setText("Message is empty!");
        }
        else if (!checkPhoneNumber(phoneText) && !message.getText().equals(""))
        {
            log.setText("Phone number is incorrect!");
        }
        else if (!checkPhoneNumber(phoneText) && message.getText().equals(""))
        {
            log.setText("Phone number is incorrect and message is empty!");
        }
    }



    public String createChatLink(JTextField phoneNumber){
        String format = "https://web.whatsapp.com/send?phone=972";
        String result = "";
        boolean valid = checkPhoneNumber(phoneText);


        if (valid) {
            String num = phoneNumber.getText();
            result += format;
            result += num;
        }
        return result;
    }

    public boolean checkPhoneNumber(JTextField phoneNum){
        String num = phoneNum.getText();
        boolean length = false;
        boolean valid = false;

        if (num.length() == Constant.PHONE_NUMBER_LENGTH){
            length = true;
        }
        if (length) {
            if (num.charAt(0) == 53) {
                valid = true;
            }
        }

        return length && valid;
    }
    public void phoneFieldLimiter(){
        phoneText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c >= '0' && c <= '9' || c == KeyEvent.VK_BACK_SPACE){
                    phoneText.setEditable(true);
                    log.setText("");
                } else {
                    phoneText.setEditable(false);
                    log.setText("# Enter only numbers between 0-9");
                }
                if(phoneText.getText().length() >= 9){
                    e.consume();
                }
            }
        });
    }

    public void createUi(){
        phoneNumber = new JLabel("Phone Number:");
        phoneNumber.setBounds(50, 100, Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT);

        startNumber = new JLabel("972-");
        startNumber.setBounds(150, 100, Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT);

        phoneText = new JTextField();
        phoneText.setBounds(180, 100, Constant.BUTTON_WIDTH/2, Constant.BUTTON_HEIGHT);

        messageLabel = new JLabel("Message:");
        messageLabel.setBounds(80, 200, Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT);

        message = new JTextField();
        message.setBounds(150, 200, Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT * 2);

        button = new JButton("Open Whatsapp");
        button.setBounds(Constant.WINDOW_WIDTH/4, 350, Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT);

        log = new JLabel("");
        log.setBounds(Constant.WINDOW_WIDTH/3 , 300, Constant.BUTTON_WIDTH*2, Constant.BUTTON_HEIGHT);
        //
        login = new JCheckBox("Login");
        login.setEnabled(false);
        login.setBounds(0, 300, 100, 50);

        sent = new JCheckBox("Sent");
        sent.setEnabled(false);
        sent.setBounds(0, 350, 100, 50);

    }
}