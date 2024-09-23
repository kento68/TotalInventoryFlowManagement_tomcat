package servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import dao.MasteritemtagDAO;
import model.Masteritemtag;
import model.User;

@WebServlet("/MasteritemtagQRcode")
public class MasteritemtagQRcode extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private BufferedImage img;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	MasteritemtagDAO dao = new MasteritemtagDAO();
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (action != null && action.equals("qrcode")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Masteritemtag ｍasteritemtag = dao.qrCreate(id);
            if (ｍasteritemtag != null) {
                createLabelImage(response, ｍasteritemtag);
      
                // 印刷するかどうかの確認ダイアログを表示
                int printResponse = JOptionPane.showConfirmDialog(
                        null,
                        "印刷しますか？",
                        "印刷確認",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                // ユーザーが印刷を選択した場合の処理
                if (printResponse == JOptionPane.YES_OPTION) {
                    printDocument();
                } else {
                    System.out.println("印刷はキャンセルされました。");
                }

                return;
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Inventory not found");
                return;
            }
        }

        List<Masteritemtag> list = dao.findAll();
        request.setAttribute("list", list);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/masteritemtaglist.jsp");
        rd.forward(request, response);
    }

    private String createLabelImage(HttpServletResponse response, Masteritemtag masteritemtag) throws IOException {
    	
        int pattern = Integer.parseInt(masteritemtag.getPattern());

         // カテゴリを決定
        String FormatPattern = null;
        FormatPattern = "Format Pattern_" + pattern + ".png";

        BufferedImage bufferedImage = ImageIO.read(new File("/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Export_Files/Picture/MasterItemtag/Format Pattern/" + FormatPattern));
    	 
        //<!-- UM425QA-KIR915W -->
        //<!-- DESKTOP-KBUH9GC -->
	    //<!-- BufferedImage bufferedImage = ImageIO.read(new File("E:\\Program Files/eclipse/workspace/totalInventoryFlowManagement/Picture/MasterItemtag/Format Pattern/" + FormatPattern)); -->

  		//<!-- Raspberry Pi(192.168.10.103 ) -->
	    //<!-- Raspberry Pi(192.168.10.122 ) -->
  		//<!-- Raspberry Pi(192.168.10.118 ) -->
	    //<!--BufferedImage bufferedImage = ImageIO.read(new File("/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Export_Files/Picture/MasterItemtag/Format Pattern/" + FormatPattern)); -->
        
    	 Graphics2D g2d = bufferedImage.createGraphics();
    	 Graphics2D g3d = bufferedImage.createGraphics();
    	 
    	 //ﾌｫｰﾏｯﾄﾊﾟﾀｰﾝによって文字の色を変更
    	 if ((pattern >= 1 && pattern <= 19) || (pattern >= 40 && pattern <= 49)|| (pattern >= 60 && pattern <= 69)|| (pattern >= 80 && pattern <= 89)) {
    		 g2d.setColor(Color.BLACK);
    		 g3d.setColor(Color.BLACK);
    	 } else {
    		 g2d.setColor(Color.WHITE);
    		 g3d.setColor(Color.WHITE);
    	}
    	 
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        g3d.setFont(new Font("Serif", Font.BOLD, 30));
        
        //品番----------------------------------------------------------------------------------------------------------
        int xPosition1 = 20;  // 初期X座標
        int yPosition1 = 100;  // 初期Y座標

        String[] productNumbers1 = {
        	//品番
        	masteritemtag.getProductname_asplus(),
        	//品番カラー
        	masteritemtag.getProductnumber_Color()
        };
        // すべての品番を順に描画
        for (String productNumber1 : productNumbers1) {
            if (productNumber1 != null) {
            	// 特定のフィールドのフォントサイズを変更
                if ((productNumber1.equals(masteritemtag.getProductnumber_Color())))
                    g3d.setFont(new Font("Serif", Font.BOLD, 45));  // サイズを大きくする
                } else {
                    g3d.setFont(new Font("Serif", Font.BOLD, 30));  // 通常サイズに戻す
                }

            	g3d.drawString(productNumber1, xPosition1, yPosition1);
                int stringWidth1 = g3d.getFontMetrics().stringWidth(productNumber1);
                xPosition1 += stringWidth1 + 30; // 文字列の幅と少しのスペースを加算
            }
        //--------------------------------------------------------------------------------------------------------------
        //品名----------------------------------------------------------------------------------------------------------
        int xPosition2 = 20;  // 初期X座標
        int yPosition2 = 175;  // 初期Y座標

        String[] productNumbers2 = {
        	//品名
            masteritemtag.getProductnumber_asplussub1(),
            masteritemtag.getProductnumber_asplus_emphasissub1(),
            masteritemtag.getProductnumber_asplus2sub(),
            masteritemtag.getProductnumber_asplus_emphasis2sub(),
            masteritemtag.getProductnumber_asplus3sub(),
            masteritemtag.getProductnumber_asplus_emphasis3sub(),
            masteritemtag.getProductnumber_asplus4sub(),
            masteritemtag.getProductnumber_asplus5sub(),
            masteritemtag.getProductnumber_asplus6sub(),
            masteritemtag.getProductnumber_asplus7sub(),
            masteritemtag.getProductnumber_asplus8sub(),
            masteritemtag.getProductnumber_asplus9sub(),
             //品名カラー
            masteritemtag.getProductname_emphasisColor()
        };

     // `Productname_emphasisColor` を取得
        String productname_emphasisColor = masteritemtag.getProductname_emphasisColor();

        // すべての品番を順に描画
        for (String productNumber2 : productNumbers2) {
            if (productNumber2 != null) {
                // 特定のフィールドのフォントサイズを変更
                if ((productNumber2.equals(masteritemtag.getProductnumber_asplus_emphasissub1()) || 
                     productNumber2.equals(masteritemtag.getProductnumber_asplus_emphasis2sub()) || 
                     productNumber2.equals(masteritemtag.getProductnumber_asplus_emphasis3sub()) ||
                     productNumber2.equals(productname_emphasisColor)) && 
                    (productname_emphasisColor != null && !productname_emphasisColor.equals("-"))) 
                {
                    g3d.setFont(new Font("Serif", Font.BOLD, 45));  // サイズを大きくする
                } else {
                    g3d.setFont(new Font("Serif", Font.BOLD, 30));  // 通常サイズに戻す
                }

                g3d.drawString(productNumber2, xPosition2, yPosition2);
                int stringWidth2 = g3d.getFontMetrics().stringWidth(productNumber2);
                xPosition2 += stringWidth2 + 3; // 文字列の幅と少しのスペースを加算
            }
        }
        //--------------------------------------------------------------------------------------------------------------

   	 	//ﾌｫｰﾏｯﾄﾊﾟﾀｰﾝによって保管場所表示・数量 を変更する。
   	 	if ((pattern >= 1 && pattern <= 79)) {
   	 		g2d.setColor(Color.BLACK);
   	 		g2d.drawString(masteritemtag.getStoragelocation(), 110, 373);
   	        //数量
   	        int quantity = masteritemtag.getQuantity();
   	        g3d.setColor(Color.BLACK);
   	        g3d.drawString(String.valueOf(quantity), 140, 255);
   	        
   	 	} else if(pattern >= 80 && pattern <= 99) {
   	 		g2d.setColor(Color.BLACK);
   	 		g2d.drawString(masteritemtag.getStoragelocation(), 260, 330);
   	 	}
        
        // 在庫管理区分
        int classification;
        try {
            classification = Integer.parseInt(masteritemtag.getInventorymanagementclassification());
        } catch (NumberFormatException e) {
            // 数値に変換できない場合のエラーハンドリング
            classification = -1;
        }
        // カテゴリを決定
        String inventoryManagementClassification;
        if (classification == 9185) {
            inventoryManagementClassification = "材料";
        } else if (classification == 9181) {
            inventoryManagementClassification = "完成品";
        } else if (classification == 9182) {
            inventoryManagementClassification = "仕掛品";
        } else if (classification == 9184) {
            inventoryManagementClassification = "部品";
        } else if (classification == 8888) {
            inventoryManagementClassification = "資材";
        } else if (classification == 4139) {
            inventoryManagementClassification = "試作材料";
        } else if (classification == 9999) {
            inventoryManagementClassification = "営業";
        } else {
            inventoryManagementClassification = "????";
        }
        g3d.setColor(Color.BLACK);
        g2d.drawString(inventoryManagementClassification, 500, 30);
      
        // 現在の日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateAndTime = sdf.format(new java.util.Date());
        g2d.setColor(Color.BLACK);
        g2d.drawString(currentDateAndTime, 300, 30);

        // QRコードの追加
        try {
            // MasterinventoryオブジェクトからQRコードの内容を取得
            String qrCodeText = masteritemtag.getQrcodeinformation();
            // QRコードの内容がnullまたは空の場合、デフォルトの内容を設定
            if (qrCodeText == null || qrCodeText.isEmpty()) {
                qrCodeText = "デフォルトのQRコード内容";
            }
            
            // QRコードのサイズを設定
            int size = 80;
            
            // QRCodeWriterを使用してQRコードを生成
            BitMatrix bitMatrix = new QRCodeWriter().encode(qrCodeText, BarcodeFormat.QR_CODE, size, size);
            
            // BitMatrixからBufferedImageに変換
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            
            // 生成したQRコード画像を指定位置に描画
       	 	if ((pattern >= 1 && pattern <= 79)) {
       	 		g2d.drawImage(qrImage, 440, 187, size, size, null);
       	        
       	 	} else if(pattern >= 80 && pattern <= 99) {
       	 		g2d.drawImage(qrImage, 440, 200, size, size, null);
       	 	}
            
        } catch (WriterException e) {
            // QRコードの生成時にエラーが発生した場合の処理
            e.printStackTrace();
        }
        
        // 画像を読み込むメソッド
        BufferedImage image = null;
        String imagePathStr = "/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Export_Files/Picture/MasterItemtag/" + masteritemtag.getProductnumber_asplus() + ".jpg";
        
        //<!-- UM425QA-KIR915W -->
        //<!-- DESKTOP-KBUH9GC -->
        //<!-- String imagePathStr = "E:\\Program Files/eclipse/workspace/totalInventoryFlowManagement/Picture/MasterItemtag/" + masteritemtag.getProductnumber_asplus() + ".jpg"; -->

        //<!-- Raspberry Pi(192.168.10.103 ) --> 
        //<!-- Raspberry Pi(192.168.10.122 ) -->
        //<!-- Raspberry Pi(192.168.10.118 ) -->
        //<!-- String imagePathStr = "/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Export_Files/Picture/MasterItemtag/" + masteritemtag.getProductnumber_asplus() + ".jpg"; -->

        File imagePath = new File(imagePathStr);
        
        if (imagePath.exists() && !imagePath.isDirectory()) {
            try {
                // 画像を読み込み
                image = ImageIO.read(imagePath);
                
            } catch (IOException e) {
                e.printStackTrace();  // 画像の読み込みに失敗した場合のエラーハンドリング
            }
        } else {
            // 画像ファイルが存在しない場合、"No Data Found .jpg" を読み込み
            image = loadDefaultImage();
        }
        
   	 	//ﾌｫｰﾏｯﾄﾊﾟﾀｰﾝによって画像ファイル表示位置を変更する。
   	 	if ((pattern >= 1 && pattern <= 79)) {
   	        if (image != null) {
   	            g2d.drawImage(image, 140, 270, 120, 80, null);
   	        }
   	        
   	 	} else if(pattern >= 80 && pattern <= 99) {
   	        if (image != null) {
   	            g2d.drawImage(image, 30, 190, 140, 95, null);
   	        }
   	 	}
        
        g2d.dispose();
        g3d.dispose();

        response.setContentType("image/png");
        ImageIO.write(bufferedImage, "png", response.getOutputStream());
		return currentDateAndTime;
    }
    
    private BufferedImage generateQRCodeImage(String barcodeText) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 150, 150);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
    


	// デフォルト画像（"No Data Found .jpg"）を読み込むメソッド
    private BufferedImage loadDefaultImage() {
        BufferedImage image = null;
        String defaultImagePathStr = "/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Export_Files/Picture/MasterItemtag/No Data Found .jpg";
        
        //<!-- UM425QA-KIR915W --> 
        //<!-- DESKTOP-KBUH9GC -->
        //<!-- String defaultImagePathStr = "E:\\Program Files/eclipse/workspace/totalInventoryFlowManagement/Picture/MasterItemtag/No Data Found .jpg"; -->

        //<!-- Raspberry Pi(192.168.10.103 ) -->  
        //<!-- Raspberry Pi(192.168.10.122 ) -->	
        //<!-- Raspberry Pi(192.168.10.118 ) -->
        //<!-- String defaultImagePathStr = "/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Export_Files/Picture/MasterItemtag/No Data Found .jpg"; -->
        
        File defaultImagePath = new File(defaultImagePathStr);
            
        try {
            image = ImageIO.read(defaultImagePath);
        } catch (IOException e) {
            e.printStackTrace(); // デフォルト画像の読み込みに失敗した場合のエラーハンドリング
        }
        return image;
    }
    
    private static void printDocument() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(new PrintableExample());

        try {
            // 印刷ダイアログを表示
            if (printerJob.printDialog()) {
                printerJob.print();
            } else {
                System.out.println("印刷がキャンセルされました。");
            }
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

    static class PrintableExample implements Printable {
        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if (pageIndex != 0) {
                return NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            // 印刷する内容を描画
            g2d.drawString("印刷する内容がここに表示されます。", 100, 100);

            return PAGE_EXISTS;
        }
    }
}