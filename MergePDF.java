import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.filechooser.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
/**
 * <p>Title: �б��</p>
 * <p>Description: ͨ����������Ԫ�غ͵����ɾ������ťɾ���б�Ԫ��</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Filename: MergePDF.java</p>
 * @author �Ž�
 * @version 1.0
 */
public class MergePDF extends JPanel
                      implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String hireString = "���";
    private static final String fireString = "ɾ��";
    private JButton delButton;
    private JButton addButton;
    private JButton clearButton;
    private JButton upButton;
    private JButton downButton;
    private JLabel  targetLabel;
    private JButton browserButton;
    private JButton startButton;
    private JLabel  emptyLabel;
    private JTextField targetFileName;
    //private Container c;

    public MergePDF() {
        super(new BorderLayout());
        //c = getContentPane();
        //����List���б�Ԫ��
        listModel = new DefaultListModel();
        /*
        listModel.addElement("Alan Sommerer");
        listModel.addElement("Alison Huml");
        listModel.addElement("Kathy Walrath");
        listModel.addElement("Lisa Friendly");
        listModel.addElement("Mary Campione");
        listModel.addElement("Sharon Zakhour");
        */

        //����һ��List����,�����б�Ԫ����ӵ��б���
        list = new JList(listModel);
        //����ѡ��ģʽΪ��ѡ
        //list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //��ʼ��ѡ��������0��λ�ã�����һ��Ԫ��
        //list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        //�����б����ͬʱ��5��Ԫ��
        list.setVisibleRowCount(5);
        //���б����һ��������
        JScrollPane listScrollPane = new JScrollPane(list);

        addButton = new JButton("���");
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand("���");
        addButton.addActionListener(addListener);
        //addButton.setEnabled(false);

        delButton = new JButton("ɾ��");
        delButton.setActionCommand("ɾ��");
        delButton.addActionListener(new DelListener());
        delButton.setEnabled(false);
        
        clearButton = new JButton("���");
        clearButton.setActionCommand("���");
        clearButton.addActionListener(new ClearListener());
        clearButton.setEnabled(false);
        
        upButton = new JButton("����");
        upButton.setActionCommand("����");
        upButton.addActionListener(new UpListener());
        
        downButton = new JButton("����");
        downButton.setActionCommand("����");
        downButton.addActionListener(new DownListener());
        
        emptyLabel = new JLabel("   ");
        
        targetLabel = new JLabel("Ŀ���ļ�:");

        targetFileName = new JTextField(10);
        //targetFileName.addActionListener(addListener);
        //targetFileName.getDocument().addDocumentListener(addListener);
        //String name = listModel.getElementAt(list.getSelectedIndex()).toString();

        browserButton = new JButton("���");
        browserButton.setActionCommand("���");
        browserButton.addActionListener(new BrowserListener());
        
        startButton = new JButton("��ʼ");
        startButton.setActionCommand("��ʼ");
        startButton.addActionListener(new StartListener());
        
        //����һ�����
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));//LINE_AXIS));
        //Box hBox1 = Box.createHorizontalBox();
        JPanel hBox1 = new JPanel();
        hBox1.setLayout(new BoxLayout(hBox1,BoxLayout.LINE_AXIS));
        hBox1.add(delButton);
        //delButton.setBorder(new EmptyBorder(0,0,0,5));
        hBox1.add(clearButton);
        //hBox1.add(emptyLabel);
        hBox1.add(new JSeparator(SwingConstants.VERTICAL));
        //hBox1.add(Box.createHorizontalStrut(5));
        //hBox1.add(new JSeparator(SwingConstants.VERTICAL));
        hBox1.add(emptyLabel);
        hBox1.add(addButton);
        //hBox1.add(new JSeparator(SwingConstants.VERTICAL));
        hBox1.add(upButton);
        hBox1.add(downButton);
        hBox1.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        //Box hBox2 = Box.createHorizontalBox();
        JPanel hBox2 = new JPanel();
        hBox2.setLayout(new BoxLayout(hBox2,BoxLayout.LINE_AXIS));
        hBox2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        hBox2.add(targetLabel);
        //hBox2.add(new JSeparator(SwingConstants.VERTICAL));
        //hBox2.add(Box.createHorizontalStrut(5));
        hBox2.add(targetFileName);
        hBox2.add(browserButton);        
        hBox2.add(startButton);        
        
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        buttonPane.add(hBox1);//, BorderLayout.NORTH);
        buttonPane.add(hBox2);

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }
/**
 *<br>��˵��������ӡ���ť����
 *<br>�����������������ӡ���ť��ʵ�ֽ�Ԫ����ӵ��б����
 */
    class DelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
          int[] indexs = list.getSelectedIndices();
          if(indexs == null || indexs.length <=0){
            JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ������");
            return;
          }
          int i=indexs.length -1;
          for(;i>=0;i--){
            listModel.remove(indexs[i]);
          }
          /*
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
           */
        }
    }
/**
 *<br>��˵��������ա���ť����
 *<br>�����������������ա���ť�󣬽��б������������ɾ��
 */
    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
          listModel.clear();
          clearButton.setEnabled(false);
        }
    }
/**
 *<br>��˵���������ơ���ť����
 *<br>������������������ơ���ť��ʵ�ֽ�Ԫ����ӵ��б����
 */
    class UpListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           
          int[] indexs = list.getSelectedIndices();
          if(indexs == null || indexs.length <=0){
            JOptionPane.showMessageDialog(null, "��ѡ��Ҫ���Ƶ���");
            return;
          }
          if(indexs[0] == 0){
            JOptionPane.showMessageDialog(null, "�Ѿ�����������");
            return;
          }
          
          String allItems = "";
          int j=0;
          for(int i=1;i<listModel.getSize();i++){
            //allItems += listModel.getElementAt(i) + ",";
            if(j<indexs.length && i==indexs[j]){
              String str = listModel.getElementAt(i-1).toString();
              listModel.setElementAt(listModel.getElementAt(i), i-1);
              listModel.setElementAt(str, i);
              indexs[j] = i-1;
              j++;
            }
          }
          list.setSelectedIndices(indexs);     
          list.ensureIndexIsVisible(indexs[0]);     
        }
    }
/**
 *<br>��˵���������ơ���ť����
 *<br>������������������ơ���ť��ʵ�ֽ�Ԫ����ӵ��б����
 */
    class DownListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           
          int[] indexs = list.getSelectedIndices();
          if(indexs == null || indexs.length <=0){
            JOptionPane.showMessageDialog(null, "��ѡ��Ҫ���Ƶ���");
            return;
          }
          if(indexs[indexs.length-1] == listModel.getSize()-1){
            JOptionPane.showMessageDialog(null, "�Ѿ�����������");
            return;
          }
          
          String allItems = "";
          int j=indexs.length-1;
          for(int i=listModel.getSize()-2;i>=0;i--){
            //allItems += listModel.getElementAt(i) + ",";
            if(j>=0 && i==indexs[j]){
              String str = listModel.getElementAt(i+1).toString();
              listModel.setElementAt(listModel.getElementAt(i), i+1);
              listModel.setElementAt(str, i);
              indexs[j] = i+1;
              j--;
            }
          }
          list.setSelectedIndices(indexs);     
          list.ensureIndexIsVisible(indexs[indexs.length-1]);  
        }
    }
/**
 *<br>��˵�������������ť����
 *<br>����������������������ť��ʵ�ֽ�Ԫ����ӵ��б����
 */
    class BrowserListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           //ѡ���ļ�
            JFileChooser saveChooser=new JFileChooser();
            saveChooser.setDialogTitle("����Ŀ��PDF�ļ�");
            javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter("PDF�ļ�","pdf");
				    saveChooser.setFileFilter(filter);
            saveChooser.setFileSelectionMode(JFileChooser.FILES_ONLY );
            //�÷�������Ϊtrue����ѡ�����ļ�
            saveChooser.setMultiSelectionEnabled(false);
            int returnval=saveChooser.showOpenDialog(null);  
            if(returnval==JFileChooser.APPROVE_OPTION)
            {
                File file=saveChooser.getSelectedFile();
                String str = file.getPath();
                if(!str.toLowerCase().endsWith(".pdf")){
                  str = str + ".pdf";
                }
                targetFileName.setText(str);
            }
        }
    }
/**
 *<br>��˵��������ʼ����ť����
 *<br>�����������������ʼ����ť��ʵ�ֽ�Ԫ����ӵ��б����
 */
    class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String targetFN = targetFileName.getText();
            if(listModel.getSize()<=0 || targetFN == null || "".equals(targetFN.trim())){
              JOptionPane.showMessageDialog(null, "��ȷ��ѡ����Ҫ�ϲ����ļ���Ŀ���ļ�");
              return;
            }
            startButton.setEnabled(false);
            String allFileStr = "";
            for(int i=0;i<listModel.getSize();i++){
              allFileStr += listModel.getElementAt(i) + ";";
            }
            String[] files = allFileStr.split(";");
            try{
              Document document = new Document();
              FileOutputStream fos = new FileOutputStream(targetFN);
              PdfCopy copy = new PdfCopy(document, fos);
              document.open();
              ArrayList<PdfReader> readerList = new ArrayList<PdfReader>();
              int totalPages = 0;
              for (String file : files) {
                PdfReader reader = new PdfReader(file);
                reader.unethicalreading = true;
                readerList.add(reader);
                totalPages += reader.getNumberOfPages();
              }
              for (PdfReader reader : readerList) {
                for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                    copy.addPage(copy.getImportedPage(reader, i));
                }
              }
              document.close();
              JOptionPane.showMessageDialog(null, "�ϲ��ɹ�");
            }catch(Exception e1){
              e1.printStackTrace();
            }finally{
              startButton.setEnabled(true);
            }
        }
    }
/**
 *<br>��˵��������ӡ���ť�����¼�
 *<br>�����������pdf�ļ�
 */
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //����ʵ�� ActionListener.
        public void actionPerformed(ActionEvent e) {
            //ѡ���ļ�
            JFileChooser addChooser=new JFileChooser();
            addChooser.setDialogTitle("ѡ��Ҫ�ϲ�PDF�ļ�");
            javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter("PDF�ļ�","pdf");
				    addChooser.setFileFilter(filter);
            addChooser.setFileSelectionMode(JFileChooser.FILES_ONLY );
            //�÷�������Ϊtrue����ѡ�����ļ�
            addChooser.setMultiSelectionEnabled(true);
            int returnval=addChooser.showOpenDialog(null);  
            if(returnval==JFileChooser.APPROVE_OPTION)
            {
                File[] files=addChooser.getSelectedFiles();
                //String str="";
                int index = list.getSelectedIndex(); //��ȡѡ����
                if (index == -1) { //���û��ѡ�񣬾Ͳ��뵽��һ��
                    index = 0;
                } else {           //�����ѡ����ô���뵽ѡ����ĺ���
                    index++;
                }
                for (File file : files) {
                  /*
                    af.add(file);
                    if(file.isDirectory())
                        str=file.getPath();
                    else{
                      str=file.getPath()+file.getName();
                    }
                    jta.append(str+"\n");
                    */
                  if(alreadyInList(file.getName())){
                    continue;
                  }
                  listModel.insertElementAt(file.getPath(), index);
                  index++;
                  if(clearButton.isEnabled() == false){
                    clearButton.setEnabled(true);  
                  }
                }

            }
            /*
            String name = targetFileName.getText();

            //�������ջ���ͬ��
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                targetFileName.requestFocusInWindow();
                targetFileName.selectAll();
                return;
            }*/

            

            //listModel.insertElementAt(targetFileName.getText(), index);
 
            //���������ı�
            //targetFileName.requestFocusInWindow();
            //targetFileName.setText("");

            //ѡ���µ�Ԫ�أ�����ʾ����
            //list.setSelectedIndex(index);
            //list.ensureIndexIsVisible(index);
        }
/**
 *<br>����˵��������Ƿ���LIST��������Ԫ��
 *<br>���������String name ��������
 *<br>�������ͣ�boolean ����ֵ��������ڷ���true
 */

        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

/**
 *<br>����˵����ʵ��DocumentListener�ӿڣ�����ʵ�ֵķ���
 *<br>���������
 *<br>�������ͣ�
 */
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

/**
 *<br>����˵����ʵ��DocumentListener�ӿڣ�����ʵ�ֵķ���
 *<br>���������
 *<br>�������ͣ�
 */
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

/**
 *<br>����˵����ʵ��DocumentListener�ӿڣ�����ʵ�ֵķ���
 *<br>���������
 *<br>�������ͣ�
 */
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }
/**
 *<br>����˵������ťʹ��
 *<br>���������
 *<br>�������ͣ�
 */
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }
/**
 *<br>����˵����ʵ��DocumentListener�ӿڣ�����ʵ�ֵķ������޸İ�ť��״̬
 *<br>���������
 *<br>�������ͣ�
 */
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }
/**
 *<br>����˵����ʵ��ListSelectionListener�ӿڣ�����ʵ�ֵķ���
 *<br>���������
 *<br>�������ͣ�
 */
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                delButton.setEnabled(false);

            } else {
                delButton.setEnabled(true);
            }
        }
    }
/**
 *<br>����˵����������
 *<br>���������
 *<br>�������ͣ�
 */
    public static void main(String[] args) {
        //String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try{
          UIManager.setLookAndFeel(lookAndFeel);
        }catch(Exception e){
          e.printStackTrace();
        }
        JFrame.setDefaultLookAndFeelDecorated(false);

        //����һ������
        JFrame frame = new JFrame("�ϲ�PDF");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 480));
        // 1����ȡ����Ŀ�͸�
        int widthFrame = 600;//jFrame.getWidth();
        int heightFrame = 480;//jFrame.getHeight();
        // 2����ȡ��Ļ�Ŀ�͸�
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = defaultToolkit.getScreenSize();
        double widthScreen = screenSize.getWidth();
        double heightScreen = screenSize.getHeight();
        // 3���������ĳߴ糬���ˣ���ֱ������Ļ�ĳߴ�
        if (widthFrame > widthScreen){
            widthFrame = (int)widthScreen;
        }
        if (heightFrame > heightScreen){
            heightFrame = (int)heightScreen;
        }
        // 4������λ��
        int positionX = (int) ((widthScreen - widthFrame)/2);
        int positionY = (int)((heightScreen - heightFrame)/2);

        frame.setSize(new Dimension(widthFrame,heightFrame));
        frame.setLocation(new Point(positionX,positionY));
        //����һ�����
        JComponent newContentPane = new MergePDF();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        
        //��ʾ����
        frame.pack();
        frame.setVisible(true);
    }
}
