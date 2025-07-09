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

import uth.edu.dieutrihiemmuon.controllers.Admin.ServicePackageController;
import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.services.ServicePackageService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ServicePackageControllerTest {

    @InjectMocks
    private ServicePackageController servicePackageController;

    @Mock
    private ServicePackageService servicePackageService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    // Test GET /admin/servicepackage/index
    @Test
    void testAdminServicePackageIndex_ReturnsViewWithList() {
        List<ServicePackageDTO> servicePackages = List.of(new ServicePackageDTO(), new ServicePackageDTO());
        when(servicePackageService.getServicePackages()).thenReturn(servicePackages);

        String view = servicePackageController.adminservicepackageindex(model);

        assertEquals("admin/servicepackage/index", view);
        verify(servicePackageService).getServicePackages();
        verify(model).addAttribute("ServicePackageDTOs", servicePackages);
    }

    // Test GET /admin/servicepackage/create
    @Test
    void testAdminServicePackageCreate_ReturnsViewWithNewDTO() {
        String view = servicePackageController.adminservicepackagecreate(model);

        assertEquals("admin/servicepackage/create", view);
        verify(model).addAttribute(eq("ServicePackageDTO"), any(ServicePackageDTO.class));
    }

    // Test POST /admin/servicepackage/create - Success
    @Test
    void testAddServicePackage_Success() {
        ServicePackageDTO dto = new ServicePackageDTO();
        dto.setServiceName("Yoga");

        when(servicePackageService.findByServiceName(dto.getServiceName())).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(servicePackageService.addServicePackage(dto)).thenReturn(true);

        String view = servicePackageController.addServicePackage(dto, bindingResult, model);

        assertEquals("redirect:/admin/servicepackage/index", view);
        verify(servicePackageService).addServicePackage(dto);
        verify(model).addAttribute("message", "Thêm thành công!");
    }

    // Test POST /admin/servicepackage/create - Duplicate Service Name
    @Test
    void testAddServicePackage_Fail_DuplicateServiceName() {
        ServicePackageDTO dto = new ServicePackageDTO();
        dto.setServiceName("Hiemmuon");

        when(servicePackageService.findByServiceName(dto.getServiceName())).thenReturn(new ServicePackageDTO());
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = servicePackageController.addServicePackage(dto, bindingResult, model);

        assertEquals("admin/servicepackage/create", view);
        verify(bindingResult).rejectValue("serviceName", "error.ServicePackageDTO", "Tên dịch vụ đã tồn tại");
        verify(model).addAttribute("ServicePackageDTO", dto);
    }

    // Test GET /admin/servicepackage/detail/{id}
    @Test
    void testAdminServicePackageDetail_ReturnsViewWithDTO() {
        long id = 1L;
        ServicePackageDTO dto = new ServicePackageDTO();
        when(servicePackageService.getServicePackage(id)).thenReturn(dto);

        String view = servicePackageController.adminservicepackagedetail(id, model);

        assertEquals("admin/servicepackage/detail", view);
        verify(model).addAttribute("ServicePackageDTO", dto);
    }

    // Test GET /admin/servicepackage/delete/{id}
    @Test
    void testAdminServicePackageDelete_RedirectsToIndex() {
        long id = 1L;

        String view = servicePackageController.adminservicepackagedelete(id, model);

        assertEquals("redirect:/admin/servicepackage/index", view);
        verify(servicePackageService).deleteServicePackage(id);
    }

    // Test GET /admin/servicepackage/edit/{id}
    @Test
    void testAdminServicePackageEdit_ReturnsViewWithDTO() {
        long id = 1L;
        ServicePackageDTO dto = new ServicePackageDTO();
        when(servicePackageService.getServicePackage(id)).thenReturn(dto);

        String view = servicePackageController.adminservicepackageedit(id, model);

        assertEquals("admin/servicepackage/edit", view);
        verify(model).addAttribute("ServicePackageDTO", dto);
    }

    // 🔥 Test POST /admin/servicepackage/edit/{id} - Success
    @Test
    void testEditServicePackage_Success() {
        long id = 1L;
        ServicePackageDTO dto = new ServicePackageDTO();
        dto.setIdService(id);
        dto.setServiceName("Updated Yoga");

        when(servicePackageService.findByServiceName(dto.getServiceName())).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        String view = servicePackageController.editServicePackage(id, dto, bindingResult, model);

        assertEquals("redirect:/admin/servicepackage/index", view);
        verify(servicePackageService).updateServicePackage(dto);
    }

    // 🔥 Test POST /admin/servicepackage/edit/{id} - Duplicate Service Name
    @Test
    void testEditServicePackage_Fail_DuplicateServiceName() {
        long id = 1L;
        ServicePackageDTO dto = new ServicePackageDTO();
        dto.setIdService(id);
        dto.setServiceName("Hiemmuon");

        ServicePackageDTO existing = new ServicePackageDTO();
        existing.setIdService(2L); // khác id
        when(servicePackageService.findByServiceName(dto.getServiceName())).thenReturn(existing);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = servicePackageController.editServicePackage(id, dto, bindingResult, model);

        assertEquals("/admin/servicepackage/edit", view);
        verify(bindingResult).rejectValue("serviceName", "error.servicePackageDTO", "Tên dịch vụ đã tồn tại");
        verify(model).addAttribute("ServicePackageDTO", dto);
    }
}
