package com.noticeflow.back.dto;

import com.noticeflow.back.domain.OrganizationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "기관 사용자 등록 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRegistrationRequest {

    @Schema(description = "기관 유형", example = "SCHOOL", required = true)
    @NotNull(message = "기관 유형을 선택해주세요")
    private OrganizationType type;

    @Schema(description = "기관명", example = "서울대학교", required = true)
    @NotBlank(message = "기관명을 입력해주세요")
    private String organizationName;

    @Schema(description = "담당자 연락처", example = "01012345678", required = true)
    @NotBlank(message = "담당자 연락처를 입력해주세요")
    private String contactNumber;

    @Schema(description = "기관 주소", example = "서울시 관악구", required = true)
    @NotBlank(message = "기관 주소를 입력해주세요")
    private String address;
}