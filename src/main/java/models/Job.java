package models;

import java.util.Date;

public class Job {
    private int jobId;
    private String title;
    private String description;
    private int postedBy; // Ce champ correspond à `posted_by` dans la base de données
    private String location;
    private String jobType;
    private Date postedDate;
    private Date expiryDate;
    private boolean isActive;

    // Constructeur par défaut
    public Job() {}

    // Constructeur avec tous les attributs
    public Job(int jobId, String title, String description, int postedBy, String location, String jobType, Date postedDate, Date expiryDate, boolean isActive) {
        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.postedBy = postedBy;
        this.location = location;
        this.jobType = jobType;
        this.postedDate = postedDate;
        this.expiryDate = expiryDate;
        this.isActive = isActive;
    }

    // Getters et Setters
    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPostedBy() { return postedBy; }
    public void setPostedBy(int postedBy) { this.postedBy = postedBy; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    public Date getPostedDate() { return postedDate; }
    public void setPostedDate(Date postedDate) { this.postedDate = postedDate; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    // Méthode toString()
    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", postedBy=" + postedBy +
                ", location='" + location + '\'' +
                ", jobType='" + jobType + '\'' +
                ", postedDate=" + postedDate +
                ", expiryDate=" + expiryDate +
                ", isActive=" + isActive +
                '}';
    }
}
