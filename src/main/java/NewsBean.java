import cleverquiz.controller.Controller;
import cleverquiz.controller.IController;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class NewsBean implements Serializable {
    private List<News> newsList;

    /**
     * Initializes the NewsBean by fetching the latest news from the controller
     * and populating the newsList.
     */
    @PostConstruct
    public void init() {
        IController controller = new Controller();
        List<cleverquiz.model.News> newslist = controller.getLatestNews();

        newsList = new ArrayList<>();
        for (cleverquiz.model.News tmp : newslist) {
            newsList.add(new News(tmp));
        }
    }

    /**
     * Returns the list of news items.
     *
     * @return a list of News objects.
     */
    public List<News> getNewsList() {
        return newsList;
    }

    public static class News {
        private String title;
        private String content;
        private LocalDateTime date;
        private String author;

        /**
         * Constructs a News object with the specified details.
         *
         * @param title   the title of the news.
         * @param content the content of the news.
         * @param date    the publication date of the news.
         * @param author  the author of the news.
         */
        public News(String title, String content, LocalDateTime date, String author) {
            this.title = title;
            this.content = content;
            this.date = date;
            this.author = author;
        }

        /**
         * Constructs a News object from a cleverquiz.model.News object.
         *
         * @param tmp the cleverquiz.model.News object to convert.
         */
        public News(cleverquiz.model.News tmp) {
            this.title = tmp.getTitle();
            this.content = tmp.getText();
            this.date = tmp.getDate();
            this.author = tmp.getAuthor().getUsername();
        }

        /**
         * Returns the title of the news.
         *
         * @return the title of the news.
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets the title of the news.
         *
         * @param title the title to set.
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Returns the content of the news.
         *
         * @return the content of the news.
         */
        public String getContent() {
            return content;
        }

        /**
         * Sets the content of the news.
         *
         * @param content the content to set.
         */
        public void setContent(String content) {
            this.content = content;
        }

        /**
         * Returns the publication date of the news.
         *
         * @return the publication date of the news.
         */
        public LocalDateTime getDate() {
            return date;
        }

        /**
         * Sets the publication date of the news.
         *
         * @param date the date to set.
         */
        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        /**
         * Returns the author of the news.
         *
         * @return the author of the news.
         */
        public String getAuthor() {
            return author;
        }

        /**
         * Sets the author of the news.
         *
         * @param author the author to set.
         */
        public void setAuthor(String author) {
            this.author = author;
        }
    }
}