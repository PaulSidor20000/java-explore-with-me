package ru.practicum.ewm.validators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.dto.EventState;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.exceptions.RequestConditionException;
import ru.practicum.ewm.request.dto.RequestStatus;
import ru.practicum.ewm.request.entity.Request;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestValidator {

    public static Event incomingRequestValidator(int userId, int confirmedRequests, Event event) {
        checkParticipantLimit(event.getParticipantLimit(), confirmedRequests);

        if (event.getUserId() == userId) {
            throw new RequestConditionException("The requester can't be event maker");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new RequestConditionException("Impossible to make a request on unpublished event");
        }
        return event;
    }

    public static void doThrow(Request request) {
        if (request != null) {
            throw new RequestConditionException(request);
        }
    }

    public static Request doCancel(Request request) {
        if (request.getStatus() == RequestStatus.PENDING) {
            request.setStatus(RequestStatus.CANCELED);
            return request;
        }
        throw new RequestConditionException("Impossible to cancel published request");
    }

    public static Request updateRequests(Request request, Event event, int confirmedRequests, RequestStatus statusToUpdate) {
        if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(statusToUpdate);
            return request;
        }

        checkParticipantLimit(event.getParticipantLimit(), confirmedRequests);

        request.setStatus(statusToUpdate);
        return request;
    }

    public static boolean isRequestPending(Request request) {
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new RequestConditionException("The request was considered already");
        }
        return true;
    }

    private static void checkParticipantLimit(int participantLimit, int confirmedRequests) {
        if (participantLimit != 0 && confirmedRequests >= participantLimit) {
            throw new RequestConditionException("Participant limit was reached");
        }
    }
}
