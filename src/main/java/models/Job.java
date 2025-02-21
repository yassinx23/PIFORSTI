package models;

public class Job {
    private int jobId;
    private String title;
    private String description;
    private String location;
    private String jobType;

    // Constructeur par défaut
    public Job() {}

    // Constructeur avec tous les attributs
    public Job(int jobId, String title, String description, String location, String jobType) {
        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.jobType = jobType;
    }

    // Constructeur avec seulement les informations essentielles
    public Job(String title, String description, String location, String jobType) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.jobType = jobType;
    }

    // Getters et Setters
    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    // Méthode toString()
    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", jobType='" + jobType + '\'' +
                '}';
    }
}
