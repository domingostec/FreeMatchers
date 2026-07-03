package org.example.freematchers.application.mapper;

import org.example.freematchers.domain.model.Allocation;
import org.example.freematchers.shared.dto.request.AllocationRequest;
import org.example.freematchers.shared.dto.response.AllocationResponse;
import org.example.freematchers.shared.dto.response.MatchResultResponse;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface AllocationMapper {

    @Mapping(target = "allocationId", source = "id")
    @Mapping(target = "developerName", source = "developer.name")
    @Mapping(target = "projectTitle", source = "project.title")
    @Mapping(target = "recruiterName", source = "project.recruiter.name")
    @Mapping(target = "hoursAllocated", source = "project.requiredHours")
    @Mapping(target = "status", source = "ACTIVE")
    AllocationResponse allocationToAllocationResponse(Allocation allocation);

    Allocation allocationRequestToAllocation(AllocationRequest allocationRequest);

    @Mapping(target = "developerId", source = "developer.id")
    @Mapping(target = "developerName", source = "developer.name")
    @Mapping(target = "availableHours", expression = "java(allocation.getDeveloper().getWorkload())")
    @Mapping(target = "matchPercentage", ignore = true)
    @Mapping(target = "matchingSkills", ignore = true)
    MatchResultResponse allocationToMatchResultResponse(Allocation allocation);

    List<MatchResultResponse> allocationToMatchResultResponse(List<Allocation> allocations);
}

