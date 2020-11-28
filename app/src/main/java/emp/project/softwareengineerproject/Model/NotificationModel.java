package emp.project.softwareengineerproject.Model;

public class NotificationModel {
    String notif_id,notif_title,notif_content,notif_date;

    public NotificationModel(String notif_id, String notif_title, String notif_content, String notif_date) {
        this.notif_id = notif_id;
        this.notif_title = notif_title;
        this.notif_content = notif_content;
        this.notif_date = notif_date;
    }

    public NotificationModel(String notif_title, String notif_content, String notif_date) {
        this.notif_title = notif_title;
        this.notif_content = notif_content;
        this.notif_date = notif_date;
    }

    public String getNotif_id() {
        return notif_id;
    }

    public String getNotif_title() {
        return notif_title;
    }

    public String getNotif_content() {
        return notif_content;
    }

    public String getNotif_date() {
        return notif_date;
    }
}
