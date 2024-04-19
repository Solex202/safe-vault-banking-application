package com.lota.SafeVaultBankingApplication.models;

import com.lota.SafeVaultBankingApplication.dtos.request.UpdateProfileRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ApplicationMapper {
    void updateProfileToSafeVaultUserProfile(UpdateProfileRequest request, @MappingTarget SafeVaultUserProfile safeVaultUserProfile);
}
