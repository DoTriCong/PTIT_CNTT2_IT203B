package ex5.model;

public class Doctor {
    private String doctorId;
    private String fullName;
    private String specialty;

    public Doctor(String doctorId, String fullName, String specialty) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.specialty = specialty;
    }

    // Getter & Setter
    public String getDoctorId() { return doctorId; }
    public String getFullName() { return fullName; }
    public String getSpecialty() { return specialty; }
}
