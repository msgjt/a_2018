package ro.msg.edu.jbugs.bugsManagement.persistence.entity;

import ro.msg.edu.jbugs.userManagement.persistence.entity.BaseEntity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "history")
public class History extends BaseEntity<Long> {


    @Transient
    private final static int MAX_STRING_LENGTH = 40;


    public History() {
    }

    @JoinColumn(name = "bugId", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Bug bug;
    @Column(name = "modifiedDate", length = MAX_STRING_LENGTH, nullable = false)
    private Date modifiedDate;
    @Column(name = "afterStatus", length = MAX_STRING_LENGTH, nullable = false)
    private String afterStatus;
    @Column(name = "beforeStatus", length = MAX_STRING_LENGTH, nullable = false)
    private String beforeStatus;
    @Column(name = "modifiedBy", length = MAX_STRING_LENGTH, nullable = false)
    private String modifiedBy;
}
