package ex5;
public class PatientController {
    private final BedService bedService;
    private final AdmissionService admissionService;

    public PatientController() {
        this.bedService = new BedService();
        this.admissionService = new AdmissionService();
    }

    public void showAvailableBeds() {
        bedService.showAvailableBeds();
    }

    public void admitPatient(String patientName, int age, int bedId, double advanceAmount) {
        admissionService.admitPatient(patientName, age, bedId, advanceAmount);
    }
}
