package models;

import java.util.Date;

public class Applications {
    private int applicationId;
    private int userId;
    private int jobId;
    private Date appliedDate;
    private String status;      // Statut de la candidature
    private String coverLetter; // Lettre de motivation de la candidature

    // Constructeur par défaut
    public Applications() {}

    // Constructeur sans applicationId (ajout sans auto-incrémentation)
    public Applications(int userId, int jobId, Date appliedDate, String status, String coverLetter) {
        this.userId = userId;
        this.jobId = jobId;
        this.appliedDate = appliedDate;
        this.status = status;
        this.coverLetter = coverLetter;
    }

    // Constructeur avec applicationId
    public Applications(int applicationId, int userId, int jobId, Date appliedDate, String status, String coverLetter) {
        this.applicationId = applicationId;
        this.userId = userId;
        this.jobId = jobId;
        this.appliedDate = appliedDate;
        this.status = status;
        this.coverLetter = coverLetter;
    }

    // Getters & Setters pour tous les champs
    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public Date getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getStatus() {
        return status;  // Retourne le statut de la candidature
    }

    public void setStatus(String status) {
        this.status = status;  // Définit le statut de la candidature
    }

    public String getCoverLetter() {
        return coverLetter;  // Retourne la lettre de motivation
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;  // Définit la lettre de motivation
    }

    // Méthode toString() pour afficher l'objet Applications sous forme de chaîne de caractères
    @Override
    public String toString() {
        return "Applications{" +
                "applicationId=" + applicationId +
                ", userId=" + userId +
                ", jobId=" + jobId +
                ", appliedDate=" + appliedDate +
                ", status='" + status + '\'' +
                ", coverLetter='" + coverLetter + '\'' +
                '}';
    }
}
