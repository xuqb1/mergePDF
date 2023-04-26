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
 * <p>Title: 列表框</p>
 * <p>Description: 通过输入框添加元素和点击“删除”按钮删除列表元素</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Filename: MergePDF.java</p>
 * @author 杜江
 * @version 1.0
 */
public class MergePDF extends JPanel
                      implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String hireString = "添加";
    private static final String fireString = "删除";
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
        //构建List的列表元素
        listModel = new DefaultListModel();
        /*
        listModel.addElement("Alan Sommerer");
        listModel.addElement("Alison Huml");
        listModel.addElement("Kathy Walrath");
        listModel.addElement("Lisa Friendly");
        listModel.addElement("Mary Campione");
        listModel.addElement("Sharon Zakhour");
        */

        //创建一个List构件,并将列表元素添加到列表中
        list = new JList(listModel);
        //设置选择模式为单选
        //list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //初始化选择索引在0的位置，即第一个元素
        //list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        //设置列表可以同时看5个元素
        list.setVisibleRowCount(5);
        //给列表添加一个滑动块
        JScrollPane listScrollPane = new JScrollPane(list);

        addButton = new JButton("添加");
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand("添加");
        addButton.addActionListener(addListener);
        //addButton.setEnabled(false);

        delButton = new JButton("删除");
        delButton.setActionCommand("删除");
        delButton.addActionListener(new DelListener());
        delButton.setEnabled(false);
        
        clearButton = new JButton("清空");
        clearButton.setActionCommand("清空");
        clearButton.addActionListener(new ClearListener());
        clearButton.setEnabled(false);
        
        upButton = new JButton("上移");
        upButton.setActionCommand("上移");
        upButton.addActionListener(new UpListener());
        
        downButton = new JButton("下移");
        downButton.setActionCommand("下移");
        downButton.addActionListener(new DownListener());
        
        emptyLabel = new JLabel("   ");
        
        targetLabel = new JLabel("目标文件:");

        targetFileName = new JTextField(10);
        //targetFileName.addActionListener(addListener);
        //targetFileName.getDocument().addDocumentListener(addListener);
        //String name = listModel.getElementAt(list.getSelectedIndex()).toString();

        browserButton = new JButton("浏览");
        browserButton.setActionCommand("浏览");
        browserButton.addActionListener(new BrowserListener());
        
        startButton = new JButton("开始");
        startButton.setActionCommand("开始");
        startButton.addActionListener(new StartListener());
        
        //创建一个面板
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
 *<br>类说明：“添加”按钮监听
 *<br>类描述：当点击“添加”按钮后，实现将元素添加到列表框中
 */
    class DelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
          int[] indexs = list.getSelectedIndices();
          if(indexs == null || indexs.length <=0){
            JOptionPane.showMessageDialog(null, "请选择要删除的项");
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
 *<br>类说明：“清空”按钮监听
 *<br>类描述：当点击“清空”按钮后，将列表框中所有内容删除
 */
    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
          listModel.clear();
          clearButton.setEnabled(false);
        }
    }
/**
 *<br>类说明：“上移”按钮监听
 *<br>类描述：当点击“上移”按钮后，实现将元素添加到列表框中
 */
    class UpListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           
          int[] indexs = list.getSelectedIndices();
          if(indexs == null || indexs.length <=0){
            JOptionPane.showMessageDialog(null, "请选择要上移的项");
            return;
          }
          if(indexs[0] == 0){
            JOptionPane.showMessageDialog(null, "已经在最上面了");
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
 *<br>类说明：“下移”按钮监听
 *<br>类描述：当点击“下移”按钮后，实现将元素添加到列表框中
 */
    class DownListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           
          int[] indexs = list.getSelectedIndices();
          if(indexs == null || indexs.length <=0){
            JOptionPane.showMessageDialog(null, "请选择要下移的项");
            return;
          }
          if(indexs[indexs.length-1] == listModel.getSize()-1){
            JOptionPane.showMessageDialog(null, "已经在最下面了");
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
 *<br>类说明：“浏览”按钮监听
 *<br>类描述：当点击“浏览”按钮后，实现将元素添加到列表框中
 */
    class BrowserListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           //选择文件
            JFileChooser saveChooser=new JFileChooser();
            saveChooser.setDialogTitle("设置目标PDF文件");
            javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter("PDF文件","pdf");
				    saveChooser.setFileFilter(filter);
            saveChooser.setFileSelectionMode(JFileChooser.FILES_ONLY );
            //该方法设置为true允许选择多个文件
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
 *<br>类说明：“开始”按钮监听
 *<br>类描述：当点击“开始”按钮后，实现将元素添加到列表框中
 */
    class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String targetFN = targetFileName.getText();
            if(listModel.getSize()<=0 || targetFN == null || "".equals(targetFN.trim())){
              JOptionPane.showMessageDialog(null, "请确定选择了要合并的文件及目标文件");
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
              JOptionPane.showMessageDialog(null, "合并成功");
            }catch(Exception e1){
              e1.printStackTrace();
            }finally{
              startButton.setEnabled(true);
            }
        }
    }
/**
 *<br>类说明：“添加”按钮监听事件
 *<br>类描述：添加pdf文件
 */
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //必须实现 ActionListener.
        public void actionPerformed(ActionEvent e) {
            //选择文件
            JFileChooser addChooser=new JFileChooser();
            addChooser.setDialogTitle("选择要合并PDF文件");
            javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter("PDF文件","pdf");
				    addChooser.setFileFilter(filter);
            addChooser.setFileSelectionMode(JFileChooser.FILES_ONLY );
            //该方法设置为true允许选择多个文件
            addChooser.setMultiSelectionEnabled(true);
            int returnval=addChooser.showOpenDialog(null);  
            if(returnval==JFileChooser.APPROVE_OPTION)
            {
                File[] files=addChooser.getSelectedFiles();
                //String str="";
                int index = list.getSelectedIndex(); //获取选择项
                if (index == -1) { //如果没有选择，就插入到第一个
                    index = 0;
                } else {           //如果有选择，那么插入到选择项的后面
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

            //如果输入空或有同名
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                targetFileName.requestFocusInWindow();
                targetFileName.selectAll();
                return;
            }*/

            

            //listModel.insertElementAt(targetFileName.getText(), index);
 
            //重新设置文本
            //targetFileName.requestFocusInWindow();
            //targetFileName.setText("");

            //选择新的元素，并显示出来
            //list.setSelectedIndex(index);
            //list.ensureIndexIsVisible(index);
        }
/**
 *<br>方法说明：检测是否在LIST中有重名元素
 *<br>输入参数：String name 检测的名字
 *<br>返回类型：boolean 布尔值，如果存在返回true
 */

        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

/**
 *<br>方法说明：实现DocumentListener接口，必需实现的方法
 *<br>输入参数：
 *<br>返回类型：
 */
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

/**
 *<br>方法说明：实现DocumentListener接口，必需实现的方法
 *<br>输入参数：
 *<br>返回类型：
 */
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

/**
 *<br>方法说明：实现DocumentListener接口，必需实现的方法
 *<br>输入参数：
 *<br>返回类型：
 */
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }
/**
 *<br>方法说明：按钮使能
 *<br>输入参数：
 *<br>返回类型：
 */
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }
/**
 *<br>方法说明：实现DocumentListener接口，必需实现的方法，修改按钮的状态
 *<br>输入参数：
 *<br>返回类型：
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
 *<br>方法说明：实现ListSelectionListener接口，必需实现的方法
 *<br>输入参数：
 *<br>返回类型：
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
 *<br>方法说明：主方法
 *<br>输入参数：
 *<br>返回类型：
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

        //创建一个窗体
        JFrame frame = new JFrame("合并PDF");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 480));
        // 1、获取窗体的宽和高
        int widthFrame = 600;//jFrame.getWidth();
        int heightFrame = 480;//jFrame.getHeight();
        // 2、获取屏幕的宽和高
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = defaultToolkit.getScreenSize();
        double widthScreen = screenSize.getWidth();
        double heightScreen = screenSize.getHeight();
        // 3、如果窗体的尺寸超过了，则直接用屏幕的尺寸
        if (widthFrame > widthScreen){
            widthFrame = (int)widthScreen;
        }
        if (heightFrame > heightScreen){
            heightFrame = (int)heightScreen;
        }
        // 4、设置位置
        int positionX = (int) ((widthScreen - widthFrame)/2);
        int positionY = (int)((heightScreen - heightFrame)/2);

        frame.setSize(new Dimension(widthFrame,heightFrame));
        frame.setLocation(new Point(positionX,positionY));
        //创建一个面版
        JComponent newContentPane = new MergePDF();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        
        //显示窗体
        frame.pack();
        frame.setVisible(true);
    }
}
