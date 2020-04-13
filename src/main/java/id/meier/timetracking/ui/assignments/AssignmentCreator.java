package id.meier.timetracking.ui.assignments;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

import id.meier.timetracking.dblayer.repository.AssignmentRefHitParadeProjectPhaseTask;
import id.meier.timetracking.dblayer.repository.AssignmentRepository;
import id.meier.timetracking.model.Assignment;

@Component
public class AssignmentCreator {
	private final AssignmentRepository assignmentRepo;

	public AssignmentCreator(AssignmentRepository assignmentRepo) {
		this.assignmentRepo = assignmentRepo;
	}
	
	Assignment createNewAssignment(boolean guessDataForNewAssignment, String templateName) {
		Assignment result = new Assignment();
		setStartDate(result);
		guessAssignment(guessDataForNewAssignment, result);
		applyTemplate(guessDataForNewAssignment, templateName, result);
		return result;
	}

	private void applyTemplate(boolean guessDataForNewAssignment, String templateName, Assignment assignment) {
		if (!guessDataForNewAssignment && templateName != null && !templateName.equals("")) {
			List<Assignment> templateAssignments = assignmentRepo.findAssignmentByCloneableTemplateName(templateName);
			if (templateAssignments.size() > 0) {
				Assignment templateAssignment = templateAssignments.get(0);
				assignment.setProject(templateAssignment.getProject());
				assignment.setPhase(templateAssignment.getPhase());
				assignment.setTask(templateAssignment.getTask());
				assignment.setDescription(templateAssignment.getDescription());
			}
		}
	}

	private void guessAssignment(boolean guessDataForNewAssignment, Assignment assignment) {
		if (guessDataForNewAssignment) {
			List<AssignmentRefHitParadeProjectPhaseTask> hitParade = assignmentRepo.findTopReferredProjectPhaseAndTasks();
			if (hitParade.size() > 0) {
				AssignmentRefHitParadeProjectPhaseTask h = hitParade.get(0);
				assignment.setProject(h.getProject());
				assignment.setPhase(h.getPhase());
				assignment.setTask(h.getTask());
			}
		}
	}

	private void setStartDate(Assignment assignment) {
		Assignment result = assignment;
		LocalDate startDate = LocalDate.now();
		LocalTime startTime = LocalTime.now();
		result.setStartDate(startDate);
		result.setStartTime(startTime);
	}

	void terminateOpenAssignmentsOnSaveNewAssignment(Assignment newAssignment, boolean terminateOpenAssignments) {
		if (terminateOpenAssignments) {				
			List<Assignment> list = assignmentRepo.findByEndDateIsNullOrEndTimeIsNull();
			for (Assignment a : list) {
				a.setEndTime(newAssignment.getStartTime());
				a.setEndDate(newAssignment.getStartDate());
				assignmentRepo.save(a);
			}
		}
	}
}
