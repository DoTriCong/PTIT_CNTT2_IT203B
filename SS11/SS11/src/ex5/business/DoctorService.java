package ex5.business;

import ex5.dao.DoctorDAO;
import ex5.model.Doctor;

import java.util.List;

public class DoctorService {
    private DoctorDAO dao = new DoctorDAO();

    public void showDoctors() {
        List<Doctor> list = dao.getAllDoctors();
        if (list.isEmpty()) {
            System.out.println(" Không có bác sĩ nào trong danh sách.");
        } else {
            for (Doctor d : list) {
                System.out.println("Mã: " + d.getDoctorId() +
                        " | Họ tên: " + d.getFullName() +
                        " | Chuyên khoa: " + d.getSpecialty());
            }
        }
    }

    public void addDoctor(Doctor doctor) {
        if (dao.addDoctor(doctor)) {
            System.out.println(" Thêm bác sĩ thành công!");
        } else {
            System.out.println(" Thêm bác sĩ thất bại!");
        }
    }

    public void statisticSpecialty() {
        dao.statisticSpecialty();
    }
}
