package chokoapp.imanani;

import android.widget.TextView;

/**
 * 勤務時間と作業時間を計って、表示に反映させるクラス。
 */
class TimeKeeper implements Runnable {

    /**
     * 勤務時間。
     */
    private long workStartTime;
    /**
     * 作業時間。
     */
    private long taskStartTime;

    /**
     * 勤務時間表示用View。
     */
    private TextView workTimeView;
    /**
     * 作業時間表示用View。
     */
    private TextView taskTimeView;

    public TimeKeeper(TextView workTimeView, TextView taskTimeView) {
        this.workTimeView = workTimeView;
        this.taskTimeView = taskTimeView;
    }

    /**
     * 勤務開始。
     * 勤務を終了しているか気にせず開始ボタンを押したらその時点でスタート
     *
     * @return 開始時刻をミリ秒で返す
     */
    public long beginWork() {
        long now = System.currentTimeMillis();
        return workStartTime = taskStartTime = now;
    }

    /**
     * 勤務終了。
     * 
     * @return 終了時刻をミリ秒で返す (勤務を始めていないと 0 が戻ってくるけど気にしない)
     */
    public long endWork() {
        long now = System.currentTimeMillis();
        try {
            return hasStartedWork() ? now : 0;
        } finally {
            workStartTime = taskStartTime = 0;
        }
    }

    /**
     * 作業変更。
     * 
     * @return 勤務開始または前回の作業変更から現在までの時間を返す。
     */
    public long changeTask() {
        if (notYetStartedWork()) {
            return 0;
        }
        long now = System.currentTimeMillis();
        try {
            return notYetStartedTask() ? 0 : now - taskStartTime;
        } finally {
            taskStartTime = now;
        }
    }

    /**
     * 勤務時間と作業時間を表示に反映させる。
     */
    @Override
    public void run() {
        long now = System.currentTimeMillis();
        if (hasStartedWork()) {
            workTimeView.setText(format(now - workStartTime));
        }
        if (hasStartedTask()) {
            taskTimeView.setText(format(now - taskStartTime));
        }
    }

    /**
     * 時間のフォーマッティング。
     * 
     * @param time
     *            ミリ秒単位の時間。
     * @return H:MM:SS形式の文字列。
     */
    public String format(long time) {
        long sec = time / 1000;
        long min = sec / 60;
        long hour = min / 60;
        return String.format("%02d:%02d:%02d", hour, min % 60, sec % 60);
    }

    public long getWorkStartTime() { return workStartTime; }
    public long getTaskStartTime() { return taskStartTime; }

    public boolean hasStartedWork() { return workStartTime != 0; }
    public boolean notYetStartedWork() { return workStartTime == 0; }
    public boolean hasStartedTask() { return taskStartTime != 0; }
    public boolean notYetStartedTask() { return taskStartTime == 0; }
}