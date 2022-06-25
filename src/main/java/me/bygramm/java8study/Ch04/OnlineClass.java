package me.bygramm.java8study.Ch04;

import java.util.Optional;

public class OnlineClass {

    private Integer id;

    private String title;

    private boolean closed;

    public Progress progress;

    public OnlineClass(Integer id, String title, boolean closed) {
        this.id = id;
        this.title = title;
        this.closed = closed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    // nullPointerException을 해결하는 기존 방식

//    public Progress getProgress() {
//        if (this.progress == null) {
//            throw new IllegalStateException();
//        }
//        return progress;
//    }

    // Java 8 이후 => Optional 활용

    public Optional<Progress> getProgress() {
        return Optional.ofNullable(progress);
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }
}
