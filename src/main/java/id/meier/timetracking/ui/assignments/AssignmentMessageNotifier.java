/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package id.meier.timetracking.ui.assignments;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import id.meier.timetracking.businesslayer.consistency.ConsistencyProblem;
import id.meier.timetracking.businesslayer.consistency.IConsistencyMessage;
import id.meier.timetracking.model.Assignment;

import java.util.List;

public class AssignmentMessageNotifier extends Notification {
    public AssignmentMessageNotifier(List<IConsistencyMessage> messages, IAssignmentEditor editor) {
        String messagesString = getMessage(messages);
        Label content = new Label();
        content.getElement().setProperty("innerHTML", messagesString);
        NativeButton buttonInside = new NativeButton(getTranslation("time.tracking.consistency.message.show.dependent.assignment"));
        setDuration(5000);
        setPosition(Position.TOP_CENTER);
        Assignment a = getReferencedAssignment(messages);
        if (a != null) {
            add(content, buttonInside);
            buttonInside.addClickListener(event -> {
                editor.editAssignment(a, false);
                this.close();
            });
        } else {
            add(content);
        }
        if (messages.size() > 0) {
            open();
        }

    }

    private Assignment getReferencedAssignment(List<IConsistencyMessage> messageList) {
        return messageList
                .stream().map(IConsistencyMessage::getMessageData)
                .flatMap(List::stream)
                .map(md -> (Assignment)md.getData())
                .findFirst().orElse(null);
    }

    private String getMessage(List<IConsistencyMessage> messageList) {
        StringBuffer textMessages = new StringBuffer();
        for (IConsistencyMessage m : messageList) {
            for (ConsistencyProblem p : m.getProblems()) {
                String key = p.getMessageKey();
                String message = getTranslation(key);
                textMessages.append("<p>" + message + "</p> ");
            }
        }
        return textMessages.toString();
    }
}
