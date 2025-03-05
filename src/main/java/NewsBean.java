import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class NewsBean implements Serializable {
    private List<News> newsList;

    @PostConstruct
    public void init() {
        newsList = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            newsList.add(new News("Title " + i, "This is the content of news item " + i, new Date(), "Author " + i));
        }
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public static class News {
        private String title;
        private String content;
        private Date date;
        private String author;

        public News(String title, String content, Date date, String author) {
            this.title = title;
            this.content = content;
            this.date = date;
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}