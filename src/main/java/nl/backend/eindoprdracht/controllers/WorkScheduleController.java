package nl.backend.eindoprdracht.controllers;

import jakarta.validation.Valid;
import nl.backend.eindoprdracht.dtos.employeeaccount.EmployeeAccountInputDto;
import nl.backend.eindoprdracht.dtos.employeeaccount.EmployeeAccountOutputDto;
import nl.backend.eindoprdracht.dtos.workschedule.WorkScheduleInputDto;
import nl.backend.eindoprdracht.dtos.workschedule.WorkScheduleOutputDto;
import nl.backend.eindoprdracht.exceptions.ValidationException;
import nl.backend.eindoprdracht.models.WorkSchedule;
import nl.backend.eindoprdracht.services.WorkScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static nl.backend.eindoprdracht.controllers.ControllerHelper.checkForBindingResult;

@RequestMapping("/workschedule")
@RestController
public class WorkScheduleController {

    private final WorkScheduleService workScheduleService;
    public WorkScheduleController(WorkScheduleService workScheduleService) {
        this.workScheduleService = workScheduleService;
    }

    @PostMapping
    public ResponseEntity<WorkScheduleOutputDto> createWorkSchedule (@Valid @RequestBody WorkScheduleInputDto workScheduleInputDto, BindingResult br) {
        if (br.hasErrors()) {
            throw new ValidationException(checkForBindingResult(br));
        } else {
            WorkScheduleOutputDto savedWorkSchedule = workScheduleService.createWorkSchedule(workScheduleInputDto);
            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + savedWorkSchedule.id).toUriString());
            return ResponseEntity.created(uri).body(savedWorkSchedule);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkScheduleOutputDto> getWorkScheduleId (@PathVariable long id){
        WorkScheduleOutputDto workScheduleOutputDto = workScheduleService.getWorkScheduleId(id);
        return ResponseEntity.ok(workScheduleOutputDto);
    }

    @GetMapping
    public ResponseEntity<List<WorkScheduleOutputDto>> getAllWorkSchedules(){
        List <WorkScheduleOutputDto> workScheduleOutputDtoList = workScheduleService.getAllWorkSchedules();
        return ResponseEntity.ok(workScheduleOutputDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkScheduleOutputDto>
    updateEmployeeAccount(@PathVariable long id, @RequestBody WorkScheduleInputDto inputDto){
        WorkScheduleOutputDto workScheduleOutputdto = workScheduleService.updateWorkSchedule(id, inputDto);
        return ResponseEntity.ok().body(workScheduleOutputdto);
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<WorkSchedule> deleteWorkSchedule(@PathVariable long id) {
        workScheduleService.deleteWorkSchedule(id);
        return ResponseEntity.noContent().build();
    }

}
