package com.lota.SafeVaultBankingApplication.mapper;

import com.lota.SafeVaultBankingApplication.dtos.request.UpdateProfileRequest;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUserProfile;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ApplicationMapper {

    @Condition
    default boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
    void updateProfileToSafeVaultUserProfile(UpdateProfileRequest request, @MappingTarget SafeVaultUserProfile safeVaultUserProfile);
}
