package chokoapp.imanani;

import java.util.Date;

public class Affair {
    public static final String TABLE_NAME = "affairs";
    private long _id;
    private long work_id;
    private Date start;
    private String code;
    private String description;

    public Affair(Work work, Date start, Task task) {
        this.work_id = work.getId();
        this.start = start;
        this.code = task.getCode();
        this.description = task.getDescription();
    }

    public static String create_sql() {
        return "CREATE TABLE affairs (\n"
                + "  _id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "  work_id INTEGER,\n"
                + "  start   INTEGER,\n"
                + "  code    TEXT,\n"
                + "  description TEXT\n"
                + ");";
    }

    public String insert_sql() {
        return "";
    }
}