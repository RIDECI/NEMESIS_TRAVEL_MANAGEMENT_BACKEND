package edu.dosw.rideci.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.dosw.rideci.application.mapper.TravelMapperInitial;
import edu.dosw.rideci.application.port.in.ChangeStateTravelUseCase;
import edu.dosw.rideci.application.port.in.CreateTravelUseCase;
import edu.dosw.rideci.application.port.in.DeleteTravelUseCase;
import edu.dosw.rideci.application.port.in.GetAllTravelByDriverIdUseCase;
import edu.dosw.rideci.application.port.in.GetAllTravelUseCase;
import edu.dosw.rideci.application.port.in.GetAllTravelsByPassengerIdUseCase;
import edu.dosw.rideci.application.port.in.GetPassengerListUseCase;
import edu.dosw.rideci.application.port.in.GetTravelUseCase;
import edu.dosw.rideci.application.port.in.ModifyTravelUseCase;
import edu.dosw.rideci.application.port.in.GetAllTravelsByOrganizerUseCase;
import edu.dosw.rideci.application.port.in.UpdateAvailableSlotsUseCase;

import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.domain.model.enums.Status;
import edu.dosw.rideci.domain.model.enums.TravelType;
import edu.dosw.rideci.infrastructure.controller.TravelController;
import edu.dosw.rideci.infrastructure.controller.dto.Request.TravelRequest;
import edu.dosw.rideci.infrastructure.controller.dto.Response.TravelResponse;
import edu.dosw.rideci.application.usecases.UpdateTravelPassengersUseCase;

@WebMvcTest(TravelController.class)
class TravelControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private CreateTravelUseCase createTravelUseCase;

        @MockitoBean
        private GetTravelUseCase getTravelUseCase;

        @MockitoBean
        private DeleteTravelUseCase deleteTravelUseCase;

        @MockitoBean
        private ModifyTravelUseCase modifyTravelUseCase;

        @MockitoBean
        private GetAllTravelUseCase getAllTravelUseCase;

        @MockitoBean
        private ChangeStateTravelUseCase changeStateTravelUseCase;

        @MockitoBean
        private GetPassengerListUseCase getPassengerListUseCase;

        @MockitoBean
        private GetAllTravelByDriverIdUseCase getAllTravelByDriverIdUseCase;

        @MockitoBean
        private GetAllTravelsByOrganizerUseCase getAllTravelsByOrganizerId;

        @MockitoBean
        private GetAllTravelsByPassengerIdUseCase getAllTravelsByPassengerIdUseCase;

        @MockitoBean
        private TravelMapperInitial travelMapper;

        @MockitoBean
        private UpdateTravelPassengersUseCase updateTravelPassengersUseCase;

        @MockitoBean
        private UpdateAvailableSlotsUseCase updateAvailableSlotsUseCase;

        private TravelRequest travelRequest;
        private TravelResponse travelResponse;
        private Travel travelDomain;
        private Travel travelDomainUpdated;

        LocalDateTime departureDate = LocalDateTime.of(2025, 12, 20, 14, 30, 0);

        @BeforeEach
        void setup() {
                travelRequest = TravelRequest.builder()
                                .driverId(10L)
                                .availableSlots(3)
                                .status(Status.ACTIVE)
                                .estimatedCost(20.5)
                                .departureDateAndTime(departureDate)
                                .passengersId(List.of(2L, 3L))
                                .travelType(TravelType.TRIP)
                                .conditions("No smoking")
                                .origin(null)
                                .destiny(null)
                                .build();

                travelResponse = TravelResponse.builder()
                                .id("550e8400-e29b-41d4-a716-446655440000")
                                .driverId(10L)
                                .availableSlots(3)
                                .estimatedCost(20.5)
                                .departureDateAndTime(departureDate)
                                .passengersId(List.of(2L, 3L))
                                .conditions("No smoking")
                                .origin(null)
                                .destiny(null)
                                .build();

                travelDomain = Travel.builder()
                                .id("550e8400-e29b-41d4-a716-446655440000")
                                .driverId(10L)
                                .availableSlots(3)
                                .status(Status.ACTIVE)
                                .estimatedCost(20.5)
                                .departureDateAndTime(departureDate)
                                .passengersId(List.of(2L, 3L))
                                .travelType(TravelType.TRIP)
                                .conditions("No smoking")
                                .origin(null)
                                .destiny(null)
                                .build();

                travelDomainUpdated = Travel.builder()
                                .id("550e8400-e29b-41d4-a716-446655440000")
                                .driverId(10L)
                                .availableSlots(5)
                                .status(Status.ACTIVE)
                                .estimatedCost(25.0)
                                .departureDateAndTime(departureDate)
                                .passengersId(List.of(2L, 3L, 4L))
                                .travelType(TravelType.TRIP)
                                .conditions("No smoking allowed")
                                .origin(null)
                                .destiny(null)
                                .build();
        }

        @DisplayName("Should create a new travel")
        @Test
        void shouldCreateTravelSuccessfully() throws Exception {
                when(travelMapper.toDomain(any(TravelRequest.class))).thenReturn(travelDomain);
                when(createTravelUseCase.createTravel(any())).thenReturn(travelDomain);
                when(travelMapper.toResponse(any(Travel.class))).thenReturn(travelResponse);

                mockMvc.perform(post("/travels")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(travelRequest)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value("550e8400-e29b-41d4-a716-446655440000"))
                                .andExpect(jsonPath("$.driverId").value(10L))
                                .andExpect(jsonPath("$.availableSlots").value(3))
                                .andExpect(jsonPath("$.estimatedCost").value(20.5))
                                .andExpect(jsonPath("$.conditions").value("No smoking"));

                verify(createTravelUseCase, times(1)).createTravel(any());
        }

        @DisplayName("Should update an existing travel")
        @Test
        void shouldUpdateTravelSuccessfully() throws Exception {
                when(travelMapper.toDomain(any(TravelRequest.class))).thenReturn(travelDomainUpdated);
                when(modifyTravelUseCase.updateTravel(eq("550e8400-e29b-41d4-a716-446655440000"),
                                any(TravelRequest.class)))
                                .thenReturn(travelDomainUpdated);
                when(travelMapper.toResponse(any(Travel.class))).thenReturn(travelResponse);

                mockMvc.perform(put("/travels/{id}", "550e8400-e29b-41d4-a716-446655440000")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(travelRequest)))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value("550e8400-e29b-41d4-a716-446655440000"))
                                .andExpect(jsonPath("$.driverId").value(10L))
                                .andExpect(jsonPath("$.availableSlots").value(3))
                                .andExpect(jsonPath("$.estimatedCost").value(20.5))
                                .andExpect(jsonPath("$.conditions").value("No smoking"));

                verify(modifyTravelUseCase, times(1))
                                .updateTravel(eq("550e8400-e29b-41d4-a716-446655440000"), any(TravelRequest.class));
        }

        @DisplayName("Should delete a travel")
        @Test
        void shouldDeleteTravelSuccessfully() throws Exception {
                doNothing().when(deleteTravelUseCase).deleteTravelById("550e8400-e29b-41d4-a716-446655440000");

                mockMvc.perform(delete("/travels/{id}", "550e8400-e29b-41d4-a716-446655440000"))
                                .andExpect(status().isNoContent());

                verify(deleteTravelUseCase, times(1)).deleteTravelById("550e8400-e29b-41d4-a716-446655440000");
        }

        @DisplayName("Should get a travel by ID")
        @Test
        void shouldGetTravelByIdSuccessfully() throws Exception {
                when(getTravelUseCase.getTravelById("550e8400-e29b-41d4-a716-446655440000")).thenReturn(travelDomain);
                when(travelMapper.toResponse(any(Travel.class))).thenReturn(travelResponse);

                mockMvc.perform(get("/travels/{id}", "550e8400-e29b-41d4-a716-446655440000")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value("550e8400-e29b-41d4-a716-446655440000"))
                                .andExpect(jsonPath("$.driverId").value(10L))
                                .andExpect(jsonPath("$.availableSlots").value(3))
                                .andExpect(jsonPath("$.estimatedCost").value(20.5))
                                .andExpect(jsonPath("$.conditions").value("No smoking"));

                verify(getTravelUseCase, times(1)).getTravelById("550e8400-e29b-41d4-a716-446655440000");
        }

        @DisplayName("Should get all travels")
        @Test
        void shouldGetAllTravelsSuccessfully() throws Exception {
                List<Travel> travelList = List.of(travelDomain);
                List<TravelResponse> travelResponseList = List.of(travelResponse);

                when(getAllTravelUseCase.getAllTravels()).thenReturn(travelList);
                when(travelMapper.toListResponse(any())).thenReturn(travelResponseList);

                mockMvc.perform(get("/travels/all")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].id").value("550e8400-e29b-41d4-a716-446655440000"))
                                .andExpect(jsonPath("$[0].driverId").value("10"))
                                .andExpect(jsonPath("$[0].availableSlots").value(3))
                                .andExpect(jsonPath("$[0].estimatedCost").value(20.5))
                                .andExpect(jsonPath("$[0].conditions").value("No smoking"));

                verify(getAllTravelUseCase, times(1)).getAllTravels();
        }

        @DisplayName("Should update travel status")
        @Test
        void shouldUpdateTravelStatusSuccessfully() throws Exception {
                Travel travelWithCancelledStatus = Travel.builder()
                                .id("550e8400-e29b-41d4-a716-446655440000")
                                .driverId(10L)
                                .availableSlots(3)
                                .status(Status.CANCELLED)
                                .estimatedCost(20.5)
                                .departureDateAndTime(departureDate)
                                .passengersId(List.of(2L, 3L))
                                .travelType(TravelType.TRIP)
                                .conditions("No smoking")
                                .origin(null)
                                .destiny(null)
                                .build();

                TravelResponse cancelledResponse = TravelResponse.builder()
                                .id("550e8400-e29b-41d4-a716-446655440000")
                                .driverId(10L)
                                .availableSlots(3)
                                .estimatedCost(20.5)
                                .departureDateAndTime(departureDate)
                                .passengersId(List.of(2L, 3L))
                                .conditions("No smoking")
                                .origin(null)
                                .destiny(null)
                                .build();

                when(changeStateTravelUseCase.changeStateTravel("550e8400-e29b-41d4-a716-446655440000",
                                Status.CANCELLED))
                                .thenReturn(travelWithCancelledStatus);
                when(travelMapper.toResponse(any(Travel.class))).thenReturn(cancelledResponse);

                mockMvc.perform(patch("/travels/{id}", "550e8400-e29b-41d4-a716-446655440000")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Status.CANCELLED)))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value("550e8400-e29b-41d4-a716-446655440000"))
                                .andExpect(jsonPath("$.driverId").value(10L))
                                .andExpect(jsonPath("$.availableSlots").value(3))
                                .andExpect(jsonPath("$.estimatedCost").value(20.5));

                verify(changeStateTravelUseCase, times(1))
                                .changeStateTravel("550e8400-e29b-41d4-a716-446655440000", Status.CANCELLED);
        }

        @DisplayName("Should get occupant list")
        @Test
        void shouldGetOccupantListSuccessfully() throws Exception {
                List<Long> passengerList = List.of(2L, 3L);

                when(getPassengerListUseCase.getPassengerList("550e8400-e29b-41d4-a716-446655440000", passengerList))
                                .thenReturn(passengerList);

                mockMvc.perform(get("/travels/occupantList/{id}", "550e8400-e29b-41d4-a716-446655440000")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(passengerList)))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0]").value(2))
                                .andExpect(jsonPath("$[1]").value(3));

                verify(getPassengerListUseCase, times(1))
                                .getPassengerList("550e8400-e29b-41d4-a716-446655440000", passengerList);
        }

        @DisplayName("Should get all travels by driver ID")
        @Test
        void shouldGetAllTravelsByDriverIdSuccessfully() throws Exception {
                List<Travel> travelList = List.of(travelDomain);
                List<TravelResponse> travelResponseList = List.of(travelResponse);

                when(getAllTravelByDriverIdUseCase.getAllTravelsByDriverId(10L)).thenReturn(travelList);
                when(travelMapper.toListResponse(any())).thenReturn(travelResponseList);

                mockMvc.perform(get("/travels/driver/{driverId}", 10L)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].id").value("550e8400-e29b-41d4-a716-446655440000"))
                                .andExpect(jsonPath("$[0].driverId").value(10L))
                                .andExpect(jsonPath("$[0].availableSlots").value(3))
                                .andExpect(jsonPath("$[0].estimatedCost").value(20.5));

                verify(getAllTravelByDriverIdUseCase, times(1)).getAllTravelsByDriverId(10L);
        }

        @DisplayName("Should get all travels by organizer ID")
        @Test
        void shouldGetAllTravelsByOrganizerIdSuccessfully() throws Exception {
                Travel travelWithOrganizer = Travel.builder()
                                .id("550e8400-e29b-41d4-a716-446655440000")
                                .organizerId(5L)
                                .driverId(10L)
                                .availableSlots(3)
                                .status(Status.ACTIVE)
                                .estimatedCost(20.5)
                                .departureDateAndTime(departureDate)
                                .passengersId(List.of(2L, 3L))
                                .travelType(TravelType.TRIP)
                                .conditions("No smoking")
                                .origin(null)
                                .destiny(null)
                                .build();

                TravelResponse responseWithOrganizer = TravelResponse.builder()
                                .id("550e8400-e29b-41d4-a716-446655440000")
                                .organizerId(5L)
                                .driverId(10L)
                                .availableSlots(3)
                                .estimatedCost(20.5)
                                .departureDateAndTime(departureDate)
                                .passengersId(List.of(2L, 3L))
                                .conditions("No smoking")
                                .origin(null)
                                .destiny(null)
                                .build();

                List<Travel> travelList = List.of(travelWithOrganizer);
                List<TravelResponse> travelResponseList = List.of(responseWithOrganizer);

                when(getAllTravelsByOrganizerId.getAllTravelsByOrganizerId(5L)).thenReturn(travelList);
                when(travelMapper.toListResponse(any())).thenReturn(travelResponseList);

                mockMvc.perform(get("/travels/organizer/{organizerId}", 5L)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].id").value("550e8400-e29b-41d4-a716-446655440000"))
                                .andExpect(jsonPath("$[0].organizerId").value(5L))
                                .andExpect(jsonPath("$[0].driverId").value(10L))
                                .andExpect(jsonPath("$[0].availableSlots").value(3));

                verify(getAllTravelsByOrganizerId, times(1)).getAllTravelsByOrganizerId(5L);
        }

        @DisplayName("Should get all travels by passenger ID")
        @Test
        void shouldGetAllTravelsByPassengerIdSuccessfully() throws Exception {
                List<Travel> travelList = List.of(travelDomain);
                List<TravelResponse> travelResponseList = List.of(travelResponse);

                when(getAllTravelsByPassengerIdUseCase.getAllTravelsByPassengerId(2L)).thenReturn(travelList);
                when(travelMapper.toListResponse(any())).thenReturn(travelResponseList);

                mockMvc.perform(get("/travels/passenger/{passengerId}", 2L)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].id").value("550e8400-e29b-41d4-a716-446655440000"))
                                .andExpect(jsonPath("$[0].driverId").value(10L))
                                .andExpect(jsonPath("$[0].passengersId[0]").value(2))
                                .andExpect(jsonPath("$[0].passengersId[1]").value(3));

                verify(getAllTravelsByPassengerIdUseCase, times(1)).getAllTravelsByPassengerId(2L);
        }

        @DisplayName("Should update available slots")
        @Test
        void shouldUpdateAvailableSlotsSuccessfully() throws Exception {
                String travelId = "550e8400-e29b-41d4-a716-446655440000";
                Integer quantity = -1;

                doNothing().when(updateAvailableSlotsUseCase).updateAvailableSlots(travelId, quantity);

                mockMvc.perform(patch("/travels/{id}/slots", travelId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(quantity)))
                                .andExpect(status().isOk());

                verify(updateAvailableSlotsUseCase, times(1)).updateAvailableSlots(travelId, quantity);
        }
}