import com.itextpdf.io.IOException;

import java.io.File;


public class Main {
    // здесь создайте сервер, который отвечал бы на нужные запросы
    // слушать он должен порт 8989
    // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
    public static void main(String[] args) throws IOException, java.io.IOException {
        File catalogSorting = new File("pdfs");
        System.out.println("Индексирование pdf файлов в папке " + catalogSorting.getPath());
        var ENGINE = new BooleanSearchEngine(catalogSorting);
        int PORT = 8989;
        Server server = new Server(PORT, ENGINE);

        System.out.println("Сервер запущен, порт: " + PORT);
        server.start();


    }
}