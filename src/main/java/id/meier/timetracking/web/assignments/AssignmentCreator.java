package id.meier.timetracking.web.assignments;

import id.meier.timetracking.adapter.in.web.ManageAssignmentController;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SaveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentCommand;
import id.meier.timetracking.domain.AssignmentRefHitParadeProjectPhaseTask;
import id.meier.timetracking.domain.Assignment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class AssignmentCreator {
	private final ManageAssignmentController manageAssignmentController;

	public AssignmentCreator(ManageAssignmentController manageAssignmentController) {
		this.manageAssignmentController = manageAssignmentController;
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
			Assignment foundAssignmentTemplate = manageAssignmentController.findAssignmentByCloneableTemplateName(
					SelectAssignmentCommand.builder().setTemplateName(templateName).build());
			if (foundAssignmentTemplate!= null) {
				assignment.setProject(foundAssignmentTemplate.getProject());
				assignment.setPhase(foundAssignmentTemplate.getPhase());
				assignment.setTask(foundAssignmentTemplate.getTask());
				assignment.setDescription(foundAssignmentTemplate.getDescription());
			}
		}
	}

	private void guessAssignment(boolean guessDataForNewAssignment, Assignment assignment) {
		if (guessDataForNewAssignment) {
			List<AssignmentRefHitParadeProjectPhaseTask> hitParade = manageAssignmentController.findTopReferredProjectPhaseAndTasks();
			if (hitParade.size() > 0) {
				AssignmentRefHitParadeProjectPhaseTask h = hitParade.get(0);
				assignment.setProject(h.getProject());
				assignment.setPhase(h.getPhase());
				assignment.setTask(h.getTask());
			}
		}
	}

	private void setStartDate(Assignment assignment) {
		LocalDate startDate = LocalDate.now();
		LocalTime startTime = LocalTime.now();
		assignment.setStartDate(startDate);
		assignment.setStartTime(startTime);
	}

	void terminateOpenAssignmentsOnSaveNewAssignment(Assignment newAssignment, boolean terminateOpenAssignments) {
		if (terminateOpenAssignments) {				
			List<Assignment> list = manageAssignmentController.findByEndDateIsNullOrEndTimeIsNull();
			for (Assignment a : list) {
				a.setEndTime(newAssignment.getStartTime());
				a.setEndDate(newAssignment.getStartDate());
				manageAssignmentController.saveAssignment(SaveAssignmentCommand.of(a));
			}
		}
	}
}
