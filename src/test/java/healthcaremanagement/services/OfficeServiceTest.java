package healthcaremanagement.services;

import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.model.Office;
import com.healthcaremanagement.repository.DoctorRepositoryImpl;
import com.healthcaremanagement.repository.OfficeRepositoryImpl;
import com.healthcaremanagement.service.OfficeService;
import com.healthcaremanagement.service.DoctorService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class OfficeServiceTest {

    private SessionFactory sessionFactory;
    private OfficeService officeService;
    private DoctorService doctorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        officeService = new OfficeService(new OfficeRepositoryImpl(sessionFactory));
        doctorService = new DoctorService(new DoctorRepositoryImpl(sessionFactory));
    }

    @AfterEach
    public void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testCreateOffice() {
        // Create Office
        Office office = new Office();
        office.setLocation("123 Example St.");
        office.setPhoneNumber("222-3333");

        // Create the doctor for the office.
        Doctor doctor = new Doctor();
        doctor.setFirstName("Jane");
        doctor.setLastName("Smith");
        doctor.setSpecialty("Cardiology");
        doctor.setEmail("jane.smith@example.com");
        doctorService.createDoctor(doctor);
        assertNotNull(doctor.getDoctorID());

        // Set doctor ID for office.
        office.setDoctor(doctor);

        officeService.createOffice(office);
        assertNotNull(office.getOfficeId());
    }
}
