public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    /* Также, списки ответов для каждого слова должны быть отсортированы в порядке уменьшения поля count.
     Для этого предлагается классу PageEntry сразу реализовывать интерфейс Comparable.*/
    @Override
    public int compareTo(PageEntry o) {
        return o.count - count;
    }

    @Override
    public String toString() {
        return pdfName + " page: " + page + " count: " + count + "\n";
    /*    return "PageEntry{" +
                "pdfName='" + pdfName + '\'' +
                ", page=" + page +
               ", count=" + count +
                '}';
  */
    }


}
