package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 *
 * Contains one single action made on model Task.
 */

@Entity
public class TaskLogEntry {

    @Id
    @GeneratedValue
    private Integer taskLogEntryId;

    @ManyToOne
    private TaskLog taskLog;

    private String changedProperty;

    private Integer oldId;

    private String oldValue;

    private Integer newId;

    private String newValue;

    public Integer getTaskLogEntryId() {
        return taskLogEntryId;
    }

    public void setTaskLogEntryId(Integer taskLogEntryId) {
        this.taskLogEntryId = taskLogEntryId;
    }

    public TaskLog getTaskLog() {
        return taskLog;
    }

    public void setTaskLog(TaskLog taskLog) {
        this.taskLog = taskLog;
    }

    public String getChangedProperty() {
        return changedProperty;
    }

    public void setChangedProperty(String changedProperty) {
        this.changedProperty = changedProperty;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public Integer getNewId() {
        return newId;
    }

    public void setNewId(Integer newId) {
        this.newId = newId;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
