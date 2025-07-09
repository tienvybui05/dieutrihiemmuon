package uth.edu.dieutrihiemmuon.AdminControllerUnitTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

import uth.edu.dieutrihiemmuon.controllers.Admin.DoctorController;
import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.services.IDoctorService;
import uth.edu.dieutrihiemmuon.services.IServicePackageService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DoctorControllerTest {

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private IDoctorService doctorService;

    @Mock
    private IServicePackageService servicePackageService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    // Test GET /admin/doctor/index
    @Test
    void testAdminDoctorIndex_ReturnsViewWithDoctors() {
        List<DoctorDTO> doctors = List.of(new DoctorDTO(), new DoctorDTO());
        when(doctorService.getDoctors()).thenReturn(doctors);

        String view = doctorController.admindoctorindex(model);

        assertEquals("admin/doctor/index", view);
        verify(model).addAttribute("DoctorDTOs", doctors);
    }

    // Test GET /admin/doctor/create
    @Test
    void testAdminDoctorCreate_ReturnsViewWithServicePackages() {
        List<ServicePackageDTO> servicePackages = List.of(new ServicePackageDTO(), new ServicePackageDTO());
        when(servicePackageService.getServicePackages()).thenReturn(servicePackages);

        String view = doctorController.admindoctorcreate(model);

        assertEquals("admin/doctor/create", view);
        verify(model).addAttribute(eq("ServicePackageDTOs"), eq(servicePackages));
        verify(model).addAttribute(eq("DoctorDTO"), any(DoctorDTO.class));
    }

    // Test POST /admin/doctor/create - thêm thành công
    @Test
    void testAddDoctor_Success() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setUserName("newdoctor");
        doctorDTO.setEmail("doctor@example.com");
        doctorDTO.setPhoneNumber("123456789");

        when(doctorService.findByUsername(doctorDTO.getUserName())).thenReturn(null);
        when(doctorService.findByEmail(doctorDTO.getEmail())).thenReturn(null);
        when(doctorService.findByPhoneNumber(doctorDTO.getPhoneNumber())).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(doctorService.addDoctor(doctorDTO)).thenReturn(true);

        String view = doctorController.addDoctor(doctorDTO, bindingResult, model);

        assertEquals("redirect:/admin/doctor/index", view);
        verify(doctorService).addDoctor(doctorDTO);
        verify(model).addAttribute("message", "Thêm thành công!");
    }

    // Test POST /admin/doctor/create - trùng username
    @Test
    void testAddDoctor_Fail_DuplicateUsername() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setUserName("existingdoctor");

        when(doctorService.findByUsername(doctorDTO.getUserName())).thenReturn(new DoctorDTO());
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = doctorController.addDoctor(doctorDTO, bindingResult, model);

        assertEquals("admin/doctor/create", view);
        verify(bindingResult).rejectValue("userName", "error.DoctorDTO", "Tên đăng nhập đã tồn tại");
        verify(servicePackageService).getServicePackages();
        verify(model).addAttribute(eq("DoctorDTO"), eq(doctorDTO));
    }

    // Test POST /admin/doctor/create - trùng email
    @Test
    void testAddDoctor_Fail_DuplicateEmail() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setEmail("existingemail");

        when(doctorService.findByEmail(doctorDTO.getEmail())).thenReturn(new DoctorDTO());
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = doctorController.addDoctor(doctorDTO, bindingResult, model);

        assertEquals("admin/doctor/create", view);
        verify(bindingResult).rejectValue("email", "error.DoctorDTO", "Email đã tồn tại");
        verify(servicePackageService).getServicePackages();
        verify(model).addAttribute(eq("DoctorDTO"), eq(doctorDTO));
    }

    // Test POST /admin/doctor/create - trùng số điện thoại
    @Test
    void testAddDoctor_Fail_DuplicatePhoneNumber() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setEmail("123456");

        when(doctorService.findByPhoneNumber(doctorDTO.getPhoneNumber())).thenReturn(new DoctorDTO());
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = doctorController.addDoctor(doctorDTO, bindingResult, model);

        assertEquals("admin/doctor/create", view);
        verify(bindingResult).rejectValue("phoneNumber", "error.DoctorDTO", "Số điện thoại đã tồn tại");
        verify(servicePackageService).getServicePackages();
        verify(model).addAttribute(eq("DoctorDTO"), eq(doctorDTO));
    }

    // Test GET /admin/doctor/edit/{id}
    @Test
    void testAdminDoctorEdit_ReturnsViewWithDoctor() {
        long doctorId = 1L;
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setIdService(2L);
        ServicePackageDTO servicePackageDTO = new ServicePackageDTO();
        List<ServicePackageDTO> servicePackages = List.of(new ServicePackageDTO());

        when(doctorService.getDoctor(doctorId)).thenReturn(doctorDTO);
        when(servicePackageService.getServicePackage(2L)).thenReturn(servicePackageDTO);
        when(servicePackageService.getServicePackages()).thenReturn(servicePackages);

        String view = doctorController.admindoctoredit(doctorId, model);

        assertEquals("admin/doctor/edit", view);
        verify(model).addAttribute("DoctorDTO", doctorDTO);
        verify(model).addAttribute("servicePackageDTOold", servicePackageDTO);
        verify(model).addAttribute("ServicePackageDTOs", servicePackages);
    }

    // Test POST /admin/doctor/edit/{id} - chỉnh sửa thành công
    @Test
    void testEditDoctor_Success() {
        long doctorId = 1L;
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setUserName("updateddoctor");
        doctorDTO.setEmail("updated@example.com");
        doctorDTO.setPhoneNumber("987654321");

        when(doctorService.findByUsername(doctorDTO.getUserName())).thenReturn(null);
        when(doctorService.findByEmail(doctorDTO.getEmail())).thenReturn(null);
        when(doctorService.findByPhoneNumber(doctorDTO.getPhoneNumber())).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        String view = doctorController.editDoctor(doctorId, doctorDTO, bindingResult, model);

        assertEquals("redirect:/admin/doctor/index", view);
        verify(doctorService).updateDoctor(doctorDTO);
    }

    // Test POST /admin/doctor/edit/{id} - trùng username
    @Test
    void testEditDoctor_Fail_DuplicateUsername() {
        long doctorId = 1L;
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setUserName("existingusername");
        doctorDTO.setEmail("email@example.com");

        DoctorDTO existingUsername = new DoctorDTO();
        existingUsername.setId_doctor(2L); // khác id

        when(doctorService.findByUsername(doctorDTO.getUserName())).thenReturn(existingUsername);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = doctorController.editDoctor(doctorId, doctorDTO, bindingResult, model);

        assertEquals("/admin/doctor/edit", view);
        verify(bindingResult).rejectValue("userName", "error.doctorDTO", "Tên đăng nhập đã tồn tại");
        verify(servicePackageService).getServicePackage(doctorDTO.getIdService());
        verify(servicePackageService).getServicePackages();
        verify(model).addAttribute(eq("DoctorDTO"), eq(doctorDTO));
    }

    // Test POST /admin/doctor/edit/{id} - trùng email
    @Test
    void testEditDoctor_Fail_DuplicateEmail() {
        long doctorId = 1L;
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setUserName("doc");
        doctorDTO.setEmail("duplicate@example.com");

        DoctorDTO existingEmail = new DoctorDTO();
        existingEmail.setId_doctor(2L); // khác id

        when(doctorService.findByEmail(doctorDTO.getEmail())).thenReturn(existingEmail);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = doctorController.editDoctor(doctorId, doctorDTO, bindingResult, model);

        assertEquals("/admin/doctor/edit", view);
        verify(bindingResult).rejectValue("email", "error.doctorDTO", "Email đã tồn tại");
        verify(servicePackageService).getServicePackage(doctorDTO.getIdService());
        verify(servicePackageService).getServicePackages();
        verify(model).addAttribute(eq("DoctorDTO"), eq(doctorDTO));
    }

    // Test POST /admin/doctor/edit/{id} - trùng số điện thoại
    @Test
    void testEditDoctor_Fail_DuplicatePhoneNumber() {
        long doctorId = 1L;
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setUserName("username");
        doctorDTO.setEmail("duplicate@example.com");
        doctorDTO.setPhoneNumber("123456789");

        DoctorDTO existingPhoneNumber = new DoctorDTO();
        existingPhoneNumber.setId_doctor(2L); // khác id

        when(doctorService.findByPhoneNumber(doctorDTO.getPhoneNumber())).thenReturn(existingPhoneNumber);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = doctorController.editDoctor(doctorId, doctorDTO, bindingResult, model);

        assertEquals("/admin/doctor/edit", view);
        verify(bindingResult).rejectValue("phoneNumber", "error.doctorDTO", "Số điện thoại đã tồn tại");
        verify(servicePackageService).getServicePackage(doctorDTO.getIdService());
        verify(servicePackageService).getServicePackages();
        verify(model).addAttribute(eq("DoctorDTO"), eq(doctorDTO));
    }

    // Test GET /admin/doctor/detail/{id}
    @Test
    void testAdminDoctorDetail_ReturnsViewWithDoctor() {
        long doctorId = 1L;
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setIdService(2L);
        ServicePackageDTO servicePackageDTO = new ServicePackageDTO();
        servicePackageDTO.setServiceName("Yoga");

        when(doctorService.getDoctor(doctorId)).thenReturn(doctorDTO);
        when(servicePackageService.getServicePackage(2L)).thenReturn(servicePackageDTO);

        String view = doctorController.admindoctordetail(doctorId, model);

        assertEquals("admin/doctor/detail", view);
        verify(model).addAttribute("DoctorDTO", doctorDTO);
        verify(model).addAttribute("nameServicePackage", "Yoga");
    }

    // Test GET /admin/doctor/delete/{id}
    @Test
    void testAdminDoctorDelete_RedirectsToIndex() {
        long doctorId = 1L;

        String view = doctorController.admindoctordelete(doctorId, model);

        assertEquals("redirect:/admin/doctor/index", view);
        verify(doctorService).deleteDoctor(doctorId);
    }
}
