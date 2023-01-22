import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;


import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    //???
    private final Map<String, List<PageEntry>> index = new HashMap<>();

    //используем мапу, где ключом будет слово,а значением - искомый список
    public BooleanSearchEngine(File pdfsDir) throws IOException {
        // прочтите тут все pdf и сохраните нужные данные,
        // тк во время поиска сервер не должен уже читать файлы
        File[] listPdfFiles = getListFiles(pdfsDir, ".pdf");
        if (listPdfFiles != null) {
            for (File pdfFile : listPdfFiles) {
                scan(pdfFile);
            }
            index.values().forEach(Collections::sort);
        }
    }


    private void scan(File pdfFile) throws IOException {
        // Чтобы создать объект пдф-документа, нужно указать объект File этого документа следующим классам:
        try (var doc = new PdfDocument(new PdfReader(pdfFile))) {
            //Чтобы получить объект одной страницы документа, нужно вызвать
            int numberOfPages = doc.getNumberOfPages();
            // Полистайте методы doc чтобы найти способ узнать количество * страниц в документе.
            for (int i = 1; i <= numberOfPages; i++) {
                PdfPage page = doc.getPage(i);
                //  Чтобы получить текст со страницы, используйте
                var text = PdfTextExtractor.getTextFromPage(page);
                // Чтобы разбить текст на слова (а они в этих документах разделены могут быть не только пробелами), используйте
                var words = text.split("\\P{IsAlphabetic}+");
                Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
                for (var word : words) { // перебираем слова
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);

                }
                for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
                    List<PageEntry> listOFCurrentWord = new ArrayList<>();
                    if (index.containsKey(entry.getKey())) {
                        listOFCurrentWord = index.get(entry.getKey());
                    }
                    listOFCurrentWord.add(new PageEntry(pdfFile.getName(), i, entry.getValue()));
                    index.put(entry.getKey(), listOFCurrentWord);
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        // тут реализуйте поиск по слову
        if (index.containsKey(word.toLowerCase())) {
            return index.get(word.toLowerCase());
        }
        return Collections.emptyList();
    }

    private File[] getListFiles(File pdfsDir, String s) {
        return pdfsDir.listFiles();
    }

}

