package application.presentation;

import application.bll.DatabaseBLL;
import application.model.BillData;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Class for printing all the database data required for the bill.
 */
public class PrintBill {

    /**
     * Prints all the database order data in pdf files, a file for each order.
     *
     * @throws NoSuchElementException Stating that no orders were found.
     * @throws FileNotFoundException Stating that the file is currently in use or was not created successfully.
     */
    public static void printBill() throws NoSuchElementException, FileNotFoundException {
        DatabaseBLL databaseBLL = new DatabaseBLL();
        List<BillData> billDataList = databaseBLL.findAllBillData();
        if (billDataList.size() == 0) {
            throw new NoSuchElementException("Order table empty");
        }

        for (BillData billData : billDataList) {
            String file = "BILL_#" + billData.getIdOrder() + ".pdf";

            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
            pdfDoc.addNewPage();
            Document doc = new Document(pdfDoc);

            List<Paragraph> paragraphs = new ArrayList<>();


            Paragraph paragraph1 = new Paragraph("Order ID #" + billData.getIdOrder() + ":");
            Paragraph paragraph2 = new Paragraph("Client ID #" + billData.getIdClient() + ":");
            Paragraph paragraph3 = new Paragraph(billData.toString());
            Paragraph paragraph4 = new Paragraph("Product ID: " + billData.getIdProduct() + " | " +
                    "Product Name: " + billData.getProductName() + " | " +
                    "Price: " + billData.getPrice() + " EUR | " +
                    "Amount: " + billData.getAmount());
            paragraph1.setBold();
            paragraph1.setUnderline();
            paragraph1.setFontSize(16);
            paragraph2.setFirstLineIndent(40f);
            paragraph3.setFirstLineIndent(80f);
            paragraph4.setFirstLineIndent(80f);
            paragraphs.add(paragraph1);
            paragraphs.add(paragraph2);
            paragraphs.add(paragraph3);
            paragraphs.add(paragraph4);

            for (Paragraph paragraph : paragraphs) {
                doc.add(paragraph);
            }

            doc.close();
            System.out.println("Bill printed successfully...");
        }
    }
}
