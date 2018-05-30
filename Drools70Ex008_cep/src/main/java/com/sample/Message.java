package com.sample;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("messageDate")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private Date messageDate;

    private String text;

    public Message(long id, Date messageDate, String text) {
        super();
        this.id = id;
        this.messageDate = messageDate;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", messageDate=" + messageDate + ", text=" + text + "]";
    }

}
