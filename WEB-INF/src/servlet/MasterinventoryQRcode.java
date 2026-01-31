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
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import dao.MasterinventoryDAO;
import model.Masterinventory;
import model.User;

@WebServlet("/MasterinventoryQRcode")
public class MasterinventoryQRcode extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MasterinventoryDAO dao = new MasterinventoryDAO();
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        try {
            if (action != null && action.equals("qrcode")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Masterinventory masterinventory = dao.qrCreate(id);
                if (masterinventory != null) {
                    createLabelImage(response, masterinventory);

                    // JFrameを作成
                    JFrame jf = new JFrame();
                    jf.setAlwaysOnTop(true);
                    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    jf.setUndecorated(true); // ウィンドウの装飾を削除する場合
                    jf.setSize(0, 0); // サイズをゼロにして非表示にする
                    jf.setLocationRelativeTo(null); // 画面の中央に配置

                    // 印刷するかどうかの確認ダイアログを表示
                    int printResponse = JOptionPane.showConfirmDialog(
                            jf,
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

                    // JFrameを閉じる
                    jf.dispose();
                    return;
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Inventory not found");
                    return;
                }
            }

            // 他の処理が必要であればここに追加

        } catch (NumberFormatException e) {
            // IDの変換に失敗した場合の処理
            request.setAttribute("err", "無効なIDが指定されました。");
            request.setAttribute("list", dao.findAll()); // エラー時でもリストを表示
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/masterinventorylist.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            // エラー発生時の処理
            e.printStackTrace(); // エラーログを出力（デバッグ用）
            request.setAttribute("err", "登録データに不備があります。「更新」ボタンを押して、登録し直してください"); // エラーメッセージを設定
            request.setAttribute("list", dao.findAll()); // エラー時でもリストを表示
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/masterinventorylist.jsp");
            rd.forward(request, response);
        }
        
        List<Masterinventory> list = dao.findAll();
        request.setAttribute("list", list);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/masterinventorylist.jsp");
        rd.forward(request, response);
    }

    private String createLabelImage(HttpServletResponse response, Masterinventory masterinventory) throws IOException {
    	BufferedImage bufferedImage = ImageIO.read(new File("/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Export_Files/Picture/Masterinventory/PhysicalSlipTemplate.png"));
    	 
	        //<!-- UM425QA-KIR915W -->
	        //<!-- DESKTOP-KBUH9GC -->
	        //<!-- BufferedImage bufferedImage = ImageIO.read(new File("E:\\Program Files/eclipse/workspace/totalInventoryFlowManagement/Picture/Masterinventory/PhysicalSlipTemplate.png")); -->

      		//<!-- Raspberry Pi(192.168.10.103 ) -->  
      		//<!-- Raspberry Pi(192.168.10.122 ) -->
      		//<!-- Raspberry Pi(192.168.10.118 ) -->
 	        //<!-- BufferedImage bufferedImage = ImageIO.read(new File("/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Export_Files/Picture/Masterinventory/PhysicalSlipTemplate.png")); -->
	        
    	 Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));

        // テキストの描画位置と内容を調整
        //品名
        g2d.drawString(masterinventory.getProductname_asplus(), 10, 90);
        
        //QRコード名
        String qrcodeInfo = masterinventory.getQrcodeinformation();
        if (qrcodeInfo == null || qrcodeInfo.isEmpty()) {
            return ""; // または null を返すこともできます
        }

        int slashIndex = qrcodeInfo.indexOf('/');

        // 最初の / が見つからなかった場合は、元の文字列を返す
        if (slashIndex == -1) {
            return qrcodeInfo; // または null や空文字を返すこともできます
        }

        // 最初の / 以降の文字列を抜き取る
        String result = qrcodeInfo.substring(slashIndex + 1);
        g2d.drawString(result, 10, 150);
        
        //保管場所
        g2d.drawString(masterinventory.getStoragelocation(), 160, 330);
        //会社名
        g2d.drawString("角一化成 株式会社", 350, 330);
        
        // 在庫管理区分
        int classification;
        try {
            classification = Integer.parseInt(masterinventory.getInventorymanagementclassification());
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
        g2d.drawString(inventoryManagementClassification, 520, 30);
      
        // 現在の日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateAndTime = sdf.format(new java.util.Date());
        g2d.drawString(currentDateAndTime, 300, 30);

        // QRコードの追加
        try {
            // MasterinventoryオブジェクトからQRコードの内容を取得
            String qrCodeText = masterinventory.getQrcodeinformation();
            // QRコードの内容がnullまたは空の場合、デフォルトの内容を設定
            if (qrCodeText == null || qrCodeText.isEmpty()) {
                qrCodeText = "デフォルトのQRコード内容";
            }
            
            // QRコードのサイズを設定
            int size = 130;
            
            // QRCodeWriterを使用してQRコードを生成
            BitMatrix bitMatrix = new QRCodeWriter().encode(qrCodeText, BarcodeFormat.QR_CODE, size, size);
            
            // BitMatrixからBufferedImageに変換
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            
            // 生成したQRコード画像を指定位置に描画
            g2d.drawImage(qrImage, 430, 170, size, size, null);
        } catch (WriterException e) {
            // QRコードの生成時にエラーが発生した場合の処理
            e.printStackTrace();
        }

        g2d.dispose();

        response.setContentType("image/png");
        ImageIO.write(bufferedImage, "png", response.getOutputStream());
		return currentDateAndTime;
    }
    
    private BufferedImage generateQRCodeImage(String barcodeText) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 150, 150);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
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